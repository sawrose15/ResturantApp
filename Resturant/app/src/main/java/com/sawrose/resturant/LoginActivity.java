package com.sawrose.resturant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sawrose.resturant.JsonParsar.JsonParsar;
import com.sawrose.resturant.SharedPrefrences.GlobalState;
import com.sawrose.resturant.database.UserDetail;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import dmax.dialog.SpotsDialog;

/**
 * Created by sawrose on 10/4/16.
 */

public class LoginActivity extends AppCompatActivity {
    EditText mUsernameEditText,mPasswordEditText;
    Button mLoginButton,mRegisterButton;
    String email,passsword;
    SpotsDialog progressDialog;
    JsonParsar jsonParser=new JsonParsar();
    UserDetail user;
    String userphone,useraddress,userid,useremail,username;
    int flag=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsernameEditText = (EditText) findViewById(R.id.activity_login_username);
        mPasswordEditText = (EditText) findViewById(R.id.activity_login_password);
        mLoginButton = (Button) findViewById(R.id.activity_login_login_button);
        mRegisterButton = (Button) findViewById(R.id.activity_login_register_button);
        progressDialog = new SpotsDialog(LoginActivity.this, R.style.Custom);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mUsernameEditText.getText().toString();
                passsword = mPasswordEditText.getText().toString();

                if (email.length()<1 || passsword.length()<1){
                    if (email.length()<1){
                        mUsernameEditText.setError("Enter Email");
                    }
                    else if (passsword.length()<1){
                        mPasswordEditText.setError("Enter Password");
                    }
                }
                else {
                    new CheckLogin().execute();
                }
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }

    public class CheckLogin extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("email",email);
            hashMap.put("password",passsword);
            JSONObject jsonObject = jsonParser.performPostCI("http://kinbech.6te.net/ResturantFoods/api/login",hashMap);

            try{
                if (jsonObject==null){
                    flag=1;
                }
                else if (jsonObject.getString("status").equals("success")){
                    flag=2;
                    userphone=jsonObject.getString("phone");
                    useraddress=jsonObject.getString("address");
                    userid =jsonObject.getString("id");
                    useremail= jsonObject.getString("email");
                    username =jsonObject.getString("name");
                    user=new UserDetail(userid,username,useremail,useraddress,userphone);
                }else {
                    flag=3;
                }
            }
            catch (JSONException e){

            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

            if (flag==1){
                Toast.makeText(LoginActivity.this, "Network Issue", Toast.LENGTH_SHORT).show();
            }
            else if (flag==2){
                GlobalState globalState=GlobalState.singleton;
                globalState.setPrefsCheckLogin("true",1);
                globalState.setPrefsUserDetail(new Gson().toJson(user),1);
                Intent log = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
            else {
                Toast.makeText(LoginActivity.this, "Invalid Crediatials", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
