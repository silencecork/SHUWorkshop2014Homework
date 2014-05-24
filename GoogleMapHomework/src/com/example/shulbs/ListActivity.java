package com.example.shulbs;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.silencecork.google.search.nearby.GooglePlaceApi;
import com.silencecork.google.search.nearby.Place;

public class ListActivity extends Activity {

	private GooglePlaceApi mGooglePlaceApi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		mGooglePlaceApi = new GooglePlaceApi("");
		List<Place> list = mGooglePlaceApi.search(24.990137, 121.545524, 3000, "");
	}
}
