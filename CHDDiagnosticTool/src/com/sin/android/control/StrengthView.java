package com.sin.android.control;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 
 * 能量显示控件
 * @author RobinTang
 * @time 2012-09-16
 */
public class StrengthView extends View {
	static private String tag = "StrengthView";
	
	static public int getLevel(){
		return colormap.length;
	}
	
	static final private int[][] colormap = new int[][]{
		{0,     0,   30},
		{0,     0,   60},
		{0,     0,   144},
		{0,     0,   160},
		{0,     0,   176},
		{0,     0,   192},
		{0,     0,   208},
		{0,     0,   224},
		{0,     0,   240},
		{0,     0,   255},
		{0,    16,   255},
		{0,    32,   255},
		{0,    48,   255},
		{0,    64,   255},
		{0,    80,   255},
		{0,    96,   255},
		{0,   112,   255},
		{0,   128,   255},
		{0,   144,   255},
		{0,   160,   255},
		{0,   176,   255},
		{0,   192,   255},
		{0,   208,   255},
		{0,   224,   255},
		{0,   240,   255},
		{0,   255,   255},
		{16,   255,   240},
		{32,   255,   224},
		{48,   255,   208},
		{64,   255,   192},
		{80,   255,   176},
		{96,   255,   160},
		{112,   255,   144},
		{128,   255,   128},
		{144,   255,   112},
		{160,   255,    96},
		{176,   255,    80},
		{192,   255,    64},
		{208,   255,    48},
		{224,   255,    32},
		{240,   255,    16},
		{255,   255,     0},
		{255,   240,     0},
		{255,   224,     0},
		{255,   208,     0},
		{255,   192,     0},
		{255,   176,     0},
		{255,   160,     0},
		{255,   144,     0},
		{255,   128,     0},
		{255,   112,     0},
		{255,    96,     0},
		{255,    80,     0},
		{255,    64,     0},
		{255,    48,     0},
		{255,    32,     0},
		{255,    16,     0},
		{255,     0,     0},
		{240,     0,     0},
		{224,     0,     0},
		{208,     0,     0},
		{192,     0,     0},
		{176,     0,     0},
		{160,     0,     0},
		{144,     0,     0},
		{128,     0,     0}
	};
	
	private int colorlevel = 1; 

	private int[][] data = null;
	private int dataw = 0;
	private int datah = 0;
	
	private Paint textPaint = null;
	private Paint linePaint = null;
	private Paint boxPaint = null;
	
	private TouchExplainer touchExplainer = null;
	
	
	private int colmapw = 50;
	private int colmaph = 3;
	private int colmapx = 0;
	private int colmapy = 0;
	private boolean colmapmoving = false;
	public StrengthView(Context context) {
		super(context);
		
		this.initThis(null);
	}
	
	
	public StrengthView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.initThis(null);
	}


	public StrengthView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initThis(null);
	}


	private void initThis(int[][] data){
		this.setData(data);
		
		this.colorlevel = colormap.length;
		
		this.textPaint = new Paint();
		this.textPaint.setColor(Color.RED);
		
		this.linePaint = new Paint();
		this.linePaint.setColor(Color.GREEN);

		this.boxPaint = new Paint();
		this.boxPaint.setColor(Color.BLUE);
		this.boxPaint.setStyle(Style.FILL);
		this.touchExplainer = new TouchExplainer();
		
		this.setOnTouchListener(onTouchListener);
	}
	
	private OnTouchListener onTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			touchExplainer.explainer(event);
			if(touchExplainer.isDown()){
				int x = touchExplainer.getX();
				int y = touchExplainer.getY();
				
				int left = colmapx;
				int top = colmapy;
				int right = left+colmapw;
				int bottom = top+colorlevel*colmaph;
				if(x>left && x<right && y>top && y<bottom){
					colmapmoving = true;
				}
				else{
					colmapmoving = false;
				}
			}
			if(touchExplainer.isMove() && colmapmoving){
				colmapx -= touchExplainer.getMoveX();
				colmapy -= touchExplainer.getMoveY();
				invalidate();
				Log.i(tag, "colmap move");
			}
			if(touchExplainer.isUp()){
				colmapmoving = false;
			}
			return true;
		}
	};
	
	public void setData(int[][] data){
		if(data!=null){
			this.data = data;
			this.dataw = data[0].length;
			this.datah = data.length;
		}
		else{
			this.data = null;
			this.dataw = 0;
			this.datah = 0;
		}
	}
	

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		if(this.data == null){
			canvas.drawLine(0, 0, getWidth(), getHeight(), linePaint);
			canvas.drawLine(0, getHeight(), getWidth(), 0, linePaint);
			canvas.drawText(tag, getWidth()/2, getHeight()/2, textPaint);
		}
		else{
			int vieww = this.getWidth();
			int viewh = this.getHeight();
			double kw = (double)vieww/(double)this.dataw;
			double kh = (double)viewh/(double)this.datah;
			
			String txt = "vw:"+vieww+" wh:"+viewh+" kw:"+kw+" kh:"+kh+" dw:"+dataw+" dh:"+datah;
			
			int left=0, top=0, right=0, bottom=0;
			int val = 0;
			int[] col = null;
			int w = (int)kw;
			int h = (int)kh;
			if(w==0)
				w = 1;
			if(h==0)
				h = 1;
			for(int j=0; j<this.datah; ++j){
				for(int i=0; i<this.dataw; ++i){
					left = (int)(i*kw)-1;
					top = (int)(j*kh)-1;
					right = left+w+1;
					bottom = top+h+1;
					
					val = this.data[j][i];
					col = colormap[val];
					int color = Color.argb(0xff, col[0], col[1], col[2]);
					boxPaint.setColor(color);
					canvas.drawRect(left, top, right, bottom, boxPaint);
				}
			}
			
			for(int i=0; i<this.colorlevel; ++i){
				col = colormap[this.colorlevel-i-1];
				if(col.length!=3)
					col = null;
				int color = Color.argb(0xff, col[0], col[1], col[2]);
				boxPaint.setColor(color);
				canvas.drawRect(colmapx, colmapy+i*colmaph, colmapx+colmapw, colmapy+(i+1)*colmaph, boxPaint);
			}
			canvas.drawText(txt, 20, 20, textPaint);
		}
	}

}
