package com.oraro.genealogy.data.http;

import android.util.Log;

import com.google.gson.Gson;
import com.oraro.genealogy.constant.Constant;
import com.oraro.genealogy.data.api.HttpService;
import com.oraro.genealogy.data.entity.Decision;
import com.oraro.genealogy.data.entity.User;
import com.oraro.genealogy.data.retrofit.ApiException;
import com.oraro.genealogy.data.retrofit.RetrofitUtils;
import com.oraro.genealogy.util.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/10.
 */
public class HttpManager extends RetrofitUtils {
    private String TAG = this.getClass().getSimpleName();

    protected static final HttpService mService = getRetrofit().create(HttpService.class);

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpManager INSTANCE = new HttpManager();
    }

    //获取单例
    public static HttpManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void doUserLogin(String username, String password, String deviceId, String appVersion, Observer<User> observer) {
        Observable observable = mService.login("login", username, password, deviceId, appVersion).map((Func1) new HttpResultFunc<HttpResult<User>>());
        setSubscribe(observable, observer);
    }

    public void doFogotPassword(String idCard, String phoneNum, String newPassword, Observer<Object> observer) {
        Observable observable = mService.resetPassword("resetPassword", idCard, phoneNum, newPassword).map((Func1) new HttpResultFunc<HttpResult<Object>>());
        setSubscribe(observable, observer);
    }

    public void uploadLockPattern(String lockPatternString, Observer<Object> observer) {
        User user = Utils.getCurrentUser();
        if (user == null) {
            observer.onError(new ApiException("No user logined", -1));
            return;
        }
        Log.i(TAG, user.getGenealogyName());
        Observable observable = mService.uploadLockPattern(user.getToken(), user.getUserId(), lockPatternString).map((Func1) new HttpResultFunc<HttpResult<Object>>());
        setSubscribe(observable, observer);
    }

    public void checkLockPattern(String lockPatternString, Observer<Object> observer) {
        User user = Utils.getCurrentUser();
        if (user == null) {
            observer.onError(new ApiException("No user logined", -1));
            return;
        }
        Log.i(TAG, user.getGenealogyName());
        Observable observable = mService.checkLockPattern(user.getToken(), user.getUserId(), lockPatternString).map((Func1) new HttpResultFunc<HttpResult<Object>>());
        setSubscribe(observable, observer);
    }

    public void submitVote(Decision decision, Map<String, String> optionPictrue, Observer<Object> observer) {
        User user = Utils.getCurrentUser();
        if (user == null) {
            observer.onError(new ApiException("No user logined", -1));
            return;
        }
        Log.i(TAG, "token=" + user.getToken() + ",userid=" + user.getUserId() + ",,id=" + user.getId());
        Map<String, RequestBody> bodyMap = new HashMap<>();
        for (int i = 0; i < decision.getOptionText().size(); i++) {
            if (optionPictrue.containsKey(Integer.toString(i))) {
                File file = new File(optionPictrue.get(Integer.toString(i)));
                RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
                bodyMap.put(Integer.toString(i) + "\"; filename=\"" + file.getName(), body);//文件头描述
            }
        }
        String gsonResult = new String();
        Gson gson = new Gson();
        gsonResult = gson.toJson(decision);
        Log.i(TAG, "gsonResult=" + gsonResult);
        Observable observable = mService.submitVote(user.getToken(), user.getUserId(), Constant.FAMILYDECISION, RequestBody.create(MediaType.parse("text/plain"), gsonResult), bodyMap).map((Func1) new HttpResultFunc<HttpResult<Object>>());
        setSubscribe(observable, observer);
    }

    public void requestVoteVerifyList(int page, int rows, Observer<List<Decision>> observer) {
        User user = Utils.getCurrentUser();
        if (user == null) {
            observer.onError(new ApiException("No user logined", -1));
            return;
        }
        Observable observable = mService.requestVoteVerifyList(user.getToken(), user.getUserId(), page, rows).map((Func1) new HttpResultFunc<HttpResult<List<Decision>>>());
        setSubscribe(observable, observer);
    }


    public void requestVoteVerifyDetail(int formId, Observer<Decision> observer) {
        User user = Utils.getCurrentUser();
        if (user == null) {
            observer.onError(new ApiException("No user logined", -1));
            return;
        }
        Observable observable = mService.requestVoteVerifyDetail(user.getToken(), user.getUserId(), formId).map((Func1) new HttpResultFunc<HttpResult<Decision>>());
        setSubscribe(observable, observer);
    }

    public void verifyVote(int formId, String reviewedStatus, Observer<Decision> observer) {
        User user = Utils.getCurrentUser();
        Log.i(TAG, "verifyVote");
        if (user == null) {
            observer.onError(new ApiException("No user logined", -1));
            return;
        }
        Observable observable = mService.verifyVote(user.getToken(), user.getUserId(), formId, Constant.FAMILYDECISION, reviewedStatus)
                .map((Func1) new HttpResultFunc<HttpResult<Decision>>());
        setSubscribe(observable, observer);
    }

    public void requestVoteGroup(int groupType, int page, int rows, Observer<List<User>> observer) {
        User user = Utils.getCurrentUser();
        Log.i(TAG, "requestVoteGroup");
        if (user == null) {
            observer.onError(new ApiException("No user logined", -1));
            return;
        }
        Observable observable = mService.requestVoteGroup(user.getToken(), user.getUserId(), groupType, page, rows)
                .map((Func1) new HttpResultFunc<HttpResult<List<User>>>());
        setSubscribe(observable, observer);
    }

    /**
     * 插入观察者
     *
     * @param observable
     * @param observer
     * @param <T>
     */
    public static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            Log.e(TAG, "httpResult..." + httpResult);
            if (httpResult.getResult() != 1) {
                throw new ApiException(httpResult);
            }
            return httpResult.getData();
        }
    }

}
