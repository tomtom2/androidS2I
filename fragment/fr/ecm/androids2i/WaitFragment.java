package fr.ecm.androids2i;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import fr.ecm.jsonparsing.JSONParser;

public class WaitFragment extends Fragment {

	private ProgressBar progressBar;

	public WaitFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.wait_layout, container, false);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
		progressBar.setIndeterminate(false);
		return view;
	}

	public void loadData() {
		// Creating JSON Parser instance
		JSONParser jParser = new JSONParser();

		// getting JSON string from URL
		String url = "http://cci.corellis.eu/pois.php";
		JSONObject json = jParser.getJSONFromUrl(url);

		// Getting Array of Contacts
		JSONArray contacts = null;
		try {
			contacts = json.getJSONArray("results");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			progressBar.setMax(contacts.length());
			for (int i = 0; i < contacts.length(); i++) {
				progressBar.setProgress(i);
				JSONObject c = contacts.getJSONObject(i);
				POI poi = new POI(c);
				// Storing each json item in variable
				FinderActivity.getPois().put(c.getInt("id"), poi);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
