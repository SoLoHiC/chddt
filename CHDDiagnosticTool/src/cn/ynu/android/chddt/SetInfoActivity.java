package cn.ynu.android.chddt;

import com.sin.android.control.WaveView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import cn.ynu.android.chddt.common.Constants;
import cn.ynu.android.chddt.common.Strings;
import cn.ynu.android.chddt.common.Utils;
import cn.ynu.android.chddt.model.Patient;

public class SetInfoActivity extends Activity {

    private static final String tag = "InfosetActivity";
    private EditText et_name;
    private EditText et_age;
    private EditText et_bed;
    private EditText et_address;
    private EditText et_remark;
    private Spinner sp_sex;
    private Spinner sp_ageunit;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setinfo);
        
        // 初始化一下工具类，以后需要用到它
        Utils.setContext(this);
        
        // 初始化控件
        initControl();
        initButton();

        // 设置下拉选项的数据
		setSpinnerData(R.id.sp_sex, R.array.sexs);
		setSpinnerData(R.id.sp_ageunit, R.array.ageunits);
    }
	
	private void initControl(){
		et_name = (EditText) findViewById(R.id.et_name);
		et_age = (EditText) findViewById(R.id.et_age);
		et_bed = (EditText) findViewById(R.id.et_bed);
		et_address = (EditText) findViewById(R.id.et_address);
		et_remark = (EditText) findViewById(R.id.et_remark);
		
		sp_sex = (Spinner) findViewById(R.id.sp_sex);
		sp_ageunit= (Spinner) findViewById(R.id.sp_ageunit);
	}
    
    // 设置按钮的单击监听
    private void initButton(){
        findViewById(R.id.btn_ok).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 验证输入
				int age = 0;
				try{
					age = Integer.parseInt(et_age.getText().toString());
				}
				catch(Exception e){
					Toast.makeText(SetInfoActivity.this, R.string.errorage, Toast.LENGTH_SHORT).show();
					et_age.requestFocus();
					return;
				}
				
				// 创建一个病人对象
				Patient patient = new Patient();
				patient.setName(et_name.getText().toString());
				patient.setAge(age);
				patient.setBed(et_bed.getText().toString());
				patient.setAddress(et_address.getText().toString());
				patient.setRemark(et_remark.getText().toString());
				
				patient.setAgeunit(sp_ageunit.getSelectedItemPosition());
				patient.setSex(sp_sex.getSelectedItemPosition()==0);	// 性别选项的第一个为男
				
				
				Intent intent = new Intent(getApplication(), SampleActivity.class);
				intent.putExtra(Strings.PATIENT, patient);
				startActivity(intent);
			}
		});
        findViewById(R.id.btn_exit).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
    }
    
    // 通过Spinner的ID和字符串数组的ID来设置Spinner的数据
	private void setSpinnerData(int spid, int straryid){
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		String[] strs = getResources().getStringArray(straryid);
		for(int i=0; i<strs.length; ++i){
			adapter.add(strs[i]);
		}
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		((Spinner)findViewById(spid)).setAdapter(adapter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(Constants.CLEARCACHEWHENEXIT)
			Utils.clearCache();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.setinfo_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_setdoctorinfo:
			Intent intent = new Intent(this, SetDoctorInfoActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		return true;
	}
}
