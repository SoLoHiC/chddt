package cn.ynu.chd.aly;

import com.sin.chd.base.pipe.ArgumentDescribe;
import com.sin.chd.base.pipe.BasePipe;
import com.sin.chd.base.pipe.PipeType;

/**
 * 分析器（诊断器）基类，其他诊断器均继承自该类
 * 
 * @author RobinTang
 * 
 */
public class BaseAnalyser extends BasePipe{
	public BaseAnalyser(String name, String explain, ArgumentDescribe inputDescribe, ArgumentDescribe outputDescribe) {
		super();
		this.name = name;
		this.explain = explain;
		this.type = PipeType.Analyser; // 分析器
		this.inputDescribe = inputDescribe;
		this.outputDescribe = outputDescribe;
	}
}
