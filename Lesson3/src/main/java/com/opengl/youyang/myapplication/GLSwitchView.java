package com.opengl.youyang.myapplication;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

/**
 * Created by youyang on 16/7/24.
 */
public class GLSwitchView extends GLSurfaceView {


    float process = 0.0f;
    int count = 0;

    float limit = 1.0f;
    private int[] size;
    float moveX = 0;

    private Scroller mScroller;

    private RenderListener mRenderListener;

    public GLSwitchView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context, new DecelerateInterpolator());
        mFlingRunnable = new FlingRunnable();
        size = getScreenSize(context);
    }

    public void setRenderListener(RenderListener renderListener) {
        mRenderListener = renderListener;
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

    public GLSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mCurrentX = event.getX();
        mCurrentY = event.getY();

        handleTouchCommon(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getX();

                queueEvent(new Runnable() {
                    @Override
                    public void run() {
                        process = Math.abs(moveX / size[0]);
                        if(mRenderListener!=null){
                            mRenderListener.setProcess(process);
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

        requestRender();
        return true;
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
                startScollAnimation(false);
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
                distance = (int) - mLastTouchX;
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
            removeCallbacks(this);
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
            post(this);
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
//            Log.d("youyang", "mScroller.getCurrX()" + x);
            int delta = mLastFlingX - x;
            if (delta != 0) {
                mMoveX += delta;
                Log.d("youyang", "size[0]" + size[0] + ", size[1]:" + size[1]);

                float process = Math.abs(mMoveX) / (size[0]);
                Log.d("youyang", "process" + process);
                if(mRenderListener!=null){
                    mRenderListener.setProcess(process);
                }
                requestRender();
            }
            if (more) {
                mLastFlingX = x;
                post(this);
            } else {
                endFling();
            }

        }
    }

    interface RenderListener{
        void setProcess(float process);
    }
}
