package com.example.goldrush;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;
import androidx.annotation.Nullable;

import java.io.IOException;

public class Music extends Service implements MediaPlayer.OnPreparedListener {
    // Actions associated with Intents that start this Service
    public static final String ACTION_PLAY = "com.example.goldrush.PLAY";
    public static final String ACTION_PAUSE = "com.example.goldrush.PAUSE";
    public static final String ACTION_STOP = "com.example.goldrush.STOP";
    public static final String ACTION_CHANGE = "com.example.goldrush.CHANGE";
    public static final String ACTION_COLLECT = "com.example.goldrush.COLLECT";

    // Channel for notifications.
    private static final String CHANNEL_NAME = "com.example.goldrush.CHANNEL";

    private MediaPlayer mMediaPlayer;
    private boolean mPrepared = false;

    public String changeMusic = "";

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_NAME,
                    "Music", NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }
    }

    private Uri resourceToUri(int resId) {
        Resources res = getResources();
        return new Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(res.getResourcePackageName(resId))
                .appendPath(res.getResourceTypeName(resId))
                .appendPath(res.getResourceEntryName(resId)).build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction() == ACTION_PLAY) {
            play(changeMusic);
        }
        else if (intent.getAction() == ACTION_PAUSE) {
            pause();
        }
        else if (intent.getAction() == ACTION_STOP) {
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mPrepared = false;
                stopForeground(true);
            }
        }
        else if (intent.getAction() == ACTION_CHANGE) {
            changeMusic = intent.getStringExtra("changemusic");
            mMediaPlayer.stop();
            mMediaPlayer = null;
            play(changeMusic);
        }
        else if (intent.getAction() == ACTION_COLLECT) {
            playSoundEffect();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void pause() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            } else if (mPrepared) {
                mMediaPlayer.start();
            }
        }
    }

    private void play(String music) {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setVolume(0.2f, 0.2f);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes attributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build();
                mMediaPlayer.setAudioAttributes(attributes);
            } else {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            }

            try {
                switch(music) {
                    case "China":
                        mMediaPlayer.setDataSource(this, resourceToUri(R.raw.china));
                        break;
                    case "Nyan Cat":
                        mMediaPlayer.setDataSource(this, resourceToUri(R.raw.nyan_cat));
                        break;
                    case "Summer":
                        mMediaPlayer.setDataSource(this, resourceToUri(R.raw.summer));
                        break;
                    case "Egypt":
                        mMediaPlayer.setDataSource(this, resourceToUri(R.raw.egypt));
                        break;
                    default:
                        mMediaPlayer.setDataSource(this, resourceToUri(R.raw.summer));
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "EXCEPTION!", Toast.LENGTH_SHORT).show();
            }

            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopForeground(true);
                }
            });
        } else if (mPrepared) {
            onPrepared(mMediaPlayer);
        } else {
            mMediaPlayer.prepareAsync();
        }
    }

    private void playSoundEffect() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes attributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build();
                mMediaPlayer.setAudioAttributes(attributes);
            } else {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            }

            try {
                mMediaPlayer.setDataSource(this, resourceToUri(R.raw.collect));

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "EXCEPTION!", Toast.LENGTH_SHORT).show();
            }

            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopForeground(true);
                }
            });
        } else if (mPrepared) {
            onPrepared(mMediaPlayer);
        } else {
            mMediaPlayer.prepareAsync();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mPrepared = true;
        mp.start();
    }
}
