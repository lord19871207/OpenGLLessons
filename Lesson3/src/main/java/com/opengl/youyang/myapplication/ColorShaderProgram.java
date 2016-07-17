package com.opengl.youyang.myapplication;

import android.content.Context;
import android.opengl.GLES20;

/**
 * Created by youyang on 15-4-19.
 */
public class ColorShaderProgram extends ShaderProgram{
    private final int u_ColorLocation;
    private final int aPositionLocation;
    private final int aTexturreCoordLocation;
    private final int u_Progress;
    private final int u_InterpolationPower;
    private final int u_From;
    private final int u_To;
//    private final int u_Resolution;



    public ColorShaderProgram(Context context){
    super(context,R.raw.point_vertext_shader,R.raw.translate4_fragment_shader);
    u_ColorLocation= GLES20.glGetUniformLocation(mProgram,U_COLOR);
        aPositionLocation=GLES20.glGetAttribLocation(mProgram,A_POSITION);
        aTexturreCoordLocation = GLES20.glGetAttribLocation(mProgram,A_TEXCOOR);
        u_Progress = GLES20.glGetUniformLocation(mProgram,U_PROGRESS);
        u_InterpolationPower = GLES20.glGetUniformLocation(mProgram,U_INTERPOLATIONPOWER);
        u_From = GLES20.glGetUniformLocation(mProgram,U_FROM);
        u_To = GLES20.glGetUniformLocation(mProgram,U_TO);
    }

    public void setUniforms(float r,float g,float b){
        GLES20.glUniform4f(u_ColorLocation,r,g,b,1f);
    }

    //from, to;progress   interpolationPower
    public void setUniformProgressAndInterpolationPower(float progress, float interpolationPower){
        GLES20.glUniform1f(u_Progress,progress);
        GLES20.glUniform1f(u_InterpolationPower,interpolationPower);

    }

    public int getPositionAttributionLocation(){
        return aPositionLocation;
    }

    public int getTexCoodAttributionLocation(){
        return aTexturreCoordLocation;
    }

    public int getUniformFromLocation(){
        return u_From;
    }

    public int getUniformToLocation(){
        return u_To;
    }


}
