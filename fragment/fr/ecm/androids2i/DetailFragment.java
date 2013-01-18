package fr.ecm.androids2i;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class DetailFragment extends Fragment implements OnClickListener {

	private Button favoris;
	private Button yaller;
	private Button carte;
	private Button back;
	private POI poi;

	public DetailFragment(POI poi) {
		this.poi = poi;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.detail_layout, container, false);

		// set the image
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.ic_launcher);
		if (poi.getImage() != null) {
			drawable = new BitmapDrawable(getResources(), poi.getImage());
		}
		ImageView image = (ImageView) view.findViewById(R.id.image);
		image.setImageDrawable(drawable);

		// set name
		TextView name = (TextView) view.findViewById(R.id.name);
		name.setText(poi.getNom());

		// set location
		TextView location = (TextView) view.findViewById(R.id.location);
		location.setText(poi.getSecteur() + " - " + poi.getQuartier());

		// set info
		TextView info = (TextView) view.findViewById(R.id.info);
		Spanned marked_up = Html.fromHtml(poi.getInformations());
		info.setText(marked_up.toString(), TextView.BufferType.SPANNABLE);

		// set buttons and associated action
		favoris = (Button) view.findViewById(R.id.favoris);
		favoris.setOnClickListener(this);
		yaller = (Button) view.findViewById(R.id.yaller);
		yaller.setOnClickListener(this);
		carte = (Button) view.findViewById(R.id.carte);
		carte.setOnClickListener(this);
		back = (Button) view.findViewById(R.id.back);
		back.setOnClickListener(this);

		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == favoris) {
			((FinderListActivity) getActivity()).goToFavoris(poi.getId());
			// getActivity().getFragmentManager().beginTransaction().remove(this).commit();
		} else if (v == yaller) {
			Intent browserIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse(this.getUrl()));
			startActivity(browserIntent);
		} else if (v == carte) {
			((FinderListActivity) getActivity()).goToMap(poi.getId());
			// getActivity().getFragmentManager().beginTransaction().remove(this).commit();
		} else if (v == back) {
			((FinderListActivity) getActivity()).backToList();
		}

	}

	public String getUrl() {// connect to map web service
		double toLat = poi.getLat()/1e6;
		double toLon = poi.getLon()/1e6;
    StringBuffer urlString = new StringBuffer();
    urlString.append("http://maps.google.com/maps?f=d&hl=en");
//    urlString.append("&saddr=");// from
//    urlString.append(Double.toString(fromLat));
//    urlString.append(",");
//    urlString.append(Double.toString(fromLon));
    urlString.append("&daddr=");// to
    urlString.append(Double.toString(toLat));
    urlString.append(",");
    urlString.append(Double.toString(toLon));
    urlString.append("&ie=UTF8&0&om=0&output=kml");
    return urlString.toString();
}
}
