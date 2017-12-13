package com.example.androidfirstproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Enemy {

	Bitmap Enemy;
	float x, y;
	boolean isDead;
	 public int type;
	int speed;
	public static final int EnemyOne = 1;
	public static final int EnemyTwo = 2;
	public static final int EnemyThree = 3;
	public static final int EnemyZero = 0;
	float frameW, frameH;
	int frameIndex;

	public Enemy(Bitmap Enemy, int type, float x, float y) {
		this.Enemy = Enemy;
		this.type = type;
		this.x = x;
		this.y = y;
		switch (type) {
		case EnemyOne:
			speed = 10;
			break;
		case EnemyTwo:
			speed=20;
			break;
		case EnemyThree:
			speed=15;
			break;
		case EnemyZero:

			break;
		}
		frameW = Enemy.getWidth() / 10;
		frameH = Enemy.getHeight();
	}

	public void Draw(Canvas canvas, Paint paint) {
		canvas.save();
		// 捕捉帧动画矩形
		canvas.clipRect(x, y, x + frameW, y + frameH);
		canvas.drawBitmap(Enemy, x - frameIndex * frameW, y, paint);
		canvas.restore();
	}

	public void Logic() {
		frameIndex++;
		if (frameIndex >= 10) {
			frameIndex = 0;
		}
		switch (type) {
		case EnemyTwo:
			if (isDead == false) {
				//减速出现，加速返回
				speed -= 1;
				y += speed;
				if (y <= -200) {
					isDead = true;
				}
			}
			break;
		case EnemyThree:
			if (isDead == false) {
				//斜右下角运动
				x += speed / 2;
				y += speed;
				if (x > MySurfaceView.ScreenW) {
					isDead = true;
				}
			}
			break;
		case EnemyOne:
			if (isDead == false) {
				//斜左下角运动
				x -= speed / 2;
				y += speed;
				if (x < -50) {
					isDead = true;
				}
			}
			break;
		case EnemyZero:
			break;
		}
	}
	
	//判断碰撞(敌机与主角子弹碰撞)
		public boolean isCollsionWith(Bullet bullet) {
			float x2 = bullet.x;
			float y2 = bullet.y;
			int w2 = bullet.bitmap.getWidth();
			int h2 = bullet.bitmap.getHeight();
			if (x >= x2 && x >= x2 + w2) {
				return false;
			} else if (x <= x2 && x + frameW <= x2) {
				return false;
			} else if (y >= y2 && y >= y2 + h2) {
				return false;
			} else if (y <= y2 && y + frameH <= y2) {
				return false;
			}
			//发生碰撞，让其死亡
			isDead = true;
			return true;
		}
}
