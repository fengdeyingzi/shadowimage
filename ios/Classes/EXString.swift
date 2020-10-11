//
//  EXString.swift
//  shadowimage
//
//  Created by mac on 2020/10/11.
//

import Foundation
import UIKit

// 字符串类扩展
extension String {

    /// 获取字符串绘制的高度
    ///
    /// - parameter font        : 要绘制的字体，将会影响行高等
    /// - parameter width       : 绘制的宽度
    /// - returns : 字符串绘制的最大高度
    func getMaxHeight(font:UIFont, width:CGFloat) -> CGFloat {
        // 获取最大的
        let s = CGSize(width: width, height: CGFloat(MAXFLOAT))
        return getMaxHeight(font: font, rangeRect: s)
    }

    /// 获取字符串绘制的高度
    ///
    /// - parameter font        : 要绘制的字体，将会影响行高等
    /// - parameter rangeRect   : 绘制的最大范围，类似于最大的画布
    /// - returns : 字符串绘制的最大高度
    func getMaxHeight(font:UIFont, rangeRect rect:CGSize) -> CGFloat {
        // draw option
        let opt:NSStringDrawingOptions = [NSStringDrawingOptions.truncatesLastVisibleLine, NSStringDrawingOptions.usesFontLeading, NSStringDrawingOptions.usesLineFragmentOrigin]
        // str
        let str = NSString(string: self)
        // max size
        let maxSize = rect
        // font
        let attr:[NSAttributedString.Key:Any] = [NSAttributedString.Key.font:font]
        // 计算出来的范围
        let resultRect = str.boundingRect(with: maxSize, options: opt, attributes: attr, context: nil)
        // 返回高度
        return CGFloat(ceil(Double(resultRect.height)))
    }

    /**
     获取字符串的单行宽度,
     有可能会超过屏幕限制
     - parameter font : 要绘制的字体
     */
    func getMaxWidth(font:UIFont) -> CGFloat {
        let opt:NSStringDrawingOptions = [NSStringDrawingOptions.truncatesLastVisibleLine, NSStringDrawingOptions.usesFontLeading, NSStringDrawingOptions.usesLineFragmentOrigin]
        // str
        let str = NSString(string: self)
        // max size
        let maxSize = CGSize(width: CGFloat(MAXFLOAT), height: CGFloat(MAXFLOAT))
        // font
        let attr:[NSAttributedString.Key:Any] = [NSAttributedString.Key.font:font]
        // 计算出来的范围
        let resultRect = str.boundingRect(with: maxSize, options: opt, attributes: attr, context: nil)
        // 返回高度
        return CGFloat(ceil(Double(resultRect.width)))
    }
    

    /**
     去除左右的空格和换行符
     */
    func trim() -> String {
        return self.trimmingCharacters(in: CharacterSet.whitespacesAndNewlines)
    }

    /**
     将字符串通过特定的字符串拆分为字符串数组
     - parameter str   : 拆分数组使用的字符串
     - returns : 字符串数组
     */
    func split(string:String) -> [String] {
        return NSString(string: self).components(separatedBy: string)
    }

    /**
     拆分字符串，并获取指定索引的字符串
     - parameter splitStr   : 拆分数组使用的字符串
     - parameter index      : 索引位置
     - parameter defaultStr : 默认字符串
     - returns : 拆分所得字符串
     */
    func strSplitByIndex(splitStr str:String, index:Int, defaultStr:String = "") -> String {
        let a = self.split(string:str)
        if index > a.count - 1  {
            return defaultStr
        }
        return a[index]
    }

    /**
     字符串替换
     - parameter of     : 被替换的字符串
     - parameter with   : 替换使用的字符串
     - returns : 替换后的字符串
     */
    func replace(of: String, with: String) -> String {
        return self.replacingOccurrences(of: of, with: with)
    }

