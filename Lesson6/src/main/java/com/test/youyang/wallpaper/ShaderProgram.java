package com.test.youyang.wallpaper;

import android.content.Context;
import android.opengl.GLES20;

/**
 * Created by youyang on 15-4-18.
 */
public class ShaderProgram {
    //uniform 常量
    protected static final String U_COLOR = "uColor";
    protected static final String U_FROM = "from";//from, to;progress   interpolationPower
    protected static final String U_PROGRESS = "progress";
    protected static final String U_TO = "to";
    protected static final String U_INTERPOLATIONPOWER = "interpolationPower";
//    protected static final String U_RESOLUTION = "resolution";

    //attribute常量
    protected static final String A_POSITION = "a_Position";
    protected static final String A_TEXCOOR = "a_TexCoor";


    protected int mProgram;

    protected ShaderProgram(Context context) {
                mProgram = ShaderHelper.buildProgram(TextResourceReader.readTextResourceFromRaw(context,
                        R.raw.point_vertext_shader), TextResourceReader.readTextResourceFromRaw(context,
                        R.raw.point_fragment_shader));
    }

    public void useProgram(){
        GLES20.glUseProgram(mProgram);
    }
}
