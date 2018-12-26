package com.dinpay.demo.mvp.model;


import android.util.Log;

import com.dinpay.demo.ApiReq;
import com.dinpay.demo.DouApplication;
import com.dinpay.demo.bean.NewPic;
import com.dinpay.demo.mvp.contract.HotListContract;
import com.dinpay.demo.utils.SharedUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.dinpay.demo.utils.CommUtil.TAG;


/**
 *
 * M层 (Model具体实现类)
 * @author yzh-t105
 * @time 2018/12/20 10:14
 */
public class HotListModel  implements HotListContract.HotListModelImp{

    int time =0;


    NewPic newPic;//测试
    @Inject
    public HotListModel(NewPic newPic) {
        this.newPic = newPic;
    }

    @Override
    public void requestList(final HotListContract.HotListCallBack callBack,final int page) {
        Log.v("getHotListModel", "getHotListModel---"+newPic.toString());
        // CommUtil.showWaitDialog(application,"加载中",false);
        ApiReq.NewApi api = DouApplication.getInstance().retrofit.create(ApiReq.NewApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("pageSize", "20");
        params.put("pageNum", String.valueOf(page));
        Observable<NewPic> observable = api.getHotList("hotList.html", params);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewPic>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(NewPic value) {

                        Gson gson = new Gson();
                        String s = gson.toJson(value);
                        Log.v(TAG, s);
                        time++;
                        // CommUtil.closeWaitDialog();
                        callBack.ok(value, page);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "请求失败" + e);
                        // CommUtil.closeWaitDialog();
                        if (time < 1) {
                            callBack.ok((NewPic) SharedUtils.getObject("hotList",DouApplication.getInstance()), page);
                        }else{
                            callBack.ok(null, page);
                        }
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "请求成功");
                    }
                });
    }
}
