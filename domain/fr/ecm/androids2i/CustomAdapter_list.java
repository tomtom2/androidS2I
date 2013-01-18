package fr.ecm.androids2i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapter_list extends BaseAdapter implements Filterable {

	ArrayList<POI> pois;
	LayoutInflater inflater;
	Resources res;
	Context context;
	FinderListActivity host;
	MyFilter filter;
	
	private static int SELECT_ON_NAME = 0;
	private static int SELECT_ON_QUARTIER = 1;
	private static int SELECT_ON_SECTEUR = 2;
	
	public CustomAdapter_list(FinderListActivity host, Resources res, Context context, HashMap<Integer, POI> data) {
		super();
		this.res = res;
		this.context = context;
		this.host = host;
		inflater = LayoutInflater.from(context);
		pois = new ArrayList<POI>();
		for(int key : data.keySet())
			pois.add(data.get(key));
		this.filter = null;
	}

	@Override
	public int getCount() {
		return pois.size();
	}

	@Override
	public Object getItem(int i) {
		return pois.get(i);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout layoutItem;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layoutitem, parent, false);
            layoutItem = (LinearLayout) convertView;
            //layoutItem.setBackgroundColor(Color.MAGENTA);
        }
        final POI poi = pois.get(position);

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView description = (TextView) convertView.findViewById(R.id.description);
        ImageView image = (ImageView) convertView.findViewById(R.id.img);
        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);

        title.setText(poi.getNom());
        description.setText(poi.getSecteur()+" - "+poi.getQuartier());
        checkBox.setChecked(poi.isFavorit());
        Drawable drawable = res.getDrawable(R.drawable.ic_launcher);
		if(poi.getImage()!=null){
			drawable = new BitmapDrawable(res, poi.getImage());
		}
        image.setImageDrawable(drawable);
        
        checkBox.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if (checkBox.isChecked()) {
					Toast.makeText(context, poi.getNom()+" added to favorites", Toast.LENGTH_SHORT).show();
		            host.addToFavorites(poi.getId());
		            return;
				}
				host.removeFromFavorites(poi.getId());
			}
        	
        });
        convertView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				host.runDetail(poi);
			}
        	
        });

        return convertView;
    }

	public MyFilter getFilter() {
		if (filter == null){
			filter = new MyFilter();
		}
	    return filter;
	}
	
	
	
	
	
	
	
	
	
	
	class MyFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			pois = new ArrayList<POI>();
			for(int key : FinderActivity.getPois().keySet())
				pois.add(FinderActivity.getPois().get(key));
		    // We implement here the filter logic
		    if (constraint == null || constraint.length() == 0) {
		        // No filter implemented we return all the list
		        results.values = pois;
		        results.count = pois.size();
		    }
		    else {
		        // We perform filtering operation
		        List<POI> poiList = new ArrayList<POI>();
		        int category = host.getCategory();
		        for (POI p : pois) {
		        	String select = "";
		        	if(category==CustomAdapter_list.SELECT_ON_QUARTIER){
		        		select = p.getQuartier();
		        	}
		        	else if(category==CustomAdapter_list.SELECT_ON_SECTEUR){
		        		select = p.getSecteur();
		        	}
		        	else{
		        		select = p.getNom();
		        	}
		            if (select.toUpperCase().startsWith(constraint.toString().toUpperCase()))
		                poiList.add(p);
		        }
		         
		        results.values = poiList;
		        results.count = poiList.size();
		 
		    }
		    return results;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			// Now we have to inform the adapter about the new list filtered
		    if (results.count == 0)
		        notifyDataSetInvalidated();
		    else {
		        pois = (ArrayList<POI>) results.values;
		        notifyDataSetChanged();
		    }
		}

	}


	
	
}
