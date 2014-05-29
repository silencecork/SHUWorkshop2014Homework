package com.example.shulbs;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.silencecork.google.search.nearby.Place;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class MapActivity extends FragmentActivity implements OnMarkerClickListener {

	private GoogleMap mMap;
	
	private Place mPlace;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		SupportMapFragment googleMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mMap = googleMapFragment.getMap();
		mMap.setOnMarkerClickListener(this);
		mMap.setMyLocationEnabled(true);
		
		Intent intent = getIntent();
		
		mPlace = intent.getParcelableExtra("place");
		
		MarkerOptions markerOption = new MarkerOptions();
		markerOption.position(new LatLng(mPlace.getLatitude(), mPlace.getLongitude()));
		markerOption.title(mPlace.getName());
		markerOption.snippet(mPlace.getVicinity());
		Marker marker = mMap.addMarker(markerOption);
		marker.showInfoWindow();
		
		CameraPosition camera = new CameraPosition.Builder()
		.target(new LatLng(mPlace.getLatitude(), mPlace.getLongitude()))
		.zoom(17.f).build();
		
		CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(camera);
		mMap.animateCamera(cameraUpdate);
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		// TODO save to favorite!!
		Toast.makeText(this, "You clicked Marker", Toast.LENGTH_LONG).show();
		return false;
	}
	
}
