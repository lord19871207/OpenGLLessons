package com.opengl.youyang.lesson5;

import android.content.Context;
import android.opengl.GLES20;

/**
 * Created by youyang on 15-4-18.
 */
public class ShaderProgram {
    //uniform 常量
    protected static final String U_COLOR = "sTexture";

    //attribute常量
    protected static final String A_POSITION = "a_Position";

    protected static final String A_TEXTURECOORD = "aTextureCoord";

    protected final int mProgram;

    protected ShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        mProgram = ShaderHelper.buildProgram(TextResourceReader.readTextResourceFromRaw(context,
                vertexShaderResourceId), TextResourceReader.readTextResourceFromRaw(context,
                fragmentShaderResourceId));
    }

    public void useProgram(){
        GLES20.glUseProgram(mProgram);
    }
}
