//
//  ImageUtil.swift
//  Pods-Runner
//
//  Created by mac on 2020/10/9.
//

import Foundation
import UIKit
//import shadowimage/Paint

class ImageUtil{
    
     /**
        * Constant indicating that no gravity has been set
        **/
     static var NO_GRAVITY:Int = 0x0000;

       /**
        * Raw bit indicating the gravity for an axis has been specified.
        */
     static var  AXIS_SPECIFIED:Int = 0x0001;

       /**
        * Raw bit controlling how the left/top edge is placed.
        */
    static var AXIS_PULL_BEFORE:Int = 0x0002;
       /**
        * Raw bit controlling how the right/bottom edge is placed.
        */
    static var AXIS_PULL_AFTER:Int = 0x0004;
       /**
        * Raw bit controlling whether the right/bottom edge is clipped to its
        * container, based on the gravity direction being applied.
        */
      static var AXIS_CLIP:Int = 0x0008;
       /**
        * Bits defining the horizontal axis.
        */
      static var AXIS_X_SHIFT:Int = 0;
       /**
        * Bits defining the vertical axis.
        */
         static var  AXIS_Y_SHIFT = 4;
       /**
        * Push object to the top of its container, not changing its size.
        */
         static var  TOP = (AXIS_PULL_BEFORE | AXIS_SPECIFIED) << AXIS_Y_SHIFT;
       /**
        * Push object to the bottom of its container, not changing its size.
        */
        static var  BOTTOM = (AXIS_PULL_AFTER | AXIS_SPECIFIED) << AXIS_Y_SHIFT;
       /**
        * Push object to the left of its container, not changing its size.
        */
        static var  LEFT = (AXIS_PULL_BEFORE | AXIS_SPECIFIED) << AXIS_X_SHIFT;
       /**
        * Push object to the right of its container, not changing its size.
        */
        static var  RIGHT = (AXIS_PULL_AFTER | AXIS_SPECIFIED) << AXIS_X_SHIFT;
        static var  CENTER_VERTICAL = AXIS_SPECIFIED << AXIS_Y_SHIFT;
        static var  CENTER_HORIZONTAL = AXIS_SPECIFIED << AXIS_X_SHIFT;
        static var  CENTER = CENTER_VERTICAL | CENTER_HORIZONTAL;

    public static func N2J_getCharW(c:String,paint:Paint) -> CGFloat{
        /*
        let opt:NSStringDrawingOptions = [NSStringDrawingOptions.truncatesLastVisibleLine, NSStringDrawingOptions.usesFontLeading, NSStringDrawingOptions.usesLineFragmentOrigin]
        let font = paint.getFont()
        // str
        let str = NSString(string: c)
        // max size
        let maxSize = CGSize(width: CGFloat(MAXFLOAT), height: CGFloat(MAXFLOAT))
        // font
//        let attributes = [NSAttributedString.Key.font:font]
        let attr:[NSAttributedString.Key : Any] = paint.getAttr()
        // 计算出来的范围
        let resultRect = str.boundingRect(with: maxSize, options: opt, attributes: attr as [NSAttributedString.Key : Any], context: nil)
        // 返回高度
        return CGFloat(ceil(Double(resultRect.width)))
        */
        var attr =  [NSAttributedString.Key.font : UIFont.systemFont(ofSize: UIFont.systemFontSize)]
        var def_width = c.widthWithStringAttributes(attributes: attr)
        var paint_width = c.widthWithStringAttributes(attributes: paint.getAttrObject())
        print("paint_width \(paint_width) textSize \(paint.getTextSize())\n")
        return paint_width
    }
    

//    //获得字符串的高度
//    -(float) heightForString:(NSString *)value fontSize:(float)fontSize andWidth:(float)width
//    {
//        CGSize sizeToFit = [value sizeWithFont:[UIFont systemFontOfSize:fontSize] constrainedToSize:CGSizeMake(width, CGFLOAT_MAX) lineBreakMode:NSLineBreakByCharWrapping];//此处的换行类型（lineBreakMode）可根据自己的实际情况进行设置
//        return sizeToFit.height;
//    }
    
    //获取在指定的宽度下，文字显示的行数
    public static func getTextLines( text:String,  paint:Paint,  width:CGFloat) ->Int {
        var line:Int = 1;
        var ix:CGFloat = 0;
        var font_w:CGFloat = 0
        for (i, c) in text.enumerated() {
//            print("\(index) --- \(value)")
            font_w = N2J_getCharW(c: String(c), paint: paint);
            if (c == "\n") {
                           ix = 0;
                           line = line+1;
                           continue;
                       }
                       if (ix + font_w < width) {
                           ix += font_w;
                       } else {
                           ix = 0;
                           line = line+1;
                       }
        }
        
//        for i in 0 ..< text.count {
//            var c = text[i];
//            float font_w = N2J_getCharW(c, paint);
//            if (c == '\n') {
//                ix = 0;
//                line++;
//                continue;
//            }
//            if (ix + font_w < width) {
//                ix += font_w;
//            } else {
//                ix = 0;
//                line++;
//            }
//        }
        return line;
    }

