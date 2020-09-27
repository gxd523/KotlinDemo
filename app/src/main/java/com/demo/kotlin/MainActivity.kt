package com.demo.kotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    private val textView: TextView by lazy {
        findViewById(R.id.text_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity_main_button.setOnClickListener {
            textView.setText("fgsdr")
            startActivity(Intent(this, DetailActivity::class.java))
        }
    }
}