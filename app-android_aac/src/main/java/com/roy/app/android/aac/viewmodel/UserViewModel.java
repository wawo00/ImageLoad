package com.roy.app.android.aac.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.roy.app.android.aac.bean.User;
import com.roy.app.android.aac.bean.UserLiveData;
import com.roy.app.android.aac.business.UserBusiness;

/**
 * @ProjectName: TestApplication
 * @Package: com.roy.app.android.aac
 * @ClassName: viewmodel
 * @Description: viewmodel
 * @Author: Roy
 * @CreateDate: 2020/5/15 18:14
 */

public class UserViewModel extends AndroidViewModel implements UserBusiness.UserListener {
    private UserBusiness mUserBusiness = UserBusiness.get();
    private UserLiveData mUserLiveData;

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public void observe(LifecycleOwner owner, Observer<User> observer) {
        // 加载数据库缓存中的用户信息
        User user = mUserBusiness.LoadUser();
        mUserLiveData = new UserLiveData();
        // LiveData注册对Activity的监听，同时Activity注册对LiveData的监听
        mUserLiveData.observe(owner, observer);
        // 更新LiveData中的数据
        mUserLiveData.postValue(user);
        mUserBusiness.addListener(this);
    }

    @Override
    public void onRequestUserResult(int code, User user) {
        if (code == 0) {
            // 更新LiveData中的数据
            mUserLiveData.postValue(user);
        } else {
            Toast.makeText(getApplication(), "刷新失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCleared() {
        mUserBusiness.removeListener(this);
        super.onCleared();
    }

    public void refresh() {
        mUserBusiness.requestUser();
    }
}