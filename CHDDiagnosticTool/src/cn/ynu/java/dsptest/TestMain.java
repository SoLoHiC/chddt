package cn.ynu.java.dsptest;

import cn.ynu.java.dsp.Complex;
import cn.ynu.java.dsp.SignalTransfor;

public class TestMain {

	public static void main(String[] args) {
		
		int len = 65536;
		double[] a = new double[len];
		for(int i=0; i<len; ++i){
			a[i] = (i/1024)*2*Math.PI;	// һ��2������
		}
		
		long ct = System.currentTimeMillis();
		Complex[] b = Complex.createComplexs(a);	// ��������
		System.out.println("Time0: " + (System.currentTimeMillis()-ct));
		
		ct = System.currentTimeMillis();
		Complex[] c= SignalTransfor.fft(b);	// ���ٸ���Ҷ�任
		System.out.println("Time1: " + (System.currentTimeMillis()-ct));
		
		ct = System.currentTimeMillis();
		Complex[][] d = SignalTransfor.stft(b);	//��ʱ����Ҷ
		System.out.println("Time2: " + (System.currentTimeMillis()-ct));
	}
}