    //获取在指定的宽度下，每一行的文字
    public static func getTexts( text:String,  paint:Paint,  width:CGFloat) -> [String]{
        var list_text:[String] = []
        var line:Int = 1;
        var ix:CGFloat = 0;
        var buffer:String? = ""
        var font_w:CGFloat = 0
         for (i, c) in text.enumerated() {
            font_w = N2J_getCharW(c: String(c), paint: paint)
            if c == "\n" {
                ix = 0
                line = line+1
                list_text.append(buffer!)
                buffer = ""
            }
            if ix + font_w < width {
                ix += font_w
                buffer?.append(c)
            }
            else {
                ix = 0
                line = line + 1
                list_text.append(buffer!)
                buffer = ""
            }
        }
        if (buffer!.count > 0) {
                  list_text.append(buffer!);
        }
//        if buffer.count > 0 {
//            list_text.append(buffer)
//        }
//
//        for (int i = 0; i < text.length(); i++) {
//            char c = text.charAt(i);
//            float font_w = N2J_getCharW(c, paint);
//            if (c == '\n') {
//                ix = 0;
//                line++;
//                list_text.add(buffer.toString());
//                buffer = new StringBuffer();
//                continue;
//            }
//            if (ix + font_w < width) {
//                ix += font_w;
//                buffer.append(c);
//            } else {
//                ix = 0;
//                line++;
//                list_text.add(buffer.toString());
//                buffer = new StringBuffer();
//            }
//        }
//        if (buffer.length() > 0) {
//            list_text.add(buffer.toString());
//        }
        return list_text;
    }
    
    //TODO:
    public static func N2J_drawText( text c:String,  x:CGFloat,  y:CGFloat,  paint:Paint) -> Void{
//           canvas.drawText(c, x, y + paint.getTextSize() * 11 / 12, paint);
            //    将文字绘制到指定的范围内, 如果一行装不下会自动换行, 当文字超出范围后就不显示
        var attr =  [NSAttributedString.Key.font:UIFont.systemFont(ofSize: UIFont.systemFontSize)]
        let width = N2J_getCharW(c: c, paint: paint)
        paint.initGraphics()
//        c.draw(in: CGRect(x:x, y:y, width:width, height:CGFloat(paint.getTextSize()!+100)), withAttributes:paint.getAttrObject());
//         c.draw(in: CGRect(x:x, y:y, width:300, height:300), withAttributes:attr);
        var nstext = NSString(string: c)
        nstext.draw(at: CGPoint(x: x, y: y), withAttributes:paint.getAttrObject())
        print("flutter: N2J_drawText(\(c),\(x),\(y))\n")
       }
    

