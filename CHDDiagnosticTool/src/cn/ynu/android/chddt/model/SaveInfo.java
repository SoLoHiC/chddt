package cn.ynu.android.chddt.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import cn.ynu.android.chddt.R;
import cn.ynu.android.chddt.common.Utils;


/**
 * 保存的信息
 * @author RobinTang
 * @time 2012-10-05
 */

@XStreamAlias("SaveInfo")
public class SaveInfo {
	private Patient patient = new Patient();	// 受检测者
	private Doctor doctor = new Doctor();	// 医生
	private SampleInfo sampleinfo = new SampleInfo();	// 采集信息
	private DataFileInfo datafileinfo = new DataFileInfo();	// 数据文件
	private AppInfo appinfo = new AppInfo();	// 应用信息
	private String time = "";
	public SaveInfo() {
		super();
	}

	public SaveInfo(Patient patient, Doctor doctor, SampleInfo sampleinfo, DataFileInfo datafileinfo, AppInfo appinfo, String time) {
		super();
		this.patient = patient;
		this.doctor = doctor;
		this.sampleinfo = sampleinfo;
		this.datafileinfo = datafileinfo;
		this.appinfo = appinfo;
		this.time = time;
	}

	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public SampleInfo getSampleinfo() {
		return sampleinfo;
	}
	public void setSampleinfo(SampleInfo sampleinfo) {
		this.sampleinfo = sampleinfo;
	}
	public DataFileInfo getDatafileinfo() {
		return datafileinfo;
	}
	public void setDatafileinfo(DataFileInfo datafileinfo) {
		this.datafileinfo = datafileinfo;
	}
	public AppInfo getAppinfo() {
		return appinfo;
	}
	public void setAppinfo(AppInfo appinfo) {
		this.appinfo = appinfo;
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
	public String getSaveString(){
		String prefix = "\t";
		StringBuilder sb = new StringBuilder();
//		sb.append(prefix);
		sb.append(String.format("%s:\r\n%s", S(R.string.patientinfo), patient.getSaveString(prefix)));
		
		sb.append("\r\n");
		sb.append("\r\n");
//		sb.append(prefix);
		sb.append(String.format("%s:\r\n%s", S(R.string.sampleinfo), sampleinfo.getSaveString(prefix)));
		
		sb.append("\r\n");
		sb.append("\r\n");
//		sb.append(prefix);
		sb.append(String.format("%s:\r\n%s", S(R.string.datafileinfo), datafileinfo.getSaveString(prefix)));
		
		sb.append("\r\n");
		sb.append("\r\n");
//		sb.append(prefix);
		sb.append(String.format("%s:\r\n%s", S(R.string.doctorinfo), doctor.getSaveString(prefix)));
		
		sb.append("\r\n");
		sb.append("\r\n");
//		sb.append(prefix);
		sb.append(String.format("%s:\r\n%s", S(R.string.appinfo), appinfo.getSaveString(prefix)));
		
		sb.append("\r\n");
		sb.append("\r\n");
//		sb.append(prefix);
		sb.append(String.format("%s: %s", S(R.string.time), time));
		return sb.toString();
	}
}
