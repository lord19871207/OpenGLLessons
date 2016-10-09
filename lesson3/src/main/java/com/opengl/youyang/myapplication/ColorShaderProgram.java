package com.opengl.youyang.myapplication;

import android.content.Context;
import android.opengl.GLES20;

/**
 * Created by youyang on 15-4-19.
 */
public class ColorShaderProgram extends ShaderProgram{
    private final int u_ColorLocation;
    private final int aPositionLocation;

    public ColorShaderProgram(Context context){
    super(context,R.raw.point_vertext_shader,R.raw.point_fragment_shader);
        //获取对应的着色器里的变量
        u_ColorLocation= GLES20.glGetUniformLocation(mProgram,U_COLOR);
        aPositionLocation=GLES20.glGetAttribLocation(mProgram,A_POSITION);
    }

    /**
     * 控制片元着色器里的颜色值
     * @param r
     * @param g
     * @param b
     */
    public void setUniforms(float r,float g,float b){
        GLES20.glUniform4f(u_ColorLocation,r,g,b,1f);
    }

    /**
     * 获取着色器里的变量的句柄
     * @return
     */
    public int getPositionAttributionLocation(){
        return aPositionLocation;
    }


}
