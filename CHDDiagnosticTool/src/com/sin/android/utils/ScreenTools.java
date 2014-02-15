package com.sin.android.utils;

import android.content.Context;

/**
 * 屏幕工具类
 * @author RobinTang
 * @time 2012-09-04
 */
public class ScreenTools {
	
	/**
	 * 独立像素(dp)到像素(px)
	 */
	public static int dip2px(Context context, float dpValue) { 
	    final float scale = context.getResources().getDisplayMetrics().density; 
	    return (int) (dpValue * scale + 0.5f); 
	} 
	  
	/**
	 * 像素(px)到独立像素(dp)
	 */ 
	public static int px2dip(Context context, float pxValue) { 
	    final float scale = context.getResources().getDisplayMetrics().density; 
	    return (int) (pxValue / scale + 0.5f); 
	} 
}
