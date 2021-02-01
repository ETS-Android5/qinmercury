package com.mercury.game.InAppDialog;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import com.mercury.game.InAppDialog.CardIdUtils;
import com.mercury.game.util.LoginCallBack;
import com.mercury.game.util.NetCheckUtil;
import com.mercury.game.util.SPUtils;
import com.mercury.game.util.SpConfig;
import com.mercury.game.util.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//shrinkpartstart
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.mercury.game.InAppDialog.LoginDialog.getAgeByIDNumber;
import static com.mercury.game.InAppRemote.RemoteConfig.verify_chinese_id;
//shrinkpartend
import static com.mercury.game.InAppRemote.RemoteConfig.account_id;
import static com.mercury.game.InAppRemote.RemoteConfig.chinese_id;
import static com.mercury.game.InAppRemote.RemoteConfig.id_signe_in_result;
import static com.mercury.game.InAppRemote.RemoteConfig.id_verify_result;

import static com.mercury.game.MercuryActivity.LogLocal;
import static com.mercury.game.MercuryActivity.mActivity;
import static com.mercury.game.util.Function.readFileData;
import static com.mercury.game.util.Function.writeFileData;
import static com.mercury.game.util.UIUtils.isJSONValid;
import static com.mercury.game.MercuryActivity.local_age;

public class IDCardVerifyDialog {
    //shrinkpartstart
    Activity mContext;
    LoginCallBack mLoginCallBack;
    final AlertDialog dialog;
    private Handler mHandler;
    public static boolean clicked = false;
    public IDCardVerifyDialog(Activity context, LoginCallBack callBack) {

        mContext = context;
        mLoginCallBack = callBack;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, getResId(mContext,"mercury_dialog_style","style"));
        dialog = builder.create();
        dialog.setCancelable(false);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        initAlertDialog(dialog);
        if (readFileData("chinese_id").equals(""))
        {
            Show();
        }
        else
        {

        }
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

    public boolean validateParams(String card_id,String name_id){
        clicked=false;
        if (card_id.equals("") || name_id.equals("")) {
            Toast.makeText(mContext, "输入不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @SuppressLint("HandlerLeak")
    public void initAlertDialog(final AlertDialog dialog) {
        int mainLayout = getResId(mContext, "mercury_dialog_verify", "layout");
        View myLayout = mContext.getLayoutInflater().inflate(mainLayout, null);
        int cardId = getResId(mContext, "mercury_username", "id");
        int name = getResId(mContext, "mercury_password", "id");
        int mgsId = getResId(mContext, "mercury_verifymgs", "id");
        int loginId = getResId(mContext, "mercury_login", "id");
        int loadingId = getResId(mContext, "mercury_loading", "id");
        int cancelId=getResId(mContext,"mercury_cancel","id");


        final EditText cardIdEditText = myLayout.findViewById(cardId);
        final EditText nameEditText = myLayout.findViewById(name);
        final Button loginButton = myLayout.findViewById(loginId);
        final TextView mgsTextView = myLayout.findViewById(mgsId);
        final ProgressBar progressBar = myLayout.findViewById(loadingId);
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
        clicked=false;
        mHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                final String cardId = (String) msg.obj;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);

                        if (id_verify_result.equals("200"))
                        {
                            Toast.makeText(mContext, "验证成功", Toast.LENGTH_SHORT).show();
                            writeFileData("chinese_id",cardId);
                            dialog.dismiss();
                        }
                        else if(id_verify_result.equals("-209"))
                        {
                            showLoginFailed("身份证号码错误");
//                            cardIdEditText.setError("身份证号码错误");
                        }
                        else if(id_verify_result.equals("-208"))
                        {
                            showLoginFailed("身份证名字错误");
//                            cardIdEditText.setError("身份证名字错误");
                        }
                        else if(id_verify_result.equals("-207"))
                        {
                            showLoginFailed("身份证未能通过服务器验证");
//                            cardIdEditText.setError("身份证未能通过服务器验证,请输入正确身份证");
                        }
                        else if(id_verify_result.equals("-202"))
                        {
                            showLoginFailed("该身份证已经被使用");
//                            cardIdEditText.setError("该身份证已经被使用");
                        }
                        else
                        {
                            showLoginFailed("未知错误");
//                            cardIdEditText.setError("未知错误");
                            dialog.dismiss();
                        }
                    }
                },1);
            }
        };
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cancelButton.setVisibility(View.GONE);
//                cardIdEditText.setVisibility(View.GONE);
//                nameEditText.setVisibility(View.GONE);
//                mgsTextView.setVisibility(View.VISIBLE);
//                loginButton.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.INVISIBLE);
//                mgsTextView.setText("实名制认证未完成，为了你的账号安全，请尽快完成实名制认证！");
//                loginButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (mLoginCallBack != null) {
//                            mLoginCallBack.success("实名制认证未完成，为了你的账号安全，请尽快完成实名制认证！");
//                        }
//                        dialog.dismiss();
//                    }
//                });
//            }
//        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clicked==false)
                {
                    clicked=true;
                }
                else
                {
                    return;
                }
                final String cardId = CardIdUtils.UpperCardId(cardIdEditText.getText().toString());
                final String nameId = nameEditText.getText().toString();
                if(!validateParams(cardId,nameId)){
                    LogLocal("[InAppDialog][verify_chinese_id] isValidateParams:"+false);
                    return;
                };
                LogLocal("[InAppDialog][verify_chinese_id] isValidateParams:"+true);
                LogLocal("chinese_id=:" + cardId);
                LogLocal("name_id=:" + nameId);
                LogLocal("account_id=:" + account_id);
                progressBar.setVisibility(View.VISIBLE);
                verify_chinese_id(account_id, cardId, nameId, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        clicked=false;
                        LogLocal("[RemoteConfig][verify_chinese_id] failed=" + e.toString());
                        Looper.prepare();
                        progressBar.setVisibility(View.INVISIBLE);
                        if(!NetCheckUtil.checkNet(mContext)){
                            Toast.makeText(mContext, "网络未连接", Toast.LENGTH_SHORT).show();
                        }
                        Looper.loop();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        clicked=false;
                        String s = response.body().string();
                        if (s != null&&isJSONValid(s)) {
                            JSONObject json = null;
                            try {
                                json = (JSONObject) new JSONTokener(s).nextValue();
                                id_verify_result = (String) json.getString("status");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            local_age = getAgeByIDNumber(chinese_id);
                            LogLocal("[RemoteConfig][verify_chinese_id] remote result=" + s);
                            LogLocal("[RemoteConfig][verify_chinese_id] remote chinese_id=" + cardId);

                            Message msg = new Message();
                            mHandler.sendMessage(msg);
                        }
                        else
                        {
                            Looper.prepare();
                            progressBar.setVisibility(View.INVISIBLE);
                            showLoginFailed("服务器无法被访问");
                            Looper.loop();
                        }
                    }
                });
                // 延时1秒
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