    /**
     判断是否包含，虽然系统提供了方法，这里也只是简单的封装。如果swift再次升级的话，就知道现在做的好处了
     - parameter string : 是否包含的字符串
     - returns : 是否包含
     */
    func has(string:String) -> Bool {
        return self.contains(string)
    }

    /**
     字符出现的位置
     - parameter string : 字符串
     - returns : 字符串出现的位置
     */
    func indexOf(_ str:String) -> Int {
        
        var i = -1
        if let r = range(of: str) {
            if !r.isEmpty {
                i = self.distance(from: self.startIndex, to: r.lowerBound)
            }
        }
        return i
    }

    /**
     这个太经典了,获取指定位置和大小的字符串
     - parameter start  : 起始位置
     - parameter length : 长度
     - returns : 字符串
     */
    func subString(start:Int, length:Int = -1) -> String {
        var len = length
        if len == -1 {
            len = self.count - start
        }
        let st = self.index(self.startIndex, offsetBy:start)
        let en = self.index(st, offsetBy:len)
        let range = st ..< en
        return self.substring(with:range)
    }

    /// 字符串的长度
    var length:Int {
        get {
            return self.count
        }
    }

    /// 将16进制字符串转为Int
    var hexInt:Int {
        get {
            return Int(self, radix: 16) ?? 0
        }
    }
    
    
      /**
         Get the height with the string.
     
         - parameter attributes: The string attributes.
         - parameter fixedWidth: The fixed width.
     
         - returns: The height.
         */
        func heightWithStringAttributes(attributes : [NSAttributedString.Key : NSObject], fixedWidth : CGFloat) -> CGFloat {
     
//            guard self.count >  && fixedWidth >  else {
//
//                return
//            }
     
            let size = CGSize(width:fixedWidth, height:CGFloat.greatestFiniteMagnitude)
            let text = self as NSString
            let rect = text.boundingRect(with: size, options:.usesLineFragmentOrigin, attributes: attributes, context:nil)
     
            return rect.size.height
        }
     
        /**
         Get the height with font.
     
         - parameter font:       The font.
         - parameter fixedWidth: The fixed width.
     
         - returns: The height.
         */
    func heightWithFont(font : UIFont = UIFont.systemFont(ofSize: UIFont.systemFontSize), fixedWidth : CGFloat) -> CGFloat {
     
//            guard self.characters.count >  && fixedWidth >  else {
//
//                return
//            }
     
            let size = CGSize(width:fixedWidth, height:CGFloat.greatestFiniteMagnitude)
            let text = self as NSString
            let rect = text.boundingRect(with: size, options:.usesLineFragmentOrigin, attributes: [NSAttributedString.Key.font : font], context:nil)
     
            return rect.size.height
        }
     
        /**
         Get the width with the string.
     
         - parameter attributes: The string attributes.
     
         - returns: The width.
         */
        func widthWithStringAttributes(attributes : [NSAttributedString.Key : NSObject]) -> CGFloat {
     
          
     
//            let size = CGSizeMake(CGFloat.max, )
            let size = CGSize(width:CGFloat.greatestFiniteMagnitude,height:CGFloat.greatestFiniteMagnitude)
            let text = self as NSString
            let rect = text.boundingRect(with: size, options:.usesLineFragmentOrigin, attributes: attributes, context:nil)
     
            return rect.size.width
        }
     
        /**
         Get the width with the string.
     
         - parameter font: The font.
     
         - returns: The string's width.
         */
    func widthWithFont(font : UIFont = UIFont.systemFont(ofSize: UIFont.systemFontSize)) -> CGFloat {
     
//            guard self.characters.count >  else {
//
//                return
//            }
     
            let size = CGSize(width:CGFloat.greatestFiniteMagnitude,height:CGFloat.greatestFiniteMagnitude)
            let text = self as NSString
            let rect = text.boundingRect(with: size, options:.usesLineFragmentOrigin, attributes: [NSAttributedString.Key.font : font], context:nil)
     
            return rect.size.width
        }
    

}
