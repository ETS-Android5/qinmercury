package com.mercury.game.InAppChannel;
import com.mercury.game.InAppDialog.IDCardVerifyDialog;
import com.mercury.game.InAppDialog.LoginDialog;
import com.mercury.game.InAppDialog.PaymentDialog;
import com.mercury.game.InAppDialog.PrivacyDialog;
import com.mercury.game.InAppDialog.SigneInDialog;
import com.mercury.game.util.APPBaseInterface;
import com.mercury.game.util.InAppBase;

//comment
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mercury.game.MercuryActivity;
import com.mercury.game.util.LoginCallBack;
import com.mercury.game.util.MercuryConst;
import com.mercury.game.util.PayMethodCallBack;
import com.mercury.game.util.SPUtils;
import com.mercury.game.util.SpConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.m4399.single.api.OperateConfig;
import cn.m4399.single.api.RechargeListener;
import cn.m4399.single.api.RechargeOrder;
import cn.m4399.single.api.SingleOperateCenter;

import static com.mercury.game.InAppDialog.LoginDialog.local_age;
import static com.mercury.game.InAppRemote.RemoteConfig.chinese_id;
import static com.mercury.game.MercuryActivity.DeviceId;
import static com.mercury.game.MercuryActivity.LogLocal;
import static com.mercury.game.MercuryApplication.channelname;
import static com.mercury.game.util.Function.readFileData;
import static com.mercury.game.util.Function.writeFileData;
import static com.mercury.game.util.MercuryConst.GlobalProductionList;
//end


public class InAppChannel extends InAppBase {
	
