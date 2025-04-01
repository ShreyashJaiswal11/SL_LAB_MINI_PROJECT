package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WorkoutTracker.db";
    private static final int DATABASE_VERSION = 2; // Incremented version

    // User Table
    private static final String TABLE_USER = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    // Workout Table (Newly Added)
    private static final String TABLE_WORKOUT = "workout";
    private static final String COLUMN_WORKOUT_ID = "workout_id";
    private static final String COLUMN_WORKOUT_NAME = "workout_name";

    // Workout Progress Table
    private static final String TABLE_PROGRESS = "progress";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_WORKOUT = "workout";
    private static final String COLUMN_REPS = "reps";
    private static final String COLUMN_SETS = "sets";

    // Notifications Table
    private static final String TABLE_NOTIFICATIONS = "notifications";
    private static final String COLUMN_TIME = "time";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating User Table
        String createUserTable = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createUserTable);

        // Creating Workout Table
        String createWorkoutTable = "CREATE TABLE " + TABLE_WORKOUT + " (" +
                COLUMN_WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_WORKOUT_NAME + " TEXT)";
        db.execSQL(createWorkoutTable);

        // Creating Progress Table
        String createProgressTable = "CREATE TABLE " + TABLE_PROGRESS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_WORKOUT + " TEXT, " +
                COLUMN_REPS + " INTEGER, " +
                COLUMN_SETS + " INTEGER)";
        db.execSQL(createProgressTable);

        // Creating Notifications Table
        String createNotificationsTable = "CREATE TABLE " + TABLE_NOTIFICATIONS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TIME + " TEXT)";
        db.execSQL(createNotificationsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Dropping existing tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRESS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);

        // Recreating tables
        onCreate(db);
    }

    // Get User Data
    public Cursor getUserData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USER + " LIMIT 1", null);
    }

    // Get Workout Count
    public int getWorkoutCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_PROGRESS, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    // User Registration
    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USER, null, values);
        return result != -1;
    }

    // User Login
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Save Workout Progress
    public boolean saveWorkoutProgress(String date, String workout, int reps, int sets) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_WORKOUT, workout);
        values.put(COLUMN_REPS, reps);
        values.put(COLUMN_SETS, sets);
        long result = db.insert(TABLE_PROGRESS, null, values);
        return result != -1;
    }

    // Retrieve Workout Progress
    public Cursor getWorkoutProgress() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PROGRESS, null);
    }

    // Insert Workout Name
    public boolean insertWorkout(String workoutName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WORKOUT_NAME, workoutName);
        long result = db.insert(TABLE_WORKOUT, null, values);
        return result != -1;
    }

    // Get All Workouts
    public Cursor getAllWorkouts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_WORKOUT, null);
    }

    // Set Notification Time
    public boolean setNotificationTime(String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME, time);
        long result = db.insert(TABLE_NOTIFICATIONS, null, values);
        return result != -1;
    }

    // Get Notification Time
    public Cursor getNotificationTime() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NOTIFICATIONS, null);
    }
}

