package com.example.mobileproject.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.mobileproject.Game;

public class PlayerDialog extends AppCompatDialogFragment {
    Game game;
    public PlayerDialog(Game game) {
        this.game = game;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String alert1 = "HP: " + game.player.getPlayerHp() ;
        String alert2 = "Atteck: "+ game.player.getPlayerAttack();
        String alert3 = "Defend: "+ game.player.getPlayerDefend();
        String alert4 = "Coins: "+game.player.getPlayerCoin();
        String alert5 = "Keys: " +game.player.getPlayerKeys();
        String alert6 = "Player has no weapon";
        String alert7 = "Player has no armor";
        if (game.player.getPlayerWeapon())
            alert6="Player has weapon";

        if (game.player.getPlayerArmor()) {
            alert7 = "Player has armor";
        }
        builder.setTitle("Player")
                .setMessage(alert1 + "\n" + alert2 + "\n" + alert3 + "\n" + alert4 + "\n" + alert5+
                        "\n"+alert6+"\n"+alert7)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }
}
