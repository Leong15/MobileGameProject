package com.example.mobileproject.Object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Walls {
    private int stage;
    Rect wallRect;
    float positionX,positionY;
    Paint paint;
    public Walls(float positionX,float positionY,int tileSize,Rect wallRect){
        stage = 1;
        this.wallRect = wallRect;
        this.positionX = positionX;
        this.positionY = positionY;
        paint = new Paint();
        paint.setColor(Color.BLACK);
    }

    public static boolean isCollision(Walls walls, Player player) {
        if (Rect.intersects(walls.wallRect,player.playerRect)){
            return true;
        }else
            return false;
    }

    public float getWallPositionX(){return positionX;}
    public float getWallPositionY(){return positionY;}
    public void draw(Canvas canvas, Bitmap resizeWall,int tileSize) {
        canvas.drawBitmap(resizeWall,positionX,positionY,null);
        //canvas.drawRect(wallRect,paint);
    }

    public void update(int tileSize){
    }
}
