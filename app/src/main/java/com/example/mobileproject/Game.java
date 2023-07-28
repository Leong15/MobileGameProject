package com.example.mobileproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.*;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.mobileproject.Activities.GameOver;
import com.example.mobileproject.Activities.StageActivity;
import com.example.mobileproject.Dialog.AboutDialog;
import com.example.mobileproject.Object.Armor;
import com.example.mobileproject.Object.Boss;
import com.example.mobileproject.Object.Coins;
import com.example.mobileproject.Object.Door;
import com.example.mobileproject.Object.Key;
import com.example.mobileproject.Object.Monster;
import com.example.mobileproject.Object.Player;
import com.example.mobileproject.Object.Potion;
import com.example.mobileproject.Object.Stair;
import com.example.mobileproject.Object.Walls;
import com.example.mobileproject.Object.Weapon;

import java.util.ArrayList;
import java.util.Iterator;

public class Game extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    private Joystick joystick;
    public Player player;
    public Boss boss;
    private GameLoop gameLoop;
    public Boolean isWin;

    //Arraylist for object
    ArrayList<Walls> walls = new ArrayList<Walls>();
    ArrayList<Potion> potions = new ArrayList<Potion>();
    ArrayList<Coins> coins = new ArrayList<Coins>();
    ArrayList<Key> keys = new ArrayList<Key>();
    ArrayList<Door> doors = new ArrayList<Door>();
    ArrayList<Monster> monsters = new ArrayList<Monster>();
    ArrayList<Stair> stairs = new ArrayList<Stair>();
    ArrayList<Weapon> weapons = new ArrayList<Weapon>();
    ArrayList<Armor> armors = new ArrayList<Armor>();
   //Arraylist for object Rect
    ArrayList<Rect> wallRects = new ArrayList<Rect>();
    ArrayList<Rect> potionRects = new ArrayList<Rect>();
    ArrayList<Rect> coinRects = new ArrayList<Rect>();
    ArrayList<Rect> keyRects = new ArrayList<Rect>();
    ArrayList<Rect> doorRects = new ArrayList<Rect>();
    ArrayList<Rect> stairRects = new ArrayList<Rect>();
    ArrayList<Rect> monsterRects = new ArrayList<Rect>();
    ArrayList<Rect> weaponRects = new ArrayList<Rect>();
    ArrayList<Rect> armorRects = new ArrayList<Rect>();

    AboutDialog aboutDialog;
    public boolean showFPS=false;
    private Context context;
    SurfaceHolder holder;
    Canvas canvas;
    Paint paint;
    DisplayMetrics displayMetrics;

    int monsterAttack;
    public int stage;
    public int tileSize;
    public int maxNumOfTileRows, maxNumOfTileColumns;
    public int gameScreenWidth, gameScreenHeight;
    public float pointX,pointY;
    int diff;
    int damageToPlayer;
    Rect playerRect, bossRects;
    public int width,height;
    Bitmap playerImg,bg, wall, door, coin, key, potion,monster,stairImg,playerRight,weapon,armor,bossImg;
    Bitmap resizePlayer,resizeBg,resizeWall,resizeDoor,resizeCoin,resizeKey,
            resizePosion,resizeMonster,resizeStair,resizePlayerRight,resizeWeapon,resizeArmor,resizeBoss;


    public Game(Context context) {
        super(context);
        this.context = context;

        displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        stage =1 ;
        //setup
        maxNumOfTileRows = 50+90;
        maxNumOfTileColumns = 50+90;
        //set tilesize based off display
        if (displayMetrics.density >= 1.0 && displayMetrics.density <= 1.5) {
            //mdpi
            tileSize = 60;
        } else if (displayMetrics.density >= 1.5 && displayMetrics.density <= 2.0) {
            //hdpi
            tileSize = 90;
        } else if (displayMetrics.density >= 2.0 && displayMetrics.density <= 3.0) {
            //xhdpi
            tileSize = 180;
        } else if (displayMetrics.density >= 3.0) {
            //xxhdpi
            tileSize = 270;
        }
        gameScreenWidth = tileSize * maxNumOfTileColumns;
        gameScreenHeight = tileSize * maxNumOfTileRows;

        holder = getHolder();
        holder.addCallback(this);
        gameLoop = new GameLoop(this, holder);
        canvas = new Canvas();
        paint = new Paint();
        paint.setColor(Color.parseColor("#FFFFFF"));
        paint.setTextSize(40);
        playerRect = new Rect((int) (width-tileSize/1.5), (int) (tileSize/1.5),width,tileSize);

        aboutDialog = new AboutDialog();
        monsterAttack = (int) (Math.random()*200+200);
        player = new Player(width-tileSize,0,
                100,50,5,0,0,false,false,
                tileSize,width,height,playerRect);

        joystick = new Joystick(width-tileSize,tileSize,30,20);
        //set size of the rect array

        //new armor Rect
        armorRects.add(new Rect(tileSize*6,tileSize*7,tileSize*7,tileSize*8));
        //new armor
        armors.add(new Armor(armorRects.get(0),tileSize*6,tileSize*7));
        //new monster Rect
        monsterRects.add(new Rect(tileSize*2,tileSize,tileSize*3,tileSize*2));
        monsterRects.add(new Rect(tileSize*3,tileSize*3,tileSize*4,tileSize*4));
        monsterRects.add(new Rect(tileSize,tileSize*8,tileSize*2,tileSize*9));
        monsterRects.add(new Rect(tileSize*6,tileSize*10,tileSize*7,tileSize*11));

        //new monster
        monsters.add(new Monster((int) ((Math.random()*100)+100),tileSize*2,tileSize,monsterRects.get(0)));
        monsters.add(new Monster((int) ((Math.random()*100)),tileSize*3,tileSize*3,monsterRects.get(1)));
        monsters.add(new Monster((int) ((Math.random()*100)+100),tileSize,tileSize*8,monsterRects.get(2)));
        monsters.add(new Monster((int) ((Math.random()*100)+100),tileSize*6,tileSize*10,monsterRects.get(3)));

        //new stair Rect
        stairRects.add(new Rect(tileSize*7,tileSize*10,tileSize*8,tileSize*11));
        //new stair
        stairs.add(new Stair(stairRects.get(0),tileSize*7,tileSize*10));
        //new door Rect
        doorRects.add(new Rect(tileSize*2,tileSize*2,tileSize*3,tileSize*3));
        doorRects.add(new Rect(tileSize*5,tileSize*3,tileSize*6,tileSize*4));
        doorRects.add(new Rect(tileSize*3,tileSize*6,tileSize*4,tileSize*7));
        doorRects.add(new Rect(tileSize,tileSize*7,tileSize*2,tileSize*8));
        doorRects.add(new Rect(tileSize*5,tileSize*8,tileSize*6,tileSize*9));
        //new door
        doors.add(new Door(doorRects.get(0),tileSize*2,tileSize*2));
        doors.add(new Door(doorRects.get(1),tileSize*5,tileSize*3));
        doors.add(new Door(doorRects.get(2),tileSize*3,tileSize*6));
        doors.add(new Door(doorRects.get(3),tileSize,tileSize*7));
        doors.add(new Door(doorRects.get(4),tileSize*5,tileSize*8));

        //new key Rect
        keyRects.add(new Rect(tileSize,tileSize*3,tileSize*2,tileSize*4));
        keyRects.add(new Rect(tileSize,tileSize*9,tileSize*2,tileSize*10));
        keyRects.add(new Rect(tileSize*7,tileSize*3,tileSize*8,tileSize*4));
        keyRects.add(new Rect(tileSize*6,tileSize*6,tileSize*7,tileSize*7));
        keyRects.add(new Rect(tileSize*7,tileSize*6,tileSize*8,tileSize*7));
        //new key
        keys.add(new Key(keyRects.get(0),tileSize,tileSize*3));
        keys.add(new Key(keyRects.get(1),tileSize,tileSize*9));
        keys.add(new Key(keyRects.get(2),tileSize*7,tileSize*3));
        keys.add(new Key(keyRects.get(3),tileSize*6,tileSize*6));
        keys.add(new Key(keyRects.get(4),tileSize*7,tileSize*6));

        //new coin Rect
        coinRects.add(new Rect(tileSize,0,tileSize*2,tileSize));
        coinRects.add(new Rect(0,tileSize,tileSize,tileSize*2));
        coinRects.add(new Rect(tileSize,tileSize*5,tileSize*2,tileSize*6));
        coinRects.add(new Rect(tileSize*2,tileSize*5,tileSize*3,tileSize*6));
        coinRects.add(new Rect(tileSize,tileSize*6,tileSize*2,tileSize*7));
        coinRects.add(new Rect(tileSize*2,tileSize*6,tileSize*3,tileSize*7));
        coinRects.add(new Rect(tileSize*7,tileSize*8,tileSize*8,tileSize*9));
        //new coins
        coins.add(new Coins(5,coinRects.get(0),tileSize,0));
        coins.add(new Coins(5,coinRects.get(1),0,tileSize));
        coins.add(new Coins(5,coinRects.get(2),tileSize,tileSize*5));
        coins.add(new Coins(5,coinRects.get(3),tileSize*2,tileSize*5));
        coins.add(new Coins(5,coinRects.get(4),tileSize,tileSize*6));
        coins.add(new Coins(5,coinRects.get(5),tileSize*2,tileSize*6));
        coins.add(new Coins(5,coinRects.get(6),tileSize*7,tileSize*8));

        //new potion Rect
        potionRects.add(new Rect(tileSize*2,0,tileSize*3,tileSize));
        potionRects.add(new Rect(0,tileSize*3,tileSize,tileSize*4));
        potionRects.add(new Rect(tileSize*7,tileSize*2,tileSize*8,tileSize*3));
        potionRects.add(new Rect(0,0,tileSize,tileSize));
        potionRects.add(new Rect(tileSize*6,tileSize*3,tileSize*7,tileSize*4));
        potionRects.add(new Rect(tileSize*7,tileSize*4,tileSize*8,tileSize*5));
        potionRects.add(new Rect(tileSize*7,tileSize*7,tileSize*8,tileSize*8));
        potionRects.add(new Rect(tileSize*6,tileSize*8,tileSize*7,tileSize*9));
        potionRects.add(new Rect(tileSize*2,tileSize*8,tileSize*3,tileSize*9));
        potionRects.add(new Rect(tileSize*2,tileSize*9,tileSize*3,tileSize*10));
        // new potions
        potions.add(new Potion(10,tileSize*2,0,potionRects.get(0)));
        potions.add(new Potion(10,0,tileSize*3,potionRects.get(1)));
        potions.add(new Potion(10,tileSize*7,tileSize*2,potionRects.get(2)));
        potions.add(new Potion(10,0,0,potionRects.get(3)));
        potions.add(new Potion(10,tileSize*6,tileSize*3,potionRects.get(4)));
        potions.add(new Potion(10,tileSize*7,tileSize*4,potionRects.get(5)));
        potions.add(new Potion(10,tileSize*7,tileSize*7,potionRects.get(6)));
        potions.add(new Potion(10,tileSize*6,tileSize*8,potionRects.get(7)));
        potions.add(new Potion(10,tileSize*2,tileSize*8,potionRects.get(8)));
        potions.add(new Potion(10,tileSize*2,tileSize*9,potionRects.get(9)));


        // new wall Rect
        wallRects.add(new Rect(tileSize*3,0,tileSize*4,tileSize));
        wallRects.add(new Rect(tileSize*3,tileSize,tileSize*4,tileSize*2));
        wallRects.add(new Rect(tileSize*5,tileSize,tileSize*6,tileSize*2));
        wallRects.add(new Rect(tileSize*6,tileSize,tileSize*7,tileSize*2));
        wallRects.add(new Rect(tileSize*7,tileSize,tileSize*8,tileSize*2));
        wallRects.add(new Rect(0,tileSize*2,tileSize,tileSize*3));
        wallRects.add(new Rect(tileSize,tileSize*2,tileSize*2,tileSize*3));
        wallRects.add(new Rect(tileSize*3,tileSize*2,tileSize*4,tileSize*3));
        wallRects.add(new Rect(tileSize*5,tileSize*2,tileSize*6,tileSize*3));
        wallRects.add(new Rect(0,tileSize*4,tileSize,tileSize*5));
        wallRects.add(new Rect(tileSize,tileSize*4,tileSize*2,tileSize*5));
        wallRects.add(new Rect(tileSize*2,tileSize*4,tileSize*3,tileSize*5));
        wallRects.add(new Rect(tileSize*3,tileSize*4,tileSize*4,tileSize*5));
        wallRects.add(new Rect(tileSize*5,tileSize*4,tileSize*6,tileSize*5));
        wallRects.add(new Rect(0,tileSize*5,tileSize,tileSize*6));
        wallRects.add(new Rect(tileSize*3,tileSize*5,tileSize*4,tileSize*6));
        wallRects.add(new Rect(tileSize*5,tileSize*5,tileSize*6,tileSize*6));
        wallRects.add(new Rect(tileSize*6,tileSize*5,tileSize*7,tileSize*6));
        wallRects.add(new Rect(tileSize*7,tileSize*5,tileSize*8,tileSize*6));
        wallRects.add(new Rect(0,tileSize*6,tileSize,tileSize*7));
        wallRects.add(new Rect(tileSize*5,tileSize*6,tileSize*6,tileSize*7));
        wallRects.add(new Rect(0,tileSize*7,tileSize,tileSize*8));
        wallRects.add(new Rect(tileSize*2,tileSize*7,tileSize*3,tileSize*8));
        wallRects.add(new Rect(tileSize*3,tileSize*7,tileSize*4,tileSize*8));
        wallRects.add(new Rect(tileSize*5,tileSize*7,tileSize*6,tileSize*8));
        wallRects.add(new Rect(0,tileSize*8,tileSize,tileSize*9));
        wallRects.add(new Rect(tileSize*3,tileSize*8,tileSize*4,tileSize*9));
        wallRects.add(new Rect(0,tileSize*9,tileSize,tileSize*10));
        wallRects.add(new Rect(tileSize*3,tileSize*9,tileSize*4,tileSize*10));
        wallRects.add(new Rect(tileSize*5,tileSize*9,tileSize*6,tileSize*10));
        wallRects.add(new Rect(tileSize*6,tileSize*9,tileSize*7,tileSize*10));
        wallRects.add(new Rect(tileSize*7,tileSize*9,tileSize*8,tileSize*10));
        wallRects.add(new Rect(0,tileSize*10,tileSize,tileSize*11));
        wallRects.add(new Rect(tileSize,tileSize*10,tileSize*2,tileSize*11));
        wallRects.add(new Rect(tileSize*2,tileSize*10,tileSize*3,tileSize*11));
        wallRects.add(new Rect(tileSize*3,tileSize*10,tileSize*4,tileSize*11));
        // new wall
        walls.add(new Walls(tileSize*3,0,tileSize,wallRects.get(0)));
        walls.add(new Walls(tileSize*3,tileSize,tileSize,wallRects.get(1)));
        walls.add(new Walls(tileSize*5,tileSize,tileSize,wallRects.get(2)));
        walls.add(new Walls(tileSize*6,tileSize,tileSize,wallRects.get(3)));
        walls.add(new Walls(tileSize*7,tileSize,tileSize,wallRects.get(4)));
        walls.add(new Walls(0,tileSize*2,tileSize,wallRects.get(5)));
        walls.add(new Walls(tileSize,tileSize*2,tileSize,wallRects.get(6)));
        walls.add(new Walls(tileSize*3,tileSize*2,tileSize,wallRects.get(7)));
        walls.add(new Walls(tileSize*5,tileSize*2,tileSize,wallRects.get(8)));
        walls.add(new Walls(0,tileSize*4,tileSize,wallRects.get(9)));
        walls.add(new Walls(tileSize,tileSize*4,tileSize,wallRects.get(10)));
        walls.add(new Walls(tileSize*2,tileSize*4,tileSize,wallRects.get(11)));
        walls.add(new Walls(tileSize*3,tileSize*4,tileSize,wallRects.get(12)));
        walls.add(new Walls(tileSize*5,tileSize*4,tileSize,wallRects.get(13)));
        walls.add(new Walls(0,tileSize*5,tileSize,wallRects.get(14)));
        walls.add(new Walls(tileSize*3,tileSize*5,tileSize,wallRects.get(15)));
        walls.add(new Walls(tileSize*5,tileSize*5,tileSize,wallRects.get(16)));
        walls.add(new Walls(tileSize*6,tileSize*5,tileSize,wallRects.get(17)));
        walls.add(new Walls(tileSize*7,tileSize*5,tileSize,wallRects.get(18)));
        walls.add(new Walls(0,tileSize*6,tileSize,wallRects.get(19)));
        walls.add(new Walls(tileSize*5,tileSize*6,tileSize,wallRects.get(20)));
        walls.add(new Walls(0,tileSize*7,tileSize,wallRects.get(21)));
        walls.add(new Walls(tileSize*2,tileSize*7,tileSize,wallRects.get(22)));
        walls.add(new Walls(tileSize*3,tileSize*7,tileSize,wallRects.get(23)));
        walls.add(new Walls(tileSize*5,tileSize*7,tileSize,wallRects.get(24)));
        walls.add(new Walls(0,tileSize*8,tileSize,wallRects.get(25)));
        walls.add(new Walls(tileSize*3,tileSize*8,tileSize,wallRects.get(26)));
        walls.add(new Walls(0,tileSize*9,tileSize,wallRects.get(27)));
        walls.add(new Walls(tileSize*3,tileSize*9,tileSize,wallRects.get(28)));
        walls.add(new Walls(tileSize*5,tileSize*9,tileSize,wallRects.get(29)));
        walls.add(new Walls(tileSize*6,tileSize*9,tileSize,wallRects.get(30)));
        walls.add(new Walls(tileSize*7,tileSize*9,tileSize,wallRects.get(31)));
        walls.add(new Walls(0,tileSize*10,tileSize,wallRects.get(32)));
        walls.add(new Walls(tileSize,tileSize*10,tileSize,wallRects.get(33)));
        walls.add(new Walls(tileSize*2,tileSize*10,tileSize,wallRects.get(34)));
        walls.add(new Walls(tileSize*3,tileSize*10,tileSize,wallRects.get(35)));
        //load bitmap and resize
        playerImg = BitmapFactory.decodeResource(getResources(), R.drawable.player);
        bg = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        wall = BitmapFactory.decodeResource(getResources(), R.drawable.walls);
        door = BitmapFactory.decodeResource(getResources(), R.drawable.door);
        coin = BitmapFactory.decodeResource(getResources(), R.drawable.coin);
        key = BitmapFactory.decodeResource(getResources(), R.drawable.key);
        stairImg = BitmapFactory.decodeResource(getResources(), R.drawable.stairs);
        monster = BitmapFactory.decodeResource(getResources(), R.drawable.monster);
        potion = BitmapFactory.decodeResource(getResources(), R.drawable.potion);
        bossImg = BitmapFactory.decodeResource(getResources(),R.drawable.boss);
        playerRight = BitmapFactory.decodeResource(getResources(),R.drawable.player_right);
        weapon = BitmapFactory.decodeResource(getResources(),R.drawable.weapon);
        armor = BitmapFactory.decodeResource(getResources(),R.drawable.armor);
        resizePlayer = Bitmap.createScaledBitmap(playerImg, tileSize, tileSize, false);
        resizeBoss = Bitmap.createScaledBitmap(bossImg,tileSize*2,tileSize*3,false);
        resizeBg = Bitmap.createScaledBitmap(bg, width, height, false);
        resizeStair = Bitmap.createScaledBitmap(stairImg, tileSize, tileSize, false);
        resizeWall = Bitmap.createScaledBitmap(wall, tileSize, tileSize, false);
        resizeDoor = Bitmap.createScaledBitmap(door, tileSize, tileSize, true);
        resizeCoin = Bitmap.createScaledBitmap(coin, tileSize, tileSize, true);
        resizeKey = Bitmap.createScaledBitmap(key, tileSize, tileSize, true);
        resizePosion = Bitmap.createScaledBitmap(potion, tileSize, tileSize, true);
        resizeMonster = Bitmap.createScaledBitmap(monster, tileSize, tileSize, true);
        resizePlayerRight = Bitmap.createScaledBitmap(playerRight,tileSize,tileSize,false);
        resizeArmor = Bitmap.createScaledBitmap(armor,tileSize,tileSize,false);
        resizeWeapon = Bitmap.createScaledBitmap(weapon,tileSize,tileSize,false);

    }

    public void update() {
        // update game state
        joystick.update(pointX,pointY);
        player.update(joystick,tileSize,width,height);

        // make player cannot walk through the wall
        Iterator<Walls> iteratorWalls= walls.iterator();
        while (iteratorWalls.hasNext()){
            if (Walls.isCollision(iteratorWalls.next(),player)){
                player.setPlayerPositionX(player.getLastX());
                player.setPlayerPositionY(player.getLastY());
            }
        }
        Iterator<Potion> iteratorPotion= potions.iterator();
        while (iteratorPotion.hasNext()){
            if (Potion.isCollision(iteratorPotion.next(),player)){
                player.setHP(10);
                iteratorPotion.remove();
            }
        }

        Iterator<Coins> iteratorCoins= coins.iterator();
        while (iteratorCoins.hasNext()){
            if (Coins.isCollision(iteratorCoins.next(),player)){
                player.setCoin(1);
                iteratorCoins.remove();
            }
        }

        Iterator<Key> iteratorKeys= keys.iterator();
        while (iteratorKeys.hasNext()){
            if (Key.isCollision(iteratorKeys.next(),player)){
                player.setKey(1,true);
                iteratorKeys.remove();
            }

        }
        Iterator<Door> iteratorDoors= doors.iterator();
        while (iteratorDoors.hasNext()){
            if (Door.isCollision(iteratorDoors.next(),player)){
                if(player.getPlayerKeys()>0) {
                    player.setKey(1,false);
                    iteratorDoors.remove();
                }else{
                    player.setPlayerPositionX(player.getLastX());
                    player.setPlayerPositionY(player.getLastY());
                }
            }
        }

        Iterator<Weapon> iteratorWeapon= weapons.iterator();
        while (iteratorWeapon.hasNext()){
            if (Weapon.isCollision(iteratorWeapon.next(),player)){
                player.setPlayerWeapon(true);
                player.setPlayerAttack(50);
                iteratorWeapon.remove();
            }
        }
        Iterator<Armor> iteratorArmor= armors.iterator();
        while (iteratorArmor.hasNext()){
            if (Armor.isCollision(iteratorArmor.next(),player)){
                player.setPlayerArmor(true);
                player.setPlayerDefend(50);
                iteratorArmor.remove();
            }
        }

        Iterator<Monster> iteratorMonsters= monsters.iterator();
        while (iteratorMonsters.hasNext()){
            if (Monster.isCollision(iteratorMonsters.next(),player)){
                diff=monsters.get(0).getMonsterAttack()-player.getPlayerAttack();
                player.setCoin(5);
                if(diff>0) {
                    damageToPlayer = diff - player.getPlayerDefend();

                    if (damageToPlayer >0) {
                        player.setLessHp(damageToPlayer);
                    }
                    if(player.getPlayerHp()>0) {
                        iteratorMonsters.remove();
                    }
                }else if (diff<=0){
                    iteratorMonsters.remove();
                }
            }
            if (player.getPlayerHp() <= 0 ){
                boolean isWin = false;
                SharedPreferences pref = context.getSharedPreferences("test",Context.MODE_PRIVATE);
                pref.edit()
                        .putBoolean("isWin",isWin)
                        .commit();
                getContext().startActivity(new Intent(getContext(), GameOver.class));
                ((Activity) context).finish();

            }
        }
        if(stage == 2) {
            if (Rect.intersects(bossRects, playerRect)) {
                diff = boss.getBossAttack() - player.getPlayerAttack();
                if (diff > 0) {
                    damageToPlayer = diff - player.getPlayerDefend();

                    if (damageToPlayer > 0) {
                        player.setLessHp(damageToPlayer);
                    }
                    if (player.getPlayerHp() > 0) {
                        boolean isWin = true;
                        SharedPreferences pref = context.getSharedPreferences("test",Context.MODE_PRIVATE);
                        pref.edit()
                                .putBoolean("isWin",isWin)
                                .commit();
                        getContext().startActivity(new Intent(getContext(), GameOver.class));
                        ((Activity) context).finish();
                    }
                } else if (diff <= 0) {
                    boolean isWin = true;
                    SharedPreferences pref = context.getSharedPreferences("test",Context.MODE_PRIVATE);
                    pref.edit()
                            .putBoolean("isWin",isWin)
                            .commit();
                    getContext().startActivity(new Intent(getContext(), GameOver.class));
                    ((Activity) context).finish();
                }
                if (player.getPlayerHp() <= 0) {
                    boolean isWin = false;
                    SharedPreferences pref = context.getSharedPreferences("test",Context.MODE_PRIVATE);
                    pref.edit()
                            .putBoolean("isWin",isWin)
                            .commit();
                    getContext().startActivity(new Intent(getContext(), GameOver.class));
                    ((Activity) context).finish();

                }
            }
        }


        if (Rect.intersects(playerRect,stairRects.get(0))){
            stage =2;
            player.setPlayerPositionX(0);
            player.setPlayerPositionY(tileSize*5);

            armors.clear();
            armorRects.clear();
            walls.clear();
            wallRects.clear();
            stairs.clear();
            doors.clear();
            doorRects.clear();
            keys.clear();
            keyRects.clear();
            potions.clear();
            potionRects.clear();
            coins.clear();
            coinRects.clear();
            monsters.clear();
            monsterRects.clear();
            stairRects.get(0).set(tileSize*15,0,tileSize*16,0);
            stage2();
        }
    }

    public Boolean getIsWin(){return isWin;}

    public void stage2(){
        //new monster Rect
        monsterRects.add(new Rect(tileSize*5,tileSize*2,tileSize*6,tileSize*3));
        monsterRects.add(new Rect(tileSize*2,tileSize*9,tileSize*3,tileSize*10));
        monsterRects.add(new Rect(tileSize*4,tileSize*5,tileSize*5,tileSize*6));
        monsterRects.add(new Rect(tileSize*4,tileSize*6,tileSize*5,tileSize*7));
        //new monster
        monsters.add(new Monster((int) ((Math.random()*100)+200),tileSize*5,tileSize*2,monsterRects.get(0)));
        monsters.add(new Monster((int) ((Math.random()*100)+200),tileSize*2,tileSize*9,monsterRects.get(1)));
        monsters.add(new Monster((int) ((Math.random()*100)+200),tileSize*4,tileSize*5,monsterRects.get(2)));
        monsters.add(new Monster((int) ((Math.random()*100)+200),tileSize*4,tileSize*6,monsterRects.get(3)));

        //new key Rect
        keyRects.add(new Rect(0,0,tileSize,tileSize));
        keyRects.add(new Rect(tileSize*2,tileSize*2,tileSize*3,tileSize*3));
        keyRects.add(new Rect(tileSize*3,tileSize*9,tileSize*4,tileSize*10));
        //new key
        keys.add(new Key(keyRects.get(0),0,0));
        keys.add(new Key(keyRects.get(1),tileSize*2,tileSize*2));
        keys.add(new Key(keyRects.get(2),tileSize*3,tileSize*9));

        //new weapon Rect
        weaponRects.add(new Rect(tileSize*5,0,tileSize*6,tileSize));
        //new weapon
        weapons.add(new Weapon(weaponRects.get(0),tileSize*5,0));

        //new coin Rect
        coinRects.add(new Rect(tileSize,tileSize*9,tileSize*2,tileSize*10));
        //new coins
        coins.add(new Coins(1,coinRects.get(0),tileSize,tileSize*9));

        //new potion Rect
        potionRects.add(new Rect(tileSize*2,0,tileSize*3,tileSize));
        potionRects.add(new Rect(tileSize*6,0,tileSize*7,tileSize));
        potionRects.add(new Rect(tileSize*5,tileSize,tileSize*6,tileSize*2));
        potionRects.add(new Rect(tileSize*7,tileSize*2,tileSize*8,tileSize*3));
        potionRects.add(new Rect(0,tileSize*9,tileSize,tileSize*10));
        potionRects.add(new Rect(tileSize*5,tileSize*9,tileSize*6,tileSize*10));
        potionRects.add(new Rect(tileSize*6,tileSize*9,tileSize*7,tileSize*10));
        potionRects.add(new Rect(tileSize*7,tileSize*9,tileSize*8,tileSize*10));
        potionRects.add(new Rect(tileSize*5,tileSize*10,tileSize*6,tileSize*11));
        potionRects.add(new Rect(tileSize*6,tileSize*10,tileSize*7,tileSize*11));
        potionRects.add(new Rect(tileSize*7,tileSize*10,tileSize*8,tileSize*11));
        // new potions
        potions.add(new Potion(10,tileSize*2,0,potionRects.get(0)));
        potions.add(new Potion(10,tileSize*6,0,potionRects.get(1)));
        potions.add(new Potion(10,tileSize*5,tileSize,potionRects.get(2)));
        potions.add(new Potion(10,tileSize*7,tileSize*2,potionRects.get(3)));
        potions.add(new Potion(10,0,tileSize*9,potionRects.get(4)));
        potions.add(new Potion(10,tileSize*5,tileSize*9,potionRects.get(5)));
        potions.add(new Potion(10,tileSize*6,tileSize*9,potionRects.get(6)));
        potions.add(new Potion(10,tileSize*7,tileSize*9,potionRects.get(7)));
        potions.add(new Potion(10,tileSize*5,tileSize*10,potionRects.get(8)));
        potions.add(new Potion(10,tileSize*6,tileSize*10,potionRects.get(9)));
        potions.add(new Potion(10,tileSize*7,tileSize*10,potionRects.get(10)));

        //new boss Rect
        bossRects = new Rect(tileSize*5+tileSize/2,tileSize*4+tileSize/2,
                tileSize*7+tileSize/2,tileSize*7+tileSize/2);
        //new boss
        boss = new Boss(400,tileSize*5+tileSize/2,tileSize*4+tileSize/2,bossRects);

        // new wall Rect
        wallRects.add(new Rect(0,tileSize*3,tileSize,tileSize*4));
        wallRects.add(new Rect(tileSize,tileSize*3,tileSize*2,tileSize*4));
        wallRects.add(new Rect(tileSize*3,tileSize*3,tileSize*4,tileSize*4));
        wallRects.add(new Rect(tileSize*4,tileSize*3,tileSize*5,tileSize*4));
        wallRects.add(new Rect(tileSize*5,tileSize*3,tileSize*6,tileSize*4));
        wallRects.add(new Rect(tileSize*6,tileSize*3,tileSize*7,tileSize*4));
        wallRects.add(new Rect(tileSize*7,tileSize*3,tileSize*8,tileSize*4));
        wallRects.add(new Rect(tileSize*4,tileSize*4,tileSize*5,tileSize*5));
        wallRects.add(new Rect(tileSize*4,tileSize*7,tileSize*5,tileSize*8));
        wallRects.add(new Rect(0,tileSize*8,tileSize,tileSize*9));
        wallRects.add(new Rect(tileSize*2,tileSize*8,tileSize*3,tileSize*9));
        wallRects.add(new Rect(tileSize*3,tileSize*8,tileSize*4,tileSize*9));
        wallRects.add(new Rect(tileSize*4,tileSize*8,tileSize*5,tileSize*9));
        wallRects.add(new Rect(tileSize*5,tileSize*8,tileSize*6,tileSize*9));
        wallRects.add(new Rect(tileSize*6,tileSize*8,tileSize*7,tileSize*9));
        wallRects.add(new Rect(tileSize*7,tileSize*8,tileSize*8,tileSize*9));
        wallRects.add(new Rect(tileSize*4,0,tileSize*5,tileSize));
        wallRects.add(new Rect(tileSize*4,tileSize,tileSize*5,tileSize*2));
        wallRects.add(new Rect(tileSize*4,tileSize*10,tileSize*5,tileSize*11));
        // new wall
        walls.add(new Walls(0,tileSize*3,tileSize,wallRects.get(0)));
        walls.add(new Walls(tileSize,tileSize*3,tileSize,wallRects.get(1)));
        walls.add(new Walls(tileSize*3,tileSize*3,tileSize,wallRects.get(2)));
        walls.add(new Walls(tileSize*4,tileSize*3,tileSize,wallRects.get(3)));
        walls.add(new Walls(tileSize*5,tileSize*3,tileSize,wallRects.get(4)));
        walls.add(new Walls(tileSize*6,tileSize*3,tileSize,wallRects.get(5)));
        walls.add(new Walls(tileSize*7,tileSize*3,tileSize,wallRects.get(6)));
        walls.add(new Walls(tileSize*4,tileSize*4,tileSize,wallRects.get(7)));
        walls.add(new Walls(tileSize*4,tileSize*7,tileSize,wallRects.get(8)));
        walls.add(new Walls(0,tileSize*8,tileSize,wallRects.get(9)));
        walls.add(new Walls(tileSize*2,tileSize*8,tileSize,wallRects.get(10)));
        walls.add(new Walls(tileSize*3,tileSize*8,tileSize,wallRects.get(11)));
        walls.add(new Walls(tileSize*4,tileSize*8,tileSize,wallRects.get(12)));
        walls.add(new Walls(tileSize*5,tileSize*8,tileSize,wallRects.get(13)));
        walls.add(new Walls(tileSize*6,tileSize*8,tileSize,wallRects.get(14)));
        walls.add(new Walls(tileSize*7,tileSize*8,tileSize,wallRects.get(15)));
        walls.add(new Walls(tileSize*4,0,tileSize,wallRects.get(16)));
        walls.add(new Walls(tileSize*4,tileSize,tileSize,wallRects.get(17)));
        walls.add(new Walls(tileSize*4,tileSize*10,tileSize,wallRects.get(18)));

        //new doorRects
        doorRects.add(new Rect(tileSize*2,tileSize*3,tileSize*3,tileSize*4));
        doorRects.add(new Rect(tileSize,tileSize*8,tileSize*2,tileSize*9));
        doorRects.add(new Rect(tileSize*4,tileSize*2,tileSize*5,tileSize*3));
        doorRects.add(new Rect(tileSize*4,tileSize*9,tileSize*5,tileSize*10));
        //new door
        doors.add(new Door(doorRects.get(0),tileSize*2,tileSize*3));
        doors.add(new Door(doorRects.get(1),tileSize,tileSize*8));
        doors.add(new Door(doorRects.get(2),tileSize*4,tileSize*2));
        doors.add(new Door(doorRects.get(3),tileSize*4,tileSize*9));

    }


    public void pasueGame(){
        gameLoop.stopLoop();
    }

    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        if (!showFPS)
            canvas.drawText(String.valueOf(gameLoop.averageFPS),tileSize,tileSize,paint);
        canvas.drawBitmap(resizeBg,0,0,null);
        if (stage == 1) {
            player.draw(canvas, resizePlayer,resizePlayerRight, width, height, tileSize);
        }else if(stage ==2){
            player.draw(canvas,resizePlayer,resizePlayerRight,tileSize*4,0,tileSize);
        }
        if(stage ==2)
            boss.draw(canvas,resizeBoss,tileSize);
        for (Potion potion: potions) {
            potion.draw(canvas,resizePosion);
        }
        for (Walls wall: walls) {
            wall.draw(canvas, resizeWall, tileSize);
        }
        for (Coins coin:coins) {
            coin.draw(canvas,resizeCoin);
        }
        for (Key key:keys) {
            key.draw(canvas,resizeKey);
        }
        for (Door door:doors) {
            door.draw(canvas,resizeDoor);
        }
        for (Stair stair:stairs) {
            stair.draw(canvas,resizeStair);
        }
        for (Monster monster:monsters){
            monster.draw(canvas,resizeMonster,tileSize);
        }
        for (Weapon weapon:weapons){
            weapon.draw(canvas,resizeWeapon);
        }
        for (Armor armor:armors){
            armor.draw(canvas,resizeArmor);
        }
        if (joystick.getIsPressed()) {
            joystick.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //touch event actions
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //if (joystick.isPressed((float)event.getX(),(float)event.getY())){
                joystick.setIsPressed(true);
                if(joystick.getIsPressed()){
                    pointX = event.getX();
                    pointY = event.getY();
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (joystick.getIsPressed()){
                    joystick.setActuator((float)event.getX(),(float)event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                joystick.setIsPressed(false);
                joystick.resetActuator();
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        if (gameLoop.getState().equals(Thread.State.TERMINATED)){
            gameLoop =new GameLoop(this,holder);
        }
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
    }

    @Override
    public void run() {

    }
}
