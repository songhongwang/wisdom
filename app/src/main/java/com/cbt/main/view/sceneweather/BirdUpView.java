package com.cbt.main.view.sceneweather;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

import com.cbt.main.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vigorous on 18/6/7.
 *
 */

public class BirdUpView extends BaseView {
    protected Matrix matrix = new Matrix();

    private static final int[] imgs = new int[]{R.drawable.finedayup_1, R.drawable.finedayup_2, R.drawable.finedayup_3, R.drawable.finedayup_4, R.drawable.finedayup_5, R.drawable.finedayup_6, R.drawable.finedayup_7, R.drawable.finedayup_8};

    float initPositionX;
    float initPositionY;
    boolean isInit;
    List<Bitmap> frames;
    RectF box;
    RectF targetBox;
    int curFrameIndex;
    long lastTime;
    Paint paint = new Paint();

    public BirdUpView(Context context, float width, float height) {
        super(context, width, height);
    }

    @Override
    public void initUI() {
        frames = new ArrayList<>();
        box = new RectF();
        targetBox = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //逻辑处理
        //初始化
        if (!isInit) {
            paint.setAntiAlias(true);
            initPositionX = width * 0.117F;
            initPositionY = height * 0.35F;
            matrix.reset();
            matrix.postTranslate(initPositionX, initPositionY);
            for (int res : imgs) {
                frames.add(BitmapFactory.decodeResource(mContext.getResources(), res));
            }
            box.set(0, 0, frames.get(0).getWidth(), frames.get(0).getHeight());
            isInit = true;
            lastTime = System.currentTimeMillis();
        }
        //移动
        matrix.postTranslate(4, 0);
        //边界处理
        matrix.mapRect(targetBox, box);
        if (targetBox.left > width) {
            matrix.postTranslate(-targetBox.right, 0);
        }
        //取得帧动画图片
        long curTime = System.currentTimeMillis();
        curFrameIndex = (int) ((curTime - lastTime) / 500 % 8);

        Bitmap curBitmap = frames.get(curFrameIndex);
        //绘制
        canvas.save();
        canvas.drawBitmap(curBitmap, matrix, paint);
        canvas.restore();
    }
}
