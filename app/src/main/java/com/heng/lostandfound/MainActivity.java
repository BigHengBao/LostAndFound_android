package com.heng.lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.heng.lostandfound.activity.LoginActivity;

/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity {
//    Button toLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this,LoginActivity.class));

//        toLoginBtn = findViewById(R.id.btn_tologin);
//        toLoginBtn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//            }
//        });
    }


}