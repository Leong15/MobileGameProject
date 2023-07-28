package com.example.mobileproject.Activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileproject.Dialog.AboutDialog;
import com.example.mobileproject.Dialog.PlayerDialog;
import com.example.mobileproject.Dialog.ShopDialog;
import com.example.mobileproject.Object.BgmManager;
import com.example.mobileproject.Game;
import com.example.mobileproject.R;

public class StageActivity extends AppCompatActivity {
    Game game;
    MediaPlayer mediaPlayer;
    public void playAudio() {
        mediaPlayer = BgmManager.getInstance(StageActivity.this,"stage");
        mediaPlayer.start();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new Game(this);
        setContentView(game);
        playAudio();

    }
    public void onPause(){
        game.pasueGame();
        BgmManager.getInstance(this,"stage").pause();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.playerinfo:
                openPlayerDialog();
                return true;
            case R.id.about:
                openAboutDialog();
                return true;
            case R.id.shop:
                openShopDialog();
                return true;
            case R.id.close:
                finish();
                System.exit(0);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openShopDialog(){
        ShopDialog shopDialog = new ShopDialog(game);
        shopDialog.show(getSupportFragmentManager(),"Shop Dialog");
    }

    public void openAboutDialog() {
        AboutDialog aboutDialog = new AboutDialog();
        aboutDialog.show(getSupportFragmentManager(), "About Dialog");
    }

    public void openPlayerDialog() {
        PlayerDialog playerDialog = new PlayerDialog(game);
        playerDialog.show(getSupportFragmentManager(), "Player Dialog");
    }
    @Override
    public void onDestroy() {
        BgmManager.getInstance(this,"stage").release();
        finish();
        super.onDestroy();
    }
}
