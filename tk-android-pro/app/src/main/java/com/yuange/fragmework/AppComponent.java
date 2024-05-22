package com.yuange.fragmework;

import android.app.Application;

import com.yuange.taoke.modulehome.HomeModules;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * @author : yuange
 * @date : 2019/9/24
 * @desc :
 */
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AndroidInjectionModule.class,
        MyModule.class})
public interface AppComponent extends AndroidInjector<MyApplication> {

    @Component.Builder
    interface Builder {
        AppComponent build();
        @BindsInstance
        Builder application(Application application);
    }

}