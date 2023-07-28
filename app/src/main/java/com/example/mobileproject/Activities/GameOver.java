package com.example.mobileproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mobileproject.Game;
import com.example.mobileproject.R;

public class GameOver extends AppCompatActivity{
    ImageView img;
    private Button backHome;
    private Button restart;
    private Button close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        img = findViewById(R.id.win);
        boolean isWin = getSharedPreferences("test",MODE_PRIVATE)
                .getBoolean("isWin",true);
        if (!isWin){
            img.setImageResource(R.drawable.defeat);
        }else
            img.setImageResource(R.drawable.victory);
        backHome = findViewById(R.id.BackHome);
        restart = findViewById(R.id.Restart);
        close = findViewById(R.id.close);

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GameOver.this, HomeActivity.class));
                finish();
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GameOver.this, StageActivity.class));
                finish();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });
    }
}
