package com.example.androidfirstproject;

import java.util.Random;
import java.util.Vector;

import com.example.androidfirstproject.R;
import com.example.androidfirstproject.Boom;
import com.example.androidfirstproject.Boss;
import com.example.androidfirstproject.Bullet;
import  com.example.androidfirstproject.GameSoundPool;
import  com.example.androidfirstproject.MySurfaceView;
import com.example.androidfirstproject.MainActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

//������SurfaceView���߳���ֱ�Ӹ���UI
//ͨ��������Ϸ��ͼ����Ƶ����
//����SurfaceView�������Եõ�һ�����ڻ�ͼ�����򣬳�֮ΪSurface
//Callback�ص�������ʵ�ָú���ΪSurfaceView������������
public class MySurfaceView extends SurfaceView implements Callback, Runnable {

	// SurfaceView������ܣ��������SurfaceView��ͼ����������SurfaceView����
	SurfaceHolder sfh;
	// �ж�ѭ������
	boolean flag;
	Canvas canvas;
	Paint paint;
	Bitmap enemyOne;//�л�
	Bitmap enemyTwo;//�л�
	Bitmap enemyThree;//�л�
	 Bitmap bitmap;// �����ӵ�
	static  Bitmap bmpEnemyBullet1;// �л��ӵ�
	static  Bitmap bmpEnemyBullet2;// �л��ӵ�
	static  Bitmap bmpEnemyBullet3;// �л��ӵ�
	 Bitmap bmpBoss;//boss
	 static Bitmap bmpBossBullet;// Boss�ӵ�
	Bitmap mainmenu;
	Bitmap bk1;
	Bitmap logo;
	Bitmap warning;
	Bitmap startButton;
	Bitmap startButtonPress;
	Bitmap text;
	private Bitmap bmpPause;// ��ͣ
	private Bitmap bmpGoon;// ����
	static Bitmap Bplayer1;//����
	Bitmap hp;//Ѫ��
	Bitmap bmpGameWin;
	Bitmap bmpGameLost;
	private Bitmap bmpBoom;// ��ըЧ��
	private Bitmap bmpBoosBoom;// Boos��ըЧ��
	static int ScreenW, ScreenH;
	// ��Ϸ��Ч��
		public static GameSoundPool sounds;
		public boolean isCollision;
	GameMenu gameMenu;
	GameBg gameBg;
	Player player;
	Random random;
	boolean isBoss;
		// Boss�Ƿ�����
		private boolean isBossDead;
		
	public static final int GAME_MENU = 1;
	public static final int GAMING = 2;
	public static final int GAME_OVER = 3;
	public static final int GAME_WIN = 4;
	public static final int GAME_PAUSE = -1;
	//��ǰ��Ϸ״̬
	public static int gameState = GAME_MENU;
	public static boolean gameGoon = false;// ������Ϸ
	private PauseOrGoOn pause;
	// �����ӵ�����
		private Vector<Bullet> vcBulletPlayer;
		//�л�
		private Vector<Enemy> enemyList;
		// �л��ӵ�����
		private Vector<Bullet> vcBullet;
		
		// ��ըЧ������
		private Vector<Boom> vcBoom;
		// ����Boss
		private Boss boss;
		// Boss���ӵ�����
		public static Vector<Bullet> vcBulletBoss;
		private int score = 0;
		// ����һ���˵�����
	int count=0;//��ʱ��
	int createPlayerBullet=8;
	int countEnemyBullet=0;
	int createEnemy =35;
	// AI����
	int[][] enemyArray = { { 1, 2 }, { 2, 3 }, { 1, 2, 3 }, { 1, 2, 3 },
			{ 1, 2 } ,{1,3,2},{3,2,1},{1,3},{2,1,3},{3,1,2},{1,1,2},{2,2,3},{1,3,3},{2,2,1},{1,3},{0},{0},{-1}};
	// ��������
	int enemyArrayIndex;

