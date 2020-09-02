package com.xl.game.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xl.game.tool.DisplayUtil;

public class XLWebView extends WebView 
{
	public static String TAG = "XLWebView";
	int progress;
	Paint paint_rect;
	Paint paint_back;
	Context context;





	void initView()
	{
		paint_rect=new Paint();
		paint_rect.setColor(0xa03090f0);

		paint_back=new Paint();
		paint_back.setColor(0xa0404040);

		getSettings().setJavaScriptEnabled(true);
		setWebViewClient(new WebViewClient()
			{
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) 
				{

					view.loadUrl(url);
					//Logcat.e("new url "+url);
					return true;
				}

				@Override
				public android.webkit.WebResourceResponse shouldInterceptRequest(android.webkit.WebView view, java.lang.String url) 
				{

					return null;
				}

				@Override
				public void onPageFinished(WebView view, String url)
				{
					if(!view.getSettings().getLoadsImagesAutomatically())
					{
						/*
						view.getSettings().setLoadsImagesAutomatically(true);

						if(Build.VERSION.SDK_INT>=15)
						view.setLayerType(View.LAYER_TYPE_NONE, null);//如果渲染后有视频播发 就得把加速器关闭了
						*/
						XLWebView.this.progress=100;
						invalidate();
					}
				}
			});

		setWebChromeClient(
			new WebChromeClient() 
			{ 
				public void onProgressChanged(WebView view, int progress) 
				{
					//Activity和Webview根据加载程度决定进度条的进度大小 
					//当加载到100%的时候 进度条自动消失 
					//ApiActivity.this.setProgress(progress);
					//progressBar.setVisibility(view.VISIBLE);
					//progressBar.setProgress(progress);
					//progressBar.postInvalidate();
					XLWebView.this.progress=progress;
					//invalidate();
					//if(progress==100)
					//	progressBar.setVisibility(View.GONE);
          //Toast.makeText(context,""+progress,0).show();
				}

			});


		//网页加载完成后再加载图片
		/*
		if(Build.VERSION.SDK_INT >= 19) {
			getSettings().setLoadsImagesAutomatically(true);
    } else {
			getSettings().setLoadsImagesAutomatically(false);
    }
    */
		//提高渲染优先级
		//getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
		//硬件加速
		/*
		if (Build.VERSION.SDK_INT >= 19)
		{
			//硬件加速器的使用
			setLayerType(View.LAYER_TYPE_HARDWARE, null);
		}
		else if(Build.VERSION.SDK_INT>=15)
		{
			setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		*/

		progress=100;
	}
	public XLWebView(android.content.Context context, android.util.AttributeSet attrs) 
	{
		super(context,attrs);
		this.context=context;
		initView();
	}
	public XLWebView(Context context)
	{
		super(context);
		this.context=context;
		initView();
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		//  Implement this method
		super.onDraw(canvas);
		int size=DisplayUtil.dip2px(context,6f);
		if(progress<100)
		{
			canvas.drawRect(getScrollX(),getScrollY(),getScrollX()+getWidth(),getScrollY()+size, paint_back);
			canvas.drawRect(getScrollX(),getScrollY(),getScrollX()+ getWidth()*progress/100,getScrollY()+size,paint_rect);
		}
		//Log.e(TAG,""+progress);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		//  Implement this method
/*
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		*/
		super.onLayout(changed, l, t, r, b);

	}



	//设置进度条颜色
	public void setProgressColor(int color){
		this.paint_rect.setColor(color);
	}
	//设置进度条背景色
	public void setProgressBackgroundColor(int backgroundColor){
		this.paint_back .setColor(backgroundColor);
	}

	@Override
	public void loadUrl(String url)
	{

		
		if(url.startsWith("http")||url.startsWith("https"))
		{
			super.loadUrl(url);
		}
		else if(url.startsWith("file"))
		{
			super.loadUrl(url);
		}
		else
		{
			Intent in = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
			try
			{
			context.startActivity(in);
			}
			catch(Exception e)
			{
				Log.e(TAG,"loadUrl error:"+url);
			}
		}

	}

}
