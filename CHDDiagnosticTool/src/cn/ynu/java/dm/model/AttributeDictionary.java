package cn.ynu.java.dm.model;

import java.util.Iterator;
import java.util.Set;

/**
 * 属性字典
 * 
 * @author RobinTang
 * 
 */
public class AttributeDictionary extends StringDictionary {
	private static final long serialVersionUID = 2657886163136642841L;

	public static final String classifyID = "C";

	private String classify;

	/**
	 * 属性字典
	 */
	public AttributeDictionary() {
		this("", null);
	}

	/**
	 * 属性字典
	 * 
	 * @param classify
	 *            该属性字典对应的分类
	 */
	public AttributeDictionary(String classify) {
		this(classify, null);
	}

	/**
	 * 属性字典
	 * 
	 * @param attributes
	 *            初始属性
	 */
	public AttributeDictionary(StringDictionary attributes) {
		this("", attributes);
	}

	/**
	 * 属性字典
	 * 
	 * @param classify
	 *            该属性字典对应的分类
	 * @param attributes
	 *            初始属性
	 * 
	 */
	public AttributeDictionary(String classify, StringDictionary attributes) {
		super();
		if (classify != null)
			setClassify(classify);
		if (attributes != null)
			this.putAll(attributes);
	}

	/**
	 * 获取分类
	 * 
	 * @return 该属性字典对应的分类
	 */
	public String getClassify() {
		return classify;
	}

	/**
	 * 设置该属性字典对应的分类
	 * 
	 * @param classify
	 *            分类名称
	 */
	public void setClassify(String classify) {
		this.classify = classify;
	}

	/**
	 * 设置属性值
	 * 
	 * @param att
	 *            属性
	 * @param val
	 *            值
	 * @return 属性字典本身
	 */
	public AttributeDictionary setAttribute(String att, String val) {
		this.setValue(att, val);
		return this;
	}

	/**
	 * 获取属性值
	 * 
	 * @param att
	 *            属性
	 * @return 该属性对应的值
	 */
	public String getAttribute(String att) {
		return this.getValue(att);
	}

	/**
	 * 获取所有属性名
	 * 
	 * @return 属性名集合
	 */
	public StringSet getAllAttributeName() {
		StringSet ret = new StringSet();
		Set<java.util.Map.Entry<String, String>> sets = this.entrySet();
		Iterator<java.util.Map.Entry<String, String>> it = sets.iterator();
		while (it.hasNext()) {
			ret.add(it.next().getKey());
		}
		return ret;
	}

	/**
	 * 获取所有值集合
	 * 
	 * @return 值集合
	 */
	public StringSet getAllAttributeValue() {
		StringSet ret = new StringSet();
		Set<java.util.Map.Entry<String, String>> sets = this.entrySet();
		Iterator<java.util.Map.Entry<String, String>> it = sets.iterator();
		while (it.hasNext()) {
			ret.add(it.next().getValue());
		}
		return ret;
	}

	@Override
	public String toString() {
		return classify + ":" + super.toString();
	}
}
