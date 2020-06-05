package com.openup.testlib;

import android.content.Context;
import android.util.Log;

import com.ms.sdk.MsSDK;
import com.ms.sdk.listener.MsSdkInitializationListener;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.testlib
 * @ClassName: TestLocalMaven
 * @Description:用于测试将module上传到本地仓库
 * @Author: Roy
 * @CreateDate: 2020/4/2 17:47
 */

public class TestLocalMaven {
      public static void printLocalMaven(String content, Context context){
          Log.e("roy", "printLocalMaven: "+content );
          MsSDK.init(context, new MsSdkInitializationListener() {
              @Override
              public void onInitializationFinished() {

              }
          });

      }


}

