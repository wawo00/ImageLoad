package com.openup.app3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.openup.app3.design.ImageLoader;

public class ImageLoaderDemo extends AppCompatActivity {
    /**
     * 图片地址集合
     */
    private String imgUrl[] = {
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1072514285,3919411822&fm=26&gp=0.jpg"
    };
    private ImageView showPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader_demo);
        showPic=findViewById(R.id.showPic);
        useImageLoader();
    }

    private void useImageLoader() {
        ImageLoader imageLoader=new ImageLoader();
        imageLoader.setUseDiskCahe(true);
        imageLoader.displayImage(showPic,imgUrl[0]);
    }

}
