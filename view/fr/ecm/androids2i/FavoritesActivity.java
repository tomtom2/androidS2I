package fr.ecm.androids2i;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class FavoritesActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_layout);
		SharedPreferences app_preferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		Set<String> arg1 = new HashSet<String>();
		Set<String> favoris = app_preferences.getStringSet("favoris", arg1);

		System.out.println(favoris);
		// TextView textview = new TextView(this);
		// String txt = "favoris:\n";
		// for(String favori : favoris){
		// txt += favori+"\n";
		// }
		// textview.setText(txt);
		// setContentView(textview);

		ListView myListView = (ListView) findViewById(android.R.id.list);

		CustomAdapter_favoris mShedule = new CustomAdapter_favoris(this,
				this.getResources(), this.getBaseContext(), getPoiList(favoris));

		if(mShedule==null){
			System.out.println("mSchedule is NULL !");
		}
		else{
			System.out.println("mSchedule is not NULL !");
		}
		myListView.setAdapter(mShedule);
	}

	private HashMap<Integer, POI> getPoiList(Set<String> favoris) {
		// TODO Auto-generated method stub
		HashMap<Integer, POI> myList = new HashMap<Integer, POI>();
		for (String favori : favoris) {
			int favori_int = new Integer(favori);
			POI poi = FinderActivity.getPois().get(favori_int);
			myList.put(poi.getId(), poi);
		}
		return myList;
	}

	public void setContent() {
		SharedPreferences app_preferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		Set<String> arg1 = new HashSet<String>();
		// Get the value for the run counter
		Set<String> favoris = app_preferences.getStringSet("favoris", arg1);

		ListView myListView = (ListView) findViewById(android.R.id.list);

		CustomAdapter_favoris mShedule = new CustomAdapter_favoris(this,
				this.getResources(), this.getBaseContext(), getPoiList(favoris));

		myListView.setAdapter(mShedule);
	}

	@Override
	public void onResume() {
		SharedPreferences app_preferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		Set<String> arg1 = null;
		// Get the value for the run counter
		Set<String> favoris = app_preferences.getStringSet("favoris", arg1);

		for (String favori : favoris) {
			Log.i("Favorit Resume", favori);
		}
		Log.i("Favorit Resume", "repaint favorites");
		setContent();
		super.onResume();
	}

	public void removeFromFavorites(int poi_id) {
		SharedPreferences app_preferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		Set<String> defaultSet = new HashSet<String>(); // on crée notre Set
		Set<String> set = app_preferences.getStringSet("favoris", defaultSet);
		SharedPreferences.Editor editor = app_preferences.edit();
		set.remove("" + poi_id);
		editor.putStringSet("favoris", set);
		editor.commit(); // Very important

		FinderActivity.getPois().get(poi_id).setFavorit(false);
		onResume();
	}

	public void goToMap(POI poi) {
		// TODO Auto-generated method stub

	}
}
