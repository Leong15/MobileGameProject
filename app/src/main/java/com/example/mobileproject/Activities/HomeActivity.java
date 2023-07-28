package com.example.mobileproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mobileproject.Dialog.AboutDialog;
import com.example.mobileproject.Object.BgmManager;
import com.example.mobileproject.R;

public class HomeActivity extends AppCompatActivity{
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    MediaPlayer mediaPlayer;

    public void playAudio() {
        mediaPlayer = BgmManager.getInstance(HomeActivity.this,"home");
        mediaPlayer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        button1 = findViewById(R.id.start);
        button2 = findViewById(R.id.setting);
        button3 = findViewById(R.id.about);
        button4 = findViewById(R.id.close);
        playAudio();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StageActivity.class));
                BgmManager.getInstance(HomeActivity.this,"home").stop();
                BgmManager.getInstance(HomeActivity.this,"home").release();
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Setting.class));

            }
        });

        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openDialog();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });

    }

    public void openDialog() {
        AboutDialog aboutDialog = new AboutDialog();
        aboutDialog.show(getSupportFragmentManager(), "About Dialog");
    }

    private void releaseMediaPlayer()
    {
            //mediaPlayer.release();
    }



    @Override
    protected void onPause() {
        super.onPause();
        BgmManager.getInstance(this,"home").pause();
    }

    @Override
    public void onDestroy() {
        BgmManager.getInstance(this,"home").release();
        super.onDestroy();
    }

}
