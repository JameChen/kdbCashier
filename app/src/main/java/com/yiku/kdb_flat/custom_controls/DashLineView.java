package com.yiku.kdb_flat.custom_controls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.yiku.kdb_flat.R;


/**
 * Created by jame on 2018/11/26.
 */

public class DashLineView extends View {
    private Paint mPaint;
    private Path mPath;

    public DashLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.line_bg));
// 需要加上这句，否则画不出东西
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setPathEffect(new DashPathEffect(new float[]{15, 5}, 0));
        mPath = new Path();
    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        // 前四个参数没啥好讲的，就是起点和终点而已。 // color数组的意思是从透明 -> 黑 -> 黑 -> 透明。 // float数组与color数组对应： // 0 -> 0.3 (透明 -> 黑) // 0.3 - 0.7 (黑 -> 黑，即不变色) // 0.7 -> 1 (黑 -> 透明)
//        mPaint.setShader(new LinearGradient(0, 0, getWidth(), 0, new int[]{Color.TRANSPARENT, Color.BLACK, Color.BLACK, Color.TRANSPARENT}, new float[]{0, 0.3f, 0.7f, 1f}, Shader.TileMode.CLAMP));
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        int centerY = getHeight() / 2;
        mPath.reset();
        mPath.moveTo(0, centerY);
        mPath.lineTo(getWidth(), centerY);
        canvas.drawPath(mPath, mPaint);
    }
}
