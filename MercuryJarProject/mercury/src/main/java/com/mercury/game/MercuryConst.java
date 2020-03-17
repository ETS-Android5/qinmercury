package com.mercury.game;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.http.params.CoreConnectionPNames;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.mercury.game.inApp.InAppBase;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.InputFilter;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;


public class MercuryConst {
	public static String LogVERSION= "1.1";
	public static final int ChinaNull = 0;
	public static final int ChinaMobile = 1;
	public static final int ChinaUnicom = 2;
	public static final int ChinaTelecom = 3;
	
	public static final int China360 = 4;
	public static final int ChinaBaidu = 5;
	public static final int ChinaMi = 6;
	public static final int ChinaUC = 7;
	public static final int ChinaAnzhi = 8;	
	public static final int ChinaOppo = 9;	
	public static final int ChinaTencent = 10;	
	public static final int ChinaVivo = 11;	
	public static final int Chinajl = 12;
	public static final int Chinals = 13;
	public static final int Chinawdj= 14;
	public static final int Chinamz=15;
	public static final int Chinac4399=16;
	public static final int Chinachw=17;
	public static final int Chinalxlsd=18;
	public static final int Chinalxyx=19;
	
	public static final int Chinameitu=20;
	public static final int Chinaceast2west=21;
	public static final int Chinamzw=22;
	public static final int Chinadl=23;
	public static final int Chinayyh=24;
	public static final int Chinataptap=25;
	public static final int Chinakp=26;
	public static final int Chinatxyyb=27;
	public static final int Chinasamsung=28;
	public static final int Chinaaqy=29;
	public static final int Chinae2wwk=30;
	public static final int Chinabilibili=31;
	public static String TAG = "IAP";	
	public static String APPChineseName = "投影寻真";	
	public static String appversion="1.9";
	public static String CarriersID="";
	public static String SDKID="";
	public static String CarriersPayLock="0";
	public static String SDKPayLock="0";
	public static String APPID="";
	public static String APPKEY="";
	public static String ADChannel="";
	public static String SDKPAY="";
	public static String ChannelPid="";
	public static String QinPid="";
	public static String Qindesc="";
	public static String UserName="";
	public static String PicUrl="";
	public static float Qinpricefloat=0f;
	public static int Unity=1001;
	public static int Cocos2D=1002;
	public static String ShowAMID="c43f63de081c2480da2813243bff4244";
	public static String ShowBDID="";
	public static String ShowCSID="";
	public static String ShowJLID="";
	public static String ShowMZID="25315607474616";
	public static String ShowTXID="";
	public static String ShowXMID="2882303761517409754";
	public static String ShowYMID="";
	
	//wechat and alipay
    public static String WX_APP_ID = "wxed748a1af0561fd4"; 
	public static String WX_MCH_ID = "1489242192";	
	public static String WX_API_KEY = "53236073337180470952250400959808"; 	
	public static String ALI_PARTNER = "2088511798626863";
	public static String ALI_SELLER = "2088511798626863";	
	public static String ALI_RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKTrA5p/o++3KydmC66AY3V0fj1evRZEbEAEwETl1ISBzmK1kEQLnVtnwq2fFbUPWsJM49WktIVCqJyAQBZ4oQCS1w+bAZxSdz4cISr98OI5xwOXJT9uDhOQOOp2qyq11RNSrEfZppT19X29aZ86aPW92EFqitngHAk8f7cGXwDHAgMBAAECgYBhpEuD4+KaASbpsr9j19wLJKfBiiAF2QkGdkmoATNsKLabNshtoTrPbYWT6kad5rNjqsaSLWw8IhVWY5COPWmEy95Bwt/4Ted1wCu+dspibOe0zdeA8BVEMbA9GjXkNVxKb9n1oacSUtyEkSglRE9tNsY3LnFPwFkm/AMIqyGSUQJBANbhdiHybiW9dk+0B1xHzMW9QoEDUcPtZI5rmMPPHzNtkUbnH9dnB+VE7ompSroXi8E1xxQ7TYQNB9cazVjY4MUCQQDEef1GHUqcdtSPjAnD754HOsodbW0X4kt+6oBu3xBbYgHGSBh7CE2LQRp0BTgrC8yen2dYTMoJRBgY6iqmXtwbAkBnezKRzJdA84nrfk5hIW6695b0XG3fBg78C1MJUVC8SpLA64NJD6QcxGJ/xxhmn/o8tLJHyvtckY3qCE1F8UPlAkEAkshUKEp/0C6SlH9ZWFEubVZFYwC6LMq5/iIxOyNYs/yfOMPpzhig3fUQTzcLBFW3U5Xg/j23/n4pxotCu7JImwJBALPNI6/lr824upEK4p/2he0LqDWIQIOMmqHgMbnwkONAHrjaMWxUA9/b0GPy+9mmUhQcvVhIYja3kfzkRAekrQY=";
	public static String ALI_RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	//exchange
	public static String appid = "Shadowmatic";
	public static String accesstoken = "3e6a0bdd0b0a73d8ae01f3b4bf0750fd";
	
