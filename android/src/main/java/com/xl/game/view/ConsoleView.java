package com.xl.game.view;

/*
运行linux命令的View控件
只能运行 不能输入
可监听关闭事件

*/
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xl.game.tool.DisplayUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleView extends ScrollView implements Console.ConsoleCallback
{

    //
    @Override
    public void onReadData(Console console, byte[] data, int len, boolean isError)
    {
        //  Implement this method
        String text = new String(data,0,len);

        textView.append(highlight( text,"error|warning|\"code\"|Connect|Socket"));
        if(textView.length()>1024*100){
            textView.setText("");
        }
        post(new Runnable() {
            @Override
            public void run() {
                fullScroll(View.FOCUS_DOWN);
            }
        });

    }



    /**
     * @param target 需要高亮的文字
     */
    public SpannableStringBuilder highlight(String text,String target){
        //String temp=getText().toString();
        if(text==null) return null;
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        CharacterStyle span=null;
        Pattern p = Pattern.compile(target);
        Matcher m = p.matcher(text);
        while (m.find()) {
            span = new ForegroundColorSpan(Color.RED);
//span = new ImageSpan(drawable,ImageSpan.XX);
//设置图片
            spannable.setSpan(span, m.start(), m.end(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        //setText(spannable);
        return spannable;
    }

    @Override
    public void onConsoleClosed(Console console)
    {
        //  Implement this method
        if(onexit!=null)onexit.onExit(this);
    }

    private Terminal term;
    private TextView textView;
    private OnExit onexit;

    public ConsoleView(android.content.Context context)
    {
        super(context);
        initView();
    }

    public ConsoleView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context,attrs);
        initView();
    }

    private void initView()
    {
        Context context = getContext();
        //初始化布局
        textView = new TextView(context);
        textView.setTextColor(0xfff0f0f0);

        textView.setTextIsSelectable(true);
        int padding = DisplayUtil.dip2px(context,8);
        textView.setPadding(padding,padding,padding,padding);
        LinearLayout layout = new LinearLayout(context);
        layout.addView(textView,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        addView(layout,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        term = new Terminal(getHandler(),this, context);
    }

    //设置linux命令
    public void setShell(String shell)
    {
        term.execute(shell);
    }


    //设置完成监听
    public void setOnExit(OnExit onexit)
    {
        this.onexit = onexit;
    }

    public interface OnExit
    {
        public void onExit(ConsoleView view);
    }

}
