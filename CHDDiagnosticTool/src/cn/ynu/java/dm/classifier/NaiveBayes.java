package cn.ynu.java.dm.classifier;

import java.util.Set;

import cn.ynu.java.dm.model.AttributeDictionary;
import cn.ynu.java.dm.model.ProbabilityDictionary;
import cn.ynu.java.dm.model.StringSet;

import com.sin.chd.base.pipe.ArgumentDescribe;

/**
 * 朴素贝叶斯
 * 
 * @author RobinTang
 * 
 */
public class NaiveBayes extends BaseClassifier {
	private StringSet classify;
	private ProbabilityDictionary rule;
	private ProbabilityDictionary power;

	/**
	 * 朴素贝叶斯
	 * 
	 * @param classify
	 *            分类集合
	 * @param rule
	 *            规则（概率字典）
	 */
	public NaiveBayes(StringSet classify, ProbabilityDictionary rule) {
		this(classify, rule, null);
	}

	/**
	 * 朴素贝叶斯
	 * 
	 * @param classify
	 *            分类集合
	 * @param rule
	 *            规则（概率字典）
	 * @param power
	 *            属性权值，未指定的为1
	 */
	public NaiveBayes(StringSet classify, ProbabilityDictionary rule, ProbabilityDictionary power) {
		super("朴素贝叶斯", "", ArgumentDescribe.objectDictionary("未知分类的属性字典（分类为空）"), ArgumentDescribe.objectDictionary("返回各个类的可能概率"));
		this.classify = classify;
		this.rule = rule;
		this.power = power;
	}

	@Override
	public Object calculate(Object input) {
		AttributeDictionary ad = (AttributeDictionary) input;
		Set<String> uats = ad.getAllAttributeName();
		ProbabilityDictionary ret = new ProbabilityDictionary();
		for (String cls : classify) {
			double pt = 0.0f;
			for (String at : uats) {
				String k = String.format(ProbabilityDictionary.classifyAttrivuteValueFormat, cls, at, ad.getAttribute(at));
				pt += (rule.getProbability(k) * (power == null ? 1 : power.getProbability(at, 1)));
			}
			ret.setProbability(cls, pt);
		}
		return ret;
	}
}
