package com.example.postapi_demo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.postapi_demo.Models.RegdData;
import com.example.postapi_demo.R;
import com.example.postapi_demo.Retro_Object_Class;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register_Activity extends AppCompatActivity {

    EditText name,email,password;
    Button btnRegister;
    String str1,str2,str3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        name=findViewById(R.id.name);
        email = findViewById(R.id.regEmail);
        password = findViewById(R.id.regPass);
        btnRegister = findViewById(R.id.btnRegister);
        str2=getIntent().getStringExtra("email");
        str3=getIntent().getStringExtra("password");

        email.setText(""+str2);
        password.setText(""+str3);
        System.out.println("email="+email.getText().toString() +"\tpass="+password.getText().toString());

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerApi();
                //loginApi();
            }
        });
    }

    void registerApi() {
        //System.out.println("API hitted");
        str1=name.getText().toString();
        Retro_Object_Class.CallApi().userRegister(str1, str2, str3)
                .enqueue(new Callback<RegdData>() {
                    @Override
                    public void onResponse(Call<RegdData> call, Response<RegdData> response) {
                        Log.d("aaa", "onResponse: " + response.body());
                        if (response.body().getConnection() == 1) {
                            if (response.body().getResult() == 1) {
                                Toast.makeText(Register_Activity.this, "User Registered", Toast.LENGTH_SHORT).show();
                            } else if (response.body().getResult() == 2) {
                                Toast.makeText(Register_Activity.this, "User already exists", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Register_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<RegdData> call, Throwable t) {
                        Log.e("aaa", "onFailure: " + t.getLocalizedMessage());
                    }
                });
    }
}
