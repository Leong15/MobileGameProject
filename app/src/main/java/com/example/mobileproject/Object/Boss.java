package com.example.mobileproject.Object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Boss {
    private final int attack;
    float positionX,positionY;
    Rect bossRects;
    Paint paint;

    public Boss(int attack,float positionX,float positionY,Rect bossRects){
        this.attack=attack;
        this.positionX = positionX;
        this.positionY = positionY;
        this.bossRects = bossRects;
        paint = new Paint();
        paint.setColor(Color.WHITE);
    }
    public int getBossAttack(){return attack;}

    public static boolean isCollision(Monster monster, Player player) {
        if (Rect.intersects(monster.monsterRects,player.playerRect)){
            return true;
        }else
            return false;
    }

    public void draw(Canvas canvas, Bitmap resizeBoss, int tileSize) {
        canvas.drawBitmap(resizeBoss,positionX,positionY,null);
        canvas.drawText(Integer.toString(attack),positionX,positionY,paint);
        //canvas.drawRect(bossRects,paint);
    }

}
