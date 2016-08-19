package com.opengl.youyang.lesson5;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Build;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by youyang on 16/5/24.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PreviewGLRender implements GLSurfaceView.Renderer ,SurfaceTexture.OnFrameAvailableListener{

    private FloatBuffer mFloatBuffer;

    private ViewListener mViewListener;

    private Context mContext;
    private FloatBuffer mTextureBuffer;
    Camera mCamera;
    SurfaceTexture mSurfaceTexture;
    private ViewListener mListener;

    public PreviewGLRender(Context context){
        mContext = context;
    }

//    float[] mVertex = new float[]{
//            0f,0f
//    } ;

    float aPoint = 0.8f;
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

    private ColorShaderProgram mColorShaderProgram;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {


        mCamera = Camera.open();

        GLES20.glClearColor(0.0f,0.5f,0.3f,0.4f);

        mColorShaderProgram = new ColorShaderProgram(mContext);

        mFloatBuffer = ByteBuffer.allocateDirect(mVertex.length*4).order(ByteOrder.nativeOrder())
                .asFloatBuffer().put(mVertex);
        mTextureBuffer = ByteBuffer.allocateDirect(mTextureCoord.length*4).order(ByteOrder.nativeOrder())
                .asFloatBuffer().put(mTextureCoord);
        mFloatBuffer.position(0);
        mTextureBuffer.position(0);

        mColorShaderProgram.useProgram();

        mSurfaceTexture = new SurfaceTexture(TextureHelper.loadTexture());
        mColorShaderProgram.setUniforms();

        mSurfaceTexture.setOnFrameAvailableListener(this);
        try {
            mCamera.setPreviewTexture(mSurfaceTexture);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();


        GLES20.glVertexAttribPointer(mColorShaderProgram.getPositionAttributionLocation(),2,GLES20.GL_FLOAT,false,2*4,mFloatBuffer);
        GLES20.glEnableVertexAttribArray(mColorShaderProgram.getPositionAttributionLocation());
//        mFloatBuffer.position(0);
        GLES20.glEnableVertexAttribArray(mColorShaderProgram.getTextureCoordAttributionLocation());
        GLES20.glVertexAttribPointer(mColorShaderProgram.getTextureCoordAttributionLocation(),2,GLES20.GL_FLOAT,false,2*4,mTextureBuffer);
    }


    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        mListener.requestRender();
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //设置显示区域大小和位置
        GLES20.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        mSurfaceTexture.updateTexImage();
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);
//        最终的绘制方法
//        GLES20.glDrawArrays(GLES20.GL_POINTS,0,1);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,6);
    }

    public void setListener(ViewListener listener) {
        mListener = listener;
    }
}
