
package com.example.contactsample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
    
    private SQLiteDatabase mDatabase;
    
    private Cursor mCursor;
    
    private ProgressDialog mProgressDialog;
    
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mListView = (ListView) findViewById(R.id.list);
        
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Wait...");
        mProgressDialog.setCancelable(false);
        
        initDatabase();
        
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
        	
        	@Override
			protected void onPreExecute() {
        		mProgressDialog.show();
			}

			@Override
			protected Void doInBackground(Void... arg0) {
				try {
					Thread.sleep(5000);
				} catch (Exception e) {
					
				}
				query();
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.contact_item, mCursor, new String[] {"NAME"}, new int[] {R.id.text});
				mListView.setAdapter(adapter);
				mListView.setOnItemClickListener(new OnItemClickListener() {

		            @Override
		            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		                mCursor.moveToPosition(position);
		                int recordId = mCursor.getInt(mCursor.getColumnIndex("_id"));
		                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
		                intent.putExtra("_id", recordId);
		                startActivityForResult(intent, 200);
		            }
		        });
				mProgressDialog.dismiss();
			}

        	
        };
        task.execute();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.alert_dialog_about_title)
            .setMessage(R.string.alert_dialog_about_message)
            .setPositiveButton(R.string.btn_ok, null);
            
            builder.show();
            
            return true;
        } else if (id == R.id.action_add) {
            Intent intent = new Intent(this, AddActivity.class);
            startActivityForResult(intent, 100);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDatabase != null) {
            mDatabase.close();
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Toast.makeText(this, R.string.toast_add_success, Toast.LENGTH_LONG).show();
        } else if (requestCode == 200 &&resultCode == RESULT_OK) {
            Toast.makeText(this, R.string.toast_delete_success, Toast.LENGTH_LONG).show();
        }
    }

    private void initDatabase() {
        mDatabase = openOrCreateDatabase("contact.db", MODE_PRIVATE, null);
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS CONTACT(_id INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,ADDR TEXT,TEL INTEGER)");
    }
    
    private void createFakeData() {
        mDatabase.execSQL("INSERT INTO CONTACT(NAME,ADDR,TEL) VALUES('John','台北市文山區木柵路一段17巷1號','0222368225')");
    }
    
    private void query() {
        mCursor = mDatabase.rawQuery("SELECT * FROM CONTACT", null);
        startManagingCursor(mCursor);
    }

}
