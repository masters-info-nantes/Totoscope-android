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

    public static final int CAMERA_VIDEO_REQUEST = 0;
    public static final int GALLERY_VIDEO_REQUEST = 1;

    ListView listView;
    MenuItem newProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        listView = (ListView)findViewById(R.id.list);
        newProject = (MenuItem) findViewById(R.id.action_newProject);

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
        int id = item.getItemId();
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
                        startActivityForResult(pickVid, GALLERY_VIDEO_REQUEST);
                    }
                })
                .setNegativeButton("Camera",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Click", "Open Camera");
                        Intent takeVid = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        startActivityForResult(takeVid, CAMERA_VIDEO_REQUEST);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == CAMERA_VIDEO_REQUEST){
                Intent intent = new Intent(ProjectListActivity.this, NewProjectActivity.class);
                intent.putExtra("VIDPATH", "uri");
                startActivity(intent);
                Log.i("MainActivity","CAMERA_VIDEO_REQUEST");
            }
            if(requestCode == GALLERY_VIDEO_REQUEST) {
                Intent intent = new Intent(ProjectListActivity.this, NewProjectActivity.class);
                intent.putExtra("VIDPATH", "uri");
                startActivity(intent);
                Log.i("MainActivity","GALLERY_VIDEO_REQUEST");
            }
        }
    }
}
