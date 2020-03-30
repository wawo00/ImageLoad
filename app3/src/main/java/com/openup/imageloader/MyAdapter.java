package com.openup.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.openup.app3.R;
import com.openup.imageloader.listener.LoadListener;
import com.openup.imageloader.utils.LogHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.imageloader
 * @ClassName: MyAdapter
 * @Description: MyAdapter
 * @Author: Roy
 * @CreateDate: 2020/3/30 10:35
 */

public class MyAdapter extends BaseAdapter {
    private List<String> datas;
    private Context mContext;

    public MyAdapter(Context context, List<String> datas) {
        this.datas = datas;
        mContext = context;
    }

    @Override
    public int getCount() {
        return datas.size() > 0 ? datas.size() : 0;
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
        convertView = View.inflate(mContext, R.layout.listview_item, null);
        ImageView imageView = convertView.findViewById(R.id.item_iv);
        ImageLoader.getInstance().displayImage(imageView, datas.get(position), new LoadListener() {
            @Override
            public void onComplete(String url, Bitmap bitmap) {
                LogHelper.logi("load complete ---" + url);
            }
        });
        return convertView;
    }
}
