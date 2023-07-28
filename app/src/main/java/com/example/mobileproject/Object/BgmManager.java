package com.example.mobileproject.Object;

import android.content.Context;
import android.media.MediaPlayer;
import com.example.mobileproject.R;

public class BgmManager {
    private static MediaPlayer mediaPlayer;

    public static MediaPlayer getInstance(Context context,String status) {
        if (mediaPlayer == null) {
            if(status =="home") {
                mediaPlayer = MediaPlayer.create(context, R.raw.home);
            }
            else if (status =="stage") {
                mediaPlayer = MediaPlayer.create(context, R.raw.ingame);
                mediaPlayer.setLooping(true);
            }
        }
        //mediaPlayer.setVolume(1.0f, 1.0f);
        mediaPlayer.setLooping(true);
        return mediaPlayer;
    }

}
