package cn.ynu.android.chddt.model;

import cn.ynu.android.chddt.R;
import cn.ynu.android.chddt.common.Utils;

/**
 * 采样信息
 * @author RobinTang
 * ＠time 2012-10-06
 */
public class SampleInfo {
	private int frequence=0;	// 采集频率
	private String remark="";	// 备注
	private String time="";	// 采集时间
	public SampleInfo() {
		super();
	}
	public SampleInfo(int frequence, String remark, String time) {
		super();
		this.frequence = frequence;
		this.remark = remark;
		this.time = time;
	}
	public int getFrequence() {
		return frequence;
	}
	public void setFrequence(int frequence) {
		this.frequence = frequence;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
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
		sb.append(String.format("%s: %d", S(R.string.frequence), frequence));
		
		sb.append("\r\n");
		sb.append(prefix);
		sb.append(String.format("%s: %s", S(R.string.time), time));
		
		sb.append("\r\n");
		sb.append(prefix);
		sb.append(String.format("%s: %s", S(R.string.remark), remark.replace("\n", "\n"+prefix+"\t")));
		
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return "SampleInfo [frequence=" + frequence + ", remark=" + remark
				+ ", time=" + time + "]";
	}
}
