package com.xl.view;
import android.widget.ImageButton;
import android.widget.Checkable;
import android.graphics.drawable.Drawable;
import android.content.Context;

public class CheckButton extends ImageButton implements Checkable
	{
private boolean isCheck;
private Drawable drawable_check;

public CheckButton(Context context)
{
	super(context);
	setBackgroundDrawable(null);
}
		public CheckButton(android.content.Context context, android.util.AttributeSet attrs)
		{
			super(context,attrs);
			setBackgroundDrawable(null);
		}


		@Override
		public void setChecked(boolean p1)
			{
			this.isCheck=p1;
				if(!isCheck)
					{
						setBackgroundDrawable(null);
					}
				else
					{
						setBackgroundDrawable(drawable_check);
					}
			}

		@Override
		public boolean isChecked()
			{
				
				return isCheck;
			}

		@Override
		public void toggle()
			{
				if(isCheck)
				{
					isCheck=false;
					setBackgroundDrawable(null);
				}
				else
				{
					isCheck=true;
					setBackgroundDrawable(drawable_check);
				}
			}
		
	
	//设置选中后显示的背景图片
	public void setCheckDrawable(Drawable drawable)
	{
		this.drawable_check = drawable;
		setChecked(isCheck);
	}
	
	
	
}
