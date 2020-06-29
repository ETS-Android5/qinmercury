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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	public static JSONObject ProductionJsonList;
    
	
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
	public static String GlobalProductionList[][];
	public static String GetProductionList()
	{
		String ProductionList[][] = {

			{"sku_ww1_remove_ads","去除广告","1.0"},
			{"sku_ww1_gold_200","200个黄金","1.0"},
			{"sku_ww1_gold_500","500个黄金","1.0"},
			{"sku_ww1_gold_1200","1200个黄金","1.0"},
			{"sku_ww1_gold_2800","2800个黄金","1.0"},
			{"sku_ww1_premium_mode","无限高级任务","1.0"},
			{"sku_ww1_bank_withdraw","从超级金库取款","1.0"},
			{"sku_ww1_plane_all_pack","各国战机","1.0"},

			{"sku_ww1_plane_en_pack","所有协约国西线战机","1.0"},
			{"sku_ww1_plane_en_01","解锁S.E.5","1.0"},
			{"sku_ww1_plane_en_02","解锁索普威斯三翼战斗机","1.0"},
			{"sku_ww1_plane_en_03","解锁Airco DH.4","1.0"},
			{"sku_ww1_plane_en_04","解锁汉莱帕杰400战机","1.0"},

			{"sku_ww1_plane_ger_pack","购买所有同盟国高级战机","1.0"},
			{"sku_ww1_plane_ger_01","解锁容克斯D.I型战机","1.0"},
			{"sku_ww1_plane_ger_02","解锁福克尔Dr.I单座三翼战斗机","1.0"},
			{"sku_ww1_plane_ger_03","解锁福克尔DVIII型战机","1.0"},
			{"sku_ww1_plane_ger_04","解锁齐柏林斯塔肯R4型战机","1.0"},

			{"sku_ww1_plane_rus_pack","购买所有三国协约同盟东线高级战机","1.0"},
			{"sku_ww1_plane_rus_01","解锁索普威斯 狙击式战机","1.0"},
			{"sku_ww1_plane_rus_02","解锁DH.5","1.0"},
			{"sku_ww1_plane_rus_03","解锁Airco DH.9","1.0"},
			{"sku_ww1_plane_rus_04","解锁西科尔斯基·伊利亚·穆罗梅茨","1.0"},
//			{"uk.fiveaces.nsfcchina.removeads","去除广告","5.0"},
//			{"uk.fiveaces.nsfcchina.unlimitededitor","无限制编辑","5.0"},
//			{"uk.fiveaces.nsfcchina.bigstaffboost","幕后提升包","3.0"},
//			{"uk.fiveaces.nsfcchina.betabasicpack","基础包","2.0"},
//			{"uk.fiveaces.nsfcchina.bux_1","星币包1","10.0"},
//			{"uk.fiveaces.nsfcchina.bux_2","星币包2","15.0"},
//			{"uk.fiveaces.nsfcchina.bux_3","星币包3","20.0"},
//			{"uk.fiveaces.nsfcchina.bux_5","星币包4","50.0"},
//			{"uk.fiveaces.nsfcchina.betamanagementpack","俱乐部包","2.0"},
//			{"uk.fiveaces.nsfcchina.fitnesscoaches1new","优秀的健身教练","4.0"},
//			{"uk.fiveaces.nsfcchina.fitnesscoaches2new","卓越的健身教练","2.0"},
//			{"uk.fiveaces.nsfcchina.physios1new","优秀的队医","4.0"},
//			{"uk.fiveaces.nsfcchina.physios2new","卓越的队医","2.0"},
//			{"uk.fiveaces.nsfcchina.skillcoaches1new","优秀的技能教练","4.0"},
//			{"uk.fiveaces.nsfcchina.skillcoaches2new","卓越的技能教练","2.0"},
//			{"uk.fiveaces.nsfcchina.concernspack","担忧包","5.0"},
//			{"uk.fiveaces.nsfcchina.contractpack","合约包","5.0"},
//			{"uk.fiveaces.nsfcchina.defensivepack","后卫包","6.0"},
//			{"uk.fiveaces.nsfcchina.chanceplayer","发掘包","1.0"},
//			{"uk.fiveaces.nsfcchina.dreamteam","梦之队","20.0"},
//			{"uk.fiveaces.nsfcchina.goaliepack","门将包","5.0"},
//			{"uk.fiveaces.nsfcchina.betagoldplayerpack","黄金球员包","2.0"},
//			{"uk.fiveaces.nsfcchina.betakeeperpack","技能包","1.0"},
//			{"uk.fiveaces.nsfcchina.marketingmanager","营销经理","4.0"},
//			{"uk.fiveaces.nsfcchina.midfieldpack","中锋包","5.0"},
//			{"uk.fiveaces.nsfcchina.betarecoverypack","恢复包","1.0"},
//			{"uk.fiveaces.nsfcchina.retries","重试包","2.0"},
//			{"uk.fiveaces.nsfcchina.betaholidaypack","特别球员包","6.0"},
//			{"uk.fiveaces.nsfcchina.strikerpack","前锋包","6.0"},
//			{"uk.fiveaces.nsfcchina.superkeeper","超级门将","11.0"},
//			{"uk.fiveaces.nsfcchina.superstriker","超级前锋","9.0"},
//			{"uk.fiveaces.nsfcchina.supremeteam","顶级球队","45.0"},
//			{"uk.fiveaces.nsfcchina.spinepack","团队精神包","8.0"},
//			{"uk.fiveaces.nsfcchina.betacoachingpack","训练包","4.0"},
//			{"uk.fiveaces.nsfcchina.versatilitypack","通才包","5.0"}
		};
		GlobalProductionList = ProductionList;
		ProductionJsonList = new JSONObject();
		for(int i=0;i<ProductionList.length;i++)
		{
			try
			{
				ProductionJsonList.put(ProductionList[i][0], ProductionList[i][2]+"¥");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return ProductionJsonList.toString();
	}
	public static void PayInfo(String SavePid)
	{
		QinPid = "";
		Qindesc= "";
		Qinpricefloat= 0f;
		for(int i=0;i<GlobalProductionList.length;i++)
		{
			if (GlobalProductionList[i][0].equals(SavePid))
			{
				QinPid =	  GlobalProductionList[i][0];
				Qindesc=	  GlobalProductionList[i][1];
				Qinpricefloat = Float.parseFloat(GlobalProductionList[i][2]);
				MercuryActivity.LogLocal("[MercuryConst][PayInfo] QinPid="+QinPid+" Qindesc="+Qindesc+" Qinpricefloat="+Qinpricefloat);
				break;
			}
		}
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
		if (OpenUmeng ==true) {
			String id = "";
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
			String temp = sf.format(new Date());
			int random = (int) ((Math.random() + 1) * 100000);
			id = temp + random;
		}
		inbase.appinterface.PurchaseSuccessCallBack(message);
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

	public void LoginSuccessCallBack(String strError,InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] LoginSuccessCallBack callback->strError="+strError+" inbase->"+inbase);
		inbase.appinterface.LoginSuccessCallBack(strError);
	}
	public void LoginCancelCallBack(String strError,InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] LoginCancelCallBack callback->strError="+strError+" inbase->"+inbase);
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
