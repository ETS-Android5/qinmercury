package com.mercury.game.InAppDialog;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.os.CountDownTimer;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(context, getResId(mContext,"singmaan_dialog_style","style"));
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
        int mainLayout = getResId(mContext, "singmaan_dialog_login", "layout");
        View myLayout = mContext.getLayoutInflater().inflate(mainLayout, null);
        int nameId = getResId(mContext, "singmaan_username", "id");
        int passId = getResId(mContext, "singmaan_password", "id");
        int codeId = getResId(mContext, "singmaan_btn_code", "id");
        int loginId = getResId(mContext, "singmaan_login", "id");
        int loadingId = getResId(mContext, "singmaan_loading", "id");
        int cancelId=getResId(mContext,"singmaan_cancel","id");

        final EditText usernameEditText = myLayout.findViewById(nameId);
        final EditText passwordEditText = myLayout.findViewById(passId);
        final Button loginButton = myLayout.findViewById(loginId);
        final Button codeButton = myLayout.findViewById(codeId);
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
                if (mLoginCallBack != null) {
                    mLoginCallBack.success(oldId);
                }
                showLoginFailed("为保障您的游戏体验，建议下次游戏绑定手机号！");
                dialog.dismiss();

            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = usernameEditText.getText().toString();
                if (isPhoneNum(phone)) {
                    loginButton.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    try {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Testing Mode");
                        builder.setTitle("Choice Result");
                        builder.setPositiveButton("Success", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showLoginFailed("登录成功");
                                SPUtils.getInstance().put(SpConfig.USER_PHONE, phone);
                                SPUtils.getInstance().put("USER_PHONE_MD5", MD5Util.getMD5String(phone));
                                loginButton.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                                if (mLoginCallBack != null) {
                                    mLoginCallBack.success(phone);
                                }
                                dialog.dismiss();
                            }
                        });
                        builder.setNeutralButton("Failed", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                passwordEditText.setError("Failed");
                                loginButton.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.create().show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    showLoginFailed("请输入正确的手机号");
                    usernameEditText.setError("请输入正确的手机号");
                }
            }
        });

        final CountDownTimer countDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long l) {
                time = (int) (l / 1000);
                codeButton.setText(String.valueOf(time));
            }

            @Override
            public void onFinish() {
                codeButton.setText("获取验证码");
            }
        };
        codeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPhoneNum(usernameEditText.getText().toString())) {
                    if (time == 0) {
                        countDownTimer.start();
                        Toast.makeText(mContext, "验证码已发送", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showLoginFailed("请输入正确的手机号");
                    usernameEditText.setError("请输入正确的手机号");
                }


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
