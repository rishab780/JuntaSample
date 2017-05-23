package com.skeleton.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.skeleton.R;
import com.skeleton.database.CommonData;
import com.skeleton.fcm.FCMTokenInterface;
import com.skeleton.fcm.MyFirebaseInstanceIdService;
import com.skeleton.model.Response;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.util.Log;
import com.skeleton.util.Util;
import com.skeleton.util.dialog.CustomAlertDialog;

/**
 * Landing Page of the App
 */
public class SplashActivity extends BaseActivity implements FCMTokenInterface {
    private static final String TAG = SplashActivity.class.getName();
    private Dialog mDialog;
    private String accessToken;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }


    /**
     *
     */
    private synchronized void init() {
        if (!Util.isNetworkAvailable(SplashActivity.this)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            mDialog = new CustomAlertDialog.Builder(SplashActivity.this)
                    .setMessage(R.string.error_internet_not_connected)
                    .setPositiveButton(R.string.text_retry, new CustomAlertDialog.CustomDialogInterface.OnClickListener() {
                        @Override
                        public void onClick() {
                            init();
                        }
                    })
                    .setNegativeButton(getString(R.string.text_exit), new CustomAlertDialog.CustomDialogInterface.OnClickListener() {
                        @Override
                        public void onClick() {
                            finish();
                        }
                    })
                    .show();
            return;
        }
        if (!checkPlayServices()) {
            return;
        }
        MyFirebaseInstanceIdService.setCallback(this);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_SCREEN_OVERLAY) {
            if (Settings.canDrawOverlays(this)) {
                init();
            }
        } else if (requestCode == REQ_CODE_PLAY_SERVICES_RESOLUTION
                && resultCode == Activity.RESULT_OK) {
            init();
        } else if (requestCode == REQ_CODE_OTP && resultCode == RESULT_OK) {
            getAccessToken();

        } else if (requestCode == REQ_CODE_SIGNIN_SIGNUP_ACTIVITY && resultCode == RESULT_OK) {
            getAccessToken();
        } else if (requestCode == REQ_CODE_PROFILE_COMPLETENESS && resultCode == RESULT_OK) {
            getAccessToken();
        }

    }

    /**
     * @return true if google play service present & updated
     * false if not presented or not updated
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, REQ_CODE_PLAY_SERVICES_RESOLUTION)
                        .show();
            } else {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                mDialog = new CustomAlertDialog.Builder(SplashActivity.this)
                        .setMessage(R.string.error_device_not_supported)
                        .setPositiveButton(R.string.text_ok, new CustomAlertDialog.CustomDialogInterface.OnClickListener() {
                            @Override
                            public void onClick() {
                                finish();
                            }
                        })
                        .show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onTokenReceived(final String token) {
        accessToken = CommonData.getAccessToken();
        Log.e(TAG, token);
//        if (accessToken != null) {
//            android.util.Log.d("debug", CommonData.getAccessToken());
//        }
        if (accessToken == null) {
            startActivityForResult(new Intent(this, HomeActivity.class), REQ_CODE_SIGNIN_SIGNUP_ACTIVITY);
        } else {
            getAccessToken();

        }
    }

    public void getAccessToken() {
        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.userProfile("bearer " + accessToken).enqueue(new ResponseResolver<Response>(this, true, true) {
            @Override
            public void success(final Response response) {
                if (!response.getData().getUserDetails().getPhoneVerified()) {
                    Log.d("debug1", "yep");
                    Intent intent = new Intent(SplashActivity.this, OtpActivity.class);
                    startActivityForResult(intent, REQ_CODE_OTP);
                } else {
                    if (response.getData().getUserDetails().getStep1CompleteOrSkip()
                            && response.getData().getUserDetails().getStep2CompleteOrSkip()) {
                        startActivityForResult(new Intent(SplashActivity.this, ProfileActivity.class), REQ_CODE_PROFILE_COMPLETENESS);

                    } else {
                        startActivity(new Intent(SplashActivity.this, SetProfileActivity.class));
                    }
                }


            }

            @Override
            public void failure(final APIError error) {
                Log.d("debug", error.getMessage());

            }
        });
    }


    @Override
    public void onFailure() {
        if (isFinishing()) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /**
                 * in failure make another attempt to get device token
                 * or finish activity which in turn finish application.
                 */
                MyFirebaseInstanceIdService.retry(SplashActivity.this);
            }
        });
    }

    /**
     * @return boolean value of permission overlay
     */
    public boolean checkDrawOverlayPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQ_CODE_SCREEN_OVERLAY);
            return false;
        } else {
            return true;
        }
    }

}
