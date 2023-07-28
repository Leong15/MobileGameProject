package com.example.mobileproject.Object;

import android.graphics.*;

import com.example.mobileproject.GameLoop;
import com.example.mobileproject.Joystick;

import java.lang.Object;

public class Player{
    public Rect playerRect;
    private float positionX,positionY;
    private float lastX,lastY;
    private int hp;
    private int attack;
    private int defend;
    private int coin;
    private int keys;
    private boolean haveWeapon,haveArmor;
    public boolean isLeft;
    private float velocityX, velocityY;
    private float SPEED_PIXELS_PER_SECOND = 400;
    private float maxSpeed = (float) (SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS);
    Paint paint;
    Joystick joystick;

    public Player(float positionX,float positionY,
                  int hp, int attack, int defend, int coin, int keys,
                  boolean haveWeapon,boolean haveArmor,int tileSize,
                  int width,int height,Rect playerRect){
        this.positionX = positionX;
        this.positionY = positionY;
        this.attack = attack;
        this.hp = hp;
        this.defend = defend;
        this.coin = coin;
        this.keys = keys;
        this.haveWeapon = haveWeapon;
        this.haveArmor = haveArmor;
        paint = new Paint();
        paint.setColor(Color.BLACK);
        this.playerRect = playerRect;
        isLeft = true;
    }
    public float getPlayerPositionX(){return positionX;}
    public float getPlayerPositionY(){return positionY;}
    public float getVelocityX(){return velocityX;}
    public float getVelocityY(){return velocityY;}
    public int getPlayerHp(){return hp;}
    public int getPlayerAttack(){return attack;}
    public int getPlayerDefend(){return defend;}
    public int getPlayerCoin(){return coin;}
    public int getPlayerKeys(){return keys;}
    public boolean getPlayerWeapon(){return haveWeapon;}
    public boolean getPlayerArmor(){return haveArmor;}
    public float getLastX(){return lastX;};
    public float getLastY(){return lastY;}

    public void setPlayerPositionX(float pX){positionX=pX;}
    public void setPlayerPositionY(float pY){positionY=pY;}
    public void setHP(int hp){this.hp+=hp;}
    public void setPlayerAttack(int attack){this.attack+=attack;}
    public void setPlayerDefend(int defend){this.defend+=defend;};
    public void setLessHp(int hp){this.hp-=hp;}
    public void setPlayerWeapon(boolean hasWeapon){this.haveWeapon = hasWeapon;}
    public void setPlayerArmor(boolean hasArmor){this.haveArmor = hasArmor;}

    public void draw(Canvas canvas,Bitmap resizePlayer,Bitmap resizePlayerRight,int width, int height,int tileSize) {
        if(isLeft) {
            canvas.drawBitmap(resizePlayer, positionX, positionY, null);
        }else{
            canvas.drawBitmap(resizePlayerRight,positionX,positionY,null);
        }
        //canvas.drawRect(playerRect,paint);
    }
    public void update(Joystick joystick, int tileSize, int width, int height){
        this.joystick = joystick;
        lastX = positionX;
        lastY = positionY;
        velocityX = joystick.getActuatorX()*maxSpeed;
        velocityY = joystick.getActuatorY()*maxSpeed;
        if(velocityX <0){isLeft =true;}else if (velocityX>0){isLeft = false;}
        if (positionX+tileSize >= width ){
            positionX = width-tileSize;
        }else if(positionX <= 0)
            positionX = 0;
        if(positionY+tileSize*3>=height) {
            positionY = height-tileSize*3;
        }else if (positionY<=0){
            positionY = 0;
        }
        positionX += velocityX;
        positionY += velocityY;

        playerRect.left = (int) ((int) positionX + tileSize * 0.3);
        playerRect.right = (int) (positionX + tileSize * 0.7);
        playerRect.top = (int) ((int) positionY + tileSize * 0.2);
        playerRect.bottom = (int) (positionY + tileSize * 0.9);

    }

    public void setCoin(int i) {
        coin += i ;
    }

    public void setKey(int i,boolean isPickKey) {
        if(isPickKey) {
            keys += 1;
        }else
            keys-=1;
    }
}
