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
	// �ƶ��ٶ�
	int speed = 10;

	// ��ײ�����޵�ʱ��
	// ��ʱ��
	private int noCollisionCount = 0;
	// ��Ϊ�޵�ʱ��
	private int noCollisionTime = 60;
	// �Ƿ���ײ�ı�ʶλ
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
			// ÿ2����Ϸѭ��������һ������
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

	// ��������
	public void onTouchEvent(MotionEvent event) {

		touchX = event.getX();
		touchY = event.getY();

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// �ж��Ƿ񴥿ص����Ƿɻ�
			if (touchX > x && touchX < x + player1.getWidth()) {
				if (touchY > y && touchY < y + player1.getHeight()) {
					isTouch = true;
				}
			}

		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (isTouch) {
				// �ж�X��λ������
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
				// �ж�Y��λ������
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
		// �����޵�״̬
				if (isCollision) {
		
					// ��ʱ����ʼ��ʱ
					noCollisionCount++;
					if (noCollisionCount >= noCollisionTime) {
						// �޵�ʱ����󣬽Ӵ��޵�״̬����ʼ��������
						isCollision = false;
						noCollisionCount = 0;
					}
				}
		
	}
	// ��������Ѫ��
	public void setPlayerHp(int hp) {
		this.HP = hp;
	}

	// ��ȡ����Ѫ��
	public int getPlayerHp() {
		return HP;
	}

	// �ж���ײ(������л�)
		public boolean isCollsionWith(Enemy en) {
			// �Ƿ����޵�ʱ��
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
				// ��ײ�������޵�״̬
				isCollision = true;
				return true;
				// �����޵�״̬��������ײ
			} else {
				return false;
			}
		}

		// �ж���ײ(������л��ӵ�)
		public boolean isCollsionWith(Bullet bullet) {
			// �Ƿ����޵�ʱ��
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
				// ��ײ�������޵�״̬
				isCollision = true;
				return true;
				// �����޵�״̬��������ײ
			} else {
				return false;
			}
		}

}
