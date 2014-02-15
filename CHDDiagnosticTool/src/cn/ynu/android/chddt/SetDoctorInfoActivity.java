package cn.ynu.android.chddt;

import cn.ynu.android.chddt.common.Strings;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SetDoctorInfoActivity extends Activity {

	private TextView tv_name;
	private TextView tv_address;
	private TextView tv_email;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setdoctorinfo);
		
		tv_name =  (TextView) findViewById(R.id.tv_name);
		tv_address =  (TextView) findViewById(R.id.tv_address);
		tv_email =  (TextView) findViewById(R.id.tv_email);
		
		SharedPreferences sp = getSharedPreferences(Strings.DOCTORINFO, MODE_PRIVATE);
		tv_name.setText(sp.getString(Strings.NAME, ""));
		tv_address.setText(sp.getString(Strings.ADDRESS, ""));
		tv_email.setText(sp.getString(Strings.EMAIL, ""));


		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		findViewById(R.id.btn_ok).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 保存设置
				Editor edit = getSharedPreferences(Strings.DOCTORINFO, MODE_PRIVATE).edit();
				edit.putString(Strings.NAME, tv_name.getText().toString());
				edit.putString(Strings.ADDRESS, tv_address.getText().toString());
				edit.putString(Strings.EMAIL, tv_email.getText().toString());
				edit.commit();
				finish();
			}
		});
	}
}

