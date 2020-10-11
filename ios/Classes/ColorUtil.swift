//
//  ColorUtil.swift
//  ToolBox 十六进制转CGColor UIColor
//
//  Created by mac on 2020/2/15.
//  Copyright © 2020 message. All rights reserved.
//

import Foundation
import UIKit

class ColorUtil{
    
    
    //将color转cgcolor
    public static func getCGColor(_ color:Int) -> CGColor{
               let rr:Int = (color>>16)&0xff;
               let gg:Int = (color>>8)&0xff;
               let bb:Int = (color)&0xff
               let aa:Int = (color>>24)&0xff
               let fr:CGFloat = CGFloat(Float(rr)/0xff)
               let fg:CGFloat = CGFloat(Float(gg)/0xff)
               let fb:CGFloat = CGFloat(Float(bb)/0xff)
               let fa:CGFloat = CGFloat(Float(aa)/0xff)
        if #available(iOS 13.0, *) {
            return CGColor(srgbRed: fr, green: fg, blue: fb, alpha: fa)
        } else {
            return UIColor(red: fr, green: fg, blue: fb, alpha: fa).cgColor
        }
           }
    
    public static func getCGColor(_ color:String) -> CGColor{
             
        return UIColor.red.cgColor
           }
    
    //将color转cgcolor
       public static func getColor(_ color:Int) -> UIColor{
                  let rr:Int = (color>>16)&0xff;
                  let gg:Int = (color>>8)&0xff;
                  let bb:Int = (color)&0xff
                  let aa:Int = (color>>24)&0xff
                  let fr:CGFloat = CGFloat(Float(rr)/0xff)
                  let fg:CGFloat = CGFloat(Float(gg)/0xff)
                  let fb:CGFloat = CGFloat(Float(bb)/0xff)
                  let fa:CGFloat = CGFloat(Float(aa)/0xff)
        
                  return UIColor(red: fr, green: fg, blue: fb, alpha: fa)
              }
    
    public static func getColor(_ color:String) -> UIColor{
               
     
        return UIColor.red
           }
    
    //十六进制转UIColor 只支持6位的String
    class func hexToUIColor(hex:String) -> UIColor{
        

var cString: String = hex.trimmingCharacters(in: NSCharacterSet.whitespacesAndNewlines).uppercased()

if(cString.hasPrefix("#")) {

cString = (cString as NSString).substring(from:1)

}

if cString.count != 6{

return UIColor.white

}

let rString = (cString as NSString).substring(to:2)

let gString = ((cString as NSString).substring(from:2) as NSString).substring(to:2)

let bString = ((cString as NSString).substring(from:4) as NSString).substring(to:2)

var r:CUnsignedInt = 0, g:CUnsignedInt = 0, b:CUnsignedInt = 0

_ = Scanner(string: rString).scanHexInt32(&r)

_ = Scanner(string: gString).scanHexInt32(&g)

_ = Scanner(string: bString).scanHexInt32(&b)

return UIColor(red:CGFloat(r)/255.0, green:CGFloat(g)/255.0, blue:CGFloat(b)/255.0, alpha:CGFloat(1))

//        作者：爱吃馒头的鱼
//        链接：https://www.jianshu.com/p/d1e299fed0fb
//        来源：简书
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    }
    
    
        //十六进制转UIColor
       class func hexToCGColor(hex:String) -> CGColor{
            return hexToUIColor(hex: hex).cgColor
        }
}

