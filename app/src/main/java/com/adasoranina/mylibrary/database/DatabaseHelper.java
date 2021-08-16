package com.adasoranina.mylibrary.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "student_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_STUDENTS = "students";
    private static final String KEY_ID = "id";
    private static final String KEY_FIRST_NAME = "name";

    private static final String LOG_TABLE = "table";
    private static final String LOG_DATA = "data";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d(LOG_TABLE, CREATE_TABLE_STUDENTS);
    }

    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE "
            + TABLE_STUDENTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_FIRST_NAME + " TEXT"
            + ");";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_STUDENTS + "'");
        onCreate(sqLiteDatabase);
    }

    public long addStudentDetail(String student) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, student);

        return db.insert(TABLE_STUDENTS, null, values);
    }

    public List<String> getAllStudents() {
        List<String> students = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_STUDENTS;
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                int columnIndex = c.getColumnIndex(KEY_FIRST_NAME);
                String name = c.getString(columnIndex);

                students.add(name);
            } while (c.moveToNext());
            Log.d(LOG_DATA, students.toString());
        }

        c.close();
        return students;
    }

}
