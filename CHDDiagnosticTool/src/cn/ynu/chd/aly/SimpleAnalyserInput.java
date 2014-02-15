package cn.ynu.chd.aly;

/**
 * 分析器输入
 * 
 * @author RobinTang
 * 
 */
public class SimpleAnalyserInput {
	public double[] aortic = null;
	public double[] mitral = null;
	public double[] pulmonary = null;
	public double[] tricuspid = null;

	/**
	 * 分析器输入
	 * 
	 * @param aortic
	 *            主动脉瓣
	 * @param mitral
	 *            二尖瓣
	 * @param pulmonary
	 *            肺动脉瓣
	 * @param tricuspid
	 *            三尖瓣
	 */
	public SimpleAnalyserInput(double[] aortic, double[] mitral, double[] pulmonary, double[] tricuspid) {
		super();
		this.aortic = aortic;
		this.mitral = mitral;
		this.pulmonary = pulmonary;
		this.tricuspid = tricuspid;
	}
}
