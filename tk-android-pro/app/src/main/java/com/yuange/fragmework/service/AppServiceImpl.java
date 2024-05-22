package com.yuange.fragmework.service;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.yuange.fragmework.modelbase.route.AroutePath;
import com.yuange.fragmework.modelbase.route.IAppService;
import com.yuange.fragmework.modelbase.route.LoctionCallBack;

/**
 * author : yuange
 * date : 2020-02-04 21:03
 * desc :
 */
@Route( path= AroutePath.APP_SERVICE)
public class AppServiceImpl implements IAppService {

    public AMapLocationClient mLocationClient = null;

    @Override
    public void getLocationInfo(Context context, LoctionCallBack callBack) {
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(context);
        }
        AMapLocation aMapLocationInfo=mLocationClient.getLastKnownLocation();
        if(aMapLocationInfo!=null&&callBack!=null&& !TextUtils.isEmpty(aMapLocationInfo.getCity())){
            callBack.callBack(aMapLocationInfo.getProvince() + aMapLocationInfo.getCity());
        }
        mLocationClient.setLocationListener(aMapLocation -> {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mLocationClient.stopLocation();
                if(callBack!=null) {
                    callBack.callBack(aMapLocation.getProvince() + aMapLocation.getCity());
                }
            }
        });
        mLocationClient.startLocation();
    }

    @Override
    public void init(Context context) {

    }
}
