package net.yzjlb.imgutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.util.Xml;

import com.xl.game.tool.DisplayUtil;
import com.xl.game.tool.Tool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
用于处理bitmap
添加水印，图片缩放
{
    path:"",
    maxWidth:"",
    maxHeight:"",

    list_text:[{
        text:"test",
        type:"address" or "text" or "time",
        img:"",
        fontSize:16,
        shadowX:0,
        shadowY:0,
        shadowColoe:"#303030",
        backgroundColor:"#ffffff",
        textColor:"#333333",
        gradient:["#333333","#595959"],
        gradient_angle:90,
        font:"font.ttf",
        x:0,
        y:0,
        w:300,
        h:200;
        gravity:"center",
        layout_gravity:"right|top"
    }],
    list_img:[{
        path:"",
        x:0,
        y:0,
        alpha:"50",
        tint:"#333333",
    }],
    list_effects:[{
    name:"alpha",
    value:100
    },
    {
    name:"brightness", //亮度
    value:10
    },
    {
    name:"contrast", //对比度
    value:10
    },
    {
    name:"flip",
    value:"horizontal" or "vertical"
    },

    ]
}
 */
public class ImageUtil {
    private static final String TAG = "ImageUtil";
    /**
     * Constant indicating that no gravity has been set
     **/
    public static final int NO_GRAVITY = 0x0000;

    /**
     * Raw bit indicating the gravity for an axis has been specified.
     */
    public static final int AXIS_SPECIFIED = 0x0001;

    /**
     * Raw bit controlling how the left/top edge is placed.
     */
    public static final int AXIS_PULL_BEFORE = 0x0002;
    /**
     * Raw bit controlling how the right/bottom edge is placed.
     */
    public static final int AXIS_PULL_AFTER = 0x0004;
    /**
     * Raw bit controlling whether the right/bottom edge is clipped to its
     * container, based on the gravity direction being applied.
     */
    public static final int AXIS_CLIP = 0x0008;
    /**
     * Bits defining the horizontal axis.
     */
    public static final int AXIS_X_SHIFT = 0;
    /**
     * Bits defining the vertical axis.
     */
    public static final int AXIS_Y_SHIFT = 4;
    /**
     * Push object to the top of its container, not changing its size.
     */
    public static final int TOP = (AXIS_PULL_BEFORE | AXIS_SPECIFIED) << AXIS_Y_SHIFT;
    /**
     * Push object to the bottom of its container, not changing its size.
     */
    public static final int BOTTOM = (AXIS_PULL_AFTER | AXIS_SPECIFIED) << AXIS_Y_SHIFT;
    /**
     * Push object to the left of its container, not changing its size.
     */
    public static final int LEFT = (AXIS_PULL_BEFORE | AXIS_SPECIFIED) << AXIS_X_SHIFT;
    /**
     * Push object to the right of its container, not changing its size.
     */
    public static final int RIGHT = (AXIS_PULL_AFTER | AXIS_SPECIFIED) << AXIS_X_SHIFT;
    public static final int CENTER_VERTICAL = AXIS_SPECIFIED << AXIS_Y_SHIFT;
    public static final int CENTER_HORIZONTAL = AXIS_SPECIFIED << AXIS_X_SHIFT;
    public static final int CENTER = CENTER_VERTICAL | CENTER_HORIZONTAL;

    //将图片缩放成指定大小，就算是小图也放大

