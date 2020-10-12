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

				{"com.koplagames.cn.set002.bundle01","bundle","6.0","91a681cb-fe91-4fe3-a2b6-6ff8fd280f5a"},
				{"com.koplagames.cn.set002.bundle02","bundle","30.0","91a681f5-e7f7-442b-9f59-8340ca505caf"},
				{"com.koplagames.cn.set002.bundle03","bundle","68.0","91a6820e-f601-4d4a-8cb0-9cbd280f2726"},
				{"com.koplagames.cn.set002.bundle04","bundle","128.0","91a68230-120e-40a1-ab8c-d19f207d9d7d"},
				{"com.koplagames.cn.set002.bundle05","bundle","328.0","91a68247-9abe-455d-a318-8066aa07ef32"},
				{"com.koplagames.cn.set002.bundle06","bundle","648.0","91a68266-ae61-4138-a75a-946bb5bd4413"},
				{"com.koplagames.cn.set002.bundle07","bundle","18.0","91a68282-2764-4068-af34-7beb5ada6881"},
				{"com.koplagames.cn.set002.bundle08","bundle","256.0","91a68298-d7ae-4d43-a72a-eba136eed9a0"},

				{"com.koplagames.nsk2.carnivalrewardpass.10","CarnivalRewardPass","68.0","910da6e0-bf42-4219-9648-477fb4e8f211"},


				{"com.koplagames.nsk2.carnivalrewardpass.15","CarnivalRewardPass","98.0","910da6e0-bf45-4313-9691-70c7041d7d59"},
				{"com.koplagames.nsk2.carnivalrewardpass.20","CarnivalRewardPass","128.0","910da6e0-bf48-499d-90da-4a4781f896c2"},
				{"com.koplagames.nsk2.carnivalrewardpass.3","CarnivalRewardPass","18.0","910da6e0-bf4d-4607-8ac3-f088184a8893"},
				{"com.koplagames.nsk2.carnivalrewardpass.5","CarnivalRewardPass","30.0","910da6e0-bf50-4c0a-b11d-0aaa40bc5c50"},
				{"com.koplagames.nsk2.carnivalrewardpass.8","CarnivalRewardPass","50.0","910da6e0-bf53-4922-b360-25636194eb0a"},
				{"com.koplagames.nsk2.multirentry.d1.1","Pet: Pet005,  PetCosmetic: PetCosmeticPet005_1","30.0","910da6e0-bf56-4fed-b9ec-3bbb52df0689"},
				{"com.koplagames.nsk2.multirentry.d1.2","Pet: Pet005,  PetCosmetic: PetCosmeticPet005_1","68.0","910da6e0-bf59-4e81-9302-01dd5044c7f3"},
				{"com.koplagames.nsk2.multirentry.d1.3","Pet: Pet005,  PetCosmetic: PetCosmeticPet005_1","128.0","910da6e0-bf5c-4fc1-a6f6-3752091032ab"},
				{"com.koplagames.nsk2.multirentry.d1.4","Pet: Pet005,  PetCosmetic: PetCosmeticPet005_1","258.0","910da6e0-bf5f-4d68-8cf3-2e9756647d8b"},
				{"com.koplagames.nsk2.multirentry.d1.5","Pet005: 10,  PetCosmetic: PetCosmeticPet005_1","30.0","910da6e0-bf62-4d42-9cb7-598655525a75"},
				{"com.koplagames.nsk2.multirentry.d1.6","Pet005: 40,  PetCosmetic: PetCosmeticPet005_1","68.0","910da6e0-bf67-46f4-847a-70d1513ae60f"},
				{"com.koplagames.nsk2.multirentry.d1.7","Pet005: 100,  PetCosmetic: PetCosmeticPet005_1","128.0","910da6e0-bf6a-4536-ab8b-21917f8291a9"},
				{"com.koplagames.nsk2.multirentry.pj.16","Pet: Pet005,  PetCosmetic: PetCosmeticPet005_1","128.0","910da6e0-bf6d-4468-98c6-341ec78e61a5"},
				{"com.koplagames.nsk2.multirentry.pj.17","Keys: 30,  Chest: Luxury,  Chest: Megapack","68.0","910da6e0-bf70-4ae8-9b7e-f0852ca46626"},
				{"com.koplagames.nsk2.multirentry.pj.2","Chest: CardPackGold,  Keys: 5","6.0","910da6e0-bf73-431b-a488-5df26249dc31"},
				{"com.koplagames.nsk2.multirentry.pj.5","Pet: Pet005,  PetCosmetic: PetCosmeticPet005_1","30.0","910da6e0-bf76-498f-b119-5dccd350b8d0"},
				{"com.koplagames.nsk2.multirentry.pj.6","Pet: Pet005,  PetCosmetic: PetCosmeticPet005_1","68.0","910da6e0-bf79-4511-b8eb-b2602b2f4894"},
				{"com.koplagames.nsk2.multirentry.pj.7","Keys: 20,  Chest: Luxury,  Chest: Megapack","128.0","910da6e0-bf7c-499d-bebd-791683169830"},
				{"com.koplagames.nsk2.multirentry.special.1","Gem: 2500,  PowerStone1: 10","128.0","910da6e0-bf7f-4504-9619-358f1f082976"},
				{"com.koplagames.nsk2.multirentry.special.10","Luxury Chest: 2,  Powerstone1: 30","328.0","910da6e0-bf84-4ac9-a93a-874b036f2ca8"},
				{"com.koplagames.nsk2.multirentry.special.11","HeroCosmetic: FacialHair003,  Chest: Luxury","68.0","910da6e0-bf87-4bc6-a423-2e0321962ed8"},
				{"com.koplagames.nsk2.multirentry.special.12","Tokens: 400,  HeroCosmetic: FacialHair001","68.0","910da6e0-bf8a-4b86-b2fe-e63dafaf8cc6"},
				{"com.koplagames.nsk2.multirentry.special.13","HeroCosmetic: FacialHair005","30.0","910da6e0-bf8e-42ba-abbb-150be0fb6434"},
				{"com.koplagames.nsk2.multirentry.special.14","HeroCosmetic: FacialHair006,  Chest: Luxury","68.0","910da6e0-bf91-4766-ac5e-6075df482756"},
				{"com.koplagames.nsk2.multirentry.special.2","Gem: 6500,  PowerStone1: 30","328.0","910da6e0-bf94-4996-873e-f7d7399b8fdd"},
				{"com.koplagames.nsk2.multirentry.special.3","Gem: 14000,  PowerStone1: 60","648.0","910da6e0-bf97-4baf-ae14-fdd8fea3a072"},
				{"com.koplagames.nsk2.multirentry.special.4","PowerStone1: 4,  PowerStone1: 4","68.0","910da6e0-bf9b-48e0-87d6-41eee2a9d40d"},
				{"com.koplagames.nsk2.multirentry.special.5","Gem: 2500,  PowerStone1: 8,  PowerStone1: 8","128.0","910da6e0-bf9e-45bc-822a-e3fe14bd757d"},
				{"com.koplagames.nsk2.multirentry.special.6","Gem: 6500,  PowerStone1: 20,  PowerStone1: 20","328.0","910da6e0-bfa2-4154-9c9e-0052954f9808"},
				{"com.koplagames.nsk2.multirentry.special.7","Gem: 14000,  PowerStone1: 40,  PowerStone1: 40","648.0","910da6e0-bfa5-4003-8b79-03cf3f2111eb"},
				{"com.koplagames.nsk2.multirentry.special.8","Grande Chest: 1,  Powerstone1: 4","68.0","910da6e0-bfa9-478e-96aa-75d0d8286830"},
				{"com.koplagames.nsk2.multirentry.special.9","Luxury Chest: 1,  Powerstone1: 10","128.0","910da6e0-bfad-491d-90cf-40b33c8e5732"},
				{"com.koplagames.nsk2.multirentry.starterbundle.2","Gem: 2000,  LuxuryChest: 2,  MegaPack: 2","128.0","910da6e0-bfb2-45a1-bd2f-4089159864dd"},
				{"com.koplagames.nsk2.multirentry.starterbundle.3","Gem: 1500,  MegaPack: 2","68.0","910da6e0-bfb5-4c37-842c-1b22f5fce333"},
				{"com.koplagames.nsk2.multirentry.starterbundle.4","Gem: 200,  Pet: Pet005","12.0","910da6e0-bfb8-41c9-b9f7-0007db63013f"},
				{"com.koplagames.nsk2.multirentry.starterbundle.5","Gem: 500,  GrandeChest: 1","30.0","910da6e0-bfbb-47b2-8535-fdb895c0d46e"},
				{"com.koplagames.nsk2.multirentry.starterbundle.6","Gem: 1000,  LuxuryChest: 1","68.0","910da6e0-bfbf-4c7b-ad84-2c60ab93122b"},
				{"com.koplagames.nsk2.multirentry.tour.1","Gems: 550,  Grande: 1,  Key: 20","30.0","910da6e0-bfc2-4ba4-81a4-851a6d4355f5"},
				{"com.koplagames.nsk2.multirentry.tour.10","Gems: 2000,  Regal Chest: 2","258.0","910da6e0-bfc5-48dc-b65c-5aed4790f264"},
				{"com.koplagames.nsk2.multirentry.tour.11","Gems: 2500,  Boreal Chest: 1","128.0","910da6e0-bfc8-423c-918b-a39b70704711"},
				{"com.koplagames.nsk2.multirentry.tour.12","Gems: 6500,  Boreal Chest: 2","258.0","910da6e0-bfcc-4a10-b462-bea42f17184d"},
				{"com.koplagames.nsk2.multirentry.tour.13","Gem: 1200,  Gem: 1200","68.0","910da6e0-bfcf-4101-af31-c152ee486b78"},
				{"com.koplagames.nsk2.multirentry.tour.14","Gem: 2500,  Gem: 2500","128.0","910da6e0-bfd3-416d-ae76-100e03a78685"},
				{"com.koplagames.nsk2.multirentry.tour.15","Gem: 6500,  Gem: 6500","328.0","910da6e0-bfd6-4845-8d46-2557f9493602"},
				{"com.koplagames.nsk2.multirentry.tour.16","Gem: 14000,  Gem: 14000","648.0","910da6e0-bfd9-4597-9f68-25d4a295fe77"},
				{"com.koplagames.nsk2.multirentry.tour.17","Luxury Chest: 3","128.0","910da6e0-bfdd-4c28-aa1d-d4458f55d10c"},
				{"com.koplagames.nsk2.multirentry.tour.18","Chest: Megapack,  Chest: Megapack,  Chest: Megapack,  Chest: Megapack,   Chest: Megapack,  Chest: Luxury,  Chest: Luxury,  Chest: Luxury,  Gems: 1500","328.0","910da6e0-bfe0-441d-be2b-da232c6edf80"},
				{"com.koplagames.nsk2.multirentry.tour.19","Gems: 5000,  Siegemaster Chest: 5","648.0","910da6e0-bfe3-48d4-94ea-72b9e9d7e4f8"},
				{"com.koplagames.nsk2.multirentry.tour.23","GrandeChests: 3","68.0","910da6e0-bfe6-4a8f-af17-926af77e6349"},
				{"com.koplagames.nsk2.multirentry.tour.24","Gem: 2500,  PowerStone1: 10","128.0","910da6e0-bfea-45f6-8e60-0acfc6b539bd"},
				{"com.koplagames.nsk2.multirentry.tour.25","Gem: 6500,  PowerStone1: 30","328.0","910da6e0-bfed-4520-8452-41aa952419de"},
				{"com.koplagames.nsk2.multirentry.tour.26","Gems: 14000,  Luxury: 10,  Grande: 6","648.0","910da6e0-bff2-4c98-904d-2d919a49bd3e"},
				{"com.koplagames.nsk2.multirentry.tour.27","Gems: 1200,  Luxury: 1,  Key: 20","68.0","910da6e0-bff5-4925-8a93-69e965578a31"},
				{"com.koplagames.nsk2.multirentry.tour.28","Gems: 2500,  Luxury: 2,  Grande: 1","128.0","910da6e0-bff8-49d4-8ad4-a1194f27f931"},
				{"com.koplagames.nsk2.multirentry.tour.29","Gems: 6500,  Luxury: 5,  Grande: 3","328.0","910da6e0-bffb-4b27-a533-81f7654f0bb4"},
				{"com.koplagames.nsk2.multirentry.tour.3","Gems: 1000,  Dragonscale Chest: 1","128.0","910da6e0-bfff-457b-b150-42dfd4a26c7f"},
				{"com.koplagames.nsk2.multirentry.tour.4","Gems: 2000,  Dragonscale Chest: 2","258.0","910da6e0-c002-4e5c-8f8a-d659452499d3"},
				{"com.koplagames.nsk2.multirentry.tour.40","Gems: 1200,  Key: 40","68.0","910da6e0-c005-4e64-a624-a45822cdbe96"},
				{"com.koplagames.nsk2.multirentry.tour.41","Gems: 2500,  Key: 40,   Grande: 1","128.0","910da6e0-c008-4485-82fd-14e9cb8f6196"},
				{"com.koplagames.nsk2.multirentry.tour.42","Gems: 6500,  Key: 40,   Luxury: 2","328.0","910da6e0-c00c-4bef-9c25-c4a78b1b1d7d"},
				{"com.koplagames.nsk2.multirentry.tour.43","Gems: 5000,  Regal Chest: 5","648.0","910da6e0-c00f-47c4-ab28-1b22a48853fe"},
				{"com.koplagames.nsk2.multirentry.tour.44","Gem: 550,  Gem: 550","30.0","910da6e0-c014-452f-8b27-077893ddaba1"},
				{"com.koplagames.nsk2.multirentry.tour.45","Gems: 1200,  EpicMark: 3600","68.0","910da6e0-c017-43e1-95a2-1a71a93b4598"},
				{"com.koplagames.nsk2.multirentry.tour.46","Gems: 1000,  Siegemaster Chest: 1","128.0","910da6e0-c01c-4c9b-b2fb-e6fd3c5f6952"},
				{"com.koplagames.nsk2.multirentry.tour.47","Gems: 2000,  Siegemaster Chest: 2","328.0","910da6e0-c01f-4726-b503-56368582c9e7"},
				{"com.koplagames.nsk2.multirentry.tour.48","Gems: 5000,  Boreal Chest: 5","648.0","910da6e0-c022-4cc0-99f4-c5d7f0e7e709"},
				{"com.koplagames.nsk2.multirentry.tour.5","Gems: 1000,  Boreal Chest: 1","128.0","910da6e0-c026-4330-9aca-7fec7a31b2b1"},
				{"com.koplagames.nsk2.multirentry.tour.6","Gems: 2000,  Boreal Chest: 2","258.0","910da6e0-c029-406c-8278-638420ee9685"},
				{"com.koplagames.nsk2.multirentry.tour.7","Gems: 1000,  Miasma Chest: 1","128.0","910da6e0-c02e-48b5-a6cb-ecce6c69f7fb"},
				{"com.koplagames.nsk2.multirentry.tour.8","Gems: 2000,  Miasma Chest: 2","258.0","910da6e0-c031-4d94-bba3-2671eb12f6b3"},
				{"com.koplagames.nsk2.multirentry.tour.9","Gems: 1000,  Regal Chest: 1","128.0","910da6e0-c034-41b0-aa19-148888abf359"},
				{"com.koplagames.nsk2.pet.0.001.0.10","Gems: 1000,  Token: 200","68.0","910da6e0-c038-45f2-a1a2-c41d979e473e"},
				{"com.koplagames.nsk2.pet.0.001.0.2","Gems: 200,  Keys: 10","12.0","910da6e0-c03b-41a5-b2b8-c02a492d2c1f"},
				{"com.koplagames.nsk2.pet.0.001.0.20","Gems: 2000,  Token: 400","128.0","910da6e0-c03e-496b-b719-a009f0958387"},
				{"com.koplagames.nsk2.pet.0.001.0.5","Token: 50,  Pet: Pet012","30.0","910da6e0-c041-457c-8083-2b8f37ffbbf9"},
				{"com.koplagames.nsk2.pet.0.001.0.50","Gems: 5000,  Token: 1000","328.0","910da6e0-c044-4371-80e9-23ab85d6ea0b"},
				{"com.koplagames.nsk2.pet.0.002.0.10","Gems: 1000,  Megapack: 1","68.0","910da6e0-c046-4d8b-92e7-4ff29aae696d"},
				{"com.koplagames.nsk2.pet.0.002.0.2","Pet: Pet010,  PetCosmetic: PetCosmeticPet010_1","12.0","910da6e0-c049-42d0-bfa6-d4a0efc94b5b"},
				{"com.koplagames.nsk2.pet.0.002.0.20","Gems: 1000,  Token: 600","128.0","910da6e0-c04c-422c-9017-c195fcb314e9"},
				{"com.koplagames.nsk2.pet.0.002.0.5","Pet: Pet010,  PetCosmetic: PetCosmeticPet010_1","30.0","910da6e0-c04f-4f3b-b290-2433193921c5"},
				{"com.koplagames.nsk2.pet.0.002.0.50","Gems: 2000,  Token: 1600","328.0","910da6e0-c052-45b0-afbb-2976430dde4d"},
				{"com.koplagames.nsk2.pet.0.003.0.10","Token: 100,  Pet: Pet002","68.0","910da6e0-c055-4c67-99c3-26d7f8098eb2"},
				{"com.koplagames.nsk2.pet.0.003.0.2","Token: 50,  Pet: Pet002","12.0","910da6e0-c058-41b3-8626-df02d58afffd"},
				{"com.koplagames.nsk2.pet.0.003.0.20","Token: 200,  Pet: Pet002","128.0","910da6e0-c05d-4cf8-aedd-e0e01aa5db6b"},
				{"com.koplagames.nsk2.pet.0.003.0.5","Token: 50,  Pet: Pet002","30.0","910da6e0-c060-4376-b25e-e57431cd8420"},
				{"com.koplagames.nsk2.pet.0.003.0.50","LuxuryChests: 3,  Gems: 1000","328.0","910da6e0-c063-4283-9c40-72d15c3c7255"},
				{"com.koplagames.nsk2.pet.0.004.0.10","Token: 100,  Pet: Pet003","68.0","910da6e0-c066-4e9d-a0bf-c1ac1c02ea96"},
				{"com.koplagames.nsk2.pet.0.004.0.2","Token: 50,  Pet: Pet003","12.0","910da6e0-c069-461e-81e1-bdf48bd28ca7"},
				{"com.koplagames.nsk2.pet.0.004.0.20","Token: 200,  Pet: Pet003","128.0","910da6e0-c06c-4dcd-b445-ed5b867de5a4"},
				{"com.koplagames.nsk2.pet.0.004.0.5","Token: 50,  Pet: Pet003","30.0","910da6e0-c06f-4b99-bd91-b7400bdd40d6"},
				{"com.koplagames.nsk2.pet.0.004.0.50","Token: 400,  Pet: Pet003","328.0","910da6e0-c072-489b-8bc5-aa42bc5cad74"},
				{"com.koplagames.nsk2.pj.0.001.0.10","Chest: Megapack,  Chest: Luxury,  Key: 25","68.0","910da6e0-c075-4f4f-9f17-ca40d124a1f9"},
				{"com.koplagames.nsk2.pj.0.001.0.2","Chest: Grande,  Key: 5","12.0","910da6e0-c07a-4a59-bcb0-f2e92fd186d3"},
				{"com.koplagames.nsk2.pj.0.001.0.20","2 Chest: Megapack,  2 Chest: Luxury,  Key: 25","128.0","910da6e0-c07d-497a-b877-d85c440ee862"},
				{"com.koplagames.nsk2.pj.0.001.0.40","4 Chest: Megapack,  4 Chest: Luxury,  Key: 50","258.0","910da6e0-c082-471a-8c8f-5291a0d1e3f8"},
				{"com.koplagames.nsk2.pj.0.001.0.5","Chest: ClassicPack,  Chest: Grande,  Key: 25","30.0","910da6e0-c086-4e2e-abd7-5d4439f52fa0"},
				{"com.koplagames.nsk2.pj.0.002.0.13","Skill: FlameBreath,  Item: Legs006,  Gem: 1000","88.0","910da6e0-c089-4616-91e2-764f0989970e"},
				{"com.koplagames.nsk2.pj.0.002.0.25","Skill: FlameBreath,  Item: Legs006,  Gem: 3000","163.0","910da6e0-c08c-4e1c-92bd-e8d60c5345f7"},
				{"com.koplagames.nsk2.pj.0.002.0.4","Skill: FlameBreath,  Item: Legs006,  Gem: 500","25.0","910da6e0-c090-4b19-92d3-4e177df81df1"},
				{"com.koplagames.nsk2.pj.0.002.0.50","Skill: FlameBreath,  Item: Legs006,  Gem: 3000","328.0","910da6e0-c095-4c10-bfa6-e35daabd9386"},
				{"com.koplagames.nsk2.pj.0.002.0.8","Skill: FlameBreath,  Item: Legs006,  Gem: 200","50.0","910da6e0-c098-4d2d-9136-fa567224bdbd"},
				{"com.koplagames.nsk2.pj.0.003.0.10","Coin: 300000,  Token: 200,  Key: 20","68.0","910da6e0-c09c-429b-95fc-f828edc59a91"},
				{"com.koplagames.nsk2.pj.0.003.0.2","Coin: 20000,  Token: 100,  Key: 10","12.0","910da6e0-c09f-4a61-a455-1d1f50836f83"},
				{"com.koplagames.nsk2.pj.0.003.0.20","Coin: 500000,  Token: 500,  Key: 20","128.0","910da6e0-c0a2-494b-88e3-49a1f6bc1f07"},
				{"com.koplagames.nsk2.pj.0.003.0.40","Coin: 500000,  Token: 1300,  Key: 20","258.0","910da6e0-c0a5-497b-b873-cfcd8c2cb421"},
				{"com.koplagames.nsk2.pj.0.003.0.5","Coin: 50000,  Token: 100,  Key: 20","30.0","910da6e0-c0a8-4752-b04b-f54f5e1a1b0b"},
				{"com.koplagames.nsk2.pj.0.004.0.10","GrandeChests: 1,  MegaPacks: 1,  Keys: 20","68.0","910da6e0-c0ac-434d-9879-6a06e4ba497b"},
				{"com.koplagames.nsk2.pj.0.004.0.2","ClassicPack: 2,  Keys: 15","12.0","910da6e0-c0af-49be-a37d-53876eb2a1b7"},
				{"com.koplagames.nsk2.pj.0.004.0.20","LuxuryChests: 1,  MegaPacks: 2,  Keys: 20","128.0","910da6e0-c0b2-4a78-bf78-89030f5ea4c8"},
				{"com.koplagames.nsk2.pj.0.004.0.4","MegaPacks: 1,  Keys: 10","25.0","910da6e0-c0b5-4ff8-8042-4ddcd86ba3c4"},
				{"com.koplagames.nsk2.pj.0.004.0.40","LuxuryChests: 2,  MegaPacks: 4,  Keys: 20","258.0","910da6e0-c0b8-4b6c-8cc3-5d6cd553fd83"},
				{"com.koplagames.nsk2.pj.0.004.0.5","MegaPacks: 1,  Keys: 10","30.0","910da6e0-c0bb-4192-881a-dd7c8e427d3b"},
				{"com.koplagames.nsk2.pj.0.004.0.50","LuxuryChests: 4,  MegaPacks: 3,  Keys: 20","328.0","910da6e0-c0be-4e51-bc43-a1d3e64afdf5"},
				{"com.koplagames.nsk2.pj.0.004.0.80","LuxuryChests: 6,  MegaPacks: 6,  Keys: 20","518.0","910da6e0-c0c1-4d97-9194-fe500172c2e5"},
				{"com.koplagames.nsk2.pj.0.005.0.1","Gem: 300,  Token: 50,  Key: 5","6.0","910da6e0-c0c4-4e19-ad1e-eed8701f2051"},
				{"com.koplagames.nsk2.pj.0.005.0.10","Gem: 600,  Token: 400,  Key: 20","68.0","910da6e0-c0c9-47ea-9b53-bb49ff693871"},
				{"com.koplagames.nsk2.pj.0.005.0.2","Gem: 300,  Token: 50,  Key: 5","12.0","910da6e0-c0cc-4767-9749-69916ff00290"},
				{"com.koplagames.nsk2.pj.0.005.0.20","Gem: 1500,  Token: 450,  Key: 20","128.0","910da6e0-c0d0-403b-858f-6a1f66934270"},
				{"com.koplagames.nsk2.pj.0.005.0.40","Gem: 4000,  Token: 1000,  Key: 20","328.0","910da6e0-c0d3-48fc-826b-2273f08afea9"},
				{"com.koplagames.nsk2.pj.0.005.0.5","Gem: 550,  Token: 100,  Key: 20","30.0","910da6e0-c0d6-4052-a62f-69513fa64f55"},
				{"com.koplagames.nsk2.pj.0.006.0.10","Chest: Luxury,  PowerStone2: 1,  Key: 20","68.0","910da6e0-c0da-4a24-8da1-ed9de014addf"},
				{"com.koplagames.nsk2.pj.0.006.0.100","Chest: Luxury,  Chest: Luxury,  Chest: Luxury,  PowerStone4: 2,  Key: 20","648.0","910da6e0-c0df-43e0-a9b9-fb5a4a0e9d14"},
				{"com.koplagames.nsk2.pj.0.006.0.2","Chest: Grande,  PowerStone1: 1","12.0","910da6e0-c0e2-42f7-a147-64fc1767f4b4"},
				{"com.koplagames.nsk2.pj.0.006.0.20","Chest: Luxury,  PowerStone3: 1,  Key: 20","128.0","910da6e0-c0e5-45ba-b07a-1270a556631c"},
				{"com.koplagames.nsk2.pj.0.006.0.4","Chest: Grande,  PowerStone1: 2","25.0","910da6e0-c0e9-497e-8300-4ee444271773"},
				{"com.koplagames.nsk2.pj.0.006.0.40","Chest: Luxury,  PowerStone4: 1,  Key: 20","258.0","910da6e0-c0ec-4ae0-a649-0ad414a47d39"},
				{"com.koplagames.nsk2.pj.0.006.0.5","Chest: Grande,  PowerStone2: 1","30.0","910da6e0-c0ef-42fb-b4d0-2eb2e68bf984"},
				{"com.koplagames.nsk2.pj.0.006.0.50","Chest: Luxury,  Chest: Luxury,  PowerStone4: 1,  Key: 20","328.0","910da6e0-c0f3-469c-b477-c44c1aacdd06"},
				{"com.koplagames.nsk2.pj.0.006.1.2","Chest: Luxury,  PowerStone1: 1","12.0","910da6e0-c0f8-4238-bf9f-8cc679512e3a"},
				{"com.koplagames.nsk2.pj.0.007.0.1","Coin: 10000,  Token: 100,  Key: 20","6.0","910da6e0-c0fa-407d-a80d-065d51409c6b"},
				{"com.koplagames.nsk2.pj.0.007.0.10","Coin: 250000,  Token: 350,  Key: 20","68.0","910da6e0-c0fd-40d4-ae01-a8d86eed6d70"},
				{"com.koplagames.nsk2.pj.0.007.0.2","Coin: 25000 Token: 100,  Key: 10","12.0","910da6e0-c100-43af-b8ac-0f1fbe4ace5c"},
				{"com.koplagames.nsk2.pj.0.007.0.20","Coin: 500000,  Token: 500,  Key: 20","128.0","910da6e0-c103-4926-ab69-4be1e8d265cb"},
				{"com.koplagames.nsk2.pj.0.007.0.40","Coin: 1500000,  Token: 1000,  Key: 20","258.0","910da6e0-c106-4989-9607-f3d881a892ba"},
				{"com.koplagames.nsk2.pj.0.007.0.5","Coin: 100000,  Token: 150,  Key: 20","30.0","910da6e0-c10a-4864-ad05-219bf7f1a1e7"},
				{"com.koplagames.nsk2.pj.0.008.0.10","1 Chest: ClassicPack,  1 Chest: Luxury,  Gem: 300","68.0","910da6e0-c10d-4bb0-ad3c-cba6be8654ef"},
				{"com.koplagames.nsk2.pj.0.008.0.2","1 Chest: ClassicPack,  Gem: 200","12.0","910da6e0-c110-45a5-87e1-0d52e98bc152"},
				{"com.koplagames.nsk2.pj.0.008.0.20","2 Chest: Megapack,  1 Chest: Luxury,  Gem: 500","128.0","910da6e0-c113-44f7-9753-6cbffeec28ea"},
				{"com.koplagames.nsk2.pj.0.008.0.4","1 Chest: ClassicPack,  1 Chest: Grande","25.0","910da6e0-c116-4606-8a63-a9fec58476e3"},
				{"com.koplagames.nsk2.pj.0.008.0.40","2 Chest: Megapack,  3 Chest: Luxury,  Gem: 1500","258.0","910da6e0-c119-4cbe-b12a-d008be885056"},
				{"com.koplagames.nsk2.pj.0.008.0.5","1 Chest: ClassicPack,  1 Chest: Luxury","30.0","910da6e0-c11c-4516-b389-e4c12ef178ea"},
				{"com.koplagames.nsk2.pj.0.008.0.50","3 Chest: Megapack,  3 Chest: Luxury,  Gem: 2500","328.0","910da6e0-c11f-4ae1-bcaa-61162d9b82f5"},
				{"com.koplagames.nsk2.pj.0.008.0.80","4 Chest: Megapack,  4 Chest: Luxury,  Gem: 6000","518.0","910da6e0-c122-480b-8cde-857f7cb54097"},
				{"com.koplagames.nsk2.pj.0.008.1.2","1 Chest: Luxury,  Gem: 500","12.0","910da6e0-c125-444c-a81d-a0877ef880ff"},
				{"com.koplagames.nsk2.pj.0.009.0.10","PowerStone1: 2,  PowerStone2: 2,  Key: 10","68.0","910da6e0-c128-4343-8ced-baa157221013"},
				{"com.koplagames.nsk2.pj.0.009.0.100","PowerStone3: 2,  PowerStone4: 1,  Key: 10","648.0","910da6e0-c12c-4cf7-b861-855ad7fbc5d2"},
				{"com.koplagames.nsk2.pj.0.009.0.2","Key: 10,  PowerStone1 1","12.0","910da6e0-c12f-4a48-9c81-cee024ad7efc"},
				{"com.koplagames.nsk2.pj.0.009.0.20","PowerStone1: 1,  PowerStone3: 1,  Key: 10","128.0","910da6e0-c134-406f-89d2-40d59a8eb477"},
				{"com.koplagames.nsk2.pj.0.009.0.5","PowerStone1: 1,  PowerStone2: 1,  Key: 10","30.0","910da6e0-c137-4c5c-9b02-a8a8c0ddf028"},
				{"com.koplagames.nsk2.pj.0.009.0.50","PowerStone2: 2,  PowerStone3: 2,  Key: 10","328.0","910da6e0-c13a-4440-a555-ab006fbd6499"},
				{"com.koplagames.nsk2.pj.0.010.0.10","Gem: 600,  Token: 400,  Key: 20","68.0","910da6e0-c13e-467d-9109-1bda11b0ef2d"},
				{"com.koplagames.nsk2.pj.0.010.0.2","Gem: 300,  Token: 50,  Key: 5","12.0","910da6e0-c143-4e18-ad23-2dc8dab517ae"},
				{"com.koplagames.nsk2.pj.0.010.0.20","Gem: 1500,  Token: 450,  Key: 20","128.0","910da6e0-c146-4f33-8e76-8fbf9b565699"},
				{"com.koplagames.nsk2.pj.0.010.0.5","Gem: 550,  Token: 100,  Key: 20","30.0","910da6e0-c149-40d8-b421-3656b59f22e0"},
				{"com.koplagames.nsk2.pj.0.010.0.50","Gem: 4000,  Token: 1000,  Key: 20","328.0","910da6e0-c14c-4a00-b0c5-13be75df641b"},
				{"com.koplagames.nsk2.pj.0.010.0.80","Gem: 5000,  Token: 2000,  Key: 20","518.0","910da6e0-c14f-43ad-8865-2738c2fd1c48"},
				{"com.koplagames.nsk2.pj.0.011.0.10","Chest: Megapack,  Chest: Luxury,  Key: 10","68.0","910da6e0-c152-4bac-b152-d430ca0693af"},
				{"com.koplagames.nsk2.pj.0.011.0.2","Chest: Grande,  Key: 5","12.0","910da6e0-c156-4bd8-a682-67ce49591d20"},
				{"com.koplagames.nsk2.pj.0.011.0.20","2 Chest: Megapack,  2 Chest: Luxury,  Key: 10","128.0","910da6e0-c15a-4bac-9b0e-754086e99241"},
				{"com.koplagames.nsk2.pj.0.011.0.40","4 Chest: Megapack,  4 Chest: Luxury,  Key: 10","258.0","910da6e0-c15f-4cb5-baa8-4b02dc0b26e5"},
				{"com.koplagames.nsk2.pj.0.011.0.5","Chest: ClassicPack,  Chest: Grande,  Key: 10","30.0","910da6e0-c162-4513-9af6-77fe2648ec7f"},
				{"com.koplagames.nsk2.pj.0.011.0.80","6 Chest: Megapack,  6 Chest: Luxury,  Key: 10","518.0","910da6e0-c165-4020-90d9-5305ba3d0612"},
				{"com.koplagames.nsk2.pj.0.012.0.10","Chest: Luxury,  PowerStone2: 1,  Key: 20","68.0","910da6e0-c169-49cc-b2e9-9906f600ec70"},
				{"com.koplagames.nsk2.pj.0.012.0.100","Chest: Luxury,  Chest: Luxury,  Chest: Luxury,  PowerStone4: 2,  Key: 20","648.0","910da6e0-c16c-4227-8e32-85940dc95f7a"},
				{"com.koplagames.nsk2.pj.0.012.0.2","Chest: Grande,  PowerStone1: 1","12.0","910da6e0-c16f-4367-992b-ea9d7c2bae77"},
				{"com.koplagames.nsk2.pj.0.012.0.20","Chest: Luxury,  PowerStone3: 1,  Key: 20","128.0","910da6e0-c172-4b9c-af99-e9e4778f4c75"},
				{"com.koplagames.nsk2.pj.0.012.0.4","Chest: Grande,  PowerStone1: 2","25.0","910da6e0-c175-4fe3-8141-f2fbf2202a4b"},
				{"com.koplagames.nsk2.pj.0.012.0.40","Chest: Luxury,  PowerStone4: 1,  Key: 20","258.0","910da6e0-c178-4af0-8ce5-4691d7e69cd0"},
				{"com.koplagames.nsk2.pj.0.012.0.5","Chest: Grande,  PowerStone2: 1","30.0","910da6e0-c17c-4514-9047-d1e7d2c33fb5"},
				{"com.koplagames.nsk2.pj.0.012.0.50","Chest: Luxury,  Chest: Luxury,  PowerStone4: 1,  Key: 20","328.0","910da6e0-c17f-458b-97b0-32e64129664c"},
				{"com.koplagames.nsk2.pj.0.013.0.10","Coin: 250000,  Token: 350,  Key: 20","68.0","910da6e0-c182-4b6c-82f7-f5ffde353356"},
				{"com.koplagames.nsk2.pj.0.013.0.2","Coin: 25000 Token: 100,  Key: 10","12.0","910da6e0-c185-4a1f-bb10-349725767eb8"},
				{"com.koplagames.nsk2.pj.0.013.0.20","Coin: 500000,  Token: 500,  Key: 20","128.0","910da6e0-c188-4197-81ce-bbc40d6d6f1e"},
				{"com.koplagames.nsk2.pj.0.013.0.40","Coin: 1500000,  Token: 1000,  Key: 20","258.0","910da6e0-c18c-4bad-8668-897a30ae0465"},
				{"com.koplagames.nsk2.pj.0.013.0.5","Coin: 100000,  Token: 150,  Key: 20","30.0","910da6e0-c18f-4fdd-b4a5-a4f5e0d82350"},
				{"com.koplagames.nsk2.pj.0.013.0.80","Coin: 1500000,  Token: 2500,  Key: 20","518.0","910da6e0-c192-4b4a-887d-4f2e65a6d9d3"},
				{"com.koplagames.nsk2.pj.1.001.0.10","GrandeChests: 1,  ClassicPacks: 1,  Keys: 20","68.0","910da6e0-c196-4e76-a173-45a323a1ccf1"},
				{"com.koplagames.nsk2.pj.1.001.0.2","ClassicPacks: 1,  Keys: 5","12.0","910da6e0-c199-46c1-be3f-1573841f2143"},
				{"com.koplagames.nsk2.pj.1.001.0.20","MegaPacks: 1,  LuxuryChests: 1,  Keys: 10","128.0","910da6e0-c19c-49a2-905e-d3a9030e9716"},
				{"com.koplagames.nsk2.pj.1.001.0.40","MegaPacks: 2,  LuxuryChests: 2,  Keys: 20","258.0","910da6e0-c1a1-4dec-b0a7-b2ecd797cd1b"},
				{"com.koplagames.nsk2.pj.1.001.0.5","GrandeChests: 1,  Keys: 5","30.0","910da6e0-c1a6-42a8-9f24-92b0e1dc9868"},
				{"com.koplagames.nsk2.pj.1.002.0.13","FlameBreathCards: 40,  Item: Legs006","88.0","910da6e0-c1a9-4423-b3f3-1e6c7a0fec10"},
				{"com.koplagames.nsk2.pj.1.002.0.25","FlameBreathCards: 100,  Item: Legs006","163.0","910da6e0-c1ac-426d-beb7-ac3c27bce67b"},
				{"com.koplagames.nsk2.pj.1.002.0.4","FlameBreathCards: 10,  Keys: 15","25.0","910da6e0-c1b0-4a2a-875a-c7de14bcc23f"},
				{"com.koplagames.nsk2.pj.1.002.0.50","FlameBreathCards: 200,  Item: Legs006,  Keys: 30","328.0","910da6e0-c1b3-466b-8a6e-d366690f50e6"},
				{"com.koplagames.nsk2.pj.1.002.0.8","FlameBreathCards: 10,  Item: Legs006","50.0","910da6e0-c1b6-4322-aa4d-8d4b6e13d501"},
				{"com.koplagames.nsk2.pj.1.003.0.10","Coins: 100000,  Tokens: 200,  Keys: 5","68.0","910da6e0-c1b9-4143-864d-21383643eb88"},
				{"com.koplagames.nsk2.pj.1.003.0.2","Coins: 10000,  Tokens: 50,  Keys: 5","12.0","910da6e0-c1be-49dd-a206-6dd77ba95afc"},
				{"com.koplagames.nsk2.pj.1.003.0.20","Coins: 250000,  Tokens: 400","128.0","910da6e0-c1c1-4c75-a969-5ad9ec3516fa"},
				{"com.koplagames.nsk2.pj.1.003.0.40","Coins: 500000,  Tokens: 800","258.0","910da6e0-c1c4-4415-acf9-22b257127940"},
				{"com.koplagames.nsk2.pj.1.003.0.5","Coins: 50000,  Tokens: 100,  Keys: 5","30.0","910da6e0-c1c8-4b26-b290-eb960e08a129"},
				{"com.koplagames.nsk2.pj.1.004.0.10","ClassicPacks: 1,  GrandeChests: 1,  Keys: 20","68.0","910da6e0-c1cc-4dc9-a64f-489be4c78b68"},
				{"com.koplagames.nsk2.pj.1.004.0.2","ClassicPacks: 1,  Keys: 5","12.0","910da6e0-c1d0-40b6-8e4e-bdaf2b4dc352"},
				{"com.koplagames.nsk2.pj.1.004.0.20","MegaPacks: 1,  LuxuryChests: 1,  Keys: 10","128.0","910da6e0-c1d3-4b8e-a0f6-67f7906e521a"},
				{"com.koplagames.nsk2.pj.1.004.0.4","ClassicPacks: 1,  Keys: 15","25.0","910da6e0-c1d6-460b-ab64-7e6a22da78cb"},
				{"com.koplagames.nsk2.pj.1.004.0.40","MegaPacks: 2,  LuxuryChests: 2,  Keys: 15","258.0","910da6e0-c1da-43d8-919a-5a7e99af6274"},
				{"com.koplagames.nsk2.pj.1.004.0.5","GrandeChests: 1,  Keys: 5","30.0","910da6e0-c1dd-41dc-a729-252c27e4c1bb"},
				{"com.koplagames.nsk2.pj.1.004.0.50","MegaPacks: 2,  LuxuryChests: 3,  Keys: 15","328.0","910da6e0-c1e0-4449-9506-3e8b7e2a7f3c"},
				{"com.koplagames.nsk2.pj.1.004.0.80","MegaPacks: 4,  LuxuryChests: 4,  Keys: 25","518.0","910da6e0-c1e3-4123-8993-2f438dedd90a"},
				{"com.koplagames.nsk2.pj.1.005.0.1","Gems: 200,  Keys: 5","6.0","910da6e0-c1e6-4548-85a6-9989bcf7bb44"},
				{"com.koplagames.nsk2.pj.1.005.0.10","Gems: 500,  Tokens: 200","68.0","910da6e0-c1e9-4f69-bad3-92e755d26e7d"},
				{"com.koplagames.nsk2.pj.1.005.0.2","Gems: 100,  Keys: 10","12.0","910da6e0-c1ec-4e25-bacb-83fcc99fa415"},
				{"com.koplagames.nsk2.pj.1.005.0.20","Gems: 1000,  Tokens: 400","128.0","910da6e0-c1ef-4a67-8f97-197593908b55"},
				{"com.koplagames.nsk2.pj.1.005.0.5","Gems: 200,  Tokens: 100","30.0","910da6e0-c1f2-4802-8258-c5b87af534f2"},
				{"com.koplagames.nsk2.pj.1.005.0.50","Gems: 2500,  Tokens: 800,  Keys: 20","328.0","910da6e0-c1f5-4827-be05-ca8a633e7085"},
				{"com.koplagames.nsk2.pj.1.006.0.10","GrandeChests: 1,  PowerStone1: 3","68.0","910da6e0-c1f8-4fe1-96f9-1d0c3681d4e7"},
				{"com.koplagames.nsk2.pj.1.006.0.100","LuxuryChests: 3,  PowerStone1: 30,  Keys: 20","648.0","910da6e0-c1fb-46f1-a025-0bc3197401c0"},
				{"com.koplagames.nsk2.pj.1.006.0.2","GrandeChests: 1,  PowerStone1: 1","12.0","910da6e0-c1fe-4ef7-8a2d-c185ff2fb73e"},
				{"com.koplagames.nsk2.pj.1.006.0.20","LuxuryChests: 1,  PowerStone1: 3,  Keys: 20","128.0","910da6e0-c201-46f8-8ae2-db0789c718b1"},
				{"com.koplagames.nsk2.pj.1.006.0.4","GrandeChests: 1","25.0","910da6e0-c204-4a72-8df7-8ee57409036d"},
				{"com.koplagames.nsk2.pj.1.006.0.40","LuxuryChests: 1,  PowerStone1: 12,  Keys: 20","258.0","910da6e0-c209-421b-ae05-d423ffdcdc51"},
				{"com.koplagames.nsk2.pj.1.006.0.5","GrandeChests: 1,  Keys: 5","30.0","910da6e0-c20d-4949-bc78-8e20dbe2d859"},
				{"com.koplagames.nsk2.pj.1.006.0.50","LuxuryChests: 2,  PowerStone1: 12,  Keys: 20","328.0","910da6e0-c210-43f8-9ff5-540041c97385"},
				{"com.koplagames.nsk2.pj.1.006.1.2","LuxuryChests: 1,  PowerStone1: 2","12.0","910da6e0-c213-4a5c-ae16-be28ea38116b"},
				{"com.koplagames.nsk2.pj.1.007.0.1","Keys: 5,  Keys: 5,  Keys: 5","6.0","910da6e0-c216-424c-9205-0f6cb703b997"},
				{"com.koplagames.nsk2.pj.1.007.0.10","Coins: 60000,  Tokens: 200,  Keys: 10","68.0","910da6e0-c219-48d2-b91a-b7c9439d522b"},
				{"com.koplagames.nsk2.pj.1.007.0.2","Coins: 25000,  Keys: 10","12.0","910da6e0-c21c-495c-899a-4e464cc35289"},
				{"com.koplagames.nsk2.pj.1.007.0.20","Coins: 120000,  Tokens: 400,  Keys: 20","128.0","910da6e0-c21f-408c-88bf-b500e20511c5"},
				{"com.koplagames.nsk2.pj.1.007.0.40","Coins: 300000,  Tokens: 800,  Keys: 30","258.0","910da6e0-c223-4d1b-82b9-2047addcbf41"},
				{"com.koplagames.nsk2.pj.1.007.0.5","Coins: 30000,  Tokens: 100,  Keys: 5","30.0","910da6e0-c227-40cf-902a-a98e232a7a5c"},
				{"com.koplagames.nsk2.pj.1.008.0.10","ClassicPacks: 2,  GrandeChests:1,  Gems: 300","68.0","910da6e0-c22a-43b2-b7ae-2a7812714352"},
				{"com.koplagames.nsk2.pj.1.008.0.2","ClassicPacks:1,  Gems: 50","12.0","910da6e0-c22d-4f0a-b82d-05b8c3817b6f"},
				{"com.koplagames.nsk2.pj.1.008.0.20","MegaPacks: 1,  LuxuryChests: 1,  Gems: 300","128.0","910da6e0-c230-4108-a379-085a536cfc4e"},
				{"com.koplagames.nsk2.pj.1.008.0.4","ClassicPacks: 1,  Gems: 300","25.0","910da6e0-c235-4c2d-bff7-e77e58215a25"},
				{"com.koplagames.nsk2.pj.1.008.0.40","MegaPacks: 2,  LuxuryChests: 2,  Gems: 600","258.0","910da6e0-c238-4ba1-b5ca-701acf903fa4"},
				{"com.koplagames.nsk2.pj.1.008.0.5","GrandeChests: 1,  Gems: 100","30.0","910da6e0-c23b-4984-8196-db0a221f889f"},
				{"com.koplagames.nsk2.pj.1.008.0.50","MegaPacks: 3,  LuxuryChests: 2,  Gems: 1000","328.0","910da6e0-c23f-46ff-9c46-9d7a69a645ce"},
				{"com.koplagames.nsk2.pj.1.008.0.80","MegaPacks: 4,  LuxuryChests: 3,  Gems: 2500","518.0","910da6e0-c242-4494-9966-08134259d9d6"},
				{"com.koplagames.nsk2.pj.1.008.1.2","GrandeChests: 1,  Keys: 10","12.0","910da6e0-c245-4ca4-acda-02cc33458d21"},
				{"com.koplagames.nsk2.pj.1.009.0.10","PowerStone1: 2,  PowerStone1: 2,  Keys: 5","68.0","910da6e0-c249-445e-a31c-38a4bfcd0dd6"},
				{"com.koplagames.nsk2.pj.1.009.0.100","PowerStone1: 21,  PowerStone1: 21,  Keys: 30","648.0","910da6e0-c24c-493d-b85b-d88587288fdb"},
				{"com.koplagames.nsk2.pj.1.009.0.2","Keys: 10,  PowerStone1: 1","12.0","910da6e0-c24f-45ae-bb9f-9467c623d28e"},
				{"com.koplagames.nsk2.pj.1.009.0.20","PowerStone1: 4,  PowerStone1: 4,  Keys: 10","128.0","910da6e0-c252-4075-a510-664c323c43e4"},
				{"com.koplagames.nsk2.pj.1.009.0.5","PowerStone1: 1,  PowerStone1: 1,  Keys: 5","30.0","910da6e0-c255-4016-a0b9-8e5198c4eece"},
				{"com.koplagames.nsk2.pj.1.009.0.50","PowerStone1: 10,  PowerStone1: 10,  Keys: 20","328.0","910da6e0-c258-47d5-bd54-34266e206755"},
				{"com.koplagames.nsk2.pj.1.009.1.2","LuxuryChests: 1,  PowerStone1: 2","12.0","910da6e0-c25c-4bd5-af48-55c19c3f97cd"},
				{"com.koplagames.nsk2.pj.1.010.0.1","Gems: 100,  Tokens: 50,  Keys: 5","6.0","910da6e0-c25f-4e93-ba6d-68cef1c2b111"},
				{"com.koplagames.nsk2.pj.1.010.0.10","Gems: 400,  Tokens: 200","68.0","910da6e0-c262-4fbf-b791-4b2b70b90aa5"},
				{"com.koplagames.nsk2.pj.1.010.0.2","Gems: 200,  Keys: 5","12.0","910da6e0-c265-40d1-a73c-40952748d160"},
				{"com.koplagames.nsk2.pj.1.010.0.20","Gems: 800,  Tokens: 400","128.0","910da6e0-c268-4c6d-baf9-e4465d64828a"},
				{"com.koplagames.nsk2.pj.1.010.0.5","Gems: 200,  Tokens: 100","30.0","910da6e0-c26d-4eb7-ba8d-c8c823aa3698"},
				{"com.koplagames.nsk2.pj.1.010.0.50","Gems: 2000,  Tokens: 1000","328.0","910da6e0-c270-4178-82d1-2a4236a9b94f"},
				{"com.koplagames.nsk2.pj.1.010.0.80","Gems: 3200,  Tokens: 1600","518.0","910da6e0-c274-441d-93b6-b41dff1970c4"},
				{"com.koplagames.nsk2.pj.1.011.0.10","ClassicPack: 2,  GrandeChests: 1,  Gems: 300","68.0","910da6e0-c278-424e-aaa1-715f2c77918e"},
				{"com.koplagames.nsk2.pj.1.011.0.2","GrandeChests: 1,  Keys: 5","12.0","910da6e0-c27c-43a9-88b7-c625feba4276"},
				{"com.koplagames.nsk2.pj.1.011.0.20","MegaPacks: 1,  LuxuryChests: 1,  Gems: 300","128.0","910da6e0-c27f-4c24-b79f-aa50903662ff"},
				{"com.koplagames.nsk2.pj.1.011.0.40","MegaPacks: 2,  LuxuryChests: 2,  Gems: 600","258.0","910da6e0-c282-49ae-b773-d6fbdc7021e7"},
				{"com.koplagames.nsk2.pj.1.011.0.5","GrandeChests: 1,  Gems: 100","30.0","910da6e0-c287-4b97-895a-ed9621be0b82"},
				{"com.koplagames.nsk2.pj.1.011.0.80","MegaPacks: 4,  LuxuryChests: 3,  Gems: 2500","518.0","910da6e0-c28a-4495-8fbd-b0ea1776c6c4"},
				{"com.koplagames.nsk2.pj.1.012.0.10","GrandeChests: 1,  PowerStone1: 2,  Keys: 10","68.0","910da6e0-c28e-49a8-ae57-6c848975fd46"},
				{"com.koplagames.nsk2.pj.1.012.0.100","LuxuryChests: 3,  PowerStone1: 30,  Keys: 40","648.0","910da6e0-c291-405b-93f7-31d0fc0722d5"},
				{"com.koplagames.nsk2.pj.1.012.0.2","GrandeChests: 1,  Keys: 10","12.0","910da6e0-c295-4b37-bac4-bc61f292184d"},
				{"com.koplagames.nsk2.pj.1.012.0.20","LuxuryChests: 1,  PowerStone1: 4,  Keys: 10","128.0","910da6e0-c299-4cb7-845e-33b51873523a"},
				{"com.koplagames.nsk2.pj.1.012.0.4","ClassicPacks: 1,  PowerStone1: 1","25.0","910da6e0-c29c-4c93-80c1-219d10a0877d"},
				{"com.koplagames.nsk2.pj.1.012.0.40","LuxuryChests: 2,  PowerStone1: 8,  Keys: 20","258.0","910da6e0-c2a0-46ae-92ad-8e3333bcb2fc"},
				{"com.koplagames.nsk2.pj.1.012.0.5","ClassicPacks: 1,  PowerStone1: 1,   Keys: 5","30.0","910da6e0-c2a4-4bd7-8076-b27c7e56fe17"},
				{"com.koplagames.nsk2.pj.1.012.0.50","LuxuryChests: 2,  PowerStone1: 13,  Keys: 20","328.0","910da6e0-c2a7-4d3d-9ecd-34aab2809fd5"},
				{"com.koplagames.nsk2.pj.1.012.1.2","LuxuryChests: 1","12.0","910da6e0-c2aa-411a-867c-4705e2fc9748"},
				{"com.koplagames.nsk2.pj.1.013.0.1","Coins: 10000,  Tokens: 100,  Keys: 20","6.0","910da6e0-c2ad-4e2d-b0ea-248e8342cd7d"},
				{"com.koplagames.nsk2.pj.1.013.0.10","Coins: 100000,  Tokens: 200,  Keys: 10","68.0","910da6e0-c2b0-4b6c-bf63-b0f59c7841f0"},
				{"com.koplagames.nsk2.pj.1.013.0.2","Coins: 25000,  Tokens: 100,  Keys: 10","12.0","910da6e0-c2b2-4c54-9849-4d6d5d69e5ae"},
				{"com.koplagames.nsk2.pj.1.013.0.20","Coins: 200000,  Tokens: 400,  Keys: 20","128.0","910da6e0-c2b5-4a6d-9cf4-bbc1452b47bf"},
				{"com.koplagames.nsk2.pj.1.013.0.40","Coins: 500000,  Tokens: 800,  Keys: 30","258.0","910da6e0-c2b9-4650-89e1-2f57ff9ae8e3"},
				{"com.koplagames.nsk2.pj.1.013.0.5","Coins: 150000,  Keys: 20","30.0","910da6e0-c2bc-400e-8cf1-025ac7c07e7f"},
				{"com.koplagames.nsk2.pj.1.013.0.80","Coins: 1000000,  Tokens: 1600,  Keys: 30","518.0","910da6e0-c2bf-4ff5-bf30-298e18a3e0fb"},
				{"com.koplagames.nsk2.special.0.001.0.10","Pet: Pet013,  PetCosmetic: PetCosmeticPet013_1","68.0","910da6e0-c2c2-40b9-966a-1be2c62b2512"},
				{"com.koplagames.nsk2.special.0.001.0.2","Pet: Pet013,  PetCosmetic: PetCosmeticPet013_1","12.0","910da6e0-c2c5-4db8-b5f7-535748977a17"},
				{"com.koplagames.nsk2.special.0.001.0.20","Pet: Pet013,  PetCosmetic: PetCosmeticPet013_1","128.0","910da6e0-c2c8-46e9-abb7-5218670e2dc8"},
				{"com.koplagames.nsk2.special.0.001.0.5","Pet: Pet013,  PetCosmetic: PetCosmeticPet013_1","30.0","910da6e0-c2cb-4f21-a7c1-475fcd8170c2"},
				{"com.koplagames.nsk2.special.0.001.0.50","Pet: Pet013,  PetCosmetic: PetCosmeticPet013_1","328.0","910da6e0-c2ce-4d53-b579-94d020976186"},
				{"com.koplagames.nsk2.special.0.002.0.10","ClassicPack: 1,  Luxury Chest: 1","68.0","910da6e0-c2d3-4bd5-90bf-157ab43a966c"},
				{"com.koplagames.nsk2.special.0.002.0.100","Megapack: 10,  Luxury Chest: 6","648.0","910da6e0-c2d6-477b-ab2a-9ad4c6cd0471"},
				{"com.koplagames.nsk2.special.0.002.0.2","Chest: Grande,  PowerStone1: 1","12.0","910da6e0-c2d9-46c2-a81a-148ef0987ee1"},
				{"com.koplagames.nsk2.special.0.002.0.20","Megapack: 2,  Luxury Chest: 1","128.0","910da6e0-c2dc-4db3-8e7c-e364fd9e02d7"},
				{"com.koplagames.nsk2.special.0.002.0.5","ClassicPack: 1,  Grande Chest: 1","30.0","910da6e0-c2df-4453-883e-9205a7212180"},
				{"com.koplagames.nsk2.special.0.002.0.50","Megapack: 5,  Luxury Chest: 3","328.0","910da6e0-c2e4-41d8-9934-53cbd8e79b03"},
				{"com.koplagames.nsk2.special.0.003.0.10","Gems: 1000,  Token: 200","68.0","910da6e0-c2e7-47e6-94aa-82e85e9f4faf"},
				{"com.koplagames.nsk2.special.0.003.0.100","Gems: 10000,  Token: 2000","648.0","910da6e0-c2eb-49ba-9011-e9c17e1b86ea"},
				{"com.koplagames.nsk2.special.0.003.0.2","Pet: Pet003,  PetCosmetic: PetCosmeticPet003_1","12.0","910da6e0-c2ef-4014-9c97-a6a6596b55e9"},
				{"com.koplagames.nsk2.special.0.003.0.20","Gems: 2000,  Token: 400","128.0","910da6e0-c2f2-4ce8-9bc9-db4ae444c694"},
				{"com.koplagames.nsk2.special.0.003.0.5","Gems: 500,  Token: 100","30.0","910da6e0-c2f5-400e-9b36-6db44430ce63"},
				{"com.koplagames.nsk2.special.0.003.0.50","Gems: 5000,  Token: 1000","328.0","910da6e0-c2f8-445d-81e2-34c89dfa1500"},
				{"com.koplagames.nsk2.special.0.004.0.10","ClassicPacks: 1,  LuxuryChests: 1","68.0","910da6e0-c2fb-413a-82a3-935309f23bc1"},
				{"com.koplagames.nsk2.special.0.004.0.100","MegaPacks: 10,  LuxuryChests: 6","648.0","910da6e0-c2fe-42d7-b8fd-43422beb3c4d"},
				{"com.koplagames.nsk2.special.0.004.0.2","Pet: Pet004,  PetCosmetic: PetCosmeticPet004_1","12.0","910da6e0-c302-4d4d-baaf-2c06c61778bd"},
				{"com.koplagames.nsk2.special.0.004.0.20","MegaPacks: 2,  LuxuryChests: 1","128.0","910da6e0-c305-4930-a0b9-1190a77a3ce2"},
				{"com.koplagames.nsk2.special.0.004.0.5","ClassicPacks: 1,  GrandeChests: 1","30.0","910da6e0-c308-4853-af29-66b34802a59b"},
				{"com.koplagames.nsk2.special.0.004.0.50","MegaPacks: 5,  LuxuryChests: 3","328.0","910da6e0-c30d-46eb-b8b2-13b73ab1b13a"},
				{"com.koplagames.nsk2.special.0.005.0.10","HeroCosmetic: FacialHair002,  MegaPack: 1","68.0","910da6e0-c310-48af-9a39-499e18000cb2"},
				{"com.koplagames.nsk2.special.1.001.0.10","Gem: 650,  Pet: Pet013","68.0","910da6e0-c313-471f-a1a2-1c572213ac4d"},
				{"com.koplagames.nsk2.special.1.001.0.100","Gem: 14000,  PowerStone1: 24","648.0","910da6e0-c316-4160-828e-162eddb078d8"},
				{"com.koplagames.nsk2.special.1.001.0.20","Gem: 2500,  PowerStone1: 5","128.0","910da6e0-c31a-4367-b697-0d9d98201a7e"},
				{"com.koplagames.nsk2.special.1.001.0.5","Gem: 300,  Pet: Pet013","30.0","910da6e0-c323-4cfe-9d1c-0460efb3ac94"},
				{"com.koplagames.nsk2.special.1.001.0.50","Gem: 6500,  PowerStone1: 12","328.0","910da6e0-c326-476e-a02d-744987bda801"},
				{"com.koplagames.nsk2.starter.0.10","Gems: 1000,  LuxuryChests: 2","68.0","910da6e0-c32a-451a-8b6a-6789ff6c2b2e"},
				{"com.koplagames.nsk2.starter.0.2","Gems: 200,  GrandeChests: 1","12.0","910da6e0-c32d-4d41-8b71-c3e263060ffc"},
				{"com.koplagames.nsk2.starter.0.5","Gems: 500,  LuxuryChests: 1","30.0","910da6e0-c330-4fdf-8413-4ad1b4be1c5b"},
				{"com.koplagames.nsk2.starter.1.10","LuxuryChests: 1,  Keys: 10","68.0","910da6e0-c337-4596-b989-4af4c4b1474e"},
				{"com.koplagames.nsk2.starter.1.2","GrandeChests: 1","12.0","910da6e0-c33b-4038-b93f-8e58fa6b25fe"},
				{"com.koplagames.nsk2.starter.1.5","GrandeChests: 1,  Keys: 10","30.0","910da6e0-c33e-4386-9171-3eb2fb2f9ae2"},
				{"com.koplagames.nsk2.starter.2.10","Gem: 1500,  Voidling Cards: 100","68.0","910da6e0-c341-4578-b6d0-a71d7b4bd755"},
				{"com.koplagames.nsk2.subscriptiontest","Subscription_VipLevel1","12.0","910da6e0-c344-4d3c-89be-1aa22aa9564f"},
				{"com.koplagames.nsk2.tour.0.00.499","Token: 500,  Luxury Chest: 1,  Key: 20","128.0","910da6e0-c347-4416-a51b-da2f78f1434e"},
				{"com.koplagames.nsk2.tour.0.00.999","Token: 200,  Item: Weapon012,  TournamentChip: 25000","68.0","910da6e0-c34a-4f93-904e-6fcd8d00c3ee"},
				{"com.koplagames.nsk2.tour.0.001.0.10","Token: 200,  Item: Bracers007,  TournamentChip: 25000","68.0","910da6e0-c34e-459f-980e-b710ed93ea9f"},
				{"com.koplagames.nsk2.tour.0.001.0.5","Token: 100,  Keys: 20","30.0","910da6e0-c351-43b0-8dfe-cf5643f3b07a"},
				{"com.koplagames.nsk2.tour.0.001.1.10","Token: 200,  Grande Chest: 1,  Key: 20","68.0","910da6e0-c354-4ff9-8f46-50981fe0a11c"},
				{"com.koplagames.nsk2.tour.0.003.0.10","Token: 200,  Item: Weapon006,  Key: 25","68.0","910da6e0-c359-4c64-b2d5-6a8620cd6929"},
				{"com.koplagames.nsk2.tour.0.003.0.5","Token: 100,  Item: Weapon006","30.0","910da6e0-c35c-4166-80a6-1ea03f1b1ef2"},
				{"com.koplagames.nsk2.tour.0.004.0.10","Token: 200,  Item: Weapon025,  Key: 25","68.0","910da6e0-c35f-46ff-8e09-71f2780a3a4a"},
				{"com.koplagames.nsk2.tour.0.004.0.5","Gems: 750,  ClassicPack: 1  ","30.0","910da6e0-c362-4088-80cd-5c025c5beb6b"},
				{"com.koplagames.nsk2.tour.0.005.0.10","Token: 200,  Item: Weapon012,  Key: 25","68.0","910da6e0-c365-4c3f-a5be-2525763e66e4"},
				{"com.koplagames.nsk2.tour.0.005.0.5","Token: 100,  Item: Weapon012","30.0","910da6e0-c368-4f52-95dc-ac6f2aaebcde"},
				{"com.koplagames.nsk2.tour.0.006.0.10","Token: 200,  Item: Weapon022,  Key: 25","68.0","910da6e0-c36b-4809-b068-d63c1e5bcf28"},
				{"com.koplagames.nsk2.tour.0.006.0.5","Token: 100,  Item: Weapon022","30.0","910da6e0-c36e-494e-8524-c1fd8e56c353"},
				{"com.koplagames.nsk2.tour.0.007.0.10","Gems: 500,  Token: 300","68.0","910da6e0-c371-4588-a870-b088ed64ea2d"},
				{"com.koplagames.nsk2.tour.0.007.0.5","Token: 200,  Keys: 5","30.0","910da6e0-c375-4d1c-8f92-0e563eaab396"},
				{"com.koplagames.nsk2.tour.0.01.999","Token: 200,  Item: Weapon012,  Key: 25","68.0","910da6e0-c378-441e-8e42-dfdb9fde0598"},
				{"com.koplagames.nsk2.tour.0.02.499","Token: 100,  Item: Weapon011","30.0","910da6e0-c37b-493e-a728-dabd0b8aaebf"},
				{"com.koplagames.nsk2.tour.0.02.999","Token: 200,  Item: Weapon011,  TournamentChip: 25000","68.0","910da6e0-c380-474f-855a-dcc7a54811f3"},
				{"com.koplagames.nsk2.tour.1.00.1999","Token: 500,  Luxury Chest","128.0","910da6e0-c383-466c-b4a7-ee3676007700"},
				{"com.koplagames.nsk2.tour.1.00.999","Token: 250,  Grande Chest: 1","68.0","910da6e0-c387-45c3-857f-c94fa06d7f3d"},
				{"com.koplagames.nsk2.tour.1.01.1999","Chest: Megapack,  Chest: Luxury,  Gems: 3500","128.0","910da6e0-c38a-4a62-bc51-9f5b447b514e"},
				{"com.koplagames.nsk2.tour.1.01.999","MegaPacks: 2","68.0","910da6e0-c38d-40d1-9bcc-eb3e3e714beb"}

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
					ProductionList[index][0] = iap_name;
					ProductionList[index][1] = remote_des;
					ProductionList[index][2] = remote_price;
					ProductionList[index][3] = remote_guid;
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
			Qinpricefloat= 1f;
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
