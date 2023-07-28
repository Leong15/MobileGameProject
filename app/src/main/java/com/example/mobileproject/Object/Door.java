package com.example.mobileproject.Object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Door {
    boolean isOpen;
    private Rect doorRect;
    Paint paint;

    float pX, pY;
    public Door(Rect doorRect,float pX,float pY){
        this.doorRect = doorRect;
        this.pX=pX;
        this.pY=pY;
        paint = new Paint();
        paint.setColor(Color.WHITE);
    }
    public static boolean isCollision(Door door, Player player) {
        if (Rect.intersects(door.doorRect,player.playerRect)){
            return true;
        }else
            return false;
    }
    public void draw(Canvas canvas, Bitmap resizeDoor){
        canvas.drawBitmap(resizeDoor,pX,pY,null);
        //canvas.drawRect(doorRect,paint);
    }
}
