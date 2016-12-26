package com.zhong.maxleapdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.maxleap.GetCallback;
import com.maxleap.MLDataManager;
import com.maxleap.MLObject;
import com.maxleap.exception.MLException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    private Context con;
    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        con = SplashActivity.this;
        deviceId =((TelephonyManager) con.getSystemService(TELEPHONY_SERVICE)).getDeviceId();
//        Log.e("deviceId--->",deviceId);
        SharedPreferences sp = this.getSharedPreferences("config",0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("deviceId", deviceId);
        editor.commit();
        Intent serviceIntent = new Intent(con, MyService.class);
        startService(serviceIntent);
        Log.e(" sHA1(con)", sHA1(con));

        findViewById(R.id.click_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MLObject myComment = new MLObject("Comment");
                myComment.put("content", "Maxleap share");
                myComment.put("pubUserId",1);
                myComment.put("isRead", false);
                MLDataManager.saveInBackground(myComment);

//                MLDataManager.fetchInBackground(MLObject.createWithoutData("foobar", "123"),
//                        new GetCallback<MLObject>() {
//                            @Override
//                            public void done(MLObject mlObject, MLException e) {
//                                if (e != null && e.getCode() == MLException.INVALID_OBJECT_ID) {
//                                    Log.d("MaxLeap", "Connect to MaxLeap server successfully！");
//                                } else {
//                                    Log.d("MaxLeap", "Invalid MaxLeap credentials!");
//                                }
//                            }
//                        });
            }
        });
    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
