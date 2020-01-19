package com.appstone.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {


    /**
     * 1. Create a class for Inserting, Retrieving, Updating and Deleting from Database and Extend the class to SQLiteOpenHelper
     * 2. Implement the onCreate and onUpgrade method and the constructor matching the super.
     * 3. Override the created constructor by just getting one argument(Context) and hardcode the other values as required.
     * 4. Create a database name, table name, columns for the database.
     * 5. Create a Create Table query with proper spaces and data type names
     * 6. Create appropriate methods.
     */


    private static String DATABASE_NAME = "studentbook.db";
    private static String TABLE_NAME = "student";
    private static String COL_STUDENT_NAME = "student_name";
    private static String COL_STUDENT_ROLLNO = "roll_no";
    private static String COL_DEPARTMENT = "department";
    private static String COL_BOOK_BORROWED = "book_borrowed";

    //CREATE TABLE studentbook.db(roll_no INTEGER PRIMARY KEY,student_name TEXT,department TEXT,book_borrowed TEXT)

    private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COL_STUDENT_ROLLNO + " INTEGER PRIMARY KEY," +
            COL_STUDENT_NAME + " TEXT," + COL_DEPARTMENT + " TEXT," + COL_BOOK_BORROWED + " TEXT)";

//    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldversion, int newVersion) {

    }


    /**
     * 1. For inserting Data into database create a method and get the reference of database and the Object that needs to be inserted
     * 2. Create a object for ContentValues(ContentValues helps the data to be mapped to the correct column name)
     * 3. Assign the value to the designated column
     * 4. insert the value into the database.
     * 5. Note for inserting the value you need to get the reference of writeable database in the Activity.
     */

    public void insertDataToDatabase(SQLiteDatabase database, Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_STUDENT_ROLLNO, student.rollNo);
        contentValues.put(COL_STUDENT_NAME, student.studentName);
        contentValues.put(COL_DEPARTMENT, student.department);
        contentValues.put(COL_BOOK_BORROWED, student.bookBorrowed);

        database.insert(TABLE_NAME, null, contentValues);
    }


    /**
     * 1. For retrieving dataform database create a method with the return type of ArrayList with parameter of the database reference
     * 2. Create a object for the ArrayList and return it
     * 3. Using the database reference from the method create a cursor by using database.rawQuery
     * 3.a. the QUERY - SELECT * FROM TABLE_NAME selects all the data from the table
     * 3.b. Pass the selection argument as null
     * 4. Check if cursor.moveToFirst() - checking if the cursor is not empty and can move to first row
     * 5. Create a do while loop where the condition of while is cursor.moveToNext()
     * 6. Inside the do while loop get the assign the values by getting the datatype from the correct cursor column index
     * 7. Add the data to the object
     * 8. Add the object to the arraylist that will be returned.
     */


    public ArrayList<Student> getDataFromDatabase(SQLiteDatabase database) {
        ArrayList<Student> data = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                int rollNo = cursor.getInt(cursor.getColumnIndex(COL_STUDENT_ROLLNO));
                String studentName = cursor.getString(cursor.getColumnIndex(COL_STUDENT_NAME));
                String department = cursor.getString(cursor.getColumnIndex(COL_DEPARTMENT));
                String bookBorrowed = cursor.getString(cursor.getColumnIndex(COL_BOOK_BORROWED));

                Student singleData = new Student();
                singleData.rollNo = rollNo;
                singleData.studentName = studentName;
                singleData.department = department;
                singleData.bookBorrowed = bookBorrowed;

                data.add(singleData);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return data;
    }


    public void updateDataInDatabase(SQLiteDatabase sqLiteDatabase, Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_STUDENT_NAME, student.studentName);
        contentValues.put(COL_DEPARTMENT, student.department);
        contentValues.put(COL_BOOK_BORROWED, student.bookBorrowed);

        sqLiteDatabase.update(TABLE_NAME, contentValues,
                COL_STUDENT_ROLLNO + "=" + student.rollNo, null);
    }


    public void deleteDataFromDatabase(SQLiteDatabase database, Student student) {
        database.delete(TABLE_NAME, COL_STUDENT_ROLLNO + "=" + student.rollNo, null);
    }
}