	//Newexchange
	public static String New_appid = "6401";
	public static String New_accesstoken = "3e6a0bdd0b0a73d8ae01f3b4bf0750fd";
	
	//huawei
	public static String PAY_ID="900086000020107215";
    public static String BUO_SECRET = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCkTf5n+SZQVI2/4HGw9xewR7FeCjXMU76w3iYeZNrej18iCfc+qpgrVd4Ptx49utqNML6lfGcTePkry7oXtQi0oUIb1A2pXikXVrWC7qL0UekzC868Gmi1DXiGxWyGK6Z1X7yZN3YoWN4pSaLJDhumDen6Oronzmre8sZ+WHDYssSQqXPiM32KnGlXR/hrh68F1Jx8raHS4C5q98LaqzGPWHjedzwFYhCN3KRk2Akj3gB6/SEAvkeK1qdB60rYblRNeeJg03YXGdLz/DgozEFzI06uEwLXfcmToJoSrCjQyIZiSDdQhPfrbZGl/pr3jinTlod7LFAMMJcfsz0nf0lvAgMBAAECggEAYpZZ/NFG6BSUKXnnPiRFloSuLJs7xKrLVR03Ci3CFqE4mBgavwNl4zrWz980yh2AXg/NlUacItcHy/umYetCmf/YxxHqUKIrTdG9gB7osGkl4HdJSR0fjuPSWivD/o3ah7s2U0kwIO2SAHwqrTFF7dGTf44VfSUigzxxVgaDT8KehEpUV752IKitVhctwHVIL+CQEz/HXrXQmjpnjLrnjdVwyZt+HtHtZL628kizl1PJgFjCCbMm5Lh7y/rZx1S86I5tG+j6HpXpOXHKhwi5PyTTGT4F+6ks3xCTmeSoNi+UsuUsBG5Em/+6846Zb2R0lKQx19i7i+y/dEp4SM32gQKBgQDPWM4l3NtkWfz9p6vDHbno1wD5S+umLoWDBmgAA1JquWuWUwNNgkOB0mYKyxKweWozZtpZ4zxDpipfevjyUCO2qegrF9g/r2aCoA1z482sRwSt1/Jech7JfW4uF5yM1VCeUBMmuExPu6XcHrqmSJvrQK2t1Ocit/q3Kyb5h/EFUQKBgQDK26tVtxn7Co9XR1D6vf2hsXcDYjwes6h0qAMHaQ0Ti38OjwS+CVsMGQz1GVfwlV0qt8h0jV5Iy88KXyI42nvNzf4pe02dvCXxhSejbXcy0eCWEYSCyxbQU+Bg0ckoPd82+4Oig0Us7JgxWoS8rysXEF2ZBItpPi26c2cTPtqyvwKBgQCQ0gf6Lg4WQzwOtqOjPgnxdOo7NSm8AlZCM6FEEQ3peOSKNCTkaA1aiWe4NioxlDv29umfSrV20oJZ+fwS0qJ/HKEKKDdkE9BXgA6lWQD4SQodmeywxqh2NuNT0i4Ht539VRRrQOIb9oZb/iKDrccpQx2Lgl6Fw1abxMoOE0LmYQKBgQCm01BsBVCQa3bGSBgInQZgWxmM5tSaSxVWGnWjOnlOgYHT7znvLrJ5mIBXcKgpaixcXRe1Ai1voGx8ExCJwOPftHA4nPaPqZNqtB6NABDu8jrIL0/SYEI6wT/dX6kWNwvCo8T1eQ8Ciu/+ZnqS0VuWdBcFJ/+eZUwl8ZHq+d3Q8QKBgQDAVVWr2IH4aRtHyQigeexVg650RbcmhkjsSefRhT3tczFa85OCFL1DebmIFUNwUSeQQx/r2ArFH/jrs+EB+1i97nBzhTuCivE9uH0SOJKpBhUZzjpYsxEFq35KJEGXoZ4g0jZW/yuNfHB9XFfH2bwQxjK+4lUKuaJK8HgP/QcmdQ==";
    public static String PAY_RSA_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCkqFML+cLnLbvrnNZ0pKkjRahOUoJJ6AjQkNhaKvvTjy/fxLiAeNxn/2IdgKHR8mpU0yYYPcsRZyDdn80CtAkP+mAw3778pf7dQxFmGA8iOUBw8vkezSzzeXgR7gnji8iqgiRoHYg7zMkgJD+Ru4qzk0ClMdYU2xbRloV+NzHYeUJNmJekLOhTBekt6/L2MZdoQ3AahXrvtJf2Dz9qq3Aj29eFaeDpRscJ37kkhTRJvmMg1HdLvr6P+QUNdb4pEJA8hwLWQhYbGkyKW0ZEXyRMzeaKF1mC23geewNhYOZg+KnMZhuuo53TsFNu0+C6zsAO+nYC3bXm2srrdkHpvFupAgMBAAECggEAG1H6hZApcKY7+foJ80ym671REb3K9GxVmhWOcFeVdSLa9Kv1WQu0tmS/aNnA/87TKcPnHDqUiQytfVhYccxhfI80iqd1TwJT6W6JYoE3cZxCPvtxrlSo0rUi4Qa4CVNWNkRlugi8Y/+0l2qsSYfumaEjJsXAGbHIdXoeKDUMRjiDsBvCZmqN9SQe6ivPp8uySV5soctQSrd9WU+kOPmJFGxgb15pfSbYgiX6tQeHBc2QT3m0CnZ0S+C1fNonfUUVu9DiuQyDH5TtkOOsvhavNYxFDWtPWwGv3tpL4ELlH6emvn/TvR+EQz3x5A+jCRYVRIv6HeaNJQpkNiKchgWqoQKBgQDrD+KqXfvOjZ617MCIh/fmkVU5LseC5QU130SdoK0ZFLQnDKgLsYxxYJI3i46/GL+WU8s/uRbNuYZDmyKimpzbTu5cfbfHWCm71tyB4gnZ/NqWISAJIiyW43v0xnUTFcIZQ6fRZ5vKmbb41o0NGse9p+lNCO/GcKWHpfXV2mMJnQKBgQCzUwGIdiYS83NAqrdzMpWNH80RG0yKJSbh/+KP4d8HBBpYGXwUgomoLXVR4HqfoWuEH+eoMh7b7Jn+AgktLgJBgIiIuydg4wziHXQuhNAOjmWrPnCiHAJLHIdNo3jz9SvF1KFr67QfcFoiL+abS2/P0Zcx4NFIgjnA4Z2NVYAyfQKBgQDRY24rCttC7L6DGimLtWrrlWyQKo6ImA/jJEaQD1ACdoHgAxMyj28mk7rMBeIbciU/+Nalpe+dRHVIhcn9hLLTXOVRuJ9b0LokXEpC3KKf7vCcKVfLKwkDRBLoQVyKqqgjLzQvghayC+mEkkJFeSSSWh+lgp9sam3ZfsmVPRtZtQKBgARDVuf9lw4gR38kD9RnQXLnbKKZeYpXM8Nvp90vy3OJP28UvARozgj3e7CEm6Wr2rh7YbB38I/d4hNNzRYL3/XarmRBvr/o5eeCN77bXW7bJ8OYamkQLWXHude4qSlzjKTZVzUPl2qf6ySg6uSKOJBCNUv0QkVtAx54vpwbij6pAoGAaHtiED7pDqQubIr+MVovNJXqmYryokEfTd3CbLek0Rc2XnWWzPYvALBNkrO/0pCJprFP4u/B7pjt0eUqjwHd1m4Oap4vVROF+BGUAFlwLaz3iQPKAdWxy+ZduwoGZUjVoTNS2D8bQVH+yDtCoeYymW/2yF+S6c+++dNtr/3CcLM=";
    public static String PAY_RSA_PUBLIC =  "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApKhTC/nC5y2765zWdKSpI0WoTlKCSegI0JDYWir7048v38S4gHjcZ/9iHYCh0fJqVNMmGD3LEWcg3Z/NArQJD/pgMN++/KX+3UMRZhgPIjlAcPL5Hs0s83l4Ee4J44vIqoIkaB2IO8zJICQ/kbuKs5NApTHWFNsW0ZaFfjcx2HlCTZiXpCzoUwXpLevy9jGXaENwGoV677SX9g8/aqtwI9vXhWng6UbHCd+5JIU0Sb5jINR3S76+j/kFDXW+KRCQPIcC1kIWGxpMiltGRF8kTM3mihdZgtt4HnsDYWDmYPipzGYbrqOd07BTbtPgus7ADvp2At215trK63ZB6bxbqQIDAQAB";
    public static String LOGIN_RSA_PUBLIC ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApE3+Z/kmUFSNv+BxsPcXsEexXgo1zFO+sN4mHmTa3o9fIgn3PqqYK1XeD7cePbrajTC+pXxnE3j5K8u6F7UItKFCG9QNqV4pF1a1gu6i9FHpMwvOvBpotQ14hsVshiumdV+8mTd2KFjeKUmiyQ4bpg3p+jq6J85q3vLGflhw2LLEkKlz4jN9ipxpV0f4a4evBdScfK2h0uAuavfC2qsxj1h43nc8BWIQjdykZNgJI94Aev0hAL5HitanQetK2G5UTXniYNN2FxnS8/w4KMxBcyNOrhMC133Jk6CaEqwo0MiGYkg3UIT3622Rpf6a944p05aHeyxQDDCXH7M9J39JbwIDAQAB";
    public static String APP_ID = "100090525";

