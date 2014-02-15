package cn.ynu.android.chddt.common;

/**
 * 
 * 该类下面放置常量
 * 
 * @author RobinTang
 * @time 2012-10-05
 * 
 */
public class Constants {

	// Request Code, 请求码
	public static final int REQ_SAMPLEONE = 10; // 对一个位置进行采集

	// MIC采集到的声波的幅度范围
	// 用于向WaveView控件设置
	// 以便WaveView在显示的时候自动调整幅度
	public static final int WAVEMAX16BIT = 32767/2; // 最大值
	public static final int WAVEMIN16BIT = -32768/2; // 最小值

	public static final float WAVEWEIGHT = 0.8f; // 采集的时候波形占整个屏幕的比例

	// 采集相关
	public static final int ORG_SAMPLEFREQUENCE = 11025; // 原始采样率
	public static final int SAMPLEFREQUENCE = 5000; // 采样率
	public static final int SAMPLETIMELEN = 60; // 采集时间（s）

	public static final int SAMPLELEN = SAMPLEFREQUENCE * SAMPLETIMELEN; // 采集的长度
	public static final int SAMPLEBUFLEN = SAMPLELEN * 2; // 采集的时候缓冲器的长度，应该比采集的长度长一些

	// 对话框ID值
	public static final int DIALOG_WAIT = 1;
	public static final int DIALOG_OPEN = 2;
	public static final int DIALOG_OPENING = 3;
	public static final int DIALOG_SAVE = 4;
	public static final int DIALOG_SAVING = 5;
	public static final int DIALOG_ANALYSISING = 6;

	// 目录配置
	public static final String DIR_APP = "chddt"; // 主目录
	public static final String DIR_CACHE = "cache"; // 缓存
	public static final String DIR_DATA = "data"; // 记录
	public static final String DIR_LOG = "log"; // 日志

	// 文件相关
	// 后缀
	public static final String SUFFIX_TXT = ".txt";
	public static final String SUFFIX_CHD = ".chd";
	public static final String SUFFIX_PROJECT = ".chd;";
	public static final String SUFFIX_PROJECT_ONLY = "chd;";
	public static final String SUFFIX_AORTIC = "_a.wav";
	public static final String SUFFIX_MITRAL = "_m.wav";
	public static final String SUFFIX_PULMONARY = "_p.wav";
	public static final String SUFFIX_TRICUSPID = "_t.wav";

	// 样式
	public static final String DF_STANDAR = "yyyy-MM-dd HH:mm:ss"; // 标准时间样式
	public static final String DF_DATE = "yyyy-MM-dd"; // 日期样式
	public static final String DF_TIME = "HH:mm:ss"; // 时间样式
	public static final String DF_SHORT = "yyyyMMddHHmmss"; // 短样式
	public static final String DF_SHORTNOS = "yyyyMMddHHmm"; // 短样式

	// 其他
	public static final boolean CLEARCACHEWHENEXIT = true;
	
	// 数据库文件
	public static final String DB_FILENAME = "chd.sqlite";
}
