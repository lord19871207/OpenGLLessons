package com.opengl.youyang.opengllessons;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by youyang on 16/5/24.
 */
public class BackgroundRender implements GLSurfaceView.Renderer {

    private float mRed;
    private float mGreen;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f,0.5f,0.3f,0.4f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(mRed,mGreen,0.3f,0.4f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }

    public void setBackgroundColor(float x, float y) {
        mRed = x%1.0f;
        mGreen = y%1.0f;
    }
}