    //samsung
    public static  String samsungappid = "5001039714";
	public static  String samsungprivateKey = "MIICXAIBAAKBgQCet1yf4kQOnqSLaEkmTHTdf8ZwPYd1zjMuSORraT6Ira5BZtWa5iRCOE8d0xIGoz65BN+jth8NuFf0thrcCx9Rl4TQw/ABDQl0OKd/Pd+c+92M+hgc3JbN6zK2Zi7ahZQuSDWEgCBoLsOQoTIW8dEBcQpmLci98elbGFcyb/TLZwIDAQABAoGAI9PsesMO7eXt6vJNoPGdZ+rSFsAs9fDoKMiXl4+YaC4cqfLWwTK2eKRRZ8Afz5PGMungugoAlI5KfJknEwkHtn4QeER9LLjgC//9FZ3ZY7m//5eJJQJDXRtDCJfRWvRe+gF7gIQbz34ocQ4Ogp1BV4MgqtPbqXkN9PViGcaneYECQQDbohfAibGPHiMacW0GAqRW7oJ7ljdeEgzYFfuerR0PgboHpShiODwQz/3ys3jkNIAtqoubL5rwu9x/OE+LrD+hAkEAuP8YlfE1ZWcqfoIc9yCsUAJLdm5MbjYiYh8NB3/BB9hFPulE+GuNtr/qeZ7B17EeTxqIk2K+Wa6Mw7SeJVxOBwJAA+k0ARKynbjMqTjh1xyO7plaq3a2T6+EmQhzLZUpklAeDurK+EzGDQqXHA5EFdQ1gBqsV7wWRzkhKQbIq9UKYQJAWqOx+hU6ojpP2bgKVRLPGkxliNs123HumsC384qmmx9dnhtQzZ50yqtxSLF+LB39yagobn4c4XfiRCkEqy10lQJBAMHo/e9fbaqPvMw9kfokuvA75sV3+Xps26cEEMJaHbYtlHxvAaEXIiqEaYib508Tziu7xtj9rBH7QxZspvfsmLE=";
	public static  String samsungpublicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCC1XZ4ds9JPCHwaW4wsX3oGPZ5C2a1usK6YbYZzrfftCRJ/PBptMieT4RN0dEmmHytreaa9jYc7fvsGmW+4sFuotbjBtPu18OGhcR40QzvaGlRLE9hthYEnS2v6JlFnQ/qoBN6RAIn7Wsa4UDKDcuSCQsulCyl4j7gKeWjQ3QYlQIDAQAB";
	public static  String samsungCLIENT_ID="";
	public static  String samsungCLIENT_SECRETE="";

	
    //server
    private static String ServerPid ="";
    private static String ServerPid1 ="";
    private static String ServerPid2 ="";
    private static String ServerPid3 ="";
    private static String ServerPid4 ="";
    private static String ServerPid5 ="";
    private static String ServerPid6 ="";
    private static String ServerPid7 ="";
    private static String ServerPid8 ="";
    private static String ServerPid9 ="";
    private static String ServerPid10 ="";
    private static String ServerPid11 ="";
    private static String ServerPid12 ="";
    private static String ServerPid13 ="";
    private static String ServerPid14 ="";
    private static String ServerPid15 ="";
    
