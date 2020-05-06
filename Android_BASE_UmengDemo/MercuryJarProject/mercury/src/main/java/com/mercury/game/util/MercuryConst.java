package com.mercury.game.util;

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

import com.mercury.game.util.InAppBase;
import com.mercury.game.MercuryActivity;
import com.mercury.game.MercuryApplication;
import com.umeng.analytics.game.UMGameAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.InputFilter;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import static com.mercury.game.MercuryApplication.OpenUmeng;


public class MercuryConst {
	public static String LogVERSION= "1.1";
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



    public static void PayInfo(String SavePid)
    {
    	  String  ydpid="";
    	  String  ltpid="";
    	  String  dxpid="";
    	  String  desc="";
    	  float  pricefloat=1f;
		  switch (SavePid)
	      {
	          case "production1":		desc = "解锁全部关卡"; pricefloat = 20.0f;  break;
	          case "production2":		desc = "2200点券";    pricefloat = 35.0f;  break;
	          case "production3":		desc = "5000点券";    pricefloat = 65.0f;  break;
	          case "production4":		desc = "11000点券";   pricefloat = 130.0f; break;
	          case "production5":		desc = "30000点券";   pricefloat = 330.0f; break;
	          case "production6":		desc = "70000点券";   pricefloat = 660.0f; break;
	          case "production7":		desc = "命运女侠";     pricefloat = 20.0f;  break;
	          case "production8":		desc = "风刃";        pricefloat = 20.0f;  break;
	          case "production9":		desc = "虚空猎手";     pricefloat = 20.0f;  break;
	          case "production10":		desc = "超能博士";     pricefloat = 30.0f;  break;
	          case "production11":		desc = "达尔文";       pricefloat = 30.0f;  break;
	          case "production12":		desc = "铁甲马克";     pricefloat = 45.0f; break;
	          case "production13":		desc = "解锁游戏";     pricefloat = 30.0f; break;
	          case "production14":		desc = "桂英";         pricefloat =  18.0f; break;
	          case "production15":		desc = "自由猎人";     pricefloat = 30.0f; break;
	          case "production16":		desc = "自由猎人";     pricefloat = 30.0f; break;
	          default:
	              break;	
	      }
		  QinPid = SavePid;
		  Qindesc= desc;
		  Qinpricefloat=pricefloat;
		  MercuryActivity.LogLocal("[MercuryConst][PayInfo] QinPid="+QinPid+" Qindesc="+Qindesc+" Qinpricefloat="+Qinpricefloat);
    }
    
	public void FunctionL(String number)
	{
		MercuryActivity.isLogOpen=number;
	}
	public void ExitGame()
	{
		MercuryActivity.LogLocal("[MercuryConst] ExitGame");
		((Activity) MercuryActivity.mContext).finish();
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	public void PurchaseSuccess(String message,InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] onPurchaseSuccess callback message->"+message+" inbase->"+inbase);
		inbase.appinterface.PurchaseSuccessCallBack(message);
		if (OpenUmeng ==true) {
			UMGameAgent.pay(Qinpricefloat,Qindesc,1,1,1);
		};
	}
	public void PurchaseFailed(String strError,InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] onPurchaseFailed callback->"+strError+" inbase->"+inbase);
		inbase.appinterface.PurchaseFailedCallBack(strError);
	}

	public void AdLoadSuccess(String strError,InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] AdLoadSuccess callback->"+strError+" inbase->"+inbase);
		inbase.appinterface.AdLoadSuccessCallBack(strError);
	}

	public void AdLoadFailed(String strError,InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] AdLoadFailed callback->"+strError+" inbase->"+inbase);
		inbase.appinterface.AdLoadFailedCallBack(strError);
	}

	public void LoginSuccess(String strError,InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] LoginSuccess callback->"+strError+" inbase->"+inbase);
		inbase.appinterface.LoginSuccessCallBack(strError);
	}

	public void LoginCancel(String strError,InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] LoginCancel callback->"+strError+" inbase->"+inbase);
		inbase.appinterface.LoginCancelCallBack(strError);
	}

	public void AdShowSuccessCallBack(String strError,InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] AdShowSuccessCallBack callback->"+strError+" inbase->"+inbase);
		inbase.appinterface.AdShowSuccessCallBack(strError);
	}

	public void AdShowFailedCallBack(String strError,InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] AdShowFailedCallBack callback->"+strError+" inbase->"+inbase);
		inbase.appinterface.AdShowFailedCallBack(strError);
	}
	public void FunctionCallBack(String strError,InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] FunctionCallBack callback->"+strError+" inbase->"+inbase);
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
		final String New_accesstoken="";
		final String New_appid="";
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
									inbase.appinterface.PurchaseFailedCallBack("");
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
