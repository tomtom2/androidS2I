package fr.ecm.androids2i;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;
import fr.ecm.jsonparsing.JSONParser;

public class FinderListActivity extends Activity {

	private DetailFragment fragment;
	private ListFragment listFragment = null;

	private int category = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		setContentView(R.layout.listview_layout);
	}
	
	public void launchMainFragment(){
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		listFragment = new ListFragment(this);
		fragmentTransaction.replace(R.id.list_layout, listFragment);
		fragmentTransaction.commit();
	}
	
	public void onResume(){
		super.onResume();
	}

	public void computeResult(int result) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "result: " + result, Toast.LENGTH_SHORT).show();
	}

	public void runDetail(POI poi) {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragment = new DetailFragment(poi);
		fragmentTransaction.replace(R.id.list_layout, fragment);
		fragmentTransaction.commit();
	}

	public void goToMap(int poi_id) {
		Log.i("Frag", "go to map!");
		FragmentManager fragmentManager = getFragmentManager();
		// Or: FragmentManager fragmentManager = getSupportFragmentManager()
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(R.id.list_layout, listFragment);
		fragmentTransaction.commit();

		SharedPreferences app_preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = app_preferences.edit();
		editor.putInt("focus", poi_id);
		editor.commit(); // Very important
		((FinderActivity) getParent()).setCurrentTab(1);
	}

	public void backToList() {
		// TODO Auto-generated method stub
		FragmentManager fragmentManager = getFragmentManager();
		// Or: FragmentManager fragmentManager = getSupportFragmentManager()
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(R.id.list_layout, listFragment);
		fragmentTransaction.commit();
	}

	public void goToFavoris(int poi_id) {
		// TODO Auto-generated method stub
		Log.i("Frag", "go to favoris!");
		FragmentManager fragmentManager = getFragmentManager();
		// Or: FragmentManager fragmentManager = getSupportFragmentManager()
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(R.id.list_layout, listFragment);
		fragmentTransaction.commit();

		// Get the app's shared preferences
		SharedPreferences app_preferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		Set<String> defaultSet = new HashSet<String>(); // on crée notre Set
		Set<String> set = app_preferences.getStringSet("favoris", defaultSet);
		SharedPreferences.Editor editor = app_preferences.edit();
		set.add("" + poi_id);
		editor.putStringSet("favoris", set);
		editor.commit(); // Very important
		FinderActivity.getPois().get(poi_id).setFavorit(true);

		((FinderActivity) getParent()).setCurrentTab(2);
	}

	public void addToFavorites(int poi_id) {
		SharedPreferences app_preferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		Set<String> defaultSet = new HashSet<String>(); // on crée notre Set
		Set<String> set = app_preferences.getStringSet("favoris", defaultSet);
		SharedPreferences.Editor editor = app_preferences.edit();
		set.add("" + poi_id);
		editor.putStringSet("favoris", set);
		editor.commit(); // Very important
		
		FinderActivity.getPois().get(poi_id).setFavorit(true);
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
	}

	public void setCategory(int arg2) {
		category = arg2;
	}

	public int getCategory() {
		return category;
	}
}
