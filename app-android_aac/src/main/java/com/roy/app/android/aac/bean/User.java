package com.roy.app.android.aac.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.roy.app.android.aac.BR;

/**
 * @ProjectName: TestApplication
 * @Package: com.roy.app.android.aac.bean
 * @ClassName: User
 * @Description: User
 * @Author: Roy
 * @CreateDate: 2020/5/15 16:51
 */

public class User extends BaseObservable {
    @Bindable
    public String name;
    @Bindable
    public int age;
    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(com.roy.app.android.aac.BR.name);
    }
    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(com.roy.app.android.aac.BR.age);

    }
}
