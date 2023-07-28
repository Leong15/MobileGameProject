package com.example.mobileproject.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.mobileproject.Game;

import java.security.AllPermission;

public class ShopDialog extends AppCompatDialogFragment {
    Game game;
    public ShopDialog(Game game) {
        this.game = game;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String alert1 = "Your Coins :" + game.player.getPlayerCoin();
        String alert2 = "1 coin for 1 hp";
        builder.setTitle("Shop");
        builder.setMessage(alert1 + "\n" + alert2);
        builder.setPositiveButton("Buy", (dialog, which) -> {
        });
        builder.setNegativeButton("Close", (dialog, which) -> {
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
            if (game.player.getPlayerCoin() > 0) {
                setToast("Bought Successful");
                game.player.setCoin(-1);
                game.player.setHP(1);
            } else if (game.player.getPlayerCoin() <= 0) {
                setToast("You have no money");
            }
        });
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
    private void setToast(String input){
        Toast.makeText(getContext(),input,Toast.LENGTH_SHORT).show();
    }
}
