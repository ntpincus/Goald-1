package io.google.citrix.goald;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import org.apache.http.client.HttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

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

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TAG", "onSuccess: triggered");
                String myResponse;
                //try{
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String accessToken = loginResult.getAccessToken().getToken();
                String url = "https://goald.herokuapp.com/auth/facebook/token?access_token=" + accessToken ;

// Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("TAG", "onErrorResponse: That worked!"+ response);
                                    //myResponse= response;
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("TAG", "onErrorResponse: That didn't work!");
                        }
                    });
                // Add the request to the RequestQueue.
                queue.add(stringRequest);



                    /*Log.d("TAG", "onSuccess: triggered");
                    String accessToken = loginResult.getAccessToken().getToken();
                    String url = "https://goald.herokuapp.com/auth/facebook/token?access_token=" + accessToken ;
                    Log.d("TAG", "onSuccess: triggered");
                    HttpClient httpclient = new DefaultHttpClient();
                    Log.d("TAG", "onSuccess: triggered");
                    HttpGet httpget= new HttpGet(url);
                    Log.d("TAG", "url:" + url);
                    HttpResponse response = httpclient.execute(httpget);
                    Log.d("TAG", "onSuccess: triggered");
                    if(response.getStatusLine().getStatusCode()== 200){
                        String server_response = EntityUtils.toString(response.getEntity());
                        Log.i("Server response", server_response );
                    } else {
                        Log.i("Server response", "Failed to get server response" );
                    }
                }catch(IOException e){
                    Log.d("TAG", "IOEXCEPTION! on get req");
                }*/
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
