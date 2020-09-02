package com.xl.game.tool;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
//import org.apache.http.protocol.HTTP;


/*
8859_1

*/



public class ZipUtils
{
	private static final int BUFF_SIZE = 1024*1024; // 1M Byte
	/**
	 * 批量压缩文件（夹）
	 *
	 * @param resFileList
	 * 要压缩的文件（夹）列表
	 * @param zipFile
	 * 生成的压缩文件
	 * @throws IOException
	 * 当压缩过程出错时抛出
	 */
	public static void zipFiles(Collection<File> resFileList,File zipFile) throws IOException
	{
		ZipOutputStream zipout = null;
		try
		{
			zipout=new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile), BUFF_SIZE));
			for(File resFile : resFileList)
			{
				zipFile(resFile, zipout, "");
			}
		}
		finally
		{
			if(zipout!=null)
				zipout.close();
		}
	}
	/**
	 * 批量压缩文件（夹）
	 *
	 * @param resFileList
	 * 要压缩的文件（夹）列表
	 * @param zipFile
	 * 生成的压缩文件
	 * @param comment
	 * 压缩文件的注释
	 * @throws IOException
	 * 当压缩过程出错时抛出
	 */
	public static void zipFiles(Collection<File> resFileList,File zipFile,String comment) throws IOException
	{
		ZipOutputStream zipout = null;
		try
		{
			zipout=new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile), BUFF_SIZE));
			for(File resFile : resFileList)
			{
				zipFile(resFile, zipout, "");
			}
			zipout.setComment(comment);
		}
		finally
		{
			if(zipout!=null)
				zipout.close();
		}
	}
	/**
	 * 解压缩一个文件
	 *
	 * @param zipFile
	 * 压缩文件
	 * @param folderPath
	 * 解压缩的目标目录
	 * @throws IOException
	 * 当解压缩过程出错时抛出
	 */
	public static void unZipFile(String zipName,String folderPath) throws ZipException, IOException
	{
		unZipFile(new File(zipName),folderPath);
	}
	public static void unZipFile(File zipFile,String folderPath) throws ZipException, IOException
	{
		File desDir = new File(folderPath);
		if(!desDir.exists())
		{
			desDir.mkdirs();
		}
		ZipFile zf = new ZipFile(zipFile);
		InputStream in = null;
		OutputStream out = null;
		try
		{
			for(Enumeration<?> entries = zf.entries(); entries.hasMoreElements();)
			{
				ZipEntry entry = ((ZipEntry) entries.nextElement());
				in=zf.getInputStream(entry);
				String str = folderPath+File.separator+entry.getName();
				str=new String(str.getBytes("UTF-8"), "UTF-8");
				File desFile = new File(str);
				if(!desFile.exists())
				{
					File fileParentDir = desFile.getParentFile();
					if(!fileParentDir.exists())
					{
						fileParentDir.mkdirs();
					}
					desFile.createNewFile();
				}
				out=new FileOutputStream(desFile);
				byte buffer[] = new byte[BUFF_SIZE];
				int realLength;
				while((realLength=in.read(buffer))>0)
				{
					out.write(buffer, 0, realLength);
				}
			}
		}
		finally
		{
			if(in!=null)
				in.close();
			if(out!=null){
				out.flush();
				out.close();
				}
		}
	}
	/**
	 * 解压文件名包含传入文字的文件
	 *
	 * @param zipFile
	 * 压缩文件
	 * @param folderPath
	 * 目标文件夹
	 * @param nameContains
	 * 传入的文件匹配名
	 * @throws ZipException
	 * 压缩格式有误时抛出
	 * @throws IOException
	 * IO错误时抛出
	 */
	public static ArrayList<File> upZipSelectedFile(File zipFile,String folderPath,String nameContains) throws ZipException,
	IOException
	{
		ArrayList<File> fileList = new ArrayList<File>();
		File desDir = new File(folderPath);
		if(!desDir.exists())
		{
			desDir.mkdir();
		}
		ZipFile zf = new ZipFile(zipFile);
		InputStream in = null;
		OutputStream out = null;
		try
		{
			for(Enumeration<?> entries = zf.entries(); entries.hasMoreElements();)
			{
				ZipEntry entry = ((ZipEntry) entries.nextElement());
				if(entry.getName().contains(nameContains))
				{
					in=zf.getInputStream(entry);
					String str = folderPath+File.separator+entry.getName();
					str=new String(str.getBytes("UTF-8"), "UTF-8");
// str.getBytes(AppConstans.UTF_8),"8859_1" 输出
// str.getBytes("8859_1"),AppConstans.UTF_8 输入
					File desFile = new File(str);
					if(!desFile.exists())
					{
						File fileParentDir = desFile.getParentFile();
						if(!fileParentDir.exists())
						{
							fileParentDir.mkdirs();
						}
						desFile.createNewFile();
					}
					out=new FileOutputStream(desFile);
					byte buffer[] = new byte[BUFF_SIZE];
					int realLength;
					while((realLength=in.read(buffer))>0)
					{
						out.write(buffer, 0, realLength);
					}
					fileList.add(desFile);
				}
			}
		}
		finally
		{
			if(in!=null)
				in.close();
			if(out!=null){
				out.flush();
				out.close();
				}
		}
		return fileList;
	}
	/**
	 * 获得压缩文件内文件列表
	 *
	 * @param zipFile
	 * 压缩文件
	 * @return 压缩文件内文件名称
	 * @throws ZipException
	 * 压缩文件格式有误时抛出
	 * @throws IOException
	 * 当解压缩过程出错时抛出
	 */
	public static ArrayList<String> getEntriesNames(File zipFile) throws ZipException, IOException
	{
		ArrayList<String> entryNames = new ArrayList<String>();
		Enumeration<?> entries = getEntriesEnumeration(zipFile);
		while(entries.hasMoreElements())
		{
			ZipEntry entry = ((ZipEntry) entries.nextElement());
			entryNames.add(new String(getEntryName(entry).getBytes("UTF-8"), "UTF-8"));
		}
		return entryNames;
	}
	/**
	 * 获得压缩文件内压缩文件对象以取得其属性
	 *
	 * @param zipFile
	 * 压缩文件
	 * @return 返回一个压缩文件列表
	 * @throws ZipException
	 * 压缩文件格式有误时抛出
	 * @throws IOException
	 * IO操作有误时抛出
	 */
	public static Enumeration<?> getEntriesEnumeration(File zipFile) throws ZipException, IOException
	{
		ZipFile zf = new ZipFile(zipFile);
		return zf.entries();
	}
	/**
	 * 取得压缩文件对象的注释
	 *
	 * @param entry
	 * 压缩文件对象
	 * @return 压缩文件对象的注释
	 * @throws UnsupportedEncodingException
	 */
	public static String getEntryComment(ZipEntry entry) throws UnsupportedEncodingException
	{
		return new String(entry.getComment().getBytes("UTF-8"), "UTF-8");
	}
	/**
	 * 取得压缩文件对象的名称
	 *
	 * @param entry
	 * 压缩文件对象
	 * @return 压缩文件对象的名称
	 * @throws UnsupportedEncodingException
	 */
	public static String getEntryName(ZipEntry entry) throws UnsupportedEncodingException
	{
		return new String(entry.getName().getBytes("UTF-8"), "UTF-8");
	}
	/**
	 * 压缩文件
	 *
	 * @param resFile
	 * 需要压缩的文件（夹）
	 * @param zipout
	 * 压缩的目的文件
	 * @param rootpath
	 * 压缩的文件路径
	 * @throws FileNotFoundException
	 * 找不到文件时抛出
	 * @throws IOException
	 * 当压缩过程出错时抛出
	 */
	private static void zipFile(File resFile,ZipOutputStream zipout,String rootpath) throws FileNotFoundException, IOException
	{
		rootpath=rootpath+(rootpath.trim().length()==0? "" :File.separator)+resFile.getName();
		rootpath=new String(rootpath.getBytes("UTF-8"), "UTF-8");
		BufferedInputStream in = null;
		try
		{
			if(resFile.isDirectory())
			{
				File[] fileList = resFile.listFiles();
				for(File file : fileList)
				{
					zipFile(file, zipout, rootpath);
				}
			}
			else
			{
				byte buffer[] = new byte[BUFF_SIZE];
				in=new BufferedInputStream(new FileInputStream(resFile), BUFF_SIZE);
				zipout.putNextEntry(new ZipEntry(rootpath));
				int realLength;
				while((realLength=in.read(buffer))!=-1)
				{
					zipout.write(buffer, 0, realLength);
				}
				in.close();
				zipout.flush();
				zipout.closeEntry();
			}
		}
		finally
		{
			if(in!=null)
				in.close();
// if (zipout != null);
// zipout.close();
		}
	}
	
	
	/*
	*获取压缩文件内文件流
	*
	*
	*/
	
	public InputStream getFile(String zippath,String filename) throws IOException
	{
		
		File file = new File(zippath);  
		ZipFile zipFile = new ZipFile(file);  
		InputStream zipInputStream = zipFile.getInputStream(zipFile.getEntry(filename));  
		return zipInputStream;
	}
	
	
}
