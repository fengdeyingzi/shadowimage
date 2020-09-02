package com.xl.view;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.app.AlertDialog;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


//import com.gmax1.qimopei_new.R;
import com.xl.game.R;
import com.xl.game.tool.ViewTool;

import java.util.List;


public class ProgressDialog {


    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, Menu menu, int deviceId) {
        //  Implement this method
    }

    private TextView text;
    /*
    public ProgressDialog(Context context) {
        super(context);

    }
    */

    public static Dialog show(android.content.Context context, java.lang.CharSequence title, java.lang.CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LinearLayout layout = (LinearLayout) ViewTool.getView(context, R.layout.dlg_progress);
        TextView text = (TextView) layout.findViewById(R.id.progress_text);
        builder.setView(layout);
        if (title != null)
            builder.setTitle(title);
        builder.setCancelable(false);
        if (text != null)
            text.setText(message);
        Dialog dialog = builder.create();
        dialog.show();
        //窗口背景去除
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x0));
        //去除白色背景
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().getDecorView().setBackgroundDrawable(new ColorDrawable(0x0));
        //去除半透明阴影
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.dimAmount = 0.0f;
        dialog.getWindow().setAttributes(layoutParams);
        /*
        如果是fragmentDialgo则获取gialog的Window方法为
        getDialog().getWindow();

        ---------------------
                作者：jingzz1
        来源：CSDN
        原文：https://blog.csdn.net/jingzz1/article/details/86366756
        版权声明：本文为博主原创文章，转载请附上博文链接！
        */
        //dialog.show();
        return dialog;
    }


}
