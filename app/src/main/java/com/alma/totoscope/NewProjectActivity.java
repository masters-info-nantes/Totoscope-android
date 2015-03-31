package com.alma.totoscope;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class NewProjectActivity extends Activity {

    public static final int GALLERY_VIDEO_REQUEST = 1;
    TextView vidPath;
    Button modifier;
    EditText editProject;
    Spinner frameRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);
        vidPath = (TextView)findViewById(R.id.vidpath);
        modifier = (Button)findViewById(R.id.vidmodifier);
        editProject = (EditText)findViewById(R.id.editProjectName);
        frameRate = (Spinner)findViewById(R.id.frameRate);

        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click", "Open Gallery");
                Intent pickVid = new Intent(Intent.ACTION_GET_CONTENT);
                pickVid.addCategory(Intent.CATEGORY_OPENABLE);
                pickVid.setType("video/*");
                startActivityForResult(pickVid, GALLERY_VIDEO_REQUEST);
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.frame_array , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frameRate.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_BACK:
                //back
                return true;
            case R.id.action_OK:
                Intent intent = new Intent(NewProjectActivity.this, MainActivity.class);
                //intent.putExtra();
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == GALLERY_VIDEO_REQUEST) {
                //back to new project
                //name vid updated
            }
        }
    }
}
