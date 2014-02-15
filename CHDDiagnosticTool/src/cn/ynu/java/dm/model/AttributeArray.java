package cn.ynu.java.dm.model;

import java.util.ArrayList;

/**
 * 属性列表
 * 
 * @author RobinTang
 * 
 */
public class AttributeArray extends ArrayList<AttributeDictionary> {
	private static final long serialVersionUID = 4835248768900253891L;

	/**
	 * 获取过滤之后的记录
	 * 
	 * @param classify
	 *            分类过滤
	 * @param attribute
	 *            属性过滤
	 * @param value
	 *            属性值过滤
	 * @return 记录数目
	 */
	public AttributeArray filterArray(String classify, String attribute, String value) {
		if (classify != null && classify.length() == 0)
			classify = null;
		if (attribute != null && attribute.length() == 0)
			attribute = null;
		if (value != null && value.length() == 0)
			value = null;

		if (classify == null && attribute == null && value == null)
			return this;
		AttributeArray ret = new AttributeArray();
		for (AttributeDictionary ad : this) {
			String v = ad.getAttribute(attribute);
			if ((classify == null || ad.getClassify().equals(classify)) && (attribute == null || ((value != null) && v != null && v.equals(value) || (value == null && v != null)))) {
				ret.add(ad);
			}
		}
		return ret;
	}

	/**
	 * 获取指定的记录数目
	 * 
	 * @param classify
	 *            分类过滤
	 * @param attribute
	 *            属性过滤
	 * @param value
	 *            属性值过滤
	 * @return 记录数目
	 */
	public int getCount(String classify, String attribute, String value) {
		return filterArray(classify, attribute, value).size();
	}

	/**
	 * 获取属性集合
	 * 
	 * @param classify
	 *            分类过滤
	 * @param attribute
	 *            属性过滤
	 * @param value
	 *            属性值过滤
	 * @return 属性集合
	 */
	public StringSet getAttributeSet(String classify, String attribute, String value) {
		AttributeArray fed = filterArray(classify, attribute, value);
		StringSet ret = new StringSet();
		for (AttributeDictionary ad : fed) {
			ret.addAll(ad.getAllAttributeName());
		}
		return ret;
	}

	/**
	 * 获取分类集合
	 * 
	 * @return 分类集合
	 */
	public StringSet getClassifySet() {
		StringSet ret = new StringSet();
		for (AttributeDictionary ad : this) {
			ret.add(ad.getClassify());
		}
		return ret;
	}

	/**
	 * 获取值集合
	 * 
	 * @return 值集合
	 */
	public StringSet getValueSet() {
		StringSet ret = new StringSet();
		for (AttributeDictionary ad : this) {
			ret.addAll(ad.getAllAttributeValue());
		}
		return ret;
	}
}
