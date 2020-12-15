package com.mercury.game.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.util.Log;
import android.widget.EditText;

import com.mercury.game.MercuryActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import static com.mercury.game.util.Function.readFileData;

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
	public static JSONObject ProductionJsonList;
    public static InAppBase ConstInstance;
	
    public static String[] convertStrToArray(String str, String symbol){
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
	@SuppressLint("NewApi")
	public static String GetProductionList()
	{
		//LinkedList<String[]> ProductionConfiglist = new LinkedList<>();
		String ProductionList[][] = {
				{"com.singmaan.terragenesis.discountenergy.1.v1","能源礼包一","6"},
				{"com.singmaan.terragenesis.discountenergy.2","能源礼包二","12"},
				{"com.singmaan.terragenesis.discountenergy.3.v1","能源礼包三","18"},
				{"com.singmaan.terragenesis.discountenergy.4.v1","能源礼包四","45"},
				{"com.singmaan.terragenesis.discountaccelerate.1","加速礼包一","12"},
				{"com.singmaan.terragenesis.discountaccelerate.2.v1","加速礼包二","25"},
				{"com.singmaan.terragenesis.discountaccelerate.3.v1","加速礼包三","50"},
				{"com.singmaan.terragenesis.discountaccelerate.4","加速礼包四","88"},
				{"com.singmaan.terragenesis.genesispoint.1","+10能源","6"},
				{"com.singmaan.terragenesis.genesispoint.5","+50能源","30"},
				{"com.singmaan.terragenesis.genesispoint.10","+200能源","68"},
				{"com.singmaan.terragenesis.genesispoint.20","+500能源","128"},
				{"com.singmaan.terragenesis.genesispoint.50","+1500能源","328"},
				{"com.singmaan.terragenesis.genesispoint.100","+3500能源","648"},
				{"com.singmaan.terragenesis.pack.fastforward.5","24小时包","30"},
				{"pack.fastforward.20","7天包","163"},
				{"com.singmaan.terragenesis.pack.fastforward.50","15天包","328"},
				{"com.singmaan.terragenesis.pack.fastforward.100","30天包","648"},
				{"com.singmaan.terragenesis.Planets.All","所有星球","128"},
				{"com.singmaan.terragenesis.Bundle.VenusEarth","陆地星球","6"},
				{"com.singmaan.terragenesis.Bundle.Jupiter","木星卫星","18"},
				{"com.singmaan.terragenesis.Bundle.Saturn","土星卫星","25"},
				{"com.singmaan.terragenesis.Bundle.Uranus","天王星卫星","25"},
				{"com.singmaan.terragenesis.Planets.Triton","海王星卫星","6"},
				{"com.singmaan.terragenesis.Bundle.Dwarf","矮星","30"},
				{"com.singmaan.terragenesis.Bundle.Trappist","特拉比斯特","45"},
				{"com.singmaan.terragenesis.bundle.historicalearths","史实星球","40"},
				{"com.singmaan.terragenesis.Bundle.Fictional","虚构星球","25"},
				{"com.singmaan.terragenesis.Generator.Random","随机星球生成器","50"},
				{"com.singmaan.terragenesis.Planets.Venus","金星","6"},
				{"com.singmaan.terragenesis.Planets.Earth","地球","6"},
				{"com.singmaan.terragenesis.Planets.Io","木卫一","6"},
				{"com.singmaan.terragenesis.Planets.Europa","木卫二","6"},
				{"com.singmaan.terragenesis.Planets.Ganymede","木卫三","6"},
				{"com.singmaan.terragenesis.Planets.Callisto","木卫四","6"},
				{"com.singmaan.terragenesis.Planets.Tethys","土卫三","6"},
				{"com.singmaan.terragenesis.Planets.Dione","土卫四","6"},
				{"com.singmaan.terragenesis.Planets.Rhea","土卫五","6"},
				{"com.singmaan.terragenesis.Planets.Titan","土卫六","6"},
				{"com.singmaan.terragenesis.Planets.Iapetus","土卫八","6"},
				{"com.singmaan.terragenesis.Planets.Miranda","天卫五","6"},
				{"com.singmaan.terragenesis.Planets.Ariel","天卫一","6"},
				{"com.singmaan.terragenesis.Planets.Umbriel","天卫二","6"},
				{"com.singmaan.terragenesis.Planets.Titania","天卫三","6"},
				{"com.singmaan.terragenesis.Planets.Oberon","天卫四","6"},
				{"com.singmaan.terragenesis.Planets.Ceres","谷神星","6"},
				{"com.singmaan.terragenesis.Planets.Pluto","冥王星","6"},
				{"com.singmaan.terragenesis.Planets.Charon","冥卫一","6"},
				{"com.singmaan.terragenesis.Planets.Makemake","鸟神星","6"},
				{"com.singmaan.terragenesis.Planets.Eris","阋神星","6"},
				{"com.singmaan.terragenesis.Planets.Bacchus","酒神星","6"},
				{"com.singmaan.terragenesis.Planets.Sedna","赛德娜","6"},
				{"com.singmaan.terragenesis.Planets.Pontus","海神星","6"},
				{"com.singmaan.terragenesis.Planets.Lethe","忘河星","6"},
				{"com.singmaan.terragenesis.Planets.Ragnarok","诸神的黄昏","6"},
				{"com.singmaan.terragenesis.Planets.Boreas","冬神星","6"},
				{"com.singmaan.terragenesis.pack.usdgovernor.20.v1","长官礼包","128"},
				{"com.singmaan.terragenesis.pack.usdgovernor.30","长官礼包","168"},
				{"com.singmaan.terragenesis.pack.usdgovernor.50","长官礼包","198"},
				{"com.singmaan.terragenesis.dailygp.14","5能源一天","18"},
				{"com.singmaan.terragenesis.dailygp.5","5能源一天","18"},
				{"com.singmaan.terragenesis.pack.starter.4","新手包","18"},
				{"com.singmaan.terragenesis.pack.starter.8","新手包","18"},
				{"com.singmaan.terragenesis.pack.conspiracy.7","平坦行星礼包","60"},
				{"com.singmaan.terragenesis.Planets.Mercury","水星","6"},
				{"com.singmaan.terragenesis.Planets.Moon","月球","6"},
				{"com.singmaan.terragenesis.Planets.SignalTower","月卡","30"},
				{"com.singmaan.terragenesis.pack.oneyuan.1","1元礼包","1"},
				{"com.singmaan.terragenesis.pack.threeyuan.1","3元礼包","3"},
				{"com.singmaan.terragenesis.pack.sixyuan.1","6元礼包","6"}
		};

		String remote_config = readFileData("get_remote_iap");
		if(remote_config.equals("")==false) {
			try {
				JSONObject json = (JSONObject) new JSONTokener(remote_config).nextValue();
				JSONObject json_data = json.getJSONObject("data");
				JSONObject json_result = json_data.getJSONObject("result");
				Iterator<String> myKeys = json_result.keys();
				int index = 0;
				while (myKeys.hasNext()) {
					String iap_name = myKeys.next();
					JSONObject json_result1 = json_result.getJSONObject(iap_name);
					String remote_des = (String) json_result1.get("des");
					String remote_price = (String) json_result1.get("price");
					//String remote_guid = (String) json_result1.get("guid");
					ProductionList[index][0] = iap_name;
					ProductionList[index][1] = remote_des;
					ProductionList[index][2] = remote_price;
					//ProductionList[index][3] = remote_guid;
					index++;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
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
		if(QinPid.equals("")==true)
		{
			QinPid = "TestPid";
			Qindesc= "测试计费点";
			Qinpricefloat= 0.01f;
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

	public void PurchaseSuccess(String message, InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] onPurchaseSuccess callback message->"+message+" inbase->"+inbase);

		inbase.appinterface.PurchaseSuccessCallBack(message);
	}
	public void PurchaseFailed(String strError, InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] onPurchaseFailed callback->"+strError+" inbase->"+inbase);
		inbase.appinterface.PurchaseFailedCallBack(strError);
	}

	public void AdLoadSuccess(String strError, InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] AdLoadSuccess callback->"+strError+" inbase->"+inbase);
		inbase.appinterface.AdLoadSuccessCallBack(strError);
	}

	public void AdLoadFailed(String strError, InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] AdLoadFailed callback->"+strError+" inbase->"+inbase);
		inbase.appinterface.AdLoadFailedCallBack(strError);
	}

	public void LoginSuccessCallBack(String strError, InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] LoginSuccessCallBack callback->strError="+strError+" inbase->"+inbase);
		inbase.appinterface.LoginSuccessCallBack(strError);
	}
	public void LoginCancelCallBack(String strError, InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] LoginCancelCallBack callback->strError="+strError+" inbase->"+inbase);
		inbase.appinterface.LoginCancelCallBack(strError);
	}

	public void AdShowSuccessCallBack(String strError, InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] AdShowSuccessCallBack callback->"+strError+" inbase->"+inbase);
		inbase.appinterface.AdShowSuccessCallBack(strError);
	}

	public void AdShowFailedCallBack(String strError, InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] AdShowFailedCallBack callback->"+strError+" inbase->"+inbase);
		inbase.appinterface.AdShowFailedCallBack(strError);
	}
	public void FunctionCallBack(String strError, InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] FunctionCallBack callback->"+strError+" inbase->"+inbase);
		inbase.appinterface.onFunctionCallBack(strError);	
	}
	public void ProductionIDCallBack(String strError, InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] ProductionIDCallBack callback->"+strError+" inbase->"+inbase);
		inbase.appinterface.ProductionIDCallBack(strError);
	}

	public void QinUnityMessage(String ObjectName, String MethodName, String QinMessage)
	{
		if(MercuryActivity.Platform== MercuryConst.Unity)
		{
			//UnityPlayer.UnitySendMessage(ObjectName,MethodName,QinMessage);
		}
	}
	public void ShareResult(int result )
	{
		if(MercuryActivity.Platform== MercuryConst.Unity)
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
		if(MercuryActivity.Platform== MercuryConst.Unity)
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
	public static String postDownloadJson(String path, String post){
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
