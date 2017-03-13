package com.appstructural.jelovnik15.a30_parcijalni.db.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;



/**
 * Created by Mika on 3/13/2017.
 */
@DatabaseTable(tableName = Actor.TABELE_NAME_USERS)
public class Actor {


    public static final String TABELE_NAME_USERS = "actor";
    public static final String FIELD_NAME_ID = "id";
    public static final String TABELE_NAME_NAME= "name";
    public static final String TABELE_NAME_BIOGRAPHY= "biography";
    public static final String TABELE_NAME_SCORE= "score";
    public static final String TABELE_NAME_BIRTH = "birth";
    public static final String TABELE_NAME_MOVIES= "movies";


    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    int mId;

    @DatabaseField(columnName =TABELE_NAME_NAME )
    String mName;


    @DatabaseField(columnName =TABELE_NAME_BIOGRAPHY )
    String mBiography;

    @DatabaseField(columnName =TABELE_NAME_SCORE )
    float mScore;

    @DatabaseField(columnName =TABELE_NAME_BIRTH )
    String mBirth;

    @ForeignCollectionField(columnName = Actor.TABELE_NAME_MOVIES, eager = true)
    private ForeignCollection<Movies>movies;


    public Actor (){

    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmBiography() {
        return mBiography;
    }

    public void setmBiography(String mBiography) {
        this.mBiography = mBiography;
    }

    public float getmScore() {
        return mScore;
    }

    public void setmScore(float mScore) {
        this.mScore = mScore;
    }

    public String getmBirth() {
        return mBirth;
    }

    public void setmBirth(String mBirth) {
        this.mBirth = mBirth;
    }

    public ForeignCollection<Movies> getMovies() {
        return movies;
    }

    public void setMovies(ForeignCollection<Movies> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return mName;
    }
}

