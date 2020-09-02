package com.xl.game.view;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
/*
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
*/
/**
 * 自定义Drawable 实现圆形图片
 * Created by hust_twj on 2017/9/26.
 */

public class CircleDrawable extends Drawable {
	private Paint mPaint;
	private int mWidth; //宽/高，直径
	private Bitmap mBitmap;

	public CircleDrawable(Bitmap bitmap) {
		this.mBitmap = bitmap;
		BitmapShader bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);//着色器 水平和竖直都需要填充满
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setShader(bitmapShader);
		mWidth = Math.min(mBitmap.getWidth(), mBitmap.getHeight()); //宽高的最小值作为直径
	}

	@Override
	public void draw( Canvas canvas) {
		canvas.drawCircle(mWidth/2, mWidth/2, mWidth/2, mPaint); //绘制圆形
	}

	@Override
	public void setAlpha(int i) {
		mPaint.setAlpha(i);
	}

	@Override
	public void setColorFilter( ColorFilter colorFilter) {
		mPaint.setColorFilter(colorFilter);
	}

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT; //设置系统默认，让drawable支持和窗口一样的透明度
	}

	//还需要从重写以下2个方法，返回drawable实际宽高
	@Override
	public int getIntrinsicWidth() {
		return mWidth;
	}

	@Override
	public int getIntrinsicHeight() {
		return mWidth;
	}


}
