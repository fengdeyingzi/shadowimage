package net.yzjlb.shadowimage;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;

import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.platform.PlatformView;

public class FlutterShadowImage implements PlatformView {
    private ImageView imageView;

    FlutterShadowImage(Context context, BinaryMessenger messenger, int id, Map<String, Object> params){
        imageView = new ImageView(context);

    }
    @Override
    public View getView() {
        return imageView;
    }

    @Override
    public void dispose() {

    }
}
