package com.xl.game.tool;
import com.xl.game.math.Str;

public class Note
	{
		//去除一个单行注释
		char[] offNote(char text[])
			{
				int ptr=0;
				int nptr;
				//进入循环
				while(ptr>=0&&ptr<text.length)
					{
						//查找注释
						ptr=Str.strstr(text, ptr,'/');
						if(ptr!=-1)
							{
								//检查是否是注释
								if(text[ptr+1]=='/')
									{
										//搜索换行符
										nptr=Str.strstr(text, ptr,'\n');
										//若搜索到，则去除
										if(ptr!=-1)
											{
												Str.strremove(text, ptr, nptr-ptr);
											}


									}
							}
						//若找不到注释，则退出
						else
						{
							break;
						}
						ptr++;
					}
					return text;
			}




	}
