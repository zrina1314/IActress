package com.sf.iactress.presenter;

import android.content.Context;

import com.sf.iactress.bean.AlbumBean;
import com.sf.iactress.model.AlbumModel;
import com.sf.iactress.ui.view.AlbumView;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 花心大萝卜 on 2016/3/8.
 * 用途：
 * 描述：
 */
public class AlbumPresenter {
    private AlbumView mUserView;
    private AlbumModel mUserModel;
    private Context mContext;

    public AlbumPresenter(Context context, AlbumView mUserView) {
        this.mUserView = mUserView;
        this.mContext = context;
        mUserModel = new AlbumModel();
    }

    public void getUser() {
        mUserView.showProgressDialog();

        // 这里如果使用 Lambda 会更简洁
        mUserModel.getUser(mContext)
                .subscribeOn(Schedulers.io())// 在非UI线程中执行getUser
                .observeOn(AndroidSchedulers.mainThread())// 在UI线程中执行结果
                .subscribe(new Subscriber<List<AlbumBean>>() {
                    @Override
                    public void onNext(List<AlbumBean> beenList) {
                        mUserView.updateView(beenList);
                    }

                    @Override
                    public void onCompleted() {
                        mUserView.hideProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mUserView.showError(e.getMessage());
                        mUserView.hideProgressDialog();
                    }
                });
    }
}
