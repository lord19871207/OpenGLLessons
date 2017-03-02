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
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button button10 = (Button) findViewById(R.id.button10);


        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button10.setOnClickListener(this);
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
            case R.id.button6:
                intent.putExtra("index",5);
                break;
            case R.id.button7:
                intent.putExtra("index",6);
                break;
            case R.id.button8:
                intent.putExtra("index",7);
                break;
            case R.id.button9:
                intent.putExtra("index",8);
                break;
            case R.id.button10:
                intent.putExtra("index",9);
                break;

        }
            startActivity(intent);
    }
}
