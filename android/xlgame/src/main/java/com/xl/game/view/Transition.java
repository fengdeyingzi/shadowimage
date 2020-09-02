package com.xl.game.view;
import android.graphics.Canvas;

/*
场景特效
如屏幕变白，屏幕变黑
*/



public class Transition
{
	int type;
	public static final int 
	_BLEAK=0,
	_WHITE=1,
	_RED=2;
	
	int ftps;
	int colors[]; //颜色变化数组
	boolean isrun;
	int time; //控制播放的时间
	
	public Transition()
	{
		time=0;
		isrun=false;
	}
	
	
	//设置类型
	void setType(int type)
	{
		this.type=type;
	}
	
	//设置帧数
	void setFtps(int n)
	{
		this.ftps=n;
		this.colors=new int[n];
	}
	
	//开始运行
	void start()
	{
		isrun=true;
	}
	
	//停止
	void stop()
	{
		isrun=false;
	}
	
	//暂停
	int pause()
	{
		isrun=false;
		return 0;
	}
	
	//恢复
	int resume()
	{
		isrun=true;
		return 0;
	}
	
	//显示
	void draw(Canvas canvas)
	{
		if(isrun)
		{
		switch(type)
		{
			case _BLEAK:
				drawBleak(canvas);
			break;
			case _WHITE:
				drawWhite(canvas);
				break;
			case _RED:
		}
		}
	}

private void drawWhite(Canvas canvas)
	{
		canvas.drawColor(colors[time]);
	}

private void drawBleak(Canvas canvas)
	{
		canvas.drawColor(colors[time]);
	}
	
	//运行
  void run()
{
	time++;
	if(time>=ftps)
		stop();
	
	
}	
	
	
	
}
