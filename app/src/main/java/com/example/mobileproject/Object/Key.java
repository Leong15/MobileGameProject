package com.example.mobileproject.Object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Key {
    //int coinValue;
    private Rect keyRect;
    Paint paint;
    //int
    float pX, pY;
    public Key(Rect keyRect,float pX,float pY){
        this.keyRect = keyRect;
        this.pX=pX;
        this.pY=pY;
        paint = new Paint();
        paint.setColor(Color.WHITE);
    }

    public static boolean isCollision(Key key, Player player) {
        if (Rect.intersects(key.keyRect,player.playerRect)){
            return true;
        }else
            return false;
    }

    public void draw(Canvas canvas, Bitmap resizeKey){
        canvas.drawBitmap(resizeKey,pX,pY,null);
        //canvas.drawRect(keyRect,paint);
    }
}
