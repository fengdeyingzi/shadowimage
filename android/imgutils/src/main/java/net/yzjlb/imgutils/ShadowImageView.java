package net.yzjlb.imgutils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import org.json.JSONException;

/*
用于展示水印效果的image控件

 */
@SuppressLint("AppCompatCustomView")
public class ShadowImageView extends ImageView {


    public ShadowImageView(Context context) {
        super(context);
        initView();
    }

    public ShadowImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ShadowImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    
    private void initView(){
        setScaleType(ScaleType.CENTER_INSIDE);
    }
    
    //传递json给image
    public void setJsonImage(String jsonStr) throws JSONException {
        Bitmap bitmap = ImageUtil.jsonBitmap(getContext(),jsonStr);
        setImageBitmap(bitmap);
    }
    
    
}
