package com.example.androidfirstproject;

import com.example.androidfirstproject.GameSoundPool;
import com.example.androidfirstproject.MainActivity;
import com.example.androidfirstproject.MySurfaceView;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

//界面
public class MainActivity extends Activity {
	// Activity运行呢的线程称之为主线程，又叫UI线程
	// UI线程规则：不能用其他线程直接更新UI
	public static MainActivity instance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		// 设置全屏
				this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
						WindowManager.LayoutParams.FLAG_FULLSCREEN);
				requestWindowFeature(Window.FEATURE_NO_TITLE);
				// 显示自定义的SurfaceView视图
				GameSoundPool gsp = new GameSoundPool(this);
				 
				gsp.initGameSound();
				setContentView(new MySurfaceView(this, gsp));
	}

}