    /***
     * 图片的缩放方法
     *
     * @param bgimage
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        //是否按比例缩放
        boolean isScale = true;
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        if (isScale) {
            if ((newWidth / width) > newHeight / height) {
                //以高度为准
                scaleWidth = (float) (newWidth / height);
                scaleHeight = (float) (newHeight / height);
            } else {
                scaleWidth = (float) (newWidth / width);
                scaleHeight = (float) (newHeight / width);
            }
        }
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
        return bitmap;
    }

    //将图片顺时针旋转指定的角度
    public static Bitmap rotateBimap(Bitmap srcBitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap bitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight()
                , matrix, true);
        return bitmap;
    }


    //按比例裁剪bitmap
    public static Bitmap clipBitmap(Bitmap image, int x, int y) {
        int width = image.getWidth();
        int height = image.getHeight();

        int width_new = 0;
        int height_new = 0;
        //以高度为准裁剪
        if (((double) width) / height > ((double) x) / y) {
            width_new = height * x / y;
            height_new = height;
        } else {
            width_new = width;
            height_new = width * y / x;
        }
        return Bitmap.createBitmap(image, 0, 0, width_new, height_new);
    }

    //将图片缩放成指定大小，如果是小图则不放大
    public static Bitmap zoomImageEx(Bitmap bgimage, double newWidth,
                                     double newHeight) {
        if (bgimage.getWidth() <= newWidth && bgimage.getHeight() <= newHeight) {
            return bgimage;
        }
        return zoomImage(bgimage, newWidth, newHeight);
    }

    //将两张图片合成一张 对齐方式
    public static Bitmap synthesisImg(Bitmap img_dst, Bitmap img_src, int gravity) {
        float ix = 0;
        float iy = 0;

        if (gravity == TOP) {
            iy = 0;
        }
        if (gravity == LEFT) {
            ix = 0;
        }
        if (gravity == RIGHT) {
            ix = img_dst.getWidth() - img_src.getWidth();
        }
        if (gravity == BOTTOM) {
            iy = img_dst.getHeight() - img_src.getHeight();
        }


        if (gravity == CENTER_HORIZONTAL) {
            ix = img_dst.getWidth() - (float) img_src.getWidth() / 2;
        } else if (gravity == CENTER_VERTICAL) {
            iy = img_dst.getHeight() - (float) img_src.getHeight() / 2;
        } else if (gravity == CENTER) {
            ix = img_dst.getWidth() - (float) img_src.getWidth() / 2;
            iy = img_dst.getHeight() - (float) img_src.getHeight() / 2;
        }

        Canvas canvas = new Canvas(img_dst);
        canvas.drawBitmap(img_src, ix, iy, null);
        return img_dst;
    }

    //通过json格式处理图片 生成水印
    public static Bitmap jsonBitmap(Context context, String jsonStr) throws JSONException {

        JSONObject object = new JSONObject(jsonStr);
        String path = object.getString("path");
        int maxWidth = object.getInt("maxWidth");
        int maxHeight = object.getInt("maxHeight");
        Bitmap bitmap = Bitmap.createBitmap(maxWidth, maxHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        JSONArray array_text = object.getJSONArray("list_text");
        JSONArray array_img = object.getJSONArray("list_img");
        JSONArray array_effects = object.getJSONArray("list_effects");
        //先进行特效处理

        //画文字
        for (int i = 0; i < array_text.length(); i++) {
            Paint paint_text = new Paint();
            paint_text.setAntiAlias(true);
            Paint paint_background = new Paint();
            JSONObject obj_text_item = array_text.getJSONObject(i);
            String text = obj_text_item.getString("text");
//            String type = obj_text_item.getString("type");
//            String img = obj_text_item.getString("img");
//            int text_x = obj_text_item.getInt("text_x");
//            int text_y = obj_text_item.getInt("text_y");
            int fontSize = obj_text_item.getInt("fontSize");
            paint_text.setTextSize(DisplayUtil.sp2px(context, fontSize));
            if (obj_text_item.has("font")) {
                String font = obj_text_item.getString("font");
                paint_text.setTypeface(Typeface.createFromFile(font));
            }


            if (obj_text_item.has("shadowColor")) {
                double shadowX = obj_text_item.getDouble("shadowX");
                double shadowY = obj_text_item.getDouble("shadowY");
                double radius = obj_text_item.getDouble("radius ");
                String shadowColor = obj_text_item.getString("shadowColor");
                paint_text.setShadowLayer((float) radius, (float) shadowX, (float) shadowY, XmlUtil.getColor(shadowColor));
            }


            if (obj_text_item.has("type")) {
                String type = obj_text_item.getString("type");
                if (type.equals("underline")) {
                    paint_text.setUnderlineText(true);
                } else if (type.equals("delete")) {
                    paint_text.setStrikeThruText(true);
                } else if (type.equals("bold")) {
                    paint_text.setSubpixelText(true);
                }
            }

            String textColor = obj_text_item.getString("textColor");
            paint_text.setColor(XmlUtil.getColor(textColor));
            if (obj_text_item.has("gradient")) {
                JSONArray array_gradient = obj_text_item.getJSONArray("gradient");
                int gradient_angle = obj_text_item.getInt("gradient_angle");
            }


            String gravity = obj_text_item.getString("gravity");
            int gravity_value = 0;
            int x = obj_text_item.getInt("x");
            int y = obj_text_item.getInt("y");
            int w = obj_text_item.getInt("w");
            int h = obj_text_item.getInt("h");
            Rect rect_draw = new Rect(x, y, x + w, y + h);
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
            if (obj_text_item.has("backgroundColor")) {
                String backgroundColor = obj_text_item.getString("backgroundColor");
                paint_background.setColor(XmlUtil.getColor(backgroundColor));
                canvas.drawRect(rect_draw, paint_background);
                Log.i(TAG, "backgroundColor:" + backgroundColor);
            }

            N2J_drawTextEx(canvas, text, rect_draw, paint_text, gravity_value);

//            if(obj_text_item.has("gravity")){
//                String gravity = obj_text_item.getString("gravity");
//            }

//            String layout_gravity = obj_text_item.getString("layout_gravity");
//            canvas.drawText(text, x,y,paint_text);
        }
        //再画图片
        for (int i = 0; i < array_img.length(); i++) {
            JSONObject obj_img_item = array_img.getJSONObject(i);
            String img_path = obj_img_item.getString("path");
            Paint paint_img = new Paint();
            paint_img.setAntiAlias(true);
            Bitmap bitmap_img = BitmapFactory.decodeFile(img_path);
            int ix = 0;
            int iy = 0;
            int x = obj_img_item.getInt("x");
            int y = obj_img_item.getInt("y");
            ix = x;
            iy = y;
            if (obj_img_item.has("layout_gravity")) {
                String layout_gravity = obj_img_item.getString("layout_gravity");
                int layout_gravity_value = 0;
                if (layout_gravity.indexOf("left") >= 0) {
                    layout_gravity_value |= LEFT;
                }
                if (layout_gravity.indexOf("top") >= 0) {
                    layout_gravity_value |= TOP;
                }
                if (layout_gravity.indexOf("right") >= 0) {
                    layout_gravity_value |= RIGHT;
                    ix -= bitmap_img.getWidth();
                }
                if (layout_gravity.indexOf("bottom") >= 0) {
                    layout_gravity_value |= BOTTOM;
                    iy -= bitmap_img.getHeight();
                }
//                if(layout_gravity.indexOf("center") >= 0){
//                    layout_gravity_value |= CENTER;
//                    ix 
//                }
            }
            if (obj_img_item.has("alpha")) {
                int alpha = obj_img_item.getInt("alpha");
                paint_img.setAlpha(alpha);
            }
            if (obj_img_item.has("tint")) {
                String tint = obj_img_item.getString("tint");
            }

            canvas.drawBitmap(bitmap_img, ix, iy, paint_img);
        }

        return bitmap;
    }

    public static float N2J_getCharW(char c, Paint paint) {
        char text[] = {c};


        return paint.measureText(text, 0, 1);
    }

    //获取在指定的宽度下，文字显示的行数
    public static int getTextLines(String text, Paint paint, int width) {
        int line = 1;
        float ix = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            float font_w = N2J_getCharW(c, paint);
            if (c == '\n') {
                ix = 0;
                line++;
                continue;
            }
            if (ix + font_w < width) {
                ix += font_w;
            } else {
                ix = 0;
                line++;
            }
        }
        return line;
    }

    //获取在指定的宽度下，每一行的文字
    public static ArrayList<String> getTexts(String text, Paint paint, int width) {
        ArrayList<String> list_text = new ArrayList<String>();
        int line = 1;
        float ix = 0;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            float font_w = N2J_getCharW(c, paint);
            if (c == '\n') {
                ix = 0;
                line++;
                list_text.add(buffer.toString());
                buffer = new StringBuffer();
                continue;
            }
            if (ix + font_w < width) {
                ix += font_w;
                buffer.append(c);
            } else {
                ix = 0;
                line++;
                list_text.add(buffer.toString());
                buffer = new StringBuffer();
            }
        }
        if (buffer.length() > 0) {
            list_text.add(buffer.toString());
        }
        return list_text;
    }

    //在canvas矩形区域内绘制文字
    public static void N2J_drawTextEx(Canvas canvas, String text, Rect rect, Paint paint, int gravity) {
        Log.i(TAG, String.format("N2J_drawTextEx: %d,%d,%d,%d,%b", rect.left, rect.top, rect.right, rect.bottom, gravity));
        float line_height = paint.getTextSize() * 18 / 16;
        int i = 0;
        float ix = rect.left;
        float iy = rect.top;
        int line = getTextLines(text, paint, rect.width());
        ArrayList<String> list_text = getTexts(text, paint, rect.width());
        float lineHeight = (float) (paint.getTextSize() * 1.5);
        if ((gravity & TOP) == TOP) {
            iy = rect.top;
        } else if ((gravity & BOTTOM) == BOTTOM) {
            iy = rect.bottom - lineHeight * line;
        } else if ((gravity & CENTER_VERTICAL) == CENTER_VERTICAL) {
            iy = rect.centerY() - lineHeight * line / 2;
        }

        if ((gravity & LEFT) == LEFT) {
            ix = 0;
        } else if ((gravity & RIGHT) == RIGHT) {
            ix = rect.right;
            paint.setTextAlign(Paint.Align.RIGHT);
        } else if ((gravity & CENTER_HORIZONTAL) == CENTER_HORIZONTAL) {
            paint.setTextAlign(Paint.Align.CENTER);
            ix = rect.centerX();
        }


        for (i = 0; i < list_text.size(); i++) {
            N2J_drawText(canvas, list_text.get(i), ix, iy, paint);
            iy += lineHeight;
        }
    }

    //算法 逆推得到top的值，然后减去top
    public static void N2J_drawChar(Canvas canvas, char c, float x, float y, Paint paint) {
        char[] chars = new char[2];
        chars[0] = c;
        canvas.drawText(chars, 0, 1, x, y + paint.getTextSize() * 11 / 12, paint);
    }

    public static void N2J_drawText(Canvas canvas, String c, float x, float y, Paint paint) {
        canvas.drawText(c, x, y + paint.getTextSize() * 11 / 12, paint);
    }
}
