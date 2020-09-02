package com.xl.game.tool;

import java.io.*;

public class Log
{
	static final String DIR="mnt/sdcard";
	
	//最大文件长度
	public static int MAX_LEN = 10240;
	
	//清除log
	public static void clear()
	{
		File file_e = new File(DIR,"log_e.txt");
		File file_i = new File(DIR,"log_i.txt");
		File file_w = new File(DIR,"log_w.txt");
		File file_d = new File(DIR,"log_d.txt");
		if(file_e.isFile())
		{
			file_e.delete();
			try
			{
				file_e.createNewFile();
			} catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		if(file_i.isFile())
		{
			file_i.delete();
			try
			{
				file_i.createNewFile();
			} catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		if(file_w.isFile())
		{
			file_w.delete();
			try
			{
				file_w.createNewFile();
			} catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		if(file_d.isFile())
		{
			file_d.delete();
			try
			{
				file_d.createNewFile();
			} catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 向文件中写入错误信息
	 * 
	 * @param info
	 */
	public static void
	e(String text,String info) {
		File dir = new File(DIR);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		//打印到log日志
		if(text!=null && info!=null)
		android.util.Log.e(text,info);
		//打印到文本文件
		File file = new File(dir, "log_e.txt");
		try
		{
			if (!file.isFile())
			{
				//file.createNewFile();
			}
			else
			{
				try
					{
						if(file.length() > MAX_LEN)
						{
							file.delete();
							file.createNewFile();
						}
						FileOutputStream fileOutputStream = new FileOutputStream(file, true);

						fileOutputStream.write((text+" "+info+"\n\n").getBytes());
						fileOutputStream.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		

	}
	
	public static void i(String text,String info) {
		File dir = new File(DIR);
		//打印到log日志
		if(text!=null && info!=null)
			android.util.Log.i(text,info);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, "log_i.txt");
		try
		{
			if (!file.isFile())file.createNewFile();
		}
		catch (IOException e)
		{}
		try
		{
			FileOutputStream fileOutputStream = new FileOutputStream(file, true);
			fileOutputStream.write(info.getBytes());
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void w(String text,String info) {
		File dir = new File(DIR);
		//打印到log日志
		if(text!=null && info!=null)
			android.util.Log.w(text,info);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, "log_w.txt");
		try
		{
			if (!file.isFile())file.createNewFile();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			FileOutputStream fileOutputStream = new FileOutputStream(file, true);
			fileOutputStream.write(info.getBytes());
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		
	public static void d(String text,String info) {
		File dir = new File(DIR);
		//打印到log日志
		if(text!=null && info!=null)
			android.util.Log.d(text,info);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, "log_d.txt");
		try
		{
			if (!file.isFile())file.createNewFile();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			FileOutputStream fileOutputStream = new FileOutputStream(file, false);
			fileOutputStream.write(info.getBytes());
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//输出日志
	public static void exception(Exception ex){
		String info = null;
		ByteArrayOutputStream baos = null;
		PrintStream printStream = null;
		try {
			baos = new ByteArrayOutputStream();
			printStream = new PrintStream(baos);
			ex.printStackTrace(printStream);
			byte[] data = baos.toByteArray();
			info = new String(data);
			e("XLLOG",info);
			data = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (printStream != null) {
					printStream.close();
				}
				if (baos != null) {
					baos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
