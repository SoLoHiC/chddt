package cn.ynu.java.dm.transformer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

import cn.ynu.java.dm.model.AttributeArray;
import cn.ynu.java.dm.model.AttributeDictionary;

import com.sin.chd.base.pipe.ArgumentDescribe;

/**
 * 文件转属性列，传入管道值为文件路径
 * 
 * @author RobinTang
 * 
 */
public class FileToAttributeArray extends BaseTransformer {

	/**
	 * 文件转属性列，传入管道值为文件路径
	 */
	public FileToAttributeArray() {
		super("文件转属性列", "", ArgumentDescribe.string("文件路径"), ArgumentDescribe.objectList("属性数组"));
	}

	@Override
	public Object calculate(Object input) {
		try {
			String datafile = (String) input;
			Reader reader = new FileReader(datafile);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String rl = null;
			AttributeArray ret = new AttributeArray();
			while ((rl = bufferedReader.readLine()) != null) {
				rl = rl.replace("\t", " ").trim().replace("  ", " ").replace("  ", " ").replace("  ", " ").replace("  ", " ").replace("  ", " ").replace("  ", " ");
				if(rl.length()==0 || rl.charAt(0)=='#')
					continue;
				String[] kvs = rl.split(" ");
				AttributeDictionary ad = new AttributeDictionary("");
				for (String kl : kvs) {
					String[] kva = getKv(kl);
					if (kva[0].equals(AttributeDictionary.classifyID))
						ad.setClassify(kva[1]);
					else
						ad.setAttribute(kva[0], kva[1]);
				}
				ret.add(ad);
			}
			bufferedReader.close();
			reader.close();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String[] getKv(String kv) {
		String[] kva = kv.split("=");
		kva[0] = kva[0].trim();
		kva[1] = kva[1].trim();
		return kva;
	}
}
