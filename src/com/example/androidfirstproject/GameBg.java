package com.example.androidfirstproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class GameBg {
	Bitmap bk1, bk2;
	float x1, y1;
	float x2, y2;

	public GameBg(Bitmap bk) {
		bk1 = bk;
		bk2 = bk;

		y1 = 0;
		y2 = y1 - bk2.getHeight();
	}

	public void Draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(bk1, 0, y1, paint);
		canvas.drawBitmap(bk2, 0, y2, paint);
	}

	public void Logic() {
		y1 = y1 + 10;
		y2 = y2 + 10;
		if (y1 > MySurfaceView.ScreenH) {
			y1 = y2 - bk1.getHeight();
		}
		if (y2 > MySurfaceView.ScreenH) {
			y2 = y1 - bk2.getHeight();
		}
	}
}