    //在canvas矩形区域内绘制文字
    public static func N2J_drawTextEx(  text:String, rect:CGRect, paint:Paint, gravity:Int) -> Void{
        print("dtawTextEx \(text) \(rect)\n")
//        Log.i(TAG, String.format("N2J_drawTextEx: %d,%d,%d,%d,%b", rect.left, rect.top, rect.right, rect.bottom, gravity));
        var line_height:CGFloat = CGFloat(paint.getTextSize()! * 18 / 16);
        var i = 0
        var ix:CGFloat = rect.origin.x
        var iy:CGFloat = rect.origin.y
        var line:Int = getTextLines(text: text, paint: paint, width: rect.size.width)
        var list_text:[String] = getTexts(text: text, paint: paint, width: rect.size.width)
        var lineHeight:CGFloat = CGFloat(paint.getTextSize()!) * CGFloat(1.5)
        if ( gravity & TOP ) == TOP {
            iy = rect.origin.y
        }
        else if (gravity & BOTTOM) == BOTTOM {
            iy = rect.origin.y + rect.size.height - lineHeight * CGFloat(line)
        }
        else if (gravity & CENTER_VERTICAL) == CENTER_VERTICAL {
            iy = (rect.origin.y + rect.size.height/2) - lineHeight * CGFloat(line)/2
        }
        
        if ((gravity & LEFT) == LEFT) {
                   ix = 0;
        } else if ((gravity & RIGHT) == RIGHT) {
            ix = rect.size.width + rect.origin.x
            paint.setTextAlign(align: Paint.Align.RIGHT);
        } else if ((gravity & CENTER_HORIZONTAL) == CENTER_HORIZONTAL) {
            paint.setTextAlign(align: Paint.Align.CENTER);
            ix = rect.origin.x + rect.size.width/2
        }
        
        for item in list_text {
            
            var text_width = N2J_getCharW(c: item, paint: paint)
            switch(paint.getTextAlign()!){
            case Paint.Align.RIGHT:
                if(text_width == 0){
                    text_width = 200
                }
                ix = ix - text_width
                print("flutter: paint right \(text_width)\n")
                break
            case .LEFT:
                break
                
            case .CENTER:
                ix = ix - text_width/2
                print("flutter: paint center \(text_width)\n")
            }
            print("text_width \(text_width)\n")
            N2J_drawText(text: item, x:ix, y:iy, paint:paint);
            iy += lineHeight;
        }
        
//        float line_height = paint.getTextSize() * 18 / 16;
//        int i = 0;
//        float ix = rect.left;
//        float iy = rect.top;
//        int line = getTextLines(text, paint, rect.width());
//        ArrayList<String> list_text = getTexts(text, paint, rect.width());
//        float lineHeight = (float) (paint.getTextSize() * 1.5);
//        if ((gravity & TOP) == TOP) {
//            iy = rect.top;
//        } else if ((gravity & BOTTOM) == BOTTOM) {
//            iy = rect.bottom - lineHeight * line;
//        } else if ((gravity & CENTER_VERTICAL) == CENTER_VERTICAL) {
//            iy = rect.centerY() - lineHeight * line / 2;
//        }
//
//        if ((gravity & LEFT) == LEFT) {
//            ix = 0;
//        } else if ((gravity & RIGHT) == RIGHT) {
//            ix = rect.right;
//            paint.setTextAlign(Paint.Align.RIGHT);
//        } else if ((gravity & CENTER_HORIZONTAL) == CENTER_HORIZONTAL) {
//            paint.setTextAlign(Paint.Align.CENTER);
//            ix = rect.centerX();
//        }
//
//
//        for (i = 0; i < list_text.size(); i++) {
//            N2J_drawText(canvas, list_text.get(i), ix, iy, paint);
//            iy += lineHeight;
//        }
    }


    
    /*
     IOS Swift 3.0 NSData与String相互转化

     var testString = "This is a test string"
     var nsdata = testString.data(using: String.Encoding.utf8)//NSData 类型
     var strdata = String(data: nsdata!, encoding: String.Encoding.utf8) as String!//String

     */
    //通过json生成图片 参数:json 素材目录
    public class func jsonBitmap(_ json:String, dir:String)->UIImage?{
        var nsdata:Data? = json.data(using: String.Encoding.utf8)//NSData 类型
        var file_dir = dir
//        var img_dst:UIImage?
//        let object = JSONSerialization.JSONObjectWithData(nsdata, options: JSONSerialization.ReadingOptions.MutableContainers, error: nil) as NSDictionary
        do{
            let object = try JSONSerialization.jsonObject(with: nsdata!, options: JSONSerialization.ReadingOptions.mutableLeaves) as! Dictionary<String, Any>
            let width = object["width"] as! Int
            let height:Int = object["height"] as! Int
            // 1.3.开始绘制图片的大小
                 UIGraphicsBeginImageContext(CGSize(width: width, height: height))
            var context = UIGraphicsGetCurrentContext()
           
                 
            
//            let cgImage = CGImage(width: width, height: height, bitsPerComponent: 8, bitsPerPixel: 8, bytesPerRow: 8, space: CGColorSpace., bitmapInfo: <#T##CGBitmapInfo#>, provider: <#T##CGDataProvider#>, decode: <#T##UnsafePointer<CGFloat>?#>, shouldInterpolate: <#T##Bool#>, intent: <#T##CGColorRenderingIntent#>)
            let bitmap:UIImage = UIGraphicsGetImageFromCurrentImageContext()!
            let array_text:Array<Dictionary<String,Any>> = object["list_text"] as! Array<Dictionary<String,Any>>
            let array_img:Array<Dictionary<String,Any>> = object["list_img"] as! Array<Dictionary<String,Any>>
            let array_effects:Array<Dictionary<String,Any>> = object["list_effects"] as! Array<Dictionary<String,Any>>
            //画文字
            for item in array_text {
                let text = item["text"] as! String
                var fontSize = item["fontSize"] as! Int
                var paint_text = Paint();
                var paint_background = Paint();
                print("flutter: 画文字\(text)\n")
//                var context:CGContext = CGContext.ini
                if(item["font"] != nil){
                    
                }
                
                if(item["shadowColor"] != nil){
                    var shadowX:CGFloat = CGFloat(item["shadowX"] as! Double)
                    var shadowY = CGFloat(item["shadowY"] as! Double)
                    var shadowColor = ColorUtil.hexToUIColor(hex: item["shadowColor"] as! String)
                    var radius = CGFloat(item["radius"] as! Double)
                    paint_text.setShadowLayer(blur: radius, shadowX: shadowX, shadowY: shadowY, shadowColor: shadowColor)
                }
                
                if(item["textColor"] != nil){
                    var textColor:UIColor = ColorUtil.hexToUIColor(hex:item["textColor"] as! String)
                    paint_text.setUIColor(textColor)
                }
                if(item["type"] != nil){
                    var type = item["type"] as! String
                    if(type == "underline"){
                        
                    }
                    else if(type == "delete"){
                        
                    }
                    else if(type == "bold"){
                        
                    }
                }
                
                if(item["gravity"] != nil){
                    
                }
                var gravity_value = 0;
                var gravity:String = item["gravity"] as! String
                var x = item["x"] as! Int
                var y = item["y"] as! Int
                let w = item["w"] as! Int
                let h = item["h"] as! Int
                if(x<0){
                               x = Int(Int(bitmap.getWidth()) + x);
                           }
                           if(y<0){
                               y = Int(Int(bitmap.getHeight()) + y)
                           }
                var rect_draw:CGRect = CGRect(x: x, y: y, width: w, height: h);
                           if (gravity.indexOf("left") >= 0) {
                               gravity_value |= LEFT;
                           }
                           if (gravity.indexOf("top") >= 0) {
                               gravity_value |= TOP;
                           }
                           if (gravity.indexOf("right") >= 0) {
                               gravity_value |= RIGHT;
                           }
                           if (gravity.indexOf("bottom") >= 0) {
                               gravity_value |= BOTTOM;
                           }
                           if (gravity.indexOf("center") >= 0) {
                               gravity_value |= CENTER;
                           }
                if (item["backgroundColor"] != nil) {
                    var backgroundColor:String = item["backgroundColor"] as! String
//                               paint_background.setColor(XmlUtil.getColor(backgroundColor))
                    paint_background.setUIColor(ColorUtil.hexToUIColor(hex: backgroundColor))
                    print("paint_background \(backgroundColor) \(paint_background.toString())\n")
                    paint_background.initGraphics()
//                               canvas.drawRect(rect_draw, paint_background);
//                    bitmap.draw(in: rect_draw)
                    //进行矩形的绘制 和填充
                    context!.fill(rect_draw)
                          //描边的矩形 在绘制之前都可以进行填充
//                          CGContextStrokeRect(context, CGRect(x: 100, y: 100, width: 20, height: 20))
//                               Log.i(TAG, "backgroundColor:" + backgroundColor);
                           }
                N2J_drawTextEx( text: text, rect: rect_draw, paint: paint_text, gravity: gravity_value)
//                    N2J_drawTextEx(canvas, text, rect_draw, paint_text, gravity_value);
            }
            
            //画图片
            for obj_img_item in array_img {
                var img_path = obj_img_item["path"] as! String
                var paint_img = Paint()
                var bitmap_img = UIImage.init(contentsOfFile: file_dir+"/"+img_path)
                var ix:CGFloat = 0
                var iy:CGFloat = 0
                var x = obj_img_item["x"] as! Int
                var y = obj_img_item["y"] as! Int
                ix = CGFloat(x)
                iy = CGFloat(y)
                print("flutter: 画图片\(img_path)\n")
                if let layout_gravity = obj_img_item["layout_gravity"] as? String {
                    var layout_gravity_value:Int = 0;
                    if (layout_gravity.indexOf("left") >= 0) {
                    layout_gravity_value |= LEFT;
                    }
                    if (layout_gravity.indexOf("top") >= 0) {
                        layout_gravity_value |= TOP;
                    }
                    if (layout_gravity.indexOf("right") >= 0) {
                    layout_gravity_value |= RIGHT;
                        ix -= bitmap_img!.getWidth();
                    }
                    if (layout_gravity.indexOf("bottom") >= 0) {
                        layout_gravity_value |= BOTTOM;
                        iy -= bitmap_img!.getHeight();
                    }
                }
//                var layout_gravity = obj_img_item["layout_gravity"] as! String
                if let alpha = obj_img_item["alpha"] as? Int {
                    paint_img.setAlpha(alpha)

                }
                if let tint = obj_img_item["tint"] as? String {
                                         
                }
                drawBitmap( bitmap_img!, x: ix,y: iy,paint: paint_img)
                
//                 JSONObject obj_img_item = array_img.getJSONObject(i);
//                            String img_path = obj_img_item.getString("path");
//                            Paint paint_img = new Paint();
//                            paint_img.setAntiAlias(true);
//                            Bitmap bitmap_img = BitmapFactory.decodeFile(new File(file_dir,img_path).getPath());
//                            int ix = 0;
//                            int iy = 0;
//                            int x = obj_img_item.getInt("x");
//                            int y = obj_img_item.getInt("y");
//                            ix = x;
//                            iy = y;
//                            if (obj_img_item.has("layout_gravity")) {
//                                String layout_gravity = obj_img_item.getString("layout_gravity");
//                                int layout_gravity_value = 0;
//                                if (layout_gravity.indexOf("left") >= 0) {
//                                    layout_gravity_value |= LEFT;
//                                }
//                                if (layout_gravity.indexOf("top") >= 0) {
//                                    layout_gravity_value |= TOP;
//                                }
//                                if (layout_gravity.indexOf("right") >= 0) {
//                                    layout_gravity_value |= RIGHT;
//                                    ix -= bitmap_img.getWidth();
//                                }
//                                if (layout_gravity.indexOf("bottom") >= 0) {
//                                    layout_gravity_value |= BOTTOM;
//                                    iy -= bitmap_img.getHeight();
//                                }
        
//                            }
//                            if (obj_img_item.has("alpha")) {
//                                int alpha = obj_img_item.getInt("alpha");
//                                paint_img.setAlpha(alpha);
//                            }
//                            if (obj_img_item.has("tint")) {
//                                String tint = obj_img_item.getString("tint");
//                            }

//                            canvas.drawBitmap(bitmap_img, ix, iy, paint_img);
               
                
            }
            
            //画特效
            for item in array_effects {
                
            }
            
            
        } catch{
            print("json转换成map失败%s",error)
            
        }
        // 1.6.获取已经绘制好的
        var img_dst = UIGraphicsGetImageFromCurrentImageContext()!
        // 1.7.结束绘制
        UIGraphicsEndImageContext()
       return img_dst
               
//           File file_dir = new File(dir);
//                JSONObject object = new JSONObject(jsonStr);
//                String path = object.optString("path");
//                int width = object.getInt("width");
//                int height = object.getInt("height");
//                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//                Canvas canvas = new Canvas(bitmap);
//                JSONArray array_text = object.getJSONArray("list_text");
//                JSONArray array_img = object.getJSONArray("list_img");
//                JSONArray array_effects = object.getJSONArray("list_effects");
//                //先进行特效处理
//
//                //画文字
//                for (int i = 0; i < array_text.length(); i++) {
//                    Paint paint_text = new Paint();
//                    paint_text.setAntiAlias(true);
//                    Paint paint_background = new Paint();
//                    JSONObject obj_text_item = array_text.getJSONObject(i);
//                    String text = obj_text_item.getString("text");
//                    int fontSize = obj_text_item.getInt("fontSize");
//                    paint_text.setTextSize(DisplayUtil.sp2px(context, fontSize));
//                    if (obj_text_item.has("font")) {
//                        String font = obj_text_item.getString("font");
//                        paint_text.setTypeface(Typeface.createFromFile(font));
//                    }
//
//
//                    if (obj_text_item.has("shadowColor")) {
//                        double shadowX = obj_text_item.getDouble("shadowX");
//                        double shadowY = obj_text_item.getDouble("shadowY");
//                        double radius = obj_text_item.getDouble("radius");
//                        String shadowColor = obj_text_item.getString("shadowColor");
//                        paint_text.setShadowLayer((float) radius, (float) shadowX, (float) shadowY, XmlUtil.getColor(shadowColor));
//                    }
//
//
//                    if (obj_text_item.has("type")) {
//                        String type = obj_text_item.getString("type");
//                        if (type.equals("underline")) {
//                            paint_text.setUnderlineText(true);
//                        } else if (type.equals("delete")) {
//                            paint_text.setStrikeThruText(true);
//                        } else if (type.equals("bold")) {
//                            paint_text.setSubpixelText(true);
//                        }
//                    }
//
//                    String textColor = obj_text_item.getString("textColor");
//                    paint_text.setColor(XmlUtil.getColor(textColor));
//                    if (obj_text_item.has("gradient")) {
//                        JSONArray array_gradient = obj_text_item.getJSONArray("gradient");
//                        int gradient_angle = obj_text_item.getInt("gradient_angle");
//                    }
//
//
//                    String gravity = obj_text_item.getString("gravity");
//                    int gravity_value = 0;
//                    int x = obj_text_item.getInt("x");
//                    int y = obj_text_item.getInt("y");
//                    int w = obj_text_item.getInt("w");
//                    int h = obj_text_item.getInt("h");
//                    if(x<0){
//                        x = bitmap.getWidth()+x;
//                    }
//                    if(y<0){
//                        y = bitmap.getHeight()+y;
//                    }
//                    Rect rect_draw = new Rect(x, y, x + w, y + h);
//                    if (gravity.indexOf("left") >= 0) {
//                        gravity_value |= LEFT;
//                    }
//                    if (gravity.indexOf("top") >= 0) {
//                        gravity_value |= TOP;
//                    }
//                    if (gravity.indexOf("right") >= 0) {
//                        gravity_value |= RIGHT;
//                    }
//                    if (gravity.indexOf("bottom") >= 0) {
//                        gravity_value |= BOTTOM;
//                    }
//                    if (gravity.indexOf("center") >= 0) {
//                        gravity_value |= CENTER;
//                    }
//                    if (obj_text_item.has("backgroundColor")) {
//                        String backgroundColor = obj_text_item.getString("backgroundColor");
//                        paint_background.setColor(XmlUtil.getColor(backgroundColor));
//                        canvas.drawRect(rect_draw, paint_background);
//                        Log.i(TAG, "backgroundColor:" + backgroundColor);
//                    }
//
//                    N2J_drawTextEx(canvas, text, rect_draw, paint_text, gravity_value);
//
//                }
//                //再画图片
//                for (int i = 0; i < array_img.length(); i++) {
//                    JSONObject obj_img_item = array_img.getJSONObject(i);
//                    String img_path = obj_img_item.getString("path");
//                    Paint paint_img = new Paint();
//                    paint_img.setAntiAlias(true);
//                    Bitmap bitmap_img = BitmapFactory.decodeFile(new File(file_dir,img_path).getPath());
//                    int ix = 0;
//                    int iy = 0;
//                    int x = obj_img_item.getInt("x");
//                    int y = obj_img_item.getInt("y");
//                    ix = x;
//                    iy = y;
//                    if (obj_img_item.has("layout_gravity")) {
//                        String layout_gravity = obj_img_item.getString("layout_gravity");
//                        int layout_gravity_value = 0;
//                        if (layout_gravity.indexOf("left") >= 0) {
//                            layout_gravity_value |= LEFT;
//                        }
//                        if (layout_gravity.indexOf("top") >= 0) {
//                            layout_gravity_value |= TOP;
//                        }
//                        if (layout_gravity.indexOf("right") >= 0) {
//                            layout_gravity_value |= RIGHT;
//                            ix -= bitmap_img.getWidth();
//                        }
//                        if (layout_gravity.indexOf("bottom") >= 0) {
//                            layout_gravity_value |= BOTTOM;
//                            iy -= bitmap_img.getHeight();
//                        }
//
//                    }
//                    if (obj_img_item.has("alpha")) {
//                        int alpha = obj_img_item.getInt("alpha");
//                        paint_img.setAlpha(alpha);
//                    }
//                    if (obj_img_item.has("tint")) {
//                        String tint = obj_img_item.getString("tint");
//                    }
//
//                    canvas.drawBitmap(bitmap_img, ix, iy, paint_img);
//                }
//
//                return bitmap;
    }
    
    
    //保存图片到png
    public class func saveImageToPNG(_ image:UIImage,path:String){
        // 保存图片到指定的路径
        var data:Data = image.pngData()!
        var url = URL(fileURLWithPath: path)
        do{
            try data.write(to: url);
        }catch{
            print(error)
        }
       
//           NSData *data = UIImagePNGRepresentation(image)
//           [data writeToFile:@"/Users/userName/Desktop/myShaoNv.png" atomically:YES];

    }
    
