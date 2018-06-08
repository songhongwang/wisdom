package com.cbt.main.view.sceneweather;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.cbt.main.R;

/**
 * Created by vigorous on 18/6/7.
 *
 */

public class CloudLeftView extends BaseView {
    protected Matrix matrix = new Matrix();
    float initPositionX;
    float initPositionY;
    boolean isInit;
    Bitmap frame;
    RectF box;
    RectF targetBox;
    Paint paint = new Paint();

    public CloudLeftView(Context context, float w, float h) {
        super(context, w, h);
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
            Log.d("weather", "cloud init");
            paint.setAntiAlias(true);
            initPositionX = width * 0.039F;
            initPositionY = height * 0.69F;
            frame = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.fine_day_cloud1);
            box.set(0, 0, frame.getWidth(), frame.getHeight());
            matrix.reset();
            matrix.setScale(2f, 2f);
            matrix.mapRect(targetBox, box);
            matrix.postTranslate(initPositionX - targetBox.width() / 2, initPositionY - targetBox.height() / 2);
            isInit = true;
        }
        //移动
        matrix.postTranslate(0.5F, 0);
        //边界处理
        matrix.mapRect(targetBox, box);
        if (targetBox.left > width) {
            matrix.postTranslate(-targetBox.right, 0);
        }
        //绘制
        canvas.drawBitmap(frame, matrix, paint);
    }
}
