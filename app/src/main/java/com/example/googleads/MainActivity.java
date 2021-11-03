package com.example.googleads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ads.google.admobads.admobnative.GoogleNativeAdAdapter;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.vapp.admoblibrary.ads.AdCallback;
import com.vapp.admoblibrary.ads.AdmodUtils;
import com.vapp.admoblibrary.ads.NativeAdCallback;
import com.vapp.admoblibrary.ads.RewardAdCallback;
import com.vapp.admoblibrary.ads.admobnative.enumclass.GoogleEBanner;
import com.vapp.admoblibrary.ads.admobnative.enumclass.GoogleENative;
import com.vapp.admoblibrary.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RewardedAd mRewardedAd;
    private final String TAG = "main";
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    Button btn_Interstitial,btn_RewardedAd;
    Button btn_Nativead,btn_rcvNative;
    RecyclerView recyclerView;
    LinearLayout banner ;
    LinearLayout nativeads;
    private long time_one = 0;
    List<ItemModel> modelList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_Interstitial = findViewById(R.id.btn_Interstitial);
        btn_RewardedAd = findViewById(R.id.btn_RewardedAd);
        banner = findViewById(R.id.banner);
        nativeads = findViewById(R.id.nativeads);
        btn_rcvNative = findViewById(R.id.btn_NativeAd_rcv);
        recyclerView = findViewById(R.id.rcv_item);

        modelList.add(new ItemModel("item 1"));
        modelList.add(new ItemModel("item 2"));
        modelList.add(new ItemModel("item 3"));
        modelList.add(new ItemModel("item 4"));
        modelList.add(new ItemModel("item 5"));

//        Button crashButton = new Button(this);
//        crashButton.setText("Test Crash");
//        crashButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                throw new RuntimeException("Test Crash"); // Force a crash
//            }
//        });
//
//        addContentView(crashButton, new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT));
        AdmodUtils.getInstance().loadAdBanner(MainActivity.this, getString(R.string.test_ads_admob_banner_id), banner, GoogleEBanner.SIZE_FULL);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        btn_Interstitial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                long a = System.currentTimeMillis();
//                long b = a - time_one;
//                if (b > 30000){
//                    Interstitial();
//                    CheckTime();
//                }else {
//                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
//                    startActivity(intent);
//                }
                AdmodUtils.getInstance().loadAndShowAdInterstitialWithCallback(MainActivity.this, "google", 10000,
                        new AdCallback() {
                            @Override
                            public void onAdClosed() {
                                //code here
                                 Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                                 startActivity(intent);
                            }

                            @Override
                            public void onAdFail() {
                                //code here
                                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                                startActivity(intent);
                            }
                        }, true);
            }
        });
        btn_RewardedAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdmodUtils.getInstance().loadAndShowAdRewardWithCallback(MainActivity.this, getString(R.string.test_ads_admob_reward_id), new RewardAdCallback() {
                    @Override
                    public void onAdClosed() {
                        //code here
                        Utils.getInstance().showMessenger(MainActivity.this,"Reward");
                    }

                    @Override
                    public void onAdFail() {
                        //code here
                        Utils.getInstance().showMessenger(MainActivity.this,"Reward fail");
                    }

                    @Override
                    public void onEarned() {
                        AdmodUtils.getInstance().dismissAdDialog();
                    }
                }, true);
            }
        });
        btn_rcvNative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false));
                AdapterRecycleView mainAdapter = new AdapterRecycleView(MainActivity.this,modelList );
                GoogleNativeAdAdapter googleNativeAdAdapter = new GoogleNativeAdAdapter(
                        new GoogleNativeAdAdapter.Param(
                                MainActivity.this,
                                mainAdapter,
                                getString(R.string.test_ads_admob_native_id),
                                R.layout.ad_template_medium,
                                2,
                                R.layout.layout_ad,
                                R.id.id_ad));
                recyclerView.setAdapter(googleNativeAdAdapter);
            }
        });
        AdmodUtils.getInstance().loadNativeAds(MainActivity.this, getString(R.string.test_ads_admob_native_id), nativeads, GoogleENative.UNIFIED_SMALL, new NativeAdCallback(){
            @Override
            public void onNativeAdLoaded() {

            }

            @Override
            public void onAdFail() {

            }
        });

        //Banner
