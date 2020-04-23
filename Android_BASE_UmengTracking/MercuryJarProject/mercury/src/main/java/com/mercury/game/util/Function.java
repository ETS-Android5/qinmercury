package com.mercury.game.util;

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

    }
