package com.opengl.youyang.myapplication;

import android.app.ActivityManager;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import android.widget.Toast;

/**
 * 着色器 是 软件与GPU硬件的桥梁，可以通过着色器去控制GPU去进行高性能的绘制。 如果不使用着色器，我们就只能通过cpu去进行渲染，两者的效率天差地别。
 *
 * 这节课开始接触着色器，用着色器 绘制一个普通的顶点Point 1.学会写简单的着色器 2.了解如何加载着色器 3.学会如何在java代码中动态给着色器的属性赋值
 */

public class MainActivity extends AppCompatActivity {

    GLSurfaceView view;

    float process = 0.0f;
    int count = 0;

    float limit = 1.0f;

    float moveX = 0;

    private int[] size;

    private Scroller mScroller;
    PointRender render;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        view = new GLSurfaceView(this);
        mFlingRunnable = new FlingRunnable();
        mScroller = new Scroller(this, new DecelerateInterpolator());

        //第一步 检验是否支持opengl 2.0
        final boolean isSuppotES2 = isSupportES2();
        //第二步创建渲染器
        render = new PointRender(this);

//        render.setShader(this,R.raw.point_vertext_shader,R.raw.translate2_fragment_shader);

        size = getScreenSize(this);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mCurrentX = event.getX();
                mCurrentY = event.getY();

                handleTouchCommon(event);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        moveX = event.getX();

                        view.queueEvent(new Runnable() {
                            @Override
                            public void run() {

                                process = moveX / size[0];
                                float k = process % limit;

                                if (process > limit) {
                                    process = 0;
                                    count++;
                                }
                                if (count % 2 == 0) {
                                    render.setProcess(k);
                                } else {
                                    render.setProcess(limit - k);
                                }
                                Log.d("youyang", "process :" + process);
                            }
                        });

                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:

