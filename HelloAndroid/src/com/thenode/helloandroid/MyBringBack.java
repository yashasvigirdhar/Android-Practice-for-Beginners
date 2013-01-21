package com.thenode.helloandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.View;

public class MyBringBack extends View{

	Bitmap gball;
	float changingY;
	Typeface font;
	
	public MyBringBack(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		gball = BitmapFactory.decodeResource(getResources(), R.drawable.rss);
		changingY = 0;
		font = Typeface.createFromAsset(context.getAssets(), "asman.TTF");
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawColor(Color.GRAY);
		
		Paint textPaint = new Paint();
		textPaint.setARGB(50, 254, 10, 50);
		textPaint.setTextAlign(Align.CENTER);
		textPaint.setTextSize(50);
		textPaint.setTypeface(font);
		
		canvas.drawText("o yeah !!!", 20, 200, textPaint);
		canvas.drawBitmap(gball, (canvas.getWidth())/2, changingY, null);
		if(changingY < canvas.getHeight())
			changingY +=10;
		else
			changingY =0;
		Rect middleRect = new Rect();
		middleRect.set(0,400,canvas.getWidth(),550);
		Paint ourBlue = new Paint();
		ourBlue.setColor(Color.BLUE);
		canvas.drawRect(middleRect, ourBlue);
		invalidate();
	}

}
