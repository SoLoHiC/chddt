package cn.ynu.android.chddt.aly;

import cn.ynu.java.dm.model.DoubleDictionary;
import cn.ynu.java.dm.model.StringDictionary;
import cn.ynu.java.dm.model.StringSet;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * SQLiteDB工具
 * 
 * @author RobinTang
 * 
 */
public class DataBaseUtils {
	/**
	 * 获取字符串字典
	 * 
	 * @param db
	 *            数据库
	 * @param tab
	 *            表名
	 * @return 字符串字典
	 */
	static public StringDictionary getStringDictionary(SQLiteDatabase db, String tab) {
		StringDictionary ret = new StringDictionary();
		String sql = String.format("select * from `%s`;", tab);
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			ret.setValue(cursor.getString(1), cursor.getString(2));
		}
		return ret;
	}

	/**
	 * 获取字符串字典
	 * 
	 * @param db
	 *            数据库
	 * @param tab
	 *            表名
	 * @return 字符串字典
	 */
	static public DoubleDictionary getDoubleDictionary(SQLiteDatabase db, String tab) {
		DoubleDictionary ret = new DoubleDictionary();
		String sql = String.format("select * from `%s`;", tab);
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			ret.setValue(cursor.getString(1), cursor.getDouble(2));
		}
		return ret;
	}

	/**
	 * 获取字符串集合
	 * 
	 * @param db
	 *            数据库
	 * @param tab
	 *            表名
	 * @return 字符串字典集合
	 */
	static public StringSet getStringSet(SQLiteDatabase db, String tab) {
		StringSet ret = new StringSet();
		String sql = String.format("select * from `%s`;", tab);
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			ret.add(cursor.getString(1));
		}
		return ret;
	}

	/**
	 * 获取字符串数组
	 * 
	 * @param db
	 *            数据库
	 * @param tab
	 *            表名
	 * @return 字符串数组
	 */
	static public String[] getStringArray(SQLiteDatabase db, String tab) {
		String sql = String.format("select * from `%s`;", tab);
		Cursor cursor = db.rawQuery(sql, null);
		String[] ret = new String[cursor.getCount()];
		int i = 0;
		while (cursor.moveToNext()) {
			ret[i] = cursor.getString(1);
			++i;
		}
		return ret;
	}

	/**
	 * 获取double数组
	 * 
	 * @param db
	 *            数据库
	 * @param tab
	 *            表名
	 * @return double数组
	 */
	static public double[] getDoubleArray(SQLiteDatabase db, String tab) {
		String sql = String.format("select * from `%s`;", tab);
		Cursor cursor = db.rawQuery(sql, null);
		double[] ret = new double[cursor.getCount()];
		int i = 0;
		while (cursor.moveToNext()) {
			ret[i] = cursor.getDouble(1);
			++i;
		}
		return ret;
	}

	/**
	 * 获取int数组
	 * 
	 * @param db
	 *            数据库
	 * @param tab
	 *            表名
	 * @return int数组
	 */
	static public int[] getIntArray(SQLiteDatabase db, String tab) {
		String sql = String.format("select * from `%s`;", tab);
		Cursor cursor = db.rawQuery(sql, null);
		int[] ret = new int[cursor.getCount()];
		int i = 0;
		while (cursor.moveToNext()) {
			ret[i] = cursor.getInt(1);
			++i;
		}
		return ret;
	}
}
