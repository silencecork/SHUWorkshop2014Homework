package com.example.shulbs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button button = (Button) findViewById(R.id.search);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText input = (EditText) findViewById(R.id.input);
				String text = input.getText().toString();
				if (text == null || text.equals("")) {
					return;
				}
				
				Intent intent = new Intent(MainActivity.this, ListActivity.class);
				intent.putExtra("keyword", text);
				startActivity(intent);
			}
		});
		
	}


}
