package com.huotu.huobanmall.seller.activity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.avast.android.dialogs.iface.ISimpleDialogListener;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.utils.ToastUtils;
import com.huotu.huobanmall.seller.widget.CircleProgressBar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
/**
 * 客户端升级
 * @author jinxiangdong
 *
 */
public class AppUpdateActivity extends BaseFragmentActivity implements ISimpleDialogListener{
	protected static final int REQUEST_INFO = 1567;
	protected static final int CODE_SUCCESS=1;
	protected static final int CODE_PROGRESS=2;
	protected static final int CODE_FAIL=0;
	private String softwarePath = null;
	private boolean isCancel;
	private boolean taskIsComplete;
	private String destMd5;
	private String tips;
	private boolean isForce;
	TextView txtTips;
	Handler handler;
	CircleProgressBar progressWithArrow;

	@Override
	public void onNegativeButtonClicked(int requestCode) {
		if(requestCode == REQUEST_INFO){
			if (taskIsComplete) {
				downloadClientFailed();
			} else {
				//stop task
				isCancel = true;
				//delete file
				File file = new File(softwarePath);
				if (file.exists())
					file.delete();
				//finish();
			}
		}
	}

	@Override
	public void onPositiveButtonClicked(int requestCode) {
		if(requestCode == REQUEST_INFO){
			if (taskIsComplete) {
				downloadClientSuccess();
			}
		}
	}

	@Override
	public void onNeutralButtonClicked(int i) {

	}

	/**
	 * 整包更新，增量更新
	 * @author edushi
	 *
	 */
	public enum UpdateType{
		FullUpate, DiffUpdate
	}
	private UpdateType updateType;
	private String oldapk_filepath ;//"WeiboV3.apk";
	private String newapk_savepath ;//"WeiboV4.1.apk";
	private String clientURL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_appupdate);

		oldapk_filepath = Constant.PATH_PKG_TEMP  + File.separator + getPackageName() + "_old.apk";
		newapk_savepath = Constant.PATH_PKG_TEMP + File.separator + getPackageName() + "_new.apk";
		softwarePath = Constant.PATH_PKG_TEMP + File.separator + getPackageName() + "_patch.apk";

		isCancel = false;
		taskIsComplete = false;
		initView();

		Bundle extra = getIntent().getExtras();
		if(extra != null){
			//start download
			File file = new File(softwarePath);
			if(!file.exists())
				file.mkdirs();
			clientURL = extra.getString("url");
			updateType = (UpdateType) extra.getSerializable("type");
			isForce = extra.getBoolean("isForce");
			destMd5 = extra.getString("md5");
			tips = extra.getString("tips");
			txtTips = (TextView) findViewById(R.id.txtTips);
			txtTips.setText(tips);

			new ApkDownloadThread(handler, clientURL).start();
		}else{
			ToastUtils.showLong("升级需要的参数不能为空");
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
					.setTitle("提示")
					.setMessage("确定要取消更新吗？")
					.setPositiveButtonText("继续更新")
					.setNegativeButtonText("取消更新")
					.setRequestCode(REQUEST_INFO)
					.show();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void initView() {
		progressWithArrow = (CircleProgressBar) findViewById(R.id.progressWithArrow);


		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if( msg.what== CODE_SUCCESS){
					progressWithArrow.setProgress(100);
					//下载完成
					downloadClientSuccess();
				}
				else if( msg.what== CODE_PROGRESS){
					int precent = Integer.valueOf( msg.obj.toString() );
					progressWithArrow.setProgress( precent );
				}
				else if( msg.what ==CODE_FAIL){
					//下载失败
					downloadClientFailed();
				}
			}
		};

	}

	private void downloadClientFailed(){
		Toast.makeText(this, "新版本更新失败...", Toast.LENGTH_SHORT).show();
		Intent intent = getIntent();
		intent.putExtra("isForce", isForce);
		setResult(Constant.RESULT_CODE_CLIENT_DOWNLOAD_FAILED, intent);
		finish();
	}

	//版本下载成功，开始启动安装
	private void downloadClientSuccess(){
			if(updateType == UpdateType.FullUpate){//整包更新,直接进行安装
				installApk(softwarePath);
			}else{//增量更新

			}

		}

	private void installApk(String path){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(path)),	"application/vnd.android.package-archive");
		startActivityForResult(intent, 0);
		AppUpdateActivity.this.finish();
	}

	class ApkDownloadThread extends Thread {
		Handler mHandler;
		String mUrl;

		public ApkDownloadThread(Handler handler, String url) {
			this.mHandler = handler;
			this.mUrl = url;
		}

		@Override
		public void run() {
			HttpURLConnection hc = null;
			try {
				URL url = new URL(mUrl);
				hc = (HttpURLConnection) url.openConnection();
			} catch (IOException ex) {
			}
			if( hc ==null ){
				Message msg = mHandler.obtainMessage( CODE_FAIL , "下载APK文件失败。" );
				mHandler.sendMessage(msg);
				return;
			}

			InputStream update_is = null;
			BufferedInputStream update_bis = null;
			FileOutputStream update_os = null;
			BufferedOutputStream update_bos = null;
			byte[] buffer = null;
			try {
				if (hc.getResponseCode() != 200) {
					Message msg = mHandler.obtainMessage( CODE_FAIL ,  "下载APK文件失败。" );
					mHandler.sendMessage(msg);
					return;
				}
				int contentLen = hc.getContentLength();
				if (contentLen == 0) {
					Message msg = mHandler.obtainMessage( CODE_FAIL ,  "下载APK文件失败。" );
					mHandler.sendMessage(msg);
					return;
				}
				update_is = hc.getInputStream();
				update_bis = new BufferedInputStream(update_is, 1024);

				File cityMapFile = new File(softwarePath);
				if (cityMapFile.exists()) {
					cityMapFile.delete();
				}

				cityMapFile.createNewFile();

				update_os = new FileOutputStream(cityMapFile, false);
				update_bos = new BufferedOutputStream(update_os, 1024);

				buffer = new byte[1024];
				int readed = 0;
				int step = 0;
				while ((step = update_bis.read(buffer)) != -1 && !isCancel) {
					readed += step;
					update_bos.write(buffer, 0, step);
					update_bos.flush();

					int precent = (int) ((readed / (float) contentLen) * 100);
					//publishProgress((int) ((readed / (float)contentLen) * 100), readed ,contentLen);
					Message msg = mHandler.obtainMessage( CODE_PROGRESS , String.valueOf(precent));
					mHandler.sendMessage(msg);
				}
				update_os.flush();

				Message msg = mHandler.obtainMessage(CODE_SUCCESS, "100");
				mHandler.sendMessage(msg);
				return;
			} catch (IOException e) {
				e.printStackTrace();
				Message msg = mHandler.obtainMessage(CODE_FAIL,"下载失败。");
				mHandler.sendMessage(msg);
				return;
			} finally {
				try {
					if (update_bis != null)
						update_bis.close();
					if (update_is != null)
						update_is.close();
					if (update_bos != null)
						update_bos.close();
					if (update_os != null)
						update_os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
