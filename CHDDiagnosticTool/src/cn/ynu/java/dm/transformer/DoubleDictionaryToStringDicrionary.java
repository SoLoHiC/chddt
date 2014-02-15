package cn.ynu.java.dm.transformer;

import java.util.Map.Entry;
import java.util.Set;

import cn.ynu.java.dm.model.DoubleAttributeMap;
import cn.ynu.java.dm.model.DoubleDictionary;
import cn.ynu.java.dm.model.StringDictionary;

import com.sin.chd.base.pipe.ArgumentDescribe;

/**
 * 浮点字典转String字典
 * 
 * @author RobinTang
 * 
 */
public class DoubleDictionaryToStringDicrionary extends BaseTransformer {
	private DoubleAttributeMap transforMap = null;

	/**
	 * 浮点字典转String字典
	 * 
	 * @param transforMap
	 *            转换依据
	 */
	public DoubleDictionaryToStringDicrionary(DoubleAttributeMap transforMap) {
		super("属性数组转规则", "", ArgumentDescribe.objectDictionary("Double字典"), ArgumentDescribe.objectDictionary("String字典"));
		this.transforMap = transforMap;
	}

	@Override
	public Object calculate(Object input) {
		DoubleDictionary dd = (DoubleDictionary) input;
		StringDictionary sd = new StringDictionary();
		Set<Entry<String, Double>> itr = dd.entrySet();
		for (Entry<String, Double> it : itr) {
			sd.put(it.getKey(), transforMap.getAttribute(it.getValue()));
		}
		return sd;
	}
}
