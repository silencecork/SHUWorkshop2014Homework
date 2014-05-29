package com.example.shulbs;

import java.util.List;

import com.silencecork.google.search.nearby.GooglePlaceApi;
import com.silencecork.google.search.nearby.Place;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListActivity extends Activity implements OnItemClickListener {

	private GooglePlaceApi mGooglePlaceApi;
	
	private ListView mPlaceList;
	
	private ProgressDialog mProgressDialog;
	
	private ArrayAdapter<Place> mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		Intent intent = getIntent();
		final String keyword = intent.getStringExtra("keyword");
		
		mPlaceList = (ListView) findViewById(R.id.listView1);
		mPlaceList.setOnItemClickListener(this);
		
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage("½Ðµyµ¥");
		
		mGooglePlaceApi = new GooglePlaceApi("AIzaSyBghUpDzrgBqWuNz9M7tsGLbh7j4BWxPAQ");
		
		AsyncTask<Void, Void, List<Place>> task = new AsyncTask<Void, Void, List<Place>>() {


			@Override
			protected void onPreExecute() {
				mProgressDialog.show();
			}

			@Override
			protected List<Place> doInBackground(Void... arg0) {
				List<Place> list = mGooglePlaceApi.search(24.990137, 121.545524, 3000, keyword);
				return list;
			}
			
			@Override
			protected void onPostExecute(List<Place> result) {
				mProgressDialog.dismiss();
				
				mAdapter = new ArrayAdapter(ListActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, result);
				mPlaceList.setAdapter(mAdapter);
			}
		};
		task.execute();
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		Place place = mAdapter.getItem(position);
		Intent intent = new Intent(this, MapActivity.class);
		intent.putExtra("place", place);
		startActivity(intent);
	}
}
