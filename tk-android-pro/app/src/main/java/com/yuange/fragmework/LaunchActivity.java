package com.yuange.fragmework;

import android.content.Intent;

import com.yuange.fragmework.modelbase.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * author : yuange
 * date : 2020-02-01 16:37
 * desc :
 */
public class LaunchActivity extends BaseActivity {

    private CompositeDisposable subCompositeDisposable=new CompositeDisposable();

    @Override
    protected void initSomeParams() {
        subCompositeDisposable.add(Observable.timer(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                    finish();
                }));
    }

    @Override
    protected void destroySomeParams() {

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subCompositeDisposable!=null){
            subCompositeDisposable.clear();
        }
    }

}
