package com.example.androidfirstproject;

import com.example.androidfirstproject.GameSoundPool;
import com.example.androidfirstproject.MainActivity;
import com.example.androidfirstproject.MySurfaceView;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

//����
public class MainActivity extends Activity {
	// Activity�����ص��̳߳�֮Ϊ���̣߳��ֽ�UI�߳�
	// UI�̹߳��򣺲����������߳�ֱ�Ӹ���UI
	public static MainActivity instance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		// ����ȫ��
				this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
						WindowManager.LayoutParams.FLAG_FULLSCREEN);
				requestWindowFeature(Window.FEATURE_NO_TITLE);
				// ��ʾ�Զ����SurfaceView��ͼ
				GameSoundPool gsp = new GameSoundPool(this);
				 
				gsp.initGameSound();
				setContentView(new MySurfaceView(this, gsp));
	}

}
