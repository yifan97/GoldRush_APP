package com.example.goldrush;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
    }

    // Check if the input field is a valid email
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // When the user clicks a button on the sign up screen
    public void BtnOnClick(View view) {
        // Sign up button
        if (view.getId() == R.id.signup) {
            EditText email = findViewById(R.id.email);
            EditText password = findViewById(R.id.password);
            TextView invalid = findViewById(R.id.invalid);

            // If the user entered an invalid email
            if (isEmailValid(email.getText().toString()) == false) {
                invalid.setText("Invalid email address");
                invalid.setVisibility(View.VISIBLE);
            }
            else if (password.getText().toString().matches("")) {
                invalid.setText("Invalid password");
                invalid.setVisibility(View.VISIBLE);
            }
        }
    }
}
