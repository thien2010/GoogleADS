package com.example.googleads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.vapp.admoblibrary.ads.AdmodUtils;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        if (AdmodUtils.getInstance().dialog != null) {
            if (AdmodUtils.getInstance().dialog.isShowing()) {
                AdmodUtils.getInstance().dialog.dismiss();
            }
        }
    }
}