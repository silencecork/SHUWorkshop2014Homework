package com.example.contactsample;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends Activity implements OnClickListener {

    private SQLiteDatabase mDatabase;
    
    
    // This is app start~~
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        
        initDatabase();
        
        Button saveButton = (Button) findViewById(R.id.btn_save);
        saveButton.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    
    @Override
    public void onClick(View v) {
        EditText inputName = (EditText) findViewById(R.id.input_name);
        String name = inputName.getText().toString();
        
        EditText inputAddr = (EditText) findViewById(R.id.input_addr);
        String addr = inputAddr.getText().toString();
        
        EditText inputTel = (EditText) findViewById(R.id.input_tel);
        String tel = inputTel.getText().toString();
        
        insert(name, addr, Integer.valueOf(tel));
        
        setResult(RESULT_OK);
        
        finish();
    }
    
    private void initDatabase() {
        mDatabase = openOrCreateDatabase("contact.db", MODE_PRIVATE, null);
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS CONTACT(_id INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,ADDR TEXT,TEL INTEGER)");
    }
    
    private void insert(String name, String addr, int tel) {
        String sql = "INSERT INTO CONTACT(NAME,ADDR,TEL) VALUES('" +name +"','" + addr + "','" + tel + "')";
        mDatabase.execSQL(sql);
    }

    

}
