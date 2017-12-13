package com.example.androidfirstproject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

//自定义视图对象，绘制UI视图
public class MyView extends View {

	// 画笔
	Paint paint;

	// Constructor 初始化
	public MyView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		paint = new Paint();
		// 调用系统给定的颜色参数
		// paint.setColor(Color.BLUE);
		// 调用ARGB调色seekBar
		// alpha透明度，0代表完全透明，255代表完全不透明
		paint.setColor(Color.argb(220, 200, 100, 100));
		// 设置画笔圆润
		paint.setAntiAlias(true);
	}

	// 用作绘制AndroidUI视图
	// Canvas画布
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawCircle(100, 100, 100, paint);
		Paint paint1 = new Paint();
		paint1.setColor(Color.RED);
		// 设置文字大小
		paint1.setTextSize(25);
		paint1.setStyle(Style.STROKE);
		canvas.drawText("中国地质大学真牛逼", 100, 350, paint1);

		Paint paint2 = new Paint();
		paint2.setStrokeWidth(9.5f);
		paint2.setColor(Color.BLACK);
		canvas.drawLine(100, 400, 400, 500, paint2);

		canvas.drawRect(100, 450, 300, 600, paint2);

		// Bitmap 位图对象，用作处理图像资源
		// BitmapFactory 位图构造工厂，用作创建位图对象
		// R文件是应用程序配置文件
		// Resources 代表程序res资源目录对象
		Resources res = getResources();
		Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.mainmenu);
		// 矩形缩放
		// Rect对象用作从位图资源中取出某个矩形的资源
		Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		// RectF对象用作适配目标设备中矩形的区域
		RectF rectF = new RectF(0, 0, this.getWidth(), this.getHeight());
		canvas.drawBitmap(bitmap, rect, rectF, paint2);
		// canvas.drawBitmap(bitmap, 0, 0, paint2);
		super.onDraw(canvas);
	}

}
