package com.zhong.maxleapdemo;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.maxleap.GetCallback;
import com.maxleap.MLDataManager;
import com.maxleap.MLObject;
import com.maxleap.MaxLeap;
import com.maxleap.exception.MLException;

public class App extends Application {

//    public static final String APP_ID = "Replace this with your App Id";
//    public static final String API_KEY = "Replace this with your Rest Key";
    public static final String APP_ID = "584bc731a1600200076aad73";
    public static final String API_KEY = "LTAzQWR2ai1pVktlWXpDM1hQalc1dw";


    @Override
    public void onCreate() {
        super.onCreate();
        if (APP_ID.startsWith("Replace") || API_KEY.startsWith("Replace")) {
            throw new IllegalArgumentException("Please replace with your app id and api key first before" +
                    "using MaxLeap SDK.");
        }
	    /*
         * Fill in this section with your MaxLeap credentials
		 */
        MaxLeap.setLogLevel(MaxLeap.LOG_LEVEL_ERROR);
        MaxLeap.initialize(this, "584bc731a1600200076aad73", "LTAzQWR2ai1pVktlWXpDM1hQalc1dw", MaxLeap.REGION_CN);
        Toast.makeText(this,""+MaxLeap.getLogLevel(),1).show();
        Log.e(" MaxLeap.getRestApiUrl()", MaxLeap.getRestApiUrl());
        Log.e(" MaxLeap.getApplicationContext()", MaxLeap.getApplicationContext()+"");
        //测试项目配置：
        MLDataManager.fetchInBackground(MLObject.createWithoutData("foobar", "123"),
                new GetCallback<MLObject>() {
                    @Override
                    public void done(MLObject mlObject, MLException e) {
                        if (e != null && e.getCode() == MLException.INVALID_OBJECT_ID) {
                            Log.e("MaxLeap--1-->", "SDK 成功连接到你的云端应用！");
                        } else {
                            Log.e("MaxLeap--2-->", "应用访问凭证不正确，请检查。");
                        }
                    }
                });
    }
}
