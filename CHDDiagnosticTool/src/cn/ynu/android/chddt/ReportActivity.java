package cn.ynu.android.chddt;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.sin.android.database.AssetsDatabaseManager;

import android.app.Activity;
import android.content.pm.LabeledIntent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.ynu.android.chddt.common.Strings;
import cn.ynu.android.chddt.model.Patient;
import cn.ynu.chd.aly.SimpleAnalyserResult;

/**
 * 报告
 * 
 * @author RobinTang
 * 
 */
public class ReportActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		SimpleAnalyserResult analyserResult = (SimpleAnalyserResult) getIntent().getSerializableExtra(Strings.RESULT);
		long costtime = getIntent().getLongExtra(Strings.COSTTIME, 0);
		Patient patient = (Patient) getIntent().getSerializableExtra(Strings.PATIENT);

		
		Log.i("report", analyserResult.toString());
		Log.i("patient", patient.toString());
		Log.i("costtime", "" + costtime);
		
	}
}
