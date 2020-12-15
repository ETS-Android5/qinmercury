package com.mercury.game.InAppDialog;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.os.Looper;
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

import com.mercury.game.InAppRemote.RemoteConfig;
import com.mercury.game.MercuryActivity;
import com.mercury.game.util.LoginCallBack;
import com.mercury.game.util.MD5Util;
import com.mercury.game.util.MercuryConst;
import com.mercury.game.util.SPUtils;
import com.mercury.game.util.SpConfig;
import com.mercury.game.util.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.mercury.game.InAppRemote.RemoteConfig.chinese_id;
import static com.mercury.game.MercuryActivity.LogLocal;
import static com.mercury.game.util.Function.readFileData;
import static com.mercury.game.util.Function.writeFileData;


public class LoginDialog {
    public  static LoginDialog Instance;

    String oldId;
    int time;
    Activity mContext;
    LoginCallBack mLoginCallBack;
   // final AlertDialog dialog;
    public static int local_age = 0;
private static final int invalidAge = -1; // 非法的年龄，用于处理异常。
    public LoginDialog(Activity context, String id, LoginCallBack callBack) {
        Instance = LoginDialog.this;
        mContext = context;
        oldId = id;
        mLoginCallBack = callBack;
//
  //  AlertDialog.Builder builder = new AlertDialog.Builder(context, getResId(mContext,"singmaan_dialog_style","style"));
        //dialog = builder.create();
       // dialog.setCancelable(false);
      //  Window dialogWindow = dialog.getWindow();
       // dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
       // initAlertDialog(dialog);

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
       // dialog.show();
        //WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
       // lp.gravity = Gravity.CENTER;
       // lp.width = UIUtils.dip2px(mContext, 332);//宽高可设置具体大小
       // lp.height =WindowManager.LayoutParams.WRAP_CONTENT;
       // dialog.getWindow().setAttributes(lp);

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
        /*usernameEditText.setKeyListener(new NumberKeyListener() {
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
        });*/

       /* passwordEditText.setKeyListener(new NumberKeyListener() {
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
        });*/


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
                                showLoginFailed("绑定成功");
                                SPUtils.getInstance().put(SpConfig.USER_PHONE, phone);
                                SPUtils.getInstance().put("USER_PHONE_MD5", MD5Util.getMD5String(phone));
                                loginButton.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                                if (mLoginCallBack != null) {
                                    if (chinese_id.equals("")) {
                                        new IDCardVerifyDialog(mContext, new LoginCallBack() {
                                            @Override
                                            public void success(String msg) {
                                                LogLocal("[InAppDialog][LoginDialog] ID card Success");
                                                writeFileData("chineseid",chinese_id);
                                                age_difference(play_time);
                                                mLoginCallBack.success(phone);
                                            }
                                            @Override
                                            public void fail(String msg) {
                                                LogLocal("[InAppDialog][LoginDialog] ID card failed");
                                            }
                                        });
                                    } else {
                                        //age verify
                                        writeFileData("chineseid",chinese_id);
                                        mLoginCallBack.success(phone);
                                        age_difference(play_time);
                                        LogLocal("[InAppDialog][LoginDialog] ID card got");
                                    }
                                }
               /*                 if (mLoginCallBack != null) {
                                    mLoginCallBack.success(phone);
                                  //  age_difference(play_time);
                                }*/
                                dialog.dismiss();
                            }
                        });
                        builder.setNeutralButton("Failed", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                passwordEditText.setError("Failed");
                                loginButton.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                                dialog.dismiss();
                            }
                        });
                        builder.setCancelable(false);
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

    private int playerAge=0;
    public static String play_time = "";//未成年人已经体验过了多少分钟
    private String set_login_time_result = "";
    public int remaing_minutes=0;//未成年人能体验的总分钟数
    public void age_difference(String Play_time)
    {
        Play_time = play_time;
        local_age = getAgeByIDNumber(chinese_id);
        LogLocal("local_age=:" + local_age);
       // LogLocal("age_difference_play_time=:" + play_time);
        if(play_time == ""){
            RemoteConfig.get_login_time(chinese_id);//分钟
            return;
        }
       // LogLocal("条件判断");
       // local_age = 17;
        if(local_age<18 && local_age>=0)
        {
            long current_time = System.currentTimeMillis();
            String local_time =  readFileData("time"+chinese_id);
            LogLocal("current_time:" + current_time);
            LogLocal("local_time:" + local_time);
            LogLocal("play_time:" + play_time);
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);

            LogLocal("hour=:" + hour);
            Looper.prepare();
            if(hour>=22 || hour<=7)
            {
                timer_quit.start();
            }
            else
            {
                if (play_time != "")
                {
                    remaing_minutes = (90 - Integer.valueOf(play_time));
                    LogLocal("remaing_minutes:" + remaing_minutes);

                    if (remaing_minutes > 0)
                    {
                    if(remaing_minutes > 60)
                        {
                            surTimeFun();//防沉迷30分钟的提示
                        }
                        delayTimeFun();//未成年人只能玩90分钟
                        Toast.makeText(mContext, "未成年人一天只能体验1.5小时，游戏将会准时提示并退出，敬请谅解", Toast.LENGTH_SHORT).show();
//                        timer_delay_param.start();
                    }
                    else
                    {
                        try {
                            RemoteConfig.set_login_time(chinese_id, 90 + "");//保存未成年人玩的时间，只能是90分钟
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setMessage("确认后强制退出");
                            builder.setTitle("未成年人一天只能体验1.5小时游戏，请合理安排时间");
                            builder.setCancelable(false);
                            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((Activity) MercuryActivity.mContext).finish();
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                }
                            });
                            builder.create().show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    //username_deviceid
                    writeFileData("time"+chinese_id, Long.toString(current_time));
                    remaing_minutes = 90;
                    delayTimeFun();
                    //    timer_delay.start();
                    surTimeFun();//防沉迷30分钟的提示
                    Toast.makeText(mContext, "未成年人一天只能体验1.5小时，游戏将会准时提示并退出，敬请谅解", Toast.LENGTH_SHORT).show();
                }
            }
            Looper.loop();
        }else
        {
            LogLocal("年龄已满18岁");
        }
    }

    private int index = 0;
    private void delayTimeFun() {
        CountDownTimer timer_delay_param = new CountDownTimer(1000 * 60 * remaing_minutes, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                LogLocal("remaing_minutes=(" + (millisUntilFinished / 1000) + ")");

                index++;
                if(index %60 == 0){
                    int time = Integer.valueOf(play_time)+(int)(index/60);
                    RemoteConfig.set_login_time(chinese_id, time+"");//分钟

                    LogLocal("----------->set login time-----------play time--------"+time);
                }
            }

            @Override
            public void onFinish() {
                try {
                    RemoteConfig.set_login_time(chinese_id, 90 + "");//保存未成年人玩的时间，只能是90分钟
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("确认后强制退出");
                    builder.setTitle("未成年人一天只能体验1.5小时游戏，请合理安排时间");
                    builder.setCancelable(false);
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((Activity) MercuryActivity.mContext).finish();
                            android.os.Process.killProcess(android.os.Process.myPid());
                        }
                    });
                    builder.create().show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timer_delay_param.start();
    }

    private void surTimeFun(){
        CountDownTimer timer_quit_30 = new CountDownTimer(1000*60*(remaing_minutes - 60), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                LogLocal("30分钟倒计时：" + (millisUntilFinished / 1000));
            }
            @Override
            public void onFinish() {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("您是未成年人，按照有关规定，您今天只能使用90分钟游戏。目前累计时间30分钟。");
                    builder.setTitle("防沉迷提示");
                    builder.setCancelable(false);
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //        ((Activity) MercuryActivity.mContext).finish();
                            //        android.os.Process.killProcess(android.os.Process.myPid());
                        }
                    });
                    builder.create().show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        timer_quit_30.start();
    }

    private CountDownTimer timer_quit = new CountDownTimer(2, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            LogLocal("(" + (millisUntilFinished / 1000) + ")");
        }
        @Override
        public void onFinish() {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("确认后强制退出");
                builder.setTitle("未成年人无法在晚上10点到第二天早上8点进入游戏");
                builder.setCancelable(false);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity) MercuryActivity.mContext).finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                });
                builder.create().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public static int getAgeByIDNumber(String idNumber) {
        String dateStr;
        if (idNumber.length() == 15) {
            dateStr = "19" + idNumber.substring(6, 12);
        } else if (idNumber.length() == 18) {
            dateStr = idNumber.substring(6, 14);
        } else {//默认是合法身份证号，但不排除有意外发生
            return invalidAge;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date birthday = simpleDateFormat.parse(dateStr);
            return getAgeByDate(birthday);
        } catch (ParseException e) {
            return invalidAge;
        }
    }

    public static int getAgeByDate(Date birthday) {
        Calendar calendar = Calendar.getInstance();
        if (calendar.getTimeInMillis() - birthday.getTime() < 0L) {
            return invalidAge;
        }

        int yearNow = calendar.get(Calendar.YEAR);
        int monthNow = calendar.get(Calendar.MONTH);
        int dayOfMonthNow = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.setTime(birthday);

        int yearBirthday = calendar.get(Calendar.YEAR);
        int monthBirthday = calendar.get(Calendar.MONTH);
        int dayOfMonthBirthday = calendar.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirthday;

        if (monthNow <= monthBirthday) {
            if (monthNow == monthBirthday) {
                if (dayOfMonthNow < dayOfMonthBirthday) {
                    age--;
                }
            } else {
                age--;
            }
        }

        return age;
    }
}
