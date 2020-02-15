package com.trianguloy.adnihilation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;


public class Ad extends View implements View.OnTouchListener {

    private int[] adsIds = {R.drawable.test_ad, R.drawable.app};

    private static final int D = 500;

    public int WIDTH = 320;
    public int HEIGHT = 50;

    private Paint btm_paint = new Paint();
    private Paint scratch_paint = new Paint();
    private Random random = new Random();

    private float r(int f, int t) {
        return random.nextInt(t - f + 1) + f;
    }

    {
        btm_paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        scratch_paint.setColor(Color.WHITE);
        scratch_paint.setAlpha(128);
        scratch_paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        scratch_paint.setStrokeWidth(5);
    }

    private Bitmap btm;
    private final Canvas c;

    private Points points;

    public Ad(Context context, AttributeSet attrs) {
        super(context, attrs);


        WIDTH = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                WIDTH,
                context.getResources().getDisplayMetrics()
        ));
        HEIGHT = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                HEIGHT,
                context.getResources().getDisplayMetrics()
        ));

        btm = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);
        btm.eraseColor(Color.TRANSPARENT);
        c = new Canvas(btm);
        loadAd();

        setOnTouchListener(this);
    }

    public void loadAd() {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), adsIds[random.nextInt(adsIds.length)]);
        c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);
        c.drawBitmap(Bitmap.createScaledBitmap(bm,WIDTH, HEIGHT, false), 0, 0, null);
        invalidate();
    }

    public void setPoints(Points p) {
        points = p;
    }

    @Override
    public void onDraw(Canvas c) {
        c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);
        c.drawBitmap(btm, 0, 0, null);
    }


    float lastx;
    float lasty;
    float dist = 0;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                lastx = x;
                lasty = y;
                dist = 0;
                break;
            case MotionEvent.ACTION_UP:
                points.addPoints(10);
                Paint paint = new Paint();
                paint.setColor(Color.RED);
                if (lastx == x && lasty == y) {
                    drawHit(x, y);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
                    float dx = lastx - x;
                    float dy = lasty - y;
                    dist += Math.sqrt(dx * dx + dy * dy);
                    while (dist > D) {
                        dist -= D;
                        points.addPoints(1);
                    }
                }
                if (r(0, 100) < 20) drawRay(x, y, r(5, 25));
                drawScratch(x, y);
                lastx = x;
                lasty = y;
                break;
            default:
                return false;
        }
        return true;
    }

    private void drawScratch(float x, float y) {
        c.drawLine(lastx, lasty, x, y, scratch_paint);
        invalidate();
    }

    private void drawHit(float x, float y) {
        for (int i = 0; i < 10; ++i) drawRay(x, y, r(40, 60));
        for (int i = 0; i < 20; ++i) drawRay(x, y, r(10, 30));
        for (int i = 0; i < 25; ++i) drawTangent(x, y, 5 + i * 2, r(40,50));
        invalidate();
    }

    private void drawRay(float x, float y, float dist) {
        double angle = random.nextFloat() * Math.PI * 2;
        c.drawLine(x, y, x + (float) Math.cos(angle) * dist, y + (float) Math.sin(angle) * dist, scratch_paint);
    }

    private void drawTangent(float x, float y, float dist, float lon) {
        float angle = (float) (random.nextFloat() * 360);
        c.drawArc(new RectF(x - dist, y - dist, x + dist, y + dist), angle, lon, false, scratch_paint);
    }

}
