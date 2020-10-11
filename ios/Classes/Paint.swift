//
//  Paint.swift
//  Pods-Runner
//
//  Created by mac on 2020/10/11.
//

import Foundation

public class Paint{
    var textSize:CGFloat?
    var color:UIColor?
    var font:UIFont?
    var align:Align?
    var shadowX:CGFloat?
    var shadowY:CGFloat?
    var shadowBlur:CGFloat?
    var shadowColor:UIColor?
    
    public enum Align:Int{
        case LEFT = 0
        case CENTER = 1
        case RIGHT = 2
    }
    
    
    public init(){
        textSize = UIFont.systemFontSize
        color = UIColor.black
        font = UIFont.systemFont(ofSize: textSize!)
        align = Align.LEFT
        shadowX = 0
        shadowY = 0
        shadowBlur = 0
    }
    
    //获取文字大小
    public func getTextSize() -> CGFloat?{
        
        return textSize
    }
    
    public func setTextSize(size:CGFloat){
        self.textSize = size
        font = UIFont.systemFont(ofSize: size)
    }
    
    //获取颜色
    public func getColor() -> UIColor?{
        return color;
    }
    
    //设置颜色
    public func setColor(_ color:Int){
        let rr:Int = (color>>16)&0xff;
        let gg:Int = (color>>8)&0xff;
        let bb:Int = (color)&0xff
        let aa:Int = (color>>24)&0xff
        let fr:CGFloat = CGFloat(Float(rr)/0xff)
        let fg:CGFloat = CGFloat(Float(gg)/0xff)
        let fb:CGFloat = CGFloat(Float(bb)/0xff)
        let fa:CGFloat = CGFloat(Float(aa)/0xff)
        
        self.color = UIColor(red: fr, green: fg, blue: fb, alpha: fa)
        
    }
    
    public func setUIColor(_ color:UIColor){
        self.color = color;
    }
    
    //获取CGColor
    public func getCGColor() -> CGColor?{
        return color?.cgColor
    }
    
    public func setAlpha(_ alpha:Int){
        var dred: CGFloat = 0
        var dgreen: CGFloat = 0
        var dblue: CGFloat = 0
        var dalpha: CGFloat = 0
        color?.getRed(&dred, green: &dgreen, blue: &dblue, alpha: &dalpha)
//        let multiplier = CGFloat(255.999999)
        color = UIColor(red: dred, green: dgreen, blue: dblue, alpha: CGFloat(Float(alpha)/0xff))
    }
    
    public func getAlpha() -> Int{
        var dred: CGFloat = 0
               var dgreen: CGFloat = 0
               var dblue: CGFloat = 0
               var dalpha: CGFloat = 0
               color?.getRed(&dred, green: &dgreen, blue: &dblue, alpha: &dalpha)
        return Int(dalpha * 0xFF)
    }
    
    public func getFont() -> UIFont? {
        return font;
    }
    
    public func setTextAlign(align:Align){
        self.align = align
    }
    
    public func getTextAlign() -> Align? {
        return self.align
    }
    
    public func setStrokeColor(_ context:CGContext){
        context.setStrokeColor(color!.cgColor)
    }
    
    public func setFillColor(_ context:CGContext){
        context.setFillColor(color!.cgColor)
    }
    
    public func setShadowX(_ x:CGFloat){
        self.shadowX = x;
    }
    
    public func setShadowY(_ y:CGFloat){
        self.shadowY = y
    }
    
    public func setShadowBlur(_ blur:CGFloat){
        self.shadowBlur = blur
    }
    
    //每次绘制之前都初始化context
    public func initContext(_ context:CGContext){
        var dred: CGFloat = 0
        var dgreen: CGFloat = 0
        var dblue: CGFloat = 0
        var dalpha: CGFloat = 0
        color?.getRed(&dred, green: &dgreen, blue: &dblue, alpha: &dalpha)
        setStrokeColor(context)
        setFillColor(context)
        context.setAlpha(dalpha)
        setShadow(context, shadowX: shadowX!, shadowY: shadowY!, blur: shadowBlur!)
    }
    
    public func initGraphics(){
        var context = UIGraphicsGetCurrentContext()
        if context != nil {
            initContext(context!)
        }
        
        else{
            print("flutter: print initGraphics fail\n")
        }
    }
    
    //设置阴影
    func setShadow(_ context:CGContext,shadowX:CGFloat,shadowY:CGFloat,blur:CGFloat){
        context.setShadow(offset: CGSize(width: shadowX, height: shadowY), blur: blur)
    }
    
    public func getAttr() -> [NSAttributedString.Key:Any]{
        //文字样式属性
        let style = NSMutableParagraphStyle ()
        let attr:[NSAttributedString.Key : Any]  = [
            NSAttributedString.Key.font : UIFont.systemFont(ofSize: getTextSize()!),
            NSAttributedString.Key.foregroundColor : getColor()!,
            NSAttributedString.Key.paragraphStyle : style]
        return attr
    }
    
    public func setShadowLayer(blur:CGFloat,shadowX:CGFloat,shadowY:CGFloat,shadowColor:UIColor){
        self.shadowX = shadowX;
        self.shadowY = shadowY
        self.shadowColor = shadowColor
        self.shadowBlur = blur
        
    }
    
    open func toString() -> String{
        return "color:\(color?.hexString ?? "") fontSize:\(textSize)\n"
    }
    
}
