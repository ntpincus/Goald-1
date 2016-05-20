package io.google.citrix.goald;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by ntpin on 5/19/2016.
 */
public class FacebookSignIn extends Activity{
    private LoginButton loginButton;
    CallbackManager callbackManager;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_page);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TAG", "onSuccess: triggered");
            }

            @Override
            public void onCancel() {
                Log.d("TAG", "onCancel: triggered");
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("TAG", "onError: triggered");
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
