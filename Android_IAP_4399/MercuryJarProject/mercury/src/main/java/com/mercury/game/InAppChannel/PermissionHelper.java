package com.mercury.game.InAppChannel;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;


public class PermissionHelper {
    static final int PERMISSION_REQUEST_CODE = 0x19;

    private static final String[] RuntimePermissions = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void requestPermission(final Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle("权限申请")
                .setMessage("首次启动，需要授予少量权限以获得更好体验，是否授予？")
                .setPositiveButton("立即申请", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if (PermissionHelper.higherAndroidM()) {
                            activity.requestPermissions(RuntimePermissions, PERMISSION_REQUEST_CODE);
                        }
                    }
                })
                .setNegativeButton("仍然拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    public static boolean hasPermission(Context context) {
        if (higherAndroidM())
            for (String p : RuntimePermissions)
                if (ContextCompat.checkSelfPermission(context, p) == PackageManager.PERMISSION_DENIED)
                    return false;

        return true;
    }

    static boolean higherAndroidM() {
        return Build.VERSION.SDK_INT >= 23;
    }
}
