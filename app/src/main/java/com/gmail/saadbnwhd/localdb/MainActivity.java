package com.gmail.saadbnwhd.localdb;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this, "On Create", Toast.LENGTH_LONG);
        PostDatabase DB = new PostDatabase(MainActivity.this);
        SQLiteDatabase sqlDB = DB.getWritableDatabase();

// Execute queries...
        ContentValues values = new ContentValues();
        values.put(PostDatabase.COL_TITLE, "Test Title");
        values.put(PostDatabase.COL_CONTENT, "Test Content");

        sqlDB.insert(PostDatabase.TABLE_POSTS, null, values);


        // Raw Query
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM posts WHERE id = ?", new String[]{ "1" });
        cursor.moveToFirst();
        if (cursor != null && cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndex(PostDatabase.COL_TITLE));
            String content = cursor.getString(cursor.getColumnIndex(PostDatabase.COL_CONTENT));

            // Dumps "Title: Test Title Content: Test Content"
            Toast.makeText(getApplicationContext(),title + " , " + content,Toast.LENGTH_LONG);

            cursor.close();
        }
        Toast.makeText(getApplicationContext(),"Hey!",Toast.LENGTH_LONG);

        cursor = sqlDB.rawQuery("SELECT * FROM posts WHERE id = ?", new String[]{ "1" });
        if (cursor != null) {
            int idIndex = cursor.getColumnIndex(PostDatabase.ID);
            int titleIndex = cursor.getColumnIndex(PostDatabase.COL_TITLE);
            int contentIndex = cursor.getColumnIndex(PostDatabase.COL_CONTENT);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(idIndex);
                String title = cursor.getString(titleIndex);
                String content = cursor.getString(contentIndex);

                // Dumps (2 rows):
                // ID: 1 Title: Test Title Content: Test Content
                // ID: 2 Title: Second Title Content: Second Content

                Toast.makeText(getApplicationContext(),title + " , " + content,Toast.LENGTH_LONG);
                cursor.moveToNext();
            }
        }


        sqlDB.close();
    }

}
