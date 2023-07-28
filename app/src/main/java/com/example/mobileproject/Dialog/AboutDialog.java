package com.example.mobileproject.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class AboutDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String alert1 = "Chau Ka Leong 12801181";
        String alert2 = "Chui Ming Hei 12779298";
        String alert3 = "Yip Yat Tai 12779446";
        String alert4 = "Kam Yan Shan Peter 12843135";
        builder.setTitle("About")
                .setMessage(alert1 + "\n" + alert2 + "\n" + alert3 + "\n" + alert4)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }
}
