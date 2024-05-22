package com.yuange.fragmework;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.yuange.fragmework.modelbase.base.BaseDaggerActivity;
import com.yuange.fragmework.modelbase.data.YuangeConstant;
import com.yuange.fragmework.modelbase.event.HomeEvent;
import com.yuange.fragmework.modelbase.route.AroutePath;
import com.yuange.fragmework.modelbase.route.IAppService;
import com.yuange.fragmework.modelbase.utils.DeviceUtil;
import com.yuange.fragmework.modelbase.utils.StringUtils;
import com.yuange.fragmework.modelbase.utils.dialog.TwoSelectorDialog;
import com.yuange.fragmework.modelbase.widget.ToastUtil;
import com.yuange.taoke.modulehome.searchprodutlist.SearchProductListActivity;
import com.yuange.taoke.modulemine.common.data.CheckAppEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import pers.loren.appupdate.AppUpdateManager;
import pers.loren.appupdate.interfaces.UpdateDownloadListener;


@Route(path = AroutePath.MAIN_HOME)
public class MainActivity extends BaseDaggerActivity<MainPresenter> {


    BottomNavigationBar mBottomNavigationBar;

    @Autowired
    boolean isFromKnow;

    private IAppService mIAppService;

    private boolean isShowDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ARouter.getInstance().inject(this);
        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottombar_main);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.icon_bottom_shop, "逛街"))
                .addItem(new BottomNavigationItem(R.drawable.icon_bottom_know, "知识"))
                .addItem(new BottomNavigationItem(R.drawable.icon_bottom_friend, "圈圈"))
                .addItem(new BottomNavigationItem(R.drawable.icon_bottom_mine, "我的"))
                .setFirstSelectedPosition(0)
                .initialise();
        Fragment fragment1 = (Fragment) ARouter.getInstance()
                .build(AroutePath.SHOP_HOME)
                .navigation();
        if (isFromKnow) {
            mBottomNavigationBar.
                    setFirstSelectedPosition(1)
                    .initialise();
            fragment1 = (Fragment) ARouter.getInstance()
                    .build(AroutePath.KNOW_HOME)
                    .navigation();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment1).commitAllowingStateLoss();
        initListener();

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE},
                1000);
        mIAppService = ARouter.getInstance().navigation(IAppService.class);
        if (mIAppService != null) {
            mIAppService.getLocationInfo(this, loctionInfo -> {
                YuangeConstant.setLoctionInfo(loctionInfo);
            });
        }
        mPresenter.actApp();
        mPresenter.checkApp();
        EventBus.getDefault().register(this);
        handleCopy();

    }

    private void initListener() {

        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                setScrollable(position);
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });


    }


    private void setScrollable(int position) {
        switch (position) {
            case 0:
                Fragment fragment1 = (Fragment) ARouter.getInstance()
                        .build(AroutePath.SHOP_HOME)
                        .navigation();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment1).commitAllowingStateLoss();
                break;
            case 1:
                Object knowObject = ARouter.getInstance()
                        .build(AroutePath.KNOW_HOME)
                        .navigation();
                if (knowObject == null) return;
                getSupportFragmentManager().beginTransaction().replace
                        (R.id.fragment_container, (Fragment) knowObject).commitAllowingStateLoss();
                break;
            case 2:
                Object friendObject = ARouter.getInstance()
                        .build(AroutePath.FRIEND_HOME)
                        .navigation();
                if (friendObject == null) return;
                getSupportFragmentManager().beginTransaction().replace
                        (R.id.fragment_container, (Fragment) friendObject).commitAllowingStateLoss();
                break;
            case 3:
                Object mineObject = ARouter.getInstance()
                        .build(AroutePath.MINE_HOME)
                        .navigation();
                if (mineObject == null) return;
                getSupportFragmentManager().beginTransaction().replace
                        (R.id.fragment_container, (Fragment) mineObject).commitAllowingStateLoss();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mIAppService == null) {
            return;
        }
        if (requestCode == 1000 && permissions[0] == Manifest.permission.ACCESS_COARSE_LOCATION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mIAppService.getLocationInfo(this, loctionInfo -> {
                YuangeConstant.setLoctionInfo(loctionInfo);
            });
        }
    }


    public void showCheckResult(CheckAppEntity checkAppEntity) {
        if (checkAppEntity == null) return;
        if (TextUtils.isEmpty(checkAppEntity.getAppversion())) {
            return;
        }
        if (!checkAppEntity.getAppversion().equals(DeviceUtil.getVersionName(this))) {
            String msg = checkAppEntity.getUpdateDesc();
            if (TextUtils.isEmpty(msg)) {
                msg = "检测到有新的版本,请下载升级.";
            }
            new AppUpdateManager.Builder()
                    .bind(this) //必须调用
                    .setDownloadUrl(checkAppEntity.getApkurl()) //必须设置
                    .setDownloadListener(new UpdateDownloadListener() { //必须设置
                        @Override
                        public void onDownloading(int process) {
                            if (!isShowDown) {
                                MainActivity.this.runOnUiThread(() -> ToastUtil.showShortToast("正在后台静默更新..."));
                                isShowDown = true;
                            }
                        }

                        @Override
                        public void onDownloadSuccess() {
                        }

                        @Override
                        public void onDownloadFail(String reason) {
                        }
                    })
                    .setUpdateMessage(msg) //自带弹框显示的内容
                    .setShowDialog(true) //是否显示自带的弹框
                    .setForceUpdate(false) //是否显示自带的强制弹框
                    .build();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUpdateManager.unbindDownloadService(this);
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void homeEvent(HomeEvent homeEvent) {
        if (homeEvent != null && homeEvent.type == HomeEvent.HIDE_BOTTOM) {
            mBottomNavigationBar.hide();
        }
        if (homeEvent != null && homeEvent.type == HomeEvent.SHOW_BOTTOM) {
            mBottomNavigationBar.show();
        }
    }

    /**
     * 处理粘贴
     */
    private void handleCopy() {
        String copy = StringUtils.getCopyContent(this);
        if (!TextUtils.isEmpty(copy)) {
            TwoSelectorDialog confirmDialog = new TwoSelectorDialog(this, "猜您想找", copy);
            confirmDialog.setConfirmText("去搜索");
            confirmDialog.setConfirmClickListener(sweetAlertDialog -> {
                SearchProductListActivity.startAction(this, copy);
                confirmDialog.dismiss();
            });
            confirmDialog.setCancelBtnVisible(View.VISIBLE);
            confirmDialog.setCancelClickListener(sweetAlertDialog -> {
                confirmDialog.dismiss();
            });
            confirmDialog.show();
            StringUtils.clearClipboard(this);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
