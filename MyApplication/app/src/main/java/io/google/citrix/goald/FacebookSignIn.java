package io.google.citrix.goald;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import java.net.HttpURLConnection;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import java.util.Arrays;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Text;

import java.io.IOException;


/**
 * Created by ntpin on 5/19/2016.
 */
public class FacebookSignIn extends Activity{

    CallbackManager callbackManager;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_page);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        //loginButton.setReadPermissions(Arrays.asList("public_profile", "user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TAG1", "onSuccess: triggered");
                String myResponse;
                //try{
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String accessToken = loginResult.getAccessToken().getToken();
                String url = "https://goald.herokuapp.com/auth/facebook/token?access_token=" + accessToken ;
                Log.d("TAG1", "url:" + url);

                // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("TAG1", "onResponse: That worked!"+ response);
                                    TextView invisText = (TextView) findViewById(R.id.invisibleTextView);
                                    MainActivity.textViewObj.setText(response.toString());
                                    Log.d("TAG1", "onResponse: Response=" + response);
                                    invisText.setText(response.toString());

                                    Log.d("TAG1", "textView:" + invisText.getText().toString());

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("TAG1", "onErrorResponse: That didn't work!");
                        }
                    });

                Intent intent=new Intent(FacebookSignIn.this,MainActivity.class);
                //TextView tV = (TextView) findViewById(R.id.invisibleTextView);
                //String answer = tV.getText().toString();
                //Log.d("TAG1", "Answer:" + answer);
                //intent.putExtra("response", answer);
                startActivity(intent);
                // Add the request to the RequestQueue.
                queue.add(stringRequest);

            }

            @Override
            public void onCancel() {
                Log.d("TAG1", "onCancel: triggered");
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("TAG1", "onError: triggered");
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
