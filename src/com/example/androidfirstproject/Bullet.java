package com.example.androidfirstproject;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bullet {
Bitmap bitmap;
public float x,y;
int speed;
public static final int BULLET_PLAYER=1;
public static final int BULLET_ENEMY1=2;
public static final int BULLET_ENEMY2=3;
public static final int BULLET_ENEMY3=4;
public static final int BULLET_BOSS=5;

//子弹是否超屏， 优化处理
public boolean isDead;

//子弹的种类以及常量
int type;
//Boss疯狂状态下子弹相关成员变量
private int dir;//当前Boss子弹方向
//8方向常量
public static final int DIR_UP = -1;
public static final int DIR_DOWN = 2;
public static final int DIR_LEFT = 3;
public static final int DIR_RIGHT = 4;
public static final int DIR_UP_LEFT = 5;
public static final int DIR_UP_RIGHT = 6;
public static final int DIR_DOWN_LEFT = 7;
public static final int DIR_DOWN_RIGHT = 8;

public Bullet(Bitmap bitmap,int type,float x,float y){
	this.bitmap=bitmap;
this.type=type;
this.x=x;
this.y=y;
	switch(type){
	case BULLET_PLAYER:speed=18;
		break;
	case BULLET_ENEMY1:speed=7;
	break;
	case BULLET_ENEMY2:speed=10;
	break;
	case BULLET_ENEMY3:speed=12;
	break;
	case BULLET_BOSS:speed=15;
	break;
	}
}
public Bullet(Bitmap bitmap,  int x, int y,int type, int dir) {
	this.bitmap = bitmap;
	this.x = x;
	this.y =y;
	this.type = type;
	speed = 15;
	this.dir = dir;
}


public void Draw(Canvas canvas,Paint paint,int type,Bitmap bitmap){
	switch(type){
	case BULLET_PLAYER:
		canvas.drawBitmap(bitmap, x, y, paint);
	break;
	case BULLET_ENEMY1:
	canvas.drawBitmap(bitmap, x, y+20, paint);
	break;
	case BULLET_ENEMY2:
		canvas.drawBitmap(bitmap, x+50 ,y+60, paint);
	break;
	case BULLET_ENEMY3:
		canvas.drawBitmap(bitmap, x+10, y+20, paint);
	break;
	case BULLET_BOSS:
		canvas.drawBitmap(bitmap, x, y+50, paint);
		break;
	}
}


public void logic(){
	//不同的子弹类型逻辑不一
			//主角的子弹垂直向上运动
			switch (type) {
			case BULLET_PLAYER:
				y -= speed;
				if (y<-50) {
					isDead = true;
				}
				break;
			case BULLET_ENEMY1:
			case BULLET_ENEMY2:
			case BULLET_ENEMY3:
				y += speed;
				if (y > MySurfaceView.ScreenH) {
					isDead = true;
				}
				break;
			case BULLET_BOSS:
				//Boss疯狂状态下的子弹逻辑待实现
				switch (dir) {
				//方向上的子弹
				case DIR_UP:
					y -= speed;
					break;
				//方向下的子弹
				case DIR_DOWN:
					y += speed;
					break;
				//方向左的子弹
				case DIR_LEFT:
					x -= speed;
					break;
				//方向右的子弹
				case DIR_RIGHT:
					x += speed;
					break;
				//方向左上的子弹
				case DIR_UP_LEFT:
					y -= speed;
					x -= speed;
					break;
				//方向右上的子弹
				case DIR_UP_RIGHT:
					x += speed;
					y -= speed;
					break;
				//方向左下的子弹
				case DIR_DOWN_LEFT:
					x -= speed;
					y += speed;
					break;
				//方向右下的子弹
				case DIR_DOWN_RIGHT:
					y += speed;
					x += speed;
					break;
				}
				//边界处理
				if (y> MySurfaceView.ScreenH || y <= -40 || x> MySurfaceView.ScreenW || x<= -40) {
					isDead = true;
				}
				break;
			}
		}
}



