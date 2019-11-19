package com.example.goldrush;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        NumberPicker picker = findViewById(R.id.number_picker);

        String[] data = new String[]{"Music One", "Music Two", "Music Three", "Music Four"};
        picker.setMinValue(0);
        picker.setMaxValue(data.length-1);
        picker.setDisplayedValues(data);
    }

    // When the user clicks a button on the setting screen
    public void BtnOnClick(View view) {
        Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
    }
}
