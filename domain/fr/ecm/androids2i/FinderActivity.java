package fr.ecm.androids2i;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.TabHost;
import fr.ecm.jsonparsing.JSONParser;

@SuppressWarnings("deprecation")
@SuppressLint({ "NewApi", "UseSparseArrays" })
public class FinderActivity extends TabActivity implements Runnable {

	ProgressDialog progressBar;
	private int progressBarStatus = 0;
	private Handler progressBarHandler = new Handler();
	
	Resources res;
	TabHost tabHost;

	private static HashMap<Integer, POI> pois = new HashMap<Integer, POI>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		setContentView(R.layout.activity_finder);
		res = getResources(); // Resource object to get Drawables
		tabHost = getTabHost(); // The activity TabHost
		buildTabs();
		getJsonStream();
		//setMockData();
		//force the methode that apply the changes on the current tab
//		String tabTag = getTabHost().getCurrentTabTag(); 
//		FinderListActivity activity = (FinderListActivity) getLocalActivityManager().getActivity(tabTag);
//		activity.launchMainFragment();
	}

	public void buildTabs() {
		res = getResources(); // Resource object to get Drawables
		tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec;
		
		// Create an Intent to launch an Activity for the tab (to be reused)
		Intent intentList = new Intent().setClass(this,
				FinderListActivity.class);

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec("list").setIndicator("List",
				res.getDrawable(R.drawable.ic_tab_list));
		spec.setContent(intentList);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		Intent intentMap = new Intent().setClass(this, FinderMapActivity.class);
		spec = tabHost.newTabSpec("map")
				.setIndicator("Map", res.getDrawable(R.drawable.ic_list))
				.setContent(intentMap);
		tabHost.addTab(spec);

		Intent intentFavorites = new Intent().setClass(this,
				FavoritesActivity.class);
		spec = tabHost
				.newTabSpec("favorites")
				.setIndicator("Favorites",
						res.getDrawable(R.drawable.ic_launcher))
				.setContent(intentFavorites);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
	}

	public void setCurrentTab(int i) {
		TabHost tabHost = getTabHost(); // The activity TabHost
		tabHost.setCurrentTab(i);
	}

	public void onClick(View v) {

	}

	public void getJsonStream() {
		// prepare for a progress bar dialog
		progressBar = ProgressDialog.show(this, "Loading...", "Finding locations:");
		new Thread(new Runnable() {
			public void run() {
				// Creating JSON Parser instance
				JSONParser jParser = new JSONParser();
				// getting JSON string from URL
				String url = "http://cci.corellis.eu/pois.php";
				JSONObject json = jParser.getJSONFromUrl(url);
				// Getting Array of Contacts
				JSONArray contacts = null;
				try {
					contacts = json.getJSONArray("results");
//					progressBar.setIndeterminate(false);
//					progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//					progressBar.setProgress(0);
//					progressBar.setMax(contacts.length());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					for (int i = 0; i < contacts.length(); i++) {
						progressBarStatus = i;
						JSONObject c = contacts.getJSONObject(i);
						POI poi = new POI(c);
						// Storing each json item in variable
						FinderActivity.pois.put(c.getInt("id"), poi);

						progressBarHandler.post(new Runnable() {
							public void run() {
								progressBar.setProgress(progressBarStatus);
							}
						});
					}
					progressBar.dismiss();
					
					//force the methode that apply the changes on the current tab
					String tabTag = getTabHost().getCurrentTabTag(); 
					FinderListActivity activity = (FinderListActivity) getLocalActivityManager().getActivity(tabTag);
					activity.launchMainFragment();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

	}

	public static HashMap<Integer, POI> getPois() {
		return pois;
	}

	@Override
	public void run() {
		getJsonStream();
	}
	
	private void setMockData(){
		for(int i=0; i<4; i++){
			POI poi = new POI();
			poi.setFavorit(false);
			poi.setId(i);
			poi.setInformations("info...");
			poi.setLat(40+i);
			poi.setLon(2+i);
			poi.setNom("nom");
			poi.setQuartier("quartier");
			poi.setSecteur("secteur");
			FinderActivity.getPois().put(poi.getId(), poi);
		}
	}

}
