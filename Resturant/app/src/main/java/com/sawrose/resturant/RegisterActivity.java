package com.sawrose.resturant;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sawrose.resturant.JsonParsar.JsonParsar;
import com.sawrose.resturant.database.UserDetail;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import dmax.dialog.SpotsDialog;

/**
 * Created by sawrose on 10/5/16.
 */

public class RegisterActivity extends AppCompatActivity {
    EditText NameEditText,EmailEditText,PasswodEditText,ConfirmPassEditText,AddressEditText,PhoneEditText;
    Button RegisterButton,SignInButton;
    String name,email,pass,confirm,address,phone;
    UserDetail user;
    JsonParsar jsonparsar = new JsonParsar();
    int flag=0;
    SpotsDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        NameEditText = (EditText) findViewById(R.id.activity_register_name_edittext);
        EmailEditText = (EditText) findViewById(R.id.activity_register_email_edittext);
        PasswodEditText = (EditText) findViewById(R.id.activity_register_password_edit);
        ConfirmPassEditText = (EditText) findViewById(R.id.activity_register_confirmPass_edit);
        AddressEditText = (EditText) findViewById(R.id.activity_register_address_edit);
        PhoneEditText = (EditText) findViewById(R.id.activity_register_phone_edit);


        RegisterButton = (Button) findViewById(R.id.activity_register_reg_button);
        SignInButton = (Button) findViewById(R.id.activity_register_login_button);
        progressDialog = new SpotsDialog(RegisterActivity.this, R.style.Custom);

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = NameEditText.getText().toString();
                email = EmailEditText.getText().toString();
                pass = PasswodEditText.getText().toString();
                confirm = ConfirmPassEditText.getText().toString();
                address = AddressEditText.getText().toString();
                phone = PhoneEditText.getText().toString();

                if ( name.length() < 1 || email.length() < 1 || pass.length() < 1 || confirm.length() < 1 || address.length() < 1 || phone.length() < 1){
                    if (name.length()<1) {
                        NameEditText.setError("Please fill the name");
                        NameEditText.requestFocus();
                    }
                    else if (email.length()<1){
                        EmailEditText.setError("Please fill the Email");
                        EmailEditText.requestFocus();
                    }
                    else if (pass.length()<1){
                        PasswodEditText.setError("Please fill the Password");
                        PasswodEditText.requestFocus();
                    }
                    else if (confirm.length()<1){
                        ConfirmPassEditText.setError("Please fill the Password");
                        ConfirmPassEditText.requestFocus();
                    }
                    else if (address.length()<1){
                        AddressEditText.setError("Please fill the Address");
                        AddressEditText.requestFocus();
                    }
                    else if (phone.length()<1){
                        PhoneEditText.setError("Please fill the Phone");
                        PhoneEditText.requestFocus();
                    }
                }
                else if(PasswodEditText.getText().length() < 6){
                    PasswodEditText.setError("Password Must be 6 Character");
                    PasswodEditText.requestFocus();
                }
                else if (!PasswodEditText.getText().toString().equals(ConfirmPassEditText.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Password doesnt match", Toast.LENGTH_SHORT).show();
                }
                else {
                    new Register().execute();
                }


            }
        });

        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

    }

    private class Register extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String,String> hashmap = new HashMap<>();
            hashmap.put("email",email);
            hashmap.put("name",name);
            hashmap.put("password",pass);
            hashmap.put("address",address);
            hashmap.put("phone",phone);

            JSONObject json = jsonparsar.performPostCI("http://kinbech.6te.net/ResturantFoods/api/register",hashmap);
            try{
                if (json==null){
                        flag=1;
                }
                else if (json.getString("status").equals("success")){
                    flag=2;
                }
                else {
                    flag=3;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    }
}
