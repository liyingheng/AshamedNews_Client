package com.paullee.liyingheng.ashamednews;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by liyingheng on 16/7/14.
 */

public class FeaturedAdapter extends BaseAdapter {

    Context mContext;


    public FeaturedAdapter(Context context) {
        super();
        mContext=context;

    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=View.inflate(mContext,R.layout.featured_news_list_item,null);
        return convertView;
    }


}
