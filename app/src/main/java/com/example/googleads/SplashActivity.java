package com.example.googleads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.vapp.admoblibrary.ads.AdCallback;
import com.vapp.admoblibrary.ads.AdmodUtils;
import com.vapp.admoblibrary.utils.Utils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        AdmodUtils.getInstance().loadAndShowAdInterstitialWithCallback(this, getString(R.string.test_ads_admob_inter_id), 0, new AdCallback() {
            @Override
            public void onAdClosed() {
                Utils.getInstance().replaceActivity(SplashActivity.this, MainActivity.class);
            }

            @Override
            public void onAdFail() {
                onAdClosed();
            }
        },false);
    }
}