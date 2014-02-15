package cn.ynu.chd.aly;

import cn.ynu.java.dm.classifier.NaiveBayes;
import cn.ynu.java.dm.model.AttributeDictionary;
import cn.ynu.java.dm.model.DoubleAttributeMap;
import cn.ynu.java.dm.model.DoubleDictionary;
import cn.ynu.java.dm.model.ProbabilityDictionary;
import cn.ynu.java.dm.model.SimpleAtrributer;
import cn.ynu.java.dm.model.StringDictionary;
import cn.ynu.java.dm.transformer.DoubleDictionaryToStringDicrionary;
import cn.ynu.java.dsp.ComplexPart;
import cn.ynu.java.dsp.adapter.ComplexsToDoubles;
import cn.ynu.java.dsp.adapter.DoublesToComplexs;
import cn.ynu.java.dsp.filter.Energy;
import cn.ynu.java.dsp.filter.FFT;
import cn.ynu.java.dsp.filter.Preprocessor;

import com.sin.chd.base.pipe.ArgumentDescribe;
import com.sin.chd.base.pipe.BasePipe;

/**
 * 简单分析器，香农能量属性+贝叶斯分类
 * 
 * @author RobinTang
 * 
 */
public class SimpleAnalyser extends BaseAnalyser {
	private SimpleAnalyserReference reference = null;

	/**
	 * 简单分析器
	 * 
	 * @param reference
	 *            分析依据
	 */
	public SimpleAnalyser(SimpleAnalyserReference reference) {
		super("简单分析器", "", ArgumentDescribe.object("分析器输入"), ArgumentDescribe.object("返回分析结果"));
		this.reference = reference;
	}

	@Override
	public Object calculate(Object input) {
		// 下面完成特真的生成
		// 输入
		SimpleAnalyserInput ainput = (SimpleAnalyserInput) input;
		// 属性
		AttributeDictionary ad = new AttributeDictionary();
		// 主动脉特征
		ad.putAll(this.getAttributes(ainput.aortic, "s_1_"));
		// .. 省略另外三个心音特真..

		// 下面是数据挖掘方面的
		// 贝叶斯分析器
		NaiveBayes naiveBayes = new NaiveBayes(this.reference.classifies, this.reference.rules, this.reference.powers);
		// 贝叶斯结果
		ProbabilityDictionary probabilityDictionary = (ProbabilityDictionary) BasePipe.pipesCal(ad, naiveBayes);
		SimpleAnalyserResult result = new SimpleAnalyserResult(probabilityDictionary, ad);
		return result;
	}

	public StringDictionary getAttributes(double[] input, String prefix) {
		// int[] dt = new WaveFileReader(file).getData()[0];
		double[] pcg = (double[]) BasePipe.pipesCal(input, new Preprocessor());
		double[] fft = (double[]) BasePipe.pipesCal(pcg, new DoublesToComplexs(), new FFT(), new ComplexsToDoubles(ComplexPart.Model, 0, this.reference.signallen / 2));
		double[] en = (double[]) BasePipe.pipesCal(fft, new Energy(this.reference.segments));

		DoubleDictionary dd = new DoubleDictionary();
		for (int i = 0; i < en.length; ++i) {
			dd.setValue(prefix + i, en[i]);
		}
		// DoubleAttributeMap dm = new LinearAtrributer(1.0/1000.0);
		DoubleAttributeMap dm = new SimpleAtrributer(this.reference.depends, this.reference.attributes);
		StringDictionary sd = (StringDictionary) BasePipe.pipesCal(dd, new DoubleDictionaryToStringDicrionary(dm));
		return sd;
	}

	/**
	 * 分析对输入进行分析
	 * 
	 * @param input
	 *            输入
	 * @return 分析结果
	 */
	public SimpleAnalyserResult analyser(SimpleAnalyserInput input) {
		return (SimpleAnalyserResult) calculate(input);
	}
}
