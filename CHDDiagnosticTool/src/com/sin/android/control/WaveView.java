package com.sin.android.control;

import com.sin.android.utils.ScreenTools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 
 * 波形控件，用来显示波形
 * @author RobinTang
 * @time 2012-09-15
 */
public class WaveView extends View {
	private Paint textPaint = null;
	private Paint pointPaint = null;
	private Paint linePaint = null;
	private String tag = "WaveView";
	
	private double startpos = 0;
	private int[] wave = null;
	private int waveoffset = 0;
	private int wavelen = 0;
	private double drawcount = 0;
	
	private int xvaluecount = 10;
	
	private boolean isdrawing = false;
	
	private TouchExplainer touchExplainer = null;
	
	private int thisWidth = 0;
	private int thisHeight = 0;
	
	private boolean lockCount = false;
	private boolean lockLocation = false;
	
	private int valuemin = 0;
	private int valuemax = 0;
	private int valueoffset = 0;
	
	// 属性
	private String title = null;

	
	private int swicthValue(int data){
		int sl = -thisHeight/2;
		int su = thisHeight/2;
		
		if(data<valuemin)
			return sl;
		else if(data>valuemax)
			return su;
		else if(valueoffset==0){
			return 0;
		}
		else{
	    	double k = (double)(thisHeight)/(double)(valuemax-valuemin);
	    	return (int)((data-valuemin)*k+sl);
		}
	}
	
