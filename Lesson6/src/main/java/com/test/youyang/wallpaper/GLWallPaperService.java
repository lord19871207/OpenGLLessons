package com.test.youyang.wallpaper;

import android.app.ActivityManager;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;
import android.widget.Toast;

/**
 * Created by youyang on 2017/1/19.
 */

public class GLWallPaperService extends WallpaperService {

    private GLWallPaperSurfaceView mGLSurfaceView;

    GLEngine mEngine = new GLEngine();

    private boolean renderSet;

//    Thread mRenderThread = new Thread(){
//        @Override
//        public void run() {
//            super.run();
//            while (true){
//                if(mVisible){
//                    SystemClock.sleep(150);
//                    if(mGLSurfaceView !=null){
//                        mGLSurfaceView.requestRender();
//                    }
//                }
//
//            }
//
//        }
//    };
    private boolean mVisible;

    @Override
    public Engine onCreateEngine() {
//        mEngine = new GLEngine();
        return mEngine;
    }

    public class GLEngine extends Engine {
        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            initGLWallPaper();
//            mRenderThread.start();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            mVisible = visible;
            if(renderSet){
                if(visible){
                    mGLSurfaceView.onResume();
                }else{
                    mGLSurfaceView.onPause();
                }
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mGLSurfaceView.onWallPaperDestroy();
        }
    }



    private void initGLWallPaper() {

        mGLSurfaceView = new GLWallPaperSurfaceView(this);

        //第一步 检验是否支持opengl 2.0
        final boolean isSuppotES2=isSupportES2();
        //第二步创建渲染器
        final PointRender render = new PointRender(this);

        if(isSuppotES2){
            //设定egl版本
            mGLSurfaceView.setEGLContextClientVersion(2);
            //设置渲染器，设置完这一步之后相当于开启了 另一条渲染线程
            mGLSurfaceView.setRenderer(render);
            //设置渲染模式 一直渲染
            mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
            renderSet = true;

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
                mGLSurfaceView.setPreserveEGLContextOnPause(true);
            }
        }else {
            Toast.makeText(this,"该手机不支持opengl es 2.0" ,Toast.LENGTH_LONG).show();
            return;
        }

    }

    /**
     * 检验是否支持opengl 2.0
     * @return
     */
    private boolean isSupportES2() {
        return ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE))
                .getDeviceConfigurationInfo().reqGlEsVersion >= 0x20000;
    }

    class GLWallPaperSurfaceView extends GLSurfaceView{

        public GLWallPaperSurfaceView(Context context) {
            super(context);
        }

        @Override
        public SurfaceHolder getHolder() {
            return mEngine.getSurfaceHolder();
        }

        public void onWallPaperDestroy(){
            super.onDetachedFromWindow();
        }
    }

}
