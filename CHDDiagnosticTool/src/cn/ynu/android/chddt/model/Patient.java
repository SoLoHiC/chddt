package cn.ynu.android.chddt.model;

import java.io.Serializable;

import cn.ynu.android.chddt.R;
import cn.ynu.android.chddt.common.Utils;

/**
 * 病人（当然不一定有病，这是接受检测而已）
 * @author RobinTang
 * @time 2012-10-05
 */
public class Patient implements Serializable{
	private static final long serialVersionUID = -7220952409663102357L;
	
	private String name=""; // 姓名
	private boolean sex=true; // true为男, false为女
	private int age; // 年龄
	private int ageunit; // 年龄单位
	private String bed=""; // 床位
	private String address=""; // 地点
	private String remark=""; // 备注

	public Patient() {
		super();
	}

	public Patient(String name, boolean sex, int age, int ageunit, String bed, String address, String remark) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.ageunit = ageunit;
		this.bed = bed;
		this.address = address;
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getAgeunit() {
		return ageunit;
	}

	public void setAgeunit(int ageunit) {
		this.ageunit = ageunit;
	}

	public String getBed() {
		return bed;
	}

	public void setBed(String bed) {
		this.bed = bed;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
		sb.append(String.format("%s: %s", S(R.string.sex), sex?S(R.string.male):S(R.string.female)));
		
		sb.append("\r\n");
		sb.append(prefix);
		sb.append(String.format("%s: %d%s", S(R.string.age), age, Utils.getStringArray(R.array.ageunits)[ageunit]));
		
		sb.append("\r\n");
		sb.append(prefix);
		sb.append(String.format("%s: %s", S(R.string.bed), bed));
		
		sb.append("\r\n");
		sb.append(prefix);
		sb.append(String.format("%s: %s", S(R.string.address), address));
		
		sb.append("\r\n");
		sb.append(prefix);
		sb.append(String.format("%s: %s", S(R.string.remark), remark.replace("\n", "\n"+prefix+"\t")));
		
		return sb.toString();
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", sex=" + sex + ", age=" + age + ", ageunit=" + ageunit + ", bed=" + bed + ", address=" + address + ", remark=" + remark + "]";
	}

}
