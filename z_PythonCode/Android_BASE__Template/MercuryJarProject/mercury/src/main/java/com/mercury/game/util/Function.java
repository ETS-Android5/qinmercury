package com.mercury.game.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import com.mercury.game.MercuryActivity;

import org.apache.http.util.EncodingUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public final class Function {
        public  static void writeFileData(String fileName,String message)
        {
            try{
                //E2WApp.LogLocal("[E2WApp]->writeFileData fileName="+fileName+",message="+message+"-"+E2WApp.mContext);
                FileOutputStream fout = MercuryActivity.mContext.openFileOutput(fileName, MercuryActivity.mContext.MODE_PRIVATE);
                byte [] bytes = message.getBytes();
                fout.write(bytes);
                fout.close();
                //E2WApp.LogLocal("[E2WApp]->writeFileData Success");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        public static String readFileData(String fileName)
        {

            String res="";
            try
            {
                //E2WApp.LogLocal("[E2WApp]->readFileData:"+fileName+"-"+E2WApp.mContext);
                FileInputStream fin = MercuryActivity.mContext.openFileInput(fileName);
                int length = fin.available();
                byte [] buffer = new byte[length];
                fin.read(buffer);
                res = EncodingUtils.getString(buffer, "UTF-8");
                fin.close();
                //E2WApp.LogLocal("[E2WApp]->readFileData Success");
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return res;
        }
        public static void verifyGame()
        {

            int local_version = 0;
            //get remote version
            int remote_version = 14;
            String download_link ="";
            //get apk version
            try {
                PackageManager manager = MercuryActivity.mContext.getPackageManager();
                PackageInfo info = manager.getPackageInfo(MercuryActivity.mContext.getPackageName(), 0);
                local_version = info.versionCode;
                Log.d("MercurySDK", "version:"+local_version);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (remote_version>=local_version)
            //have new version
            {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MercuryActivity.mContext);
                    builder.setMessage("检测到新版本");
                    builder.setTitle("说明");
                    builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse("https://m.3839.com/a/121237.htm");//此处填链接
                            intent.setData(content_url);
                            MercuryActivity.mContext.startActivity(intent);
                            ((Activity) MercuryActivity.mContext).finish();
                            android.os.Process.killProcess(android.os.Process.myPid());
                        }
                    });
                    builder.setNeutralButton("退出游戏", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((Activity) MercuryActivity.mContext).finish();
                            android.os.Process.killProcess(android.os.Process.myPid());
                        }
                    });
                    builder.setCancelable(false);
                    builder.create().show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //have no new version
            else
            {

            }
        }

    }
