package com.alma.totoscope;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.SparseArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/* Classe de la zone de dessins*/

public class DrawingZone  extends View {

    private static final float TOUCH_TOLERANCE = 4;

    private Paint paint;
    private int color = Color.BLACK;
    private int penSize;
    private boolean eraser;
    private Paint imgPaint;
    private Bitmap bitmap;
    private Canvas canvas;
    private int width;
    private int height;

    private Path path;
    private Path circlePath;
    private float _x, _y;
    Context context;

    private SparseArray<PointF> pointers;


    public DrawingZone(Context c) {
        super(c);
        initialisation(c);
    }

    public DrawingZone(Context c, AttributeSet attrs) {
        super(c,attrs);
        initialisation(c);
    }

    public DrawingZone(Context c, AttributeSet attrs, int defStyle) {
        super(c,attrs,defStyle);
        initialisation(c);
    }

    private void initialisation(Context c) {
        // Initialisation
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(penSize = 5);

        eraser = false;
        context = c;
        path = new Path();
        circlePath = new Path();
        imgPaint = new Paint(Paint.DITHER_FLAG);

        pointers = new SparseArray<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, imgPaint);
        canvas.drawPath(path, paint);
    }

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);

        if (bitmap != null) {
            canvas = new Canvas(bitmap);
        }
    }

    @Override
    // Listener de la zone de dessin
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN :
                    startTouch(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE :
                    moveTouch(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP :
                    upTouch();
                    invalidate();
                    break;
            }
        return true;
    }

    private void startTouch(float x, float y) {
        path.reset();
        path.moveTo(x,y);
        _x = x;
        _y = y;
    }

    private void moveTouch(float x, float y) {
        float dx = Math.abs(x-_x);
        float dy = Math.abs(y-_y);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            path.quadTo(_x, _y, (x+_x)/2, (y+_y)/2);
            _x = x;
            _y = y;
        }
    }

    private void upTouch() {
        path.lineTo(_x, _y);
        circlePath.reset();
        canvas.drawPath(path,paint);
        path.reset();
    }

    // Réinitialisation
    public void reinit() {
        //canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        //this.invalidate();
    }

    // Activation du crayon
    public void setPen() {
        eraser = false;
        //paint.setXfermode(null);
    }

    // Change la couleur du crayon
    public void setColor(int color) {
        eraser = false;
        paint.setXfermode(null);
        paint.setColor(color);
    }

    // Change la taille du crayon
    public void setPenSize(int size){
        paint.setStrokeWidth(size);
        penSize = size;
    }

    // Activation de la gomme
    public void erase() {
        eraser = true;
        //paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    // Frame suivante
    public void nextFrame() {
        //TODO
        Toast toast = Toast.makeText(context,"Image Suivante",Toast.LENGTH_SHORT);
        toast.show();
    }

    // Frame précédente
    public void previousFrame() {
        //TODO
        Toast toast = Toast.makeText(context,"Image Précédente",Toast.LENGTH_SHORT);
        toast.show();
    }

    // Lecture des dessins
    public void play() {
        Toast toast = Toast.makeText(context,"Lecture des dessins",Toast.LENGTH_SHORT);
        toast.show();
    }

    // arrêt de la lecture
    public void stop() {
        Toast toast = Toast.makeText(context,"Arrêt lecture",Toast.LENGTH_SHORT);
        toast.show();
    }
    /*
    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Bitmap bitmap) {
        canvas = new Canvas(bitmap);
        invalidate();
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    */


}
