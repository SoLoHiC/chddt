package com.sin.android.media;

/**
 * 麦克风录制监听器
 * @author RobinTang
 * @time 2012-10-05
 */
public interface MICRecordListener {
	abstract public void recordedOneFrame(int len, int nowindex);	// 记录一帧数据
	abstract public void bufferFull();	// 缓冲器已满
}
