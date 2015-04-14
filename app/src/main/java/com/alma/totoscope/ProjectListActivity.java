package com.alma.totoscope;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.List;

/* Fenêtre de démarrage contenant la liste des projets*/

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

        /* Création de la liste */
        final List<String> values = new ArrayList<String>();
            values.add("MonPoney");
            values.add("Project1");

        /* Mise en forme et ajout d'un listener*/
        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,values);
        listView.setAdapter(adapter);
        // clic court -> import vers la fenêtre principale
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Intent intent = new Intent(ProjectListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        // clic long -> ouverture du menu contextuel
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> arg0, View view, int pos, final long id) {

                CharSequence options[] = new CharSequence[] {"Supprimer", "Renommer", "Afficher les détails"};

                AlertDialog.Builder builder = new AlertDialog.Builder(ProjectListActivity.this);
                builder.setTitle("Options");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0 : //Supprimer
                                    Log.i("Options", "Supprimer");
                                    AlertDialog.Builder removeBuilder = new AlertDialog.Builder(ProjectListActivity.this);
                                    removeBuilder.setTitle("Supprimer")
                                            .setMessage("Confirmer la suppression?")
                                            .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Log.d("Suppression", "OK");
                                                    values.remove((int) id);
                                                    adapter.notifyDataSetChanged();
                                                }
                                            });
                                    AlertDialog removeDialog = removeBuilder.create();
                                    removeDialog.show();
                                    break;
                            case 1 : //Renommer
                                    Log.i("Options","Renommer");
                                    AlertDialog.Builder renameBuilder = new AlertDialog.Builder(ProjectListActivity.this);
                                    final EditText name = new EditText(ProjectListActivity.this);
                                    renameBuilder.setTitle("Renommer")
                                         .setView(name)
                                         .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                             @Override
                                             public void onClick(DialogInterface dialog, int which) {
                                                 Log.d("Input", name.getText().toString());
                                                 values.set((int) id, name.getText().toString());
                                                 adapter.notifyDataSetChanged();
                                             }
                                         });

                                    AlertDialog renameDialog = renameBuilder.create();
                                    renameDialog.show();
                                    break;
                            case 2 : //Afficher les détails
                                    Log.i("Options","Détails");
                                    AlertDialog.Builder infoBuilder = new AlertDialog.Builder(ProjectListActivity.this);
                                    infoBuilder.setTitle("Informations")
                                            .setMessage(TextUtils.concat("Nom du projet : ",values.get((int)id)));

                                    AlertDialog infoDialog = infoBuilder.create();
                                    infoDialog.show();
                                    break;
                        }
                    }
                });
                builder.show();
                return true;
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
        // Création d'un noveau projet
        if (id == R.id.action_newProject) {
            newProjectDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Fenêtre de création d'un nouveau projet
    public void newProjectDialog() {
        Log.i("call", "newProjectDialog");

        /* Choix de la source*/
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

        //Affiche une image sur les boutons
        Button gallery = sourcesDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        gallery.setCompoundDrawablesWithIntrinsicBounds(this.getResources().getDrawable(R.drawable.collection), null, null, null);
        gallery.setText("Gallerie");

        Button camera = sourcesDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        camera.setCompoundDrawablesWithIntrinsicBounds(this.getResources().getDrawable(R.drawable.camera), null, null, null);
        camera.setText("Camera");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /* Ouverture de l'activité selon le choix de la source*/
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