    private static String ServerPrice ="";
    private static String ServerPrice1 ="";
    private static String ServerPrice2 ="";
    private static String ServerPrice3="";
    private static String ServerPrice4="";
    private static String ServerPrice5="";
    private static String ServerPrice6="";
    private static String ServerPrice7="";
    private static String ServerPrice8 ="";
    private static String ServerPrice9 ="";
    private static String ServerPrice10 ="";
    private static String ServerPrice11 ="";
    private static String ServerPrice12 ="";
    private static String ServerPrice13 ="";
    private static String ServerPrice14 ="";
    private static String ServerPrice15 ="";

    private static String ServerDes  ="";
    private static String ServerDes1 ="";
    private static String ServerDes2 ="";
    private static String ServerDes3="";
    private static String ServerDes4="";
    private static String ServerDes5="";
    private static String ServerDes6="";
    private static String ServerDes7="";
    private static String ServerDes8 ="";
    private static String ServerDes9 ="";
    private static String ServerDes10 ="";
    private static String ServerDes11 ="";
    private static String ServerDes12 ="";
    private static String ServerDes13 ="";
    private static String ServerDes14 ="";
    private static String ServerDes15 ="";
	public static String exchangeID;
	public static String Restoreappid = "letscreatepottery";
    
    
	
    public static String[] convertStrToArray(String str,String symbol){   
        String[] strArray = null;   
        strArray = str.split(symbol); //拆分字符为symbol 可以是 "," ,然后把结果交给数组strArray 
        return strArray;
    }