                        break;
                    default:
                        break;
                }
                mPreY = mCurrentY;
                mPreX = mCurrentX;

                view.requestRender();
                return true;
            }
        });


        if (isSuppotES2) {
            //设定egl版本
            view.setEGLContextClientVersion(2);
            //设置渲染器，设置完这一步之后相当于开启了 另一条渲染线程
            view.setRenderer(render);
            //设置渲染模式 一直渲染
            view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        } else {
            Toast.makeText(this, "该手机不支持opengl es 2.0", Toast.LENGTH_LONG).show();
            return;
        }
        setContentView(view);
    }


    @Override
    protected void onResume() {
        super.onResume();
        view.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        view.onPause();
    }


    /**
     * 上一页下一页的趋势 0空闲状态；1 上一页；2 下一页；3菜单；4长按事件
     */
    private int mScrollDirection = DIRECTION_VOID;
    /**
     * 空闲状态or点击状态
     */
    public static final int DIRECTION_VOID = 0x0004;
    /**
     * 上一页
     */
    public static final int DIRECTION_LAST = 0x0005;
    /**
     * 下一页o
     */
    public static final int DIRECTION_NEXT = 0x0006;

    /**
     * touch事件down时的y轴坐标
     */
    private float mTouchDownY;
    /**
     * 一次完整touch事件中上一次的x轴坐标
     */
    private float mLastTouchX;
    /**
     * 一次完整touch事件中上一次的y轴坐标
     */
    private float mLastTouchY;

    /**
     * touch事件down时的x轴坐标
     */
    private float mTouchDownX;

    private float mPreY;
    private float mCurrentY;
    private float mDy;

    private float mPreX;
    private float mCurrentX;
    private float mDx;
    private float mMoveX = 0;
    private float mMoveY;
    private FlingRunnable mFlingRunnable;


    /**
     * 处理触摸事件的公共部分
     */
    private void handleTouchCommon(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mScrollDirection = DIRECTION_VOID;
                mTouchDownX = event.getX();
                mTouchDownY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                mDy = mCurrentY - mPreY;
                mDx = mCurrentX - mPreX;

                if (mDx > 0) {
                    mScrollDirection = DIRECTION_LAST;
                } else if (mDx < 0) {
                    mScrollDirection = DIRECTION_NEXT;
                }
                mMoveX = event.getX();
                mMoveY = event.getY();


                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastTouchX = event.getX();
                mLastTouchY = event.getY();
//                startScollAnimation(false);
                break;
            default:
                break;
        }
    }


    private void startScollAnimation(boolean isTouch) {
        int distance = 0;
        // 翻下一页
        if (mScrollDirection == DIRECTION_NEXT) {
            if (mDx <= 0) {
                // 手左右来回划，最后滑的方向是从右往左
//                distance = mTouchDownX - mLastTouchX > 0 ? (int) (mTouchDownX - mLastTouchX)
//                        - mWidth : (int) (mLastTouchX - mWidth);
                distance = mTouchDownX - mLastTouchX > 0 ? (int) (mTouchDownX - mLastTouchX)
                        - size[0] : (int) (-size[0] + mTouchDownX - mLastTouchX);
            } else {
                distance = mTouchDownX - mLastTouchX > 0 ? (int) (mTouchDownX - mLastTouchX) : 0;
            }
        } else if (mScrollDirection == DIRECTION_LAST) {
            // 手左右来回划，最后滑的方向是从右往左
            if (mDx < 0) {
                distance = mTouchDownX - mLastTouchX < 0 ? (int) (mTouchDownX - mLastTouchX) : 0;
            } else {
                distance = (int) (size[0] - mLastTouchX + mTouchDownX);
            }
        }
        if (isTouch) {
            if (mScrollDirection == DIRECTION_LAST) {
                mFlingRunnable.startByTouch(size[0]);
            } else if (mScrollDirection == DIRECTION_NEXT) {
                mFlingRunnable.startByTouch(-size[0]);
            }
        } else {
            if (distance == 0) {
                distance = 1;
            }
            mFlingRunnable.startByTouch(distance);
        }

    }

    class FlingRunnable implements Runnable {
        private static final int TOUCH_ANIMATION_DURATION = 350;
        private static final int MIN_ANIMATION_DURATION = 2000;
        private int mLastFlingX;

        /**
         * 移除消息队列里的上个动作
         */
        private void startCommon() {
            view.removeCallbacks(this);
        }

        /**
         * 另外开启一个线程来横向滑动书页
         *
         * @param distance 滑动的距离
         */
        public void startByTouch(int distance) {
            startUsingDistance(distance, TOUCH_ANIMATION_DURATION);
        }

        /**
         * 异步书页偏移的 实际执行方法
         *
         * @param distance 偏移距离
         * @param during   持续时间
         */
        public void startUsingDistance(int distance, int during) {
            if (distance == 0)
                return;
            startCommon();
            mLastFlingX = 0;
            // 起始点为（0，0），x偏移量为 -distance ，y的偏移量为 0，持续时间
            mScroller.startScroll(0, 0, -distance, 0, Math.max(MIN_ANIMATION_DURATION, Math.abs(distance) * during / size[0]));
            view.post(this);
        }

        /**
         * 停止滑动
         */
        private void endFling() {
            mScroller.forceFinished(true);
        }

        @Override
        public void run() {

            boolean more = mScroller.computeScrollOffset();// 返回true的话则动画还没有结束
            final int x = mScroller.getCurrX();// 返回滚动时 当前的x坐标
            Log.d("youyang","mScroller.getCurrX()"+x);
            int delta = mLastFlingX - x;
            if (delta != 0) {
                mMoveX += delta;
                Log.d("youyang","mMoveX"+mMoveX +", size[0]:"+size[0]);

                float process = Math.abs(mMoveX) / (size[0]);
                float k = process % limit;

                if (process > limit) {
                    process = 0;
                    count++;
                }
                if (count % 2 == 0) {
                    render.setProcess(k);
                } else {
                    render.setProcess(limit - k);

                }

                view.requestRender();
            }
            if (more) {
                mLastFlingX = x;
                view.post(this);
            } else {
                endFling();
            }

        }
    }

    /**
     * 检验是否支持opengl 2.0
     */
    private boolean isSupportES2() {
        return ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE))
                .getDeviceConfigurationInfo().reqGlEsVersion >= 0x20000;
    }

    /**
     * 方法描述：获取屏幕宽高
     */
    public static int[] getScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay()
                .getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        int[] size = {mScreenWidth, mScreenHeight};
        localDisplayMetrics = null;
        return size;
    }
}
