package cn.ynu.android.chddt.model;

import cn.ynu.android.chddt.R;
import cn.ynu.android.chddt.common.Utils;


/**
 * 医生类
 * @author RobinTang
 *	@time 2012-10-06
 */
public class Doctor {
	private String name="";	// 姓名
	private String address="";	// 地址
	private String email="";	// E-Mail地址
	public Doctor() {
		super();
	}
	public Doctor(String name, String address, String email) {
		super();
		this.name = name;
		this.address = address;
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
		sb.append(String.format("%s: %s", S(R.string.name), name));
		
		sb.append("\r\n");
		sb.append(prefix);
		sb.append(String.format("%s: %s", S(R.string.address), address));
		
		sb.append("\r\n");
		sb.append(prefix);
		sb.append(String.format("%s: %s", S(R.string.email), email));
		
		return sb.toString();
	}
	@Override
	public String toString() {
		return "Doctor [name=" + name + ", address=" + address + ", email="
				+ email + "]";
	}
}
