package com.cbt.main.view.sceneweather;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

import com.cbt.main.R;

/**
 * Created by vigorous on 18/6/7.
 *
 */

public class SunShineView extends BaseView {
    protected Matrix matrix = new Matrix();
    float initPositionX;
    float initPositionY;
    boolean isInit;
    Bitmap frame;
    RectF box;
    RectF targetBox;
    Paint paint = new Paint();
    int alpha;
    boolean alphaUp = true;

    public SunShineView(Context context, float width, float height) {
        super(context, width, height);
    }

    @Override
    public void initUI() {
        box = new RectF();
        targetBox = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInit) {
            paint.setAntiAlias(true);
            initPositionX = width * 0.275F;
            initPositionY = height * 0.365F;
            frame = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.sunshine);
            box.set(0, 0, frame.getWidth(), frame.getHeight());
            matrix.reset();
            matrix.setScale(2f, 2f);
            matrix.mapRect(targetBox, box);
            matrix.postTranslate(initPositionX - targetBox.width() / 2, initPositionY - targetBox.height() / 2);
            isInit = true;
        }
        //旋转
        matrix.mapRect(targetBox, box);
        matrix.postRotate(0.5F, targetBox.centerX(), targetBox.centerY());
        //透明度变化
        if (alphaUp) {
            alpha++;
        } else {
            alpha--;
        }
        if (alpha >= 255) {
            alphaUp = false;
        }
        if (alpha <= 0) {
            alphaUp = true;
        }
        paint.setAlpha(alpha);
        //绘制
        canvas.drawBitmap(frame, matrix, paint);
    }
}
