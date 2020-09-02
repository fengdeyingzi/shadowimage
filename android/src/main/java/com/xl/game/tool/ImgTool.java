package com.xl.game.tool;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ImgTool
{
//添加到媒体库

	public static void FileToMedia(Context context, String filename)
	{
		MediaScannerConnection.scanFile(context, new String[] { filename }, null,                    
			new MediaScannerConnection.OnScanCompletedListener() 
			{
				public void onScanCompleted(String path, Uri uri) 
				{
					//Log.i("ExternalStorage","Scanned " + path + ":");
					//Log.i("ExternalStorage", "-> uri=" + uri);
				}
			});
	}
	
	

	/**
     * 根据指定的图像路径和大小来获取缩略图
     * 此方法有两点好处：
     *     1. 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
     *        第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。
     *     2. 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使
     *        用这个工具生成的图像不会被拉伸。
     * @param imagePath 图像的路径
     * @param width 指定输出图像的宽度
     * @param height 指定输出图像的高度
     * @return 生成的缩略图
     */
	 public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
	 Bitmap bitmap = null;
	 BitmapFactory.Options options = new BitmapFactory.Options();
	 options.inJustDecodeBounds = true;
	 // 获取这个图片的宽和高，注意此处的bitmap为null
	 bitmap = BitmapFactory.decodeFile(imagePath, options);
	 options.inJustDecodeBounds = false; // 设为 false
	 // 计算缩放比
	 int h = options.outHeight;
	 int w = options.outWidth;
	 int beWidth = w / width;
	 int beHeight = h / height;
	 int be = 1;
	 if (beWidth < beHeight) {
	 be = beWidth;
	 } else {
	 be = beHeight;
	 }
	 if (be <= 0) {
	 be = 1;
	 }
	 options.inSampleSize = be;
	 // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
	 bitmap = BitmapFactory.decodeFile(imagePath, options);
	 // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
	 bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
	 ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
	 return bitmap;
	 }

	Bitmap stringToBitmap(String string) {
		//将字符串转换成Bitmap类型
		Bitmap bitmapd = null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(string, Base64.DEFAULT);
			bitmapd = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmapd;
	}

	public String bitmaptoString(Bitmap bitmap) {
		//将Bitmap转换成字符串String
		String string = null;
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
		byte[] bytes = bStream.toByteArray();
		string = Base64.encodeToString(bytes, Base64.DEFAULT);
		return string;
	}
}
