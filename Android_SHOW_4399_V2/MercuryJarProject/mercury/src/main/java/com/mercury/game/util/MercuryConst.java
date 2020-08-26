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
				{"com.koplagames.kopla02.test.099","Gem","6.0","9141b098-26ea-4827-8c56-fc4d9d12f5c3"},
				{"com.koplagames.kopla02.test.1999","Gem","120.0","9141b098-26ea-4827-8c56-fc4d9d12f5c3"},
				{"com.koplagames.kopla02.test.299","Gem","18.0","9141b098-26ea-4827-8c56-fc4d9d12f5c3"},
				{"com.koplagames.kopla02.test.499","Gem","30.0","9141b098-26ea-4827-8c56-fc4d9d12f5c3"},
				{"com.koplagames.kopla02.test.4999","Gem","300.0","9141b098-26ea-4827-8c56-fc4d9d12f5c3"},
				{"com.koplagames.kopla02.test.999","Gem","60.0","9141b098-26ea-4827-8c56-fc4d9d12f5c3"},
				{"com.koplagames.kopla02.test.9999","Gem","600.0","9141b098-26ea-4827-8c56-fc4d9d12f5c3"},

				{"com.koplagames.cn.1","bundle","1.0","9141b098-26ea-4827-8c56-fc4d9d12f5c3"},
				{"com.koplagames.cn.3","bundle","3.0","9141b0b3-fa3a-4fda-9f88-a394d3c5a83c"},
				{"com.koplagames.cn.8","bundle","8.0","9141b0cc-4a23-482c-9833-40c89aed6d1c"},
				{"com.koplagames.cn.16","bundle","16.0","9141b0df-598e-4825-9235-1436f0d9b747"},
				{"com.koplagames.cn.36","bundle","36.0","9141b0fc-75d6-49d5-b79b-517efcc6fc92"},
				{"com.koplagames.cn.86","bundle","86.0","9141b10f-f5ad-43cf-824b-80f1edb2da65"},
				{"com.koplagames.cn.188","bundle","188.0","9141b124-a680-4265-9129-324b82bcacb8"},
				{"com.koplagames.cn.256","bundle","256.0","9141b136-bfd8-4c73-bec7-fe958e447c6e"},

				{"com.koplagames.nsk2.carnivalrewardpass.10","CarnivalRewardPass","60.0"},
				{"com.koplagames.nsk2.carnivalrewardpass.15","CarnivalRewardPass","90.0"},
				{"com.koplagames.nsk2.carnivalrewardpass.20","CarnivalRewardPass","120.0"},
				{"com.koplagames.nsk2.carnivalrewardpass.3","CarnivalRewardPass","18.0"},
				{"com.koplagames.nsk2.carnivalrewardpass.5","CarnivalRewardPass","30.0"},
				{"com.koplagames.nsk2.carnivalrewardpass.8","CarnivalRewardPass","48.0"},
				{"com.koplagames.nsk2.multirentry.d1.1","Pet: Pet005, PetCosmetic: PetCosmeticPet005_1","30.0"},
				{"com.koplagames.nsk2.multirentry.d1.2","Pet: Pet005, PetCosmetic: PetCosmeticPet005_1","60.0"},
				{"com.koplagames.nsk2.multirentry.d1.3","Pet: Pet005, PetCosmetic: PetCosmeticPet005_1","120.0"},
				{"com.koplagames.nsk2.multirentry.d1.4","Pet: Pet005, PetCosmetic: PetCosmeticPet005_1","240.0"},
				{"com.koplagames.nsk2.multirentry.d1.5","Pet005: 10, PetCosmetic: PetCosmeticPet005_1","30.0"},
				{"com.koplagames.nsk2.multirentry.d1.6","Pet005: 40, PetCosmetic: PetCosmeticPet005_1","60.0"},
				{"com.koplagames.nsk2.multirentry.d1.7","Pet005: 100, PetCosmetic: PetCosmeticPet005_1","120.0"},
				{"com.koplagames.nsk2.multirentry.pj.16","Pet: Pet005, PetCosmetic: PetCosmeticPet005_1","120.0"},
				{"com.koplagames.nsk2.multirentry.pj.17","Keys: 30, Chest: Luxury, Chest: Megapack","60.0"},
				{"com.koplagames.nsk2.multirentry.pj.2","Chest: CardPackGold, Keys: 5","6.0"},
				{"com.koplagames.nsk2.multirentry.pj.5","Pet: Pet005, PetCosmetic: PetCosmeticPet005_1","30.0"},
				{"com.koplagames.nsk2.multirentry.pj.6","Pet: Pet005, PetCosmetic: PetCosmeticPet005_1","60.0"},
				{"com.koplagames.nsk2.multirentry.pj.7","Keys: 20, Chest: Luxury, Chest: Megapack","120.0"},
				{"com.koplagames.nsk2.multirentry.special.1","Gem: 2500, PowerStone1: 10","120.0"},
				{"com.koplagames.nsk2.multirentry.special.10","Luxury Chest: 2, Powerstone1: 30","300.0"},
				{"com.koplagames.nsk2.multirentry.special.11","HeroCosmetic: FacialHair003, Chest: Luxury","60.0"},
				{"com.koplagames.nsk2.multirentry.special.12","Tokens: 400, HeroCosmetic: FacialHair001","60.0"},
				{"com.koplagames.nsk2.multirentry.special.13","HeroCosmetic: FacialHair005","30.0"},
				{"com.koplagames.nsk2.multirentry.special.14","HeroCosmetic: FacialHair006, Chest: Luxury","60.0"},
				{"com.koplagames.nsk2.multirentry.special.2","Gem: 6500, PowerStone1: 30","300.0"},
				{"com.koplagames.nsk2.multirentry.special.3","Gem: 14000, PowerStone1: 60","600.0"},
				{"com.koplagames.nsk2.multirentry.special.4","PowerStone1: 4, PowerStone1: 4","60.0"},
				{"com.koplagames.nsk2.multirentry.special.5","Gem: 2500, PowerStone1: 8, PowerStone1: 8","120.0"},
				{"com.koplagames.nsk2.multirentry.special.6","Gem: 6500, PowerStone1: 20, PowerStone1: 20","300.0"},
				{"com.koplagames.nsk2.multirentry.special.7","Gem: 14000, PowerStone1: 40, PowerStone1: 40","600.0"},
				{"com.koplagames.nsk2.multirentry.special.8","Grande Chest: 1, Powerstone1: 4","60.0"},
				{"com.koplagames.nsk2.multirentry.special.9","Luxury Chest: 1, Powerstone1: 10","120.0"},
				{"com.koplagames.nsk2.multirentry.starterbundle.2","Gem: 2000, LuxuryChest: 2, MegaPack: 2","120.0"},
				{"com.koplagames.nsk2.multirentry.starterbundle.3","Gem: 1500, MegaPack: 2","60.0"},
				{"com.koplagames.nsk2.multirentry.starterbundle.4","Gem: 200, Pet: Pet005","12.0"},
				{"com.koplagames.nsk2.multirentry.starterbundle.5","Gem: 500, GrandeChest: 1","30.0"},
				{"com.koplagames.nsk2.multirentry.starterbundle.6","Gem: 1000, LuxuryChest: 1","60.0"},
				{"com.koplagames.nsk2.multirentry.tour.1","Gems: 550, Grande: 1, Key: 20","30.0"},
				{"com.koplagames.nsk2.multirentry.tour.10","Gems: 2000, Regal Chest: 2","240.0"},
				{"com.koplagames.nsk2.multirentry.tour.11","Gems: 2500, Boreal Chest: 1","120.0"},
				{"com.koplagames.nsk2.multirentry.tour.12","Gems: 6500, Boreal Chest: 2","240.0"},
				{"com.koplagames.nsk2.multirentry.tour.13","Gem: 1200, Gem: 1200","60.0"},
				{"com.koplagames.nsk2.multirentry.tour.14","Gem: 2500, Gem: 2500","120.0"},
				{"com.koplagames.nsk2.multirentry.tour.15","Gem: 6500, Gem: 6500","300.0"},
				{"com.koplagames.nsk2.multirentry.tour.16","Gem: 14000, Gem: 14000","600.0"},
				{"com.koplagames.nsk2.multirentry.tour.17","Luxury Chest: 3","120.0"},
				{"com.koplagames.nsk2.multirentry.tour.18","Chest: Megapack, Chest: Megapack, Chest: Megapack, Chest: Megapack,  Chest: Megapack, Chest: Luxury, Chest: Luxury, Chest: Luxury, Gems: 1500","300.0"},
				{"com.koplagames.nsk2.multirentry.tour.19","Gems: 5000, Siegemaster Chest: 5","600.0"},
				{"com.koplagames.nsk2.multirentry.tour.23","GrandeChests: 3","60.0"},
				{"com.koplagames.nsk2.multirentry.tour.24","Gem: 2500, PowerStone1: 10","120.0"},
				{"com.koplagames.nsk2.multirentry.tour.25","Gem: 6500, PowerStone1: 30","300.0"},
				{"com.koplagames.nsk2.multirentry.tour.26","Gems: 14000, Luxury: 10, Grande: 6","600.0"},
				{"com.koplagames.nsk2.multirentry.tour.27","Gems: 1200, Luxury: 1, Key: 20","60.0"},
				{"com.koplagames.nsk2.multirentry.tour.28","Gems: 2500, Luxury: 2, Grande: 1","120.0"},
				{"com.koplagames.nsk2.multirentry.tour.29","Gems: 6500, Luxury: 5, Grande: 3","300.0"},
				{"com.koplagames.nsk2.multirentry.tour.3","Gems: 1000, Dragonscale Chest: 1","120.0"},
				{"com.koplagames.nsk2.multirentry.tour.4","Gems: 2000, Dragonscale Chest: 2","240.0"},
				{"com.koplagames.nsk2.multirentry.tour.40","Gems: 1200, Key: 40","60.0"},
				{"com.koplagames.nsk2.multirentry.tour.41","Gems: 2500, Key: 40,  Grande: 1","120.0"},
				{"com.koplagames.nsk2.multirentry.tour.42","Gems: 6500, Key: 40,  Luxury: 2","300.0"},
				{"com.koplagames.nsk2.multirentry.tour.43","Gems: 5000, Regal Chest: 5","600.0"},
				{"com.koplagames.nsk2.multirentry.tour.44","Gem: 550, Gem: 550","30.0"},
				{"com.koplagames.nsk2.multirentry.tour.45","Gems: 1200, EpicMark: 3600","60.0"},
				{"com.koplagames.nsk2.multirentry.tour.46","Gems: 1000, Siegemaster Chest: 1","120.0"},
				{"com.koplagames.nsk2.multirentry.tour.47","Gems: 2000, Siegemaster Chest: 2","300.0"},
				{"com.koplagames.nsk2.multirentry.tour.48","Gems: 5000, Boreal Chest: 5","600.0"},
				{"com.koplagames.nsk2.multirentry.tour.5","Gems: 1000, Boreal Chest: 1","120.0"},
				{"com.koplagames.nsk2.multirentry.tour.6","Gems: 2000, Boreal Chest: 2","240.0"},
				{"com.koplagames.nsk2.multirentry.tour.7","Gems: 1000, Miasma Chest: 1","120.0"},
				{"com.koplagames.nsk2.multirentry.tour.8","Gems: 2000, Miasma Chest: 2","240.0"},
				{"com.koplagames.nsk2.multirentry.tour.9","Gems: 1000, Regal Chest: 1","120.0"},
				{"com.koplagames.nsk2.pet.0.001.0.10","Gems: 1000, Token: 200","60.0"},
				{"com.koplagames.nsk2.pet.0.001.0.2","Gems: 200, Keys: 10","12.0"},
				{"com.koplagames.nsk2.pet.0.001.0.20","Gems: 2000, Token: 400","120.0"},
				{"com.koplagames.nsk2.pet.0.001.0.5","Token: 50, Pet: Pet012","30.0"},
				{"com.koplagames.nsk2.pet.0.001.0.50","Gems: 5000, Token: 1000","300.0"},
				{"com.koplagames.nsk2.pet.0.002.0.10","Gems: 1000, Megapack: 1","60.0"},
				{"com.koplagames.nsk2.pet.0.002.0.2","Pet: Pet010, PetCosmetic: PetCosmeticPet010_1","12.0"},
				{"com.koplagames.nsk2.pet.0.002.0.20","Gems: 1000, Token: 600","120.0"},
				{"com.koplagames.nsk2.pet.0.002.0.5","Pet: Pet010, PetCosmetic: PetCosmeticPet010_1","30.0"},
				{"com.koplagames.nsk2.pet.0.002.0.50","Gems: 2000, Token: 1600","300.0"},
				{"com.koplagames.nsk2.pet.0.003.0.10","Token: 100, Pet: Pet002","60.0"},
				{"com.koplagames.nsk2.pet.0.003.0.2","Token: 50, Pet: Pet002","12.0"},
				{"com.koplagames.nsk2.pet.0.003.0.20","Token: 200, Pet: Pet002","120.0"},
				{"com.koplagames.nsk2.pet.0.003.0.5","Token: 50, Pet: Pet002","30.0"},
				{"com.koplagames.nsk2.pet.0.003.0.50","LuxuryChests: 3, Gems: 1000","300.0"},
				{"com.koplagames.nsk2.pet.0.004.0.10","Token: 100, Pet: Pet003","60.0"},
				{"com.koplagames.nsk2.pet.0.004.0.2","Token: 50, Pet: Pet003","12.0"},
				{"com.koplagames.nsk2.pet.0.004.0.20","Token: 200, Pet: Pet003","120.0"},
				{"com.koplagames.nsk2.pet.0.004.0.5","Token: 50, Pet: Pet003","30.0"},
				{"com.koplagames.nsk2.pet.0.004.0.50","Token: 400, Pet: Pet003","300.0"},
				{"com.koplagames.nsk2.pj.0.001.0.10","Chest: Megapack, Chest: Luxury, Key: 25","60.0"},
				{"com.koplagames.nsk2.pj.0.001.0.2","Chest: Grande, Key: 5","12.0"},
				{"com.koplagames.nsk2.pj.0.001.0.20","2 Chest: Megapack, 2 Chest: Luxury, Key: 25","120.0"},
				{"com.koplagames.nsk2.pj.0.001.0.40","4 Chest: Megapack, 4 Chest: Luxury, Key: 50","240.0"},
				{"com.koplagames.nsk2.pj.0.001.0.5","Chest: ClassicPack, Chest: Grande, Key: 25","30.0"},
				{"com.koplagames.nsk2.pj.0.002.0.13","Skill: FlameBreath, Item: Legs006, Gem: 1000","78.0"},
				{"com.koplagames.nsk2.pj.0.002.0.25","Skill: FlameBreath, Item: Legs006, Gem: 3000","150.0"},
				{"com.koplagames.nsk2.pj.0.002.0.4","Skill: FlameBreath, Item: Legs006, Gem: 500","24.0"},
				{"com.koplagames.nsk2.pj.0.002.0.50","Skill: FlameBreath, Item: Legs006, Gem: 3000","300.0"},
				{"com.koplagames.nsk2.pj.0.002.0.8","Skill: FlameBreath, Item: Legs006, Gem: 200","48.0"},
				{"com.koplagames.nsk2.pj.0.003.0.10","Coin: 300000, Token: 200, Key: 20","60.0"},
				{"com.koplagames.nsk2.pj.0.003.0.2","Coin: 20000, Token: 100, Key: 10","12.0"},
				{"com.koplagames.nsk2.pj.0.003.0.20","Coin: 500000, Token: 500, Key: 20","120.0"},
				{"com.koplagames.nsk2.pj.0.003.0.40","Coin: 500000, Token: 1300, Key: 20","240.0"},
				{"com.koplagames.nsk2.pj.0.003.0.5","Coin: 50000, Token: 100, Key: 20","30.0"},
				{"com.koplagames.nsk2.pj.0.004.0.10","GrandeChests: 1, MegaPacks: 1, Keys: 20","60.0"},
				{"com.koplagames.nsk2.pj.0.004.0.2","ClassicPack: 2, Keys: 15","12.0"},
				{"com.koplagames.nsk2.pj.0.004.0.20","LuxuryChests: 1, MegaPacks: 2, Keys: 20","120.0"},
				{"com.koplagames.nsk2.pj.0.004.0.4","MegaPacks: 1, Keys: 10","24.0"},
				{"com.koplagames.nsk2.pj.0.004.0.40","LuxuryChests: 2, MegaPacks: 4, Keys: 20","240.0"},
				{"com.koplagames.nsk2.pj.0.004.0.5","MegaPacks: 1, Keys: 10","30.0"},
				{"com.koplagames.nsk2.pj.0.004.0.50","LuxuryChests: 4, MegaPacks: 3, Keys: 20","300.0"},
				{"com.koplagames.nsk2.pj.0.004.0.80","LuxuryChests: 6, MegaPacks: 6, Keys: 20","480.0"},
				{"com.koplagames.nsk2.pj.0.005.0.1","Gem: 300, Token: 50, Key: 5","6.0"},
				{"com.koplagames.nsk2.pj.0.005.0.10","Gem: 600, Token: 400, Key: 20","60.0"},
				{"com.koplagames.nsk2.pj.0.005.0.2","Gem: 300, Token: 50, Key: 5","12.0"},
				{"com.koplagames.nsk2.pj.0.005.0.20","Gem: 1500, Token: 450, Key: 20","120.0"},
				{"com.koplagames.nsk2.pj.0.005.0.40","Gem: 4000, Token: 1000, Key: 20","300.0"},
				{"com.koplagames.nsk2.pj.0.005.0.5","Gem: 550, Token: 100, Key: 20","30.0"},
				{"com.koplagames.nsk2.pj.0.006.0.10","Chest: Luxury, PowerStone2: 1, Key: 20","60.0"},
				{"com.koplagames.nsk2.pj.0.006.0.100","Chest: Luxury, Chest: Luxury, Chest: Luxury, PowerStone4: 2, Key: 20","600.0"},
				{"com.koplagames.nsk2.pj.0.006.0.2","Chest: Grande, PowerStone1: 1","12.0"},
				{"com.koplagames.nsk2.pj.0.006.0.20","Chest: Luxury, PowerStone3: 1, Key: 20","120.0"},
				{"com.koplagames.nsk2.pj.0.006.0.4","Chest: Grande, PowerStone1: 2","24.0"},
				{"com.koplagames.nsk2.pj.0.006.0.40","Chest: Luxury, PowerStone4: 1, Key: 20","240.0"},
				{"com.koplagames.nsk2.pj.0.006.0.5","Chest: Grande, PowerStone2: 1","30.0"},
				{"com.koplagames.nsk2.pj.0.006.0.50","Chest: Luxury, Chest: Luxury, PowerStone4: 1, Key: 20","300.0"},
				{"com.koplagames.nsk2.pj.0.006.1.2","Chest: Luxury, PowerStone1: 1","12.0"},
				{"com.koplagames.nsk2.pj.0.007.0.1","Coin: 10000, Token: 100, Key: 20","6.0"},
				{"com.koplagames.nsk2.pj.0.007.0.10","Coin: 250000, Token: 350, Key: 20","60.0"},
				{"com.koplagames.nsk2.pj.0.007.0.2","Coin: 25000 Token: 100, Key: 10","12.0"},
				{"com.koplagames.nsk2.pj.0.007.0.20","Coin: 500000, Token: 500, Key: 20","120.0"},
				{"com.koplagames.nsk2.pj.0.007.0.40","Coin: 1500000, Token: 1000, Key: 20","240.0"},
				{"com.koplagames.nsk2.pj.0.007.0.5","Coin: 100000, Token: 150, Key: 20","30.0"},
				{"com.koplagames.nsk2.pj.0.008.0.10","1 Chest: ClassicPack, 1 Chest: Luxury, Gem: 300","60.0"},
				{"com.koplagames.nsk2.pj.0.008.0.2","1 Chest: ClassicPack, Gem: 200","12.0"},
				{"com.koplagames.nsk2.pj.0.008.0.20","2 Chest: Megapack, 1 Chest: Luxury, Gem: 500","120.0"},
				{"com.koplagames.nsk2.pj.0.008.0.4","1 Chest: ClassicPack, 1 Chest: Grande","24.0"},
				{"com.koplagames.nsk2.pj.0.008.0.40","2 Chest: Megapack, 3 Chest: Luxury, Gem: 1500","240.0"},
				{"com.koplagames.nsk2.pj.0.008.0.5","1 Chest: ClassicPack, 1 Chest: Luxury","30.0"},
				{"com.koplagames.nsk2.pj.0.008.0.50","3 Chest: Megapack, 3 Chest: Luxury, Gem: 2500","300.0"},
				{"com.koplagames.nsk2.pj.0.008.0.80","4 Chest: Megapack, 4 Chest: Luxury, Gem: 6000","480.0"},
				{"com.koplagames.nsk2.pj.0.008.1.2","1 Chest: Luxury, Gem: 500","12.0"},
				{"com.koplagames.nsk2.pj.0.009.0.10","PowerStone1: 2, PowerStone2: 2, Key: 10","60.0"},
				{"com.koplagames.nsk2.pj.0.009.0.100","PowerStone3: 2, PowerStone4: 1, Key: 10","600.0"},
				{"com.koplagames.nsk2.pj.0.009.0.2","Key: 10, PowerStone1 1","12.0"},
				{"com.koplagames.nsk2.pj.0.009.0.20","PowerStone1: 1, PowerStone3: 1, Key: 10","120.0"},
				{"com.koplagames.nsk2.pj.0.009.0.5","PowerStone1: 1, PowerStone2: 1, Key: 10","30.0"},
				{"com.koplagames.nsk2.pj.0.009.0.50","PowerStone2: 2, PowerStone3: 2, Key: 10","300.0"},
				{"com.koplagames.nsk2.pj.0.010.0.10","Gem: 600, Token: 400, Key: 20","60.0"},
				{"com.koplagames.nsk2.pj.0.010.0.2","Gem: 300, Token: 50, Key: 5","12.0"},
				{"com.koplagames.nsk2.pj.0.010.0.20","Gem: 1500, Token: 450, Key: 20","120.0"},
				{"com.koplagames.nsk2.pj.0.010.0.5","Gem: 550, Token: 100, Key: 20","30.0"},
				{"com.koplagames.nsk2.pj.0.010.0.50","Gem: 4000, Token: 1000, Key: 20","300.0"},
				{"com.koplagames.nsk2.pj.0.010.0.80","Gem: 5000, Token: 2000, Key: 20","480.0"},
				{"com.koplagames.nsk2.pj.0.011.0.10","Chest: Megapack, Chest: Luxury, Key: 10","60.0"},
				{"com.koplagames.nsk2.pj.0.011.0.2","Chest: Grande, Key: 5","12.0"},
				{"com.koplagames.nsk2.pj.0.011.0.20","2 Chest: Megapack, 2 Chest: Luxury, Key: 10","120.0"},
				{"com.koplagames.nsk2.pj.0.011.0.40","4 Chest: Megapack, 4 Chest: Luxury, Key: 10","240.0"},
				{"com.koplagames.nsk2.pj.0.011.0.5","Chest: ClassicPack, Chest: Grande, Key: 10","30.0"},
				{"com.koplagames.nsk2.pj.0.011.0.80","6 Chest: Megapack, 6 Chest: Luxury, Key: 10","480.0"},
				{"com.koplagames.nsk2.pj.0.012.0.10","Chest: Luxury, PowerStone2: 1, Key: 20","60.0"},
				{"com.koplagames.nsk2.pj.0.012.0.100","Chest: Luxury, Chest: Luxury, Chest: Luxury, PowerStone4: 2, Key: 20","600.0"},
				{"com.koplagames.nsk2.pj.0.012.0.2","Chest: Grande, PowerStone1: 1","12.0"},
				{"com.koplagames.nsk2.pj.0.012.0.20","Chest: Luxury, PowerStone3: 1, Key: 20","120.0"},
				{"com.koplagames.nsk2.pj.0.012.0.4","Chest: Grande, PowerStone1: 2","24.0"},
				{"com.koplagames.nsk2.pj.0.012.0.40","Chest: Luxury, PowerStone4: 1, Key: 20","240.0"},
				{"com.koplagames.nsk2.pj.0.012.0.5","Chest: Grande, PowerStone2: 1","30.0"},
				{"com.koplagames.nsk2.pj.0.012.0.50","Chest: Luxury, Chest: Luxury, PowerStone4: 1, Key: 20","300.0"},
				{"com.koplagames.nsk2.pj.0.013.0.10","Coin: 250000, Token: 350, Key: 20","60.0"},
				{"com.koplagames.nsk2.pj.0.013.0.2","Coin: 25000 Token: 100, Key: 10","12.0"},
				{"com.koplagames.nsk2.pj.0.013.0.20","Coin: 500000, Token: 500, Key: 20","120.0"},
				{"com.koplagames.nsk2.pj.0.013.0.40","Coin: 1500000, Token: 1000, Key: 20","240.0"},
				{"com.koplagames.nsk2.pj.0.013.0.5","Coin: 100000, Token: 150, Key: 20","30.0"},
				{"com.koplagames.nsk2.pj.0.013.0.80","Coin: 1500000, Token: 2500, Key: 20","480.0"},
				{"com.koplagames.nsk2.pj.1.001.0.10","GrandeChests: 1, ClassicPacks: 1, Keys: 20","60.0"},
				{"com.koplagames.nsk2.pj.1.001.0.2","ClassicPacks: 1, Keys: 5","12.0"},
				{"com.koplagames.nsk2.pj.1.001.0.20","MegaPacks: 1, LuxuryChests: 1, Keys: 10","120.0"},
				{"com.koplagames.nsk2.pj.1.001.0.40","MegaPacks: 2, LuxuryChests: 2, Keys: 20","240.0"},
				{"com.koplagames.nsk2.pj.1.001.0.5","GrandeChests: 1, Keys: 5","30.0"},
				{"com.koplagames.nsk2.pj.1.002.0.13","FlameBreathCards: 40, Item: Legs006","78.0"},
				{"com.koplagames.nsk2.pj.1.002.0.25","FlameBreathCards: 100, Item: Legs006","150.0"},
				{"com.koplagames.nsk2.pj.1.002.0.4","FlameBreathCards: 10, Keys: 15","24.0"},
				{"com.koplagames.nsk2.pj.1.002.0.50","FlameBreathCards: 200, Item: Legs006, Keys: 30","300.0"},
				{"com.koplagames.nsk2.pj.1.002.0.8","FlameBreathCards: 10, Item: Legs006","48.0"},
				{"com.koplagames.nsk2.pj.1.003.0.10","Coins: 100000, Tokens: 200, Keys: 5","60.0"},
				{"com.koplagames.nsk2.pj.1.003.0.2","Coins: 10000, Tokens: 50, Keys: 5","12.0"},
				{"com.koplagames.nsk2.pj.1.003.0.20","Coins: 250000, Tokens: 400","120.0"},
				{"com.koplagames.nsk2.pj.1.003.0.40","Coins: 500000, Tokens: 800","240.0"},
				{"com.koplagames.nsk2.pj.1.003.0.5","Coins: 50000, Tokens: 100, Keys: 5","30.0"},
				{"com.koplagames.nsk2.pj.1.004.0.10","ClassicPacks: 1, GrandeChests: 1, Keys: 20","60.0"},
				{"com.koplagames.nsk2.pj.1.004.0.2","ClassicPacks: 1, Keys: 5","12.0"},
				{"com.koplagames.nsk2.pj.1.004.0.20","MegaPacks: 1, LuxuryChests: 1, Keys: 10","120.0"},
				{"com.koplagames.nsk2.pj.1.004.0.4","ClassicPacks: 1, Keys: 15","24.0"},
				{"com.koplagames.nsk2.pj.1.004.0.40","MegaPacks: 2, LuxuryChests: 2, Keys: 15","240.0"},
				{"com.koplagames.nsk2.pj.1.004.0.5","GrandeChests: 1, Keys: 5","30.0"},
				{"com.koplagames.nsk2.pj.1.004.0.50","MegaPacks: 2, LuxuryChests: 3, Keys: 15","300.0"},
				{"com.koplagames.nsk2.pj.1.004.0.80","MegaPacks: 4, LuxuryChests: 4, Keys: 25","480.0"},
				{"com.koplagames.nsk2.pj.1.005.0.1","Gems: 200, Keys: 5","6.0"},
				{"com.koplagames.nsk2.pj.1.005.0.10","Gems: 500, Tokens: 200","60.0"},
				{"com.koplagames.nsk2.pj.1.005.0.2","Gems: 100, Keys: 10","12.0"},
				{"com.koplagames.nsk2.pj.1.005.0.20","Gems: 1000, Tokens: 400","120.0"},
				{"com.koplagames.nsk2.pj.1.005.0.5","Gems: 200, Tokens: 100","30.0"},
				{"com.koplagames.nsk2.pj.1.005.0.50","Gems: 2500, Tokens: 800, Keys: 20","300.0"},
				{"com.koplagames.nsk2.pj.1.006.0.10","GrandeChests: 1, PowerStone1: 3","60.0"},
				{"com.koplagames.nsk2.pj.1.006.0.100","LuxuryChests: 3, PowerStone1: 30, Keys: 20","600.0"},
				{"com.koplagames.nsk2.pj.1.006.0.2","GrandeChests: 1, PowerStone1: 1","12.0"},
				{"com.koplagames.nsk2.pj.1.006.0.20","LuxuryChests: 1, PowerStone1: 3, Keys: 20","120.0"},
				{"com.koplagames.nsk2.pj.1.006.0.4","GrandeChests: 1","24.0"},
				{"com.koplagames.nsk2.pj.1.006.0.40","LuxuryChests: 1, PowerStone1: 12, Keys: 20","240.0"},
				{"com.koplagames.nsk2.pj.1.006.0.5","GrandeChests: 1, Keys: 5","30.0"},
				{"com.koplagames.nsk2.pj.1.006.0.50","LuxuryChests: 2, PowerStone1: 12, Keys: 20","300.0"},
				{"com.koplagames.nsk2.pj.1.006.1.2","LuxuryChests: 1, PowerStone1: 2","12.0"},
				{"com.koplagames.nsk2.pj.1.007.0.1","Keys: 5, Keys: 5, Keys: 5","6.0"},
				{"com.koplagames.nsk2.pj.1.007.0.10","Coins: 60000, Tokens: 200, Keys: 10","60.0"},
				{"com.koplagames.nsk2.pj.1.007.0.2","Coins: 25000, Keys: 10","12.0"},
				{"com.koplagames.nsk2.pj.1.007.0.20","Coins: 120000, Tokens: 400, Keys: 20","120.0"},
				{"com.koplagames.nsk2.pj.1.007.0.40","Coins: 300000, Tokens: 800, Keys: 30","240.0"},
				{"com.koplagames.nsk2.pj.1.007.0.5","Coins: 30000, Tokens: 100, Keys: 5","30.0"},
				{"com.koplagames.nsk2.pj.1.008.0.10","ClassicPacks: 2, GrandeChests:1, Gems: 300","60.0"},
				{"com.koplagames.nsk2.pj.1.008.0.2","ClassicPacks:1, Gems: 50","12.0"},
				{"com.koplagames.nsk2.pj.1.008.0.20","MegaPacks: 1, LuxuryChests: 1, Gems: 300","120.0"},
				{"com.koplagames.nsk2.pj.1.008.0.4","ClassicPacks: 1, Gems: 300","24.0"},
				{"com.koplagames.nsk2.pj.1.008.0.40","MegaPacks: 2, LuxuryChests: 2, Gems: 600","240.0"},
				{"com.koplagames.nsk2.pj.1.008.0.5","GrandeChests: 1, Gems: 100","30.0"},
				{"com.koplagames.nsk2.pj.1.008.0.50","MegaPacks: 3, LuxuryChests: 2, Gems: 1000","300.0"},
				{"com.koplagames.nsk2.pj.1.008.0.80","MegaPacks: 4, LuxuryChests: 3, Gems: 2500","480.0"},
				{"com.koplagames.nsk2.pj.1.008.1.2","GrandeChests: 1, Keys: 10","12.0"},
				{"com.koplagames.nsk2.pj.1.009.0.10","PowerStone1: 2, PowerStone1: 2, Keys: 5","60.0"},
				{"com.koplagames.nsk2.pj.1.009.0.100","PowerStone1: 21, PowerStone1: 21, Keys: 30","600.0"},
				{"com.koplagames.nsk2.pj.1.009.0.2","Keys: 10, PowerStone1: 1","12.0"},
				{"com.koplagames.nsk2.pj.1.009.0.20","PowerStone1: 4, PowerStone1: 4, Keys: 10","120.0"},
				{"com.koplagames.nsk2.pj.1.009.0.5","PowerStone1: 1, PowerStone1: 1, Keys: 5","30.0"},
				{"com.koplagames.nsk2.pj.1.009.0.50","PowerStone1: 10, PowerStone1: 10, Keys: 20","300.0"},
				{"com.koplagames.nsk2.pj.1.009.1.2","LuxuryChests: 1, PowerStone1: 2","12.0"},
				{"com.koplagames.nsk2.pj.1.010.0.1","Gems: 100, Tokens: 50, Keys: 5","6.0"},
				{"com.koplagames.nsk2.pj.1.010.0.10","Gems: 400, Tokens: 200","60.0"},
				{"com.koplagames.nsk2.pj.1.010.0.2","Gems: 200, Keys: 5","12.0"},
				{"com.koplagames.nsk2.pj.1.010.0.20","Gems: 800, Tokens: 400","120.0"},
				{"com.koplagames.nsk2.pj.1.010.0.5","Gems: 200, Tokens: 100","30.0"},
				{"com.koplagames.nsk2.pj.1.010.0.50","Gems: 2000, Tokens: 1000","300.0"},
				{"com.koplagames.nsk2.pj.1.010.0.80","Gems: 3200, Tokens: 1600","480.0"},
				{"com.koplagames.nsk2.pj.1.011.0.10","ClassicPack: 2, GrandeChests: 1, Gems: 300","60.0"},
				{"com.koplagames.nsk2.pj.1.011.0.2","GrandeChests: 1, Keys: 5","12.0"},
				{"com.koplagames.nsk2.pj.1.011.0.20","MegaPacks: 1, LuxuryChests: 1, Gems: 300","120.0"},
				{"com.koplagames.nsk2.pj.1.011.0.40","MegaPacks: 2, LuxuryChests: 2, Gems: 600","240.0"},
				{"com.koplagames.nsk2.pj.1.011.0.5","GrandeChests: 1, Gems: 100","30.0"},
				{"com.koplagames.nsk2.pj.1.011.0.80","MegaPacks: 4, LuxuryChests: 3, Gems: 2500","480.0"},
				{"com.koplagames.nsk2.pj.1.012.0.10","GrandeChests: 1, PowerStone1: 2, Keys: 10","60.0"},
				{"com.koplagames.nsk2.pj.1.012.0.100","LuxuryChests: 3, PowerStone1: 30, Keys: 40","600.0"},
				{"com.koplagames.nsk2.pj.1.012.0.2","GrandeChests: 1, Keys: 10","12.0"},
				{"com.koplagames.nsk2.pj.1.012.0.20","LuxuryChests: 1, PowerStone1: 4, Keys: 10","120.0"},
				{"com.koplagames.nsk2.pj.1.012.0.4","ClassicPacks: 1, PowerStone1: 1","24.0"},
				{"com.koplagames.nsk2.pj.1.012.0.40","LuxuryChests: 2, PowerStone1: 8, Keys: 20","240.0"},
				{"com.koplagames.nsk2.pj.1.012.0.5","ClassicPacks: 1, PowerStone1: 1,  Keys: 5","30.0"},
				{"com.koplagames.nsk2.pj.1.012.0.50","LuxuryChests: 2, PowerStone1: 13, Keys: 20","300.0"},
				{"com.koplagames.nsk2.pj.1.012.1.2","LuxuryChests: 1","12.0"},
				{"com.koplagames.nsk2.pj.1.013.0.1","Coins: 10000, Tokens: 100, Keys: 20","6.0"},
				{"com.koplagames.nsk2.pj.1.013.0.10","Coins: 100000, Tokens: 200, Keys: 10","60.0"},
				{"com.koplagames.nsk2.pj.1.013.0.2","Coins: 25000, Tokens: 100, Keys: 10","12.0"},
				{"com.koplagames.nsk2.pj.1.013.0.20","Coins: 200000, Tokens: 400, Keys: 20","120.0"},
				{"com.koplagames.nsk2.pj.1.013.0.40","Coins: 500000, Tokens: 800, Keys: 30","240.0"},
				{"com.koplagames.nsk2.pj.1.013.0.5","Coins: 150000, Keys: 20","30.0"},
				{"com.koplagames.nsk2.pj.1.013.0.80","Coins: 1000000, Tokens: 1600, Keys: 30","480.0"},
				{"com.koplagames.nsk2.special.0.001.0.10","Pet: Pet013, PetCosmetic: PetCosmeticPet013_1","60.0"},
				{"com.koplagames.nsk2.special.0.001.0.2","Pet: Pet013, PetCosmetic: PetCosmeticPet013_1","12.0"},
				{"com.koplagames.nsk2.special.0.001.0.20","Pet: Pet013, PetCosmetic: PetCosmeticPet013_1","120.0"},
				{"com.koplagames.nsk2.special.0.001.0.5","Pet: Pet013, PetCosmetic: PetCosmeticPet013_1","30.0"},
				{"com.koplagames.nsk2.special.0.001.0.50","Pet: Pet013, PetCosmetic: PetCosmeticPet013_1","300.0"},
				{"com.koplagames.nsk2.special.0.002.0.10","ClassicPack: 1, Luxury Chest: 1","60.0"},
				{"com.koplagames.nsk2.special.0.002.0.100","Megapack: 10, Luxury Chest: 6","600.0"},
				{"com.koplagames.nsk2.special.0.002.0.2","Chest: Grande, PowerStone1: 1","12.0"},
				{"com.koplagames.nsk2.special.0.002.0.20","Megapack: 2, Luxury Chest: 1","120.0"},
				{"com.koplagames.nsk2.special.0.002.0.5","ClassicPack: 1, Grande Chest: 1","30.0"},
				{"com.koplagames.nsk2.special.0.002.0.50","Megapack: 5, Luxury Chest: 3","300.0"},
				{"com.koplagames.nsk2.special.0.003.0.10","Gems: 1000, Token: 200","60.0"},
				{"com.koplagames.nsk2.special.0.003.0.100","Gems: 10000, Token: 2000","600.0"},
				{"com.koplagames.nsk2.special.0.003.0.2","Pet: Pet003, PetCosmetic: PetCosmeticPet003_1","12.0"},
				{"com.koplagames.nsk2.special.0.003.0.20","Gems: 2000, Token: 400","120.0"},
				{"com.koplagames.nsk2.special.0.003.0.5","Gems: 500, Token: 100","30.0"},
				{"com.koplagames.nsk2.special.0.003.0.50","Gems: 5000, Token: 1000","300.0"},
				{"com.koplagames.nsk2.special.0.004.0.10","ClassicPacks: 1, LuxuryChests: 1","60.0"},
				{"com.koplagames.nsk2.special.0.004.0.100","MegaPacks: 10, LuxuryChests: 6","600.0"},
				{"com.koplagames.nsk2.special.0.004.0.2","Pet: Pet004, PetCosmetic: PetCosmeticPet004_1","12.0"},
				{"com.koplagames.nsk2.special.0.004.0.20","MegaPacks: 2, LuxuryChests: 1","120.0"},
				{"com.koplagames.nsk2.special.0.004.0.5","ClassicPacks: 1, GrandeChests: 1","30.0"},
				{"com.koplagames.nsk2.special.0.004.0.50","MegaPacks: 5, LuxuryChests: 3","300.0"},
				{"com.koplagames.nsk2.special.0.005.0.10","HeroCosmetic: FacialHair002, MegaPack: 1","60.0"},
				{"com.koplagames.nsk2.special.1.001.0.10","Gem: 650, Pet: Pet013","60.0"},
				{"com.koplagames.nsk2.special.1.001.0.100","Gem: 14000, PowerStone1: 24","600.0"},
				{"com.koplagames.nsk2.special.1.001.0.20","Gem: 2500, PowerStone1: 5","120.0"},
				{"com.koplagames.nsk2.special.1.001.0.5","Gem: 300, Pet: Pet013","30.0"},
				{"com.koplagames.nsk2.special.1.001.0.50","Gem: 6500, PowerStone1: 12","300.0"},
				{"com.koplagames.nsk2.starter.0.10","Gems: 1000, LuxuryChests: 2","60.0"},
				{"com.koplagames.nsk2.starter.0.2","Gems: 200, GrandeChests: 1","12.0"},
				{"com.koplagames.nsk2.starter.0.5","Gems: 500, LuxuryChests: 1","30.0"},
				{"com.koplagames.nsk2.starter.1.10","LuxuryChests: 1, Keys: 10","60.0"},
				{"com.koplagames.nsk2.starter.1.2","GrandeChests: 1","12.0"},
				{"com.koplagames.nsk2.starter.1.5","GrandeChests: 1, Keys: 10","30.0"},
				{"com.koplagames.nsk2.starter.2.10","Gem: 1500, Voidling Cards: 100","60.0"},
				{"com.koplagames.nsk2.subscriptiontest","Subscription_VipLevel1","12.0"},
				{"com.koplagames.nsk2.tour.0.00.499","Token: 500, Luxury Chest: 1, Key: 20","120.0"},
				{"com.koplagames.nsk2.tour.0.00.999","Token: 200, Item: Weapon012, TournamentChip: 25000","60.0"},
				{"com.koplagames.nsk2.tour.0.001.0.10","Token: 200, Item: Bracers007, TournamentChip: 25000","60.0"},
				{"com.koplagames.nsk2.tour.0.001.0.5","Token: 100, Keys: 20","30.0"},
				{"com.koplagames.nsk2.tour.0.001.1.10","Token: 200, Grande Chest: 1, Key: 20","60.0"},
				{"com.koplagames.nsk2.tour.0.003.0.10","Token: 200, Item: Weapon006, Key: 25","60.0"},
				{"com.koplagames.nsk2.tour.0.003.0.5","Token: 100, Item: Weapon006","30.0"},
				{"com.koplagames.nsk2.tour.0.004.0.10","Token: 200, Item: Weapon025, Key: 25","60.0"},
				{"com.koplagames.nsk2.tour.0.004.0.5","Gems: 750, ClassicPack: 1  ","30.0"},
				{"com.koplagames.nsk2.tour.0.005.0.10","Token: 200, Item: Weapon012, Key: 25","60.0"},
				{"com.koplagames.nsk2.tour.0.005.0.5","Token: 100, Item: Weapon012","30.0"},
				{"com.koplagames.nsk2.tour.0.006.0.10","Token: 200, Item: Weapon022, Key: 25","60.0"},
				{"com.koplagames.nsk2.tour.0.006.0.5","Token: 100, Item: Weapon022","30.0"},
				{"com.koplagames.nsk2.tour.0.007.0.10","Gems: 500, Token: 300","60.0"},
				{"com.koplagames.nsk2.tour.0.007.0.5","Token: 200, Keys: 5","30.0"},
				{"com.koplagames.nsk2.tour.0.01.999","Token: 200, Item: Weapon012, Key: 25","60.0"},
				{"com.koplagames.nsk2.tour.0.02.499","Token: 100, Item: Weapon011","30.0"},
				{"com.koplagames.nsk2.tour.0.02.999","Token: 200, Item: Weapon011, TournamentChip: 25000","60.0"},
				{"com.koplagames.nsk2.tour.1.00.1999","Token: 500, Luxury Chest","120.0"},
				{"com.koplagames.nsk2.tour.1.00.999","Token: 250, Grande Chest: 1","60.0"},
				{"com.koplagames.nsk2.tour.1.01.1999","Chest: Megapack, Chest: Luxury, Gems: 3500","120.0"},
				{"com.koplagames.nsk2.tour.1.01.999","MegaPacks: 2","60.0"}
		};

		String remote_config = readFileData("get_remote_iap");
		try {
			JSONObject json = (JSONObject) new JSONTokener(remote_config).nextValue();
			JSONObject json_data = json.getJSONObject("data");
			JSONObject json_result = json_data.getJSONObject("result");
			Iterator<String>  myKeys = json_result.keys();
			int index = 0;
			while (myKeys.hasNext()) {
				String iap_name = myKeys.next();
				JSONObject json_result1 = json_result.getJSONObject(iap_name);
				String remote_des = (String) json_result1.get("des");
				String remote_price = (String) json_result1.get("price");
				String remote_guid = (String) json_result1.get("guid");
				ProductionList[index][0]=iap_name;
				ProductionList[index][1]=remote_des;
				ProductionList[index][2]=remote_price;
				ProductionList[index][3]=remote_guid;
				index++;
			}
		} catch (JSONException e) {
			e.printStackTrace();
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
