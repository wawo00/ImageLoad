package com.openup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.openup.app3.R;
import com.openup.imageloader.ImageLoader;
import com.openup.imageloader.MyAdapter;
import com.openup.imageloader.cache.DoubleCache;
import com.openup.imageloader.cache.MyDiskCache;
import com.openup.imageloader.cache.MyLruCache;
import com.openup.imageloader.config.ImageLoaderConfig;
import com.openup.imageloader.listener.LoadListener;
import com.openup.imageloader.strategy.ReverseStrategy;
import com.openup.imageloader.strategy.SerialPolicy;
import com.openup.imageloader.utils.LogHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.REQUEST_INSTALL_PACKAGES;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ImageLoaderDemo extends AppCompatActivity implements LoadListener {
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
    private ImageView showPic1,showPic2,showPic3;
    private MyAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader_demo);
        showPic1=findViewById(R.id.showPic1);
        showPic2=findViewById(R.id.showPic2);
        showPic3=findViewById(R.id.showPic3);
//        showPic=findViewById(R.id.showPic);
        // 获得extendsd卡读取权限
        getAccess();
        mListView=findViewById(R.id.listview);
        useImageLoader();
//        mAdapter=new MyAdapter(this, Arrays.asList(imgUrl));
//        mListView.setAdapter(mAdapter);
    }

    private void getAccess() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, REQUEST_INSTALL_PACKAGES) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_PHONE_STATE, READ_CONTACTS, ACCESS_FINE_LOCATION, REQUEST_INSTALL_PACKAGES}, 001);
        }

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
        ImageLoader.getInstance().displayImage(showPic1,imgUrl[0],this);
        ImageLoader.getInstance().displayImage(showPic2,imgUrl[1],this);
        ImageLoader.getInstance().displayImage(showPic3,imgUrl[2],this);


        //        ImageLoader imageLoader=new ImageLoader();
//        imageLoader.setImageCache(new MyDiskCache());
//        imageLoader.displayImage(showPic,imgUrl[0]);

    }

    @Override
    protected void onStop() {
        super.onStop();
//        ImageLoader.getInstance().stop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onComplete(String url, Bitmap bitmap) {
        LogHelper.logi("loadComplete -- "+url);
    }
}
