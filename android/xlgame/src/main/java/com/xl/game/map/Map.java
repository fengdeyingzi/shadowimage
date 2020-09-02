package com.xl.game.map;
import java.io.*;

public class Map
{
	static Byte row[]=new Byte[1024];
	static int x;
	static int y;
	static int mapw;
	static int maph;
	static int map[]=new int[200*200];
	
	//从resource的asset中读取文件数据
	String fileName = "test.txt"; //文件名字
	String res=new String();
	/*
	try
	{
		//得到资源中的asset数据流
		InputStream in = getResources().getAssets().open(fileName);
		int length = in.available();
		byte [] buffer = new byte[length];
		in.read(buffer);
		res = EncodingUtils.getString(buffer, "UTF-8");
  }
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	*/
	String file=new String();
	String handfile=new String();
	String mapname = null;
	int mapx,mapy;
	int boxsize;
	
	void maping()
	{
		int ix;
		int iy;
		int i;
		
		handfile = "" + file + "/金手指/" + mapname;
		//if(filestate(handfile)==1)handc=1;
		//else handc=0;
		//cl();
		//mapx=(skyView_all.SCRW-mapw*16)/2;
		//mapy=(skyView_all.SCRW-maph*16)/2;
		boxsize=0;
		for(iy=0;iy<maph;iy++)
		{
			for(ix=0;ix<mapw;ix++)
			{
				if(map[mapw*iy+ix]==0)
				{continue;}
				else 
					//skyView_all.bmpshowflip(boxptr,mapx+ix*16,mapy+iy*16,16,16,16,6,0,16*map[mapw*iy+ix],*boxptr);
				if(map[mapw*iy+ix]==3)boxsize++;
			}
		}
		//bmpshowflip(boxptr,mapx+x*16,mapy+y*16,16,16,16,6,0,0,*boxptr);
		/*
		if(handc!=0&&ka==2)toolbar("菜单","金手指");
		else if(handc==0&&ka==2)toolbar("菜单","");
		else if(ka==10)toolbar("","取消");
		
		//bmpshowflip(keyptr,(SCRW-30)/2,SCRH-80,30,30,30,6,0,0,*keyptr);
		//bmpshowflip(keyptr,(SCRW-30)/2,SCRH-80+30,30,30,30,6,0,30,*keyptr);
		//bmpshowflip(keyptr,(SCRW-30)/2-30,SCRH-80+30,30,30,30,6,0,60,*keyptr);
		//bmpshowflip(keyptr,(SCRW-30)/2+30,SCRH-80+30,30,30,30,6,0,90,*keyptr);

		for(i=0;i<3;i++)
		{
			drect(button[i][0],button[i][1],button[i][2],button[i][3],85,175,220);
			drect(button[i][0]+2,button[i][1]+2,button[i][2]-4,button[i][3]-4,220,220,212);
			wz(buttontext[i],button[i][0]+5,button[i][1],button[i][3]);
		}
		if(boxs==0)wz(mapname,5,0,28);
		//rf();
		*/
	}
	
	
	
	
	void mapload(String mapfile)
{
	/*
boxe=1;
boxs=1;
mapf=open(mapfile,1);
seek(mapf,5,0);
read(mapf,&x,4);
read(mapf,&y,4);
read(mapf,&mapw,4);
read(mapf,&maph,4);
read(mapf,map,mapw*maph);
close(mapf);
maping();
step=0;
*/
}


int aopen(String filename,int type)
{
	
	return 1;
}

int agetlen(String filename)
{
	return 8;
}



	
	
}
