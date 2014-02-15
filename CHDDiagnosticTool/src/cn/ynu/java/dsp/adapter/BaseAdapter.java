package cn.ynu.java.dsp.adapter;

import cn.ynu.java.dsp.Complex;

import com.sin.chd.base.pipe.ArgumentDescribe;
import com.sin.chd.base.pipe.BasePipe;
import com.sin.chd.base.pipe.PipeType;

/**
 * 滤波器基类
 * 所有的适配器均继承自该类
 * 适配器主要完成的是值类型直接的转换
 * @author RobinTang
 *
 */
public class BaseAdapter extends BasePipe {
	protected int offset;	// 起始位置
	protected int lenght;	// 长度
	public BaseAdapter(String name, String explain, ArgumentDescribe inputDescribe, ArgumentDescribe outputDescribe, int offset, int lenngth) {
		super();
		this.name = name;
		this.explain = explain;
		this.type = PipeType.Adapter;	// 适配器
		this.inputDescribe = inputDescribe;
		this.outputDescribe = outputDescribe;
		this.offset = offset;
		this.lenght = lenngth;
	}
	
	/**
	 * 直接强转对应类型的返回
	 * @param input
	 * @return
	 */
	public Complex[] calComplexs(Object input){
		return (Complex[])calculate(input);
	}
}
