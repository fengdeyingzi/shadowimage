#import "ShadowimagePlugin.h"
#if __has_include(<shadowimage/shadowimage-Swift.h>)
#import <shadowimage/shadowimage-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "shadowimage-Swift.h"
#endif

@implementation ShadowimagePlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftShadowimagePlugin registerWithRegistrar:registrar];
}
@end
