package com.opengl.youyang.myapplication;

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

    protected ShaderProgram(Context context, int index) {
        switch (index){
            case 0:
                mProgram = ShaderHelper.buildProgram(TextResourceReader.readTextResourceFromRaw(context,
                        R.raw.point_vertext_shader), TextResourceReader.readTextResourceFromRaw(context,
                        R.raw.translate1_fragment_shader));
                break;
            case 1:
                mProgram = ShaderHelper.buildProgram(TextResourceReader.readTextResourceFromRaw(context,
                        R.raw.point_vertext_shader), TextResourceReader.readTextResourceFromRaw(context,
                        R.raw.translate2_fragment_shader));
                break;
            case 2:
                mProgram = ShaderHelper.buildProgram(TextResourceReader.readTextResourceFromRaw(context,
                        R.raw.point_vertext_shader), TextResourceReader.readTextResourceFromRaw(context,
                        R.raw.translate3_fragment_shader));
                break;
            case 3:
                mProgram = ShaderHelper.buildProgram(TextResourceReader.readTextResourceFromRaw(context,
                        R.raw.point_vertext_shader), TextResourceReader.readTextResourceFromRaw(context,
                        R.raw.translate4_fragment_shader));
                break;
            case 4:
                mProgram = ShaderHelper.buildProgram(TextResourceReader.readTextResourceFromRaw(context,
                        R.raw.point_vertext_shader), TextResourceReader.readTextResourceFromRaw(context,
                        R.raw.translate5_fragment_shader));
                break;
            case 5:
                mProgram = ShaderHelper.buildProgram(TextResourceReader.readTextResourceFromRaw(context,
                        R.raw.point_vertext_shader), TextResourceReader.readTextResourceFromRaw(context,
                        R.raw.translate6_fragment_shader));
                break;
            case 6:
                mProgram = ShaderHelper.buildProgram(TextResourceReader.readTextResourceFromRaw(context,
                        R.raw.point_vertext_shader), TextResourceReader.readTextResourceFromRaw(context,
                        R.raw.translate7_fragment_shader));
                break;
            case 7:
                mProgram = ShaderHelper.buildProgram(TextResourceReader.readTextResourceFromRaw(context,
                        R.raw.point_vertext_shader), TextResourceReader.readTextResourceFromRaw(context,
                        R.raw.translate8_fragment_shader));
                break;
            case 8:
                mProgram = ShaderHelper.buildProgram(TextResourceReader.readTextResourceFromRaw(context,
                        R.raw.point_vertext_shader), TextResourceReader.readTextResourceFromRaw(context,
                        R.raw.translate10_fragment_shader));
                break;
            case 9:
                mProgram = ShaderHelper.buildProgram(TextResourceReader.readTextResourceFromRaw(context,
                        R.raw.point_vertext_shader), TextResourceReader.readTextResourceFromRaw(context,
                        R.raw.point_fragment_shader));
                break;

        }

    }

    public void useProgram(){
        GLES20.glUseProgram(mProgram);
    }
}
