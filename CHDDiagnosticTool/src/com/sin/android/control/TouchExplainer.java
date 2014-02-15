package com.sin.android.control;

import android.util.Log;
import android.view.MotionEvent;

/**
 * 
 * 触摸解释器
 * @author RobinTang
 * @time 2012-09-15
 */
public class TouchExplainer {	
	private int oldx = 0;	//上一次x坐标
	private int oldy = 0;	//上一次y坐标
	
	private boolean ismovex = false;
	private boolean ismovey = false;
	private boolean ismove = false;
	private boolean isdown = false;
	private boolean isup = false;
	
	private boolean iszoom = false;
	private boolean iszoomx = false;
	private boolean iszoomy = false;
	
	private double zoomx = 0;
	private double zoomy = 0;
	private double zoom = 0;
	
	private double oldlen = 0;
	private int oldlenx = 0;
	private int oldleny = 0;
	
	private int movex = 0;
	private int movey = 0;
	
	private boolean istwopoint = false;
	
	static private String tag = "MotionEventExplainer";
	
	
	public int getX(){
		return this.oldx;
	}
	
	public int getY(){
		return this.oldy;
	}
	
	public double getZoom(){
		return this.zoom;
	}
	
	public double getZoomX(){
		return this.zoomx;
	}
	
	public double getZoomY(){
		return this.zoomy;
	}
	
	public boolean isZoomX(){
		return this.iszoomx;
	}
	
	public boolean isZoomY(){
		return this.iszoomy;
	}
	
	public boolean isZoom(){
		return this.iszoom;
	}
	
	public boolean isMoveX() {
		return this.ismovex;
	}
	
	public boolean isMoveY() {
		return this.ismovey;
	}
	
	public boolean isDown(){
		return this.isdown;
	}
	
	public boolean isUp(){
		return this.isup;
	}
	
	public boolean isMove(){
		return this.ismove;
	}
	
	public int getMoveX() {
		return this.movex;
	}
	
	public int getMoveY() {
		return this.movey;
	}
	
	//解释事件
	public TouchExplainer explainer(MotionEvent event){
		ismovex = false;
		ismovey = false;
		ismove = false;
		isdown = false;
		isup = false;
		
		iszoom = false;
		iszoomx = false;
		iszoomy = false;
		
		int pointerCount = event.getPointerCount();
		
		//Log.i(tag, "pcount="+pointerCount + " Event="+event.getAction());
		
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:{
				//Log.i(tag, "Touch: ACTION_DOWN");
				if(pointerCount == 1){
					oldx = (int) event.getX(0);
					oldy = (int) event.getY(0);
					this.istwopoint = false;
				}
				else if(pointerCount == 2){
					int x0 = (int) event.getX(0);
					int y0 = (int) event.getY(0);
					int x1 = (int) event.getX(1);
					int y1 = (int) event.getY(1);
					oldlenx = Math.abs(x0-x1);
					oldleny = Math.abs(y0-y1);
					oldlen = Math.sqrt(oldlenx*oldlenx+oldleny*oldleny);
					this.zoomy = this.zoomx = this.zoom = 1;
				}
				this.isdown = true;
				break;
			}
			case MotionEvent.ACTION_POINTER_2_UP:
			case MotionEvent.ACTION_UP:{
				//Log.i(tag, "Touch: ACTION_UP");
				this.isup = true;
				this.istwopoint = false;
				this.oldx = (int) event.getX(0);
				this.oldy = (int) event.getY(0);
				break;
			}
			case MotionEvent.ACTION_MOVE:{
				//Log.i(tag, "Touch: ACTION_MOVE");
				int x = 0, y = 0;
				if(pointerCount == 1){
					// 单点移动
					x = (int) event.getX(0);
					y = (int) event.getY(0);
					if(this.istwopoint){
						this.istwopoint = false;
					}
				}
				else if(pointerCount == 2){
					int x0,y0,x1,y1;
					if(this.istwopoint == false){
						x0 = (int) event.getX(0);
						y0 = (int) event.getY(0);
						x1 = (int) event.getX(1);
						y1 = (int) event.getY(1);
						oldlenx = Math.abs(x0-x1);
						oldleny = Math.abs(y0-y1);
						oldlen = Math.sqrt(oldlenx*oldlenx+oldleny*oldleny);
						this.zoomy = this.zoomx = this.zoom = 1;
						this.istwopoint = true;
						
						oldx = Math.abs(x0-x1)/2+Math.min(x0, x1);
						oldy = Math.abs(y0-y1)/2+Math.min(y0, y1);
						break;
					}
					else{
						x0 = (int) event.getX(0);
						y0 = (int) event.getY(0);
						x1 = (int) event.getX(1);
						y1 = (int) event.getY(1);
						int w = Math.abs(x0-x1);
						int h = Math.abs(y0-y1);
						if(w != this.oldlenx){
							this.iszoomx = true;
							this.zoomx = (double)this.oldlenx/(double)w;
							this.oldlenx = w;
						}
						if(h != this.oldleny){
							this.iszoomy = true;
							this.zoomy = (double)this.oldleny/(double)h;
							this.oldleny = h;
						}
						if(this.iszoomx && this.iszoomy){
							this.iszoom = true;
							double len = Math.sqrt(w*w+h*h);
							this.zoom = (double)this.oldlen/len;
							this.oldlen = len;
						}
						
						x = Math.abs(x0-x1)/2+Math.min(x0, x1);
						y = Math.abs(y0-y1)/2+Math.min(y0, y1);
					}
				}
				if(pointerCount==1 || (pointerCount==2 && this.istwopoint)){
					if(x != oldx){
						this.ismovex = true;
						this.movex = this.oldx - x;
						this.oldx = x;
					}
					else{
						this.movex = 0;
					}
					if(y != oldy){
						this.ismovey = true;
						this.movey = this.oldy - y;
						this.oldy = x;
					}
					else{
						this.movey = 0;
					}
					this.ismove = true;
					this.istwopoint = false;
					this.oldx = x;
					this.oldy = y;
				}
				
				//Log.i("movex", "x="+oldx+" movex:"+this.movex);
				break;
			}
		}
		return this;
	}
}