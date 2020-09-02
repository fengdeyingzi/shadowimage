
import 'dart:async';

import 'package:flutter/services.dart';

class Shadowimage {
  static const MethodChannel _channel =
      const MethodChannel('shadowimage');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
  
  static Future<Map<String,String>> jsonPath(String jsonStr) async{
     var reages = await _channel.invokeListMethod("getJsonBitmap");
     Map<String,String> result = new Map();
    //  reages.forEach((k,v){
    //    result[k] = v;
    //    print('${k}: ${v}');
    //  });
    return result;
  }


}
