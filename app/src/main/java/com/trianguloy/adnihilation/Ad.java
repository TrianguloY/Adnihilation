package com.trianguloy.adnihilation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Manages the Ad view
 */
public class Ad extends View implements View.OnTouchListener {

    /**
     * Dragging distance to add 1 point
     */
    private static final int D = 500;

    /**
     * Size of ads
     */
    public int WIDTH = 320;
    public int HEIGHT = 50;

    /**
     * Paint utils
     */
    private Paint btm_paint = new Paint();
    private Paint scratch_paint = new Paint();
    {
        btm_paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        scratch_paint.setColor(Color.WHITE);
        scratch_paint.setAlpha(128);
        scratch_paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        scratch_paint.setStrokeWidth(5);
    }

    /**
     * Random utils
     */
    private Random random = new Random();

    /**
     * Random number in range [f,t]
     * @param f minimum value, included
     * @param t maximum value, included
     * @return random int
     */
    private int r(int f, int t) {
        return random.nextInt(t - f + 1) + f;
    }

    /**
     * Canvas utils
     */
    private Bitmap btm;
    private final Canvas c;

    /**
     * Classes
     */
    private Points points;
    private AdsList adsList;

    // ------------------- Constructor -------------------

    public Ad(Context context, AttributeSet attrs) {
        super(context, attrs);

        // update WIDTH and HEIGHT values with device resolution
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

        // initialize transparent bitmap
        btm = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);
        btm.eraseColor(Color.TRANSPARENT);
        c = new Canvas(btm);

        // load ads
        adsList = new AdsList();
        loadAd(true);

        // prepare view
        setOnTouchListener(this);
    }

    /**
     * Loads a new ad (using the nextAd as index).
     * Prepares nextAd for a different random one
     */
    public void loadAd(boolean test) {
        // load
        Bitmap bm = BitmapFactory.decodeResource(getResources(), test ? adsList.getTestAd() : adsList.getRandomAd());
        c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);
        c.drawBitmap(Bitmap.createScaledBitmap(bm, WIDTH, HEIGHT, false), 0, 0, null);
        invalidate();
    }

    /**
     * Loads the points
     * @param p points
     */
    public void setPoints(Points p) {
        points = p;
    }

    // ------------------- Drawing -------------------

    /**
     * Draws the local ad in the canvas
     */
    @Override
    public void onDraw(Canvas c) {
        c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);
        c.drawBitmap(btm, 0, 0, null);
    }

    /**
     * last touch position and total distance
     */
    float lastx;
    float lasty;
    float dist = 0;

    /**
     * When the user touches the ad
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                // start of touch
                lastx = x;
                lasty = y;
                dist = 0;
                break;
            case MotionEvent.ACTION_UP:
                // end of touch
                if (lastx == x && lasty == y) {
                    // a press (not a drag)
                    drawHit(x, y);
                    points.addPoints(10);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // a drag
                if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
                    // inside the ad

                    // check distance
                    float dx = lastx - x;
                    float dy = lasty - y;
                    dist += Math.sqrt(dx * dx + dy * dy);
                    while (dist > D) {
                        // enough distance, add points
                        dist -= D;
                        points.addPoints(1);
                    }
                    // draw random ray and scratch
                    if (r(0, 100) < 20) drawRay(x, y, r(5, 25));
                    drawScratch(lastx, lasty, x, y);
                }
                // update position
                lastx = x;
                lasty = y;
                break;
            default:
                // unkown event
                return false;
        }
        return true;
    }

    /**
     * Draws a scratch (line) between two points
     * @param x_1 starting x position
     * @param y_1 starting y position
     * @param x_2 end x position
     * @param y_2 end y position
     */
    private void drawScratch(float x_1, float y_1, float x_2, float y_2) {
        c.drawLine(x_1, y_1, x_2, y_2, scratch_paint);
        invalidate();
    }

    /**
     * Draws a hit (crash) in a point
     * @param x center x position
     * @param y center y position
     */
    private void drawHit(float x, float y) {
        for (int i = 0; i < 10; ++i) drawRay(x, y, r(40, 60));
        for (int i = 0; i < 20; ++i) drawRay(x, y, r(10, 30));
        for (int i = 0; i < 25; ++i) drawTangent(x, y, 5 + i * 2, r(40, 50));
        invalidate();
    }

    /**
     * Draws a ray (line) in a random direction
     * @param x starting x position
     * @param y starting y position
     * @param dist length of ray
     */
    private void drawRay(float x, float y, float dist) {
        double angle = random.nextFloat() * Math.PI * 2;
        c.drawLine(x, y, x + (float) Math.cos(angle) * dist, y + (float) Math.sin(angle) * dist, scratch_paint);
    }

    /**
     * Draws a ray (line) tangent to a center point
     * @param x center x position
     * @param y center y position
     * @param dist radius distance
     * @param lon length of tangent
     */
    private void drawTangent(float x, float y, float dist, float lon) {
        float angle = random.nextFloat() * 360;
        c.drawArc(new RectF(x - dist, y - dist, x + dist, y + dist), angle, lon, false, scratch_paint);
    }

}
