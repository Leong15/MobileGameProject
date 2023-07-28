package com.example.mobileproject;

import android.graphics.Canvas;
import android.view.Surface;
import android.view.SurfaceHolder;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import java.util.Observer;

public class GameLoop extends Thread {

    public static final double MAX_UPS = 60.0;
    private static final double UPS_PERIOD = 1E+3/MAX_UPS;
    private boolean isRunning = false;
    private SurfaceHolder surfaceHolder;
    public Executor e;
    private Game game;
    public int screen_width,screen_height;
    public long startTime;
    public double averageUPS;
    public double averageFPS;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        this.game = game;

        screen_width=game.width;
        screen_height=game.height;

        e=Executors.newSingleThreadExecutor();
        startTime = System.currentTimeMillis();
    }
    public double getAverageUPS(){
        return averageUPS;
    }
    public double getAverageFPS(){
        return averageFPS;
    }
    public void startLoop(){
        isRunning=true;
        start();
    }
    public void stopLoop(){
        isRunning =false;
        try {
            join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        int updateCount = 0;
        int frameCount = 0;

        long startTime;
        long elapsedTime;
        long sleeptime;
        //game loop
        Canvas canvas = null;
        startTime = System.currentTimeMillis();
        while (isRunning){
            //update and render game
            try {
                // canvas = surfaceHolder.lockCanvas();
                canvas = surfaceHolder.lockHardwareCanvas();
                synchronized (surfaceHolder){
                    game.update();
                    updateCount++;
                    game.draw(canvas);
                }
            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }finally {
                if(canvas !=null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            //pause game loop to not exceed target UPS
            elapsedTime = System.currentTimeMillis()-startTime;
            sleeptime = (long) (updateCount*UPS_PERIOD -elapsedTime);
            if (sleeptime > 0){
                try {
                    sleep(sleeptime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //skip frames to keep up with target UPS
            while (sleeptime<0 && updateCount<MAX_UPS-1){
                game.update();
                updateCount++;
                elapsedTime=System.currentTimeMillis()-startTime;
                sleeptime = (long) (updateCount*UPS_PERIOD -elapsedTime);
            }
            //cal average UPS and FPS
            if (game.showFPS == false) {
                elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime >= 1000) {
                    averageUPS = updateCount / (1E-3 * elapsedTime);
                    averageFPS = frameCount / (1E-3 * elapsedTime);
                    updateCount = 0;
                    frameCount = 0;
                    startTime = System.currentTimeMillis();
                }
            }
        }

    }
}
