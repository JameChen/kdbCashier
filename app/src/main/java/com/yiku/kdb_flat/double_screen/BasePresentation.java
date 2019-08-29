package com.yiku.kdb_flat.double_screen;

import android.app.Presentation;
import android.content.Context;
import android.view.Display;
import android.view.View;

import de.greenrobot.event.EventBus;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by jame on 2018/12/8.
 */

public class BasePresentation extends Presentation {
    @Override
    protected void onStart() {
        super.onStart();
        try {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (EventBus.getDefault() != null)
                EventBus.getDefault().unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BasePresentation(Context outerContext, Display display) {
        super(outerContext, display);
    }

    protected CompositeDisposable mCompositeDisposable;

    protected <T extends View> T findKdbViewById(int resId) {
        return (T) findViewById(resId);
    }

    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    public void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    @Override
    public void cancel() {
        try {
            super.cancel();
            unSubscribe();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
