package cn.ynu.chd.aly;

import java.io.Serializable;

import cn.ynu.java.dm.model.AttributeDictionary;
import cn.ynu.java.dm.model.ProbabilityDictionary;

/**
 * 分析器结果
 * 
 * @author RobinTang
 * 
 */
public class SimpleAnalyserResult implements Serializable {
	private static final long serialVersionUID = -5310437642671296500L;
	public ProbabilityDictionary probabilityDictionary;
	public AttributeDictionary attributeDictionary;
	/**
	 * 分析器结果
	 * 
	 * @param probabilityDictionary
	 *            概率字典
	 */
	public SimpleAnalyserResult(ProbabilityDictionary probabilityDictionary, AttributeDictionary attributeDictionary) {
		this.probabilityDictionary = probabilityDictionary;
		this.attributeDictionary = attributeDictionary;
	}

	@Override
	public String toString() {
		return String.format("\r\n  属性%s\r\n  概率分布%s\r\n  可能的分类%s", this.attributeDictionary, this.probabilityDictionary.toString(), this.probabilityDictionary.getSimilarestClassify().toString());
	}
}
