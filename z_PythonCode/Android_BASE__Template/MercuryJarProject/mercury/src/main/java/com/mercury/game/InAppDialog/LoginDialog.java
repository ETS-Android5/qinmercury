package com.mercury.game.InAppDialog;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import com.mercury.game.util.NetCheckUtil;
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
import okhttp3.Response;

import static com.mercury.game.InAppRemote.RemoteConfig.account_id;
import static com.mercury.game.InAppRemote.RemoteConfig.chinese_id;
import static com.mercury.game.InAppRemote.RemoteConfig.id_signe_in_result;
import static com.mercury.game.InAppRemote.RemoteConfig.login_in;
import static com.mercury.game.InAppRemote.RemoteConfig.login_in_result;
import static com.mercury.game.MercuryActivity.LogLocal;
import static com.mercury.game.MercuryActivity.mActivity;
import static com.mercury.game.util.Function.readFileData;
import static com.mercury.game.util.Function.writeFileData;
import static com.mercury.game.util.UIUtils.isJSONValid;


public class LoginDialog {
    public static int local_age = 0;
    //shrinkpartstart
    String oldId;
    int time;
    public  static LoginDialog Instance;
    Activity mContext;
    LoginCallBack mLoginCallBack;
    final AlertDialog dialog;
    private static final int invalidAge = -1; // 非法的年龄，用于处理异常。
    public static String local_account = "";
    public static String local_chinese_id = "";
    public static String isLoginPermitted = "0";
    private Handler mHandler;
    public LoginDialog(Activity context, String id, LoginCallBack callBack) {

        Instance = LoginDialog.this;
        mContext = context;
        mLoginCallBack = callBack;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, getResId(mContext,"mercury_dialog_style","style"));
        dialog = builder.create();
        dialog.setCancelable(false);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        initAlertDialog(dialog);
        local_account = readFileData("account");
        if (local_account.equals(""))
        {
            Show();
        }
        else
        {
            LoginSuccessDialog(local_account);
        }
    }

    public boolean validateParams(String username,String password){
        if (username.equals("") || password.equals("")) {
            Toast.makeText(mContext, "输入不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void onLogin(final ProgressBar progressBar,final String username){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
                switch (login_in_result){
                    case "":
                        Toast.makeText(mContext, "服务器繁忙", Toast.LENGTH_SHORT).show();
                        break;
                    case "-200":
                        Toast.makeText(mContext, "密码错误", Toast.LENGTH_SHORT).show();
                        break;
                    case "-201":
                        Toast.makeText(mContext, "账号不存在", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        writeFileData("account",username);
                        LoginSuccessDialog(username);
                        dialog.dismiss();
                }
            }
        }, 1);
    }
    public void LoginSuccessDialog(final String username)
    {
        Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
        LogLocal("[InAppDialog][LoginSuccessDialog] chinese_id="+chinese_id);
        chinese_id =  readFileData("chinese_id");
        if (mLoginCallBack != null) {
            if (chinese_id.equals(""))
            {
                new IDCardVerifyDialog(mContext, new LoginCallBack() {
                    @Override
                    public void success(String msg) {
                        LogLocal("[InAppDialog][LoginSuccessDialog] ID card Success");
                        age_difference(play_time);
                        mLoginCallBack.success(username);
                    }
                    @Override
                    public void fail(String msg) {
                        LogLocal("[InAppDialog][LoginSuccessDialog] ID card failed");
                    }
                });
            }
            else
            {
                //age verify
                mLoginCallBack.success(username);
                age_difference(play_time);
                LogLocal("[InAppDialog][LoginSuccessDialog] ID card got");
            }
        }
    }
    public void Show() {
        LogLocal("[InAppDialog][LoginDialog] isLoginPermitted"+ isLoginPermitted);
        if (isLoginPermitted.equals("-1")) {
            Toast.makeText(mContext, "该用户不允许登陆", Toast.LENGTH_SHORT).show();
            mLoginCallBack.fail("该用户不允许登陆");
            return;
        }else if(isLoginPermitted.equals("1")){
            Toast.makeText(mContext, "该用户需要绑定", Toast.LENGTH_SHORT).show();
            mLoginCallBack.fail("该用户需要绑定");
            return;
        }
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
//      dialog.getWindow().setLayout(UIUtils.dip2px(mContext, 332), ViewGroup.LayoutParams.WRAP_CONTENT);


    }
    public int getResId(Context context, String name, String type) {
        return context.getResources().getIdentifier(name, type, context.getPackageName());
    }
    @SuppressLint("HandlerLeak")
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
//        usernameEditText.setKeyListener(new NumberKeyListener() {
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

