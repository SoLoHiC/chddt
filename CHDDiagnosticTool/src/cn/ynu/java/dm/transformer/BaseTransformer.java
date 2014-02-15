package cn.ynu.java.dm.transformer;

import com.sin.chd.base.pipe.ArgumentDescribe;
import com.sin.chd.base.pipe.BasePipe;
import com.sin.chd.base.pipe.PipeType;

/**
 * 变换器基类，所有的变换器均继承自该类。变换器和适配器功能类似，只是变换器在DM中使用
 * 
 * @author RobinTang
 * 
 */
public class BaseTransformer extends BasePipe {
	public BaseTransformer(String name, String explain, ArgumentDescribe inputDescribe, ArgumentDescribe outputDescribe) {
		super();
		this.name = name;
		this.explain = explain;
		this.type = PipeType.Transformer; // 变换器
		this.inputDescribe = inputDescribe;
		this.outputDescribe = outputDescribe;
	}
}
