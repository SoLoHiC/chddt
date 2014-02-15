package cn.ynu.java.dm.model;

import java.util.Iterator;
import java.util.Set;

/**
 * 概率字典
 * 
 * @author RobinTang
 * 
 */
public class ProbabilityDictionary extends DoubleDictionary {
	private static final long serialVersionUID = -4431480514999343575L;
	public static final String classifyAttrivuteValueFormat = "%s/%s(%s)";

	/**
	 * 概率字典
	 */
	public ProbabilityDictionary() {
		super();
	}

	/**
	 * 通过浮点字典创建概率字典
	 * 
	 * @param doubleDictionary
	 *            浮点字典
	 */
	public ProbabilityDictionary(DoubleDictionary doubleDictionary) {
		super();
		this.putAll(doubleDictionary);
	}

	/**
	 * 设置概率值
	 * 
	 * @param pro
	 *            概率
	 * @param val
	 *            值
	 * @return 概率字典本身
	 */
	public ProbabilityDictionary setProbability(String pro, double val) {
		this.put(pro, val);
		return this;
	}

	/**
	 * 获取概率值
	 * 
	 * @param pro
	 *            概率
	 * @return 该概率对应的值，默认值为0
	 */
	public double getProbability(String pro) {
		return this.getProbability(pro, 0.0f);
	}

	/**
	 * 获取概率值
	 * 
	 * @param pro
	 *            概率
	 * @param def
	 *            默认值
	 * @return 该概率对应的值
	 */
	public double getProbability(String pro, double def) {
		if (this.containsKey(pro))
			return this.get(pro);
		else
			return def;
	}

	/**
	 * 获取最可能的分类
	 * 
	 * @return 最可能的分类集合
	 */
	public StringSet getSimilarestClassify() {
		StringSet ret = new StringSet();
		Set<java.util.Map.Entry<String, Double>> sets = this.entrySet();
		Iterator<java.util.Map.Entry<String, Double>> it = sets.iterator();
		double maxpro = 0.0f;
		while (it.hasNext()) {
			java.util.Map.Entry<String, Double> i = it.next();
			if (i.getValue() == maxpro) {
				ret.add(i.getKey());
			} else if (i.getValue() > maxpro) {
				ret.clear();
				ret.add(i.getKey());
				maxpro = i.getValue();
			}
		}
		return ret;
	}
}
