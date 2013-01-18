package fr.ecm.androids2i;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapter_favoris extends BaseAdapter {

	ArrayList<POI> pois;
	LayoutInflater inflater;
	Resources res;
	Context context;
	FavoritesActivity host;
	
	public CustomAdapter_favoris(FavoritesActivity host, Resources res, Context context, HashMap<Integer, POI> data) {
		super();
		this.res = res;
		this.context = context;
		this.host = host;
		inflater = LayoutInflater.from(context);
		pois = new ArrayList<POI>();
		for(int key : data.keySet())
			pois.add(data.get(key));
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
            layoutItem.setBackgroundColor(Color.MAGENTA);
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
		            //host.addToFavorites(poi.getId());
		            return;
				}
				host.removeFromFavorites(poi.getId());
			}
        	
        });
        convertView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				host.goToMap(poi);
			}
        	
        });

        return convertView;
    }
}
