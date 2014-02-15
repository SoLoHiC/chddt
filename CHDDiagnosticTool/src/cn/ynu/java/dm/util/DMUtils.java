package cn.ynu.java.dm.util;

import cn.ynu.java.dm.classifier.NaiveBayes;
import cn.ynu.java.dm.model.AttributeArray;
import cn.ynu.java.dm.model.AttributeDictionary;
import cn.ynu.java.dm.model.ProbabilityDictionary;
import cn.ynu.java.dm.model.StringSet;
import cn.ynu.java.dm.transformer.AttributeArrayToRule;

import com.sin.chd.base.pipe.BasePipe;
import com.sin.chd.base.util.XLog;

/**
 * DataMining工具
 * 
 * @author RobinTang
 * 
 */
public class DMUtils {

	/**
	 * 检验属性列
	 * 
	 * @param ats
	 */
	public static double checkAttributeArray(AttributeArray ats) {
		ProbabilityDictionary rule = (ProbabilityDictionary) BasePipe.pipesCal(ats, new AttributeArrayToRule());
		StringSet classify = ats.getClassifySet();
//		XLog.i(ats);
//		XLog.i("rule:%s", rule);
		NaiveBayes naiveBayes = new NaiveBayes(classify, rule);
		int errcount = 0;
		int ix = 0;
		for (AttributeDictionary ad : ats) {
			ProbabilityDictionary pd = (ProbabilityDictionary) BasePipe.pipesCal(ad, naiveBayes);
//			XLog.i(ad);
			StringSet cls = pd.getSimilarestClassify();
			if (cls.size() != 1 || !cls.contains(ad.getClassify())) {
				XLog.e("Index:%d Probability:%s Classify:%s Correct:%s", ix, pd, cls, ad.getClassify());
				++errcount;
			} else {
				XLog.i("Index:%d Probability:%s Classify:%s Correct:%s", ix, pd, cls, ad.getClassify());
			}
			++ix;
		}
		double errorProbability = (double) errcount * 100 / (double) ats.size();
		if (errcount > 0)
			XLog.e("Error-Probability:%.2f%%  %d/%d", errorProbability, errcount, ats.size());
		else
			XLog.i("Error-Probability:%.2f%%  %d/%d", errorProbability, errcount, ats.size());
		XLog.i("orl:%s", rule);
		return errorProbability;
	}
}
