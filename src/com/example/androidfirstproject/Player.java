package com.example.androidfirstproject;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class Player{

	public Bitmap player1;
	private int score;
	float x, y;
	Bitmap bitmapHp;
	int HP = 5;
	float hpX, hpY;
	boolean isTouch;
	 float midX, midY;
	float touchX, touchY;
	// 移动速度
	int speed = 10;

	// 碰撞后处于无敌时间
	// 计时器
	private int noCollisionCount = 0;
	// 因为无敌时间
	private int noCollisionTime = 60;
	// 是否碰撞的标识位
	public boolean isCollision;
	public Player(Bitmap player, Bitmap bitmapHp,int score) {
		this.score=score;
		this.player1 = player;
		this.bitmapHp = bitmapHp;

		x = MySurfaceView.ScreenW / 2.0f - player.getWidth() / 2.0f;
		y = MySurfaceView.ScreenH - player.getHeight();
		midX= x + player.getWidth() / 2.0f;
		midY = y + player.getHeight() / 2.0f;
		hpY = 0;
		hpX = 0;
		
	}

	public void Draw(Canvas canvas, Paint paint) {
		if (isCollision) {
			// 每2次游戏循环，绘制一次主角
			if (noCollisionCount % 2 == 0) {
				canvas.drawBitmap(player1, x, y, paint);
			}
		}else {
			canvas.drawBitmap(player1, x, y, paint);
			
		}
		for (int i = 0; i < HP; i++) {
			canvas.drawBitmap(bitmapHp, hpX + i * bitmapHp.getWidth(), hpY,
					paint);
		}
	}
	public Bitmap getBmpPlayer() {
		return player1;
	}

	// 触屏监听
	public void onTouchEvent(MotionEvent event) {

		touchX = event.getX();
		touchY = event.getY();

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// 判断是否触控到主角飞机
			if (touchX > x && touchX < x + player1.getWidth()) {
				if (touchY > y && touchY < y + player1.getHeight()) {
					isTouch = true;
				}
			}

		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (isTouch) {
				// 判断X轴位移增减
				if (touchX > midX) {
					if (midX + speed <= MySurfaceView.ScreenW) {
						midX = midX + speed;
						x= midX - player1.getWidth() / 2.0f;
					}
				} else if (touchX < midX) {
					if (midX - speed >= 0) {
						midX= midX - speed;
						x = midX - player1.getWidth() / 2.0f;
					}

				}
				// 判断Y轴位移增减
				if (touchY > midY) {
					if (midY + speed <= MySurfaceView.ScreenH) {
						midY = midY + speed;
						y = midY - player1.getHeight() / 2.0f;
					}

				} else if (touchY < midY) {
					if (midY - speed >= 0) {
						midY = midY - speed;
						y = midY - player1.getHeight() / 2.0f;
					}

				}
			}

		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			isTouch = false;
		}
	}

	public void Logic() {
		// 处理无敌状态
				if (isCollision) {
		
					// 计时器开始计时
					noCollisionCount++;
					if (noCollisionCount >= noCollisionTime) {
						// 无敌时间过后，接触无敌状态及初始化计数器
						isCollision = false;
						noCollisionCount = 0;
					}
				}
		
	}
	// 设置主角血量
	public void setPlayerHp(int hp) {
		this.HP = hp;
	}

	// 获取主角血量
	public int getPlayerHp() {
		return HP;
	}

	// 判断碰撞(主角与敌机)
		public boolean isCollsionWith(Enemy en) {
			// 是否处于无敌时间
			if (isCollision == false) {
				float x2 = en.x;
				float y2 = en.y;
				float w2 = en.frameW;
				float h2 = en.frameH;
				if (x >= x2 && x >= x2+ w2) {
					return false;
				} else if (x <= x2 && x + player1.getWidth() <= x2) {
					return false;
				} else if (y >= y2 && y >= y2 + h2) {
					return false;
				} else if (y <= y2 && y + player1.getHeight() <= y2) {
					return false;
				}
				// 碰撞即进入无敌状态
				isCollision = true;
				return true;
				// 处于无敌状态，无视碰撞
			} else {
				return false;
			}
		}

		// 判断碰撞(主角与敌机子弹)
		public boolean isCollsionWith(Bullet bullet) {
			// 是否处于无敌时间
			if (isCollision == false) {
				float x2= bullet.x;
				float y2 = bullet.y;
				int w2 = bullet.bitmap.getWidth();
				int h2 = bullet.bitmap.getHeight();
				if (x >= x2 && x >= x2 + w2) {
					return false;
				} else if (x <= x2&& x + player1.getWidth() <= x2){
					return false;
				} else if (y >= y2 && y >= y2 + h2) {
					return false;
				} else if (y <= y2 && y + player1.getHeight() <= y2) {
					return false;
				}
				// 碰撞即进入无敌状态
				isCollision = true;
				return true;
				// 处于无敌状态，无视碰撞
			} else {
				return false;
			}
		}

}
