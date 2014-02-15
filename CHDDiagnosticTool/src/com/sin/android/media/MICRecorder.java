package com.sin.android.media;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

/**
 * 麦克风录制
 * @author RobinTang
 * @time 2012-10-04
 */
public class MICRecorder {
	private static final String tag = "MICRecorder";
	
	private int orgSampleFrequence = 11025; // 原始的采样率
	private int sampleFrequence = 5000;	// 所需的采样率
	private int[] buffer = null;	// 缓冲区，直接采集到该缓冲区中去
	private int bufferlen = 0;
	private MICRecordListener listener = null;
	public MICRecorder(int orgSampleFrequence, int sampleFrequence, int[] buffer) {
		super();
		this.orgSampleFrequence = orgSampleFrequence;
		this.sampleFrequence = sampleFrequence;
		this.buffer = buffer;
		this.bufferlen = buffer.length;
	}
	
	public void setListener(MICRecordListener listener){
		this.listener = listener;
	}
	
	
	private RecThread thread = null;
	private boolean recording;
	private boolean stop;
	private int index;
	// 记录线程
	class RecThread extends Thread{
		@Override
		public void run() {
			Log.i(tag, "record running");
			int channelcfg = AudioFormat.CHANNEL_CONFIGURATION_MONO; 	// 声道
			int encoding = AudioFormat.ENCODING_PCM_16BIT;	// 编码方式
			int source = MediaRecorder.AudioSource.MIC;	// 声音源
			// 计算最小的缓冲区大小
			int recbufsize = AudioRecord.getMinBufferSize(orgSampleFrequence, channelcfg, encoding);
			AudioRecord audioRecord = new AudioRecord(source, orgSampleFrequence, channelcfg, encoding, recbufsize);
			byte[] recbuf = new byte[recbufsize];
			audioRecord.startRecording();

			
			int readlen = 0;
			int data = 0;

			// 根据采样率设置步长
			// 乘以2是因为每个数据占两字节
			int step = 2*(orgSampleFrequence/sampleFrequence);
			
			stop = false;
			recording = true;
			index = 0;
			int startpos = 0;
			while(stop == false){
				
				// 从缓冲区中读取当前帧
				readlen = audioRecord.read(recbuf, 0, recbufsize);
				if(recording){
					int len = 0;
					for(int i=startpos; i<readlen; i+=step){
						
						// 下面的两句完成2byte到int的转换
						data = recbuf[i+1];
						data = (data<<8)|(recbuf[i] & 0x000000FF);
						if(index < bufferlen){
							// 记录没满
							buffer[index] = data;
							++index;
							++len;
						}
						else{
							// 记录满
							break;
						}
					}
					// 计算下一次的起始位置，防止readLen不能被step整除是引起的数据丢失
					startpos = (readlen-startpos)%step;
					// 一帧完
					if(listener != null){
						listener.recordedOneFrame(len, index);
						if(index >= bufferlen){
							// 缓冲区满
							listener.bufferFull();
						}
					}
				}
			}
			// 停止记录
			audioRecord.stop();
			Log.i(tag, "record stoped");
		}
	}
	
	public void pause(){
		this.recording = false;
	}
	public void record(){
		if(thread == null){
			thread = new RecThread();
		}
		if(thread.isAlive()==false)
			thread.start();
		this.recording = true;
	}
	public void stop(){
		this.recording = false;
		this.stop = true;
		this.thread = null;
	}
}
