package com.opengl.youyang.opengllessons;

import android.app.ActivityManager;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //第一课教你  如何绘制一个背景
    /**
     * 主要涉及到的知识有
     * 1.如何判断当前手机是否支持opengl
     * 2.如何创建渲染器
     * 3.设置渲染模式
     * 4.响应触摸事件时 如何保证异步渲染，和主线程的触摸事件保持同步
     * 5.如何设置背景
     */
    
    private GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mGLSurfaceView = new GLSurfaceView(this);

        //第一步 检验是否支持opengl 2.0
        final boolean isSuppotES2=isSupportES2();
        //第二步创建渲染器
        final BackgroundRender render = new BackgroundRender();

        if(isSuppotES2){
            //设定egl版本
            mGLSurfaceView.setEGLContextClientVersion(2);
            //设置渲染器，设置完这一步之后相当于开启了 另一条渲染线程
            mGLSurfaceView.setRenderer(render);
            //设置渲染模式 一直渲染
            mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        }else {
            Toast.makeText(this,"该手机不支持opengl es 2.0" ,Toast.LENGTH_LONG).show();
            return;
        }

        setContentView(mGLSurfaceView);

        mGLSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, final MotionEvent event) {

                if(event !=null){
                    mGLSurfaceView.queueEvent(new Runnable() {
                        @Override
                        public void run() {
                            render.setBackgroundColor(event.getX(),event.getY());
                            Log.d("opengl","event.getX():"+event.getX()+", event.getY():"+event.getY());
                        }
                    });
                    return true;
                }else {
                    return false;
                }

            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
    }

    /**
     * 检验是否支持opengl 2.0
     * @return
     */
    private boolean isSupportES2() {
        return ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE))
                .getDeviceConfigurationInfo().reqGlEsVersion >= 0x20000;
    }
}
