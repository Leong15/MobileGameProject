package com.example.mobileproject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Joystick {
    private Paint outerJoystickPaint, innerJoystickPaint;
    private int outerJoystickPositionX,outerJoystickPositionY;
    private int innerJoystickPositionX,innerJoystickPositionY;
    private int outerJoystickRadius,innerJoystickRadius;
    private float joystickCenterAndPointDistance;
    private boolean isPressed;
    private float actuatorX, actuatorY;

    public Joystick(int centerPositionX,int centerPositionY,int outerJoystickRadius,int innerJoystickRadius){
        outerJoystickPositionX = centerPositionX;
        outerJoystickPositionY = centerPositionY;
        innerJoystickPositionX = centerPositionX;
        innerJoystickPositionY = centerPositionY;

        this.outerJoystickRadius = outerJoystickRadius;
        this.innerJoystickRadius = innerJoystickRadius;

        outerJoystickPaint = new Paint();
        outerJoystickPaint.setColor(Color.GRAY);
        outerJoystickPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        innerJoystickPaint = new Paint();
        innerJoystickPaint.setColor(Color.RED);
        innerJoystickPaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }
    public void draw(Canvas canvas) {
        canvas.drawCircle(outerJoystickPositionX,outerJoystickPositionY,outerJoystickRadius,outerJoystickPaint);
        canvas.drawCircle(innerJoystickPositionX,innerJoystickPositionY,innerJoystickRadius,innerJoystickPaint);
    }

    public void update(float pointX,float pointY) {
        updateJoystickPosition(pointX,pointY);
        updateInnerJoystick();
    }

    private void updateInnerJoystick() {
        innerJoystickPositionX = (int) (outerJoystickPositionX+actuatorX*outerJoystickRadius);
        innerJoystickPositionY = (int) (outerJoystickPositionY+actuatorY*outerJoystickRadius);
    }
    private void updateJoystickPosition(float pointX,float pointY) {
        outerJoystickPositionX = (int) pointX;
        outerJoystickPositionY = (int) pointY;
        innerJoystickPositionX = (int) pointX;
        innerJoystickPositionY = (int) pointY;
    }

    public boolean isPressed(float pointX, float pointY) {
        joystickCenterAndPointDistance = (float) Math.sqrt(Math.pow(outerJoystickPositionX - pointX,2)
                +Math.pow(outerJoystickPositionY - pointY,2));
        return  joystickCenterAndPointDistance < outerJoystickRadius;

    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed=isPressed;
    }

    public boolean getIsPressed() {
        return isPressed;
    }


    public void setActuator(float pointX, float pointY) {
        float deltaX = pointX - outerJoystickPositionX;
        float deltaY = pointY - outerJoystickPositionY;
        float deltaDistance = (float) Math.sqrt(Math.pow(deltaX,2)+Math.pow(deltaY,2));

        if (deltaDistance < outerJoystickRadius){
            actuatorX = deltaX / outerJoystickRadius;
            actuatorY = deltaY / outerJoystickRadius;
        }else {
            actuatorX = deltaX / deltaDistance;
            actuatorY = deltaY / deltaDistance;
        }
    }

    public void resetActuator() {
        actuatorX = 0;
        actuatorY = 0;
    }

    public float getActuatorX() {
        return actuatorX;
    }
    public float getActuatorY() {
        return actuatorY;
    }
}
