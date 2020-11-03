package com.mercury.game.InAppDialog;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
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
import com.mercury.game.MercuryActivity;
import com.mercury.game.util.LoginCallBack;
import com.mercury.game.util.SPUtils;
import com.mercury.game.util.SpConfig;
import com.mercury.game.util.UIUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mercury.game.InAppRemote.RemoteConfig.account_id;
import static com.mercury.game.InAppRemote.RemoteConfig.chinese_id;
import static com.mercury.game.InAppRemote.RemoteConfig.id_signe_in_result;
import static com.mercury.game.InAppRemote.RemoteConfig.verify_signe_in;
import static com.mercury.game.MercuryActivity.LogLocal;


public class SigneInDialog {
    //shrinkpartstart
    Activity mContext;
    LoginCallBack mLoginCallBack;
    final AlertDialog dialog;

    public SigneInDialog(Activity context, LoginCallBack callBack) {

        mContext = context;
        mLoginCallBack = callBack;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, getResId(mContext,"mercury_dialog_style","style"));
        dialog = builder.create();
        dialog.setCancelable(true);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        initAlertDialog(dialog);
        Show();
    }

    public void Show() {
        String mCardId = SPUtils.getInstance().getString(SpConfig.USER_CARD_ID);

        if (!TextUtils.isEmpty(mCardId)) {
            if (mLoginCallBack != null) {
                mLoginCallBack.success(mCardId);
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
        int mainLayout = getResId(mContext, "mercury_dialog_signein", "layout");
        View myLayout = mContext.getLayoutInflater().inflate(mainLayout, null);
        int user_name = getResId(mContext, "mercury_username", "id");
        int password = getResId(mContext, "mercury_password", "id");
        final int password_again = getResId(mContext, "mercury_password_again", "id");
        int mgsId = getResId(mContext, "mercury_verifymgs", "id");
        int loginId = getResId(mContext, "mercury_login", "id");
        int loadingId = getResId(mContext, "mercury_loading", "id");
//        int cancelId=getResId(mContext,"mercury_cancel","id");


        final EditText cardIdEditText = myLayout.findViewById(user_name);
        final EditText nameEditText = myLayout.findViewById(password);
        final EditText passwordagainEditText = myLayout.findViewById(password_again);
        final Button loginButton = myLayout.findViewById(loginId);
        final TextView mgsTextView = myLayout.findViewById(mgsId);
        final ProgressBar progressBar = myLayout.findViewById(loadingId);
//        final Button  cancelButton = myLayout.findViewById(cancelId);

//        cardIdEditText.setKeyListener(new NumberKeyListener() {
//            @NonNull
//            @Override
//            protected char[] getAcceptedChars() {
//                char[] c = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
//                        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
//                        'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
//                        'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
//                        'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
//                        'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
//                return c;
//
//            }
//
//            @Override
//            public int getInputType() {
//                return 3;
//            }
//        });


        progressBar.setVisibility(View.INVISIBLE);
        mgsTextView.setVisibility(View.GONE);

        //设置显示父容器

        dialog.setView(myLayout);

        nameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final String user_name = cardIdEditText.getText().toString();
                final String password = nameEditText.getText().toString();
                final String password_again = passwordagainEditText.getText().toString();
                account_id = user_name;
                if (!password.equals(password_again))
                {
//                    cardIdEditText.setError("两次密码不匹配");
                    Toast.makeText(mContext, "两次密码不匹配", Toast.LENGTH_SHORT).show();
                }
                else if (user_name.equals("") || password.equals("") || password_again.equals(""))
                {
//                    cardIdEditText.setError("输入不能为空");
                    Toast.makeText(mContext, "输入不能为空", Toast.LENGTH_SHORT).show();
                }
                else
                    {
                    verify_signe_in(user_name, password);
                    progressBar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                            LogLocal("[InAppDialog][SigneInDialog] id_signe_in_result=" + id_signe_in_result);
                            if (id_signe_in_result.equals("")) {
                                Toast.makeText(mContext, "服务器繁忙", Toast.LENGTH_SHORT).show();
                            } else if (id_signe_in_result.equals("-200")) {
                                Toast.makeText(mContext, "密码错误", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    }, 3000); // 延时1秒
                }
            }
        });

    }


    private boolean isIDNum(String phone) {
        String regExp = "^(\\d{18,18}|\\d{15,15}|\\d{17,17}X)$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phone);
        return m.find();//boolean
    }

    private void showLoginFailed(String errorString) {
        Toast.makeText(mContext, errorString, Toast.LENGTH_SHORT).show();
    }
    //shrinkpartend
}
