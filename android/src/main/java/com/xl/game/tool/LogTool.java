package com.xl.game.tool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xl.game.view.ConsoleView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class LogTool {
    private static final String TAG = "LogTool";
    private static float mTouchX; //相对坐标
    private static float mTouchY;
    private static boolean isMove = false;
    private static boolean isRight = false;
    public static final int REQUEST_DIALOG_PERMISSION = 100;
    public static WindowManager windowManager;
    public static LinearLayout layout_main;
    public static boolean isSmall;
    public static int win_x, win_y;


    public static boolean checkFloatPermission(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return true;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            try {
                Class cls = Class.forName("android.content.Context");
                Field declaredField = cls.getDeclaredField("APP_OPS_SERVICE");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(cls);
                if (!(obj instanceof String)) {
                    return false;
                }
                String str2 = (String) obj;
                obj = cls.getMethod("getSystemService", String.class).invoke(context, str2);
                cls = Class.forName("android.app.AppOpsManager");
                Field declaredField2 = cls.getDeclaredField("MODE_ALLOWED");
                declaredField2.setAccessible(true);
                Method checkOp = cls.getMethod("checkOp", Integer.TYPE, Integer.TYPE, String.class);
                int result = (Integer) checkOp.invoke(obj, 24, Binder.getCallingUid(), context.getPackageName());
                return result == declaredField2.getInt(cls);
            } catch (Exception e) {
                return false;
            }
        } else {
            return Settings.canDrawOverlays(context);
        }
    }
//---------------------
//    作者：LOVE宝
//    来源：CSDN
//    原文：https://blog.csdn.net/lovedou0816/article/details/79253710
//    版权声明：本文为博主原创文章，转载请附上博文链接！


    public static void hidden(Activity activity, Context context) {
        if (windowManager != null) {
            windowManager.removeViewImmediate(layout_main);
            layout_main = null;
            windowManager = null;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public static void show(final Activity activity, final Context context) {
        Log.i(TAG, "show: 显示log");
        if (windowManager != null)
            return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!checkFloatPermission(context)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                activity.startActivityForResult(intent, REQUEST_DIALOG_PERMISSION);
                return;
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!checkFloatPermission(context)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                activity.startActivityForResult(intent, REQUEST_DIALOG_PERMISSION);
                return;
            }
        } else {
            if (!checkFloatPermission(context)) {
                Log.i(TAG, "show: 不具有权限");
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                activity.startActivityForResult(intent, REQUEST_DIALOG_PERMISSION);
                return;
            }
        }
        Log.i(TAG, "show: 加载悬浮窗");
        final ConsoleView consoleView = new ConsoleView(context);

        layout_main = new LinearLayout(context);
        layout_main.setBackgroundColor(0xa06090f0);
//        layout_main.setPadding(32,0,0,0);
        LinearLayout layout_ver = new LinearLayout(context);
        layout_ver.setOrientation(LinearLayout.VERTICAL);
        layout_main.addView(layout_ver);

        TextView button = new TextView(context);
        button.setTextSize(24);
        button.setPadding(8, 8, 8, 8);
        button.setText("×");
        TextView text_small = new TextView(context);
        text_small.setPadding(8, 8, 8, 8);
        text_small.setText("—");
        text_small.setTextSize(24);
        if (isSmall) {
            consoleView.setVisibility(View.GONE);
        }
        text_small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (consoleView.getVisibility() == View.VISIBLE) {
                    consoleView.setVisibility(View.GONE);
                    isSmall = true;
                } else {
                    consoleView.setVisibility(View.VISIBLE);
                    isSmall = false;
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidden(activity, context);
            }
        });
        layout_ver.addView(button);
        layout_ver.addView(text_small);
        layout_main.addView(consoleView);

        consoleView.setBackgroundColor(0xf0909090);
//        consoleView.setShell("echo showlog\nlogcat *:I\n");
        consoleView.setShell("echo showlog\nlogcat -c\nlogcat XConnect:I OConnect:I Socket:I flutter:I UMLog_Social:E MainActivity:I Log:I *:S\n");
