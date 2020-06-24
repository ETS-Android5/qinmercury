using UnityEngine;
using System.Collections;
using UnityEditor.iOS.Xcode;
using UnityEditor.Callbacks;
using UnityEditor;
using System.IO;

public class XcodeLibrary : MonoBehaviour
{

#if UNITY_IOS || UNITY_EDITOR
    // ios版本xcode工程维护代码
    [PostProcessBuild(999)]
    public static void OnPostprocessBuild(BuildTarget BuildTarget, string path)
    {

        #if UNITY_IOS
        // Get plist
        string plistPath = path + "/Info.plist";
        PlistDocument plist = new PlistDocument();
        plist.ReadFromString(File.ReadAllText(plistPath));
        // Get root
        PlistElementDict rootDict = plist.root;
        // Set encryption usage boolean
        string encryptKey = "ITSAppUsesNonExemptEncryption";
            rootDict.SetBoolean(encryptKey, false);
        // remove exit on suspend if it exists.
        string exitsOnSuspendKey = "UIApplicationExitsOnSuspend";
        if(rootDict.values.ContainsKey(exitsOnSuspendKey))
        {
            rootDict.values.Remove(exitsOnSuspendKey);
        }
        // Write to file
        File.WriteAllText(plistPath, plist.WriteToString());
    #endif
        // if (BuildTarget == BuildTarget.iOS)
        // {
        //     string projPath = PBXProject.GetPBXProjectPath(path);
        //     PBXProject proj = new PBXProject();
        //     proj.ReadFromString(File.ReadAllText(projPath));

        //     // 获取当前项目名字
        //     string target = proj.TargetGuidByName(PBXProject.GetUnityTargetName());

        //     // 对所有的编译配置设置选项
        //     proj.SetBuildProperty(target, "GCC_C_LANGUAGE_STANDARD", "gnu99");//为e2wsdk添加的
        //     proj.SetBuildProperty(target, "ENABLE_BITCODE", "NO");//为友盟添加的
        //     proj.SetBuildProperty(target, "DEBUG_INFORMATION_FORMAT", "DWARF");//debug模式不卡死

        //     // 添加依赖库
        //     // 语音sdk
        //     //proj.AddFrameworkToProject(target, "Security.framework", false);
        //     // システムのフレームワークを追加
        //     proj.AddFrameworkToProject(target, "CoreTelephony.framework", false);
        //     proj.AddFrameworkToProject(target, "CFNetwork.framework", false);
        //     proj.AddFrameworkToProject(target, "Security.framework", false);//微信登录
        //     proj.AddFrameworkToProject(target, "libsqlite3.tbd", false);//微信登录
        //     proj.AddFrameworkToProject(target, "libstdc++.6.0.9.tbd", false);
        //     proj.AddFrameworkToProject(target, "libz.tbd", false);//微信登录
        //     proj.AddBuildProperty(target, "OTHER_LDFLAGS", "-ObjC");//微信登录
        //     proj.AddBuildProperty(target, "OTHER_LDFLAGS", "-all_load");//微信登录
        //     // 書き出し
        //     System.IO.File.WriteAllText(projPath, proj.WriteToString());


        //     //

        //     // 设置签名
        //     //			proj.SetBuildProperty (target, "CODE_SIGN_IDENTITY", "iPhone Distribution: _______________");
        //     //			proj.SetBuildProperty (target, "PROVISIONING_PROFILE", "********-****-****-****-************"); 

        //     // 保存工程
        //     proj.WriteToFile(projPath);

        //     //修改plist
        //     string plistPath = path + "/Info.plist";
        //     PlistDocument plist = new PlistDocument();
        //     plist.ReadFromString(File.ReadAllText(plistPath));
        //     PlistElementDict rootDict = plist.root;

        //     var arrAqs = rootDict.CreateArray("LSApplicationQueriesSchemes");//微信登录
        //     arrAqs.AddString("weixin");//微信登录
        //     arrAqs.AddString("wechat");//微信登录

        //     var arrUrlTypes = rootDict.CreateArray("CFBundleURLTypes");
        //     {
        //         //微信通知回调
        //         var item0 = arrUrlTypes.AddDict();
        //         item0.SetString("CFBundleURLName", "weixin");
        //         item0.SetString("CFBundleTypeRole", "Editor");
        //         var arrUrlSchemes = item0.CreateArray("CFBundleURLSchemes");
        //         arrUrlSchemes.AddString("wxb0ad5afde0479df0");
        //     }


        //     // 语音所需要的声明，iOS10必须
        //     //rootDict.SetString("NSContactsUsageDescription", "是否允许此游戏使用麦克风？");

        //     // 保存plist
        //     plist.WriteToFile(plistPath);
        //     System.IO.File.WriteAllText(plistPath, plist.WriteToString());
        // }
    }
#endif
}
