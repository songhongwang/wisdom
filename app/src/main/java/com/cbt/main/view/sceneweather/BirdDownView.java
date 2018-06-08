package com.cbt.main.view.sceneweather;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.cbt.main.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vigorous on 18/6/8.
 */

public class BirdDownView extends View {
    private Context mContext;
    protected Matrix matrix = new Matrix();
    private float width;
    private float height;

    private static final int[] imgs = new int[]{R.drawable.finedaydown_1, R.drawable.finedaydown_2, R.drawable.finedaydown_3, R.drawable.finedaydown_4, R.drawable.finedaydown_5, R.drawable.finedaydown_6, R.drawable.finedaydown_7, R.drawable.finedaydown_8};

    float initPositionX;
    float initPositionY;
    boolean isInit;
    List<Bitmap> frames;
    RectF box;
    RectF targetBox;
    int curFrameIndex;
    long lastTime;
    Paint paint = new Paint();

    public BirdDownView(Context context) {
        super(context);
        initUI(context);
    }

    public BirdDownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    public BirdDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI(context);
    }

    private void initUI(Context context) {
        mContext = context;

        frames = new ArrayList<Bitmap>();
        box = new RectF();
        targetBox = new RectF();
        paint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //逻辑处理
        //初始化
        if (!isInit) {
            initPositionX = width * 0.117F;
            initPositionY = height * 0.785F;
            matrix.reset();
            matrix.postTranslate(initPositionX, initPositionY);
            for (int res : imgs) {
                frames.add(BitmapFactory.decodeResource(mContext.getResources(), res));
            }
            box.set(0, 0, frames.get(0).getWidth(), frames.get(0).getHeight());
            isInit = true;
            lastTime = System.currentTimeMillis();
            return;
        }
        //移动
        matrix.postTranslate(2, 0);
        //边界处理
        matrix.mapRect(targetBox, box);
        if (targetBox.left > width) {
            matrix.postTranslate(-targetBox.right, 0);
        }
        //取得帧动画图片
        long curTime = System.currentTimeMillis();
        curFrameIndex = (int) ((curTime - lastTime) / 200 % 8);
        Bitmap curBitmap = frames.get(curFrameIndex);
        //绘制
        canvas.save();
        canvas.drawBitmap(curBitmap, matrix, paint);
        canvas.restore();
    }
}
