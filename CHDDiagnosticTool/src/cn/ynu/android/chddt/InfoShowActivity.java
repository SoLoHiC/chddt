package cn.ynu.android.chddt;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import cn.ynu.android.chddt.common.Strings;

public class InfoShowActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String inf = getIntent().getExtras().getString(Strings.INFO).replace("\r\n", "\n");
		Log.i("info", inf);
		setContentView(R.layout.activity_info);
		
		TextView view = (TextView) findViewById(R.id.tv_info);
		view.setText(inf);
	}
}
