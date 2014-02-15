package cn.ynu.java.dsp.filter;

import cn.ynu.java.dsp.Complex;
import cn.ynu.java.dsp.SignalTransfor;

import com.sin.chd.base.pipe.ArgumentDescribe;

/**
 * 快速傅立叶变换器
 * 
 * @author RobinTang
 * 
 */
public class FFT extends BaseFilter {
	/**
	 * 快速傅立叶变换器
	 */
	public FFT() {
		super("复数数组快速傅里叶变换", "", ArgumentDescribe.complexArray("输入复数数组"), ArgumentDescribe.complexArray("输出复数数组"));
	}

	@Override
	public Object calculate(Object input) {
		return SignalTransfor.fft((Complex[]) input);
	}
}
