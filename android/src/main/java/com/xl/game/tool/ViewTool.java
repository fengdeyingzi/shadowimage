package com.xl.game.tool;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ViewTool
{
	//加载布局成view
	public static View getView(Context context,int id)
	{
		LayoutInflater factory =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	//LayoutInflater factory = LayoutInflater.from(context);
	return  factory.inflate(id, null);
	}
	
	//
	public static Drawable getDrawable(Context context,int id){
		if(Build.VERSION.SDK_INT>=21){
			return context.getDrawable(id);
		}
		else{
			return context.getResources().getDrawable(id);
		}
	}
	
	public static String getString(Context context, int id){
		if(Build.VERSION.SDK_INT>=21){
			return context.getString(id);
		}
		else
			return context.getResources().getString(id);
	}
	
	
	//通过文件名加载drawable
	public static Drawable getDrawable(Context context,String name)
	{
		int id = context.getResources().getIdentifier(name,"drawable",  context.getPackageName());  
		return context.getResources().getDrawable(id); 
	}
	
	//获取View的截图
	public static Bitmap getBitmap(View view) 
	{
		if(view.getMeasuredWidth()==0)
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)); 
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap= view.getDrawingCache();
		return bitmap; 
	}
	
	/**
	 * View渐隐动画效果
	 *
	 */
	public static void setHideAnimation( View view, int duration ){
		AlphaAnimation mHideAnimation= null;
		if( null == view || duration<0 ){
			return;
		}
		if( null != mHideAnimation ){
			mHideAnimation.cancel( );
		}
		mHideAnimation = new AlphaAnimation(1.0f, 0.0f);
		mHideAnimation.setDuration( duration );
		mHideAnimation.setFillAfter( true );
		view.startAnimation( mHideAnimation );
	}
	/**
	 * View渐显动画效果
	 *
	 */
	public static void setShowAnimation( View view, int duration ){
		AlphaAnimation mShowAnimation= null;
		if( null == view || duration<0 ){
			return;
		}
		if( null != mShowAnimation ){
			mShowAnimation.cancel( );
		}
		mShowAnimation = new AlphaAnimation(0.0f, 1.0f);
		mShowAnimation.setDuration( duration );
		mShowAnimation.setFillAfter( true );
		view.startAnimation( mShowAnimation );
	}
	
	/*
	兼容设置background
	*/
	public static void setBackgroundDrawable(View view,Drawable drawable)
	{
		if(Build.VERSION.SDK_INT >=16)
		{
			view.setBackground(drawable);
		}
		else
		{
			view.setBackgroundDrawable(drawable);
		}
	}
	
	//获取状态栏高度
	public static int getStatusBarHeight(Context context) {
	int statusBarHeight = 0;
	Resources res = context.getResources();
	int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
	if (resourceId > 0) {
	statusBarHeight = res.getDimensionPixelSize(resourceId);
	}
	return statusBarHeight;
	}
	
	/** 
	* 设置状态栏图标为深色和魅族特定的文字风格 
	* 可以用来判断是否为Flyme用户 
	* @param window 需要设置的窗口 
	* @param dark 是否把状态栏字体及图标颜色设置为深色 
	* @return boolean 成功执行返回true 
	*
	*/ public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) 
	{
		boolean result = false; 
		if (window != null) 
			{
				try 
				{
					WindowManager.LayoutParams lp = window.getAttributes(); 
					Field darkFlag = WindowManager.LayoutParams.class .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON"); 
					Field meizuFlags = WindowManager.LayoutParams.class .getDeclaredField("meizuFlags"); 
					darkFlag.setAccessible(true); 
					meizuFlags.setAccessible(true); 
					int bit = darkFlag.getInt(null);
					int value = meizuFlags.getInt(lp); 
					if (dark) 
						{ value |= bit; } 
					else { value &= ~bit; } 
					meizuFlags.setInt(lp, value);
					window.setAttributes(lp); 
					result = true; 
					}
					catch (Exception e) 
					{  }
					}
					return result; 
					} 


	



	/** 
	* 设置状态栏字体图标为深色，需要MIUIV6以上 
	* @param window 需要设置的窗口 
	* @param dark 是否把状态栏字体及图标颜色设置为深色 
	* @return boolean 成功执行返回true
	*
	*/ 
	public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) 
	{
		boolean result = false; 
		if (window != null)
			{
				Class clazz = window.getClass(); 
				try 
				{
					int darkModeFlag = 0;
					Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams"); 
					Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
					darkModeFlag = field.getInt(layoutParams);
					Method extraFlagField = clazz.getMethod("setExtraFlags", 
					int.class, int.class); 
					if(dark){ extraFlagField.invoke(window,darkModeFlag,darkModeFlag);
					//状态栏透明且黑色字体
					}
					else
						{
							extraFlagField.invoke(window, 0, darkModeFlag);
							//清除黑色字体 
							}
							result=true; 
					}
					catch (Exception e)
					{  }
					} 
					return result; 
					} 


	


/*
	官方在Android6.0中提供了亮色状态栏模式，配置很简单：
	*/
	public static void setStatusBarLightMode(Activity activity)
	{
	if (Build.VERSION.SDK_INT >= 23) 
		{
			activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		} 
}

/*
	或者在style属性中加上
	<item name="android:windowLightStatusBar">true</item>
	*/
	
	//设置状态栏颜色(仅支持安卓5.0以上)
	public static void setStatusBarColor(Window window,int color)
	{
	if(Build.VERSION.SDK_INT>=21)
	{
	//取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
	window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
	//需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
	window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
	//设置状态栏颜色
	window.setStatusBarColor(color);
	}
	}
	
	//设置透明状态栏
	public static void setWindowTranslucentStatus(Window window)
	{
	window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
	}
	
	
	
	
	//为根布局下所有按钮设置监听
	public static void setOnClickListenerAllButtons(Activity activity,View.OnClickListener listener)
	{
		//获取根布局
		ViewGroup group = (ViewGroup)((ViewGroup)activity.findViewById(android.R.id.content)).getChildAt(0);
    setOnClickListenerAllButtons(group,listener);
	}
	
	//为所有按钮设置监听(按钮id不等于-1)
	public static void setOnClickListenerAllButtons(View view,View.OnClickListener listener) {

		List<View> allchildren = new ArrayList<View>();

		if(view instanceof Button)
		{
			if(view.getId()!= -1)
				view.setOnClickListener(listener);
		}
		else if(view instanceof ImageButton)
		{
			if(view.getId()!=-1)
				view.setOnClickListener(listener);
		}

		else if (view instanceof ViewGroup) {

			ViewGroup vp = (ViewGroup) view;

			for (int i = 0; i < vp.getChildCount(); i++) {

				View viewchild = vp.getChildAt(i);

				setOnClickListenerAllButtons(viewchild,listener);

			}

		}

	}
}
