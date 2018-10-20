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

public class Areaadapter extends RecyclerView.Adapter<Areaadapter.MyViewHolder> {
    private List<City> mDatalist;
    private Context Mcontext;
    private LayoutInflater mInflater;
    private City city;

    public Areaadapter(List datalist, Context context){
        this.mDatalist=datalist;
        this.Mcontext=context;
        mInflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.rec_item, null);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
         city = mDatalist.get(i);

        myViewHolder.tv_content.setText(city.city_name);
    }

    @Override
    public int getItemCount() {
        return mDatalist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_content;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_content=(TextView) itemView.findViewById(R.id.tv_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Mcontext,DetailActivity.class);
                    intent.putExtra("province",city);
                    Mcontext.startActivity(intent);
                }
            });
        }
    }
}
