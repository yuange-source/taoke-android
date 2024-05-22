package com.yuange.fragmework;

import com.alibaba.fastjson.JSON;
import com.yuange.fragmework.modelbase.auth.LoginAuthUtils;
import com.yuange.fragmework.modelbase.base.BasePresenter;
import com.yuange.fragmework.modelbase.data.YuangeConstant;
import com.yuange.fragmework.modelbase.net.NetRequestCallBack;
import com.yuange.fragmework.modelbase.net.NetRequestType;
import com.yuange.fragmework.modelbase.net.NetRequestUtils;
import com.yuange.fragmework.modelbase.utils.DeviceUtil;
import com.yuange.taoke.modulemine.common.api.MineApi;
import com.yuange.taoke.modulemine.common.data.CheckAppEntity;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * author : yuange
 * date : 2020-03-15 18:03
 * desc :
 */
public class MainPresenter extends BasePresenter<MainActivity> {

    @Inject
    public MainPresenter(MainActivity mView) {
        super(mView);
    }

    public void checkApp() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("channel", DeviceUtil.getMetaData(getView().getApplicationContext(), "tk_key"));
        NetRequestUtils.getInstance().request(MineApi.GET_CHECKAPP, NetRequestType.POST, hashMap, new NetRequestCallBack() {
            @Override
            public void start() {

            }

            @Override
            public void success(Object data, int code) {
                if (getView() == null || data == null) {
                    return;
                }
                CheckAppEntity checkAppEntity = JSON.parseObject(data.toString(), CheckAppEntity.class);
                getView().showCheckResult(checkAppEntity);
            }

            @Override
            public void failed(Throwable throwable) {

            }

            @Override
            public void end() {
            }
        });
    }

    public void actApp() {
        if (getView() == null) return;
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("channel", DeviceUtil.getMetaData(getView().getApplicationContext(), "tk_key"));
        hashMap.put("username", LoginAuthUtils.getUserName());
        hashMap.put("city", YuangeConstant.getLoctionInfo());
        hashMap.put("imei", DeviceUtil.getDeviceId(getView()));
        hashMap.put("phonemodel", DeviceUtil.getPhoneModel());
        hashMap.put("sysversion", DeviceUtil.getBuildVersion());
        hashMap.put("appversion", DeviceUtil.getVersionName(getView()));
        NetRequestUtils.getInstance().request(MineApi.SAVE_ACTIV_APP, NetRequestType.POST, hashMap, new NetRequestCallBack() {
            @Override
            public void start() {

            }

            @Override
            public void success(Object data, int code) {
            }

            @Override
            public void failed(Throwable throwable) {
            }

            @Override
            public void end() {
            }
        });
    }

}
