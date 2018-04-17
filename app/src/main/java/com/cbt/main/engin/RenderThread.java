package com.cbt.main.engin;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;

import com.cbt.main.R;

/**
 * Created by wujiajun
 * @author 928320442@qq.com
 */
public class RenderThread extends Thread {
    public static final int FLAG_DRAWING = 0;
    public static final int FLAG_SLEEPING = 1;

    private Context context;
    private SurfaceHolder surfaceHolder;
    private RenderHandler renderHandler;
    private Scene scene;
    private boolean mIsStop = false;

    public RenderThread(SurfaceHolder surfaceHolder, Context context) {
        this.context = context;
        this.surfaceHolder = surfaceHolder;
        scene = new Scene(context);
        //add scene/actor
        scene.setBg(BitmapFactory.decodeResource(context.getResources(), R.drawable.bg0_fine_day));
        scene.add(new BirdUp(context));
        scene.add(new CloudLeft(context));
        scene.add(new CloudRight(context));
        scene.add(new BirdDown(context));
        scene.add(new SunShine(context));
    }

    @Override
    public void run() {
        Log.d("weather", "run");
        //在非主线程使用消息队列
        Looper.prepare();
        renderHandler = new RenderHandler();
        renderHandler.sendEmptyMessage(FLAG_DRAWING);
        Looper.loop();
    }

    public RenderHandler getRenderHandler() {
        return renderHandler;
    }

    public class RenderHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FLAG_DRAWING:
                    if (scene.getWidth() != 0 && scene.getHeight() != 0) {
                        draw();
                    }
                    renderHandler.sendEmptyMessage(FLAG_DRAWING);
                    break;
                case FLAG_SLEEPING:
                    renderHandler.sendEmptyMessageDelayed(FLAG_SLEEPING, 500);
                    break;
            }
        }
    }


    private void draw() {
        if(mIsStop){
            return;
        }
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas != null) {
            scene.draw(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }


    public void setWidth(int width) {
        scene.setWidth(width);
    }

    public void setHeight(int height) {
        scene.setHeight(height);
    }


    public void setStop(boolean stop) {
        mIsStop = stop;
    }
}
