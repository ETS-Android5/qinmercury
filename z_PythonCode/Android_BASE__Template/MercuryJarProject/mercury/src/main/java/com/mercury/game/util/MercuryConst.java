package com.mercury.game.util;

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
import java.util.Iterator;

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
	public static String GetProductionList()
	{
		String ProductionList[][] = {
				{"com.koplagames.kopla02.test.099","Gem","6.0","910da6e0-bf15-4454-abc2-dbfc474dd914"},
				{"com.koplagames.kopla02.test.1999","Gem","128.0","910da6e0-bf2a-449c-baca-0de2c7d4b73a"},
				{"com.koplagames.kopla02.test.299","Gem","18.0","910da6e0-bf2f-4fda-ab8c-851aa875b971"},
				{"com.koplagames.kopla02.test.499","Gem","30.0","910da6e0-bf32-4a16-b8d5-27d4694de627"},
				{"com.koplagames.kopla02.test.4999","Gem","328.0","910da6e0-bf36-4505-92d9-2a3f84d9b5b0"},
				{"com.koplagames.kopla02.test.999","Gem","68.0","910da6e0-bf39-468a-ab51-71ea993937cd"},
				{"com.koplagames.kopla02.test.9999","Gem","648.0","910da6e0-bf3d-470c-babe-aec8748c5280"},

				{"com.koplagames.cn.1","bundle","1.0","9141b098-26ea-4827-8c56-fc4d9d12f5c3"},
				{"com.koplagames.cn.3","bundle","3.0","9141b0b3-fa3a-4fda-9f88-a394d3c5a83c"},
				{"com.koplagames.cn.8","bundle","8.0","9141b0cc-4a23-482c-9833-40c89aed6d1c"},
				{"com.koplagames.cn.16","bundle","16.0","9141b0df-598e-4825-9235-1436f0d9b747"},
				{"com.koplagames.cn.36","bundle","36.0","9141b0fc-75d6-49d5-b79b-517efcc6fc92"},
				{"com.koplagames.cn.86","bundle","86.0","9141b10f-f5ad-43cf-824b-80f1edb2da65"},
				{"com.koplagames.cn.188","bundle","188.0","9141b124-a680-4265-9129-324b82bcacb8"},
				{"com.koplagames.cn.256","bundle","256.0","9141b136-bfd8-4c73-bec7-fe958e447c6e"},

				{"com.koplagames.nsk2.carnivalrewardpass.10","CarnivalRewardPass","68.0","910da6e0-bf42-4219-9648-477fb4e8f211"},
				{"com.koplagames.nsk2.carnivalrewardpass.15","CarnivalRewardPass","98.0","910da6e0-bf45-4313-9691-70c7041d7d59"},
				{"com.koplagames.nsk2.carnivalrewardpass.20","CarnivalRewardPass","128.0","910da6e0-bf48-499d-90da-4a4781f896c2"},
				{"com.koplagames.nsk2.carnivalrewardpass.3","CarnivalRewardPass","18.0","910da6e0-bf4d-4607-8ac3-f088184a8893"},
				{"com.koplagames.nsk2.carnivalrewardpass.5","CarnivalRewardPass","30.0","910da6e0-bf50-4c0a-b11d-0aaa40bc5c50"},
				{"com.koplagames.nsk2.carnivalrewardpass.8","CarnivalRewardPass","50.0","910da6e0-bf53-4922-b360-25636194eb0a"},

				{"com.koplagames.cn.perm.bundle01","Pet: Pet005,  PetCosmetic: PetCosmeticPet005_1","30.0","910da6e0-bf56-4fed-b9ec-3bbb52df0689"},
				{"com.koplagames.cn.set002.bundle01","Pet: Pet005,  PetCosmetic: PetCosmeticPet005_1","68.0","910da6e0-bf59-4e81-9302-01dd5044c7f3"},
				{"com.koplagames.cn.set002.bundle02","Pet: Pet005,  PetCosmetic: PetCosmeticPet005_1","128.0","910da6e0-bf5c-4fc1-a6f6-3752091032ab"},
				{"com.koplagames.cn.set002.bundle03","Pet: Pet005,  PetCosmetic: PetCosmeticPet005_1","258.0","910da6e0-bf5f-4d68-8cf3-2e9756647d8b"},
				{"com.koplagames.cn.set002.bundle04","Pet005: 10,  PetCosmetic: PetCosmeticPet005_1","30.0","910da6e0-bf62-4d42-9cb7-598655525a75"},
				{"com.koplagames.cn.set002.bundle05","Pet005: 40,  PetCosmetic: PetCosmeticPet005_1","68.0","910da6e0-bf67-46f4-847a-70d1513ae60f"},
				{"com.koplagames.cn.set002.bundle06","Pet005: 100,  PetCosmetic: PetCosmeticPet005_1","128.0","910da6e0-bf6a-4536-ab8b-21917f8291a9"},
				{"com.koplagames.cn.set002.bundle07","Pet: Pet005,  PetCosmetic: PetCosmeticPet005_1","128.0","910da6e0-bf6d-4468-98c6-341ec78e61a5"},
				{"com.koplagames.cn.set002.bundle08","Keys: 30,  Chest: Luxury,  Chest: Megapack","68.0","910da6e0-bf70-4ae8-9b7e-f0852ca46626"},

				{"com.koplagames.cn.set003.bundle01","Chest: CardPackGold,  Keys: 5","6.0","910da6e0-bf73-431b-a488-5df26249dc31"},
				{"com.koplagames.cn.set003.bundle02","Pet: Pet005,  PetCosmetic: PetCosmeticPet005_1","30.0","910da6e0-bf76-498f-b119-5dccd350b8d0"},
				{"com.koplagames.cn.set003.bundle03","Pet: Pet005,  PetCosmetic: PetCosmeticPet005_1","68.0","910da6e0-bf79-4511-b8eb-b2602b2f4894"},
				{"com.koplagames.cn.set003.bundle04","Keys: 20,  Chest: Luxury,  Chest: Megapack","128.0","910da6e0-bf7c-499d-bebd-791683169830"},
				{"com.koplagames.cn.set003.bundle05","Gem: 2500,  PowerStone1: 10","128.0","910da6e0-bf7f-4504-9619-358f1f082976"},
				{"com.koplagames.cn.set003.bundle06","Luxury Chest: 2,  Powerstone1: 30","328.0","910da6e0-bf84-4ac9-a93a-874b036f2ca8"},
				{"com.koplagames.cn.set003.bundle07","HeroCosmetic: FacialHair003,  Chest: Luxury","68.0","910da6e0-bf87-4bc6-a423-2e0321962ed8"},
				{"com.koplagames.cn.set003.bundle08","Tokens: 400,  HeroCosmetic: FacialHair001","68.0","910da6e0-bf8a-4b86-b2fe-e63dafaf8cc6"},

				{"com.koplagames.cn.set004.bundle01","HeroCosmetic: FacialHair005","30.0","910da6e0-bf8e-42ba-abbb-150be0fb6434"},
				{"com.koplagames.cn.set004.bundle02","HeroCosmetic: FacialHair006,  Chest: Luxury","68.0","910da6e0-bf91-4766-ac5e-6075df482756"},
				{"com.koplagames.cn.set004.bundle03","Gem: 6500,  PowerStone1: 30","328.0","910da6e0-bf94-4996-873e-f7d7399b8fdd"},
				{"com.koplagames.cn.set004.bundle04","Gem: 14000,  PowerStone1: 60","648.0","910da6e0-bf97-4baf-ae14-fdd8fea3a072"},
				{"com.koplagames.cn.set004.bundle05","PowerStone1: 4,  PowerStone1: 4","68.0","910da6e0-bf9b-48e0-87d6-41eee2a9d40d"},
				{"com.koplagames.cn.set004.bundle06","Gem: 2500,  PowerStone1: 8,  PowerStone1: 8","128.0","910da6e0-bf9e-45bc-822a-e3fe14bd757d"},
				{"com.koplagames.cn.set004.bundle07","Gem: 6500,  PowerStone1: 20,  PowerStone1: 20","328.0","910da6e0-bfa2-4154-9c9e-0052954f9808"},
				{"com.koplagames.cn.set004.bundle08","Gem: 14000,  PowerStone1: 40,  PowerStone1: 40","648.0","910da6e0-bfa5-4003-8b79-03cf3f2111eb"},

				{"com.koplagames.cn.set005.bundle01","HeroCosmetic: FacialHair005","30.0","910da6e0-bf8e-42ba-abbb-150be0fb6434"},
				{"com.koplagames.cn.set005.bundle02","HeroCosmetic: FacialHair006,  Chest: Luxury","68.0","910da6e0-bf91-4766-ac5e-6075df482756"},
				{"com.koplagames.cn.set005.bundle03","Gem: 6500,  PowerStone1: 30","328.0","910da6e0-bf94-4996-873e-f7d7399b8fdd"},
				{"com.koplagames.cn.set005.bundle04","Gem: 14000,  PowerStone1: 60","648.0","910da6e0-bf97-4baf-ae14-fdd8fea3a072"},
				{"com.koplagames.cn.set005.bundle05","PowerStone1: 4,  PowerStone1: 4","68.0","910da6e0-bf9b-48e0-87d6-41eee2a9d40d"},
				{"com.koplagames.cn.set005.bundle06","Gem: 2500,  PowerStone1: 8,  PowerStone1: 8","128.0","910da6e0-bf9e-45bc-822a-e3fe14bd757d"},
				{"com.koplagames.cn.set005.bundle07","Gem: 6500,  PowerStone1: 20,  PowerStone1: 20","328.0","910da6e0-bfa2-4154-9c9e-0052954f9808"},
				{"com.koplagames.cn.set005.bundle08","Gem: 14000,  PowerStone1: 40,  PowerStone1: 40","648.0","910da6e0-bfa5-4003-8b79-03cf3f2111eb"},

				{"com.koplagames.cn.set006.bundle01","HeroCosmetic: FacialHair005","30.0","910da6e0-bf8e-42ba-abbb-150be0fb6434"},
				{"com.koplagames.cn.set006.bundle02","HeroCosmetic: FacialHair006,  Chest: Luxury","68.0","910da6e0-bf91-4766-ac5e-6075df482756"},
				{"com.koplagames.cn.set006.bundle03","Gem: 6500,  PowerStone1: 30","328.0","910da6e0-bf94-4996-873e-f7d7399b8fdd"},
				{"com.koplagames.cn.set006.bundle04","Gem: 14000,  PowerStone1: 60","648.0","910da6e0-bf97-4baf-ae14-fdd8fea3a072"},
				{"com.koplagames.cn.set006.bundle05","PowerStone1: 4,  PowerStone1: 4","68.0","910da6e0-bf9b-48e0-87d6-41eee2a9d40d"},
				{"com.koplagames.cn.set006.bundle06","Gem: 2500,  PowerStone1: 8,  PowerStone1: 8","128.0","910da6e0-bf9e-45bc-822a-e3fe14bd757d"},
				{"com.koplagames.cn.set006.bundle07","Gem: 6500,  PowerStone1: 20,  PowerStone1: 20","328.0","910da6e0-bfa2-4154-9c9e-0052954f9808"},
				{"com.koplagames.cn.set006.bundle08","Gem: 14000,  PowerStone1: 40,  PowerStone1: 40","648.0","910da6e0-bfa5-4003-8b79-03cf3f2111eb"},

				{"com.koplagames.cn.set007.bundle01","HeroCosmetic: FacialHair005","30.0","910da6e0-bf8e-42ba-abbb-150be0fb6434"},
				{"com.koplagames.cn.set007.bundle02","HeroCosmetic: FacialHair006,  Chest: Luxury","68.0","910da6e0-bf91-4766-ac5e-6075df482756"},
				{"com.koplagames.cn.set007.bundle03","Gem: 6500,  PowerStone1: 30","328.0","910da6e0-bf94-4996-873e-f7d7399b8fdd"},
				{"com.koplagames.cn.set007.bundle04","Gem: 14000,  PowerStone1: 60","648.0","910da6e0-bf97-4baf-ae14-fdd8fea3a072"},
				{"com.koplagames.cn.set007.bundle05","PowerStone1: 4,  PowerStone1: 4","68.0","910da6e0-bf9b-48e0-87d6-41eee2a9d40d"},
				{"com.koplagames.cn.set007.bundle06","Gem: 2500,  PowerStone1: 8,  PowerStone1: 8","128.0","910da6e0-bf9e-45bc-822a-e3fe14bd757d"},
				{"com.koplagames.cn.set007.bundle07","Gem: 6500,  PowerStone1: 20,  PowerStone1: 20","328.0","910da6e0-bfa2-4154-9c9e-0052954f9808"},
				{"com.koplagames.cn.set007.bundle08","Gem: 14000,  PowerStone1: 40,  PowerStone1: 40","648.0","910da6e0-bfa5-4003-8b79-03cf3f2111eb"},

				{"com.koplagames.cn.set008.bundle01","HeroCosmetic: FacialHair005","30.0","910da6e0-bf8e-42ba-abbb-150be0fb6434"},
				{"com.koplagames.cn.set008.bundle02","HeroCosmetic: FacialHair006,  Chest: Luxury","68.0","910da6e0-bf91-4766-ac5e-6075df482756"},
				{"com.koplagames.cn.set008.bundle03","Gem: 6500,  PowerStone1: 30","328.0","910da6e0-bf94-4996-873e-f7d7399b8fdd"},
				{"com.koplagames.cn.set008.bundle04","Gem: 14000,  PowerStone1: 60","648.0","910da6e0-bf97-4baf-ae14-fdd8fea3a072"},
				{"com.koplagames.cn.set008.bundle05","PowerStone1: 4,  PowerStone1: 4","68.0","910da6e0-bf9b-48e0-87d6-41eee2a9d40d"},
				{"com.koplagames.cn.set008.bundle06","Gem: 2500,  PowerStone1: 8,  PowerStone1: 8","128.0","910da6e0-bf9e-45bc-822a-e3fe14bd757d"},
				{"com.koplagames.cn.set008.bundle07","Gem: 6500,  PowerStone1: 20,  PowerStone1: 20","328.0","910da6e0-bfa2-4154-9c9e-0052954f9808"},
				{"com.koplagames.cn.set008.bundle08","Gem: 14000,  PowerStone1: 40,  PowerStone1: 40","648.0","910da6e0-bfa5-4003-8b79-03cf3f2111eb"},

				{"com.koplagames.cn.set009.bundle01","HeroCosmetic: FacialHair005","30.0","910da6e0-bf8e-42ba-abbb-150be0fb6434"},
				{"com.koplagames.cn.set009.bundle02","HeroCosmetic: FacialHair006,  Chest: Luxury","68.0","910da6e0-bf91-4766-ac5e-6075df482756"},
				{"com.koplagames.cn.set009.bundle03","Gem: 6500,  PowerStone1: 30","328.0","910da6e0-bf94-4996-873e-f7d7399b8fdd"},
				{"com.koplagames.cn.set009.bundle04","Gem: 14000,  PowerStone1: 60","648.0","910da6e0-bf97-4baf-ae14-fdd8fea3a072"},
				{"com.koplagames.cn.set009.bundle05","PowerStone1: 4,  PowerStone1: 4","68.0","910da6e0-bf9b-48e0-87d6-41eee2a9d40d"},
				{"com.koplagames.cn.set009.bundle06","Gem: 2500,  PowerStone1: 8,  PowerStone1: 8","128.0","910da6e0-bf9e-45bc-822a-e3fe14bd757d"},
				{"com.koplagames.cn.set009.bundle07","Gem: 6500,  PowerStone1: 20,  PowerStone1: 20","328.0","910da6e0-bfa2-4154-9c9e-0052954f9808"},
				{"com.koplagames.cn.set009.bundle08","Gem: 14000,  PowerStone1: 40,  PowerStone1: 40","648.0","910da6e0-bfa5-4003-8b79-03cf3f2111eb"},

				{"com.koplagames.cn.set010.bundle01","HeroCosmetic: FacialHair005","30.0","910da6e0-bf8e-42ba-abbb-150be0fb6434"},
				{"com.koplagames.cn.set010.bundle02","HeroCosmetic: FacialHair006,  Chest: Luxury","68.0","910da6e0-bf91-4766-ac5e-6075df482756"},
				{"com.koplagames.cn.set010.bundle03","Gem: 6500,  PowerStone1: 30","328.0","910da6e0-bf94-4996-873e-f7d7399b8fdd"},
				{"com.koplagames.cn.set010.bundle04","Gem: 14000,  PowerStone1: 60","648.0","910da6e0-bf97-4baf-ae14-fdd8fea3a072"},
				{"com.koplagames.cn.set010.bundle05","PowerStone1: 4,  PowerStone1: 4","68.0","910da6e0-bf9b-48e0-87d6-41eee2a9d40d"},
				{"com.koplagames.cn.set010.bundle06","Gem: 2500,  PowerStone1: 8,  PowerStone1: 8","128.0","910da6e0-bf9e-45bc-822a-e3fe14bd757d"},
				{"com.koplagames.cn.set010.bundle07","Gem: 6500,  PowerStone1: 20,  PowerStone1: 20","328.0","910da6e0-bfa2-4154-9c9e-0052954f9808"},
				{"com.koplagames.cn.set010.bundle08","Gem: 14000,  PowerStone1: 40,  PowerStone1: 40","648.0","910da6e0-bfa5-4003-8b79-03cf3f2111eb"},

				{"com.koplagames.cn.week01.bundle01","HeroCosmetic: FacialHair005","30.0","910da6e0-bf8e-42ba-abbb-150be0fb6434"},
				{"com.koplagames.cn.week01.bundle02","HeroCosmetic: FacialHair006,  Chest: Luxury","68.0","910da6e0-bf91-4766-ac5e-6075df482756"},
				{"com.koplagames.cn.week01.bundle03","Gem: 6500,  PowerStone1: 30","328.0","910da6e0-bf94-4996-873e-f7d7399b8fdd"},
				{"com.koplagames.cn.week01.bundle04","Gem: 14000,  PowerStone1: 60","648.0","910da6e0-bf97-4baf-ae14-fdd8fea3a072"},
				{"com.koplagames.cn.week01.bundle05","PowerStone1: 4,  PowerStone1: 4","68.0","910da6e0-bf9b-48e0-87d6-41eee2a9d40d"},
				{"com.koplagames.cn.week01.bundle06","Gem: 2500,  PowerStone1: 8,  PowerStone1: 8","128.0","910da6e0-bf9e-45bc-822a-e3fe14bd757d"},
				{"com.koplagames.cn.week01.bundle07","Gem: 6500,  PowerStone1: 20,  PowerStone1: 20","328.0","910da6e0-bfa2-4154-9c9e-0052954f9808"},
				{"com.koplagames.cn.week01.bundle08","Gem: 14000,  PowerStone1: 40,  PowerStone1: 40","648.0","910da6e0-bfa5-4003-8b79-03cf3f2111eb"},

				{"com.koplagames.cn.week02.bundle01","HeroCosmetic: FacialHair005","30.0","910da6e0-bf8e-42ba-abbb-150be0fb6434"},
				{"com.koplagames.cn.week02.bundle02","HeroCosmetic: FacialHair006,  Chest: Luxury","68.0","910da6e0-bf91-4766-ac5e-6075df482756"},
				{"com.koplagames.cn.week02.bundle03","Gem: 6500,  PowerStone1: 30","328.0","910da6e0-bf94-4996-873e-f7d7399b8fdd"},
				{"com.koplagames.cn.week02.bundle04","Gem: 14000,  PowerStone1: 60","648.0","910da6e0-bf97-4baf-ae14-fdd8fea3a072"},
				{"com.koplagames.cn.week02.bundle05","PowerStone1: 4,  PowerStone1: 4","68.0","910da6e0-bf9b-48e0-87d6-41eee2a9d40d"},
				{"com.koplagames.cn.week02.bundle06","Gem: 2500,  PowerStone1: 8,  PowerStone1: 8","128.0","910da6e0-bf9e-45bc-822a-e3fe14bd757d"},
				{"com.koplagames.cn.week02.bundle07","Gem: 6500,  PowerStone1: 20,  PowerStone1: 20","328.0","910da6e0-bfa2-4154-9c9e-0052954f9808"},
				{"com.koplagames.cn.week02.bundle08","Gem: 14000,  PowerStone1: 40,  PowerStone1: 40","648.0","910da6e0-bfa5-4003-8b79-03cf3f2111eb"},

				{"com.koplagames.cn.week03.bundle01","HeroCosmetic: FacialHair005","30.0","910da6e0-bf8e-42ba-abbb-150be0fb6434"},
				{"com.koplagames.cn.week03.bundle02","HeroCosmetic: FacialHair006,  Chest: Luxury","68.0","910da6e0-bf91-4766-ac5e-6075df482756"},
				{"com.koplagames.cn.week03.bundle03","Gem: 6500,  PowerStone1: 30","328.0","910da6e0-bf94-4996-873e-f7d7399b8fdd"},
				{"com.koplagames.cn.week03.bundle04","Gem: 14000,  PowerStone1: 60","648.0","910da6e0-bf97-4baf-ae14-fdd8fea3a072"},
				{"com.koplagames.cn.week03.bundle05","PowerStone1: 4,  PowerStone1: 4","68.0","910da6e0-bf9b-48e0-87d6-41eee2a9d40d"},
				{"com.koplagames.cn.week03.bundle06","Gem: 2500,  PowerStone1: 8,  PowerStone1: 8","128.0","910da6e0-bf9e-45bc-822a-e3fe14bd757d"},
				{"com.koplagames.cn.week03.bundle07","Gem: 6500,  PowerStone1: 20,  PowerStone1: 20","328.0","910da6e0-bfa2-4154-9c9e-0052954f9808"},
				{"com.koplagames.cn.week03.bundle08","Gem: 14000,  PowerStone1: 40,  PowerStone1: 40","648.0","910da6e0-bfa5-4003-8b79-03cf3f2111eb"},

				{"com.koplagames.cn.week04.bundle01","HeroCosmetic: FacialHair005","30.0","910da6e0-bf8e-42ba-abbb-150be0fb6434"},
				{"com.koplagames.cn.week04.bundle02","HeroCosmetic: FacialHair006,  Chest: Luxury","68.0","910da6e0-bf91-4766-ac5e-6075df482756"},
				{"com.koplagames.cn.week04.bundle03","Gem: 6500,  PowerStone1: 30","328.0","910da6e0-bf94-4996-873e-f7d7399b8fdd"},
				{"com.koplagames.cn.week04.bundle04","Gem: 14000,  PowerStone1: 60","648.0","910da6e0-bf97-4baf-ae14-fdd8fea3a072"},
				{"com.koplagames.cn.week04.bundle05","PowerStone1: 4,  PowerStone1: 4","68.0","910da6e0-bf9b-48e0-87d6-41eee2a9d40d"},
				{"com.koplagames.cn.week04.bundle06","Gem: 2500,  PowerStone1: 8,  PowerStone1: 8","128.0","910da6e0-bf9e-45bc-822a-e3fe14bd757d"},
				{"com.koplagames.cn.week04.bundle07","Gem: 6500,  PowerStone1: 20,  PowerStone1: 20","328.0","910da6e0-bfa2-4154-9c9e-0052954f9808"},
				{"com.koplagames.cn.week04.bundle08","Gem: 14000,  PowerStone1: 40,  PowerStone1: 40","648.0","910da6e0-bfa5-4003-8b79-03cf3f2111eb"},

				{"com.koplagames.cn.week05.bundle01","HeroCosmetic: FacialHair005","30.0","910da6e0-bf8e-42ba-abbb-150be0fb6434"},
				{"com.koplagames.cn.week05.bundle02","HeroCosmetic: FacialHair006,  Chest: Luxury","68.0","910da6e0-bf91-4766-ac5e-6075df482756"},
				{"com.koplagames.cn.week05.bundle03","Gem: 6500,  PowerStone1: 30","328.0","910da6e0-bf94-4996-873e-f7d7399b8fdd"},
				{"com.koplagames.cn.week05.bundle04","Gem: 14000,  PowerStone1: 60","648.0","910da6e0-bf97-4baf-ae14-fdd8fea3a072"},
				{"com.koplagames.cn.week05.bundle05","PowerStone1: 4,  PowerStone1: 4","68.0","910da6e0-bf9b-48e0-87d6-41eee2a9d40d"},
				{"com.koplagames.cn.week05.bundle06","Gem: 2500,  PowerStone1: 8,  PowerStone1: 8","128.0","910da6e0-bf9e-45bc-822a-e3fe14bd757d"},
				{"com.koplagames.cn.week05.bundle07","Gem: 6500,  PowerStone1: 20,  PowerStone1: 20","328.0","910da6e0-bfa2-4154-9c9e-0052954f9808"},
				{"com.koplagames.cn.week05.bundle08","Gem: 14000,  PowerStone1: 40,  PowerStone1: 40","648.0","910da6e0-bfa5-4003-8b79-03cf3f2111eb"},

				{"com.koplagames.cn.week06.bundle01","HeroCosmetic: FacialHair005","30.0","910da6e0-bf8e-42ba-abbb-150be0fb6434"},
				{"com.koplagames.cn.week06.bundle02","HeroCosmetic: FacialHair006,  Chest: Luxury","68.0","910da6e0-bf91-4766-ac5e-6075df482756"},
				{"com.koplagames.cn.week06.bundle03","Gem: 6500,  PowerStone1: 30","328.0","910da6e0-bf94-4996-873e-f7d7399b8fdd"},
				{"com.koplagames.cn.week06.bundle04","Gem: 14000,  PowerStone1: 60","648.0","910da6e0-bf97-4baf-ae14-fdd8fea3a072"},
				{"com.koplagames.cn.week06.bundle05","PowerStone1: 4,  PowerStone1: 4","68.0","910da6e0-bf9b-48e0-87d6-41eee2a9d40d"},
				{"com.koplagames.cn.week06.bundle06","Gem: 2500,  PowerStone1: 8,  PowerStone1: 8","128.0","910da6e0-bf9e-45bc-822a-e3fe14bd757d"},
				{"com.koplagames.cn.week06.bundle07","Gem: 6500,  PowerStone1: 20,  PowerStone1: 20","328.0","910da6e0-bfa2-4154-9c9e-0052954f9808"},
				{"com.koplagames.cn.week06.bundle08","Gem: 14000,  PowerStone1: 40,  PowerStone1: 40","648.0","910da6e0-bfa5-4003-8b79-03cf3f2111eb"},

				{"com.koplagames.cn.week07.bundle01","HeroCosmetic: FacialHair005","30.0","910da6e0-bf8e-42ba-abbb-150be0fb6434"},
				{"com.koplagames.cn.week07.bundle02","HeroCosmetic: FacialHair006,  Chest: Luxury","68.0","910da6e0-bf91-4766-ac5e-6075df482756"},
				{"com.koplagames.cn.week07.bundle03","Gem: 6500,  PowerStone1: 30","328.0","910da6e0-bf94-4996-873e-f7d7399b8fdd"},
				{"com.koplagames.cn.week07.bundle04","Gem: 14000,  PowerStone1: 60","648.0","910da6e0-bf97-4baf-ae14-fdd8fea3a072"},
				{"com.koplagames.cn.week07.bundle05","PowerStone1: 4,  PowerStone1: 4","68.0","910da6e0-bf9b-48e0-87d6-41eee2a9d40d"},
				{"com.koplagames.cn.week07.bundle06","Gem: 2500,  PowerStone1: 8,  PowerStone1: 8","128.0","910da6e0-bf9e-45bc-822a-e3fe14bd757d"},
				{"com.koplagames.cn.week07.bundle07","Gem: 6500,  PowerStone1: 20,  PowerStone1: 20","328.0","910da6e0-bfa2-4154-9c9e-0052954f9808"},
				{"com.koplagames.cn.week07.bundle08","Gem: 14000,  PowerStone1: 40,  PowerStone1: 40","648.0","910da6e0-bfa5-4003-8b79-03cf3f2111eb"},

				{"com.koplagames.cn.week08.bundle01","HeroCosmetic: FacialHair005","30.0","910da6e0-bf8e-42ba-abbb-150be0fb6434"},
				{"com.koplagames.cn.week08.bundle02","HeroCosmetic: FacialHair006,  Chest: Luxury","68.0","910da6e0-bf91-4766-ac5e-6075df482756"},
				{"com.koplagames.cn.week08.bundle03","Gem: 6500,  PowerStone1: 30","328.0","910da6e0-bf94-4996-873e-f7d7399b8fdd"},
				{"com.koplagames.cn.week08.bundle04","Gem: 14000,  PowerStone1: 60","648.0","910da6e0-bf97-4baf-ae14-fdd8fea3a072"},
				{"com.koplagames.cn.week08.bundle05","PowerStone1: 4,  PowerStone1: 4","68.0","910da6e0-bf9b-48e0-87d6-41eee2a9d40d"},
				{"com.koplagames.cn.week08.bundle06","Gem: 2500,  PowerStone1: 8,  PowerStone1: 8","128.0","910da6e0-bf9e-45bc-822a-e3fe14bd757d"},
				{"com.koplagames.cn.week08.bundle07","Gem: 6500,  PowerStone1: 20,  PowerStone1: 20","328.0","910da6e0-bfa2-4154-9c9e-0052954f9808"},
				{"com.koplagames.cn.week08.bundle08","Gem: 14000,  PowerStone1: 40,  PowerStone1: 40","648.0","910da6e0-bfa5-4003-8b79-03cf3f2111eb"},

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
					String remote_guid = (String) json_result1.get("guid");
					if(ProductionList.length>index) {
						ProductionList[index][0] = iap_name;
						ProductionList[index][1] = remote_des;
						ProductionList[index][2] = remote_price;
						ProductionList[index][3] = remote_guid;
						index++;
					}
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
		MercuryActivity.LogLocal("[MercuryConst] ProductionIDCallBack callback"+" inbase->"+inbase);
		inbase.appinterface.ProductionIDCallBack(strError);
	}

	public void OnClaimReward(String strError, InAppBase inbase) {
		MercuryActivity.LogLocal("[MercuryConst] OnClaimReward callback->"+strError+" inbase->"+inbase);
		inbase.appinterface.OnClaimReward(strError);
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
