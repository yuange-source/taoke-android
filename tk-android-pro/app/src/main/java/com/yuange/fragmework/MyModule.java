package com.yuange.fragmework;

import com.yuange.fragmework.modelbase.di.scope.FragmentScope;
import com.yuange.moduleknow.KnowModules;
import com.yuange.modulelogin.LoginModule;
import com.yuange.taoke.modulefriend.FriendModules;
import com.yuange.taoke.modulehome.HomeModules;
import com.yuange.taoke.modulemine.MineModules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author : yuange
 * @date : 2019/9/24
 * @desc :
 */
@Module(includes = {
        HomeModules.class,
        KnowModules.class,
        FriendModules.class,
        MineModules.class,
        LoginModule.class})
public abstract class MyModule {


    @FragmentScope
    @ContributesAndroidInjector
    abstract MainActivity mainActivity();



}
