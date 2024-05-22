# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/Shared/resources/android-sdk-macosx/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontwarn
-ignorewarnings
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Application{*;}
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

## support:appcompat-v7
#-keep public class android.support.v7.widget.** { *; }
#-keep public class android.support.v7.internal.widget.** { *; }
#-keep public class android.support.v7.internal.view.menu.** { *; }
#
#-keep public class * extends android.support.v4.view.ActionProvider {
#    public <init>(android.content.Context);
#}

# support:appcompat-v4
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }
#-keep class android.support.v4.app.** { *; }
#-keep interface android.support.v4.app.** { *; }
#-keep class android.support.v4.** { *; }

# support:appcompat-v7
-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }
#-keep class android.support.v7.internal.** { ; }
#-keep interface android.support.v7.internal.* { ; }

# support design
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.* { *; }
-keep public class android.support.design.R$ { *; }

# 避免混淆泛型
-keepattributes Signature
-keepattributes EnclosingMethod
# 保留Annotation不混淆和内部类不混淆
-keepattributes *Annotation*
-keepattributes InnerClasses


-keepattributes Exceptions
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

-keep class android.support.annotation.Keep

-keep @android.support.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}

######################依赖的第三方库 混淆规则-BEGIN###########################

############################ 框架 ############################
# RXJava
-dontwarn rx.*
-keep class rx.** {*;}
-dontwarn sun.misc.**
-keep class sun.misc.Unsafe { *; }

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

-dontnote rx.internal.util.PlatformDependent

#RXAndroid
-keepclasseswithmembers class rx.android.**{*;}

#okhttp
-dontwarn okhttp3.**
-keep class .okhttp3.** { *;}

#okio
-dontwarn okio.**
-keep class okio.** {*;}

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Exceptions

#RxJavaAdapter
-dontwarn retrofit2.adapter.rxjava.**
-keep class retrofit2.adapter.rxjava.** {*;}

#JavaWriter
-dontwarn com.squareup.javawriter.**
-keep class com.squareup.javawriter.** {*;}

#Rx
-dontwarn retrofit2.converter.gson.**
-keep class retrofit2.converter.gson.** {*;}

# Retrolambda
-dontwarn java.lang.invoke.*

# Gson
-dontwarn com.google.gson.**
-keep class com.google.gson.** { *; }

# RxBinding
-dontwarn com.jakewharton.rxbinding.**
-keep class com.jakewharton.rxbinding.** {*;}

# dagger
-dontwarn dagger.**
-keep class dagger.** {*;}

-keepclassmembers class ** {
    @dagger.Component <methods>;
}
-keepclassmembers class ** {
    @dagger.Module <methods>;
}
-keepclassmembers class ** {
    @dagger.Provides <methods>;
}
-keepclassmembers class ** {
    @dagger.Subcomponent <methods>;
}
-keepclassmembers class ** {
    @dagger.Lazy <methods>;
}
-keepclassmembers class ** {
    @dagger.MembersInjector <methods>;
}
-keepclassmembers class ** {
    @dagger.Reusable <methods>;
}

# ARouter
-dontwarn com.alibaba.android.arouter.**
-keep class com.alibaba.android.arouter.** {*;}
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider
# -keep class * implements com.alibaba.android.arouter.facade.template.IProvider

# EventBus
-dontwarn org.greenrobot.eventbus.**
-keep class org.greenrobot.eventbus.** {*;}
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}


############################ 数据收集 ############################
# bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

############################ 地图 ############################

# 高德地图
-dontwarn com.alibaba.idst.nls.**
-keep class com.alibaba.idst.nls.** {*;}

-dontwarn com.amap.**
-keep class com.amap.**{*;}

-dontwarn com.autonavi.**
-keep class com.autonavi.**{*;}

-dontwarn com.nlspeech.nlscodec.**
-keep class com.nlspeech.nlscodec.** {*;}

############################ 工具 ############################


# FastJson
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** {*;}



# MutilDex
-dontwarn android.support.multidex.**
-keep class android.support.multidex.** {*;}

############################ UI部分 ############################

# glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule


# Sweet Alert Dialog
 -keep class cn.pedant.SweetAlert.Rotate3dAnimation {
    public <init>(...);
 }


 # SmartFresh
 -dontwarn com.scwang.smartrefresh.layout.**
 -keep class com.scwang.smartrefresh.layout.** {*;}


# IconFonts 图标
-keep class .R
-keep class **.R$* {
    <fields>;
}


-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}




######################依赖的第三方库 混淆规则-END###########################

# gson
-dontwarn sun.misc.**
-keep class sun.misc.** { *; }
-keep class com.google.gson.** { *; }



-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable



-keepclassmembers class * extends android.webkit.WebChromeClient {
    public void openFileChooser(...);
}

# glide 的混淆代码
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }

# web
-keep class com.just.agentweb.** {
    *;
}
-dontwarn com.just.agentweb.**
-keepclassmembers class com.just.agentweb.sample.common.AndroidInterface{ *; }

-dontwarn com.squareup.okhttp.**

-dontwarn com.flyco.dialog.**
-keep class com.flyco.dialog.** { *; }

-dontwarn com.lcodecore.tkrefreshlayout.**
-keep class com.lcodecore.tkrefreshlayout.** { *; }


-dontwarn com.ashokvarma.bottomnavigation.**
-keep class com.ashokvarma.bottomnavigation.** { *; }


#定位
-keep class com.amap.api.location.**{*;}
-keep class com.loc.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}

-keep class org.greenrobot.greendao.**{*;}
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

-dontwarn com.yuange.taoke.modulehome.common.data.**
-keep class com.yuange.taoke.modulehome.common.data.** { *; }

-dontwarn com.yuange.moduleknow.common.data.**
-keep class com.yuange.moduleknow.common.data.** { *; }

-dontwarn com.yuange.taoke.modulefriend.home.circle.bean.**
-keep class com.yuange.taoke.modulefriend.home.circle.bean.** { *; }

-dontwarn com.yuange.taoke.modulefriend.common.data.**
-keep class com.yuange.taoke.modulefriend.common.data.** { *; }

-dontwarn com.yuange.taoke.modulemine.common.data.**
-keep class com.yuange.taoke.modulemine.common.data.** { *; }


