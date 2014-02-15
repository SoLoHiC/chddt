package cn.ynu.java.dm.model;

/**
 * 线性属性器
 * 
 * @author RobinTang
 * 
 */
public class LinearAtrributer implements DoubleAttributeMap {
	private double expand;

	/**
	 * 线性属性器
	 * 
	 * @param expand
	 *            值乘以expand取整作为属性
	 */
	public LinearAtrributer(double expand) {
		super();
		this.expand = expand;
	}

	@Override
	public String getAttribute(double value) {
		return String.format("a%d", (int)(value*expand));
	}
}