    public static void AnalysisID(String IDString)
    {
    	String[] strArray=null;    	 
    	strArray = convertStrToArray(IDString,",");
    	if(strArray.length==1)
    	{
    		CarriersID=strArray[0].toString();
    	}
    	else if(strArray.length==2)
    	{
    		CarriersID=strArray[0].toString();
    		SDKID=strArray[1].toString();
    	}
    	else if(strArray.length==3)
    	{
    		CarriersID=strArray[0].toString();
    		SDKID=strArray[1].toString();
    		CarriersPayLock=strArray[2].toString();
    	}
    	else if(strArray.length==4)
    	{
    		CarriersID=strArray[0].toString();
    		SDKID=strArray[1].toString();
    		CarriersPayLock=strArray[2].toString();
    		SDKPayLock=strArray[3].toString();
    	}   	
    }
    public static void GetChannelID(String ChannelName)
    { 
    	//AddCodeChannelSettingPython
		APPID = "155309";
		APPKEY = "";
		ADChannel="";
		SDKPAY="0,1";
		//end		
    }
   



    public static void PayInfo(String SavePid)
    {
		MercuryConst.CarriersPayLock="0";
		MercuryConst.SDKPayLock="1";
    	  String  ydpid="";
    	  String  ltpid="";
    	  String  dxpid="";
    	  String  desc="";
    	  float  pricefloat=10f;
    	  String  strProductId="";
		  switch (SavePid)
	      {
	          case "production1":		ydpid = "001"; ltpid = "001"; dxpid = "TOOL1";   desc = "解锁全部关卡"; pricefloat = 20.0f;  ServerPid=ServerPid1;   ServerPrice=ServerPrice1;ServerDes=ServerDes1;break;
	          case "production2":		ydpid = "002"; ltpid = "002"; dxpid = "TOOL2";   desc = "2200点券";    pricefloat = 35.0f;   ServerPid=ServerPid2;	ServerPrice=ServerPrice2;ServerDes=ServerDes2;break;
	          case "production3":		ydpid = "003"; ltpid = "003"; dxpid = "TOOL3";   desc = "5000点券";    pricefloat = 65.0f;  ServerPid=ServerPid3;	ServerPrice=ServerPrice3;ServerDes=ServerDes3;break;
	          case "production4":		ydpid = "004"; ltpid = "004"; dxpid = "TOOL4";   desc = "11000点券";   pricefloat = 130.0f;   ServerPid=ServerPid4;	ServerPrice=ServerPrice4;ServerDes=ServerDes4;break;
	          case "production5":		ydpid = "005"; ltpid = "005"; dxpid = "TOOL5";   desc = "30000点券";   pricefloat = 330.0f;  ServerPid=ServerPid5;	ServerPrice=ServerPrice5;ServerDes=ServerDes5;break;
	          case "production6":		ydpid = "006"; ltpid = "006"; dxpid = "TOOL6";   desc = "70000点券";   pricefloat = 660.0f;  ServerPid=ServerPid6;	ServerPrice=ServerPrice6;ServerDes=ServerDes6;break;
	          case "production7":		ydpid = "007"; ltpid = "007"; dxpid = "TOOL7";   desc = "命运女侠";     pricefloat = 20.0f;  ServerPid=ServerPid7;	ServerPrice=ServerPrice7;ServerDes=ServerDes7;break;
	          case "production8":		ydpid = "008"; ltpid = "008"; dxpid = "TOOL8";   desc = "风刃";        pricefloat = 20.0f;  ServerPid=ServerPid8;	ServerPrice=ServerPrice8;ServerDes=ServerDes8;break;
	          case "production9":		ydpid = "009"; ltpid = "009"; dxpid = "TOOL9";   desc = "虚空猎手";     pricefloat = 20.0f;  ServerPid=ServerPid9;	ServerPrice=ServerPrice9;ServerDes=ServerDes9;break;
	          case "production10":		ydpid = "010"; ltpid = "010"; dxpid = "TOOL10";  desc = "超能博士";     pricefloat = 30.0f;  ServerPid=ServerPid10;	ServerPrice=ServerPrice10;ServerDes=ServerDes10;break;
	          case "production11":		ydpid = "011"; ltpid = "011"; dxpid = "TOOL11";  desc = "达尔文";       pricefloat = 30.0f;  ServerPid=ServerPid11;	ServerPrice=ServerPrice11;ServerDes=ServerDes11;break;
	          case "production12":		ydpid = "012"; ltpid = "012"; dxpid = "TOOL12";  desc = "铁甲马克";     pricefloat = 45.0f; ServerPid=ServerPid12;	ServerPrice=ServerPrice12;ServerDes=ServerDes12;break;
	          case "production13":		ydpid = "013"; ltpid = "013"; dxpid = "TOOL13";  desc = "解锁游戏";     pricefloat = 30.0f; ServerPid=ServerPid13;	ServerPrice=ServerPrice13;ServerDes=ServerDes13;break;
	          case "production14":		ydpid = "014"; ltpid = "014"; dxpid = "TOOL14";  desc = "桂英";         pricefloat =  18.0f; ServerPid=ServerPid14;	ServerPrice=ServerPrice14;ServerDes=ServerDes14;break;
	          case "production15":		ydpid = "015"; ltpid = "015"; dxpid = "TOOL15";  desc = "自由猎人";     pricefloat = 30.0f; ServerPid=ServerPid15;	ServerPrice=ServerPrice15;ServerDes=ServerDes15;break;
	          case "production16":		ydpid = "016"; ltpid = "016"; dxpid = "TOOL16";  desc = "自由猎人";     pricefloat = 30.0f; ServerPid=ServerPid15;	ServerPrice=ServerPrice15;ServerDes=ServerDes15;break;
	          
	          default:
	              break;	
	      }

		  switch (MercuryApplication.mSimOperatorId)
	      {
	          case 1: strProductId = ydpid; break;
	          case 2: strProductId = ltpid; break;
	          case 3: strProductId = dxpid; break;
	          default:
	              strProductId = ydpid;break;
		  }
		  if(!ServerPid.equals(""))
		  {
			  ChannelPid=ServerPid;
		  }
		  if(!ServerPrice.equals(""))
		  {
			  pricefloat= Float.parseFloat(ServerPrice);
		  }
		  if(!ServerDes.equals(""))
		  {
			  desc=ServerDes;
		  }
		  QinPid = strProductId + ","+ChannelPid+"," + SDKPAY;
		  Qindesc= desc;
		  Qinpricefloat=pricefloat;
		MercuryActivity.LogLocal("[MercuryConst PayInfo] QinPid="+QinPid+" Qindesc="+Qindesc+" Qinpricefloat="+Qinpricefloat);
    }
    
