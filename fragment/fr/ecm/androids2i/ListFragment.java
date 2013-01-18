package fr.ecm.androids2i;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class ListFragment extends Fragment {

	private FinderListActivity host;
	private CustomAdapter_list mShedule;
	private Spinner spinner;
	private ListView myListView;
	private EditText mySearch;
	
	private View view;

	public ListFragment(FinderListActivity host) {
		this.host = host;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.listview_layout_frag, container,
				false);

		myListView = (ListView) view.findViewById(android.R.id.list);

		mShedule = new CustomAdapter_list(host, host.getResources(),
				host.getBaseContext(), FinderActivity.getPois());

		spinner = (Spinner) view.findViewById(R.id.category);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				host.setCategory(arg2);
				String s = mySearch.getText().toString();
				System.out.println("search on: "+s);
				mShedule.getFilter().filter(s.toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		mySearch = (EditText) view.findViewById(R.id.search);
		mySearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mShedule.getFilter().filter(s.toString());
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		myListView.setAdapter(mShedule);

		return view;
	}
	
}
