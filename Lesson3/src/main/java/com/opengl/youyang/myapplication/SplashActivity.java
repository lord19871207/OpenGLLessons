package com.opengl.youyang.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by youyang on 16/7/19.
 */
public class SplashActivity extends Activity implements View.OnClickListener {

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_select);
        intent = new Intent(SplashActivity.this,MainActivity.class);

        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                intent.putExtra("index",0);
                break;
            case R.id.button2:
                intent.putExtra("index",1);
                break;
            case R.id.button3:
                intent.putExtra("index",2);
                break;
            case R.id.button4:
                intent.putExtra("index",3);
                break;
            case R.id.button5:
                intent.putExtra("index",4);
                break;
        }
            startActivity(intent);
    }
}
