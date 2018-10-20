package com.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adapter.Areaadapter;
import com.bean.City;
import com.constants.Constants;
import com.example.testing.weathercode.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AreaFragment extends BaseFragment {
    private static final String TAG ="TAG";
    private RecyclerView rec_area;
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ArrayList<City> provinceCity= (ArrayList<City>) msg.obj;
//            Log.d(TAG,provinceCity.toString());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rec_area.setLayoutManager(layoutManager);
            rec_area.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
            rec_area.setAdapter(new Areaadapter(provinceCity,mActivity));
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_area,null,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         rec_area = (RecyclerView) view.findViewById(R.id.rec_area);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDatafromServer();
    }

    private void getDatafromServer() {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(Constants.BASEURL).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"出错了");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                List<City> cities = processData(result);
                ArrayList<City> provinceCity = new ArrayList<>();
                for (City city:cities){
                    if (city.pid==0){
                        provinceCity.add(city);
                    }
                }
                Message msg=Message.obtain();
                msg.obj=provinceCity;
                handler.sendMessage(msg);
            }
        });
    }

    private List<City> processData(String result) {
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
//                Log.d(TAG,cities.toString());
                return cities;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
}
