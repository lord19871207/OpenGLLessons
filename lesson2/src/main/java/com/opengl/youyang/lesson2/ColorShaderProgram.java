package com.opengl.youyang.lesson2;

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
        u_ColorLocation= GLES20.glGetUniformLocation(mProgram,U_COLOR);
        aPositionLocation=GLES20.glGetAttribLocation(mProgram,A_POSITION);
    }

    public void setUniforms(float r,float g,float b){
        GLES20.glUniform4f(u_ColorLocation,r,g,b,1f);
    }

    public int getPositionAttributionLocation(){
        return aPositionLocation;
    }


}
