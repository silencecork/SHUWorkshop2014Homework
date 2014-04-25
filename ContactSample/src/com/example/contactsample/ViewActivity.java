package com.example.contactsample;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ViewActivity extends Activity {

    private SQLiteDatabase mDatabase;
    private Cursor mCursor;
    private int mQueryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);
        
        Intent intent = getIntent();
        mQueryId = intent.getIntExtra("_id", -1);
        
        initDatabase();
        
        query(mQueryId);
        
        if (mCursor.moveToFirst()) { 
            TextView textName = (TextView) findViewById(R.id.text_name);
            textName.setText(mCursor.getString(mCursor.getColumnIndex("NAME")));
            
            TextView textAddr = (TextView) findViewById(R.id.text_addr);
            textAddr.setText(mCursor.getString(mCursor.getColumnIndex("ADDR")));
            
            TextView textTel = (TextView) findViewById(R.id.text_tel);
            textTel.setText(mCursor.getString(mCursor.getColumnIndex("TEL")));
        }
        
        Button deleteButton = (Button) findViewById(R.id.btn_delete);
        deleteButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                delete(mQueryId);
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    
    private void initDatabase() {
        mDatabase = openOrCreateDatabase("contact.db", MODE_PRIVATE, null);
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS CONTACT(_id INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,ADDR TEXT,TEL INTEGER)");
    }
    
    private void query(int id) {
        String sql = "SELECT * FROM CONTACT WHERE _id='" + id + "'";
        mCursor = mDatabase.rawQuery(sql, null);
        startManagingCursor(mCursor);
    }

    private void delete(int id) {
        String sql = "DELETE FROM CONTACT WHERE _id='" + id + "'";
        mDatabase.execSQL(sql);
    }
}
