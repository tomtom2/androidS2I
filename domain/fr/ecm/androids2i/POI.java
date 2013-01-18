package fr.ecm.androids2i;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class POI {

	private int id;
	private String nom;
	private int lat;
	private int lon;
	private Bitmap image;
	private String secteur;
	private String quartier;
	private String informations;
	private boolean favorit;
	
	// JSON Node names
    //private static final String TAG_CONTACTS = "results";
    private static final String TAG_ID = "id";
    //private static final String TAG_CATEGORIE_ID = "categorie_id";
    private static final String TAG_NAME = "nom";
    private static final String TAG_LAT = "lat";
    private static final String TAG_LON = "lon";
    //private static final String TAG_IMG = "image";
    private static final String TAG_IMG_SMALL = "small_image";
    private static final String TAG_SECTEUR = "secteur";
    private static final String TAG_QUARTIER = "quartier";
    private static final String TAG_INFO = "informations";
	
    public POI(){
    	
    }
	public POI(JSONObject p){
		favorit = false;
		try {
			id = p.getInt(TAG_ID);
			nom = p.getString(TAG_NAME);
			lat = (int) (p.getDouble(TAG_LAT)*1e6);
			lon = (int) (p.getDouble(TAG_LON)*1e6);
			
			secteur = p.getString(TAG_SECTEUR);
			quartier = p.getString(TAG_QUARTIER);
			informations = p.getString(TAG_INFO);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		image = null;
		try {
			URL pictureURL = new URL(p.getString(TAG_IMG_SMALL));
			image = BitmapFactory.decodeStream(pictureURL.openStream());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(image!=null){
			image = Bitmap.createScaledBitmap(image, 72, 72, true);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getLat() {
		return lat;
	}

	public void setLat(int lat) {
		this.lat = lat;
	}

	public int getLon() {
		return lon;
	}

	public void setLon(int lon) {
		this.lon = lon;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public String getSecteur() {
		return secteur;
	}

	public void setSecteur(String secteur) {
		this.secteur = secteur;
	}

	public String getQuartier() {
		return quartier;
	}

	public void setQuartier(String quartier) {
		this.quartier = quartier;
	}

	public String getInformations() {
		return informations;
	}

	public void setInformations(String informations) {
		this.informations = informations;
	}
	public boolean isFavorit() {
		return favorit;
	}
	public void setFavorit(boolean favorit) {
		this.favorit = favorit;
	}
	
}
