package com.alexandros.mytwitterlogin.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.alexandros.mytwitterlogin.Activities.DownloadActivity;
import com.alexandros.mytwitterlogin.Activities.MainActivity;
import com.alexandros.mytwitterlogin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthCredential;
import com.google.firebase.auth.OAuthProvider;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    String loggedInTwitterUserScreenName;
    String userName;

    TextView userIdTextView;
    TextView userNameTextView;

    Button downloadButton;
    Button signOutButton;

    String accessToken;
    String accessTokenSecret;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        sharedPreferences = getBaseContext().getSharedPreferences(getPackageName(), Activity.MODE_PRIVATE);

        Task<AuthResult> pendingResultTask = firebaseAuth.getPendingAuthResult();
        firebaseAuth.getPendingAuthResult();

        if(pendingResultTask !=null){
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    try{
                                        OAuthCredential oAuthCredential = (OAuthCredential) authResult.getCredential();
                                        oAuthCredential.getAccessToken();
                                        authResult.getAdditionalUserInfo().getProfile();
                                        oAuthCredential.getSecret();

                                        userIdTextView = (TextView) findViewById(R.id.userIdTextView);
                                        userIdTextView.setText(authResult.getUser().getUid().toString());
                                        userNameTextView = (TextView) findViewById(R.id.userNameTextView);
                                        userNameTextView.setText(authResult.getUser().getDisplayName().toString());
                                        loggedInTwitterUserScreenName = authResult.getAdditionalUserInfo().getUsername();
                                        sharedPreferences.edit().putString("user",loggedInTwitterUserScreenName).apply();
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle failure.
                                    e.printStackTrace();
                                }
                            });
        } else

        {
            // There's no pending result so you need to start the sign-in flow.
            OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");
            firebaseAuth
                    .startActivityForSignInWithProvider(/* activity= */ this, provider.build())
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    OAuthCredential oAuthCredential = (OAuthCredential) authResult.getCredential();
                                    oAuthCredential.getAccessToken();
                                    authResult.getAdditionalUserInfo().getProfile();
                                    oAuthCredential.getSecret();
                                    try {
                                        userIdTextView = (TextView) findViewById(R.id.userIdTextView);
                                        userIdTextView.setText(authResult.getUser().getUid().toString());
                                        userNameTextView = (TextView) findViewById(R.id.userNameTextView);
                                        userNameTextView.setText(authResult.getUser().getDisplayName().toString());
                                        loggedInTwitterUserScreenName = authResult.getAdditionalUserInfo().getUsername();
                                        sharedPreferences.edit().putString("user",loggedInTwitterUserScreenName).apply();
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    try {
                                        accessToken = oAuthCredential.getAccessToken().toString();
                                        accessTokenSecret = oAuthCredential.getSecret().toString();
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    Log.i("Access token", oAuthCredential.getAccessToken().toString());
                                    Log.i("Secret Token", oAuthCredential.getSecret().toString());
                                    Log.i("twitter_screen_name: ", loggedInTwitterUserScreenName);
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle failure.
                                    e.printStackTrace();
                                }
                            });
        }
        // Here ends the onCreate method!
}


//    @Override
    protected void onStart(){
        super.onStart();
        downloadButton = findViewById(R.id.downloadButton);
        signOutButton = findViewById(R.id.signOutButton);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDownloadActivity();
                //finish();
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

    }





    public void signOutFunction(View view){
        Log.i("button pressed", "Ok");
        try {
            sharedPreferences.edit().clear().apply();
            firebaseAuth.signOut();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (firebaseAuth.getCurrentUser() == null){
            Log.i("Logged Out", "log out success");
        }else{
            Log.i("Logged Out", "problem login out");
        }
        openMainActivity();
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void openDownloadActivity(){
        Intent downloadIntent = new Intent(this, DownloadActivity.class);
        downloadIntent.putExtra("accessToken", accessToken);
        downloadIntent.putExtra("accessTokenSecret", accessTokenSecret);
        downloadIntent.putExtra("loggedInTwitterUserScreenName", loggedInTwitterUserScreenName);
        startActivity(downloadIntent);
    }

 // Here ends the main method!!
}
