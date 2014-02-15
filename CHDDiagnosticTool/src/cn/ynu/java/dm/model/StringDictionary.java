package cn.ynu.java.dm.model;

import java.util.HashMap;

/**
 * 字符串字典
 * 
 * @author RobinTang
 * 
 */
public class StringDictionary extends HashMap<String, String> {
	private static final long serialVersionUID = -8294258308860737741L;

	/**
	 * 键拼接
	 * 
	 * @param split
	 *            分割符
	 * @param format
	 *            格式
	 * @return 拼接后的字符串
	 */
	public String keyJoin(String split, String format) {
		StringBuffer sb = new StringBuffer();
		boolean notfirst = false;
		for (java.util.Map.Entry<String, String> it : this.entrySet()) {
			if (notfirst)
				sb.append(split);
			sb.append(String.format(format, it.getKey()));
			notfirst = true;
		}
		return sb.toString();
	}

	/**
	 * 值拼接
	 * 
	 * @param split
	 *            分割符
	 * @param format
	 *            格式
	 * @return 拼接后的字符串
	 */
	public String valueJoin(String split, String format) {
		StringBuffer sb = new StringBuffer();
		boolean notfirst = false;
		for (java.util.Map.Entry<String, String> it : this.entrySet()) {
			if (notfirst)
				sb.append(split);
			sb.append(String.format(format, it.getValue()));
			notfirst = true;
		}
		return sb.toString();
	}

	/**
	 * 设置字典值
	 * 
	 * @param key
	 *            键名
	 * @param value
	 *            值
	 * @return 字典本身
	 */
	public StringDictionary setValue(String key, String value) {
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
	public String getValue(String key) {
		return this.get(key);
	}
}
