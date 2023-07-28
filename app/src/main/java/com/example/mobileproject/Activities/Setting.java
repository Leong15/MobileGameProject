package com.example.mobileproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mobileproject.Object.BgmManager;
import com.example.mobileproject.R;

public class Setting extends AppCompatActivity {

    private SeekBar soundbarVolume, brightnessBar;
    private AudioManager audioManager;
    private MediaPlayer mediaPlayer;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mediaPlayer = BgmManager.getInstance(Setting.this,"home");
        mediaPlayer.start();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        soundbarVolume = findViewById(R.id.soundbarVolume);

        brightnessBar = findViewById(R.id.brightnessBar);
        textView = findViewById(R.id.textView);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //get max volume
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        //get Current Volume
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        //Brightness
        int controlBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 0);
        textView.setText(controlBrightness+"/255");
        brightnessBar.setProgress(controlBrightness);

        brightnessBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Context context = getApplicationContext();
                boolean canWrite = Settings.System.canWrite(context);

                if (canWrite){
                    int setBrightness = progress * 255/255;
                    textView.setText(setBrightness + "/255");
                    Settings.System.putInt(context.getContentResolver(),
                            Settings.System.SCREEN_BRIGHTNESS_MODE,
                            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

                    Settings.System.putInt(context.getContentResolver(),
                            Settings.System.SCREEN_BRIGHTNESS,
                            setBrightness);
                }
                else{
                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    context.startActivity(intent);
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        soundbarVolume.setMax(maxVolume);
        soundbarVolume.setProgress(curVolume);

        soundbarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
