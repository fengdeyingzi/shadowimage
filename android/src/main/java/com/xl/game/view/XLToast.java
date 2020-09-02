package com.xl.game.view;

import android.content.Context;
import android.widget.Toast;


public class XLToast
{
	Context context;
	String text;
	int time;
	Toast toast;
	public XLToast(Context context)
	{
		toast = Toast.makeText(context,"",0);
		this.context = context;
		this.time=0;
	}
	
	public Toast setText(CharSequence text)
	{
		toast.setText(text);
		return toast;
	}
	
	public void show()
	{
		toast.setDuration(time);
		toast.show();
	}
	
	public Toast setTime(int time)
	{
		toast.setDuration(time);
		this.time=time;
		return toast;
	}
	
}
