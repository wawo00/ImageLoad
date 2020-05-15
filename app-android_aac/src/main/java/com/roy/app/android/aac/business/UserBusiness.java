package com.roy.app.android.aac.business;

import android.content.Context;
import android.database.Cursor;
import android.nfc.cardemulation.OffHostApduService;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.roy.app.android.aac.MainActivity;
import com.roy.app.android.aac.bean.User;
import com.roy.app.android.aac.utils.DbUtils;
import com.roy.app.android.aac.utils.HttpUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @ProjectName: TestApplication
 * @Package: com.roy.app.android.aac.business
 * @ClassName: UserBusiness
 * @Description: UserBusiness
 * @Author: Roy
 * @CreateDate: 2020/5/15 17:03
 */

public class UserBusiness {

    private static UserBusiness sInstance = new UserBusiness();
    private List<UserListener> mListeners = new LinkedList<>();

    private UserBusiness() {
    }

    public static UserBusiness get() {
        return sInstance;
    }

    public interface UserListener {
        void onRequestUserResult(int code, User user);
    }

    public synchronized void addListener(UserListener listener) {
        if (listener == null) {
            return;
        }
        synchronized (mListeners) {
            if (!mListeners.contains(listener)) {
                mListeners.add(listener);
            }
        }
    }

    public synchronized void removeListener(UserListener listener) {
        if (listener == null) {
            return;
        }
        synchronized (mListeners) {
            mListeners.remove(listener);
        }

    }

    private void notifyRequestUser(int code, User user) {
        List<UserListener> listeners = new LinkedList<>();
        synchronized (mListeners) {
            listeners.addAll(mListeners);
        }
        for (UserListener listener : listeners) {
            listener.onRequestUserResult(code, user);
        }
    }

    public User LoadUser() {
        // 这里本应从cursor中获取数据，但为求程序尽量简单，我们直接使用模拟数据。之所以要加入query这段代码，是为了尽可能模拟真实的流程。
        Cursor cursor = DbUtils.query(null);
        if (cursor != null) {
            try {

            } catch (Exception e) {

            } finally {
                cursor.close();
            }
        }
        User user = new User();
        user.name = "小虾米";
        user.age = 19;
        return user;
    }

    public void requestUser() {
        HttpUtils.request(new HttpUtils.ResponseCallback() {
            @Override
            public void onResponseSuccessed(String json) {
                User user = null;
                try {
                    user = new Gson().fromJson(json, User.class);
                } catch (Exception e) {
                }
                if (user != null) {
                    String updateSql = null;
                    DbUtils.update(updateSql);
                    notifyRequestUser(0, user);
                } else {
                    notifyRequestUser(1, null);
                }
            }

            @Override
            public void onResponseFailed(int reason) {
                notifyRequestUser(reason, null);
            }
        });
    }
}
