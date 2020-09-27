package com.demo.kotlin;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * Created by guoxiaodong on 2020/9/27 10:52
 */
public class DetailActivity extends Activity {
    private TextView textView = findViewById(R.id.text_view);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}