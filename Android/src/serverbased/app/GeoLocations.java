package serverbased.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class GeoLocations extends MapActivity
{
	MapView map;
	MyLocationOverlay myLocOverlay;
    MapController mc;
    Button findMe, placepins;
    int press = 0;
    private ArrayList<Entry> radiusData = null;
    private MyItemizedOverlay itemOverlay;
    private Drawable drawable;
    private List<Overlay> mapOverlays;
    public Context marker;
    LocationManager locationManager;
	private GeoPoint g;
    
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.geolocation);
		Bundle extras = getIntent().getExtras();
		marker = getApplicationContext();
		Object temp_data = extras.getSerializable("radiusData");
		radiusData = (ArrayList<Entry>) extras.getSerializable("radiusData");
		map = (MapView)findViewById(R.id.mapview);
		findMe = (Button)findViewById(R.id.currloc);
		placepins = (Button)findViewById(R.id.placepins);
		placepins.setEnabled(false);
		findMe.setOnClickListener(new OnClickListener()
    	{
			@Override
			public void onClick(View arg0)
			{
				findUser();
				placepins.setEnabled(true);
			}
    		
    	});
		
		placepins.setOnClickListener(new OnClickListener()
    	{
			@Override
			public void onClick(View arg0)
			{
				map.getOverlays().clear();
				myLocOverlay.enableMyLocation();
				map.getOverlays().add(myLocOverlay);
				mc = map.getController();
				myLocOverlay.runOnFirstFix(new Runnable() 
				{
					@Override
					public void run() 
					{}
				});
			
				dropThosePins();
				mc.setZoom(11);                                            // Zoom Strength (will be changed as needed)
				try
				{
					mc.animateTo(myLocOverlay.getMyLocation());
				}catch(Exception e)
				{
					Log.d("Loc", e.getMessage()+"");
				}
			}
    		
    	});
	}

	private void findUser() 
	{
		map.getOverlays().clear();
		if (press == 0)
		{
			myLocOverlay = new MyLocationOverlay(this, map);
			
			myLocOverlay.enableCompass();
			myLocOverlay.enableMyLocation();
			map.getOverlays().add(myLocOverlay);
			mc = map.getController();
			myLocOverlay.runOnFirstFix(new Runnable() 
			{
				@Override
				public void run() 
				{
					mc.setZoom(15);                                            // Zoom Strength (will be changed as needed)
					mc.animateTo(myLocOverlay.getMyLocation());
				}
			});
			
			 
			LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
			Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if(location == null)
				location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

			double longitude = location.getLongitude();
			double latitude = location.getLatitude();
			 g = new GeoPoint((int)(latitude * 1E6),(int)(longitude * 1E6));
			
			mc.setCenter(g);
			map.invalidate();
			
			
			press = 1;
		}
		else 
		{
			myLocOverlay.enableMyLocation();
			map.getOverlays().add(myLocOverlay);
			mc = map.getController();
			myLocOverlay.runOnFirstFix(new Runnable() 
			{
				@Override
				public void run() 
				{
					mc.setZoom(15);                                            // Zoom Strength (will be changed as needed)
					mc.animateTo(myLocOverlay.getMyLocation());
				}
			});
			//mc.setCenter(g);
		}
    }
	
	public void onLocationChanged(Location location)
	{
		//List<Overlay> overlays = map.getOverlays();
		int lat = (int)(location.getLatitude() * 1E6);
		int lng = (int)(location.getLongitude() * 1E6);
		GeoPoint p = new GeoPoint(lat, lng);
		mc.setCenter(p);
		map.invalidate();
	}
	
	private void dropThosePins()
	{
		
		mapOverlays = map.getOverlays();
		drawable = getResources().getDrawable(R.drawable.redpin);
		itemOverlay = new MyItemizedOverlay(drawable, this);
		double pinLatd = 0;
		double pinLongd = 0;
		int pinLat = 0;
		int pinLong = 0;
		
		
		for (int i = 0; i < radiusData.size(); i++)
		{
			pinLatd = Double.parseDouble(radiusData.get(i).latitude);
			pinLongd = Double.parseDouble(radiusData.get(i).longitude);
			pinLatd *= 1E6;
			pinLongd *= 1E6;
			pinLat = (int) pinLatd;
			pinLong = (int) pinLongd;
			GeoPoint gp = new GeoPoint(pinLat, pinLong);
			itemOverlay.addItem(gp, "", radiusData.get(i).animal);
			mapOverlays.add(itemOverlay);
		}
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		if (press == 1)
			myLocOverlay.disableMyLocation();
	}
	
	@Override
	protected boolean isRouteDisplayed() 
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
