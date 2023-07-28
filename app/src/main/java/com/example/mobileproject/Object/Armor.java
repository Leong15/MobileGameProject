package com.example.mobileproject.Object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Armor {
    private Rect armorRect;
    private float pX,pY;
    Paint paint;

    public Armor(Rect armorRect,float pX,float pY){
        this.pX = pX;
        this.pY = pY;
        this.armorRect=armorRect;
        paint = new Paint();
        paint.setColor(Color.WHITE);
    }

    public static boolean isCollision(Armor armor, Player player) {
        if (Rect.intersects(armor.armorRect,player.playerRect)){
            return true;
        }else
            return false;
    }

    public void draw(Canvas canvas, Bitmap resizeArmor){
        canvas.drawBitmap(resizeArmor,pX,pY,null);
        //canvas.drawRect(armorRect,paint);
    }
}
