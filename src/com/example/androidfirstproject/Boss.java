package com.example.androidfirstproject;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.androidfirstproject.Bullet;
import com.example.androidfirstproject.MySurfaceView;

public class Boss {
	//Boss��Ѫ��
	public int hp = 40;
	//Boss��ͼƬ��Դ
	private Bitmap bmpBoss;
	//Boss����
	public int x, y;
	//Bossÿ֡�Ŀ��
	public int frameW, frameH;
	//Boss��ǰ֡�±�
	private int frameIndex;
	//Boss�˶����ٶ�
	private int speedx = 8;
	private int speedy=8;
	//Boss���˶��켣
	//һ��ʱ���������Ļ�·��˶������ҷ����Χ�ӵ������Ƿ��̬��
	//����״̬�� ���ӵ���ֱ�����˶�
	private boolean isCrazy;
	//������״̬��״̬ʱ����
	private int crazyTime = 200;
	//������
	private int count;
Random random=new Random();
	//Boss�Ĺ��캯��
	public Boss(Bitmap bmpBoss) {
		this.bmpBoss = bmpBoss;
		frameW = bmpBoss.getWidth() / 10;
		frameH = bmpBoss.getHeight();
		//Boss��X�������
		x = MySurfaceView.ScreenW / 2 - frameW / 2;
		y = 0;
	}

	//Boss�Ļ���
	public void draw(Canvas canvas, Paint paint) {
		canvas.save();
		canvas.clipRect(x, y, x + frameW, y + frameH);
		canvas.drawBitmap(bmpBoss, x - frameIndex * frameW, y, paint);
		canvas.restore();
	}

	//Boss���߼�
	public void logic() {
		//����ѭ������֡�γɶ���
		frameIndex++;
		if (frameIndex >= 10) {
			frameIndex = 0;
		}
		//û�з���״̬
		if (isCrazy == false) {
			x += speedx;
			y+=speedy;
			if (x + frameW >= MySurfaceView.ScreenW) {
				speedx=-speedx;
			}
			else if (y+frameH>=MySurfaceView.ScreenH-300) {
				speedy=-speedy;
			}
			else if(x<=0)
			{
				speedx=-speedx;
			}
			else if(y<=0)
			{
				speedy=-speedy;
			}
			count++;
			if (count % crazyTime == 0) {
				isCrazy = true;
				speedx = 24;
				speedy=24;
			}
			//����״̬
		} else {
			speedx -= 1;
			speedy-=1;
			//��Boss����ʱ���������ӵ�
			if (speedx == 0||speedy==0) {
				//���8�����ӵ�
				MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_UP));
				MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_DOWN));
				MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_LEFT));
				MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_RIGHT));
				MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_UP_LEFT));
				MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_UP_RIGHT));
				MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_DOWN_LEFT));
				MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_DOWN_RIGHT));
				
			}
			y += speedy;
			if (y <= 0) {
				//�ָ�����״̬
				isCrazy = false;
				speedx = 8;
				speedy=8;
			}
		}
	}

	//�ж���ײ(Boss�������ӵ�����)
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
		return true;
	}

	//����BossѪ��
	public void setHp(int hp) {
		this.hp = hp;
	}
}
