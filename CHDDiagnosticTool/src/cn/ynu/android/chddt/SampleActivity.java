package cn.ynu.android.chddt;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.ynu.android.chddt.aly.AnalyserUtils;
import cn.ynu.android.chddt.common.Constants;
import cn.ynu.android.chddt.common.Strings;
import cn.ynu.android.chddt.common.Utils;
import cn.ynu.android.chddt.model.AppInfo;
import cn.ynu.android.chddt.model.DataFileInfo;
import cn.ynu.android.chddt.model.Doctor;
import cn.ynu.android.chddt.model.Patient;
import cn.ynu.android.chddt.model.SampleInfo;
import cn.ynu.android.chddt.model.SaveInfo;
import cn.ynu.android.chddt.ui.CallbackBundle;
import cn.ynu.android.chddt.ui.OpenFileDialog;
import cn.ynu.chd.aly.SimpleAnalyser;
import cn.ynu.chd.aly.SimpleAnalyserInput;
import cn.ynu.chd.aly.SimpleAnalyserResult;
import cn.ynu.java.dsp.adapter.IntsToDoubles;

import com.sin.android.control.WaveView;
import com.sin.android.utils.Runner;
import com.sin.chd.base.pipe.BasePipe;
import com.sin.chd.base.util.XLog;
import com.sin.java.media.WaveFileReader;
import com.sin.java.media.WaveFileWriter;
import com.sin.java.utils.DataUtils;
import com.thoughtworks.xstream.XStream;

public class SampleActivity extends Activity {
	private static final String tag = "SampleActivity";
	private Patient patient = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 初始化
		AnalyserUtils.initUtil(this);