    //保存图片为jpg
    public class func saveImageToJPG(_ image:UIImage,load:Int, path:String){
        var data:Data = image.jpegData(compressionQuality: CGFloat(load))!
        var url = URL(fileURLWithPath: path)
        do{
            try data.write(to: url);
        }catch{
            print(error)
        }
    }
    
    //将图片缩放成指定大小
    public class func zoomImage(_ bgimage:UIImage,  newWidth:Int,
                         newHeight:Int) -> UIImage{
        var reSize:CGSize = CGSize()
        reSize.width = CGFloat(newWidth);
        reSize.height = CGFloat(newHeight);
        UIGraphicsBeginImageContextWithOptions (reSize, false , 1);
        bgimage.draw( in: CGRect(x: 0, y: 0, width: reSize.width, height: reSize.height));
        let  reSizeImage: UIImage  =  UIGraphicsGetImageFromCurrentImageContext () ?? bgimage;
        UIGraphicsEndImageContext ();
        return  reSizeImage;
    }
    /*
     + (UIImage *)image:(UIImage *)image rotation:(UIImageOrientation)orientation
     {
         long double rotate = 0.0;
         CGRect rect;
         float translateX = 0;
         float translateY = 0;
         float scaleX = 1.0;
         float scaleY = 1.0;
         
         switch (orientation) {
             case UIImageOrientationLeft:
                 rotate = M_PI_2;
                 rect = CGRectMake(0, 0, image.size.height, image.size.width);
                 translateX = 0;
                 translateY = -rect.size.width;
                 scaleY = rect.size.width/rect.size.height;
                 scaleX = rect.size.height/rect.size.width;
                 break;
             case UIImageOrientationRight:
                 rotate = 33 * M_PI_2;
                 rect = CGRectMake(0, 0, image.size.height, image.size.width);
                 translateX = -rect.size.height;
                 translateY = 0;
                 scaleY = rect.size.width/rect.size.height;
                 scaleX = rect.size.height/rect.size.width;
                 break;
             case UIImageOrientationDown:
                 rotate = M_PI;
                 rect = CGRectMake(0, 0, image.size.width, image.size.height);
                 translateX = -rect.size.width;
                 translateY = -rect.size.height;
                 break;
             default:
                 rotate = 0.0;
                 rect = CGRectMake(0, 0, image.size.width, image.size.height);
                 translateX = 0;
                 translateY = 0;
                 break;
         }
         
         UIGraphicsBeginImageContext(rect.size);
         CGContextRef context = UIGraphicsGetCurrentContext();
         //做CTM变换
         CGContextTranslateCTM(context, 0.0, rect.size.height);
         CGContextScaleCTM(context, 1.0, -1.0);
         CGContextRotateCTM(context, rotate);
         CGContextTranslateCTM(context, translateX, translateY);
         
         CGContextScaleCTM(context, scaleX, scaleY);
         //绘制图片
         CGContextDrawImage(context, CGRectMake(0, 0, rect.size.width, rect.size.height), image.CGImage);
         
         UIImage *newPic = UIGraphicsGetImageFromCurrentImageContext();
         
         return newPic;
     }
     */
    //旋转图片到指定角度
    public class func rotateBimap(_ image:UIImage, rotate:Int) -> UIImage{
        var rotate = 0.0;
        var rect:CGRect;
        var translateX:CGFloat = 0;
        var translateY:CGFloat = 0;
        var scaleX:CGFloat = 1.0;
        var scaleY:CGFloat = 1.0;
        var orientation:UIImage.Orientation = UIImage.Orientation.right
        var swip = false;
        if(rotate==90 || rotate==270){
            swip = true
        }
        
