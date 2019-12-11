package com.example.goldrush;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;

    static String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

//        final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);

        gameView = new GameView(this, point.x, point.y, this);
        user_name = getIntent().getStringExtra("user_name");
        setContentView(gameView);
//
//        Intent intent = new Intent();
//        intent.putExtra("score", prefs.getInt("highscore", 0));
//        setResult(RESULT_OK, intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

}
