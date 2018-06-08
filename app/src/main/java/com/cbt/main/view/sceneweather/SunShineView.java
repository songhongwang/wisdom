package com.cbt.main.view.sceneweather;

import android.app.Activity;
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
import com.cbt.main.utils.ScreenUtil;

/**
 * Created by vigorous on 18/6/7.
 *
 */

public class SunShineView extends View {
    private Context mContext;

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
    private float width;
    private float height;

    public SunShineView(Context context) {
        super(context);
        initUI(context);
    }

    public SunShineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    public SunShineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI(context);
    }

    private void initUI(Context context) {
        mContext = context;

        width = ScreenUtil.getFullScreenWidth((Activity) mContext);
        height = ScreenUtil.getFullScreenHeight((Activity) mContext);

        box = new RectF();
        targetBox = new RectF();
        paint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInit) {
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
