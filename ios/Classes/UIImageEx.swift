//
//  UIImageEx.swift
//  Pods-Runner
//
//  Created by mac on 2020/10/9.
//

import Foundation

extension UIImage {
    /// 图片加水印
    ///
    /// - Parameters:
    ///   - text: 水印完整文字
    ///   - textColor: 文字颜色
    ///   - textFont: 文字大小
    ///   - suffixText: 尾缀文字(如果是nil可以不传)
    ///   - suffixFont: 尾缀文字大小(如果是nil可以不传)
    ///   - suffixColor: 尾缀文字颜色(如果是nil可以不传)
    /// - Returns: 水印图片
//    func drawTextInImage(text: String, textColor: UIColor, textFont: UIFont,suffixText: String?, suffixFont: UIFont?, suffixColor: UIColor?) -> UIImage {
//        // 开启和原图一样大小的上下文（保证图片不模糊的方法）
//        UIGraphicsBeginImageContextWithOptions(self.size, false, self.scale)
//
//        // 图形重绘
//        self.draw(in: CGRect(x: 0, y: 0, width: self.size.width, height: self.size.height))
//        
//        var suffixAttr: [NSAttributedStringKey: Any] = [NSAttributedStringKey.foregroundColor:textColor, NSAttributedStringKey.font:textFont]
//        let attrS = NSMutableAttributedString(string: text, attributes: suffixAttr)
//
//        // 添加后缀的属性字符串
//        if let suffixStr = suffixText {
//            let range = NSRange(location: text.count - suffixStr.count, length: suffixStr.count)
//            if suffixFont != nil {
//                suffixAttr[NSAttributedStringKey.font] = suffixFont
//            }
//
//            if suffixColor != nil {
//                suffixAttr[NSAttributedStringKey.foregroundColor] = suffixColor
//            }
//            attrS.addAttributes(suffixAttr, range: range)
//        }
//
//        // 文字属性
//        let size =  attrS.size()
//        let x = (self.size.width - size.width) / 2
//        let y = (self.size.height - size.height) / 2
//
//        // 绘制文字
//        attrS.draw(in: CGRect(x: x, y: y, width: size.width, height: size.height))
//        // 从当前上下文获取图片
//        let image = UIGraphicsGetImageFromCurrentImageContext()
//        //关闭上下文
//        UIGraphicsEndImageContext()
//
//        return image!
//    }
}

