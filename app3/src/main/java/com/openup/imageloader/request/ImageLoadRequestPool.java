package com.openup.imageloader.request;

import com.openup.imageloader.load.LoadManager;
import com.openup.imageloader.utils.LogHelper;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.imageloader.request
 * @ClassName: ImageLoadRequestPool
 * @Description: 使用线程池来实现
 * @Author: Roy
 * @CreateDate: 2020/3/30 16:55
 */

public class ImageLoadRequestPool implements ILoadRequest {
    private ThreadPoolExecutor mExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2);
    // 请求id的生成器
    private AtomicInteger mSerialNumGenerator = new AtomicInteger(0);

    @Override
    public void addRequest(final BitmapRequest request) {
        final String schema = parseSchema(request.getRequestUrl());
        request.setSerialNum(generateSerialNum());
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                LoadManager.getInstance().getLoader(schema).loadBitmap(request);
            }
        });

    }

    @Override
    public void start() {
    }

    @Override
    public void stopDispath() {
        mExecutor.shutdown();
    }

    @Override
    public void clear() {
        mExecutor.shutdownNow();
    }

    private String parseSchema(String uri) {
        if (uri.contains("://")) {
            return uri.split("://")[0];
        } else {
            LogHelper.loge("ImageLoadRequestPool" + "### wrong scheme, image uri is : " + uri);
        }
        return "";
    }

    private final int generateSerialNum() {
        // 为每个请求生成序列号
        return mSerialNumGenerator.incrementAndGet();
    }

}
