package com.xl.game.tool;

import java.util.HashMap;
import java.util.Map;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
public class Urlquest {
	/**
	 * 解析出url请求的路径，包括页面
	 * @param strURL url地址
	 * @return url路径
	 */
	public static String UrlPage(String strURL)
	{
    String strPage=null;
		String[] arrSplit=null;

		strURL=strURL.trim().toLowerCase();

		arrSplit=strURL.split("[?]");
		if(strURL.length()>0)
		{
			if(arrSplit.length>1)
			{
				if(arrSplit[0]!=null)
				{
					strPage=arrSplit[0];
				}
			}
		}

    return strPage;   
	}
	
	/*
	获取url中文件名
	
	*/
	public static String getUrlFileName(String strUrl)
	{
		if (strUrl==null){
			throw new NullPointerException("strUrl is null");
		}
		String name=null;
		String url = UrlPage(strUrl);
		if (url == null) {
			return null;
		}
		int index = url.lastIndexOf("/");
		if(index>0)
		{
		name=url.substring(index + 1);
		}
		return name;
	}
	
	
	/**
	 * 去掉url中的路径，留下请求参数部分
	 * @param strURL url地址
	 * @return url请求参数部分
	 */
	private static String TruncateUrlPage(String strURL)
	{
    String strAllParam=null;
		String[] arrSplit=null;

		strURL=strURL.trim().toLowerCase();

		arrSplit=strURL.split("[?]");
		if(strURL.length()>1)
		{
			if(arrSplit.length>1)
			{
				if(arrSplit[1]!=null)
				{
					strAllParam=arrSplit[1];
				}
			}
		}

    return strAllParam;   
	}
	/**
	 * 解析出url参数中的键值对
	 * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
	 * @param URL  url地址
	 * @return  url请求参数部分
	 */
	public static Map<String, String> URLRequest(String URL)
	{
    Map<String, String> mapRequest = new HashMap<String, String>();

		String[] arrSplit=null;

    String strUrlParam=TruncateUrlPage(URL);
    if(strUrlParam==null)
    {
			return mapRequest;
    }
		//每个键值为一组 www.2cto.com
    arrSplit=strUrlParam.split("[&]");
    for(String strSplit:arrSplit)
    {
			String[] arrSplitEqual=null;         
			arrSplitEqual= strSplit.split("[=]");

			//解析出键值
			if(arrSplitEqual.length>1)
			{
				//正确解析
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

			}
			else
			{
				if(arrSplitEqual[0]!="")
				{
					//只有参数没有值，不加入
					mapRequest.put(arrSplitEqual[0], "");       
				}
			}
    }   
    return mapRequest;   
	}

	//拼接网址
	public static String addUrl(String url, String key,String value)
	{
		if(value==null)
			value="null";
		if(value.length()==0)
			return url;
		if(url.indexOf("?")>0)
		{
			try
			{
				url += "&"+key+"="+URLEncoder.encode(value, "UTF-8");
			} catch(UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				url += "?"+key+"="+URLEncoder.encode(value, "UTF-8");
			} catch(UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}
		return url;
	}

	//获取get请求的参数(有问题)
	public static String get(String url,String name)
	{
		String get_text="";
		int index=0;
		if((index=url.indexOf("?"))>=0)
		{
			//String item_get[]=fileName.split("?");
			//fileName=request[1].substring(0,index);
			//fileName=item_get[0];
			get_text=url.substring(index+1,url.length());
			//get=item_get[1];
		}


		if(get_text==null)return null;
		String post_items[];
		if(get_text.indexOf("&")>0)
		 post_items=get_text.split("&");
		else
			post_items = new String[]{get_text};
		String temp=null;
		for(String item:post_items)
		{
			if(item.startsWith(name+"="))
			{
				for(int i=0;i<item.length();i++)
				{
					char c=item.charAt(i);
					if(c=='=')
					{
						temp=item.substring(i+1,item.length());
						try
						{
							return URLDecoder.decode(temp, "UTF-8");
						}
						catch (UnsupportedEncodingException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
		return null;
	}
	
	
	//获取get请求
	public static String _get(String url,String name)
	{
		String get_text="";
		int index=0;
		if((index=url.indexOf("?"))>=0)
		{
			//String item_get[]=fileName.split("?");
			//fileName=request[1].substring(0,index);
			//fileName=item_get[0];
			get_text=url.substring(index+1,url.length());
			//get=item_get[1];
		}


		if(get_text==null)return null;
		String post_items[]=get_text.split("&");
		String temp=null;
		for(String item:post_items)
		{
			if(item.startsWith(name+"="))
			{
				for(int i=0;i<item.length();i++)
				{
					char c=item.charAt(i);
					if(c=='=')
					{
						temp=item.substring(i+1,item.length());
						try
						{
							return URLDecoder.decode(temp, "utf-8");
						}
						catch (UnsupportedEncodingException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
		return null;
	}
}
