package com.xl.game.music;
import android.media.MediaPlayer;
import java.io.IOException;
import android.content.res.AssetManager;
import android.content.res.AssetFileDescriptor;
import android.content.*;

/*
MediaPlayer音乐播放集成

*/
public class Player
{
	MediaPlayer mdplayr;
	int id;
	
	
	
	
	void init(Context context,String assetname)
	{
		try
			{
				
				AssetManager asset = context.getAssets();
				//MediaPlayer player = new MediaPlayer();
				
						AssetFileDescriptor r4_AssetFileDescriptor = asset.openFd(assetname);
				
								mdplayr.setDataSource(r4_AssetFileDescriptor.getFileDescriptor(), r4_AssetFileDescriptor.getStartOffset(), r4_AssetFileDescriptor.getLength());
							
						
						
				
				//设置同步播放，阻塞声音
				mdplayr.prepare();
			}
		catch(IllegalStateException e)
			{
				
			}
		catch(IOException e)
			{
				e.printStackTrace();
			}
	}
	
	void settype(int type)
	{
		
	}
	
	void setID(int id)
	{
		this.id=id;
	}
	
	void setVolume(int n)
	{
		mdplayr.setVolume(n,n);
	}
	
	void setPlayTime(int time)
	{
		mdplayr.seekTo(time);
	}
	
	//获取音乐总时间
	int getsoundtotaltime()
	{
		return mdplayr.getDuration();
	}
	
	//获取当前播放时间
	int getsoundcurtime()
	{
		return mdplayr.getCurrentPosition();
	}
	
	//设置当前播放时间
	void setplaytime(int time)
	{
		mdplayr.seekTo(time);
	}
	
	void play()
	{
		mdplayr.start();
	}
	
	void pause()
	{
		mdplayr.pause();
	}
	
	void resume()
	{
		if(!mdplayr.isPlaying())
		{
			mdplayr.start();
		}
	}
	
	void stop()
	{
		mdplayr.stop();
	}
	
	void close()
	{
		mdplayr.release();
	}
}




/*
方法：create(Context context, Uri uri)  
解释：静态方法，通过Uri创建一个多媒体播放器。

方法：create(Context context, int resid) 
解释：静态方法，通过资源ID创建一个多媒体播放器

方法：create(Context context, Uri uri, SurfaceHolder holder) 
解释：静态方法，通过Uri和指定 SurfaceHolder 【抽象类】 创建一个多媒体播放器

方法： getCurrentPosition() 
解释：返回 Int， 得到当前播放位置

方法： getDuration() 
解释：返回 Int，得到文件的时间

方法：getVideoHeight() 
解释：返回 Int ，得到视频的高度

方法：getVideoWidth() 
解释：返回 Int，得到视频的宽度

方法：isLooping() 
解释：返回 boolean ，是否循环播放

方法：isPlaying() 
解释：返回 boolean，是否正在播放

方法：pause() 
解释：无返回值 ，暂停

方法：prepare() 
解释：无返回值，准备同步

方法：prepareAsync() 
解释：无返回值，准备异步

方法：release() 
解释：无返回值，释放 MediaPlayer  对象

方法：reset() 
解释：无返回值，重置 MediaPlayer  对象

方法：seekTo(int msec) 
解释：无返回值，指定播放的位置（以毫秒为单位的时间）

方法：setAudioStreamType(int streamtype) 
解释：无返回值，指定流媒体的类型

方法：setDataSource(String path) 
解释：无返回值，设置多媒体数据来源【根据 路径】

方法：setDataSource(FileDescriptor fd, long offset, long length)
解释：无返回值，设置多媒体数据来源【根据 FileDescriptor】 

方法：setDataSource(FileDescriptor fd) 
解释：无返回值，设置多媒体数据来源【根据 FileDescriptor】

方法：setDataSource(Context context, Uri uri) 
解释：无返回值，设置多媒体数据来源【根据 Uri】

方法：setDisplay(SurfaceHolder sh) 
解释：无返回值，设置用 SurfaceHolder 来显示多媒体

方法：setLooping(boolean looping) 
解释：无返回值，设置是否循环播放

事件：setOnBufferingUpdateListener(MediaPlayer.OnBufferingUpdateListener listener) 
解释：监听事件，网络流媒体的缓冲监听 

事件：setOnCompletionListener(MediaPlayer.OnCompletionListener listener) 
解释：监听事件，网络流媒体播放结束监听

事件：setOnErrorListener(MediaPlayer.OnErrorListener listener) 
解释：监听事件，设置错误信息监听

事件：setOnVideoSizeChangedListener(MediaPlayer.OnVideoSizeChangedListener listener) 
解释：监听事件，视频尺寸监听

方法：setScreenOnWhilePlaying(boolean screenOn) 
解释：无返回值，设置是否使用 SurfaceHolder 显示

方法：setVolume(float leftVolume, float rightVolume) 
解释：无返回值，设置音量

方法：start() 
解释：无返回值，开始播放

方法：stop() 
解释：无返回值，停止播放



*/
