package com.nyklundamade.pickulacka;

import android.content.Intent;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;

import java.util.concurrent.atomic.AtomicBoolean;

import static android.content.ContentValues.TAG;

public class WelcomeActivity extends AppCompatActivity {

    ActivityResultLauncher<IntentSenderRequest> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartIntentSenderForResult(),
            result -> {
                // handle callback
                if (result.getResultCode() != RESULT_OK) {
                    Log.d(TAG, "Nepovedl se update");
                    // If the update is cancelled or fails,
                    // you can request to start the update again.
                    zkontrolujAktualizace();
                } else {
                    Log.d(TAG, "Update se povedl");
                    ukazHlavniAktivitu();
                }
            });

    private ConsentInformation consentInformation;
    // Use an atomic boolean to initialize the Google Mobile Ads SDK and load ads once.
    private final AtomicBoolean isMobileAdsInitializeCalled = new AtomicBoolean(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        ImageView imgvwSpsoa = findViewById(R.id.imageViewLogo);
        Animation animSpsoaLogo = AnimationUtils.loadAnimation(this, R.anim.welcome_sc);
        imgvwSpsoa.startAnimation(animSpsoaLogo); // spuštění samotné animace

        Thread thrdWlcmscrnDelay = new Thread(() -> {
            try {
                Thread.sleep(4000);
                zjistitSouhlasGdpr();
            } catch (InterruptedException e) {
                Log.d(TAG, "Vlákno animace přerušeno: " + e.getMessage());
            }
        });

        thrdWlcmscrnDelay.start();
    }

    private void zkontrolujAktualizace() {
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    appUpdateManager.startUpdateFlowForResult(
                        // Pass the intent that is returned by 'getAppUpdateInfo()'.
                        appUpdateInfo,
                        // an activity result launcher registered via registerForActivityResult
                        activityResultLauncher,
                        // Or pass 'AppUpdateType.FLEXIBLE' to newBuilder() for
                        // flexible updates.
                        AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build());
                    Log.d(TAG, "nalezen update, povolen update");
                } else {
                    ukazHlavniAktivitu();
                    Log.d(TAG, "nalezen update");
                }
            } else {
                ukazHlavniAktivitu();
                Log.d(TAG, "update nenalezen");
            }
        }).addOnFailureListener(e -> {
            ukazHlavniAktivitu();
            Log.d(TAG, "update neúspěšný: " + e.getMessage());
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);

        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                    appUpdateInfo -> {
                        if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                            // If an in-app update is already running, resume the update.
                            appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // an activity result launcher registered via registerForActivityResult
                            activityResultLauncher,
                            // Or pass 'AppUpdateType.FLEXIBLE' to newBuilder() for
                            // flexible updates.
                            AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build());
                        }
                    });
    }

    private void zjistitSouhlasGdpr() {
        // Set tag for under age of consent. false means users are not under age
        // of consent.
        ConsentRequestParameters params = new ConsentRequestParameters
                .Builder()
                .setTagForUnderAgeOfConsent(false)
                .build();

        consentInformation = UserMessagingPlatform.getConsentInformation(this);
        consentInformation.requestConsentInfoUpdate(
                this,
                params,
                () -> UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                        this,
                        loadAndShowError -> {
                            if (loadAndShowError != null) {
                                // Consent gathering failed.
                                Log.w(TAG, String.format("%s: %s",
                                        loadAndShowError.getErrorCode(),
                                        loadAndShowError.getMessage()));
                            }

                            // Consent has been gathered.
                            if (consentInformation.canRequestAds()) {
                                initializeMobileAdsSdk();
                            }
                            zkontrolujAktualizace();
                        }
                ),
                requestConsentError -> {
                    // Consent gathering failed.
                    Log.w(TAG, String.format("%s: %s",
                            requestConsentError.getErrorCode(),
                            requestConsentError.getMessage()));
                    zkontrolujAktualizace();
                });
        // Check if you can initialize the Google Mobile Ads SDK in parallel
        // while checking for new consent information. Consent obtained in
        // the previous session can be used to request ads.
        if (consentInformation.canRequestAds()) {
            initializeMobileAdsSdk();
        }
    }

    private void ukazHlavniAktivitu() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void initializeMobileAdsSdk() {
        if (isMobileAdsInitializeCalled.getAndSet(true)) {
            return;
        }
        // Initialize the Google Mobile Ads SDK.
        MobileAds.initialize(this, initializationStatus -> {
        });
    }
}
