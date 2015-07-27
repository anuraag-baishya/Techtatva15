package com.appex.tryproject.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appex.tryproject.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import chipset.potato.Potato;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG=MainActivity.class.getSimpleName();
    private CallbackManager mCallbackManager;
    private LoginButton mLoginButton;
    private static final int RC_SIGN_IN = 0;
    private GoogleApiClient mGoogleApiClient;
    private boolean mSignInClicked;
    private ProgressDialog progressDialog;
    /**
     * A flag indicating that a PendingIntent is in progress and prevents us
     * from starting further intents.
     */
    private boolean mIntentInProgress;

    private ConnectionResult mConnectionResult;

    private SignInButton mBtnSignIn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Signing in...");
        progressDialog.setCancelable(false);
        setContentView(R.layout.toolbar_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_r);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        AccessTokenTracker accessTokenTracker=new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken1) {
                updateWithToken(accessToken1);
            }
        };
        updateWithToken(AccessToken.getCurrentAccessToken());
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.primary_dark));
        }
        mCallbackManager = CallbackManager.Factory.create();
        mLoginButton = (LoginButton) findViewById(R.id.fb_loginbutton);
        mLoginButton.setReadPermissions("user_friends");
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                Profile profile = Profile.getCurrentProfile();
                if(profile==null)
                    Log.d(TAG,"null");
                if(profile !=null) {
                    Log.d(TAG, profile.getProfilePictureUri(30, 30).toString());
                    Potato.potate().Preferences().putSharedPreference(getApplicationContext(), "name", profile.getName());
                    Potato.potate().Preferences().putSharedPreference(getApplicationContext(), "mode", "facebook");
                    Potato.potate().Preferences().putSharedPreference(getApplicationContext(), "pp", profile.getProfilePictureUri(50, 50).toString());
                }
                Intent intent = new Intent(getApplicationContext(), EventActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login canceled", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), EventActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(), "Login error", Toast.LENGTH_SHORT).show();
            }
        });
        TextView orText = (TextView) findViewById(R.id.optionView);
        orText.setGravity(Gravity.CENTER);
        Button RegisterButton = (Button) findViewById(R.id.registerActivity_button);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        com.rey.material.widget.Button LoginEmail = (com.rey.material.widget.Button) findViewById(R.id.emailActivity_button);
        LoginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EmailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        mBtnSignIn = (SignInButton) findViewById(R.id.google_loginbutton);

        // Button click listeners
        mBtnSignIn.setOnClickListener(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
                progressDialog.show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
                progressDialog.show();
            }
        }
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConnected(Bundle bundle) {
        mSignInClicked = false;
        progressDialog.dismiss();
        Intent intent = new Intent(MainActivity.this, EventActivity.class);
        Potato.potate().Preferences().putSharedPreference(getApplicationContext(),"name", Plus.PeopleApi.getCurrentPerson(mGoogleApiClient).getDisplayName());
        Potato.potate().Preferences().putSharedPreference(getApplicationContext(),"mode", "google");
        Potato.potate().Preferences().putSharedPreference(getApplicationContext(), "pp", Plus.PeopleApi.getCurrentPerson(mGoogleApiClient).getImage().getUrl());

        startActivity(intent);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google_loginbutton:
                // Signin button clicked
                signInWithGplus();
                break;
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }
    }

    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        }
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
        }
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
    private void updateWithToken(AccessToken currentAccessToken){
        if(currentAccessToken!=null)
            startActivity(new Intent(getApplicationContext(),EventActivity.class));
    }
}