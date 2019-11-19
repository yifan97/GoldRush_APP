package com.example.goldrush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Home extends AppCompatActivity {

    MediaPlayer musicPlayer;
    boolean soundOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        musicPlayer = MediaPlayer.create(getApplicationContext(), R.raw.endless_road);
        musicPlayer.start();
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
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
    }
}