	//comment
	private String Channelname="InAppChannel";
	private static String pid;
	private String name_4399 = "永不言弃登峰";
	private String key_4399 = "2762";
	SingleOperateCenter  mOpeCenter;
	private ArrayList<String> mSKUList = new ArrayList<>(); //临时存储购买成功的物品，当activity在活动时显示出来
	@Override
	public void ActivityInit(Activity context, final APPBaseInterface appinterface)
	{		
		super.ActivityInit(context, appinterface);
		LogLocal("[InAppChannel][ActivityInit]="+Channelname);
//		Toast.makeText(mContext, "只限于"+channelname+"测试，请勿泄漏", Toast.LENGTH_SHORT).show();
		//shrinkpartstart
//		if(readFileData("privacyagreement").equals(""))
//		{
//			new PrivacyDialog(mContext);
//		}
//		if(readFileData("card_id").equals("")) {
//			new IDCardVerifyDialog(mContext, new LoginCallBack() {
//				@Override
//				public void success(String msg) {
//					LogLocal("[InAppDialog][SigneInDialog] ID card Success");
//				}
//
//				@Override
//				public void fail(String msg) {
//					LogLocal("[InAppDialog][SigneInDialog] ID card failed");
//				}
//			});
//		}
		//shrinkpartend
		if (PermissionHelper.hasPermission(mContext)) {
		OperateConfig config = new OperateConfig()
				.debuggable(false)  //发布游戏时，要设为false
				.orientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) //设置SDK界面方向，应与游戏设置一直
				.supportExcess(true) // 设置“是否支持超出金额”默认值，也可以在每次充值的订单信息里设置
				.gameKey(name_4399)    //换成实际游戏的game key
				.gameName(key_4399);   //换成实际游戏的名字，原则上与游戏名字匹配
		SingleOperateCenter.init(mContext, config, singleRechargeListener);
		} else {
			PermissionHelper.requestPermission(mContext);
		}
	}
	RechargeListener singleRechargeListener = new RechargeListener() {

		/*
		 * 充值过程结束时SDK回调此方法
		 *
		 * 充值过程结束并不代表订单生命周期全部完成，SDK还需要查询订单状态，游戏
		 * 要根据订单状态决定是否发放物品等
		 *
		 * @param msg 表示充值结果的友好的文本说明
		 *
		 */
		@Override
		public void onRechargeFinished(boolean success, String msg) {
			LogLocal("Pay: [" + success + ", " + msg + "]");
			Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
		}

		/*
		 * 充值过程成功完成后，SDK会查询订单状态，根据订单状态状态正常则通知游戏发放物品
		 *
		 * @param shouldDeliver
		 *  是否要发放物品
		 * @param o
		 *  封装了最后提交的订单信息的对象，主要包含以下成员，各成员都有getter方法
		 *  payChannel：   充值渠道
		 *  orderId：      	充值订单号
		 *  je：			充值金额
		 *  goods：        	购买的物品
		 *
		 * @return
		 *  物品发放过程是否成功
		 */

		@Override
		public synchronized boolean notifyDeliverGoods(boolean shouldDeliver, RechargeOrder o) {
			if (shouldDeliver) {
				LogLocal( o.toString());
				Toast.makeText(mContext, o.toString(), Toast.LENGTH_SHORT).show();
				mSKUList.add(String.format("您花费 %1$s元， 购买了 %2$s", o.money(), o.commodity()));
				// 如果游戏因为某些原因发放失败，应返回false
				return true;
			}
			return false;
		}
	};

	public void ActivityBundle(Bundle bundle)
	{
		super.ActivityBundle(bundle);
		MercuryActivity.LogLocal("[InAppChannel][ActivityBundle]="+bundle);
	}

	public void ApplicationInit(Application appcontext)
	{
		mAppContext=appcontext;
		MercuryActivity.LogLocal("[InAppChannel][ApplicationInit]="+Channelname);
	}

	public static String[] convertStrToArray(String str,String symbol){
		String[] strArray = null;
		strArray = str.split(symbol); //拆分字符为symbol 可以是 "," ,然后把结果交给数组strArray
		return strArray;
	}
	@Override
	public void Purchase(final String strProductId)
	{
		pid = strProductId;
		MercuryConst.PayInfo(strProductId);
		MercuryActivity.LogLocal("[InAppChannel][Purchase] MercuryConst.QinPid="+MercuryConst.QinPid);
		MercuryActivity.LogLocal("[InAppChannel][Purchase] MercuryConst.Qindesc="+MercuryConst.Qindesc);
		MercuryActivity.LogLocal("[InAppChannel][Purchase] MercuryConst.Qinpricefloat="+MercuryConst.Qinpricefloat);
		if(local_age<8 && local_age!=0 )
		{
			try {
				AlertDialog.Builder builder = new Builder(mContext);
				builder.setMessage("提示");
				builder.setTitle("未成年人无法充值");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
//						onPurchaseSuccess(MercuryConst.QinPid);
					}
				});
				builder.create().show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
		{
			//shrinkpartstart
			new PaymentDialog(mContext, new PayMethodCallBack() {
				@Override
				public void Alipay(String msg) {
					MercuryActivity.LogLocal("[InAppChannel][Purchase] Alipay");
					//shrinkpartend
					TestPay();
					//shrinkpartstart
				}

				@Override
				public void WechatPay(String msg) {
					MercuryActivity.LogLocal("[InAppChannel][Purchase] WechatPay");
					TestPay();
				}
			});
			//shrinkpartend
		}


	}
	@Override
	public void ExitGame()
	{
		try {
			AlertDialog.Builder builder = new Builder(mContext);
			builder.setMessage("Testing Mode");
			builder.setTitle("ExitGame");
			builder.setPositiveButton("Success", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					((Activity) MercuryActivity.mContext).finish();
					android.os.Process.killProcess(android.os.Process.myPid());
					mOpeCenter.destroy();
				}
			});
			builder.setNegativeButton("Dismiss", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.setCancelable(false);
			builder.create().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void TestPay()
	{
		try {
			AlertDialog.Builder builder = new Builder(mContext);
			builder.setMessage("Testing Mode");
			builder.setTitle("TestPay");
			builder.setPositiveButton("Success", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					onPurchaseSuccess(MercuryConst.QinPid);
				}
			});
			builder.setNeutralButton("Failed", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					onPurchaseFailed(MercuryConst.QinPid);
				}
			});
			builder.setCancelable(false);
			builder.create().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void SingmaanLogin()
	{
		String phone = "";
		LogLocal("[InAppChannel][SingmaanLogin]" + DeviceId);
		//shrinkpartstart
		LoginDialog loginDialog = new LoginDialog(mContext, MercuryActivity.DeviceId, new LoginCallBack() {
			@Override
			public void success(String phone) {
				LogLocal("[InAppChannel][SingmaanLogin] Success");
				DeviceId = phone;
				//shrinkpartend
				LoginSuccessCallBack(DeviceId);
				//shrinkpartstart
			}
			@Override
			public void fail(String msg) {
				LogLocal("[InAppChannel][SingmaanLogin] Login failed");
				LoginCancelCallBack(msg);
			}
		});
		//shrinkpartend
	}
	public void SingmaanLogout()
	{
		LogLocal("[MercuryActivity][SingmaanLogout]"+DeviceId);
		writeFileData("chineseid","");
		writeFileData("account","");
		LoginCancelCallBack(DeviceId);
	}
	public void MercuryIDVerify()
	{


	}
	public void UploadGameData()
	{
		LogLocal("[MercuryActivity][SingmaanLogin]"+DeviceId);
		onFunctionCallBack("SingmaanUploadGameData");
	}
	public void DownloadGameData()
	{
		LogLocal("[MercuryActivity][SingmaanLogout]"+DeviceId);
		onFunctionCallBack("SingmaanDownloadGameData");
	}
	public void Redeem()
	{
		int max=GlobalProductionList.length;
		int min=0;
		Random random = new Random();
		int s = random.nextInt(max)%(max-min+1) + min;
		onPurchaseSuccess(GlobalProductionList[s][0]);
	}
	public void RateGame()
	{
		LogLocal("[InAppChannel][RateGame]");
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse("http://www.singmaan.com");//此处填链接
		intent.setData(content_url);
		MercuryActivity.mContext.startActivity(intent);
	}
	public void ShareGame()
	{
		LogLocal("[InAppChannel][ShareGame]");
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse("http://www.singmaan.com");//此处填链接
		intent.setData(content_url);
		MercuryActivity.mContext.startActivity(intent);
	}
	public void OpenGameCommunity()
	{
		LogLocal("[InAppChannel][OpenGameCommunity]");
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse("http://www.singmaan.com");//此处填链接
		intent.setData(content_url);
		MercuryActivity.mContext.startActivity(intent);
	}
	public void VIPPanel() {
		try {
			AlertDialog.Builder builder = new Builder(mContext);
			builder.setMessage("Testing Mode");
			builder.setTitle("VIPPanel");
			builder.setPositiveButton("Success", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					try
					{
						String[][] item_list = {{"Coin", "1000"},{"Token", "15"}};
						int[] ages = {18, 20};
						JSONArray reward_list = new JSONArray();
						for (int i = 0; i < item_list.length; i++) {
							JSONObject student = new JSONObject();
							student.put("ResourceId", item_list[i][0]);
							student.put("Amount", Integer.parseInt(item_list[i][1]));
							reward_list.put(student);
						}
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("OnClaimReward", reward_list);
						MercuryActivity.LogLocal("[VIPPanel]OnClaimReward="+reward_list.toString());
						OnClaimReward(reward_list.toString());
					}
					catch (JSONException e)
					{
						e.printStackTrace();
					}

				}
			});
			builder.setNeutralButton("Failed", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			});
			builder.setNegativeButton("Dismiss", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

					dialog.dismiss();
				}
			});
			builder.create().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void DailyCheckInPanel() {
		try {
			AlertDialog.Builder builder = new Builder(mContext);
			builder.setMessage("Testing Mode");
			builder.setTitle("DailyCheckInPanel");
			builder.setPositiveButton("Success", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					try
					{
						String[][] item_list = {{"Coin", "1000"},{"Token", "15"}};
						int[] ages = {18, 20};
						JSONArray reward_list = new JSONArray();
						for (int i = 0; i < item_list.length; i++) {
							JSONObject student = new JSONObject();
							student.put("ResourceId", item_list[i][0]);
							student.put("Amount", Integer.parseInt(item_list[i][1]));
							reward_list.put(student);
						}
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("OnClaimReward", reward_list);
						MercuryActivity.LogLocal("[DailyCheckInPanel]OnClaimReward="+reward_list.toString());
						OnClaimReward(reward_list.toString());
					}
					catch (JSONException e)
					{
						e.printStackTrace();
					}
				}
			});
			builder.setNeutralButton("Failed", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			});
			builder.setNegativeButton("Dismiss", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

					dialog.dismiss();
				}
			});
			builder.create().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onPause()
	{
		MercuryActivity.LogLocal("["+Channelname+"][onPause]");
	}

	@Override
	public void onResume()
	{
		MercuryActivity.LogLocal("["+Channelname+"][onResume]");
	}
	@Override
	public void onDestroy()
	{
		MercuryActivity.LogLocal("["+Channelname+"][onDestroy]");
		SingleOperateCenter.destroy();
	}
	@Override
	public void onStop()
	{
		MercuryActivity.LogLocal("["+Channelname+"][onStop]");
	}
	@Override
	public void onStart()
	{
		MercuryActivity.LogLocal("["+Channelname+"][onStart]");
	}
	@Override
	public void onRestart()
	{
		MercuryActivity.LogLocal("["+Channelname+"][onRestart]");
	}
	public void onTerminate()
	{

	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		MercuryActivity.LogLocal("["+Channelname+"] onActivityResult(int requestCode, int resultCode, Intent data)");
	}
	@Override
	public void onNewIntent(Intent intent)
	{
		MercuryActivity.LogLocal("["+Channelname+"] onNewIntent(Intent intent) ");
	}
	@Override
	public void attachBaseContext(Context base)
	{
		MercuryActivity.LogLocal("["+Channelname+"] attachBaseContext(Context base) ");
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		MercuryActivity.LogLocal("["+Channelname+"] onWindowFocusChanged(Context base) ");
	}
	public void MercurySigneIn() {
		//shrinkpartstart
		new IDCardVerifyDialog(mContext, new LoginCallBack() {
			@Override
			public void success(String msg) {
				LogLocal("[InAppDialog][SigneInDialog] ID card Success");
			}
			@Override
			public void fail(String msg) {
				LogLocal("[InAppDialog][SigneInDialog] ID card failed");
			}
		});
		//shrinkpartend
	}
}
