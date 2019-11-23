package com.example.goldrush;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Home extends AppCompatActivity {



    static final int GOOGLE_SIGN = 123;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;


    MediaPlayer musicPlayer;
    boolean soundOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        musicPlayer = MediaPlayer.create(getApplicationContext(), R.raw.endless_road);
        musicPlayer.start();

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
//                        startActivity(new Intent(this, Game.class));
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

            // Turn of the music
            if (soundOn == true) {
                sound.setImageResource(R.drawable.nosound);
                musicPlayer.stop();
                musicPlayer.reset();
                soundOn = false;
            }
            // Plays the music
            else {
                sound.setImageResource(R.drawable.sound);
                musicPlayer = MediaPlayer.create(getApplicationContext(), R.raw.endless_road);
                musicPlayer.start();
                soundOn = true;
            }
        }
        // Setting button
        else if (view.getId() == R.id.setting) {
            Intent intent = new Intent(this, Setting.class);
            startActivity(intent);
        }
        // Login button
        else if (view.getId() == R.id.login) {
//            Intent intent = new Intent(this, Login.class);
//            startActivity(intent);
            startActivity(new Intent(this, GameActivity.class));
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "who is on now: ", Toast.LENGTH_SHORT).show();
        }

        // google login
        else if (view.getId() == R.id.googlelogin){
            // Configure Google Sign In
            Intent signIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signIntent, GOOGLE_SIGN);
        }
    }

}
