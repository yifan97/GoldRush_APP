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
import static com.example.goldrush.Music.ACTION_CHANGE;

public class Setting extends AppCompatActivity {

    NumberPicker picker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        picker = findViewById(R.id.number_picker);

        String[] data = new String[]{"Summer", "Egypt", "Nyan Cat", "China"};
        picker.setMinValue(0);
        picker.setMaxValue(data.length-1);
        picker.setDisplayedValues(data);
    }

    // When the user clicks a button on the setting screen
    public void BtnOnClick(View view) {
        //Toast.makeText(getApplicationContext(), "Saved",Toast.LENGTH_SHORT).show();

        String strings[] = picker.getDisplayedValues();
        String music = strings[picker.getValue()];

        Intent intent = new Intent(this, Music.class);
        intent.setAction(ACTION_CHANGE);
        intent.putExtra("changemusic", music);
        startService(intent);
    }


}
