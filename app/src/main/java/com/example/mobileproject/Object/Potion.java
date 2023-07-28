package com.example.mobileproject.Object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Potion {
    private int recover;
    private float positionX;
    private float positionY;
    private Rect potionRect;
    Paint paint;
    public Potion(int HP, float positionX, float positionY, Rect potionRect){
        this.positionX = positionX;
        this.positionY = positionY;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        recover = HP;
        this.potionRect = potionRect;
    }

    public static boolean isCollision(Potion potion, Player player) {
        if (Rect.intersects(potion.potionRect,player.playerRect)){
            return true;
        }else
            return false;
    }

    public int getRecover() {
        return recover;
    }

    public void draw(Canvas canvas, Bitmap resizePotion){
        canvas.drawBitmap(resizePotion,positionX,positionY,null);
        //canvas.drawRect(potionRect,paint);
    }
}
