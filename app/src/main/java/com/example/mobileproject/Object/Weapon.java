package com.example.mobileproject.Object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Weapon {
    private Rect weaponRect;
    Paint paint;
    //int
    float pX, pY;
    public Weapon(Rect weaponRect,float pX,float pY){
        this.weaponRect = weaponRect;
        this.pX=pX;
        this.pY=pY;
        paint = new Paint();
        paint.setColor(Color.WHITE);
    }

    public static boolean isCollision(Weapon weapon, Player player) {
        if (Rect.intersects(weapon.weaponRect,player.playerRect)){
            return true;
        }else
            return false;
    }

    public void draw(Canvas canvas, Bitmap resizeWeapon){
        canvas.drawBitmap(resizeWeapon,pX,pY,null);
        //canvas.drawRect(weaponRect,paint);
    }
}