        switch (orientation) {
        case UIImage.Orientation.left:
                rotate = M_PI_2;
                rect = CGRect.init(x:0, y:0, width:image.size.height, height:image.size.width);
                translateX = 0;
                translateY = (-rect.size.width);
                scaleY = (rect.size.width/rect.size.height);
                scaleX = (rect.size.height/rect.size.width);
                break;
        case UIImage.Orientation.right:
                rotate = 33 * M_PI_2;
                rect = CGRect(x:0, y:0, width:image.size.height, height:image.size.width);
                translateX = (-rect.size.height);
                translateY = 0;
                scaleY = rect.size.width/rect.size.height;
                scaleX = rect.size.height/rect.size.width;
                break;
        case UIImage.Orientation.down:
                rotate = M_PI;
                rect = CGRect(x:0, y:0, width:image.size.width, height:image.size.height);
                translateX = -rect.size.width;
                translateY = -rect.size.height;
                break;
            default:
                rotate = 0.0;
                rect = CGRect(x:0, y:0, width:image.size.width, height:image.size.height);
                translateX = 0;
                translateY = 0;
                break;
        }
        
        UIGraphicsBeginImageContext(rect.size);
        var context:CGContext = UIGraphicsGetCurrentContext() as! CGContext;
        //做CTM变换
        context.translateBy(x: 0.0, y: rect.size.height);
        context.scaleBy(x: 1.0, y: -1.0);
        context.rotate(by: CGFloat(rotate));
        context.translateBy(x: translateX, y: translateY);
        
