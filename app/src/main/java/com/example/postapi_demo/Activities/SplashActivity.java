package com.example.postapi_demo.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.postapi_demo.R;

public class SplashActivity extends AppCompatActivity {




    public  static SharedPreferences sp;
    public  static SharedPreferences.Editor editor;


    Boolean  Islogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sp = getSharedPreferences("myyy",0);
        editor = sp.edit();



        Islogin = sp.getBoolean("Loginstatus",false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Islogin)
                {
                    startActivity(new Intent(SplashActivity.this,HomePage_Activity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(SplashActivity.this,Login_Activity.class));
                    finish();
                }
            }
        },3000);





    }
}