	public void FunctionL(String number)
	{
		MercuryActivity.isLogOpen=number;
	}
	public void ExitGame()
	{
		MercuryActivity.LogLocal("[MercuryConst] MercuryApplication.mSimOperatorId="+MercuryApplication.mSimOperatorId);
		((Activity) MercuryActivity.mContext).finish();
		android.os.Process.killProcess(android.os.Process.myPid());
//		if(MercuryApplication.iscarriersneed.equals("open")&&MercuryApplication.mSimOperatorId!=MercuryConst.ChinaMobile)
//		{
//			MercuryActivity.LogLocal("[InAppBase] Android Exit With Mobile Code");
//			// exit
//			AlertDialog.Builder builder = new Builder(MercuryActivity.mContext);
//			builder.setMessage("确认退出吗?");
//			builder.setTitle("提示");
//			builder.setPositiveButton("确认", new OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					((Activity) MercuryActivity.mContext).finish();
//					android.os.Process.killProcess(android.os.Process.myPid());
//				}
//			});
//			builder.setNegativeButton("取消", new OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					dialog.dismiss();
//				}
//			});
//			builder.create().show();
//		}
//		else
//		{
//			MercuryActivity.LogLocal("[MercuryConst] Kill Process");
//			((Activity) MercuryActivity.mContext).finish();
//			android.os.Process.killProcess(android.os.Process.myPid());
//		}
	}
	