	public MySurfaceView(Context context, GameSoundPool sounds) {
		super(context);
		// TODO Auto-generated constructor stub
		// ͨ����ǰSurfaceView�����ȡ�������
		sfh = this.getHolder();
		// ��ӻص������������ڵ�SurfaceView����
		sfh.addCallback(this);
		flag = true;

		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.YELLOW);
		// ���ñ�������
				this.setKeepScreenOn(true);
				MySurfaceView.sounds = sounds;

	}

	// Surface��ʼ������ʱ����
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		ScreenW = this.getWidth();
		ScreenH = this.getHeight();
		initialize();

		new Thread(this).start();
	}

	private void initialize() {
		score=0;
		// ������Ϸ�����̨���½�����Ϸʱ����Ϸ������!
				// ����Ϸ״̬���ڲ˵�ʱ���Ż�������Ϸ
				if (gameState == GAME_MENU) {
					// ������Ϸ��Դ
		mainmenu = BitmapFactory.decodeResource(getResources(),
				R.drawable.menu);
		logo = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
		startButton = BitmapFactory.decodeResource(getResources(),
				R.drawable.menustart);
		startButtonPress = BitmapFactory.decodeResource(getResources(),
				R.drawable.menustartpress);
		text = BitmapFactory.decodeResource(getResources(),
				R.drawable.starttext);
		bk1 = BitmapFactory.decodeResource(getResources(), R.drawable.bk);
		Bplayer1 = BitmapFactory.decodeResource(getResources(),
				R.drawable.player);
		hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp);
		gameMenu = new GameMenu(mainmenu, logo, startButton, startButtonPress,text);
		gameBg = new GameBg(bk1);
		player = new Player(Bplayer1,hp,score);
		
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mybullet);
		enemyOne = BitmapFactory.decodeResource(getResources(),
				R.drawable.img_t);
		enemyTwo = BitmapFactory.decodeResource(getResources(),
				R.drawable.enemy_pig);
		enemyThree = BitmapFactory.decodeResource(getResources(),
				R.drawable.img_ph);
		bmpEnemyBullet1 = BitmapFactory.decodeResource(getResources(),
				R.drawable.enemybullet);
		bmpEnemyBullet2 = BitmapFactory.decodeResource(getResources(),
				R.drawable.img_bullet);
		bmpEnemyBullet3= BitmapFactory.decodeResource(getResources(),
				R.drawable.img_b);
		bmpBossBullet = BitmapFactory.decodeResource(getResources(),
				R.drawable.boosbullet);
		 bmpBoss= BitmapFactory.decodeResource(getResources(),
					R.drawable.bossplane);
		 warning=BitmapFactory.decodeResource(getResources(),
					R.drawable.warning);
		 random = new Random();
			bmpBoom = BitmapFactory.decodeResource(getResources(), R.drawable.boom);
			bmpBoosBoom=BitmapFactory.decodeResource(getResources(), R.drawable.boos_boom);
			bmpGameWin = BitmapFactory.decodeResource(getResources(), R.drawable.gamewin);
			bmpGameLost = BitmapFactory.decodeResource(getResources(), R.drawable.gamelost);
			bmpPause = BitmapFactory.decodeResource(getResources(),
					R.drawable.pause);
			bmpGoon = BitmapFactory.decodeResource(getResources(),
					R.drawable.goon);
			// ʵ��boss����
			boss = new Boss( bmpBoss);
			// ʵ��Boss�ӵ�����
			vcBulletBoss = new Vector<Bullet>();
			vcBoom = new Vector<Boom>();
			vcBulletPlayer = new Vector<Bullet>();
			 enemyList=new Vector<Enemy>();
			 vcBullet=new Vector<Bullet>();
				pause = new PauseOrGoOn(bmpPause, bmpGoon, PauseOrGoOn.PAUSE);
				}
	}

	// Surface״̬�ı�ʱ����
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	// Surface����ʱ���ã�һ������ͷ���Դ�Ĳ���
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

		flag = false;
	}

	public void Draw() {
		// ��ͼ����
		// lockCanvas()�������������ڴ�SurfaceView����
		canvas = sfh.lockCanvas();
		if (canvas != null) {
			canvas.drawColor(Color.BLACK);
			switch (gameState) {
			case GAME_MENU:
				gameMenu.Draw(canvas, paint);
			
				break;
			case GAMING:
				gameBg.Draw(canvas, paint);
				pause.draw(canvas, paint);
				canvas.save();
				canvas.scale(2, 2, MySurfaceView.ScreenW - 140, 20);
				canvas.drawText("�÷֣�" + score,
						MySurfaceView.ScreenW - 140, 50, paint);
				canvas.restore();

				player.Draw(canvas, paint);
	
				//���������ӵ�
				for (int i = 0; i <vcBulletPlayer.size(); i++) {
					vcBulletPlayer.elementAt(i).Draw(canvas, paint,Bullet.BULLET_PLAYER,bitmap);
				}
				if (!isBoss) {
					// ���Ƶл�
					for (int i = 0; i < enemyList.size(); i++) {
						if(enemyList.elementAt(i).type!=Enemy.EnemyZero){
						enemyList.elementAt(i).Draw(canvas, paint);
						}else {
							canvas.drawBitmap(warning, 80, 200,paint);
						}
					}
						// �л��ӵ�����
						for (int i = 0; i < vcBullet.size(); i++) {
							Bitmap bmpEnemyBullet=bmpBossBullet;
							switch(vcBullet.elementAt(i).type){
							case Bullet.BULLET_PLAYER:
								 bmpEnemyBullet= bitmap;
							break;
						case Bullet.BULLET_ENEMY1:
							 bmpEnemyBullet= bmpEnemyBullet1;
						break;
						case Bullet.BULLET_ENEMY2:
							 bmpEnemyBullet= bmpEnemyBullet2;
						break;
						case Bullet.BULLET_ENEMY3:
							 bmpEnemyBullet= bmpEnemyBullet3;
                                break;
							}
							vcBullet.elementAt(i).Draw(canvas, paint,vcBullet.elementAt(i).type,bmpEnemyBullet);
						}
				} else {
					if (!isBossDead) {
						// Boss�Ļ���
						boss.draw(canvas, paint);
					}
					// Boss�ӵ��߼�
					for (int i = 0; i < vcBulletBoss.size(); i++) {
						vcBulletBoss.elementAt(i).Draw(canvas, paint,Bullet.BULLET_BOSS,bmpBossBullet);
					}
				}
				// ��ըЧ������
				for (int i = 0; i < vcBoom.size(); i++) {
					vcBoom.elementAt(i).draw(canvas, paint);
					score += 10;
				}
				break;
			case GAME_PAUSE:
				break;
			
			case GAME_WIN:
				canvas.save();
				float sx = (float) (MySurfaceView.ScreenW)
						/ bmpGameWin.getWidth();
				float sy = (float) (MySurfaceView.ScreenH)
						/ bmpGameWin.getHeight();
				canvas.scale(sx, sy);
				canvas.drawBitmap(bmpGameWin, 0, 0, paint);
				canvas.restore();
				break;
			case GAME_OVER:
				canvas.save();
				 sx = (float) (MySurfaceView.ScreenW)
						/ bmpGameLost.getWidth();
				 sy = (float) (MySurfaceView.ScreenH)
						/ bmpGameLost.getHeight();
				canvas.scale(sx, sy);
				canvas.drawBitmap(bmpGameLost, 0, 0, paint);
				canvas.restore();
				break;
			default:break;
			}
		}
		// �����������ͷŻ�������
		if (canvas != null) {
			sfh.unlockCanvasAndPost(canvas);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (flag) {
			long start = System.currentTimeMillis();
			// ��ͼ����
			Draw();
			// �߼�����
			Logic();
			long end = System.currentTimeMillis();
			while (pause.getType() == PauseOrGoOn.GOON) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			try {
				if (end - start < 50) {
					Thread.sleep(50 - (end - start));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (gameGoon == true) {
				gameGoon = false;
				try {
					initialize();
				} catch (Exception e) {
					initialize();
				}
			}
		}
	}

	private void Logic() {
		// TODO Auto-generated method stub
		switch(gameState){
		case GAME_MENU:
			break;
		case GAMING:
			gameBg.Logic();
		player.Logic();
			//��ʱ���������ӵ�
			count++;
				if (count % createPlayerBullet == 0) {
					vcBulletPlayer.add(new Bullet(bitmap,Bullet.BULLET_PLAYER,player.midX-10,
							player.midY - 50));
				}
				// ���������ӵ��߼�
				for (int i = 0; i <vcBulletPlayer.size(); i++) {
					Bullet b = vcBulletPlayer.elementAt(i);
					if (b.isDead) {
							vcBulletPlayer.removeElement(b);
					} else {
						b.logic();
					}
				}
				// ��ըЧ���߼�
				for (int i = 0; i < vcBoom.size(); i++) {
					Boom boom = vcBoom.elementAt(i);
					if (boom.playEnd) {
						// ������ϵĴ�������ɾ��
						vcBoom.removeElementAt(i);
					} else {
						vcBoom.elementAt(i).logic();
					}
				}
				//�л��߼�
				for (int i = 0; i < enemyList.size(); i++) {
					Enemy e = enemyList.elementAt(i);
					if (e.isDead) {
						enemyList.removeElementAt(i);
					} else {
					   e.Logic();
					}
				}
				if (!isBoss) {
					// ��ʱ���ɵл�
					if (count % createEnemy == 0) {
						// ���ɵĵл�������Ҫ���
						for (int i = 0; i < enemyArray[enemyArrayIndex].length; i++) {
							//�� 
							if (enemyArray[enemyArrayIndex][i] == 1) {
								float y = random.nextInt(20) + 50;
								Enemy enemy = new Enemy(enemyOne, 1,
										MySurfaceView.ScreenW + 20, y);
								enemyList.addElement(enemy);
								
							//��
							} else if (enemyArray[enemyArrayIndex][i] == 2) {
								float x = random.nextInt(MySurfaceView.ScreenW-100)-20;
								Enemy enemy = new Enemy(enemyTwo, 2,
										x, -50);
								enemyList.addElement(enemy);
								//��
							} else if (enemyArray[enemyArrayIndex][i] == 3) {
								float y = random.nextInt(20) +10;
								enemyList.addElement(new Enemy(enemyThree, 3,
										-50, y));
							}else if(enemyArray[enemyArrayIndex][i] == 0){
								enemyList.addElement(new Enemy(warning, 0,
										80, 200));
							}
						}
						if (enemyArrayIndex < enemyArray.length - 1) {
							enemyArrayIndex++;
						} else {
							isBoss = true;
						}

					}
				
					// ����л������ǵ���ײ
					for (int i = 0; i < enemyList.size(); i++) {
						if (player.isCollsionWith(enemyList.elementAt(i))) {
							// ������ײ������Ѫ��-1
							player.setPlayerHp(player.getPlayerHp() - 1);
							sounds.playSound(3, 0);
							// ������Ѫ��С��0���ж���Ϸʧ��
							if (player.getPlayerHp() <= -1) {
								gameState = GAME_OVER;
							}
						}
					}
					
					countEnemyBullet++;
					if (countEnemyBullet % createEnemy == 0) {
						for (int i = 0; i <enemyList.size(); i++) {
							Enemy e =enemyList.elementAt(i);
						// ��ͬ���͵л���ͬ���ӵ����й켣
							int type = 0;
							Bitmap bmpEnemyBullet=bmpBossBullet;
							switch (e.type) {
							
						case Enemy.EnemyOne:
								type = Bullet.BULLET_ENEMY1;
								bmpEnemyBullet=bmpEnemyBullet1;
								break;
						
							case Enemy.EnemyTwo:
								type = Bullet.BULLET_ENEMY2;
								bmpEnemyBullet=bmpEnemyBullet2;
								break;
							
							case Enemy.EnemyThree:
								type = Bullet.BULLET_ENEMY3;
								bmpEnemyBullet=bmpEnemyBullet3;
								break;
							}
							vcBullet.add(new Bullet(bmpEnemyBullet, type,e.x+40,
									e.y+40));
						}
					}
					// ����л��ӵ��߼�
				for (int i = 0; i < vcBullet.size(); i++) {
						Bullet b = vcBullet.elementAt(i);
						if (b.isDead) {
							vcBullet.removeElement(b);
						} else {
							b.logic();
						}
					}
				// ����л��ӵ���������ײ
				for (int i = 0; i < vcBullet.size(); i++) {
					if (player.isCollsionWith(vcBullet.elementAt(i))) {
						// ������ײ������Ѫ��-1
						player.setPlayerHp(player.getPlayerHp() - 1);
						sounds.playSound(3, 0);
						// ������Ѫ��С��0���ж���Ϸʧ��
						if (player.getPlayerHp() <= -1) {
							gameState = GAME_OVER;
						}
					}
				}
				// ���������ӵ���л���ײ
				for (int i = 0; i < vcBulletPlayer.size(); i++) {
					// ȡ�������ӵ�������ÿ��Ԫ��
					Bullet blPlayer = vcBulletPlayer.elementAt(i);
					for (int j = 0; j <enemyList.size(); j++) {
						// ��ӱ�ըЧ��
						// ȡ���л�������ÿ��Ԫ�������ӵ������ж�
						if (enemyList.elementAt(j).isCollsionWith(blPlayer)) {
							vcBoom.add(new Boom(bmpBoom,
									enemyList.elementAt(j).x+30,enemyList.elementAt(j).y+30, 7));
							sounds.playSound(2, 0);
						}
					}
				}
				} else {
					// ����BOSS
					boss.logic();
					if (count % 20 == 0) {
						// Boss��û����֮ǰ����ͨ�ӵ�
						vcBulletBoss.add(new Bullet(bmpBossBullet,Bullet.BULLET_ENEMY1,boss.x + 35,
								boss.y + 40));
					}
					// Boss�ӵ��߼�
					for (int i = 0; i < vcBulletBoss.size(); i++) {
						Bullet b = vcBulletBoss.elementAt(i);
						if (b.isDead) {
							vcBulletBoss.removeElement(b);
						} else {
							b.logic();
						}
					}
					// Boss�ӵ������ǵ���ײ
					for (int i = 0; i < vcBulletBoss.size(); i++) {
						if (player.isCollsionWith(vcBulletBoss.elementAt(i))) {
							// ������ײ������Ѫ��-
							player.setPlayerHp(player.getPlayerHp() - 1);
							// ������Ѫ��С��0���ж���Ϸʧ��
							if (player.getPlayerHp() <= -1) {
								gameState = GAME_OVER;
							}
						}
					}
					// Boss�������ӵ����У�������ըЧ��
					for (int i = 0; i < vcBulletPlayer.size(); i++) {
						Bullet b = vcBulletPlayer.elementAt(i);
						if (boss.isCollsionWith(b)) {
							boss.setHp(boss.hp - 1);
							if (boss.hp <= 15&&boss.hp>0) {
								vcBoom.add(new Boom(bmpBoosBoom, boss.x + 25,
										boss.y + 30, 5));
								// ��Boss���������Boss��ըЧ��
								vcBoom.add(new Boom(bmpBoosBoom, boss.x + 35,
										boss.y + 40, 5));
								vcBoom.add(new Boom(bmpBoosBoom, boss.x + 40,
										boss.y + 25, 5));
								vcBoom.add(new Boom(bmpBoosBoom, boss.x + 55,
										boss.y + 50, 5));
								vcBoom.add(new Boom(bmpBoosBoom, boss.x + 45,
										boss.y + 35, 5));
								vcBoom.add(new Boom(bmpBoosBoom, boss.x + 30,
										boss.y + 45, 5));
								vcBoom.add(new Boom(bmpBoosBoom, boss.x + 33,
										boss.y + 40, 5));
								vcBoom.add(new Boom(bmpBoosBoom, boss.x + 42,
										boss.y + 45, 5));
								vcBoom.add(new Boom(bmpBoosBoom, boss.x + 60,boss.y + 30, 5));
								
							} 
							if(boss.hp<=0)
								{
								isBossDead = true;
					
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								sounds.playSound(4, 0);
								// ��Ϸʤ��
								gameState = GAME_WIN;
								}
							if(boss.hp>15) {
								// ��ʱɾ��������ײ���ӵ�����ֹ�ظ��ж����ӵ���Boss��ײ��
								b.isDead = true;
								
								// ��Boss���������Boss��ըЧ��
								vcBoom.add(new Boom(bmpBoosBoom, boss.x + 25,
										boss.y + 30, 5));
								vcBoom.add(new Boom(bmpBoosBoom, boss.x + 40,
										boss.y + 40, 5));
								vcBoom.add(new Boom(bmpBoosBoom, boss.x + 55,
										boss.y + 50, 5));
								sounds.playSound(2, 0);
							}
							
						}
					}
				}
			
				
			break;
		}
	}

	// MotionEvent�������¼�,ÿ�δ����¼���������һ��Event����
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (gameState) {
		case GAME_MENU:
			// ����ť�¼�����
			gameMenu.onTouchEvent(event);
			break;
		case GAMING:
			if (pause.onTouchEvent(event) == false) {
				if (pause.getType() == PauseOrGoOn.GOON) {
					return false;
				}
			player.onTouchEvent(event);
			}
			break;
		case GAME_PAUSE:
			break;
		case GAME_WIN:
			break;
		case GAME_OVER:

			break;
		}

		// ����true�������¼�����
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// ����back���ذ���
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// ��Ϸʤ����ʧ�ܡ�����ʱ��Ĭ�Ϸ��ز˵�
			if (gameState == GAMING || gameState == GAME_WIN
					|| gameState == GAME_OVER) {
				gameState = GAME_MENU;
				// Boss״̬����Ϊû����
				isBoss = false;
				// ������Ϸ
				 initialize();
				// ���ù������
				enemyArrayIndex = 0;
			} else if (gameState == GAME_MENU) {
				// ��ǰ��Ϸ״̬�ڲ˵����棬Ĭ�Ϸ��ذ����˳���Ϸ
				MainActivity.instance.finish();
				System.exit(0);
			}
			// ��ʾ�˰����Ѵ������ٽ���ϵͳ����
			// �Ӷ�������Ϸ�������̨
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * ����̧���¼�����
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// ����back���ذ���
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// ��Ϸʤ����ʧ�ܡ�����ʱ��Ĭ�Ϸ��ز˵�
			if (gameState == GAMING || gameState == GAME_WIN
					|| gameState == GAME_OVER) {
				gameState = GAME_MENU;
			}
			// ��ʾ�˰����Ѵ������ٽ���ϵͳ����
			// �Ӷ�������Ϸ�������̨
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
