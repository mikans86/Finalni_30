package com.appstructural.jelovnik15.a30_parcijalni.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Movie;

import com.appstructural.jelovnik15.a30_parcijalni.db.model.Actor;
import com.appstructural.jelovnik15.a30_parcijalni.db.model.Movies;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Mika on 3/13/2017.
 */

public class DataBaseHelper extends OrmLiteSqliteOpenHelper{


    private static final String DATA_BASE_NAME= "zavrsni.db";
    private static final  int DATA_BASE_VERSION = 1;

    private Dao<Actor,Integer>mActorDao= null;
    private Dao<Movies,Integer>mMovieDao = null;

    public DataBaseHelper (Context context){

        super(context,DATA_BASE_NAME,null,DATA_BASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, Actor.class);
            TableUtils.createTable(connectionSource, Movies.class);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            TableUtils.dropTable(connectionSource,Actor.class,true);
            TableUtils.dropTable(connectionSource,Movies.class,true);

            onCreate(database,connectionSource);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public Dao<Actor,Integer>getActorDao() throws SQLException{
        if(mActorDao == null){
            mActorDao = getDao(Actor.class);
        }return mActorDao;
    }


    public Dao<Movies,Integer> getMoviesDao()throws SQLException {
        if (mMovieDao == null) {
            mMovieDao = getDao(Movies.class);
        }
        return mMovieDao;

    }

    //obavezno prilikom zatvarnaj rada sa bazom osloboditi resurse
    @Override
    public void close() {
        mMovieDao = null;
        mActorDao = null;

        super.close();
    }

}
