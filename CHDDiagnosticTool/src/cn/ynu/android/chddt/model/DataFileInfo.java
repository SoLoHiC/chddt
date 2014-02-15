package cn.ynu.android.chddt.model;

import cn.ynu.android.chddt.R;
import cn.ynu.android.chddt.common.Utils;

/**
 * 数据文件信息
 * @author RobinTang
 * @time 2012-10-13
 */
public class DataFileInfo {
    private String aorticfile="";
    private String mitralfile="";
    private String pulmonaryfile="";
    private String tricuspidfile="";
    
	public DataFileInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DataFileInfo(String aorticfile, String mitralfile, String pulmonaryfile, String tricuspidfile) {
		super();
		this.aorticfile = aorticfile;
		this.mitralfile = mitralfile;
		this.pulmonaryfile = pulmonaryfile;
		this.tricuspidfile = tricuspidfile;
	}

	public String getAorticfile() {
		return aorticfile;
	}
	public void setAorticfile(String aorticfile) {
		this.aorticfile = aorticfile;
	}
	public String getMitralfile() {
		return mitralfile;
	}
	public void setMitralfile(String mitralfile) {
		this.mitralfile = mitralfile;
	}
	public String getPulmonaryfile() {
		return pulmonaryfile;
	}
	public void setPulmonaryfile(String pulmonaryfile) {
		this.pulmonaryfile = pulmonaryfile;
	}
	public String getTricuspidfile() {
		return tricuspidfile;
	}
	public void setTricuspidfile(String tricuspidfile) {
		this.tricuspidfile = tricuspidfile;
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
		sb.append(String.format("%s: %s", S(R.string.aorticfile), aorticfile));
		
		sb.append("\r\n");
		sb.append(prefix);
		sb.append(String.format("%s: %s", S(R.string.mitralfile), mitralfile));
		
		sb.append("\r\n");
		sb.append(prefix);
		sb.append(String.format("%s: %s", S(R.string.pulmonaryfile), pulmonaryfile));
		
		sb.append("\r\n");
		sb.append(prefix);
		sb.append(String.format("%s: %s", S(R.string.tricuspidfile), tricuspidfile));
		return sb.toString();
	}
	@Override
	public String toString() {
		return "DataFileInfo [aorticfile=" + aorticfile + ", mitralfile=" + mitralfile + ", pulmonaryfile=" + pulmonaryfile + ", tricuspidfile=" + tricuspidfile + "]";
	}
}
