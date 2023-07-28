package com.example.mobileproject.Object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Stair {
    //int coinValue;
    private Rect stairRect;
    Paint paint;
    //int
    float pX, pY;
    public Stair(Rect stairRect,float pX,float pY){
        this.stairRect = stairRect;
        this.pX=pX;
        this.pY=pY;
        paint = new Paint();
        paint.setColor(Color.WHITE);
    }

    public static boolean isCollision(Stair stair, Player player) {
        if (Rect.intersects(stair.stairRect,player.playerRect)){
            return true;
        }else
            return false;
    }

    public void draw(Canvas canvas, Bitmap resizeStair){
        canvas.drawBitmap(resizeStair,pX,pY,null);
        //canvas.drawRect(stairRect,paint);
    }
}