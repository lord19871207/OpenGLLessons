package com.opengl.youyang.lesson5;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by youyang on 15-4-12.
 */
public class TextResourceReader {

    public static String readTextResourceFromRaw(Context context,int resourceId){
        StringBuilder body=new StringBuilder();
        try{
            InputStream inputStream=context.getResources().openRawResource(resourceId);
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String nextLine;
            while((nextLine=bufferedReader.readLine())!=null){
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("无法打开raw中的资源resourceId："+resourceId);
        }
        return body.toString();

    };
}
