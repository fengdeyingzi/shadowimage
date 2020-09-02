package net.yzjlb.shadowimage;


import io.flutter.plugin.common.PluginRegistry;

public class FlutterToAndroidShadowImageViewPlugin {
    public static void registerWith(PluginRegistry registry) {
        final String key = ShadowimagePlugin.class.getCanonicalName();
        if (registry.hasPlugin(key)) return;
        PluginRegistry.Registrar registrar = registry.registrarFor(key);
        registrar.platformViewRegistry().registerViewFactory("plugins.woshiku.top/myview", new ImageViewFactory(registrar.messenger()));
    }
}
