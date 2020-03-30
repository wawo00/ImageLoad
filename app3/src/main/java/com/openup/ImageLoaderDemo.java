package com.openup;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.openup.app3.R;
import com.openup.imageloader.ImageLoader;
import com.openup.imageloader.cache.DoubleCache;
import com.openup.imageloader.config.DisplayConfig;
import com.openup.imageloader.config.ImageLoaderConfig;
import com.openup.imageloader.strategy.SerialPolicy;
import com.openup.imageloader.utils.LogHelper;

public class ImageLoaderDemo extends AppCompatActivity {
    /**
     * 图片地址集合
     */
    private String imgUrl[] = {
            "http://image.zhangxinxu.com/image/study/s/s256/mm1.jpg",
            "http://image.zhangxinxu.com/image/study/s/s256/mm2.jpg",
            "http://image.zhangxinxu.com/image/study/s/s256/mm3.jpg",
            "http://image.zhangxinxu.com/image/study/s/s256/mm4.jpg",
            "http://image.zhangxinxu.com/image/study/s/s256/mm5.jpg",
            "http://image.zhangxinxu.com/image/study/s/s256/mm6.jpg",
            "http://image.zhangxinxu.com/image/study/s/s256/mm7.jpg"
    };
    private ListView mListView;
    private ImageView showPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader_demo);
        showPic=findViewById(R.id.showPic);
//        mListView=findViewById(R.id.listview);
        useImageLoader();
    }

    private void useImageLoader() {

        ImageLoaderConfig config=new ImageLoaderConfig()
                .setImageLoadingHold(R.drawable.loading)
                .setImageLoadingFail(R.drawable.not_found)
                .setImageCache(new DoubleCache())
                .setThreadCount(4)
                .setLoadSrategy(new SerialPolicy());

//        LogHelper.logi("config is "+config);
        // 初始化
        ImageLoader.getInstance().init(config);
        ImageLoader.getInstance().displayImage(showPic,imgUrl[2]);
//        ImageLoader imageLoader=new ImageLoader();
//        imageLoader.setImageCache(new MyDiskCache());
//        imageLoader.displayImage(showPic,imgUrl[0]);

    }

    @Override
    protected void onStop() {
        super.onStop();
        ImageLoader.getInstance().stop();
    }
}
