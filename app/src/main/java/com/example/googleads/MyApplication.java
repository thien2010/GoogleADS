package com.example.googleads;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.vapp.admoblibrary.AdsMultiDexApplication;
import com.vapp.admoblibrary.ads.AdmodUtils;
import com.vapp.admoblibrary.iap.PurchaseUtils;

/** The Application class that manages AppOpenManager. */
public class MyApplication extends AdsMultiDexApplication {
    boolean isShowAds = true;
    boolean isShowAdsResume = true;

    @Override
    public void onCreate() {
        super.onCreate();

        PurchaseUtils.getInstance().initBilling(this,getString(R.string.play_console_license));
        if (PurchaseUtils.getInstance().isPurchased(getString(R.string.premium))) {
            isShowAds = false;
        }else {
            isShowAds = true;
        }

        AdmodUtils.getInstance().initAdmob(this, 10000, BuildConfig.DEBUG, isShowAds);
        if (isShowAdsResume) {
            com.vapp.admoblibrary.ads.AppOpenManager.getInstance().init(this, getString(R.string.test_ads_admob_app_open));
            com.vapp.admoblibrary.ads.AppOpenManager.getInstance().disableAppResumeWithActivity(SplashActivity.class);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
