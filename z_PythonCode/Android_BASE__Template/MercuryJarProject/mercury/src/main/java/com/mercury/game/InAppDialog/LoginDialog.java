package com.mercury.game.InAppDialog;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;

import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
//shrinkpartstart
import androidx.annotation.NonNull;
//shrinkpartend

import com.mercury.game.util.LoginCallBack;
import com.mercury.game.util.MD5Util;
import com.mercury.game.util.MercuryConst;
import com.mercury.game.util.SPUtils;
import com.mercury.game.util.SpConfig;
import com.mercury.game.util.UIUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mercury.game.InAppRemote.RemoteConfig.account_id;
import static com.mercury.game.InAppRemote.RemoteConfig.chinese_id;
import static com.mercury.game.InAppRemote.RemoteConfig.id_signe_in_result;
import static com.mercury.game.InAppRemote.RemoteConfig.login_in;
import static com.mercury.game.InAppRemote.RemoteConfig.login_in_result;
import static com.mercury.game.MercuryActivity.LogLocal;


public class LoginDialog {
    //shrinkpartstart
    String oldId;
    int time;
    Activity mContext;
    LoginCallBack mLoginCallBack;
    final AlertDialog dialog;

    public LoginDialog(Activity context, String id, LoginCallBack callBack) {
        mContext = context;
        oldId = id;
        mLoginCallBack = callBack;
//
        AlertDialog.Builder builder = new AlertDialog.Builder(context, getResId(mContext,"mercury_dialog_style","style"));
        dialog = builder.create();
        dialog.setCancelable(false);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);

        initAlertDialog(dialog);
        Show();
    }

    public void Show() {
        String mPhone = SPUtils.getInstance().getString(SpConfig.USER_PHONE);
        String m5 = SPUtils.getInstance().getString("USER_PHONE_MD5");


        if (!TextUtils.isEmpty(mPhone) && TextUtils.equals(m5, MD5Util.getMD5String(mPhone))) {
            Log.e("mPhone", mPhone);
            if (mLoginCallBack != null) {

                mLoginCallBack.success(mPhone);
            }
            return;
        }
        dialog.show();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = UIUtils.dip2px(mContext, 332);//宽高可设置具体大小
        lp.height =WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
//        dialog.getWindow().setLayout(UIUtils.dip2px(mContext, 332), ViewGroup.LayoutParams.WRAP_CONTENT);


    }

    public int getResId(Context context, String name, String type) {
        return context.getResources().getIdentifier(name, type, context.getPackageName());

    }

    public void initAlertDialog(final AlertDialog dialog) {
        int mainLayout = getResId(mContext, "mercury_dialog_login", "layout");
        View myLayout = mContext.getLayoutInflater().inflate(mainLayout, null);
        int nameId = getResId(mContext, "mercury_username", "id");
        int passId = getResId(mContext, "mercury_password", "id");
        int loginId = getResId(mContext, "mercury_login", "id");
        int loadingId = getResId(mContext, "mercury_loading", "id");
        int cancelId=getResId(mContext,"mercury_cancel","id");

        final EditText usernameEditText = myLayout.findViewById(nameId);
        final EditText passwordEditText = myLayout.findViewById(passId);
        final Button loginButton = myLayout.findViewById(loginId);
        final ProgressBar progressBar = myLayout.findViewById(loadingId);
        final Button  cancelButton = myLayout.findViewById(cancelId);
        usernameEditText.setKeyListener(new NumberKeyListener() {
            @NonNull
            @Override
            protected char[] getAcceptedChars() {
                char[] c = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                        'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                        'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
                        'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                        'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
                return c;

            }

            @Override
            public int getInputType() {
                return 3;
            }
        });

        passwordEditText.setKeyListener(new NumberKeyListener() {
            @NonNull
            @Override
            protected char[] getAcceptedChars() {
                char[] c = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                        'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                        'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
                        'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                        'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
                return c;
            }

            @Override
            public int getInputType() {
                return 3;
            }
        });


        progressBar.setVisibility(View.INVISIBLE);

        //设置显示父容器

        dialog.setView(myLayout);

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                return false;
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user_name = usernameEditText.getText().toString();
                final String password = passwordEditText.getText().toString();
                account_id = user_name;
                if (user_name.equals("") || password.equals(""))
                {
                    Toast.makeText(mContext, "输入不能为空", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    login_in(user_name, password);
                    progressBar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                            LogLocal("[RemoteConfig][SigneInDialog] id_signe_in_result=" + id_signe_in_result);
                            if (login_in_result.equals("")) {
                                Toast.makeText(mContext, "服务器繁忙", Toast.LENGTH_SHORT).show();
                            } else if (login_in_result.equals("-200")) {
                                Toast.makeText(mContext, "密码错误", Toast.LENGTH_SHORT).show();
                            }
                            else if (login_in_result.equals("-201")) {
                                Toast.makeText(mContext, "账号不存在", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                                if (mLoginCallBack != null) {
                                    if (chinese_id.equals("")) {
                                        new IDCardVerifyDialog(mContext, new LoginCallBack() {
                                            @Override
                                            public void success(String msg) {
                                                LogLocal("[InAppDialog][SigneInDialog] ID card Success");
                                                mLoginCallBack.success(user_name);
                                            }
                                            @Override
                                            public void fail(String msg) {
                                                LogLocal("[InAppDialog][SigneInDialog] ID card failed");
                                            }
                                        });
                                    }
                                    else
                                    {
                                        //age verify
                                        LogLocal("[InAppDialog][SigneInDialog] ID card failed");
                                    }
                                }
                                dialog.dismiss();
                            }
                        }
                    }, 3000); // 延时1秒
                }

            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = usernameEditText.getText().toString();
                    dialog.dismiss();
                    new SigneInDialog(mContext, new LoginCallBack() {
                        @Override
                        public void success(String msg) {
                            LogLocal("[InAppChannel][MercurySigneIn] ID card Success");
                            mLoginCallBack.success(msg);
                        }
                        @Override
                        public void fail(String msg) {
                            LogLocal("[InAppChannel][MercurySigneIn] ID card failed");
                            mLoginCallBack.success(msg);
                        }
                    });
                }
        });

    }


    private boolean isPhoneNum(String phone) {
        String regExp = "^[1]([3-9])[0-9]{9}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phone);
        return m.find();//boolean
    }

    private void showLoginFailed(String errorString) {
        Toast.makeText(mContext, errorString, Toast.LENGTH_SHORT).show();
    }
    //shrinkpartend
}
