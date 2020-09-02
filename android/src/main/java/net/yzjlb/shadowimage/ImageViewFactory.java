package net.yzjlb.shadowimage;

import android.content.Context;

import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class ImageViewFactory extends PlatformViewFactory {
    private BinaryMessenger messenger;

    public ImageViewFactory(BinaryMessenger messenger){
        super(StandardMessageCodec.INSTANCE);
        this.messenger = messenger;
    }
    @Override
    public PlatformView create(Context context, int viewId, Object args) {
        Map<String,Object> params = (Map<String,Object>) args;

        return new FlutterShadowImage(context,null,viewId,params);
    }
}
