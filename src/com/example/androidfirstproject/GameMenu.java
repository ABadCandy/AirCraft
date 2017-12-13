package com.example.androidfirstproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class GameMenu {

	Bitmap mainmenu;
	Bitmap logo;
	Bitmap startButton;
	Bitmap startButtonPress;
	Bitmap text;

	float buttonX, buttonY;
	boolean isPress;

	public GameMenu(Bitmap mainmenu, Bitmap logo, Bitmap startButton,
			Bitmap startButtonPress, Bitmap text) {
		this.mainmenu = mainmenu;
		this.logo = logo;
		this.startButton = startButton;
		this.startButtonPress = startButtonPress;
		this.text = text;

		buttonX = MySurfaceView.ScreenW / 2.0f - startButton.getWidth() / 2.0f+20;
		buttonY = MySurfaceView.ScreenH * 2.0f / 3+20;
	}

	public void Draw(Canvas canvas, Paint paint) {

		canvas.drawBitmap(mainmenu, 0, 0, paint);
		canvas.drawBitmap(logo, 80,300, paint);
		if (isPress) {
			canvas.drawBitmap(startButtonPress, buttonX, buttonY, paint);
			canvas.drawBitmap(text, buttonX+20, buttonY+10, paint);
		} else {
			canvas.drawBitmap(startButton, buttonX, buttonY, paint);
			canvas.drawBitmap(text, buttonX+20, buttonY+10, paint);
		}

	}

	public void onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		// MotionEvent.ACTION_DOWN ����
		// MotionEvent.ACTION_MOVE �϶�
		// MotionEvent.ACTION_UP ̧��
		float x = event.getX();
		float y = event.getY();
		// getAction()�жϵ�ǰ�����¼�
		if (event.getAction() == MotionEvent.ACTION_DOWN
				|| event.getAction() == MotionEvent.ACTION_MOVE) {
			// �жϰ��µĴ���λ���Ƿ��ڰ�ť��Χ��

			if (x > buttonX && x < buttonX + startButton.getWidth()) {
				if (y > buttonY && y < buttonY + startButton.getHeight()) {
					isPress = true;
				} else {
					isPress = false;
				}
			} else {
				isPress = false;
			}

		} else if (event.getAction() == MotionEvent.ACTION_UP) {

			if (x > buttonX && x < buttonX + startButton.getWidth()) {
				if (y > buttonY && y < buttonY + startButton.getHeight()) {
					isPress = false;
					MySurfaceView.gameState = MySurfaceView.GAMING;
				}
			}
		}

	}
}