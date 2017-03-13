package com.appstructural.jelovnik15.a30_parcijalni.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import static com.appstructural.jelovnik15.a30_parcijalni.db.model.Movies.FIELD_NAME_USER;

/**
 * Created by Mika on 3/13/2017.
 */
@DatabaseTable(tableName =Movies.TABELE_NAME_USERS )
public class Movies {


    public static final String TABELE_NAME_USERS = "movies";
    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_NAME= "name";
    public static final String FIELD_NAME_GENRE= "genre";
    public static final String FIELD_NAME_YEAR= "year";
    public static final String FIELD_NAME_USER= "user";


    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = FIELD_NAME_NAME)
    private String name;

    @DatabaseField(columnName = FIELD_NAME_GENRE)
    private String genre;

    @DatabaseField(columnName = FIELD_NAME_YEAR)
    private String year;

    @DatabaseField (columnName =FIELD_NAME_USER,foreign = true, foreignAutoRefresh = true)
    private Actor user;

    public Movies(){

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Actor getUser() {
        return user;
    }

    public void setUser(Actor user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return name;
    }

}