	public WaveView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.initView(null, 0, false, false);
	}

	public WaveView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initView(null, 0, false, false);
	}

	public WaveView(Context context) {
		super(context);
		this.initView(null, 0, false, false);
	}

	public WaveView(Context context, int[] wav, int drawcount, boolean locakLocation, boolean lockCount) {
		super(context);
		this.initView(wav, drawcount, locakLocation, lockCount);
	}
	
	public WaveView(Context context, int[] wav, int drawcount) {
		super(context);
		this.initView(wav, drawcount, false, false);
	}
	
	public WaveView(Context context, int[] wav) {
		super(context);
		this.initView(wav, 0, false, false);
	}
	
	
	private void initView(int[] wav, int drawcount, boolean locakLocation, boolean lockCount){
		if(wav != null)
			this.setWaveData(wav, 0, wav.length);
		
		if(this.getTag()!=null)
			this.title = (String) this.getTag();
		
		this.drawcount = drawcount;
		this.lockCount = lockCount;
		this.lockLocation = locakLocation;
		
		this.textPaint = new Paint();
		this.textPaint.setColor(Color.RED);
		this.textPaint.setTextSize(ScreenTools.dip2px(getContext(), 16));
		
		this.linePaint = new Paint();
		this.linePaint.setColor(Color.GREEN);
		
		this.pointPaint = new Paint();
		this.pointPaint.setColor(Color.GREEN);
		
		this.touchExplainer = new TouchExplainer();
		
		this.setOnTouchListener(onTouchListener);
	}
	
	private OnTouchListener onTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			//Log.i(tag, "Touch:" + event.getAction());
			touchExplainer.explainer(event);
			if(touchExplainer.isZoomX() && lockCount==false){
				
				drawcount = Math.min((int)(drawcount*touchExplainer.getZoomX()), wavelen);
				
				/*
				if(motionEventExplainer.isMoveX() && lockLocation==false && vieww!=0){
					startpos = (double)(startpos+(motionEventExplainer.getMoveX()*(double)drawcount/(double)vieww));
				}
				*/
				
				if(startpos>(wavelen-(int)drawcount))
					startpos = wavelen-(int)drawcount;
				if(drawcount<0){
					drawcount = 1;
				}
				reDraw();
			}
			else if(touchExplainer.isMoveX() && lockLocation==false){
				//Log.i(tag, "oX:"+touchExplainer.getMoveX());
				if((int)drawcount > 0 && thisWidth != 0){
					startpos = (double)(startpos+(touchExplainer.getMoveX()*(double)drawcount/(double)thisWidth));
				}
				
				if(startpos<0)
					startpos = 0;
				else if(startpos>(wavelen-(int)drawcount))
					startpos = wavelen-(int)drawcount;
				reDraw();
			}
			
			return true;
		}
	};
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
//		canvas.drawColor(Color.BLACK);
		thisWidth = this.getWidth();
		thisHeight = this.getHeight();
		
		if(this.title != null){
			float w = textPaint.measureText(this.title);
			float h = textPaint.getTextSize();
			canvas.drawText(this.title, (thisWidth-w)/2, h, textPaint);
		}
		
		if(this.wave == null){
			canvas.drawLine(0, 0, thisWidth, thisHeight, linePaint);
			canvas.drawLine(0, thisHeight, thisWidth, 0, linePaint);
		
			return;
		}
		isdrawing = true;		
		
		int count = (int)drawcount;
		
		if(count == 0){
			drawcount = this.getWidth();
		}
		else if(count < xvaluecount){
			drawcount = xvaluecount;
		}
		else if(count > (this.wavelen-startpos)){
			drawcount = this.wavelen-startpos;
		}
		
		canvas.drawLine(0, thisHeight/2, thisWidth, thisHeight/2, textPaint);
		
		count = (int)drawcount;
		
		// String txt = "startpos:"+(int)startpos + " drawcount:"+(int)drawcount;
		// canvas.drawText(txt, 0, txt.length(), this.getWidth()/2-50, 20, textPaint);
		// //Log.i(tag, txt);
		String txt = null;
		int px = 0, py = 0;
		
		if(count<=thisWidth){
			double k = (double)thisWidth/(double)count;
			int md = (int)drawcount/xvaluecount;
			if(md==0)
				md = 1;
			int pos, x, y;
			for(int i=0; i<count+1; ++i){
				pos = i + (int)startpos;
				x = (int)(i*k);
				if((pos+this.waveoffset)<wave.length && pos<this.wavelen && pos>=0){
					y = thisHeight-(swicthValue(this.wave[this.waveoffset + pos])+thisHeight/2);
					if(i!=0){
						canvas.drawLine(x, y, px, py, pointPaint);
					}
					px = x;
					py = y;
					if(pos%md == 0){
						txt = "" + pos;
						float h = textPaint.getTextSize();
//						canvas.drawText(txt, 0, txt.length(), x, (this.getHeight()+h)/2, textPaint);
					}
				}
				else{
					break;
				}
			}
		}
		else{
			int pos, x, y;
			for(int i=0; i<thisWidth; ++i){
				double k = (double)count/(double)thisWidth;
				int md = (int)thisWidth/xvaluecount;
				if(md==0)
					md = 1;
				pos = (int)(i*k + startpos);
				x = i;
				
				if((pos+this.waveoffset)<wave.length && pos<this.wavelen && pos>=0){
					y = thisHeight-(swicthValue(this.wave[this.waveoffset + pos])+thisHeight/2);
					
					int n = (int)(k);
					int mxy = this.wave[this.waveoffset + pos];
					int mny = this.wave[this.waveoffset + pos];
					for(int j=0; j<(n+1); ++j){
						int ix = j+pos;
						if((ix)<wave.length){
							int ny = this.wave[ix];
							if(ny>mxy)
								mxy = ny;
							if(ny<mny)
								mny = ny;
						}
						else{
							break;
						}
					}
					if(mny!=mxy){
						if(i!=0){
							mny = swicthValue(mny);
							mxy = swicthValue(mxy);
							canvas.drawLine(x, thisHeight-(mxy+thisHeight/2), x, thisHeight-(mny+thisHeight/2), pointPaint);
						}
					}
					else{
						
					}
					
					if(i!=0){
						canvas.drawLine(x, y, px, py, pointPaint);
					}

					px = x;
					py = y;
					if(i%md == 0){
						txt = "" + pos;
						float h = textPaint.getTextSize();
//						canvas.drawText(txt, 0, txt.length(), x, (thisHeight+h)/2, textPaint);
					}
				}
				else{
					break;
				}
			}
		}
		isdrawing = false;
	}
	
	public void reDraw(){
		if(!isdrawing){
			this.invalidate();
		}
	}
	
	public void setStartX(int x){
		this.startpos = x;
	}
	
	public int getStartX(){
		return (int)this.startpos;
	}
	
	public int getCount(){
		return (int)this.drawcount;
	}
	
	public void setCount(int count){
		this.drawcount = count;
	}
	
	public void setWaveData(int[] wav){
		if(wav!=null)
			setWaveData(wav, 0, wav.length);
	}
	
	public void setWaveData(int[] wav, int offset, int len){
		this.wave = wav;
		this.waveoffset = offset;
		this.wavelen = len;
	}
	
	public void setZoomable(boolean flag){
		this.lockCount = !flag;
	}
	
	public void setMoveable(boolean flag){
		this.lockLocation = !flag;
	}
	
	public int setValueRange(int min, int max){
		this.valuemin = Math.min(min, max);
		this.valuemax = Math.max(min, max);
		this.valueoffset = valuemax-valuemin;
		return this.valueoffset; 
	}
}


