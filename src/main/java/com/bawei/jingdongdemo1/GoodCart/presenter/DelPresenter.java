package com.bawei.jingdongdemo1.GoodCart.presenter;

import com.bawei.jingdongdemo1.GoodCart.Beans.MessageBean;
import com.bawei.jingdongdemo1.GoodCart.model.DelModel;
import com.bawei.jingdongdemo1.GoodCart.view.Iview;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by 王利博 on 2018/5/23.
 */

public class DelPresenter implements BasePresenter {
    private Iview iv;
    private DisposableSubscriber subscriber2;

    public void attachView(Iview iv) {
        this.iv = iv;
    }

    public void detachView() {
        if (iv != null) {
            iv = null;
        }

        if (!subscriber2.isDisposed()){
            subscriber2.dispose();
        }
    }

    @Override
    public void getData(String uid,String pid) {
        DelModel model = new DelModel(this);
        model.getData(uid,pid);
    }



    public void delData(Flowable<MessageBean> delFlowable) {
        subscriber2 = delFlowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<MessageBean>() {
                    @Override
                    public void onNext(MessageBean listMessageBean) {
                        if (listMessageBean != null) {
                            iv.delSuccess(listMessageBean);

                        }
                    }



                    @Override
                    public void onError(Throwable t) {
                        iv.onFailed((Exception) t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}