package cn.ynu.java.dm.classifier;

import com.sin.chd.base.pipe.ArgumentDescribe;
import com.sin.chd.base.pipe.BasePipe;
import com.sin.chd.base.pipe.PipeType;

/**
 * 分类器基类，所有的分类器均继承自该类
 * 
 * @author RobinTang
 * 
 */
public class BaseClassifier extends BasePipe {
	public BaseClassifier(String name, String explain, ArgumentDescribe inputDescribe, ArgumentDescribe outputDescribe) {
		super();
		this.name = name;
		this.explain = explain;
		this.type = PipeType.Classifier; // 分类器
		this.inputDescribe = inputDescribe;
		this.outputDescribe = outputDescribe;
	}
}
