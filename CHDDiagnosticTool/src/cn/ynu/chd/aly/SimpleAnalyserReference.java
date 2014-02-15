package cn.ynu.chd.aly;

import java.util.Arrays;

import cn.ynu.java.dm.model.ProbabilityDictionary;
import cn.ynu.java.dm.model.StringSet;

/**
 * 简单分析器依据，贝叶斯依据
 * 
 * @author RobinTang
 * 
 */
public class SimpleAnalyserReference {
	public StringSet classifies;
	public ProbabilityDictionary rules;
	public ProbabilityDictionary powers;
	
	public int signallen = 1024;
	public int[] segments = null;
	public double[] depends = null;
	public String[] attributes = null;
	
	@Override
	public String toString() {
		return "SimpleAnalyserReference [classifies=" + classifies + ", rules=" + rules + ", powers=" + powers + ", signallen=" + signallen + ", segments=" + Arrays.toString(segments) + ", depends=" + Arrays.toString(depends) + ", attributes=" + Arrays.toString(attributes) + "]";
	}
}
