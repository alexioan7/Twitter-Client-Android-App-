package com.alexandros.mytwitterlogin.Activities;

import android.content.Intent;

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



    TextView userIdTextView;
    TextView userNameTextView;

    Button downloadButton;
    String accessToken;
    String accessTokenSecret;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);



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
                                        //casting to make AuthCredential OAuthCredential
                                        OAuthCredential oAuthCredential = (OAuthCredential) authResult.getCredential();
                                        oAuthCredential.getAccessToken();

                                        // User is signed in.
                                        // IdP data available in
                                        authResult.getAdditionalUserInfo().getProfile();
                                        // The OAuth access token can also be retrieved:
                                        //authResult.getCredential().getAccessToken();
                                        // The OAuth secret can be retrieved by calling:
                                        //authResult.getCredential().getSecret();
                                        oAuthCredential.getSecret();


                                        userIdTextView = (TextView) findViewById(R.id.userIdTextView);
                                        userIdTextView.setText(authResult.getUser().getUid().toString());
                                        userNameTextView = (TextView) findViewById(R.id.userNameTextView);
                                        userNameTextView.setText(authResult.getUser().getDisplayName().toString());


                                        loggedInTwitterUserScreenName = authResult.getAdditionalUserInfo().getUsername();



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
            // See below.
            OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");

            firebaseAuth
                    .startActivityForSignInWithProvider(/* activity= */ this, provider.build())
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    //casting to make AuthCredential OAuthCredential
                                    OAuthCredential oAuthCredential = (OAuthCredential) authResult.getCredential();
                                    oAuthCredential.getAccessToken();
                                    // User is signed in.
                                    // IdP data available in
                                    authResult.getAdditionalUserInfo().getProfile();
                                    // The OAuth access token can also be retrieved:
                                    //authResult.getCredential().getAccessToken();

                                    // The OAuth secret can be retrieved by calling:
                                    // authResult.getCredential().getSecret().
                                    oAuthCredential.getSecret();
                                    try {
                                        userIdTextView = (TextView) findViewById(R.id.userIdTextView);
                                        userIdTextView.setText(authResult.getUser().getUid().toString());
                                        userNameTextView = (TextView) findViewById(R.id.userNameTextView);
                                        userNameTextView.setText(authResult.getUser().getDisplayName().toString());


                                        loggedInTwitterUserScreenName = authResult.getAdditionalUserInfo().getUsername();


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



        //function for opening the downloadActivity
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDownloadActivity();
                finish();
            }
        });

    }





    public void signOutFunction(View view){

        Log.i("button pressed", "Ok");
        try {

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