		setContentView(R.layout.activity_sample);
		this.patient = (Patient) getIntent().getExtras().get(Strings.PATIENT);
		// 设置折叠按钮的折叠事件
		((CheckBox) findViewById(R.id.ck_expand)).setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				LinearLayout ll = (LinearLayout) findViewById(R.id.ll_sample_left);
				if (isChecked) {
					// 折叠的时候
					ll.setVisibility(View.GONE);
				} else {
					// 展开的时候
					ll.setVisibility(View.VISIBLE);
				}
			}
		});

		// 初始化四个波形显示控件
		this.wv_aortic = (WaveView) findViewById(R.id.wv_aortic);
		this.wv_mitral = (WaveView) findViewById(R.id.wv_mitral);
		this.wv_pulmonary = (WaveView) findViewById(R.id.wv_pulmonary);
		this.wv_tricuspid = (WaveView) findViewById(R.id.wv_tricuspid);
		initWaveViewAttribute();

		// 设置左边心音位置选择的四个CheckBox
		initPositionCheckBox(R.id.cb_aortic);
		initPositionCheckBox(R.id.cb_mitral);
		initPositionCheckBox(R.id.cb_pulmonary);
		initPositionCheckBox(R.id.cb_tricuspid);
	}

	private WaveView wv_aortic = null;
	private WaveView wv_mitral = null;
	private WaveView wv_pulmonary = null;
	private WaveView wv_tricuspid = null;

	// 初始化CheckBox
	private void initPositionCheckBox(int id) {
		CheckBox cb = ((CheckBox) findViewById(id));
		registerForContextMenu(cb);
		cb.setOnCheckedChangeListener(positionCheckListener);
		cb.setOnLongClickListener(positionClickListener);
	}

	private void initWaveViewAttribute() {
		// 设置波形幅度范围
		this.wv_aortic.setValueRange(Constants.WAVEMIN16BIT, Constants.WAVEMAX16BIT);
		this.wv_mitral.setValueRange(Constants.WAVEMIN16BIT, Constants.WAVEMAX16BIT);
		this.wv_pulmonary.setValueRange(Constants.WAVEMIN16BIT, Constants.WAVEMAX16BIT);
		this.wv_tricuspid.setValueRange(Constants.WAVEMIN16BIT, Constants.WAVEMAX16BIT);

		this.setSignalLength(Constants.SAMPLELEN);
	}

	/**
	 * 设置信号长度
	 * 
	 * @param len
	 *            长度
	 */
	private void setSignalLength(int len) {
		this.wv_aortic.setCount(len);
		this.wv_mitral.setCount(len);
		this.wv_pulmonary.setCount(len);
		this.wv_tricuspid.setCount(len);

		this.wv_aortic.postInvalidate();
		this.wv_mitral.postInvalidate();
		this.wv_pulmonary.postInvalidate();
		this.wv_tricuspid.postInvalidate();
	}

	private int checkboxid = 0;

	// 创建左边四个按钮的上下文菜单
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		CheckBox cb = (CheckBox) v;
		checkboxid = cb.getId();
		getMenuInflater().inflate(R.menu.sample_contextmenu, menu);
		menu.setHeaderTitle(Utils.getPositonByCheckBoxID(this, checkboxid));
		if (cb.isChecked()) {
			menu.findItem(R.id.menu_show).setVisible(false);
		} else {
			menu.findItem(R.id.menu_hide).setVisible(false);
		}
	}

	// 上下文菜单被选中时
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_sample:
			Intent intent = new Intent(this, SampleOneActivity.class);
			intent.putExtra(Strings.POSITION_ID, checkboxid);
			startActivityForResult(intent, Constants.REQ_SAMPLEONE);
			break;
		case R.id.menu_hide:
			((CheckBox) findViewById(checkboxid)).setChecked(false);
			break;
		case R.id.menu_show:
			((CheckBox) findViewById(checkboxid)).setChecked(true);
			break;
		case R.id.menu_cancel:
			// When cancel, nothing to do
			break;
		default:
			Log.e(tag, "未处理的上下文菜单: " + item.getItemId());
			break;
		}
		return true;
	}

	// 创建菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sample_menu, menu);
		return true;
	}

	// 菜单选项事件
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Log.i(tag, item.getTitle().toString());
		switch (item.getItemId()) {
		case R.id.menu_save:
			this.showDialog(Constants.DIALOG_SAVE);
			break;
		case R.id.menu_open:
			this.showDialog(Constants.DIALOG_OPEN);
			break;
		case R.id.menu_length:
			setSignalLength(32768);
			break;
		case R.id.menu_analysis:
			this.showDialog(Constants.DIALOG_ANALYSISING);
			this.analysisSingal();
			break;
		default:
			onContextItemSelected(item);
			break;
		}
		return true;
	}

	EditText et_dlg_filename;
	EditText et_dlg_remark;

	// 保存
	private boolean saveData() {
		if (et_dlg_filename.getText().toString().trim().length() == 0) {
			Toast.makeText(this, R.string.filenameempty, Toast.LENGTH_LONG).show();
			et_dlg_filename.requestFocus();
			return false;
		}
		SampleActivity.this.showDialog(Constants.DIALOG_SAVING);
		new Runner(new Runnable() {
			@Override
			public void run() {
				String path = Utils.getDataPath() + "/";
				String filename = et_dlg_filename.getText().toString().trim();
				String remark = et_dlg_remark.getText().toString();
				int samplerate = Constants.SAMPLEFREQUENCE;
				SaveInfo saveinfo = new SaveInfo();
				SampleInfo sampleinfo = new SampleInfo(samplerate, remark, Utils.getDateTime(Constants.DF_STANDAR));

				// 下面保存四个通道的数据
				DataFileInfo datafileinfo = new DataFileInfo();
				if (sam_aortic != null) {
					datafileinfo.setAorticfile(filename + Constants.SUFFIX_AORTIC);
					WaveFileWriter.saveSingleChannel(path + datafileinfo.getAorticfile(), sam_aortic, samplerate);
				}
				if (sam_mitral != null) {
					datafileinfo.setMitralfile(filename + Constants.SUFFIX_MITRAL);
					WaveFileWriter.saveSingleChannel(path + datafileinfo.getMitralfile(), sam_mitral, samplerate);
				}
				if (sam_pulmonary != null) {
					datafileinfo.setPulmonaryfile(filename + Constants.SUFFIX_PULMONARY);
					WaveFileWriter.saveSingleChannel(path + datafileinfo.getPulmonaryfile(), sam_pulmonary, samplerate);
				}
				if (sam_tricuspid != null) {
					datafileinfo.setTricuspidfile(filename + Constants.SUFFIX_TRICUSPID);
					WaveFileWriter.saveSingleChannel(path + datafileinfo.getTricuspidfile(), sam_tricuspid, samplerate);
				}

				SharedPreferences sp = getSharedPreferences(Strings.DOCTORINFO, MODE_PRIVATE);
				Doctor doctor = new Doctor(sp.getString(Strings.NAME, ""), sp.getString(Strings.ADDRESS, ""), sp.getString(Strings.EMAIL, ""));

				AppInfo appinfo = new AppInfo();

				saveinfo.setPatient(patient);
				saveinfo.setSampleinfo(sampleinfo);
				saveinfo.setDatafileinfo(datafileinfo);
				saveinfo.setDoctor(doctor);
				saveinfo.setAppinfo(appinfo);
				saveinfo.setTime(Utils.getDateTime(Constants.DF_STANDAR));

				XStream stream = new XStream();
				String xml = stream.toXML(saveinfo);
				String txt = saveinfo.getSaveString();

				// 保存文本文件
				DataUtils.writeString(path + filename + Constants.SUFFIX_CHD, xml); // 工程文件
				DataUtils.writeString(path + filename + Constants.SUFFIX_TXT, txt); // 文本文件

				SampleActivity.this.dismissDialog(Constants.DIALOG_SAVING);

				showInfo(txt);
			}
		});
		return true;
	}

	// 分析
	private void analysisSingal() {
		new Runner(new Runnable() {
			@Override
			public void run() {
				long st = System.currentTimeMillis();
				XLog.i("start:%d", st);
				SimpleAnalyser analyser = AnalyserUtils.getSimpleAnalyser();
				
				double[] aortic = (double[])BasePipe.pipesCal(sam_aortic, new IntsToDoubles(wv_aortic.getStartX(), 32768));
				double[] mitral = null;//(double[])BasePipe.pipesCal(sam_aortic, new IntsToDoubles(wv_mitral.getStartX(), 32768));
				double[] pulmonary = null;//(double[])BasePipe.pipesCal(sam_aortic, new IntsToDoubles(wv_pulmonary.getStartX(), 32768));
				double[] tricuspid = null;//(double[])BasePipe.pipesCal(sam_aortic, new IntsToDoubles(wv_tricuspid.getStartX(), 32768));
				SimpleAnalyserInput analyserInput = new SimpleAnalyserInput(aortic, mitral, pulmonary, tricuspid);
				SimpleAnalyserResult analyserResult = analyser.analyser(analyserInput);

				long ed = System.currentTimeMillis();
				XLog.i("cost:%d", ed - st);
				XLog.i("result:%s", analyserResult.toString());
				
				
				SampleActivity.this.dismissDialog(Constants.DIALOG_ANALYSISING);
				long costtime = ed - st;
				String log = String.format("结果:%s\r\n耗时:%dms", analyserResult.toString(), costtime);
				XLog.i(log);
//				showInfo(log);
//				ReportActivity reportActivity = new ReportActivity();
				Intent intent = new Intent(SampleActivity.this, ReportActivity.class);
				intent.putExtra(Strings.RESULT, analyserResult);
				intent.putExtra(Strings.COSTTIME, costtime);
				intent.putExtra(Strings.PATIENT, patient);
				
				startActivity(intent);
			}
		});
	}

	// 打开
	private String openfilename = null;

	private boolean openData(String filename) {
		openfilename = filename;
		showDialog(Constants.DIALOG_OPENING);
		new Runner(new Runnable() {
			@Override
			public void run() {
				String xml = DataUtils.readString(openfilename);
				XStream xstream = new XStream();
				xstream.processAnnotations(SaveInfo.class);
				SaveInfo saveinfo = (SaveInfo) xstream.fromXML(xml);
				// Log.i(tag, saveinfo.getSaveString());
				DataFileInfo datafileinfo = saveinfo.getDatafileinfo();
				File file = new File(openfilename);
				String path = file.getParent() + "/";
				// Log.i(tag, path);
				wv_aortic.setWaveData(sam_aortic = WaveFileReader.readSingleChannel(path + datafileinfo.getAorticfile()));
				wv_mitral.setWaveData(sam_mitral = WaveFileReader.readSingleChannel(path + datafileinfo.getMitralfile()));
				wv_pulmonary.setWaveData(sam_pulmonary = WaveFileReader.readSingleChannel(path + datafileinfo.getPulmonaryfile()));
				wv_tricuspid.setWaveData(sam_tricuspid = WaveFileReader.readSingleChannel(path + datafileinfo.getTricuspidfile()));

				initWaveViewAttribute();

				SampleActivity.this.dismissDialog(Constants.DIALOG_OPENING);

				showInfo(saveinfo.getSaveString());
			}
		});
		return true;
	}

	private void showInfo(String info) {
		Intent intent = new Intent(SampleActivity.this, InfoShowActivity.class);
		intent.putExtra(Strings.INFO, info);
		startActivity(intent);
	}

	// 创建对话框
	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		// TODO Auto-generated method stub
		Dialog dialog = null;
		switch (id) {
		case Constants.DIALOG_SAVE:
			Builder builder = new Builder(this);
			builder.setTitle(R.string.save);
			View view = LinearLayout.inflate(this, R.layout.dialog_savedata, null);
			et_dlg_filename = (EditText) view.findViewById(R.id.et_filename);
			et_dlg_remark = (EditText) view.findViewById(R.id.et_remark);
			builder.setView(view);
			builder.setPositiveButton(R.string.ok, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					saveData();
				}
			});
			builder.setNegativeButton(R.string.cancel, null);
			dialog = builder.create();
			break;
		case Constants.DIALOG_SAVING:
			ProgressDialog savingdlg = new ProgressDialog(this);
			savingdlg.setMessage(Utils.getString(R.string.savingdata));
			dialog = savingdlg;
			break;
		case Constants.DIALOG_OPEN:
			Map<String, Integer> images = new HashMap<String, Integer>();
			images.put(OpenFileDialog.sRoot, R.drawable.filedialog_root);
			images.put(OpenFileDialog.sParent, R.drawable.filedialog_folder_up);
			images.put(OpenFileDialog.sFolder, R.drawable.filedialog_folder);
			images.put(Constants.SUFFIX_PROJECT_ONLY, R.drawable.filedialog_chd);
			images.put(OpenFileDialog.sEmpty, R.drawable.filedialog_chd);
			dialog = OpenFileDialog.createDialog(id, this, getResources().getString(R.string.openfile), new CallbackBundle() {
				@Override
				public void callback(Bundle bundle) {
					openData(bundle.getString("path"));
				}
			}, Constants.SUFFIX_PROJECT, images, Utils.getDataPath());
			break;
		case Constants.DIALOG_OPENING:
			ProgressDialog openingdlg = new ProgressDialog(this);
			openingdlg.setMessage(Utils.getString(R.string.openingfile));
			dialog = openingdlg;
			break;
		case Constants.DIALOG_ANALYSISING:
			ProgressDialog analysisingdlg = new ProgressDialog(this);
			analysisingdlg.setMessage(Utils.getString(R.string.analysising));
			dialog = analysisingdlg;
			break;
		}

		return dialog;
	}

	// 准备显示对话时
	@Override
	protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
		switch (id) {
		case Constants.DIALOG_SAVE:
			String name = null;
			if (patient.getName().length() > 0) {
				name = Utils.getDateTime(Constants.DF_SHORTNOS) + "-" + patient.getName();
			} else {
				name = Utils.getDateTime(Constants.DF_SHORTNOS);
			}
			et_dlg_filename.setText(name);
			break;

		default:
			break;
		}
	}

	// 四个位置采样到的数据
	private int[] sam_aortic = null;
	private int[] sam_mitral = null;
	private int[] sam_pulmonary = null;
	private int[] sam_tricuspid = null;

	// SampleOneActivity返回
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(tag, "onActivityResult");
		if (resultCode == RESULT_OK) { // 正常返回
			switch (requestCode) {
			case Constants.REQ_SAMPLEONE: // 采集一个位置的
				int len = data.getIntExtra(Strings.DATA_LENGTH, 0);
				int id = data.getIntExtra(Strings.POSITION_ID, 0);
				if (len > 0 && id > 0) {
					int[] buf = null;
					if (data.hasExtra(Strings.DATA))
						buf = data.getIntArrayExtra(Strings.DATA);
					else {
						String cachefile = data.getStringExtra(Strings.CACHE_FILE);
						Log.i(tag, "cache file: " + cachefile);
						buf = DataUtils.readIntArray(cachefile);
					}

					switch (id) {
					case R.id.cb_aortic:
						sam_aortic = buf;
						wv_aortic.setWaveData(sam_aortic);
						wv_aortic.postInvalidate();
						break;
					case R.id.cb_mitral:
						sam_mitral = buf;
						wv_mitral.setWaveData(sam_mitral);
						wv_mitral.postInvalidate();
						break;
					case R.id.cb_pulmonary:
						sam_pulmonary = buf;
						wv_pulmonary.setWaveData(sam_pulmonary);
						wv_pulmonary.postInvalidate();
						break;
					case R.id.cb_tricuspid:
						sam_tricuspid = buf;
						wv_tricuspid.setWaveData(sam_tricuspid);
						wv_tricuspid.postInvalidate();
						break;
					default:
						break;
					}
					((CheckBox) findViewById(id)).setChecked(true);
				}
				break;

			default:
				break;
			}
		}
	}

	// 设置四个CheckBox的长按事件监听器
	// 弹出菜单
	private OnLongClickListener positionClickListener = new OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {
			SampleActivity.this.openContextMenu(v); // 打开上下文菜单
			return true; // 已处理，不再传递该消息，返回true
		}
	};

	// 设置对应点波形是否可见
	private OnCheckedChangeListener positionCheckListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton ck, boolean isChecked) {
			WaveView wv = null;
			switch (ck.getId()) {
			case R.id.cb_aortic:
				wv = (WaveView) findViewById(R.id.wv_aortic);
				break;
			case R.id.cb_mitral:
				wv = (WaveView) findViewById(R.id.wv_mitral);
				break;
			case R.id.cb_pulmonary:
				wv = (WaveView) findViewById(R.id.wv_pulmonary);
				break;
			case R.id.cb_tricuspid:
				wv = (WaveView) findViewById(R.id.wv_tricuspid);
				break;
			default:
				break;
			}
			if (isChecked) {
				wv.setVisibility(View.VISIBLE);
			} else {
				wv.setVisibility(View.GONE);
			}
		}
	};
}