        context.scaleBy(x: scaleX, y: scaleY);
        //绘制图片
//        CGContext.draw(context)
        if !swip {
            
            context.draw(image.cgImage!, in: CGRect(x:0, y:0, width:rect.size.width, height:rect.size.height))
        }
        
        else{
            context.draw(image.cgImage!, in: CGRect(x:0, y:0, width:rect.size.height, height:rect.size.width))

        }
        
//        CGContextDrawImage(context, CGRect(x:0, y:0, width:rect.size.width, height:rect.size.height), image.cgImage);
        
        let newPic:UIImage = UIGraphicsGetImageFromCurrentImageContext() ?? image;
        
        return newPic;
    }
    
    public class func rotateImage(_ image: UIImage, withAngle angle: Double) -> UIImage? {
        if angle.truncatingRemainder(dividingBy: 360) == 0 { return image }
        
        let imageRect = CGRect(origin: .zero, size: image.size)
        let radian = CGFloat(angle / 180 * Double.pi)
        let rotatedTransform = CGAffineTransform.identity.rotated(by: radian)
        var rotatedRect = imageRect.applying(rotatedTransform)
        rotatedRect.origin.x = 0
        rotatedRect.origin.y = 0
        
        UIGraphicsBeginImageContext(rotatedRect.size)
        guard let context = UIGraphicsGetCurrentContext() else { return nil }
        context.translateBy(x: rotatedRect.width / 2, y: rotatedRect.height / 2)
        context.rotate(by: radian)
        context.translateBy(x: -image.size.width / 2, y: -image.size.height / 2)
        image.draw(at: .zero)
        let rotatedImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return rotatedImage
    }
    
    
    public class func zoomImage(_ bgimage:UIImage, newWidth:Int,
                                newHeight:Int, isScale:Bool) -> UIImage? {
    // 获取这个图片的宽和高
    //是否按比例缩放
//    var isScale = true;
        let width = bgimage.size.width
        let height = bgimage.size.height
        let size = CGSize()
        let new_width = CGFloat(newWidth)
        let new_height = CGFloat(newHeight)
        var scaleWidth = new_width/width;
        var scaleHeight = new_height/height;
        if (isScale) {
            if((new_width/width) > new_height/height) {
                scaleWidth = new_width/height;
                scaleHeight = new_height/height;
            }
            else{
                scaleWidth = new_width/width
                scaleHeight = new_height/width
            }
        }
        else{
            scaleWidth = new_width/width;
            scaleHeight = new_height/height;
        }
    // 创建操作图片用的matrix对象t
//    Matrix matrix = new Matrix();
//    // 计算宽高缩放率
//    float scaleWidth = ((float) newWidth) / width;
//    float scaleHeight = ((float) newHeight) / height;
//    if (isScale) {
//        if ((newWidth / width) > newHeight / height) {
//            //以高度为准
//            scaleWidth = (float) (newWidth / height);
//            scaleHeight = (float) (newHeight / height);
//        } else {
//            scaleWidth = (float) (newWidth / width);
//            scaleHeight = (float) (newHeight / width);
//        }
//    }
//    // 缩放图片动作
//    matrix.postScale(scaleWidth, scaleHeight);
//    Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
    // 创建一个bitmap的context
       // 并把它设置成为当前正在使用的context
       UIGraphicsBeginImageContext(size);
       
       // 绘制改变大小的图片
//       [image drawInRect:CGRectMake(0, 0, size.width, size.height)];
        bgimage.draw(in: CGRect(x: 0, y: 0, width: scaleWidth*width, height: scaleHeight*height))
       // 从当前context中创建一个改变大小后的图片
//       UIImage* scaledImage = UIGraphicsGetImageFromCurrentImageContext();
        let scaledImage = UIGraphicsGetImageFromCurrentImageContext()
       // 使当前的context出堆栈
       UIGraphicsEndImageContext();
    return scaledImage;
}

