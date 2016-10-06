package com.sawrose.resturant;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.sawrose.resturant.Adapters.ProductAdapter;
import com.sawrose.resturant.JsonParsar.JsonParsar;
import com.sawrose.resturant.database.ProductClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity{

    ProductClass productClass;
    JsonParsar jsonParsar = new JsonParsar();
    int flag=0;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<ProductClass> productClassArrayList = new ArrayList<>();
    SpotsDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressDialog = new SpotsDialog(MainActivity.this, R.style.Custom);

        new preapareproduct().execute();
    }

    private class preapareproduct extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("id","1");
            JSONObject jsonObject = jsonParsar.performPostCI("http://kinbech.6te.net/ResturantFoods/api/showmenulist",hashMap);

            try{
                if (jsonObject==null){
                    flag = 1;
                }
                else if (jsonObject.getString("status").equals("success")){
                    flag = 2;
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0 ; i<=jsonArray.length();i++){
                        JSONObject jsonMenuobject = jsonArray.getJSONObject(i);
                        String name = jsonMenuobject.getString("name");
                        String id = jsonMenuobject.getString("id");
                        String price = jsonMenuobject.getString("price");
                        String details = jsonMenuobject.getString("details");
                        String image = jsonMenuobject.getString("image");
                        String material = jsonMenuobject.getString("materials");
                        productClass = new ProductClass(id,name,price,details,material,image);
                        productClassArrayList.add(productClass);
                    }

                }
                else {
                    flag = 3;
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

            if (flag == 1 ){
                Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
            else if (flag == 2){
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                adapter = new ProductAdapter(MainActivity.this,productClassArrayList);

                // Setup and Handover data to recyclerview
                recyclerView = (RecyclerView)findViewById(R.id.activity_main_recycle);
                adapter = new ProductAdapter(MainActivity.this, productClassArrayList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


            }
            else {
                Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
