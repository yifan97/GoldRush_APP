package com.example.goldrush;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import static com.example.goldrush.Music.ACTION_PAUSE;
import static com.example.goldrush.Music.ACTION_PLAY;

public class Home extends AppCompatActivity {

    static final int GOOGLE_SIGN = 123;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;

    //    MediaPlayer musicPlayer;
    boolean soundOn = true;
    public ImageButton music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Intent intent = new Intent(this, Music.class);
        intent.setAction(ACTION_PLAY);
        startService(intent);

//        musicPlayer = MediaPlayer.create(getApplicationContext(), R.raw.endless_road);
//        musicPlayer.start();
        music = findViewById(R.id.sound);


        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.
                Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode  == GOOGLE_SIGN){
            Task<GoogleSignInAccount> task = GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if(account != null){
                    Toast.makeText(this, "email is: " + account.getEmail(), Toast.LENGTH_SHORT).show();
                    firebaseAuthWithGoogle(account);
                }
            }catch (ApiException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("TAG", "FIREBASEaUTHwithGoogle: " + account.getId());
        AuthCredential credential = GoogleAuthProvider
                .getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task ->{
                    if(task.isSuccessful()){
                        Log.d("TAG", "signin success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        //startActivity(new Intent(this, Game.class));
                        updateUI(true);
                    }else{
                        Log.d("TAG", "signin failure", task.getException());
                        Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUI(boolean b) {
        ImageView googlelogin_view = findViewById(R.id.googlelogin);
        ImageView login_view =  findViewById(R.id.login);
        if (b){
            //sign in, change login to play, change googlelogin to googlelogout
        }else{
            googlelogin_view.setImageResource(R.drawable.googlelogin);
            login_view.setImageResource(R.drawable.loginbtn);
        }
    }

    // When the user clicks a button on the homepage screen
    public void BtnOnClick(View view) {

        // Music button
        if (view.getId() == R.id.sound){
            ImageButton sound = findViewById(R.id.sound);

            // Turn off the music
            if (soundOn == true) {
                sound.setImageResource(R.drawable.nosound);
                Intent intent = new Intent(this, Music.class);
                intent.setAction(ACTION_PAUSE);
                startService(intent);
                soundOn = false;
            }
            // Plays the music
            else {
                sound.setImageResource(R.drawable.sound);
                Intent intent = new Intent(this, Music.class);
                intent.setAction(ACTION_PLAY);
                startService(intent);
                soundOn = true;
            }
        }
        // Setting button
        else if (view.getId() == R.id.setting) {
            Intent intent = new Intent(this, Setting.class);
            startActivity(intent);
        }
        // play button
        else if (view.getId() == R.id.play) {

            startActivity(new Intent(this, GameActivity.class));

            // Firebase sign out
            mAuth.signOut();

            // Google sign out
            mGoogleSignInClient.signOut().addOnCompleteListener(this,
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            return;
                        }
                    });
        }

        // google login
        else if (view.getId() == R.id.googlelogin){
            // Configure Google Sign In
            Intent signIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signIntent, GOOGLE_SIGN);

            ImageButton play = findViewById(R.id.play);
            play.setVisibility(View.VISIBLE);

        }
    }

}
