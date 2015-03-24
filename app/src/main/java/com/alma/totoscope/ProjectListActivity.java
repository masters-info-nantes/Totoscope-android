package com.alma.totoscope;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ArrayAdapter;


public class ProjectListActivity extends Activity {

    final Context context = this;
    ListView listView;
    MenuItem newProject;
    //AlertDialog.Builder sourcesDialog;
    //ImageButton gallery;
    //ImageButton camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        listView = (ListView)findViewById(R.id.list);
        newProject = (MenuItem) findViewById(R.id.action_newProject);


        /*newProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Bouton", "yes");
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Bouton", "no");
                    }
                });


                //AlertDialog.Builder sourcesDialog = new AlertDialog.Builder(context);
                //sourcesDialog.setTitle("Choix de la source");
                /*sourcesDialog.setPositiveButton("Camera"/*R.id.camera, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent takeVid = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        startActivityForResult(takeVid, 0);
                    }
                });*/
                /*sourcesDialog.setNegativeButton("Gallery"/*R.id.gallery, new DialogInterface.OnClickListener() {
                    /*@Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent takeVid = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        startActivityForResult(takeVid, 0);
                    }
                });*/
                //sourcesDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
               //sourcesDialog.setContentView(getLayoutInflater().inflate(R.layout.sources_layout,null));
               //sourcesDialog.show();
        //    }
        //});

        /*camera = (ImageButton)findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeVid = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(takeVid, 0);
            }
        });

        gallery = (ImageButton)findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto,1);
            }
        });
        */
        String[] values = new String[]{"coucou","lol","swag","poney"};


        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,values);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Intent intent = new Intent(ProjectListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_newProject) {
            newProjectDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void newProjectDialog() {
        Log.i("call", "newProjectDialog");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Click", "Open Gallery");
                        Intent pickVid = new Intent(Intent.ACTION_GET_CONTENT);
                        pickVid.addCategory(Intent.CATEGORY_OPENABLE);
                        pickVid.setType("video/*");
                        startActivityForResult(pickVid, 1);
                    }
                })
                .setNegativeButton("Camera",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Click", "Open Camera");
                        Intent takeVid = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        startActivityForResult(takeVid, 0);
                    }
                });

        AlertDialog sourcesDialog = builder.create();
        sourcesDialog.show();

        Button gallery = sourcesDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        gallery.setCompoundDrawablesWithIntrinsicBounds(this.getResources().getDrawable(R.drawable.collection), null, null, null);
        gallery.setText("");

        Button camera = sourcesDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        camera.setCompoundDrawablesWithIntrinsicBounds(this.getResources().getDrawable(R.drawable.camera), null, null, null);
        camera.setText("");
    }
}