// 获取WindowManager
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        final WindowManager.LayoutParams windowManagerParams = new WindowManager.LayoutParams();
        // 设置LayoutParams(全局变量）相关参数

        //	windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        // 显示myFloatView图像
        //	intent_cliplist=new Intent(this,clipboardservice.class);

        //intent_cliplist.addFlags(268435456);
        //	startService(intent_cliplist);
        if (Build.VERSION.SDK_INT >= 26) {
            windowManagerParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY; // 设置window type
        } else
            windowManagerParams.type = WindowManager.LayoutParams.TYPE_PHONE; // 设置window type
        windowManagerParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
        // 设置Window flag
        if (Build.VERSION.SDK_INT >= 23) {
            windowManagerParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM; //LayoutParams.FLAG_NOT_TOUCH_MODAL
        } else
            windowManagerParams.flags = 40;

        //	|LayoutParams.FLAG_NOT_FOCUSABLE;
        /*
         * 注意，flag的值可以为： LayoutParams.FLAG_NOT_TOUCH_MODAL 不影响后面的事件
         * LayoutParams.FLAG_NOT_FOCUSABLE 不可聚焦 LayoutParams.FLAG_NOT_TOUCHABLE
         * 不可触摸
         */
        // 调整悬浮窗口至左上角，便于调整坐标
        windowManagerParams.gravity = Gravity.START | Gravity.TOP;
        // 以屏幕左上角为原点，设置x、y初始值
        //windowManagerParams.x=0;
        //windowManagerParams.y=0;
        // 设置悬浮窗口长宽数据
        windowManagerParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        windowManagerParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        int width = DisplayUtil.dip2px(context, 300);
        int height = DisplayUtil.dip2px(context, 320);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        consoleView.setLayoutParams(layoutParams);

        windowManagerParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        windowManagerParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layout_main.setMinimumHeight(height / 2);
        windowManager.addView(layout_main, windowManagerParams);
        updateViewPosition(windowManager, layout_main, (int) win_x, (int) win_y, windowManagerParams);
        layout_main.setOnTouchListener(new View.OnTouchListener() {
            float hx, hy;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                // 获取到状态栏的高度
                Rect frame = new Rect();
                view.getWindowVisibleDisplayFrame(frame);
                int statusBarHeight = frame.top;

//                System.out.println("statusBarHeight:"+statusBarHeight);
                // 获取相对屏幕的坐标，即以屏幕左上角为原点
                float x = event.getRawX();
                float y = event.getRawY() - statusBarHeight; // statusBarHeight是系统状态栏的高度
                //Log.i("tag", "currX"+x+"====currY"+y);

                int screenWidth = consoleView.getResources().getDisplayMetrics().widthPixels;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // 捕获手指触摸按下动作
                        // 获取相对View的坐标，即以此View左上角为原点
                        mTouchX = event.getX();
                        mTouchY = event.getY();
                        hx = x;
                        hy = y;
                        isMove = false;
//                        Log.i("tag", "startX"+mTouchX+"====startY"+mTouchY);
                        if (isRight) {
                            //	setImageResource(focusRightResource);
                        } else {
                            //	setImageResource(focusLeftResource);
                        }
                        break;

                    case MotionEvent.ACTION_MOVE: // 捕获手指触摸移动动作
                        if (Math.abs(hx - x) > 0 || Math.abs(hy - y) > 0) {
                            isMove = true;
                        }
                        //setImageResource(defaultResource);
                        if (isMove)
                            updateViewPosition(windowManager, layout_main, (int) x, (int) y, windowManagerParams);

                        break;
                    case MotionEvent.ACTION_UP: // 捕获手指触摸离开动作
                        if (isMove) {
                            isMove = false;
                            float halfScreen = screenWidth / 2;
                            if (x <= halfScreen) {
                                //	setImageResource(leftResource);
                                //	x = 0;
                                isRight = false;
                            } else {
                                //	setImageResource(rightResource);
                                //	x = screenWidth;
                                isRight = true;
                            }
                            updateViewPosition(windowManager, layout_main, (int) x, (int) y, windowManagerParams);
                        } else {
                            //		setImageResource(leftResource);
//                            if(mClickListener!=null)
//                            {
//                                mClickListener.onClick(view);
//                            }
                        }
                        mTouchX = mTouchY = 0;
                        break;
                }
                return true;
            }


        });
    }

    private static void updateViewPosition(WindowManager windowManager, View view, int x, int y, WindowManager.LayoutParams windowManagerParams) {
        // 更新浮动窗口位置参数
        windowManagerParams.x = (int) (x - mTouchX);
        windowManagerParams.y = (int) (y - mTouchY);
        win_x = windowManagerParams.x;
        win_y = windowManagerParams.y;
        //windowManagerParams.x=(int) (x);
        //windowManagerParams.y=(int) (y);

        windowManager.updateViewLayout(view, windowManagerParams); // 刷新显示
    }

}