	public void onPurchaseSuccess(String message,InAppBase inbase,String mProductId) {
		MercuryActivity.LogLocal("[MercuryConst] onPurchaseSuccess callback->strProductId="+mProductId+" message->"+message+" inbase->"+inbase+" MercuryApplication.msg="+MercuryApplication.getExtSDKId());
		if(message!="3"||"exchange"!=message)
		{
			
		}
		else
		{
			MercuryActivity.LogLocal("[MercuryConst] onPurchaseSuccess");
		}
		if(MercuryApplication.getExtSDKId()==MercuryConst.ChinaTencent)
		{
			MercuryActivity.LogLocal("[MercuryConst] onPurchaseSuccess->wxgame:"+message);
			//UnityPlayer.UnitySendMessage("DontDestroy_Qin", "BuySuccess", message);
		}
		else
			inbase.appinterface.onPurchaseSuccessCallBack(message);
	}
	public void onPurchaseFailed(String strError,InAppBase inbase,String mProductId) {
		MercuryActivity.LogLocal("[MercuryConst] onPurchaseFailed callback->strError="+strError+" inbase->"+inbase+" MercuryApplication.msg="+MercuryApplication.getExtSDKId());
		
		if(MercuryApplication.getExtSDKId()==MercuryConst.ChinaTencent)
		{
			MercuryActivity.LogLocal("[MercuryConst] onPurchaseFailed->wxgame:"+strError);
			//UnityPlayer.UnitySendMessage("DontDestroy_Qin", "BuyFail", strError);
		}
		else
			inbase.appinterface.onPurchaseFailedCallBack(strError);
	}
	public void onPurchaseCanceled(String strError,InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] onPurchaseCanceled callback->strError="+strError+" inbase->"+inbase+" MercuryApplication.msg="+MercuryApplication.getExtSDKId());
		
