package com.sin.android.utils;


/**
 * 一个奔跑者?
 * @author RobinTang
 * @time 2012-09-04
 */
public class Runner {
	public Runner(Runnable runnable) {
		new Thread(runnable).start();
	}
}
