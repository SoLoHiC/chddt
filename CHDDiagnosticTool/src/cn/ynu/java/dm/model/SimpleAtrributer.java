package cn.ynu.java.dm.model;

/**
 * 简单属性器
 * 
 * @author RobinTang
 * 
 */
public class SimpleAtrributer implements DoubleAttributeMap {
	private double[] depends;
	private String[] attributes;

	/**
	 * 简单属性器，根据依据条件返回属性
	 * 例如条件依。据的值为:[1,3,6,10],属性为[A，B，C，D]，那么有以下值和属性的对应关系:0=>A，1=
	 * >A，1.3=>B，2=>B，8=>D，12=>null。如果属性是对整个数值空间进行映射的话，最大值设置成Double.MAX_VALUE。
	 * 
	 * 
	 * @param depends
	 *            依据条件，需升序排列
	 * @param attributes
	 *            属性值
	 */
	public SimpleAtrributer(double[] depends, String[] attributes) {
		super();
		this.depends = depends;
		this.attributes = attributes;
		if (depends.length == 0 || depends.length != attributes.length) {
			throw new IllegalArgumentException("depends或attributes长度不对!");
		}
	}

	@Override
	public String getAttribute(double value) {
		String atb = null;
		for (int i = (depends.length - 1); i >= 0; --i) {
			if (depends[i] >= value) {
				atb = attributes[i];
			}
		}
		if (atb == null) {
			throw new IllegalArgumentException(String.format("未能找到值%s对应的属性!", value));
		}
		return atb;
	}
}
