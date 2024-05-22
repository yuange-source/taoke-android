package com.yuange.fragmework;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.yuange.fragmework.modelbase.base.BaseApp;
import com.yuange.fragmework.modelbase.utils.LogUtils;
import com.yuange.moduleknow.common.db.KnowDBManager;
import com.yuange.taoke.modulehome.common.db.bean.DBManager;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * @author : yuange
 * @date : 2019/9/25
 * @desc :
 */
public class MyApplication extends BaseApp {


    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            // layout.setPrimaryColorsId(R.color.white, android.R.color.white);//全局设置主题颜色
            return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));
            // 指定为经典Header，默认是 贝塞尔雷达Header
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setDrawableSize(20);
        });
    }


    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.getInstance().init(this);
        KnowDBManager.getInstance().init(this);
        if (BuildConfig.DEBUG) {
            Bugly.init(getApplicationContext(), "3e5445b927", false);
        } else {
            Bugly.init(getApplicationContext(), "0d5d35d982", false);
        }
        if (!BuildConfig.DEBUG) {
            LogUtils.setDebugMode(false);
        }
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }


}
