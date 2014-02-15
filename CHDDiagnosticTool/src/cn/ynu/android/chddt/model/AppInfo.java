package cn.ynu.android.chddt.model;

import cn.ynu.android.chddt.R;
import cn.ynu.android.chddt.common.Utils;

/**
 * 应用程序信息
 * @author RobinTang
 * @time 2012-10-13
 */
public class AppInfo {
	private String appname;
	private String company;
	private String version;
	private String develop;
	public AppInfo() {
		this.appname = Utils.getString(R.string.app_name);
		this.company = Utils.getString(R.string.app_company);
		this.version = Utils.getString(R.string.app_version);
		this.develop = Utils.getString(R.string.app_develop);
	}
	public String getAppname() {
		return appname;
	}
	public String getCompany() {
		return company;
	}
	public String getVersion() {
		return version;
	}
	public String getDevelop() {
		return develop;
	}

	private String S(int id){
		return Utils.getString(id);
	}
	
	// 获取用于保存的字符串
	public String getSaveString(String prefix){
		if(prefix == null){
			prefix = "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(prefix);
		sb.append(String.format("%s: %s", S(R.string.appname), appname));
		
		sb.append("\r\n");
		sb.append(prefix);
		sb.append(String.format("%s: %s", S(R.string.company), company));
		
		sb.append("\r\n");
		sb.append(prefix);
		sb.append(String.format("%s: %s", S(R.string.version), version));
		
		sb.append("\r\n");
		sb.append(prefix);
		sb.append(String.format("%s: %s", S(R.string.develop), develop));
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return "AppInfo [appname=" + appname + ", company=" + company + ", version=" + version + ", develop=" + develop + "]";
	}
}
