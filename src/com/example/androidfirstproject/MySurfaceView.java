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

//可以在SurfaceView的线程中直接更新UI
//通常用于游戏绘图和视频播放
//调用SurfaceView对象后可以得到一块用于绘图的区域，称之为Surface
//Callback回调函数，实现该函数为SurfaceView创建生命周期
public class MySurfaceView extends SurfaceView implements Callback, Runnable {

	// SurfaceView构建框架，用于添加SurfaceView绘图所需的组件到SurfaceView对象
	SurfaceHolder sfh;
	// 判断循环条件
	boolean flag;
	Canvas canvas;
	Paint paint;
	Bitmap enemyOne;//敌机
	Bitmap enemyTwo;//敌机
	Bitmap enemyThree;//敌机
	 Bitmap bitmap;// 主角子弹
	static  Bitmap bmpEnemyBullet1;// 敌机子弹
	static  Bitmap bmpEnemyBullet2;// 敌机子弹
	static  Bitmap bmpEnemyBullet3;// 敌机子弹
	 Bitmap bmpBoss;//boss
	 static Bitmap bmpBossBullet;// Boss子弹
	Bitmap mainmenu;
	Bitmap bk1;
	Bitmap logo;
	Bitmap warning;
	Bitmap startButton;
	Bitmap startButtonPress;
	Bitmap text;
	private Bitmap bmpPause;// 暂停
	private Bitmap bmpGoon;// 继续
	static Bitmap Bplayer1;//主角
	Bitmap hp;//血量
	Bitmap bmpGameWin;
	Bitmap bmpGameLost;
	private Bitmap bmpBoom;// 爆炸效果
	private Bitmap bmpBoosBoom;// Boos爆炸效果
	static int ScreenW, ScreenH;
	// 游戏音效池
		public static GameSoundPool sounds;
		public boolean isCollision;
	GameMenu gameMenu;
	GameBg gameBg;
	Player player;
	Random random;
	boolean isBoss;
		// Boss是否死亡
		private boolean isBossDead;
		
	public static final int GAME_MENU = 1;
	public static final int GAMING = 2;
	public static final int GAME_OVER = 3;
	public static final int GAME_WIN = 4;
	public static final int GAME_PAUSE = -1;
	//当前游戏状态
	public static int gameState = GAME_MENU;
	public static boolean gameGoon = false;// 继续游戏
	private PauseOrGoOn pause;
	// 主角子弹容器
		private Vector<Bullet> vcBulletPlayer;
		//敌机
		private Vector<Enemy> enemyList;
		// 敌机子弹容器
		private Vector<Bullet> vcBullet;
		
		// 爆炸效果容器
		private Vector<Boom> vcBoom;
		// 声明Boss
		private Boss boss;
		// Boss的子弹容器
		public static Vector<Bullet> vcBulletBoss;
		private int score = 0;
		// 声明一个菜单对象
	int count=0;//计时器
	int createPlayerBullet=8;
	int countEnemyBullet=0;
	int createEnemy =35;
	// AI数组
	int[][] enemyArray = { { 1, 2 }, { 2, 3 }, { 1, 2, 3 }, { 1, 2, 3 },
			{ 1, 2 } ,{1,3,2},{3,2,1},{1,3},{2,1,3},{3,1,2},{1,1,2},{2,2,3},{1,3,3},{2,2,1},{1,3},{0},{0},{-1}};
	// 数组索引
	int enemyArrayIndex;

