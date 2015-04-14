package com.alma.totoscope;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.*;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;


/* Activité principale contenant la zone de dessin, les boutons et le menu */
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
    AlertDialog drawDialog;

    ImageButton nextF;
    ImageButton previousF;
    ImageButton play;
    ImageButton stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDrawZone = (DrawingZone)findViewById(R.id.DrawZone);

        /* Initialisation de la barre latérale de boutons*/
        setContentView(R.layout.activity_main);
        lateralButton = (RelativeLayout)findViewById(R.id.lateralButton);

        /* Initialisation des sliders de taille */
        penSlideLayout = (RelativeLayout)findViewById(R.id.penSlide);
        penSlideLayout.setVisibility(View.INVISIBLE);

        eraserSlideLayout = (RelativeLayout)findViewById(R.id.eraserSlide);
        eraserSlideLayout.setVisibility(View.INVISIBLE);

        /* Init de la pop-up de navigation*/
        View drawView = View.inflate(MainActivity.this, R.layout.draw_options_layout, null);
        AlertDialog.Builder drawBuilder = new AlertDialog.Builder(MainActivity.this);
        drawBuilder.setView(drawView);
        drawDialog = drawBuilder.create();

        //Boutons de la pop-up
        nextF = (ImageButton)findViewById(R.id.next);
        previousF = (ImageButton)findViewById(R.id.previous);
        play = (ImageButton)findViewById(R.id.play);
        stop = (ImageButton)findViewById(R.id.stop);

        //Error!!
        /*nextF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Navigation","next");
                //myDrawZone.nextFrame();
            }
        });
        previousF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Navigation","previous");
                myDrawZone.previousFrame();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Navigation","play");
                myDrawZone.play();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Navigation","stop");
                myDrawZone.stop();
            }
        });
*/
        myLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        myLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                /*View drawView = View.inflate(MainActivity.this, R.layout.draw_options_layout, null);
                AlertDialog.Builder drawBuilder = new AlertDialog.Builder(MainActivity.this);
                drawBuilder.setView(drawView);
                AlertDialog drawDialog = drawBuilder.create();
                drawDialog.show();*/
                drawDialog.show();
                return false;
            }
        });

        /* Initialisation des boutons*/
        pen = (ImageButton)findViewById(R.id.pen);
        eraser = (ImageButton)findViewById(R.id.eraser);
        color = (ImageButton)findViewById(R.id.color);
        undo = (ImageButton)findViewById(R.id.undo);

        pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click","pen");
                //myDrawZone.setPen();
                Toast toast = Toast.makeText(getApplicationContext(),"Crayon",Toast.LENGTH_SHORT);
                toast.show();
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
                //myDrawZone.erase();
                Toast toast = Toast.makeText(getApplicationContext(),"Gomme",Toast.LENGTH_SHORT);
                toast.show();
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
                Toast toast = Toast.makeText(getApplicationContext(),"Annulé",Toast.LENGTH_SHORT);
                toast.show();
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
        /* Affectations des actions du menu*/
        switch (id) {
            case R.id.save : //Sauvegarder
                Toast toast = Toast.makeText(getApplicationContext(),"Projet Sauvegardé",Toast.LENGTH_SHORT);
                toast.show();
                Log.d("Toast","Save");
                return true;
            case R.id.export : //Exporter
                AlertDialog.Builder expBuilder = new AlertDialog.Builder(MainActivity.this);
                expBuilder.setTitle("Exportation terminée")
                        .setPositiveButton("Voir la vidéo", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("Retour", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog expDialog = expBuilder.create();
                expDialog.show();
                return true;
            case R.id.init : //Réinitialiser
                myDrawZone.reinit();
                myDrawZone.invalidate();
                return true;
            case R.id.about : //A Propos
                View aboutView = View.inflate(MainActivity.this, R.layout.about_layout, null);
                AlertDialog.Builder aboutBuilder = new AlertDialog.Builder(MainActivity.this);
                aboutBuilder.setTitle("A Propos")
                        .setView(aboutView)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                AlertDialog aboutDialog = aboutBuilder.create();
                aboutDialog.show();
                return true;
            case R.id.menu : //Retour au menu
                onBackPressed();
                return true;
            case R.id.quitter : //Quitter
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* Fonction d'appel de la palette de couleur*/
    public void colorPickerCall() {
        //color.setBackgroundColor(Color.BLUE);
    }
}