//        passwordEditText.setKeyListener(new NumberKeyListener() {
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
//            }
//
//            @Override
//            public int getInputType() {
//                return 3;
//            }
//        });
        progressBar.setVisibility(View.INVISIBLE);
        mHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                final String username = (String) msg.obj;
                LogLocal("[RemoteConfig][LoginDialog] result:" + login_in_result);
                onLogin(progressBar,username);
            }
        };

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
                final String username = usernameEditText.getText().toString();
                final String password = passwordEditText.getText().toString();
                account_id = username;
                if (!validateParams(username, password)) {
                    LogLocal("[InAppDialog][login_in] isValidateParams:" + false);
                    return;
                }
                ;
                LogLocal("[InAppDialog][login_in] isValidateParams:" + true);
                progressBar.setVisibility(View.VISIBLE);
                    login_in(username, password, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LogLocal("[RemoteConfig][login_in] failed=" + e.toString());
                            Looper.prepare();
                            progressBar.setVisibility(View.INVISIBLE);
                            if (!NetCheckUtil.checkNet(mContext)) {
                                Toast.makeText(mContext, "网络未连接", Toast.LENGTH_SHORT).show();
                            }
                            Looper.loop();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String s = response.body().string();
                            if (s != null && isJSONValid(s)) {
                                JSONObject json = null;
                                try {
                                    json = (JSONObject) new JSONTokener(s).nextValue();
                                    login_in_result = (String) json.getString("status");
                                    json = (JSONObject) new JSONTokener(s).nextValue();
                                    String json_result = (String) json.getString("data");
                                    json = (JSONObject) new JSONTokener(json_result).nextValue();
                                    String json_result1 = (String) json.getString("result");
                                    json = (JSONObject) new JSONTokener(json_result1).nextValue();
                                    String json_result2 = (String) json.getString("chineseid");
                                    chinese_id = json_result2;

                                } catch (JSONException e) {
                                    LogLocal("[RemoteConfig][login_in] JSONException e=" + e.toString());
                                    e.printStackTrace();
                                }
                                writeFileData("chinese_id",chinese_id);
                                LogLocal("[RemoteConfig][login_in] chineseid=" + chinese_id+" ,data = " + login_in_result);
                                LogLocal("[RemoteConfig][login_in] remote result=" + s);
                                Message msg = new Message();
                                msg.obj = username;
                                mHandler.sendMessage(msg);
                            } else {
                                LogLocal("[RemoteConfig][verify_signe_in] server returned formate is not a json");
                            }
                        }
                    });
                    // 延时1秒

            }
        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = usernameEditText.getText().toString();
                new SigneInDialog(mContext, new LoginCallBack() {
                    @Override
                    public void success(String msg) {
                        LogLocal("[InAppDialog][LoginDialog] ID card Success");
                        mLoginCallBack.success(msg);
                    }
                    @Override
                    public void fail(String msg) {
                        LogLocal("[InAppDialog][LoginDialog] ID card failed");
                        mLoginCallBack.success(msg);
                    }
                });
            }
        });

    }




    /*
     * 将时间转换为时间戳
     */
    public String dateToStamp(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(time);
        long ts = date.getTime();
        return String.valueOf(ts);
    }

    /*
     * 将时间戳转换为时间
     */
    public String stampToDate(long timeMillis){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }
    public int remaing_minutes=0;
    private int playerAge=0;
    public String play_time = "";//未成年人已经体验过了多少分钟
    private String set_login_time_result = "";
    public void age_difference(String Play_time)
    {

        Play_time = play_time;
        local_age = getAgeByIDNumber(chinese_id);
        LogLocal("local_age=:" + local_age);
        if(play_time == ""){
            RemoteConfig.get_login_time(chinese_id);//分钟
            return;
        }

        if(local_age<18 &&local_age>=0)
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
        }
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
    private CountDownTimer timer_delay_param = new CountDownTimer(1000*60*(60-remaing_minutes), 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            LogLocal("remaing_minutes=(" + (millisUntilFinished / 1000) + ")");
        }
        @Override
        public void onFinish() {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("确认后强制退出");
                builder.setTitle("根据健康系统限制，由于您是未成年玩家，一天只能只能仅能游戏一小时，您今天已经进行游戏1小时，不能继续游戏，请注意休息");
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
    private CountDownTimer timer_delay = new CountDownTimer(1000*60*60, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            LogLocal("(" + (millisUntilFinished / 1000) + ")");
        }
        @Override
        public void onFinish() {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("确认后强制退出");
                builder.setTitle("根据健康系统限制，由于您是未成年玩家，一天只能只能仅能游戏一小时，您今天已经进行游戏1小时，不能继续游戏，请注意休息");
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
                builder.setTitle("根据健康系统限制，由于您是未成年玩家，每天22:00 ~ 次日8:00无法登陆游戏，请注意游戏");
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

        // calendar.before()有的点bug
//    if (calendar.before(birthday)) {
//      return invalidAge;
//    }
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