	public MySurfaceView(Context context, GameSoundPool sounds) {
		super(context);
		// TODO Auto-generated constructor stub
		// 通过当前SurfaceView对象获取构建框架
		sfh = this.getHolder();
		// 添加回调函数生命周期到SurfaceView对象
		sfh.addCallback(this);
		flag = true;

		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.YELLOW);
		// 设置背景常亮
				this.setKeepScreenOn(true);
				MySurfaceView.sounds = sounds;

	}

	// Surface初始化创建时调用
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
		// 放置游戏切入后台重新进入游戏时，游戏被重置!
				// 当游戏状态处于菜单时，才会重置游戏
				if (gameState == GAME_MENU) {
					// 加载游戏资源
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
			// 实例boss对象
			boss = new Boss( bmpBoss);
			// 实例Boss子弹容器
			vcBulletBoss = new Vector<Bullet>();
			vcBoom = new Vector<Boom>();
			vcBulletPlayer = new Vector<Bullet>();
			 enemyList=new Vector<Enemy>();
			 vcBullet=new Vector<Bullet>();
				pause = new PauseOrGoOn(bmpPause, bmpGoon, PauseOrGoOn.PAUSE);
				}
	}

	// Surface状态改变时调用
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	// Surface销毁时调用，一般加入释放资源的操作
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

		flag = false;
	}

	public void Draw() {
		// 绘图操作
		// lockCanvas()锁定画布，用于从SurfaceView中拿
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
				canvas.drawText("得分：" + score,
						MySurfaceView.ScreenW - 140, 50, paint);
				canvas.restore();

				player.Draw(canvas, paint);
	
				//绘制主角子弹
				for (int i = 0; i <vcBulletPlayer.size(); i++) {
					vcBulletPlayer.elementAt(i).Draw(canvas, paint,Bullet.BULLET_PLAYER,bitmap);
				}
				if (!isBoss) {
					// 绘制敌机
					for (int i = 0; i < enemyList.size(); i++) {
						if(enemyList.elementAt(i).type!=Enemy.EnemyZero){
						enemyList.elementAt(i).Draw(canvas, paint);
						}else {
							canvas.drawBitmap(warning, 80, 200,paint);
						}
					}
						// 敌机子弹绘制
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
						// Boss的绘制
						boss.draw(canvas, paint);
					}
					// Boss子弹逻辑
					for (int i = 0; i < vcBulletBoss.size(); i++) {
						vcBulletBoss.elementAt(i).Draw(canvas, paint,Bullet.BULLET_BOSS,bmpBossBullet);
					}
				}
				// 爆炸效果绘制
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
		// 解锁画布，释放画布对象
		if (canvas != null) {
			sfh.unlockCanvasAndPost(canvas);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (flag) {
			long start = System.currentTimeMillis();
			// 绘图函数
			Draw();
			// 逻辑函数
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
			//定时生成主角子弹
			count++;
				if (count % createPlayerBullet == 0) {
					vcBulletPlayer.add(new Bullet(bitmap,Bullet.BULLET_PLAYER,player.midX-10,
							player.midY - 50));
				}
				// 处理主角子弹逻辑
				for (int i = 0; i <vcBulletPlayer.size(); i++) {
					Bullet b = vcBulletPlayer.elementAt(i);
					if (b.isDead) {
							vcBulletPlayer.removeElement(b);
					} else {
						b.logic();
					}
				}
				// 爆炸效果逻辑
				for (int i = 0; i < vcBoom.size(); i++) {
					Boom boom = vcBoom.elementAt(i);
					if (boom.playEnd) {
						// 播放完毕的从容器中删除
						vcBoom.removeElementAt(i);
					} else {
						vcBoom.elementAt(i).logic();
					}
				}
				//敌机逻辑
				for (int i = 0; i < enemyList.size(); i++) {
					Enemy e = enemyList.elementAt(i);
					if (e.isDead) {
						enemyList.removeElementAt(i);
					} else {
					   e.Logic();
					}
				}
				if (!isBoss) {
					// 定时生成敌机
					if (count % createEnemy == 0) {
						// 生成的敌机类型需要设计
						for (int i = 0; i < enemyArray[enemyArrayIndex].length; i++) {
							//右 
							if (enemyArray[enemyArrayIndex][i] == 1) {
								float y = random.nextInt(20) + 50;
								Enemy enemy = new Enemy(enemyOne, 1,
										MySurfaceView.ScreenW + 20, y);
								enemyList.addElement(enemy);
								
							//上
							} else if (enemyArray[enemyArrayIndex][i] == 2) {
								float x = random.nextInt(MySurfaceView.ScreenW-100)-20;
								Enemy enemy = new Enemy(enemyTwo, 2,
										x, -50);
								enemyList.addElement(enemy);
								//左
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
				
					// 处理敌机与主角的碰撞
					for (int i = 0; i < enemyList.size(); i++) {
						if (player.isCollsionWith(enemyList.elementAt(i))) {
							// 发生碰撞，主角血量-1
							player.setPlayerHp(player.getPlayerHp() - 1);
							sounds.playSound(3, 0);
							// 当主角血量小于0，判定游戏失败
							if (player.getPlayerHp() <= -1) {
								gameState = GAME_OVER;
							}
						}
					}
					
					countEnemyBullet++;
					if (countEnemyBullet % createEnemy == 0) {
						for (int i = 0; i <enemyList.size(); i++) {
							Enemy e =enemyList.elementAt(i);
						// 不同类型敌机不同的子弹运行轨迹
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
					// 处理敌机子弹逻辑
				for (int i = 0; i < vcBullet.size(); i++) {
						Bullet b = vcBullet.elementAt(i);
						if (b.isDead) {
							vcBullet.removeElement(b);
						} else {
							b.logic();
						}
					}
				// 处理敌机子弹与主角碰撞
				for (int i = 0; i < vcBullet.size(); i++) {
					if (player.isCollsionWith(vcBullet.elementAt(i))) {
						// 发生碰撞，主角血量-1
						player.setPlayerHp(player.getPlayerHp() - 1);
						sounds.playSound(3, 0);
						// 当主角血量小于0，判定游戏失败
						if (player.getPlayerHp() <= -1) {
							gameState = GAME_OVER;
						}
					}
				}
				// 处理主角子弹与敌机碰撞
				for (int i = 0; i < vcBulletPlayer.size(); i++) {
					// 取出主角子弹容器的每个元素
					Bullet blPlayer = vcBulletPlayer.elementAt(i);
					for (int j = 0; j <enemyList.size(); j++) {
						// 添加爆炸效果
						// 取出敌机容器的每个元与主角子弹遍历判断
						if (enemyList.elementAt(j).isCollsionWith(blPlayer)) {
							vcBoom.add(new Boom(bmpBoom,
									enemyList.elementAt(j).x+30,enemyList.elementAt(j).y+30, 7));
							sounds.playSound(2, 0);
						}
					}
				}
				} else {
					// 绘制BOSS
					boss.logic();
					if (count % 20 == 0) {
						// Boss的没发疯之前的普通子弹
						vcBulletBoss.add(new Bullet(bmpBossBullet,Bullet.BULLET_ENEMY1,boss.x + 35,
								boss.y + 40));
					}
					// Boss子弹逻辑
					for (int i = 0; i < vcBulletBoss.size(); i++) {
						Bullet b = vcBulletBoss.elementAt(i);
						if (b.isDead) {
							vcBulletBoss.removeElement(b);
						} else {
							b.logic();
						}
					}
					// Boss子弹与主角的碰撞
					for (int i = 0; i < vcBulletBoss.size(); i++) {
						if (player.isCollsionWith(vcBulletBoss.elementAt(i))) {
							// 发生碰撞，主角血量-
							player.setPlayerHp(player.getPlayerHp() - 1);
							// 当主角血量小于0，判定游戏失败
							if (player.getPlayerHp() <= -1) {
								gameState = GAME_OVER;
							}
						}
					}
					// Boss被主角子弹击中，产生爆炸效果
					for (int i = 0; i < vcBulletPlayer.size(); i++) {
						Bullet b = vcBulletPlayer.elementAt(i);
						if (boss.isCollsionWith(b)) {
							boss.setHp(boss.hp - 1);
							if (boss.hp <= 15&&boss.hp>0) {
								vcBoom.add(new Boom(bmpBoosBoom, boss.x + 25,
										boss.y + 30, 5));
								// 在Boss上添加三个Boss爆炸效果
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
								// 游戏胜利
								gameState = GAME_WIN;
								}
							if(boss.hp>15) {
								// 及时删除本次碰撞的子弹，防止重复判定此子弹与Boss碰撞、
								b.isDead = true;
								
								// 在Boss上添加三个Boss爆炸效果
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

	// MotionEvent代表触屏事件,每次触屏事件发生返回一个Event对象
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (gameState) {
		case GAME_MENU:
			// 处理按钮事件方法
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

		// 返回true代表触屏事件发生
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 处理back返回按键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 游戏胜利、失败、进行时都默认返回菜单
			if (gameState == GAMING || gameState == GAME_WIN
					|| gameState == GAME_OVER) {
				gameState = GAME_MENU;
				// Boss状态设置为没出现
				isBoss = false;
				// 重置游戏
				 initialize();
				// 重置怪物出场
				enemyArrayIndex = 0;
			} else if (gameState == GAME_MENU) {
				// 当前游戏状态在菜单界面，默认返回按键退出游戏
				MainActivity.instance.finish();
				System.exit(0);
			}
			// 表示此按键已处理，不再交给系统处理，
			// 从而避免游戏被切入后台
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 按键抬起事件监听
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// 处理back返回按键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 游戏胜利、失败、进行时都默认返回菜单
			if (gameState == GAMING || gameState == GAME_WIN
					|| gameState == GAME_OVER) {
				gameState = GAME_MENU;
			}
			// 表示此按键已处理，不再交给系统处理，
			// 从而避免游戏被切入后台
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
