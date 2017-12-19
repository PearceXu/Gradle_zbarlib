package com.yyx_yu.as_zbar.permission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.support.v4.content.ContextCompat;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 检查权限的工具类
 * <p/>
 * Created by yu on 16/12/14.
 */
public class PermissionsChecker implements Serializable {

    // 所有的敏感权限的列表
    //https://developer.android.com/guide/topics/security/permissions.html#normal-dangerous
   private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR,
            Manifest.permission_group.CALENDAR,

            Manifest.permission.CAMERA,
            Manifest.permission_group.CAMERA,

            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission_group.CONTACTS,

            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission_group.LOCATION,

            Manifest.permission.RECORD_AUDIO,
            Manifest.permission_group.MICROPHONE,

            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.ADD_VOICEMAIL,
            Manifest.permission.USE_SIP,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission_group.PHONE,


            Manifest.permission.BODY_SENSORS,
            Manifest.permission_group.SENSORS,

            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH,
            Manifest.permission.RECEIVE_MMS,
            Manifest.permission_group.SMS,


            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission_group.STORAGE,
    };


    private static String[]  permission;

    /**
     * 判定输入的敏感权限的权限是否有放开
     *
     * @param mContext 当前的上下文权限
     * @param permissions 权限列表
     * @return
     */
    public static boolean lacksPermissions(Context mContext, String... permissions) {



        for (String permission : permissions) {

            if (StringUtils.isNotBlank(permission) && lacksPermission(mContext,permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 自动判断 AndroidManifest.xml 里面注册的敏感权限 并且对所有的权限进行权限动态获取
     * @param mContext
     * @return
     */
    public static boolean lacksPermissions(Context mContext){
        synchronized (PERMISSIONS){

            try {

                if(permission == null){

                    List<String> permissionList=new ArrayList(PERMISSIONS.length);

                    Collections.addAll(permissionList, PERMISSIONS);

                    permissionList.retainAll(Arrays.asList(getUsesPermissions(mContext)));

                    permission =new String[permissionList.size()];

                    permissionList.toArray(permission);
                }

                return  lacksPermissions(mContext,permission);


            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * 返回当前APP所需要的所有的敏感权限
     * @return
     */
    public static String[] getPermission(){
        return permission;
    }


    // 判断是否缺少权限
    private static boolean lacksPermission(Context mContext, String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

    //获取APP注册的所有的权限
    private static String[] getUsesPermissions(Context mContext) throws PackageManager.NameNotFoundException {

        PackageManager packageManager=mContext.getPackageManager();

        String callingApp = packageManager.getNameForUid(Binder.getCallingUid());

        PackageInfo packageInfo=packageManager.getPackageInfo(callingApp, PackageManager.GET_PERMISSIONS);
        String[] usesPermissionsArray=packageInfo.requestedPermissions;
        return usesPermissionsArray;
    }


}
