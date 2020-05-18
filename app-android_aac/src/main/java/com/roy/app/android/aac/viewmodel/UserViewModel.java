package com.roy.app.android.aac.viewmodel;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.roy.app.android.aac.bean.User;
import com.roy.app.android.aac.bean.UserLiveData;
import com.roy.app.android.aac.business.UserBusiness;
import com.roy.app.android.aac.databinding.ActivityMainBinding;



/**
 * @ProjectName: TestApplication
 * @Package: com.roy.app.android.aac
 * @ClassName: viewmodel
 * @Description: viewmodel
 * @Author: Roy
 * @CreateDate: 2020/5/15 18:14
 */

public class UserViewModel extends AndroidViewModel implements UserBusiness.UserListener,Observer<User> {
    private UserBusiness mUserBusiness = UserBusiness.get();
    private UserLiveData mUserLiveData;
    private User mUser;
    private static String TAG="aac";

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public void observe(LifecycleOwner owner, ActivityMainBinding binding) {
        // 加载数据库缓存中的用户信息
//        User user = mUserBusiness.LoadUser();
//        mUserLiveData = new UserLiveData();
//        // LiveData注册对Activity的监听，同时Activity注册对LiveData的监听
//        mUserLiveData.observe(owner, binding);
//        // 更新LiveData中的数据
//        mUserLiveData.postValue(user);
//        mUserBusiness.addListener(this);
        mUser = mUserBusiness.LoadUser();
        binding.setUser(mUser);
        binding.setHost(this);

        mUserLiveData=new UserLiveData();
        // LiveData注册对Activity的监听，同时Activity注册对LiveData的监听
        mUserLiveData.observe(owner, this);
        // 更新LiveData中的数据
//        mUserLiveData.postValue(mUser);
        mUserBusiness.addListener(this);
    }

    @Override
    public void onRequestUserResult(int code, User user) {
        if (code == 0) {
            Log.i(TAG, "onRequestUserResult: "+code);
            // 更新LiveData中的数据
            mUserLiveData.postValue(user);
        } else {
            Log.i(TAG, "onRequestUserResult: 刷新失败 ");
//            Toast.makeText(getApplication(), "刷新失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCleared() {
        mUserBusiness.removeListener(this);
        super.onCleared();
    }

    public void onRefresh(View v) {
        Log.i(TAG, "onRefresh: ");
        mUserBusiness.requestUser();
    }

    @Override
    public void onChanged(User user) {
        Log.i(TAG, "onChanged: user "+user.toString());
        mUser.setName(user.name);
        mUser.setAge(user.age);
    }
}