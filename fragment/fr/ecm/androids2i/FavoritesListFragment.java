package fr.ecm.androids2i;

import java.util.HashMap;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class FavoritesListFragment extends Fragment {

	private FavoritesActivity host;
	private CustomAdapter_favoris mShedule;
	private ListView myListView;
	
	private View view;
	private HashMap<Integer, POI> myList;

	public FavoritesListFragment(FavoritesActivity host, HashMap<Integer, POI> hashMap) {
		this.host = host;
		myList = hashMap;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.listview_layout_frag, container,
				false);

		myListView = (ListView) view.findViewById(android.R.id.list);

		mShedule = new CustomAdapter_favoris(host, host.getResources(),
				host.getBaseContext(), myList);

		myListView.setAdapter(mShedule);

		return view;
	}
	
}
