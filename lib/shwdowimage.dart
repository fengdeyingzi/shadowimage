
import 'dart:async';

import 'package:flutter/services.dart';

class Shadowimage {
  static const MethodChannel _channel =
      const MethodChannel('shadowimage');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
  //获取水印图片
  static Future<Map<String,String>> jsonPath(String jsonStr) async{
    Map<String,String> args = {
      "json":jsonStr,
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
    return result;
  }
//两张图片合成一张
  static Future<String> imgSynthesis(String path, String templateImgPath,String gravity, String outPath) async{
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
    return result["path"];
  }
  
  //旋转图片
  static Future<String> imgRotate(String path, String rotate) async{
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
    if(result["type"] == "success"){
      return result["path"];
    }
    else{
      print("旋转图片失败：${result['msg']}");
    }
    return null;
  }


}
