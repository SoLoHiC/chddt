package cn.ynu.java.dm.model;

import java.util.HashMap;

/**
 * 浮点字典
 * 
 * @author RobinTang
 * 
 */
public class DoubleDictionary extends HashMap<String, Double> {
	private static final long serialVersionUID = 2657886163136642841L;

	/**
	 * 设置字典值
	 * 
	 * @param key
	 *            键名
	 * @param value
	 *            值
	 * @return 字典本身
	 */
	public DoubleDictionary setValue(String key, double value) {
		this.put(key, value);
		return this;
	}
	
	/**
	 * 获取值
	 * 
	 * @param key
	 *            键名
	 * @return 值
	 */
	public double getValue(String key) {
		return this.get(key);
	}
}
