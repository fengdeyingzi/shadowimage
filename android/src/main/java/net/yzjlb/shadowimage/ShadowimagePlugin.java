package net.yzjlb.shadowimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import net.yzjlb.imgutils.ImageUtil;

import org.json.JSONException;

import java.io.File;
import java.util.HashMap;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** ShadowimagePlugin */
public class ShadowimagePlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private Context context;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "shadowimage");
    channel.setMethodCallHandler(this);
    context = flutterPluginBinding.getApplicationContext();
    BinaryMessenger messenger = flutterPluginBinding.getBinaryMessenger();
    flutterPluginBinding
            .getPlatformViewRegistry()
//            .getPlatformViewsController()
//            .getRegistry()
            .registerViewFactory(
                    "shadowimage.yzjbl.net/ShadowImageView", new ImageViewFactory(messenger));
//    flutterCookieManager = new FlutterCookieManager(messenger);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    }
    else if(call.method.equals("getJsonBitmap")){
      HashMap<String,String> map = new HashMap<>();
      HashMap<String,String> args = (HashMap<String, String>) call.arguments;
      String out_path = args.get("out");
      File file_out = new File(context.getExternalCacheDir(),"out.png");
      if(out_path!=null){
        file_out = new File(out_path);
      }
      map.put("type","success");

      try {
        Bitmap bitmap = ImageUtil.jsonBitmap(context, args.get("json"),args.get("dir"));
        ImageUtil.saveBitmapToPNG(bitmap, file_out);
      } catch (JSONException e) {
        map.put("type","error");
        map.put("msg",e.getMessage());
      }

      map.put("path", file_out.getAbsolutePath());
      result.success(map);
    }
    //将两张图片合成一张
    else if(call.method.equals("synthesisImg")){
      HashMap<String,String> map = new HashMap<>();
      map.put("type","success");
      HashMap<String,String> args = (HashMap<String, String>) call.arguments;
      String dstPath = args.get("dst");
      String srcPath = args.get("src");
      String gravity = args.get("gravity");
      String format = args.get("format");
      String out_path = args.get("out");
      Bitmap dst_temp = BitmapFactory.decodeFile(dstPath).copy(Bitmap.Config.ARGB_8888,true);
      Bitmap src_temp = BitmapFactory.decodeFile(srcPath);
      File file_out = new File(context.getExternalCacheDir(),"out_synthesis.png");
      if(out_path!=null){
        file_out = new File(out_path);
      }
      int gravity_value = 0;

      if(gravity.indexOf("left")>=0){
        gravity_value |= ImageUtil.LEFT;
      }
      if(gravity.indexOf("top")>=0){
        gravity_value |= ImageUtil.TOP;
      }
      if(gravity.indexOf("right")>=0){
        gravity_value |= ImageUtil.RIGHT;
      }
      if(gravity.indexOf("bottom")>=0){
        gravity_value |= ImageUtil.BOTTOM;
      }
      if(gravity.indexOf("center")>=0){
        gravity_value |= ImageUtil.CENTER;
      }
      dst_temp = ImageUtil.clipBitmapCenter(dst_temp, src_temp.getWidth(),src_temp.getHeight());
      dst_temp = ImageUtil.zoomImage(dst_temp, src_temp.getWidth(),src_temp.getHeight());
//       if(dst_temp.getWidth() < src_temp.getWidth()){
//         dst_temp = ImageUtil.zoomImage(dst_temp, dst_temp.getWidth() * src_temp.getWidth()/dst_temp.getWidth(),dst_temp.getHeight() * src_temp.getWidth() /  dst_temp.getWidth());
//       }
//       else if(dst_temp.getWidth() > src_temp.getWidth()){
//         dst_temp = ImageUtil.zoomImage(dst_temp, dst_temp.getWidth() * src_temp.getWidth()/dst_temp.getWidth(),dst_temp.getHeight() * src_temp.getWidth()/dst_temp.getWidth());
//       }
       Bitmap bitmap = ImageUtil.synthesisImg(dst_temp,src_temp,gravity_value);
       if(format.equals("jpg")){
         ImageUtil.saveBitmapToJPG(bitmap,60, file_out);
       }
       else if(format.equals("png")){
         ImageUtil.saveBitmapToPNG(bitmap,file_out);
       }

       map.put("path",file_out.getAbsolutePath());
       result.success(map);
    }
    else if(call.method.equals("imgDirection")){
      HashMap<String,String> map = new HashMap<>();
      map.put("type","success");
      HashMap<String,String> args = (HashMap<String, String>) call.arguments;
      String imgPath = args.get("path");
      String format = args.get("format");





      map.put("type","success");
      map.put("direction",""+ImageUtil.getDirection(imgPath));
      result.success(map);
    }
    else if(call.method.equals("rotateImg")){
      HashMap<String,String> map = new HashMap<>();
      map.put("type","success");
      HashMap<String,String> args = (HashMap<String, String>) call.arguments;
      String rotate = args.get("rotate");
      String imgPath = args.get("path");
      String format = args.get("format");
      String out_path = args.get("out");
      Bitmap src_temp = BitmapFactory.decodeFile(imgPath);
      Bitmap temp = ImageUtil.rotateBimap(src_temp, Double.valueOf(rotate).floatValue());
      File file_out = new File(context.getExternalCacheDir(),"out_rotate.png");
      if(out_path!=null){
        file_out = new File(out_path);
      }
      if(format.equals("jpg")){
        ImageUtil.saveBitmapToJPG(temp,60, file_out);
      }
      else if(format.equals("png")){
        ImageUtil.saveBitmapToPNG(temp,file_out);
      }
      map.put("type","success");
      map.put("path",file_out.getAbsolutePath());
      result.success(map);
    }
    else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
