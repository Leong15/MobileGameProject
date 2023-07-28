package com.example.mobileproject.Object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Monster {
    private final int attack;
    float positionX,positionY;
    Rect monsterRects;
    Paint paint;

    public Monster(int attack,float positionX,float positionY,Rect monsterRects){
        this.attack=attack;
        this.positionX = positionX;
        this.positionY = positionY;
        this.monsterRects = monsterRects;
        paint = new Paint();
        paint.setColor(Color.WHITE);
    }
    public int getMonsterAttack(){return attack;}

    public static boolean isCollision(Monster monster, Player player) {
        if (Rect.intersects(monster.monsterRects,player.playerRect)){
            return true;
        }else
            return false;
    }

    public void draw(Canvas canvas, Bitmap resizeMonster, int tileSize) {
        canvas.drawBitmap(resizeMonster,positionX,positionY,null);
        canvas.drawText(Integer.toString(attack),positionX,positionY,paint);
        //canvas.drawRect(monsterRects,paint);
    }

}
