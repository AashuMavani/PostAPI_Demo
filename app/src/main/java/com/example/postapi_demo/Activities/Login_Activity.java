package com.example.postapi_demo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.postapi_demo.MODEL.LoginData;
import com.example.postapi_demo.R;
import com.example.postapi_demo.Retro_Object_Class;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity {
    EditText email,password;
    Button btnLogin,btnReg;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String str1,str2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        email=findViewById(R.id.loginEmail);
        password=findViewById(R.id.loginPass);
        btnReg=findViewById(R.id.btnReg);
        preferences=getSharedPreferences("myPref",MODE_PRIVATE);
        editor=preferences.edit();
        btnLogin=findViewById(R.id.btnLogin);
        email.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user=preferences.getString("email","null");
                System.out.println("user="+user);
                if(user.equals(email))
                {
                    Toast.makeText(Login_Activity.this, "Already logged in", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loginApi();
                }
            }
        });
    }
    TextWatcher textWatcher=new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            str1=email.getText().toString();
            str2=password.getText().toString();
            btnLogin.setEnabled(!str1.isEmpty()&&!str2.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    void loginApi()
    {
        Log.d("bbb", "API hitted.."+str1.toString());
        Retro_Object_Class.CallApi().userLogin(str1,str2).enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                Log.d("bbb", "onResponse: "+response.body().getResult());
                if (response.body().getConnection()==1)
                {
                    if(response.body().getResult()==1)
                    {
                        Toast.makeText(Login_Activity.this, "User Logged in", Toast.LENGTH_SHORT).show();
                        //editor.putString("name", response.body().getUserdata().getName());
                        editor.putString("email", response.body().getUserdata().getEmail());
                        editor.commit();
                        Intent intent = new Intent(Login_Activity.this,FirstActivity.class);
                        startActivity(intent);
                    }
                    else if(response.body().getResult()==2)
                    {
                        Toast.makeText(Login_Activity.this, "User already Logged in", Toast.LENGTH_SHORT).show();
                    }
                    else if(response.body().getResult()==0)
                    {
                        Toast.makeText(Login_Activity.this, "Invalid Email or Password or Register first", Toast.LENGTH_LONG).show();
                        btnReg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Login_Activity.this,Register_Activity.class);
                                intent.putExtra("email",email.getText().toString());
                                intent.putExtra("password",password.getText().toString());
                                startActivity(intent);
                                finish();
                            }
                        });
//                        email.setError("Re-Enter valid e-mail id");
//                        password.setError("");
//                        email.setText("");
//                        password.setText("");
                    }
                }
                else
                {
                    Toast.makeText(Login_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {

            }
        });
    }
}