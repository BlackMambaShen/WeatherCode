package com.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activity.DetailActivity;
import com.bean.City;
import com.example.testing.weathercode.R;

import java.util.List;

public class cityAdapter extends RecyclerView.Adapter<cityAdapter.cityViewHolder> {
    private List<City> mDatalist;
    private Context Mcontext;
    private LayoutInflater mInflater;
    private City city;

    public cityAdapter(List datalist, Context context){
        this.mDatalist=datalist;
        this.Mcontext=context;
        mInflater=LayoutInflater.from(context);
    }
    public cityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.rec_item, null);
        cityViewHolder cityViewHolder = new cityViewHolder(view);
        return cityViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull cityViewHolder cityViewHolder, int i) {
        City city = mDatalist.get(i);
        cityViewHolder.tv_content.setText(city.city_name);
    }

    @Override
    public int getItemCount() {
        return mDatalist.size();
    }

    class cityViewHolder extends RecyclerView.ViewHolder{
        TextView tv_content;

        public cityViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_content=(TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
