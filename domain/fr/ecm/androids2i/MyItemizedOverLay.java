package fr.ecm.androids2i;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MyItemizedOverLay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> myOverlays;
	private ArrayList<Integer> pois_id = new ArrayList<Integer>();

	public MyItemizedOverLay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		myOverlays = new ArrayList<OverlayItem>();
		populate();
	}

	public void addOverlay(int i, OverlayItem overlay) {
		pois_id.add(i);
		myOverlays.add(overlay);
		populate();
	}

	protected OverlayItem createItem(int i) {
		return myOverlays.get(i);
	}

	protected boolean onTap(int i) {
		Log.i("onTap", "onTap() methode init!");
		Log.i("onTap", "title: " + myOverlays.get(0).getTitle());
		/*
		 EditText editText = new EditText(FinderMapActivity.context); GeoPoint
		 point = myOverlays.get(0).getPoint(); POI poi =
		 FinderActivity.getPois().get(poi_ids.get(i));
		 editText.setText(poi.getNom()); LayoutParams lp = new
		 LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
		 point, LayoutParams.TOP_LEFT); mapView.addView(editText, lp);
		 */
		
		POI poi = FinderActivity.getPois().get(pois_id.get(i));
		Toast.makeText(FinderMapActivity.context, poi.getNom(), Toast.LENGTH_SHORT).show();
		Log.i("Alert", poi.getNom());
//		AlertDialog.Builder dialog = new AlertDialog.Builder(FinderMapActivity.context);
//		 dialog.setTitle(poi.getNom());
//		 dialog.setMessage(poi.getSecteur());
//		 dialog.show();
		return true;
	}

	public int size() {
		return myOverlays.size();
	}
}