//        Banner();

    }
    public void Banner(){
//        AdView adView = new AdView(this);
//        adView.setAdSize(AdSize.FULL_BANNER);
//        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
//        adView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);
//
//        adView.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                // Code to be executed when an ad finishes loading.
//            }
//
//            @Override
//            public void onAdFailedToLoad(LoadAdError adError) {
//                // Code to be executed when an ad request fails.
//            }
//
//            @Override
//            public void onAdOpened() {
//                // Code to be executed when an ad opens an overlay that
//                // covers the screen.
//            }
//
//            @Override
//            public void onAdClicked() {
//                // Code to be executed when the user clicks on an ad.
//            }
//
//            @Override
//            public void onAdClosed() {
//                // Code to be executed when the user is about to return
//                // to the app after tapping on an ad.
//            }
//        });
    }
    public void Interstitial(){
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when fullscreen content is dismissed.
                                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                startActivity(intent);
                                Log.d("TAG", "The ad was dismissed.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when fullscreen content failed to show.
                                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                startActivity(intent);
                                Log.d("TAG", "The ad failed to show.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when fullscreen content is shown.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
                                mInterstitialAd = null;
                                Log.d("TAG", "The ad was shown.");
                            }
                        });
                        if (mInterstitialAd != null) {
                            mInterstitialAd.show(MainActivity.this);
                        } else {
                            Log.d("TAG", "The interstitial ad wasn't ready yet.");
                        }
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                        startActivity(intent);
                        mInterstitialAd = null;
                    }
                });
    }
    public void RewardedAd(){
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.getMessage());
                        mRewardedAd = null;
                    }
                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Activity activityContext = MainActivity.this;
                        mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                            @Override
                            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                // Handle the reward.
                                Log.d(TAG, "The user earned the reward.");
                                int rewardAmount = rewardItem.getAmount();
                                String rewardType = rewardItem.getType();
                            }
                        });
                        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d(TAG, "Ad was shown.");

                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.d(TAG, "Ad failed to show.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d(TAG, "Ad was dismissed.");
                                mRewardedAd = null;
                            }
                        });
                        Log.d(TAG, "Ad was loaded.");
                    }
                });
    }
//    public void NativeAd(){
//        AdLoader adLoader = new AdLoader.Builder(MainActivity.this, "ca-app-pub-3940256099942544/2247696110")
//                .forNativeAd(nativeAd -> {
//                    // Show the ad.
//                    NativeTemplateStyle styles = new
//                            NativeTemplateStyle.Builder().build();
//                    TemplateView template = findViewById(R.id.my_template);
//                    template.setStyles(styles);
//                    template.setNativeAd(nativeAd);
//                })
//                .withAdListener(new AdListener() {
//                    @Override
//                    public void onAdFailedToLoad(LoadAdError adError) {
//                        // Handle the failure by logging, altering the UI, and so on.
//                        Log.e("err",adError+"");
//                    }
//                })
//                .withNativeAdOptions(new NativeAdOptions.Builder()
//                        // Methods in the NativeAdOptions.Builder class can be
//                        // used here to specify individual options settings.
//                        .build())
//                .build();
////        adLoader.loadAd(new AdRequest.Builder().build());
//        adLoader.loadAds(new AdRequest.Builder().build(), 3);
//    }
    public void CheckTime(){
        System.currentTimeMillis();
        time_one = System.currentTimeMillis();
        Log.d("Time", System.currentTimeMillis()+"");
    }
}