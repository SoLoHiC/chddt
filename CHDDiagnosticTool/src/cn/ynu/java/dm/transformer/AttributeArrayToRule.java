package cn.ynu.java.dm.transformer;

import java.util.Set;

import cn.ynu.java.dm.model.AttributeArray;
import cn.ynu.java.dm.model.ProbabilityDictionary;

import com.sin.chd.base.pipe.ArgumentDescribe;

/**
 * 属性数组转规则
 * 
 * @author RobinTang
 * 
 */
public class AttributeArrayToRule extends BaseTransformer {

	/**
	 * 属性数组转规则
	 */
	public AttributeArrayToRule() {
		super("属性数组转规则", "", ArgumentDescribe.objectList("属性数组"), ArgumentDescribe.objectDictionary("概率字典"));
	}

	@Override
	public Object calculate(Object input) {
		AttributeArray dep = (AttributeArray) input;
		Set<String> ats = dep.getAttributeSet(null, null, null);
		Set<String> clss = dep.getClassifySet();
		ProbabilityDictionary ret = new ProbabilityDictionary();
		for (String at : ats) {
			for (String cls : clss) {
				AttributeArray aa = dep.filterArray(cls, at, null);
				Set<String> vs = aa.getValueSet();
				for (String v : vs) {
					int ct = aa.getCount(cls, at, v);
					int al = dep.getCount(cls, null, null);
					double pt = (double) ct / (double) al;
					String k = String.format(ProbabilityDictionary.classifyAttrivuteValueFormat, cls, at, v);
					ret.setProbability(k, pt);
				}
			}
		}
		return ret;
	}
}
