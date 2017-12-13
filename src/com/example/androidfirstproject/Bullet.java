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

//�ӵ��Ƿ����� �Ż�����
public boolean isDead;

//�ӵ��������Լ�����
int type;
//Boss���״̬���ӵ���س�Ա����
private int dir;//��ǰBoss�ӵ�����
//8������
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
	//��ͬ���ӵ������߼���һ
			//���ǵ��ӵ���ֱ�����˶�
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
				//Boss���״̬�µ��ӵ��߼���ʵ��
				switch (dir) {
				//�����ϵ��ӵ�
				case DIR_UP:
					y -= speed;
					break;
				//�����µ��ӵ�
				case DIR_DOWN:
					y += speed;
					break;
				//��������ӵ�
				case DIR_LEFT:
					x -= speed;
					break;
				//�����ҵ��ӵ�
				case DIR_RIGHT:
					x += speed;
					break;
				//�������ϵ��ӵ�
				case DIR_UP_LEFT:
					y -= speed;
					x -= speed;
					break;
				//�������ϵ��ӵ�
				case DIR_UP_RIGHT:
					x += speed;
					y -= speed;
					break;
				//�������µ��ӵ�
				case DIR_DOWN_LEFT:
					x -= speed;
					y += speed;
					break;
				//�������µ��ӵ�
				case DIR_DOWN_RIGHT:
					y += speed;
					x += speed;
					break;
				}
				//�߽紦��
				if (y> MySurfaceView.ScreenH || y <= -40 || x> MySurfaceView.ScreenW || x<= -40) {
					isDead = true;
				}
				break;
			}
		}
}



