package com.xl.view;

import android.view.*;
import android.widget.*;

import java.util.*;

import android.app.Activity;
import android.content.Context;
import android.widget.GridLayout.LayoutParams;

public class ItemAdapter extends BaseAdapter {


    public final class ViewHolder {

        public TextView[] textview; //

    }

    Context context;


    private List<String> mData;

    private LayoutInflater mInflater;

    public ItemAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;

        this.mData = new ArrayList<String>();
    }


    public void onClick(View p1) {
        mData.remove(p1.getId());

        notifyDataSetChanged();

    }

    public void add(String text) {

        mData.add(text);
    }

    public void remove(int pos) {
        mData.remove(pos);
    }

    public void clear() {
        mData.clear();
    }


    public int getCount() {
        return mData.size();
    }

    public Object getItem(int arg0) {
        return mData.get(arg0);
    }


    public long getItemId(int arg0) {
        return arg0;
    }

    //显示按钮
    public void isVisibility() {

    }

    public List<String> getData() {
        return mData;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        //	if (convertView == null)
        {
            holder = new ViewHolder();
            LinearLayout layout = new LinearLayout(context);
            convertView = layout;
            //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
            //convertView.setLayoutParams(params);
            String text = mData.get(position);
            {
                String items[] = text.split(" ");
                for (int i = 0; i < items.length; i++) {
                    TextView textview = new TextView(context);
                    textview.setGravity(Gravity.CENTER);
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f);

                    textview.setLayoutParams(param);
                    layout.addView(textview);
                    textview.setText(items[i]);
                }
            }

            convertView.setTag(holder);
        }


        //holder.img.setOnClickListener(XueyayiActivity.this);
        //holder.img.setId(position);
        return convertView;
    }
}