 //按比例裁剪bitmap
    public static func clipBitmapCenter(_ image:UIImage,  x:Int,  y:Int) -> UIImage? {
        let width = image.size.width
        let height = image.size.height

        var width_new:CGFloat = 0;
        var height_new:CGFloat = 0;
     //以高度为准裁剪
     if ((width) / height > CGFloat(x) / CGFloat(y)) {
         width_new = height * CGFloat(x) / CGFloat(y);
         height_new = height;
     } else {
         width_new = width;
         height_new = width * CGFloat(y) / CGFloat(x);
     }
    return cropImage(image,withRect: CGRect(x: (width - width_new)/2, y: (height-height_new)/2, width: width_new, height: height_new))
//     return Bitmap.createBitmap(image, (width - width_new)/2, (height - height_new)/2, width_new, height_new);
 }
    
    static func cropImage(_ image: UIImage, withRect rect: CGRect) -> UIImage? {
      UIGraphicsBeginImageContext(rect.size)
      guard let context = UIGraphicsGetCurrentContext() else { return nil }
      context.translateBy(x: -rect.minX, y: -rect.minY)
      image.draw(at: .zero)
      let croppedImage = UIGraphicsGetImageFromCurrentImageContext()
      UIGraphicsEndImageContext()
      return croppedImage
    }
    
