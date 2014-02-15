package cn.ynu.android.chddt.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.ynu.android.chddt.R;
import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;

/**
 * 
 * 工具类
 * 
 * @author RobinTang
 * @time 2012-10-05
 */
public class Utils {
	private static final String tag = "Utils";
	private static Context context = null;
	private static Resources resources = null;

	// 为该静态工具类设置上下文
	// 一般在第一个Activity中调用
	public static void setContext(Context context) {
		Utils.context = context;
		Utils.resources = context.getResources();
	}

	public static Context getContext() {
		return context;
	}

	// 检查环境合法性
	private static boolean checkResources() {
		if (resources == null && context != null) {
			resources = context.getResources();
		}
		if (context == null) {
			Log.e(tag, "未初始化Resources");
		}
		return resources != null;
	}

	// 获取资源字符串
	public static String getString(int id) {
		if (checkResources()) {
			return resources.getString(id);
		} else {
			return null;
		}
	}

	// 获取字符串数组
	public static String[] getStringArray(int id) {
		if (checkResources()) {
			return resources.getStringArray(id);
		} else {
			return null;
		}
	}

	// 通过采集界面中的CheckBox的ID获取该位置名称
	public static String getPositonByCheckBoxID(Context context, int checkboxid) {
		int stringid = 0;
		switch (checkboxid) {
		case R.id.cb_aortic:
			stringid = R.string.aortic;
			break;
		case R.id.cb_mitral:
			stringid = R.string.mitral;
			break;
		case R.id.cb_pulmonary:
			stringid = R.string.pulmonary;
			break;
		case R.id.cb_tricuspid:
			stringid = R.string.tricuspid;
			break;
		default:
			Log.e(tag, "未处理的checkboxid: " + checkboxid);
			return null;
		}
		return context.getResources().getString(stringid);
	}

	// 获取当前时间字符串
	public static String getDateTime(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(new Date());
	}

	// 获取应用程序目录
	public static String getAppPath() {
		String path = Environment.getExternalStorageDirectory().getPath() + "/" + Constants.DIR_APP;
		if (createDir(path))
			return path;
		else
			return null;
	}

	// 获取采集记录的目录
	public static String getDataPath() {
		String path = getAppPath() + "/" + Constants.DIR_DATA;
		if (createDir(path))
			return path;
		else
			return null;
	}

	// 获取缓存目录
	public static String getCachePath() {
		String path = getAppPath() + "/" + Constants.DIR_CACHE;
		createDir(path);
		if (createDir(path))
			return path;
		else
			return null;
	}

	// 获取临时文件名
	public static String getCacheFileName() {
		String res = null;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			String path = getCachePath();
			String file = getDateTime(Constants.DF_SHORT);
			if (path != null) {
				res = path + "/" + file;
			}
		}
		return res;
	}

	// 创建目录
	public static boolean createDir(String dir) {
		File file = new File(dir);
		if (!file.exists()) {
			return file.mkdirs();
		}
		return file.isDirectory();
	}

	// 删除文件
	public static boolean delFile(String filename) {
		File file = new File(filename);
		if (file.exists()) {
			return file.delete();
		}
		return false;
	}

	// 清空缓存
	public static boolean clearCache() {
		Log.i(tag, "清空缓存!");
		String path = getCachePath();
		File file = new File(path);
		if (file.exists() && file.isDirectory()) {
			return deleteFile(path);
		}
		return true;
	}

	// 删除文件夹
	public static boolean deleteFile(String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			String[] files = file.list();
			for (int i = 0; i < files.length; ++i) {
				deleteFile(file.getAbsolutePath() + "/" + files[i]);
			}
		}
		return file.delete();
	}
}
