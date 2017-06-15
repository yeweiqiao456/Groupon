package com.ywq.tarena.groupon.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;

import com.ywq.tarena.groupon.R;
import com.ywq.tarena.groupon.config.Constant;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //界面停留几秒钟
        //
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //读取偏好设置文件中的值
                SharedPreferences sp = getSharedPreferences("sp", Context.MODE_PRIVATE);
                boolean first = sp.getBoolean(Constant.FIRST, true);
                //根据是否是第一次使用进行相应的界面跳转
                Intent intent;
                if (first){
                    //向新手指导页跳转
                    intent = new Intent(SplashActivity.this,GuideActivity.class);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean(Constant.FIRST,false);
                    edit.commit();
                } else {
                    //向主页面跳转
                    intent = new Intent(SplashActivity.this,MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },1500);
    }
}
