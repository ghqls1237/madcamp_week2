package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth ;
    CallbackManager callbackManager;

    String[] info_list = {"email", "public_profile"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
        if(auth.getCurrentUser() != null){
            //Login state
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            //should login
            System.out.println("you should login first!!");
        }
        Button facebookBtn = findViewById(R.id.facebook_login_btn);
        facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookLogin();
            }
        });

    }

    private void facebookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(info_list));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Success", "Facebook Login Success");
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("Cancel", "Facebook Login Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Error", "Facebook Login Error");
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token){
        Log.d("Token", "handleFacebookAccessToken : " + token.toString());
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        Log.d("Credential", "Credential is " + credential);
        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Login success
                    Log.d("Success", "signInWithCredential:Success");
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    //Login Fail
                    Log.d("Failure", "signInWithCredential:Failure : " + task);
                    Toast.makeText(LoginActivity.this, "Login fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 페이스북 콜백 등록
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void logout(){
        auth.signOut();
    }



}
