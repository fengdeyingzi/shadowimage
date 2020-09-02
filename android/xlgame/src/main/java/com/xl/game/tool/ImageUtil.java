package com.xl.game.tool;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/*
图片操作工具类 可以对图片进行压缩 读取 缩放 旋转等操作
zoomImageFile 压缩文件
 */
public class ImageUtil {


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
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        //是否按比例缩放
        boolean isScale = true;
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        if(isScale){
            if((newWidth/width)>newHeight/height){
                //以高度为准
                scaleWidth = (float) (newWidth/height);
                scaleHeight = (float) (newHeight/height);
            }
            else{
                scaleWidth = (float) (newWidth/width);
                scaleHeight = (float) (newHeight/width);
            }
        }
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
        return bitmap;
    }

    //将图片顺时针旋转指定的角度
    public static Bitmap rotateBimap(Bitmap srcBitmap,float degree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap bitmap = Bitmap.createBitmap(srcBitmap,0,0,srcBitmap.getWidth(),srcBitmap.getHeight()
                ,matrix,true);
        return bitmap;
    }


    //按比例裁剪bitmap
    public static Bitmap clipBitmap(Bitmap image,int x,int y){
        int width= image.getWidth();
        int height= image.getHeight();

        int width_new = 0;
        int height_new = 0;
        //以高度为准裁剪
        if(((double)width)/height> ((double)x)/y){
            width_new= height*x/y;
            height_new= height;
        }
        else{
            width_new= width;
            height_new= width*y/x;
        }
        return Bitmap.createBitmap(image,0,0,width_new,height_new);
    }

    public static Bitmap clipBitmap(Bitmap image, int x,int y,int width,int height){
        return Bitmap.createBitmap(image,x,y,width,height);
    }

    //
    public static void saveBitmapToJPG(Bitmap bitmap,int load, File file)
    {
        //int load = 0;
        try
        {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,load,out);
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    //
    public static void saveBitmapToJPG(Bitmap bitmap,int load,String filename)
    {
        // int load = 0;
        try
        {
            FileOutputStream out = new FileOutputStream(new File(filename));
            bitmap.compress(Bitmap.CompressFormat.JPEG,load,out);
            out.close();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void saveBitmapToPNG(Bitmap bitmap, File file)
    {
        int load = 0;
        try
        {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,load,out);
            out.close();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //保存bitmap为png
    public static void saveBitmapToPNG(Bitmap bitmap,String filename)
    {
        int load = 0;
        try
        {
            FileOutputStream out = new FileOutputStream(new File(filename));
            bitmap.compress(Bitmap.CompressFormat.PNG,load,out);
            out.close();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //获取指定大小的bitmap
    private static Bitmap sizeCompres(String path, int rqsW, int rqsH) {
        // 用option设置返回的bitmap对象的一些属性参数
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;// 设置仅读取Bitmap的宽高而不读取内容
        BitmapFactory.decodeFile(path, options);// 获取到图片的宽高，放在option里边
        final int height = options.outHeight;//图片的高度放在option里的outHeight属性中
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (rqsW == 0 || rqsH == 0) {
            options.inSampleSize = 1;
        } else if (height > rqsH || width > rqsW) {
            final int heightRatio = Math.round((float) height / (float) rqsH);
            final int widthRatio = Math.round((float) width / (float) rqsW);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            options.inSampleSize = inSampleSize;
        }
        options.inJustDecodeBounds=false;
        return BitmapFactory.decodeFile(path, options);// 主要通过option里的inSampleSize对原图片进行按比例压缩
    }

    //读取图片
    public static Bitmap getBitmap(String path){
        return BitmapFactory.decodeFile(path);
    }

    //只获取图片的宽高
    public static BitmapFactory.Options getBitmapInfo(String path){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;// 设置仅读取Bitmap的宽高而不读取内容
        BitmapFactory.decodeFile(path, options);// 获取到图片的宽高，放在option里边
        return options;
    }

    //图片文件压缩 返回压缩后的图片文件
    public static String zoomImageFile(Context context, String path){
        Bitmap bitmap= sizeCompres(path, 1024, 1024);
        File file= new File(path);
        File file_new = new File(context.getFilesDir(), file.getName()+".jpg");
        saveBitmapToJPG(bitmap, 80, file_new);
        return file_new.getAbsolutePath();
    }

    public static File zoomImageFile(Context context,File path){
        String result = zoomImageFile(context, path.getPath());

        return new File(result);
    }


    //从assets目录获取bitmap
    public static Bitmap getBitmapFromAssets(Context context, String name){
        AssetManager am = context.getResources().getAssets();
        Bitmap image= null;
        InputStream is = null;
        try {
            is = am.open(name);  image = BitmapFactory.decodeStream(is).copy(Bitmap.Config.ARGB_8888,true);
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

}
