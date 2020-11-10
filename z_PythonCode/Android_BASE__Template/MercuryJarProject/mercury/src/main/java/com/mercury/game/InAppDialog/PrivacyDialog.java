package com.mercury.game.InAppDialog;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.http.SslError;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mercury.game.util.LoginCallBack;
import com.mercury.game.util.UIUtils;

import static com.mercury.game.util.Function.writeFileData;


public class PrivacyDialog {
    Activity mContext;
    LoginCallBack mLoginCallBack;
    final AlertDialog dialog;

    public PrivacyDialog(Activity context) {
        mContext = context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, getResId(mContext, "youlfot_dialog_style", "style"));
        dialog = builder.create();
        dialog.setCancelable(false);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        initAlertDialog(dialog);
        Show();
    }

    public void Show() {
//        String mPhone = SPUtils.getInstance().getString(SpConfig.USER_PHONE);
//        String m5 = SPUtils.getInstance().getString("USER_PHONE_MD5");
//
//
//        if (!TextUtils.isEmpty(mPhone) && TextUtils.equals(m5, MD5Util.getMD5String(mPhone))) {
//            Log.e("mPhone", mPhone);
//            if (mLoginCallBack != null) {
//
//                mLoginCallBack.success(mPhone);
//            }
//            return;
//        }



        dialog.show();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = UIUtils.dip2px(mContext, 332);//宽高可设置具体大小
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
//        dialog.getWindow().setLayout(UIUtils.dip2px(mContext, 332), ViewGroup.LayoutParams.WRAP_CONTENT);


    }

    public int getResId(Context context, String name, String type) {
        return context.getResources().getIdentifier(name, type, context.getPackageName());

    }

    public void initAlertDialog(final AlertDialog dialog) {
        int mainLayout = getResId(mContext, "mercury_dialog_privacy", "layout");
        View myLayout = mContext.getLayoutInflater().inflate(mainLayout, null);
        int mgsId = getResId(mContext, "mercury_mgs", "id");
        int loginId = getResId(mContext, "mercury_login", "id");
        int cancelId = getResId(mContext, "mercury_cancel", "id");




        final TextView textView = myLayout.findViewById(mgsId);

        final Button loginButton = myLayout.findViewById(loginId);
        loginButton.setText("同意");

        final Button cancelButton = myLayout.findViewById(cancelId);

        SpannableString spannableString = new SpannableString("为了保护您的个人信息和隐私安全，根据《个人信息安全规范》的要求，请您在进入游戏前仔细阅读并同意我们的 用户协议隐私条款。");
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.GREEN);
        spannableString.setSpan(foregroundColorSpan, spannableString.length()-9, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                final LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams maskParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                final WebView webview = new WebView(mContext);
                Button button = new Button(mContext);
                button.setText("同意");

                button.setTextColor(Color.parseColor("#000000"));
                button.setBackgroundColor(Color.parseColor("#ffffff"));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (webview != null) {
                            webview.destroy();
                        }
                        linearLayout.setVisibility(View.GONE);
                    }
                });
                ViewGroup.LayoutParams btnParams = new ViewGroup.LayoutParams(
                        UIUtils.dip2px(mContext, 100), UIUtils.dip2px(mContext, 40));
                linearLayout.addView(button, btnParams);

                WebSettings webSettings = webview.getSettings();
                webSettings.setJavaScriptEnabled(true);
                // 如果是图片频道，则必须设置该接口为true，否则页面无法展现
                webSettings.setDomStorageEnabled(true);
                webview.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                        return super.shouldOverrideKeyEvent(view, event);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        return super.shouldOverrideUrlLoading(view, request);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);

                    }

                    @Override
                    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                        handler.proceed();
                    }

                    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                        super.onReceivedError(view, errorCode, description, failingUrl);
//                Toast.makeText(getApplicationContext(), "暂时维护中，请换其他方式支付", Toast.LENGTH_LONG).show();
//                finish();
                    }

                });

                webview.loadUrl("https://www.singmaan.com");
                linearLayout.addView(webview, new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                mContext.addContentView(linearLayout, maskParams);
                writeFileData("privacyagreement","1");
            }
        });


        //设置显示父容器

        dialog.setView(myLayout);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                android.os.Process.killProcess(android.os.Process.myPid());   //获取PID
                System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出
            }
        });



    }

}
