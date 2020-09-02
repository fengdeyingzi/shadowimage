/*
 * Decompiled with CFR 0_58.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.
 *  android.graphics.Rect
 *  android.graphics.drawable.BitmapDrawable
 *  java.io.InputStream
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 */
package com.xl.game.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class Image {
	//获取裁剪的bitmap
    public static Bitmap BitmapClipBitmap(Bitmap bitmap, int n, int n2, int n3, int n4) {
        return Bitmap.createBitmap((Bitmap)(bitmap), (int)(n), (int)(n2), (int)(n3), (int)(n4));
    }

    public static Bitmap CreateMap(Bitmap bitmap, int n, int n2, int n3) {
        Bitmap bitmap2 = Bitmap.createBitmap((int)((n2 * n)), (int)(n3), (Bitmap.Config)(Bitmap.Config.ARGB_8888));
        Canvas canvas = new Canvas(bitmap2);
        canvas.drawColor(-1);
        int n4 = 0;
        while (n4 < n) {
            canvas.drawBitmap(bitmap, (float)((n2 * n4)), 0, (Paint)(null));
            ++n4;
        }
        return bitmap2;
    }

    public static BitmapDrawable Drs(Context context, String string) {
        Bitmap bitmap = Image.ReadBitMap(context, string);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
        bitmapDrawable.setDither(true);
        return bitmapDrawable;
    }

    public static Bitmap FitTheImage(Bitmap bitmap, float f, float f2) {
        Matrix matrix = new Matrix();
        matrix.postScale(f, f2);
        return Bitmap.createBitmap((Bitmap)(bitmap), (int)(0), (int)(0), (int)(bitmap.getWidth()), (int)(bitmap.getHeight()), (Matrix)(matrix), (boolean)(true));
    }

    public static Bitmap FitTheScreenSizeImage(Bitmap bitmap, int n, int n2) {
        float f = ((float)(n) / (float)(bitmap.getWidth()));
        float f2 = ((float)(n2) / (float)(bitmap.getHeight()));
        Matrix matrix = new Matrix();
        matrix.postScale(f, f2);
        return Bitmap.createBitmap((Bitmap)(bitmap), (int)(0), (int)(0), (int)(bitmap.getWidth()), (int)(bitmap.getHeight()), (Matrix)(matrix), (boolean)(true));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public static Bitmap ReadBitMap(Context context, String string) {
        InputStream inputStream;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        try {
            InputStream inputStream2;
            inputStream = (inputStream2 = context.getAssets().open(string));
            return BitmapFactory.decodeStream((InputStream)(inputStream), (Rect)(null), (BitmapFactory.Options)(options));
        }
        catch (Exception var3_5) {
            var3_5.printStackTrace();
            inputStream = null;
        }
        return BitmapFactory.decodeStream((InputStream)(inputStream), (Rect)(null), (BitmapFactory.Options)(options));
    }
	
	
	public static Bitmap readBitmapFromDrawable(Context context,int r)
	{
	//根据资源id获取图片资源的原始尺寸大小
	BitmapFactory.Options options = new BitmapFactory.Options();
	options.inJustDecodeBounds = true;
	return BitmapFactory.decodeResource(context.getResources(), r, options);

	//item.setSrcsize(new Point(options.outWidth, options.outHeight));
	}
	
	/** 
     * 给bitmap画圆角 
     *  
     * @param bitmap 
     *            bitmap对象 
     * @param roundPX 
     *            圆角的角度 
     * @return 画好圆角后的bitmap对象 
     */  
    public static Bitmap roundBitmap(Bitmap bitmap, float roundPX) {  
        try {  

            final int width = bitmap.getWidth();  
            final int height = bitmap.getHeight();  

            Bitmap outputBitmap = Bitmap.createBitmap(bitmap.getWidth(),  
													  bitmap.getHeight(), Config.ARGB_8888);  
            Canvas canvas = new Canvas(outputBitmap);  
            final Paint paint = new Paint();  
            final Rect rect = new Rect(0, 0, width, height);  
            final RectF rectF = new RectF(rect);  


            paint.setAntiAlias(true);  
            paint.setFilterBitmap(true);  
            canvas.drawARGB(0, 0, 0, 0);  
            paint.setColor(Color.WHITE);  
            canvas.drawRoundRect(rectF, roundPX, roundPX, paint);  
            final PorterDuffXfermode pdx = new PorterDuffXfermode(  
				PorterDuff.Mode.SRC_IN);  
            paint.setXfermode(pdx);  


            canvas.drawBitmap(bitmap, rect, rect, paint);  
            bitmap.recycle();  

            return outputBitmap;  
        } catch (Exception e) {  
            return bitmap;  
        }  
   }
	
   
   
	/**
	 * 水平翻转处理
	 * @param bitmap 原图
	 * @return 水平翻转后的图片
	 */
	public static Bitmap reverseByHorizontal(Bitmap bitmap){
		Matrix matrix = new Matrix();
		matrix.preScale(-1, 1);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
	}

	/**
	 * 垂直翻转处理
	 * @param bitmap 原图
	 * @return 垂直翻转后的图片
	 */
	public static Bitmap reverseByVertical(Bitmap bitmap){
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
	}
	


	/*** 
	 * 图片的缩放方法 
	 *  
	 * @param bgimage 
	 *            ：源图片资源 
	 * @param newWidth 
	 *            ：缩放后宽度 
	 * @param newHeight 
	 *            ：缩放后高度 
	 * @return 
	 */  
	public static Bitmap zoomImage(Bitmap bgimage, int newWidth,  
								   int newHeight) {  
		// 获取这个图片的宽和高  
		int  width = bgimage.getWidth();  
		int  height = bgimage.getHeight();  
		// 创建操作图片用的matrix对象  
		Matrix matrix = new Matrix();  
		// 计算宽高缩放率  
		float scaleWidth = ((float) newWidth) / (float)width;  
		float scaleHeight = ((float) newHeight) / (float)height;  
		// 缩放图片动作  
		matrix.postScale(scaleWidth, scaleHeight);  
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);  
		return bitmap;  
	}

	public static void saveBitmap(Bitmap bitmap,File file) 
	{
		int load = 0;
		try
		{
			FileOutputStream out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG,load,out);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public static void saveBitmap(Bitmap bitmap,String filename) 
	{
		int load = 0;
		try
		{
			FileOutputStream out = new FileOutputStream(new File(filename));
			bitmap.compress(Bitmap.CompressFormat.PNG,load,out);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
}

