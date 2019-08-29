package com.yiku.kdb_flat.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nahuo.library.controls.AutoCompleteTextViewEx;
import com.nahuo.library.controls.LoadingDialog;
import com.nahuo.library.helper.FunctionHelper;
import com.nahuo.library.helper.MD5Utils;
import com.yiku.kdb_flat.R;
import com.yiku.kdb_flat.custom_controls.ViewHub;
import com.yiku.kdb_flat.di.module.HttpManager;
import com.yiku.kdb_flat.model.bean.LoginBean;
import com.yiku.kdb_flat.model.bean.PublicData;
import com.yiku.kdb_flat.model.http.CommonSubscriber;
import com.yiku.kdb_flat.model.http.exception.ApiException;
import com.yiku.kdb_flat.model.http.response.KDBResponse;
import com.yiku.kdb_flat.ui.base.BaseAppCompatActivity;
import com.yiku.kdb_flat.utils.DialogUtils;
import com.yiku.kdb_flat.utils.RxUtil;
import com.yiku.kdb_flat.utils.SpManager;
import com.yiku.kdb_flat.utils.Utils;

import static com.yiku.kdb_flat.constant.Constant.LOGIN_REGISTER_FROM_ANDROID;

public class LoginActivity extends BaseAppCompatActivity implements OnClickListener {
	private static String TAG=LoginActivity.class.getSimpleName();
	private LoginActivity mContext = this;
	private AutoCompleteTextViewEx edtAccount;
	private EditText edtPassword;
	private Button btnLogin;
	public static  String EXTA_ISSHOWERROR="EXTA_ISSHOWERROR";
	private boolean isShowError=false;
	private LoadingDialog loadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
	}


	/**
	 * 初始化视图
	 */
	private void initView() {


		loadingDialog = new LoadingDialog(mContext);
		edtAccount = (AutoCompleteTextViewEx) mContext.findViewById(R.id.login_edtAccount);
		edtPassword = (EditText) mContext.findViewById(R.id.login_edtPassword);

		btnLogin = (Button) mContext.findViewById(R.id.login_btnLogin);

		edtAccount.setOnSearchLogDeleteListener(new AutoCompleteTextViewEx.OnSearchLogDeleteListener() {
			@Override
			public void onSearchLogDeleted(String text) {
				String newChar = SpManager.deleteLoginAccounts(mContext, text);
				Log.i(getClass().getSimpleName(), "deleteSearchItemHistories:" + newChar);
				edtAccount.populateData(newChar, ",");
				edtAccount.getFilter().filter(edtAccount.getText());
			}
		});

		edtAccount.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				edtAccount.setFocusable(true);
				edtAccount.setFocusableInTouchMode(true);
				edtAccount.requestFocus();
				edtAccount.findFocus();
				edtPassword.setFocusable(false);
				edtPassword.setFocusableInTouchMode(false);

				return false;
			}
		});
		edtPassword.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				edtPassword.setFocusable(true);
				edtPassword.setFocusableInTouchMode(true);
				edtPassword.requestFocus();
				edtPassword.findFocus();
				edtAccount.setFocusable(false);
				edtAccount.setFocusableInTouchMode(false);

				return false;
			}
		});
		String username = SpManager.getLoginAccounts(mContext);
		edtAccount.populateData(username, ",");

		btnLogin.setOnClickListener(this);
		findViewById(R.id.img_see_pwd).setOnClickListener(this) ;
		isShowError=getIntent().getBooleanExtra(EXTA_ISSHOWERROR,false);
		if (isShowError){
			try {
				ViewHub.showOkDialog(LoginActivity.this, "提示", "未授权登陆", "知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.login_btnLogin:
				login();
				break;
			case R.id.img_see_pwd:
				int length = edtPassword.getText().length() ;
				if(length> 0){
					if(edtPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD){
						edtPassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD) ;
						edtPassword.invalidate() ;
						edtPassword.setSelection(length) ;
					}
					else{
						edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD) ;
						edtPassword.invalidate() ;
						edtPassword.setSelection(edtPassword.getText().length()) ;
					}
				}
				break ;
		}
	}
	private void gotoLogin(String phoneNo, String password) {
		String imei = Utils.GetAndroidImei(mContext);
		//android版本号
		String currentapiVersion = android.os.Build.VERSION.RELEASE;
		//手机型号
		String phoneName = android.os.Build.MANUFACTURER;
		//网络
		String netName = Utils.GetNetworkType(mContext);
		addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG)
						.getLoginData(phoneNo, MD5Utils.encrypt32bit(password),
								true, LOGIN_REGISTER_FROM_ANDROID, imei, phoneName, "android" + currentapiVersion, netName, imei, "快店宝_Android_平板")
						.compose(RxUtil.<KDBResponse<LoginBean>>rxSchedulerHelper())
						.compose(RxUtil.<LoginBean>handleResult())
						.subscribeWith(new CommonSubscriber<LoginBean>(mContext, true, R.string.login_doLogin_loading) {
							@Override
							public void onNext(LoginBean loginBean) {
								super.onNext(loginBean);
								if (!TextUtils.isEmpty(PublicData.getCookie(mContext))) {
									SpManager.setCookie(mContext, PublicData.getCookie(mContext));
								}
								//设置登录状态
								SpManager.setIs_Login(mContext, true);
								SpManager.setLoginAccount(mContext, edtAccount.getText().toString());
								if (loginBean != null) {
									SpManager.setUserId(mContext, loginBean.getUserID());
									SpManager.setUserName(mContext, loginBean.getUserName());
								}

								Intent intent = new Intent(mContext, MainActivity.class);
								startActivity(intent);
								finish();
							}

							@Override
							public void onError(Throwable e) {
								super.onError(e);
								SpManager.setIs_Login(mContext, false);
								if (e instanceof ApiException) {
									ApiException apiException = (ApiException) e;
									if (!TextUtils.isEmpty(apiException.getCode())) {
										if (apiException.getCode().equals(RxUtil.USER_NO_EXIST)) {
											//设置登录状态
//											DialogUtils.showSureCancelDialog(WXEntryActivity.this, "登陆提示", "该账号还没注册，是否立即去注册？"
//													, "注册账号", "换号登陆", new OnClickListener() {
//														@Override
//														public void onClick(View v) {
//															Dialog dialog = (Dialog) v.getTag();
//															dialog.dismiss();
//															//注册账号
//															toReg();
//														}
//													}, new OnClickListener() {
//														@Override
//														public void onClick(View v) {
//															Dialog dialog = (Dialog) v.getTag();
//															dialog.dismiss();
//															edtAccount.setText(null);
//															edtPassword.setText(null);
//															edtAccount.requestFocus();
//															edtAccount.setSelection(0);
//														}
//													});
											DialogUtils.showToast(mContext, apiException.getMessage() , 2000);
										} else if (apiException.getCode().equals(RxUtil.PASSWORD_ERROR)) {
											DialogUtils.showToast(mContext, apiException.getMessage() + "\n请重新填写", 2000);
										}
									}
								}

							}
						})
		);
	}

	/**
	 * 登录
	 */
	private void login() {
		// 验证用户录入
		if (!validateInput())
			return;
		// 验证网络
		if (!FunctionHelper.CheckNetworkOnline(mContext))
			return;
		// 执行登录操作
		//new Task().execute();
		String phoneNo = edtAccount.getText().toString().trim();
		String password = edtPassword.getText().toString().trim();
		gotoLogin(phoneNo,password);
	}

	/**
	 * 验证用户输入
	 */
	private boolean validateInput() {
		String phoneNo = edtAccount.getText().toString().trim();
		String password = edtPassword.getText().toString().trim();
		// 验证手机号
		if (TextUtils.isEmpty(phoneNo)) {
			Toast.makeText(mContext, R.string.login_edtAccount_empty, Toast.LENGTH_SHORT).show();
			edtAccount.requestFocus();
			return false;
		}
		// 验证密码
		if (TextUtils.isEmpty(password)) {
			Toast.makeText(mContext, R.string.login_edtPassword_empty, Toast.LENGTH_SHORT).show();
			edtPassword.requestFocus();
			return false;
		}
		return true;
	}

//	private class Task extends AsyncTask<Object, Void, Object> {
//
//		public Task() {
//		}
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//					loadingDialog.start(getString(R.string.login_doLogin_loading));
//
//		}
//
//		@Override
//		protected Object doInBackground(Object... params) {
//			try {
////						String phoneNo = edtAccount.getText().toString().trim();
////						String password = edtPassword.getText().toString().trim();
////						AccountAPI.getInstance().userLogin(phoneNo, password);
//
//			} catch (Exception e) {
//				e.printStackTrace();
//				return "error:" + e.getMessage();
//			}
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(Object result) {
//			super.onPostExecute(result);
//			if (loadingDialog.isShowing()) {
//				loadingDialog.stop();
//			}
//			if (result instanceof String && ((String) result).startsWith("error:")) {
//				ViewHub.showLongToast(mContext, ((String) result).replace("error:", ""));
//			} else {
//						if (!TextUtils.isEmpty(PublicData.getCookie(mContext))) {
//							SpManager.setCookie(mContext, PublicData.getCookie(mContext));
//						}
//						SpManager.setLoginAccount(mContext, edtAccount.getText().toString());
//
//						Intent intent = new Intent(mContext, MainActivity.class);
//						startActivity(intent);
//			}
//		}

	//}
}
