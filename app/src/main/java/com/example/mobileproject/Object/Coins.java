package com.example.mobileproject.Object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Coins {
    int coinValue;
    private Rect coinRect;
    Paint paint;
    //int
    float pX, pY;
    public Coins(int coinValue,Rect coinRect,float pX,float pY){
       this.coinValue = coinValue;
       this.coinRect = coinRect;
       this.pX=pX;
       this.pY=pY;
       paint = new Paint();
       paint.setColor(Color.WHITE);
    }

    public static boolean isCollision(Coins coin, Player player) {
        if (Rect.intersects(coin.coinRect,player.playerRect)){
            return true;
        }else
            return false;
    }

    public void draw(Canvas canvas, Bitmap resizeCoin){
        canvas.drawBitmap(resizeCoin,pX,pY,null);
        //canvas.drawRect(coinRect,paint);
    }
}
