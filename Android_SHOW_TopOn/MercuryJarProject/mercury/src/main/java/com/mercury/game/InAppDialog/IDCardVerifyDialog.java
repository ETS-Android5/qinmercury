package com.mercury.game.InAppDialog;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.text.method.NumberKeyListener;
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

//shrinkpartend
import com.mercury.game.util.LoginCallBack;
import com.mercury.game.util.SPUtils;
import com.mercury.game.util.SpConfig;
import com.mercury.game.util.UIUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IDCardVerifyDialog {
    //shrinkpartstart
    Activity mContext;
    LoginCallBack mLoginCallBack;
    final AlertDialog dialog;

    public IDCardVerifyDialog(Activity context, LoginCallBack callBack) {

        mContext = context;
        mLoginCallBack = callBack;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, getResId(mContext,"singmaan_dialog_style","style"));
        dialog = builder.create();
        dialog.setCancelable(false);
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
        int mainLayout = getResId(mContext, "singmaan_dialog_verify", "layout");
        View myLayout = mContext.getLayoutInflater().inflate(mainLayout, null);
        int cardId = getResId(mContext, "singmaan_username", "id");
        int name = getResId(mContext, "singmaan_password", "id");
        int mgsId = getResId(mContext, "singmaan_verifymgs", "id");
        int loginId = getResId(mContext, "singmaan_login", "id");
        int loadingId = getResId(mContext, "singmaan_loading", "id");
        int cancelId=getResId(mContext,"singmaan_cancel","id");


        final EditText cardIdEditText = myLayout.findViewById(cardId);
        final EditText nameEditText = myLayout.findViewById(name);
        final Button loginButton = myLayout.findViewById(loginId);
        final TextView mgsTextView = myLayout.findViewById(mgsId);
        final ProgressBar progressBar = myLayout.findViewById(loadingId);
        final Button  cancelButton = myLayout.findViewById(cancelId);

        cardIdEditText.setKeyListener(new NumberKeyListener() {
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
        mgsTextView.setVisibility(View.GONE);

        //设置显示父容器

        dialog.setView(myLayout);

        nameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                return false;
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelButton.setVisibility(View.GONE);
                cardIdEditText.setVisibility(View.GONE);
                nameEditText.setVisibility(View.GONE);
                mgsTextView.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                mgsTextView.setText("实名制认证未完成，为了你的账号安全，请尽快完成实名制认证！");
                loginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mLoginCallBack != null) {
                            mLoginCallBack.success("实名制认证未完成，为了你的账号安全，请尽快完成实名制认证！");
                        }
                        dialog.dismiss();
                    }
                });
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = cardIdEditText.getText().toString();
                if (isIDNum(id)) {
                    loginButton.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(mContext, "验证成功", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    showLoginFailed("请输入正确的身份证号");
                    cardIdEditText.setError("请输入正确的身份证号");
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