    // 1.把两张图片绘制成一张图片
    public class func synthesisImg(img_dst: UIImage, img_src: UIImage, gravity:Int) -> UIImage {
        
        var ix:CGFloat = 0;
        var iy:CGFloat = 0;

               if ((gravity & TOP) == TOP) {
                   iy = 0;
               }
               else if((gravity&BOTTOM) == BOTTOM){
                iy = img_dst.size.height - img_src.size.height;
               }
               else if((gravity & CENTER_VERTICAL) == CENTER_VERTICAL){
                iy = img_dst.size.height/2 - img_src.size.height/2;
               }
               if ((gravity & LEFT) == LEFT) {
                   ix = 0;
               }
               else if ((gravity & RIGHT) == RIGHT) {
                ix = img_dst.size.width - img_src.size.width;
               }
               else if ((gravity & CENTER_HORIZONTAL)==CENTER_HORIZONTAL) {
                ix = img_dst.size.width/2 - img_src.size.width/2;
               }


        // 1.1.获取第一张图片的宽度
        let width = img_dst.size.width
        // 1.2.获取第一张图片的高度
        let height = img_dst.size.height

        // 1.3.开始绘制图片的大小
        UIGraphicsBeginImageContext(CGSize(width: width, height: height))
        // 1.4.绘制第一张图片的起始点
        img_dst.draw(at: CGPoint(x: 0, y: 0))
        // 1.5.绘制第二章图片的起始点
        img_src.draw(at: CGPoint(x: ix, y: iy))

        // 1.6.获取已经绘制好的
        let imageLong = UIGraphicsGetImageFromCurrentImageContext()!
        // 1.7.结束绘制
        UIGraphicsEndImageContext()

        // 1.8.返回已经绘制好的图片
        return imageLong
    }
    
    static func drawBitmap(_ img1:UIImage,bitmap:UIImage,x:CGFloat,y:CGFloat,paint:Paint) -> UIImage{
        // 1.3.开始绘制图片的大小
        UIGraphicsBeginImageContext(CGSize(width: img1.getWidth(), height: img1.getHeight()))
        paint.initGraphics()
        img1.draw(at: CGPoint(x: x, y: y))
              // 1.4.绘制第一张图片的起始点
              bitmap.draw(at: CGPoint(x: x, y: y))
              // 1.5.绘制第二章图片的起始点
//              img_src.draw(at: CGPoint(x: ix, y: iy))

              // 1.6.获取已经绘制好的
              let imageLong = UIGraphicsGetImageFromCurrentImageContext()!
              // 1.7.结束绘制
              UIGraphicsEndImageContext()
        return imageLong
    }
    
    static func drawBitmap(_ bitmap:UIImage,x:CGFloat,y:CGFloat,paint:Paint){
            paint.initGraphics()
           
            // 1.4.绘制第一张图片的起始点
            bitmap.draw(at: CGPoint(x: x, y: y))
    }
    
    class func createImage(_ image:UIImage,isCornored: Bool = true,size: CGSize = CGSize.zero,backgroundColor: UIColor = UIColor.white,callBack: @escaping (_ image: UIImage) ->()) {
    //在子线程中执行
            DispatchQueue.global().async {
                let rect = CGRect(origin: CGPoint.zero, size: size)
                //1. 开启上下文
                UIGraphicsBeginImageContext(size)
                //2. 设置颜色
                backgroundColor.setFill()
                //3. 颜色填充
                UIRectFill(rect)
                //4. 图像绘制
                //切回角
                let path = UIBezierPath(ovalIn: rect)
                path.addClip()
                
                image.draw(in: rect)
                //5. 获取图片
                let image1 = UIGraphicsGetImageFromCurrentImageContext()
                //6 关闭上下文
                UIGraphicsEndImageContext()
                //回到主线程刷新UI
                DispatchQueue.main.async(execute: {
                    callBack(image1!)
                })
            }
        }
    
    class func createImage(width:CGFloat, height:CGFloat) -> UIImage{
        // 1.3.开始绘制图片的大小
                  UIGraphicsBeginImageContext(CGSize(width: width, height: height))

                  // 1.6.获取已经绘制好的
             var img_dst = UIGraphicsGetImageFromCurrentImageContext()!
                  // 1.7.结束绘制
                  UIGraphicsEndImageContext()
        return img_dst;
    }
}
