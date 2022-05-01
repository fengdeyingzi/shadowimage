
import 'dart:async';
import 'dart:io';
import 'package:flutter/services.dart';
import 'package:exif/exif.dart';


class Shadowimage {
  static const MethodChannel _channel =
      const MethodChannel('shadowimage');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
  //获取水印图片
  static Future<Map<String,String>> jsonPath(String jsonStr,String dir, String outPath) async{
    print("开始获取水印图片");
    Map<String,String> args = {
      "json":jsonStr,
      "out": outPath,
      "dir":dir
    };
     var reages = await _channel.invokeMethod("getJsonBitmap",args);
     if(reages == null){
       print("错误: 获取jsonBitmap失败");
     }
     Map<String,String> result = new Map();
      reages.forEach((k,v){
        result[k] = v;
        print('${k}: ${v}');
      });
    print("获取水印图片完成${result}");
    return result;
  }
//两张图片合成一张
  static Future<String> imgSynthesis(String path, String templateImgPath,String gravity, String outPath) async{
    print("两张图片合成一张");
    Map<String,String> args = {
      "dst":path,
      "src":templateImgPath,
      "gravity":gravity??"left|top",
      "out":outPath,
      "format":"jpg"
    };
    var reages = await _channel.invokeMethod("synthesisImg",args);
    Map<String,String> result = new Map();
    reages.forEach((k,v){
      result[k] = v;
      print('${k}: ${v}');
    });
    print("合成完成${result}");
    return result["path"];
  }

//获取图片旋转角度
  static Future<int> imgOrientation(String path) async{
      var fileBytes = null;

    fileBytes = File(path).readAsBytesSync();
  
  final data = await readExifFromBytes(fileBytes);
  if (data.isEmpty) {
    print("No EXIF information found");
    return 0;
  }
  if (data.containsKey('Image Orientation')) {
    print('照片旋转角度：${data["Image Orientation"]}');
    var orientation = data["Image Orientation"];
    if(orientation == "Rotated 90 CW"){
      return 90;
    } else if(orientation == "Rotated 90 CCW"){
      return 270;
    } else if(orientation == "Rotated 180"){
      return 180;
    } else if(orientation == "Rotated 270 CW"){
      return 270;
    } else if(orientation == "Rotated 270 CCW"){
      return 90;
    }
  }else{
    print("没有获取到照片角度");
  }
  
    return 0;
  }
  
  //旋转图片
  static Future<String> imgRotate(String path, String rotate) async{
    print("旋转图片");
    Map<String,String> args = {
      "path":path,
      "rotate":rotate,
      "format":"png"
    };
    print("开始旋转");
    var reages = await _channel.invokeMethod("rotateImg",args);
    print("旋转成功");
    Map<String,String> result = new Map();
    reages.forEach((k,v){
      result[k] = v;
      print('${k}: ${v}');
    });
    print("旋转图片完成${result}");
    if(result["type"] == "success"){
      return result["path"];
    }
    else{
      print("旋转图片失败：${result['msg']}");
    }
    return null;
  }


}
