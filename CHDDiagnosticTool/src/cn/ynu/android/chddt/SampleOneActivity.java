package cn.ynu.android.chddt;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import cn.ynu.android.chddt.common.Constants;
import cn.ynu.android.chddt.common.Strings;
import cn.ynu.android.chddt.common.Utils;

import com.sin.android.control.WaveView;
import com.sin.android.media.MICRecordListener;
import com.sin.android.media.MICRecorder;
import com.sin.java.media.WaveFileWriter;
import com.sin.java.utils.DataUtils;

public class SampleOneActivity extends Activity implements OnClickListener {

	private static final String tag = "SampleOneActivity";

	// 本次采样对应的位置ID(即SampleActivity中的CheckBox的ID)
	private int positionid;

	// 波形缓冲区
	private int[] wavebuf = null;

	// 波形控件
	private WaveView waveView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sampleone);

		// 获取采集的位置
		Intent intent = getIntent();
		positionid = intent.getIntExtra(Strings.POSITION_ID, 0);
		String title = getResources().getString(R.string.sample) + ":" + Utils.getPositonByCheckBoxID(this, positionid);
		this.setTitle(title);

		// 初始化按钮
		initButton(R.id.btn_record);
		initButton(R.id.btn_back);
		initButton(R.id.btn_ok);
		initButton(R.id.btn_stop);

		// 初始化缓冲区
		// 这个过程可能占用很长的一部分时间来分配内存
		// 如果效果实在不理想的话
		// 可以尝试使用静态变量的方式，这样只是在第一次分配的时候消耗时间
		wavebuf = new int[Constants.SAMPLEBUFLEN]; //

		// 初始化波形显示控件
		waveView = (WaveView) findViewById(R.id.wv_sample);
		waveView.setValueRange(Constants.WAVEMIN16BIT, Constants.WAVEMAX16BIT); // 设置波形幅度范围
		waveView.setWaveData(wavebuf); // 设置波形数据
		waveView.setCount(Constants.SAMPLELEN);
	}

	private void initButton(int id) {
		Button button = (Button) findViewById(id);
		button.setOnClickListener(this);
	}

	// 按钮事件监听
	@Override
	public void onClick(View v) {
		Button button = (Button) v;
		Log.i(tag, "Click: " + button.getText());
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.btn_ok:
			// 采集完毕，把采集到的数据返回去
			// 如果数据太大的话可以考虑使用wav文件的方式返回
			// 因为Activity之间传递的数据大小是有限制的
			stop();
			showDialog(Constants.DIALOG_WAIT); // 因为把数据回传的过程很慢，因此先弹出等待对话框

			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					String cachefile = Utils.getCacheFileName() + ".dat";
					if (cachefile == null || cachefile.length() == 0) {
						Toast.makeText(SampleOneActivity.this, R.string.createcachefilefail, Toast.LENGTH_LONG).show();
						return;
					}

					int[] rtdata = new int[Constants.SAMPLELEN]; // 用于返回的数据
					int start = Math.min(Constants.SAMPLEBUFLEN - Constants.SAMPLELEN, waveView.getStartX());
					System.arraycopy(wavebuf, start, rtdata, 0, Constants.SAMPLELEN); // 回传数据
					if (!DataUtils.writeIntArray(rtdata, cachefile)) {
						Toast.makeText(SampleOneActivity.this, R.string.returnfail, Toast.LENGTH_LONG).show();
						return;
					}
					Intent intent = new Intent();
					Bundle bundle = new Bundle();

					bundle.putInt(Strings.POSITION_ID, positionid);
					bundle.putInt(Strings.DATA_LENGTH, rtdata.length);
					bundle.putString(Strings.CACHE_FILE, cachefile);

					intent.putExtras(bundle);
					setResult(RESULT_OK, intent);
					dismissDialog(Constants.DIALOG_WAIT);
					finish();
				}
			});
			thread.start();
			break;
		case R.id.btn_record:
			record();
			break;
		case R.id.btn_stop:
			stop();
			break;
		default:
			break;
		}
	}

	// 创建对话框
	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		Dialog dialog = null;
		switch (id) {
		case Constants.DIALOG_WAIT:
			ProgressDialog dlg = new ProgressDialog(this);
			dlg.setMessage(getResources().getString(R.string.waitting));
			dialog = dlg;
			break;
		default:
			dialog = super.onCreateDialog(id, args);
			break;
		}

		return dialog;
	}

	// 下面的方法主要是和采集相关的
	private MICRecorder recorder = null;

	private MICRecordListener listener = new MICRecordListener() {

		@Override
		public void recordedOneFrame(int len, int nowindex) {
			int ct = waveView.getCount(); // 获取waveView控件一屏能够显示的点数
			int w = (int) (ct * Constants.WAVEWEIGHT); // 计算出一个屏幕上波形的宽度
			int start = Math.max(0, nowindex - w);
			waveView.setStartX(start); // 设置waveView的显示起始位置
			waveView.postInvalidate(); // 通知waveView更新显示
		}

		@Override
		public void bufferFull() {
			stop();
		}
	};

	// 停止采集
	private void stop() {
		if (recorder != null) {
			recorder.stop();
		}
		recorder = null;
	}

	// 开始采集
	private void record() {
		if (recorder == null) {
			// 创建MIC记录器
			recorder = new MICRecorder(Constants.ORG_SAMPLEFREQUENCE, Constants.SAMPLEFREQUENCE, wavebuf);
			recorder.setListener(listener); // 设置监听器
			recorder.record(); // 开始记录
		}
	}
}
