package com.alma.totoscope;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

    ImageButton pen;
    ImageButton eraser;
    ImageButton color;
    ImageButton undo;

    RelativeLayout myLayout;
    RelativeLayout lateralButton;
    RelativeLayout penSlideLayout;
    RelativeLayout eraserSlideLayout;

    DrawingZone myDrawZone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDrawZone = (DrawingZone)findViewById(R.id.DrawZone);

        setContentView(R.layout.activity_main);
        lateralButton = (RelativeLayout)findViewById(R.id.lateralButton);

        penSlideLayout = (RelativeLayout)findViewById(R.id.penSlide);
        penSlideLayout.setVisibility(View.INVISIBLE);

        eraserSlideLayout = (RelativeLayout)findViewById(R.id.eraserSlide);
        eraserSlideLayout.setVisibility(View.INVISIBLE);

        myLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        myLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                View drawView = View.inflate(MainActivity.this, R.layout.draw_options_layout, null);
                AlertDialog.Builder drawBuilder = new AlertDialog.Builder(MainActivity.this);
                drawBuilder.setView(drawView);

                AlertDialog drawDialog = drawBuilder.create();
                drawDialog.show();
                return false;
            }
        });

        pen = (ImageButton)findViewById(R.id.pen);
        eraser = (ImageButton)findViewById(R.id.eraser);
        color = (ImageButton)findViewById(R.id.color);
        undo = (ImageButton)findViewById(R.id.undo);

        pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click","pen");
                myDrawZone.setPen();
            }
        });
        pen.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //lateralButton.setVisibility(View.INVISIBLE);
                //penSlideLayout.setVisibility(View.VISIBLE);
                //v.invalidate();
                return false;
            }
        });

        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click","eraser");
                myDrawZone.erase();
            }
        });
        eraser.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //lateralButton.setVisibility(View.INVISIBLE);
                //eraserSlideLayout.setVisibility(View.VISIBLE);
                //v.invalidate();
                return false;
            }
        });

        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click","color");
                colorPickerCall();
            }
        });

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click","undo");


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.save :
                return true;
            case R.id.saveas :
                return true;
            case R.id.export :
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void colorPickerCall() {

    }

}
