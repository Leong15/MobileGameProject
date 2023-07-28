package com.example.mobileproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import com.example.mobileproject.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set content view to main game
        setContentView(R.layout.splash_screen);

    }

    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN)
            showMainActivity();
        return true;
    }
    private void showMainActivity(){
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }

}