package cn.ynu.java.dsptest;

import cn.ynu.java.dsp.Complex;
import cn.ynu.java.dsp.SignalTransfor;

public class TestMain {

	public static void main(String[] args) {
		
		int len = 65536;
		double[] a = new double[len];
		for(int i=0; i<len; ++i){
			a[i] = (i/1024)*2*Math.PI;	// 一共2个周期
		}
		
		long ct = System.currentTimeMillis();
		Complex[] b = Complex.createComplexs(a);	// 创建复数
		System.out.println("Time0: " + (System.currentTimeMillis()-ct));
		
		ct = System.currentTimeMillis();
		Complex[] c= SignalTransfor.fft(b);	// 快速傅立叶变换
		System.out.println("Time1: " + (System.currentTimeMillis()-ct));
		
		ct = System.currentTimeMillis();
		Complex[][] d = SignalTransfor.stft(b);	//短时傅里叶
		System.out.println("Time2: " + (System.currentTimeMillis()-ct));
	}
}

