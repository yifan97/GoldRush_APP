package com.example.goldrush;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
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
    String user_name;
    DBHelper mDatabase;
    SharedPreferences prefs;
    SharedPreferences prefs2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.
                Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        mDatabase = new DBHelper(this);

        prefs = getSharedPreferences("account", MODE_PRIVATE);

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
                    user_name = account.getEmail();
                    firebaseAuthWithGoogle(account);
                }
            }catch (ApiException e){
                e.printStackTrace();
            }

            if(resultCode == RESULT_OK){
                Toast.makeText(this, "Welcome! " + user_name, Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("user", user_name);
                editor.commit();

                Intent intent = new Intent(this, HomeTwo.class);
                startActivityForResult(intent, 1);
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Google Login failed", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == 1){
            if(resultCode == RESULT_OK) {
                String higestScore = data.getStringExtra("score");
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
                        Log.d("TEST", "signin success ");
                        FirebaseUser user = mAuth.getCurrentUser();
                    }else{
                        Log.d("TAG", "signin failure", task.getException());
                        Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // When the user clicks a button on the homepage screen
    public void BtnOnClick(View view) {

        // google login
        if (view.getId() == R.id.googlelogin){
            // Configure Google Sign In
            Intent signIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signIntent, GOOGLE_SIGN);
        }
    }

}
