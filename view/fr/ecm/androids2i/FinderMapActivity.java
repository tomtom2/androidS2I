package fr.ecm.androids2i;


import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class FinderMapActivity extends MapActivity {

	private List<Overlay> mapOverlays;
	public static Context context;
	private MapView mapView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layout);

		context = getApplicationContext();

		mapView = (MapView) findViewById(R.id.map_view);
		mapView.setSatellite(true);
		mapView.setBuiltInZoomControls(true);
		setOverlay();

		MapController mapController = mapView.getController();
		Double lat = 48.88803669 * 1E6;
		Double lon = 2.24758386 * 1E6;
		GeoPoint point = new GeoPoint(lat.intValue(), lon.intValue());


		mapController.setCenter(point);
		mapController.setZoom(18);
	}
	
	public void onResume(){
		super.onResume();
		
		SharedPreferences app_preferences = 
            	PreferenceManager.getDefaultSharedPreferences(this);
            int centre_id = app_preferences.getInt("focus", -1);
            if(centre_id==-1){
            	return;
            }
            
    		SharedPreferences.Editor editor = app_preferences.edit();
            editor.putInt("focus", -1);
            editor.commit(); // Very important
            
            POI poi = FinderActivity.getPois().get(centre_id);
            GeoPoint centre = new GeoPoint(poi.getLat(), poi.getLon());
            MapController mapController = mapView.getController();
            mapController.setCenter(centre);
		Log.i("Map Resume", "recenter mapController");
	}

	public void setOverlay() {
		mapOverlays = mapView.getOverlays();

		for (int key : FinderActivity.getPois().keySet()) {
			POI poi = FinderActivity.getPois().get(key);
			Drawable drawable = this.getResources().getDrawable(
					R.drawable.ic_launcher);
			if (poi.getImage() != null) {
				drawable = new BitmapDrawable(getResources(), poi.getImage());
			}
			MyItemizedOverLay itemizedOverlay = new MyItemizedOverLay(drawable);
			OverlayItem item = new OverlayItem(new GeoPoint((int) poi.getLat(),
					(int) poi.getLon()), poi.getNom(), poi.getSecteur() + " - "
					+ poi.getQuartier());
			itemizedOverlay.addOverlay(poi.getId(), item);
			mapOverlays.add(itemizedOverlay);
		}

		mapView.postInvalidate();
	}

	public void drawpath(GeoPoint destination) {
		LocationManager myLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// Get the current location in start-up
		GeoPoint start = new GeoPoint((int) (myLocationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER)
				.getLatitude() * 1000000), (int) (myLocationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER)
				.getLongitude() * 1000000));

		double fromlat = ((double) start.getLatitudeE6()) / 1e6;
		double fromlng = ((double) start.getLongitudeE6()) / 1e6;

		double tolat = ((double) destination.getLatitudeE6()) / 1e6;
		double tolng = ((double) destination.getLongitudeE6()) / 1e6;
		
		//String url = RoadProvider.getUrl(fromlat, fromlng, tolat, tolng);
		//InputStream is = getConnection(url);
		//RoadProvider mRoad = RoadProvider.getRoute(is);
		//MapOverlay mapOverlay = new MapOverlay(mRoad, mapView);
		//List<Overlay> listOfOverlays = mapView.getOverlays();
		//listOfOverlays.add(mapOverlay);
		mapView.invalidate();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
