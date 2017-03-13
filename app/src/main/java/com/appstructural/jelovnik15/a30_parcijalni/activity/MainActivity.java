package com.appstructural.jelovnik15.a30_parcijalni.activity;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.appstructural.jelovnik15.a30_parcijalni.R;
import com.appstructural.jelovnik15.a30_parcijalni.db.DataBaseHelper;
import com.appstructural.jelovnik15.a30_parcijalni.db.model.Actor;
import com.appstructural.jelovnik15.a30_parcijalni.dialog.AboutDialog;
import com.appstructural.jelovnik15.a30_parcijalni.preferenc.Preferences;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public DataBaseHelper dataBaseHelper;
    private SharedPreferences prefers;
    public static String ACTOR_KEY = "ACTOR_KEY";
    public static String NOTIF_TOAST = "notif_toast";
    public static String NOTIF_STATUS = "notif_statis";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //instanciranje toolbara
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }

        prefers = PreferenceManager.getDefaultSharedPreferences(this);

        final ListView listView = (ListView) findViewById(R.id.lista_glumaca);

        try {
            List<Actor> list = getDataBaseHelper().getActorDao().queryForAll();

            ListAdapter adapter = new ArrayAdapter<>(MainActivity.this, R.layout.list_item, list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Actor p = (Actor) listView.getItemAtPosition(position);

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra(ACTOR_KEY, p.getmId());
                    startActivity(intent);
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }





    }

    private void refresh() {
        ListView listview = (ListView) findViewById(R.id.lista_glumaca);

        if (listview != null){
            ArrayAdapter<Actor> adapter = (ArrayAdapter<Actor>) listview.getAdapter();

            if(adapter!= null)
            {
                try {
                    adapter.clear();
                    List<Actor> list = getDataBaseHelper().getActorDao().queryForAll();

                    adapter.addAll(list);

                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void showStatusMessage (String message){

        NotificationManager mNotificationMenager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_action_notification);
        mBuilder.setContentTitle("Zadnji test");
        mBuilder.setContentText(message);

        Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.ic_action_not2);

        mBuilder.setLargeIcon(bm);

        mNotificationMenager.notify(1,mBuilder.build());



    }

    @Override
    protected void onResume() {
        super.onResume();

        refresh();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.add_new_acctor:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.add_actor_layout);

                Button add = (Button)dialog.findViewById(R.id.add_actor);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText name = (EditText) dialog.findViewById(R.id.actor_name);
                        EditText bio = (EditText) dialog.findViewById(R.id.actor_biography);
                        RatingBar rating = (RatingBar) dialog.findViewById(R.id.acrtor_rating);
                        EditText birth = (EditText) dialog.findViewById(R.id.actor_birth);

                        Actor a = new Actor();

                        a.setmName(name.getText().toString());
                        a.setmBiography(bio.getText().toString());
                        a.setmBirth(birth.getText().toString());
                        a.setmScore(rating.getRating());

                     try {
                         getDataBaseHelper().getActorDao().create(a);

                        boolean toast = prefers.getBoolean(NOTIF_TOAST,false);
                         boolean status = prefers.getBoolean(NOTIF_STATUS,false);

                        if(toast){
                           Toast.makeText(MainActivity.this,"Added a new Actor",Toast.LENGTH_SHORT).show();
                         }
                        if(status) {
                            showStatusMessage("Added a new actor234");
                        }
                        refresh();



                     } catch (SQLException e) {
                       e.printStackTrace();}
                       dialog.dismiss();
                    }
                });
                dialog.show();

                break;
            case R.id.about:

                AlertDialog alertDialog = new AboutDialog(this).prepareDialog();
                alertDialog.show();
                break;
            case R.id.preferenc:
                startActivity(new Intent(MainActivity.this, Preferences.class));
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.first_menu, menu);


        return super.onCreateOptionsMenu(menu);
    }

    //Metoda koja komunicira sa bazom podataka
    public DataBaseHelper getDataBaseHelper(){

        if(dataBaseHelper == null){
            dataBaseHelper= OpenHelperManager.getHelper(this, DataBaseHelper.class);
        }
        return dataBaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // nakon rada sa bazo podataka potrebno je obavezno i sloboditi resurse
        if (dataBaseHelper!=null){
            OpenHelperManager.releaseHelper();
            dataBaseHelper=null;
        }
    }
}
