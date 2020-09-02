package com.xl.game.math;

/*
物体坐标移动
*/


public class Move
{
	int px[],py[]; //每次移动的屏幕坐标
	int x,y; //当前坐标
	float v;  //速度
	int road; //当前保存的坐标位置
	
	public Move(int ftps,int x,int y)
	{
		px=new int[ftps];
		py=new int[ftps];
		
		road=1;
		px[0]=x;
		py[0]=y;
		
	}
	
	public void setV(float v)
	{
		this.v=v;
	}
	
	//设置移动的相对位置
	public void setMove(int x,int y)
	{
		this.x+=x;
		this.y+=y;
		float movex=0,movey=0;
		float tempx=px[road-1],tempy=py[road-1];
		int type;
		int ftp;
		//计算以哪一方移动为准
		if(x*x>y*y)//x
		{
			type=0;
			ftp=(int)(Math.abs(x)/v);
			movex=(float)x/ftp;
			movey=(float)y/ftp;
		}
		else 
		{
			type=1;
			ftp=(int)(Math.abs(y)/v);
			movex=(float)x/ftp;
			movey=(float)y/ftp;
		}
		for(int i=0;i<ftp;i++)
		{
			tempx+=movex;
			tempy+=movey;
		this.px[road]=(int)tempx;
		this.py[road]=(int)tempy;
		
		road++;
		}
		
		//Logcat.e(  "ftp"+ftp);
	}
	
	
	public int getFtps()
	{
		return road;
	}
	
	public int getx(int n)
	{
		return px[n];
	}
	
	public int gety(int n)
	{
		return py[n];
	}
}
