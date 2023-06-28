package com.gyula.recipebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String dbName = "Receptkonyv";
    private static final int dbVersion = 1;
    private static final String dbTableName = "receptek";
    private static final String dbID = "id";
    private static final String dbCategories = "categories";
    private static final String dbSampleType = "sampleType";
    private static final String dbTitle = "title";
    private static final String dbShortStory = "shortStory";
    private static final String dbTime = "time";
    private static final String dbTimeType = "timeType";
    private static final String dbDifficulty = "difficulty";
    private static final String dbIngredients = "ingredients";
    private static final String dbInstructions = "instructions";


    public DatabaseHandler(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + dbTableName + " ("
                + dbID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + dbCategories + " TEXT,"
                + dbSampleType + " INTEGER,"
                + dbTitle + " TEXT,"
                + dbShortStory + " TEXT,"
                + dbTime + " INTEGER,"
                + dbTimeType + " TEXT,"
                + dbDifficulty + " TEXT,"
                + dbIngredients + " TEXT,"
                + dbInstructions + " TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + dbTableName);
        onCreate(sqLiteDatabase);
    }

    public void addRecepies(String categories, String sampleType, String title, String shortStory, int time, String timeType, String difficulty, String ingredients, String instructions) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(dbCategories, categories);
        contentValues.put(dbSampleType, sampleType);
        contentValues.put(dbTitle, title);
        contentValues.put(dbShortStory, shortStory);
        contentValues.put(dbTime, time);
        contentValues.put(dbTimeType, timeType);
        contentValues.put(dbDifficulty, difficulty);
        contentValues.put(dbIngredients, ingredients);
        contentValues.put(dbInstructions, instructions);

        db.insert(dbTableName, null, contentValues);
    }

    public void removeTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE " + dbTableName);
        onCreate(db);
    }

    public void updateRecipes(int id, String categories, String sampleType, String title, String shortStory, int time, String timeType, String difficulty, String ingredients, String instructions) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + dbTableName +
                " SET " + dbCategories + "=" + "'" + categories + "'" +", " +
                dbSampleType + "=" + "'" + sampleType  + "'" +", " +
                dbTitle + "=" + "'" + title  + "'" +", " +
                dbShortStory + "=" + "'" + shortStory  + "'" +
                dbTime + "=" + "'" + time  + "'" +", " +
                dbTimeType + "=" + "'" + timeType  + "'" +", " +
                dbDifficulty + "=" + "'" + difficulty  + "'" +
                dbIngredients + "=" + "'" + ingredients  + "'" +", " +
                dbInstructions + "=" + "'" + instructions  + "'" +
                " WHERE " + dbID + "=" + id);

    }

    public void removeRecipes(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + dbTableName + " WHERE "+ dbID + "=" + id);
    }

    public ArrayList<Recipe> readRecipes() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + dbTableName, null);
        ArrayList<Recipe> list = new ArrayList<>();

        if (cursorCourses.moveToFirst()) {
            do {
                list.add(new Recipe(cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3),
                        cursorCourses.getString(4),
                        cursorCourses.getInt(5),
                        cursorCourses.getString(6),
                        cursorCourses.getString(7),
                        cursorCourses.getString(8),
                        cursorCourses.getString(9)));
            } while (cursorCourses.moveToNext());
        }
        cursorCourses.close();
        return list;
    }

    public ArrayList<Recipe> readRec() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + dbTableName, null);
        ArrayList<Recipe> list = new ArrayList<>();

        if (cursorCourses.moveToFirst()) {
            do {
                list.add(new Recipe(cursorCourses.getString(1),
                        cursorCourses.getString(3),
                        cursorCourses.getInt(5),
                        cursorCourses.getString(6)));
            } while (cursorCourses.moveToNext());
        }
        cursorCourses.close();
        return list;
    }
    public boolean CheckIsDataAlreadyInDBorNot(String categories, String sampleType, String title, String shortStory, int time, String timeType, String difficulty, String ingredients, String instructions) {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "Select * from " + dbTableName +
                " where " + dbCategories + " = " + "'" + categories + "'" + " AND " +
                dbSampleType + " = " + "'" + sampleType + "'" + " AND " +
                dbTitle + " = " + "'" + title + "'" + " AND " +
                dbShortStory + " = " + "'" + shortStory + "'" + " AND " +
                dbTime + " = " + "'" + time + "'" + " AND " +
                dbTimeType + " = " + "'" + timeType + "'" + " AND " +
                dbDifficulty + " = " + "'" + difficulty + "'" + " AND " +
                dbIngredients + " = " + "'" + ingredients + "'" + " AND " +
                dbInstructions + " = " + "'" + instructions + "'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        } else{
            cursor.close();
            return true;
        }

    }
}
