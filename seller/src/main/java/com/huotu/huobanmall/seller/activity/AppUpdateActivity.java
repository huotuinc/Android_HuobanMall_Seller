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
import com.huotu.huobanmall.seller.common.BaseService;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.utils.ToastUtils;
import com.huotu.huobanmall.seller.widget.CircleProgressBar;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.text.format.Formatter;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
/**
 * 客户端升级
 * @author edushi
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
//	private String PATH = Environment.getExternalStorageDirectory()
	private String oldapk_filepath ;//"WeiboV3.apk";
	private String newapk_savepath ;//"WeiboV4.1.apk";
	//private String patchpath = "patch.apk";//"weibopatch.apk";
	private String clientURL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_appupdate);
		//LinearLayout lay = (LinearLayout) findViewById(R.id.lay);
		//lay.getBackground().setAlpha(150);

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

		//progressWithArrow.setColorSchemeColors(Color.BLUE,Color.YELLOW);
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
//				new Thread(){
//					public void run() {
//						//将本地中的apk拷贝一份至临时文件夹
//						backupApk(getPackageResourcePath(), oldapk_filepath);
//						//step1.patch包与旧包生成新包，并生成新包md5
//						L.i("patch start!");
//						PatchUtils.patch(oldapk_filepath, newapk_savepath, softwarePath);
//						L.i("patch end!");
//						//step2.检测md5值是否一致
//						String patchMd5 = SecurityUtil.md5sum(newapk_savepath);
//						if(destMd5.equals(patchMd5)){//step3.2.md5一致，进行安装
//							installApk(newapk_savepath);
//						}else{//step3.1.md5不一致重新下载，进行整包更新
//							//T.show(AppUpdateActivity.this, "重新更新");
//							handler.post(new Runnable() {
//								@Override
//								public void run() {
//									CustomDialog.showChooiceDialg(AppUpdateActivity.this, "温馨提示", "验证错误!更新失败，是否重新更新?", "重新更新", "取消", null,
//											new DialogInterface.OnClickListener() {
//												@Override
//												public void onClick(DialogInterface dialog, int which) {
//													task = new ClientDownLoadTask();
//													task.execute(clientURL);
//												}
//											}, new DialogInterface.OnClickListener() {
//												@Override
//												public void onClick(DialogInterface dialog, int which) {
//													//下载失败
//													downloadClientFailed();
//												}
//											});
//								}
//							});
//						}
//					};
//				}.start();
			}

		}

	private void installApk(String path){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(path)),	"application/vnd.android.package-archive");
		startActivityForResult(intent, 0);
		AppUpdateActivity.this.finish();
	}

//	class ClientDownLoadTask extends AsyncTask<String, Integer, Integer>{
//		private ClientDownLoadHttpService service;
//		public ClientDownLoadTask(){
//			service = new ClientDownLoadHttpService();
//		}
//		@Override
//		protected Integer doInBackground(String... params) {
//			HttpURLConnection hc = service.download(params[0]);
//
//			if(hc != null){
//				InputStream update_is = null;
//				BufferedInputStream update_bis = null;
//				FileOutputStream update_os = null;
//				BufferedOutputStream update_bos = null;
//				byte[] buffer = null;
//				try{
//					if(hc.getResponseCode() != 200){
//						return null;
//					}
//					int contentLen = hc.getContentLength();
//					if(contentLen == 0){
//						return null;
//					}
//					update_is = hc.getInputStream();
//					update_bis = new BufferedInputStream(update_is, 1024);
//
//					File cityMapFile = new File(softwarePath);
//					if(cityMapFile.exists()){
//						cityMapFile.delete();
//					}
//
//					cityMapFile.createNewFile();
//
//					update_os = new FileOutputStream(cityMapFile,false);
//					update_bos = new BufferedOutputStream(update_os, 1024);
//
//					buffer = new byte[1024];
//					int readed = 0;
//					int step = 0;
//					while((step = update_bis.read(buffer)) != -1 && !isCancel){
//						readed += step;
//						update_bos.write(buffer,0,step);
//						update_bos.flush();
//						publishProgress((int) ((readed / (float)contentLen) * 100), readed ,contentLen);
//					}
//					update_os.flush();
//					return contentLen;
//				}catch(IOException e){
//					e.printStackTrace();
//					return null;
//				}finally{
//					try{
//						if(update_bis != null)
//							update_bis.close();
//						if(update_is != null)
//							update_is.close();
//						if(update_bos != null)
//							update_bos.close();
//						if(update_os != null)
//							update_os.close();
//					}catch(IOException e){
//						e.printStackTrace();
//					}
//				}
//			}else{
//				//下载失败
//				return null;
//			}
//		}
//
//		@Override
//		protected void onProgressUpdate(Integer... values) {
//			super.onProgressUpdate(values);
//			String des = String.format("%s/%s", formatterSize(values[1]), formatterSize(values[2]));
//			//mTasksView.setProgress(values[0], des);
//			progressWithArrow.setProgress( values[0] );
//			txtTips.setText( des );
//		}
//
//		@Override
//		protected void onPostExecute(Integer result) {
//			super.onPostExecute(result);
//			taskIsComplete = true;
//			if(result != null && !isCancel){
//				String des = String.format("%s/%s", formatterSize(result), formatterSize(result));
//				//mTasksView.setProgress(100, des);
//				progressWithArrow.setProgress(100);
//				//下载完成
//				downloadClientSuccess();
//			}else{
//				//下载失败
//				downloadClientFailed();
//			}
//		}
//	}

//	private String formatterSize(int size){
//		return Formatter.formatFileSize(AppUpdateActivity.this, size);
//	}

//	class ClientDownLoadHttpService extends BaseService {
//		HttpURLConnection download(String url){
//			try{
//				HttpURLConnection hc = openMyConnection(url);
//				return hc;
//			}catch (IOException e) {
//				e.printStackTrace();
//			}
//			return null;
//		}
//	}

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
