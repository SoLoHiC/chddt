package cn.ynu.java.dm.model;

import java.util.HashSet;

/**
 * 字符串集合
 * 
 * @author RobinTang
 * 
 */
public class StringSet extends HashSet<String> {
	private static final long serialVersionUID = -471864563801975670L;

	/**
	 * 拼接字符串
	 * 
	 * @param split
	 *            分割符
	 * @param format
	 *            格式
	 * @return 拼接后的字符串
	 */
	public String join(String split, String format) {
		StringBuffer sb = new StringBuffer();
		boolean notfirst = false;
		for (String s : this) {
			if (notfirst)
				sb.append(split);
			sb.append(String.format(format, s));
			notfirst = true;
		}
		return sb.toString();
	}
}
