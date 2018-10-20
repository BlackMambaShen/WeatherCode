package com.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.testing.weathercode.MainActivity;
import com.example.testing.weathercode.R;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        initView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        },2000);
    }

    private void initView() {
        ImageView iv_launch = (ImageView) findViewById(R.id.iv_launch);
        iv_launch.setImageResource(R.drawable.weather);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
