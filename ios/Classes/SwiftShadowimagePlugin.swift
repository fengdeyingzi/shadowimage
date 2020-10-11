import Flutter
import UIKit


public class SwiftShadowimagePlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "shadowimage", binaryMessenger: registrar.messenger())
    let instance = SwiftShadowimagePlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    if(call.method == "getPlatformVersion"){
        result("iOS phone " + UIDevice.current.systemVersion)
    }
        //通过json获取bitmap
    else if(call.method == "getJsonBitmap"){
        var map: [String:String] = [:]
        var args: [String: String] = call.arguments as! [String : String] //["YYZ": "Toronto Pearson", "DUB": "Dublin"]
        var out_path:String = args["out"]!
        var json = args["json"] as! String
        var dir = args["dir"] as! String
//             File file_out = new File(context.getExternalCacheDir(),"out.png");
//             if(out_path!=null){
//               file_out = new File(out_path);
//             }
        let image = ImageUtil.jsonBitmap(json, dir: dir)
        map["type"] = "success"
        if(image != nil){
            ImageUtil.saveImageToPNG(image!, path: out_path)
        }
        else{
            map["type"] = "error"
            map["msg"] = "生成图片失败"
        }
        map["path"] = out_path
        result(map)
//             try {
//               Bitmap bitmap = ImageUtil.jsonBitmap(context, args.get("json"),args.get("dir"));
//               ImageUtil.saveBitmapToPNG(bitmap, file_out);
//             } catch (JSONException e) {
//               map.put("type","error");
//               map.put("msg",e.getMessage());
//             }

//             map.put("path", file_out.getAbsolutePath());
    }
        //合成图片
    else if(call.method == "synthesisImg"){
        var map: [String:String] = [:]
        var args: [String: String] = call.arguments as! [String : String] //
        map["type"] = "success"
        var dstPath = args["dst"]
        var srcPath = args["src"]
        var gravity = args["gravity"]
        var format = args["format"]
        var out_path = args["out"]
        var gravity_value:Int = 0
         if(gravity?.range(of: "left") != nil){
            gravity_value |= ImageUtil.LEFT;
        }
        if(gravity?.range(of: "top") != nil){
            gravity_value |= ImageUtil.TOP;
        }
        if(gravity?.range(of: "right") != nil){
            gravity_value |= ImageUtil.RIGHT;
        }
        if(gravity?.range(of: "bottom") != nil){
            gravity_value |= ImageUtil.BOTTOM;
        }
        if(gravity?.range(of: "center") != nil){
            gravity_value |= ImageUtil.CENTER;
        }
        
        
        if(out_path == nil){
            let cachePaths = NSSearchPathForDirectoriesInDomains(FileManager.SearchPathDirectory.cachesDirectory, FileManager.SearchPathDomainMask.userDomainMask, true)
            let cachePath = cachePaths[0] as! String
            out_path = cachePath+"/out_synthesis.png"
        }
        var dst_temp = UIImage.init(contentsOfFile: dstPath!)
        var src_temp = UIImage.init(contentsOfFile: srcPath!)
        
        dst_temp = ImageUtil.clipBitmapCenter(dst_temp!, x: Int(src_temp!.size.width),y: Int(src_temp!.size.height));
        dst_temp = ImageUtil.zoomImage(dst_temp!, newWidth: Int(src_temp!.size.width),newHeight: Int(src_temp!.size.height));
        
        let img_out:UIImage = ImageUtil.synthesisImg(img_dst: dst_temp!, img_src: src_temp!, gravity:gravity_value)
        ImageUtil.saveImageToPNG(img_out, path: out_path!)
        map["path"] = out_path
        result(map)
//         HashMap<String,String> map = new HashMap<>();
//              map.put("type","success");
//              HashMap<String,String> args = (HashMap<String, String>) call.arguments;
//              String dstPath = args.get("dst");
//              String srcPath = args.get("src");
//              String gravity = args.get("gravity");
//              String format = args.get("format");
//              String out_path = args.get("out");
//              Bitmap dst_temp = BitmapFactory.decodeFile(dstPath).copy(Bitmap.Config.ARGB_8888,true);
//              Bitmap src_temp = BitmapFactory.decodeFile(srcPath);
//              File file_out = new File(context.getExternalCacheDir(),"out_synthesis.png");
//              if(out_path!=null){
//                file_out = new File(out_path);
//              }
//              int gravity_value = 0;
//
//              if(gravity.indexOf("left")>=0){
//                gravity_value |= ImageUtil.LEFT;
//              }
//              if(gravity.indexOf("top")>=0){
//                gravity_value |= ImageUtil.TOP;
//              }
//              if(gravity.indexOf("right")>=0){
//                gravity_value |= ImageUtil.RIGHT;
//              }
//              if(gravity.indexOf("bottom")>=0){
//                gravity_value |= ImageUtil.BOTTOM;
//              }
//              if(gravity.indexOf("center")>=0){
//                gravity_value |= ImageUtil.CENTER;
//              }
//              dst_temp = ImageUtil.clipBitmapCenter(dst_temp, src_temp.getWidth(),src_temp.getHeight());
//              dst_temp = ImageUtil.zoomImage(dst_temp, src_temp.getWidth(),src_temp.getHeight());
//
//               Bitmap bitmap = ImageUtil.synthesisImg(dst_temp,src_temp,gravity_value);
//               if(format.equals("jpg")){
//                 ImageUtil.saveBitmapToJPG(bitmap,60, file_out);
//               }
//               else if(format.equals("png")){
//                 ImageUtil.saveBitmapToPNG(bitmap,file_out);
//               }
//
//               map.put("path",file_out.getAbsolutePath());
//               result.success(map);
    }
        //旋转图片
    else if(call.method == "rotateImg"){
        var map: [String:String] = [:]
        var args: [String: String] = call.arguments as! [String : String]
        map["type"] = "success"
        var rotate = args["rotate"] as! String
        var imgPath = args["path"] as! String
        var format = args["format"] as! String
        var out_path:String? = args["out"] as? String
        
        if(out_path == nil){
            let cachePaths = NSSearchPathForDirectoriesInDomains(FileManager.SearchPathDirectory.cachesDirectory, FileManager.SearchPathDomainMask.userDomainMask, true)
            let cachePath = cachePaths[0] as! String
            out_path = cachePath+"/out_rotate.png"
        }
        
        print("旋转图片%s",out_path)
        var src_temp = UIImage.init(contentsOfFile: imgPath)
        let temp:UIImage = ImageUtil.rotateImage(src_temp!, withAngle: Double.init(rotate)!)!
        if(format == "jpg"){
            ImageUtil.saveImageToJPG(temp,load: 80, path: out_path!)
        }
        else if(format == "png"){
            ImageUtil.saveImageToPNG(temp, path: out_path!)
        }
        map["path"] = out_path
        result(map)
//        HashMap<String,String> map = new HashMap<>();
//            map.put("type","success");
//            HashMap<String,String> args = (HashMap<String, String>) call.arguments;
//            String rotate = args.get("rotate");
//            String imgPath = args.get("path");
//            String format = args.get("format");
//            String out_path = args.get("out");
//            Bitmap src_temp = BitmapFactory.decodeFile(imgPath);
//            Bitmap temp = ImageUtil.rotateBimap(src_temp, Double.valueOf(rotate).floatValue());
//            File file_out = new File(context.getExternalCacheDir(),"out_rotate.png");
//            if(out_path!=null){
//              file_out = new File(out_path);
//            }
//            if(format.equals("jpg")){
//              ImageUtil.saveBitmapToJPG(temp,60, file_out);
//            }
//            else if(format.equals("png")){
//              ImageUtil.saveBitmapToPNG(temp,file_out);
//            }
//            map.put("type","success");
//            map.put("path",file_out.getAbsolutePath());
//            result.success(map);
    }
    else{
        result("")
    }
    
  }
}
