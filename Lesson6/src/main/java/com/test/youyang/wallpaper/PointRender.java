package com.test.youyang.wallpaper;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by youyang on 16/5/24.
 */
public class PointRender implements GLSurfaceView.Renderer {

    private FloatBuffer mFloatBuffer;
    private FloatBuffer mTextureBuffer;

    private Context mContext;

    public PointRender(Context context) {
        mContext = context;
    }

    private int[] mResources = new int[2];

    float aPoint = 1f;
    float[] mVertex = new float[]{
            -aPoint, aPoint,
            -aPoint, -aPoint,
            aPoint, -aPoint,

            aPoint, -aPoint,
            aPoint, aPoint,
            -aPoint, aPoint

    };

    float[] mTextureCoord = new float[]{
            0f, 0f,
            0f, 1f,
            1f, 1f,

            1f, 1f,
            1f, 0f,
            0f, 0f
    };

    int[] ids;
    private ColorShaderProgram mColorShaderProgram;

    float time = 0;
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

//        time = SystemClock.currentThreadTimeMillis();
        mColorShaderProgram = new ColorShaderProgram(mContext);
        GLES20.glClearColor(0f, 0f, 0f, 0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDisable(GLES20.GL_CULL_FACE);
        ids = TextureHelper.loadTexture(mContext, mResources);
        mFloatBuffer = ByteBuffer.allocateDirect(mVertex.length * 4).order(ByteOrder.nativeOrder())
                .asFloatBuffer().put(mVertex);
        mTextureBuffer = ByteBuffer.allocateDirect(mVertex.length * 4).order(ByteOrder.nativeOrder())
                .asFloatBuffer().put(mTextureCoord);
        mFloatBuffer.position(0);
        mTextureBuffer.position(0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        time +=0.1f;
        limitFrameRate(15);

        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        mColorShaderProgram.useProgram();
        mColorShaderProgram.setUniformProgressAndInterpolationPower(time);
        GLES20.glVertexAttribPointer(mColorShaderProgram.getPositionAttributionLocation(), 2, GLES20.GL_FLOAT, false, 2 * 4, mFloatBuffer);
        GLES20.glVertexAttribPointer(mColorShaderProgram.getTexCoodAttributionLocation(), 2, GLES20.GL_FLOAT, false, 2 * 4, mTextureBuffer);
        GLES20.glEnableVertexAttribArray(mColorShaderProgram.getPositionAttributionLocation());
        GLES20.glEnableVertexAttribArray(mColorShaderProgram.getTexCoodAttributionLocation());
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);
    }

    private long frameStartTime;

    private void limitFrameRate(int i) {
        long frameTime = SystemClock.elapsedRealtime() - frameStartTime;
        long expectTime = 1000/i ;
        long timeToSleep = expectTime - frameTime;

        if(timeToSleep > 0){
            SystemClock.sleep(timeToSleep);
        }
        frameStartTime = SystemClock.elapsedRealtime();
    }

}
