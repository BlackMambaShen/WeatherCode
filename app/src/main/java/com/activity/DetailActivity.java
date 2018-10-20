package com.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.adapter.Areaadapter;
import com.adapter.cityAdapter;
import com.bean.City;
import com.example.testing.weathercode.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private RecyclerView rec_provinceCity;
    private ArrayList<Object> areaCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        Intent intent = getIntent();
        City province = (City) intent.getSerializableExtra("province");
        try {
            InputStream in = getAssets().open("_city.json");
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            byte[]buffer=new byte[1024];
            int len=0;
            in.read(buffer);
            while ((len=in.read(buffer))!=-1){
                bos.write(buffer,0,len);
            }
            bos.close();
            in.close();
            String result = bos.toString();
            List<City> cities = processData(result);
            areaCity = new ArrayList<>();
            for (City city:cities){
                if (city.pid==province.id){
                    areaCity.add(city);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rec_provinceCity.setLayoutManager(layoutManager);
        rec_provinceCity.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
        rec_provinceCity.setAdapter(new cityAdapter(areaCity,getApplicationContext()));
    }

    private void initView() {
         rec_provinceCity = (RecyclerView) findViewById(R.id.rec_provinceCity);
    }

    private List<City> processData(String result) {
//        Gson gson=new Gson();
////        ArrayList<City> cities = gson.fromJson(result, new TypeToken<List<City>>() {}.getType());
        ArrayList<City> cities;
        try {
            JSONArray jsonArray = new JSONArray(result);
            cities = new ArrayList<>();
            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                City city=new City();
//                "_id": 1,
//                        "id": 1,
//                        "pid": 0,
//                        "city_code": "101010100",
//                        "city_name": "北京"
                city.pid=jsonObject.getInt("pid");
                city._id=jsonObject.getInt("_id");
                city.id=jsonObject.getInt("id");
                city.city_code=jsonObject.getString("city_code");
                city.city_name=jsonObject.getString("city_name");
                cities.add(city);
            }
            return cities;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
