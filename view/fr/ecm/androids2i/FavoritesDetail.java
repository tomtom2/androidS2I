package fr.ecm.androids2i;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FavoritesDetail extends Activity implements OnClickListener {

	private Button back;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_layout_favorites);
		Bundle bundle = getIntent().getExtras();

		String poi_id = "";
        if(bundle.getString("favorites_detail")!= null)
        {
        	poi_id= bundle.getString("favorites_detail");
        }
        int id = Integer.valueOf(poi_id);
        POI poi = FinderActivity.getPois().get(id);
        
     // set the image
     		Drawable drawable = this.getResources().getDrawable(
     				R.drawable.ic_launcher);
     		if (poi.getImage() != null) {
     			drawable = new BitmapDrawable(getResources(), poi.getImage());
     		}
     		ImageView image = (ImageView) findViewById(R.id.image);
     		image.setImageDrawable(drawable);

     		// set name
     		TextView name = (TextView) findViewById(R.id.name);
     		name.setText(poi.getNom());

     		// set location
     		TextView location = (TextView) findViewById(R.id.location);
     		location.setText(poi.getSecteur() + " - " + poi.getQuartier());

     		// set info
     		TextView info = (TextView) findViewById(R.id.info);
     		Spanned marked_up = Html.fromHtml(poi.getInformations());
     		info.setText(marked_up.toString(), TextView.BufferType.SPANNABLE);

     		// set buttons and associated action
     		back = (Button) findViewById(R.id.back);
     		back.setOnClickListener(this);
//		FragmentManager fragmentManager = getFragmentManager();
//		FragmentTransaction fragmentTransaction = fragmentManager
//				.beginTransaction();
//		DetailFragment_favorites fragment = new DetailFragment_favorites(poi);
//		fragmentTransaction.replace(android.R.id.list, fragment);
//		fragmentTransaction.commit();
	}

	@Override
	public void onClick(View v) {
		if (v == back) {
			finish();
		}		
	}
}
