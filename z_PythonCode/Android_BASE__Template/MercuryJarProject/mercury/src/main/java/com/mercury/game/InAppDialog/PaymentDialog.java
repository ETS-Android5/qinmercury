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
import androidx.annotation.NonNull;
//shrinkpartend
import com.mercury.game.util.PayMethodCallBack;
import com.mercury.game.util.SPUtils;
import com.mercury.game.util.SpConfig;
import com.mercury.game.util.UIUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PaymentDialog {
    //shrinkpartstart
    Activity mContext;
    PayMethodCallBack mLoginCallBack;
    final AlertDialog dialog;

    public PaymentDialog(Activity context, PayMethodCallBack callBack) {

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
//                mLoginCallBack.success(mCardId);
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
        int mainLayout = getResId(mContext, "singmaan_dialog_pay", "layout");
        View myLayout = mContext.getLayoutInflater().inflate(mainLayout, null);
        int loginId = getResId(mContext, "singmaan_login", "id");
        int loadingId = getResId(mContext, "singmaan_loading", "id");
        int cancelId=getResId(mContext,"singmaan_cancel","id");
        final Button WechatPayButton = myLayout.findViewById(loginId);
        final ProgressBar progressBar = myLayout.findViewById(loadingId);
        final Button  AliypayButton = myLayout.findViewById(cancelId);
        progressBar.setVisibility(View.INVISIBLE);
        //设置显示父容器
        dialog.setView(myLayout);
        AliypayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginCallBack.Alipay("Alipay");
                dialog.dismiss();
            }
        });
        WechatPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginCallBack.WechatPay("WechatPay");
                dialog.dismiss();
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
