package cn.ynu.android.chddt.aly;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.sin.android.database.AssetsDatabaseManager;
import com.sin.chd.base.util.XLog;

import cn.ynu.android.chddt.common.Constants;
import cn.ynu.chd.aly.SimpleAnalyser;
import cn.ynu.chd.aly.SimpleAnalyserReference;
import cn.ynu.java.dm.model.ProbabilityDictionary;
import cn.ynu.java.dm.model.StringDictionary;

/**
 * 分析器工具
 * 
 * @author RobinTang
 * 
 */
public class AnalyserUtils {
	static private AssetsDatabaseManager databaseManager = null;

	// static private Context context = null;

	/**
	 * 初始化工具
	 * 
	 * @param context
	 *            上下文
	 */
	static public void initUtil(Context context) {
		// AnalyserUtil.context = context;
		AssetsDatabaseManager.initManager(context);
		databaseManager = AssetsDatabaseManager.getManager();
	}

	static public SimpleAnalyser getSimpleAnalyser() {
		// Analysis
		SQLiteDatabase database = databaseManager.getDatabase(Constants.DB_FILENAME);
		SimpleAnalyserReference reference = new SimpleAnalyserReference();
		reference.attributes = DataBaseUtils.getStringArray(database, "attributes");
		reference.classifies = DataBaseUtils.getStringSet(database, "classifies");
		reference.depends = DataBaseUtils.getDoubleArray(database, "depends");
		StringDictionary setting = DataBaseUtils.getStringDictionary(database, "setting");
		reference.signallen = Integer.parseInt(setting.getValue("signallen"));
		reference.powers = null;
		reference.rules = new ProbabilityDictionary(DataBaseUtils.getDoubleDictionary(database, "rules"));
		reference.segments = DataBaseUtils.getIntArray(database, "segments");
		XLog.i("rf:%s", reference);
		return new SimpleAnalyser(reference);
	}
}
