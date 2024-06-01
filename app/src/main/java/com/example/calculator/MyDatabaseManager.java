package com.example.calculator;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MyDatabaseManager {
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public MyDatabaseManager(Context context) {
        dbHelper = new MyDatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Phương thức để chèn dữ liệu
    public void insertCalculation(String expression, double result) {
        ContentValues values = new ContentValues();
        values.put("expression", expression);
        values.put("result", result);
        database.insert("calculations", null, values);
    }

    // Phương thức để lấy dữ liệu
    public Cursor fetchAllCalculations() {
        return database.query("calculations", null, null, null, null, null, null);
    }

    // Các phương thức khác nếu cần (update, delete...)
}