		if(MercuryApplication.getExtSDKId()==MercuryConst.ChinaTencent)
		{
			MercuryActivity.LogLocal("[MercuryConst] onPurchaseFailed->wxgame:"+strError);
			//UnityPlayer.UnitySendMessage("DontDestroy_Qin", "BuyFail", strError);
		}
		else
			inbase.appinterface.onPurchaseCancelCallBack(strError);
	}
	public void onLoginSuccess(String strError,InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] onLoginSuccess callback->strError="+strError+" inbase->"+inbase);
		
		inbase.appinterface.onLoginSuccessCallBack(strError);	
	}
	public void onLoginCancel(String strError,InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] onLoginCancel callback->strError="+strError+" inbase->"+inbase);
		inbase.appinterface.onLoginCancelCallBack(strError);
	}
	public void onLoginFailed(String strError,InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] onLoginFailed callback->strError="+strError+" inbase->"+inbase);
		inbase.appinterface.onLoginFailedCallBack(strError);	
	}
	public void onFunctionCallBack(String strError,InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] onFunctionCallBack callback->strError="+strError+" inbase->"+inbase);
		inbase.appinterface.onFunctionCallBack(strError);	
	}
	public void QinUnityMessage(String ObjectName,String MethodName,String QinMessage)
	{
		if(MercuryActivity.Platform==MercuryConst.Unity)
		{
			//UnityPlayer.UnitySendMessage(ObjectName,MethodName,QinMessage);
		}
	}
	public void ShareResult(int result )
	{
		if(MercuryActivity.Platform==MercuryConst.Unity)
		{
			MercuryActivity.LogLocal("[MercuryConst Unity] ShareResult->"+result);
			//UnityPlayer.UnitySendMessage("DontDestroy_Qin","ShareResult",result+"");
		}
		else
		{
			
		}
	}
	public void Exchange(String text) {
		// TODO Auto-generated method stub
		if(MercuryActivity.Platform==MercuryConst.Unity)
		{
			MercuryActivity.LogLocal("[MercuryConst Unity] Exchange->"+text);
			//UnityPlayer.UnitySendMessage("DontDestroy_Qin", "ExchangeString", text);
		}
		else
		{
			
		}
	}
	public void Exchange(final InAppBase inbase) {
		// TODO Auto-generated method stub
		MercuryActivity.LogLocal("[MercuryConst Unity] Exchange->"+inbase);
		MercuryActivity.LogLocal("[MercuryConst Unity] Exchange->Android");
		final EditText inputServer = new EditText(((Activity) MercuryActivity.mContext));
		inputServer.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
		AlertDialog.Builder builder = new AlertDialog.Builder(((Activity) MercuryActivity.mContext));
		builder.setTitle("兑换中心").setView(inputServer).setNegativeButton("取消", null);
		builder.setPositiveButton("确定",new DialogInterface.OnClickListener() 
		{
					public void onClick(DialogInterface dialog,
							int which) {
						new Thread(new Runnable() 
						{
							
							@Override
							public void run() 
							{
								// TODO Auto-generated method stub

								String text = inputServer.getText().toString();
								String prKey = New_accesstoken+text;
								String sign = MD5(prKey);
								String list = "appid="+New_appid+"&cdkey="+text+"&sign="+sign;
								
								String log = postDownloadJson("http://101.200.214.15/wk/e2wcdkey/public/index.php/createcdkey/index/use_key ",list);		
								JSONTokener jsonParser = new JSONTokener(log);
								JSONObject Parameter;
								try 
								{
									Parameter = (JSONObject) jsonParser.nextValue();
									String errorcodeString = Parameter.getString("code");
									Log.e("IAP","errorcodeString="+errorcodeString);
									if(errorcodeString.equalsIgnoreCase("0"))
									{												
										
										JSONArray jArroy = Parameter.getJSONArray("msg");
										int nNum = jArroy.length();
					
										for(int i= 0;i<nNum;i++)
										{
											String strkey = jArroy.getString(i);
									        JSONObject jsonObject = new org.json.JSONObject(strkey);  
									        Iterator iterator = jsonObject.keys();  
									        while(iterator.hasNext())
									        {  
									          String key = (String) iterator.next();  
									          String num = jsonObject.getString(key);
									          int keyNum = Integer.parseInt(num); 
									          for(int j=0;j<keyNum;j++)
									          {
									        	  inbase.appinterface.onFunctionCallBack(key);
									          }
									        } 
										}			
										
									}
									else
									{
										inbase.appinterface.onFunctionCallBack("ExchangeFailed");
									}
								} 
								catch (JSONException e) 
								{
									
									// TODO Auto-generated catch block
									inbase.appinterface.onPurchaseFailedCallBack("");
									e.printStackTrace();
								}
							}
						}).start();
						
					}
				});
		builder.show();		
	}
	private static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            
        }
        return result;
    }
	public static String postDownloadJson(String path,String post){
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            printWriter.write(post);//post的参数 xx=xx&yy=yy
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while((len=bis.read(arr))!= -1){
                bos.write(arr,0,len);
                bos.flush();
            }
            bos.close();
            return bos.toString("utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
	public static String sendPost(String strUrl, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		Log.e("IAP", param);
		try {
			URL realUrl = new URL(strUrl);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			Log.e("IAP","发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
}
