package serverbased.app;

import java.util.ArrayList;

import serverbased.app.MaxHeap.Obj;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class LoadNaviDataTask extends AsyncTask<Void,Void,Void> {
// This Task is used to load the data items for the Navigation list
	double latitude;
	double longitude;
	ArrayList<Navigation> nav = new ArrayList<Navigation>();
	Entry[] e = new Entry[10];
	boolean success = false;
	Context c;
	public LoadNaviDataTask(double latitude,double longitude,Context c ){
		this.latitude = latitude;
		this.longitude = longitude;
		this.c = c;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try{
		GeoDistance g = new GeoDistance();
		ArrayList<Entry> data ;
		data = g.getAll();
		
		MaxHeap h = new MaxHeap(20);
		for(int i=0; i<20;i++){
			Entry e = data.get(i);
			double lat = Double.parseDouble(e.latitude);
			double lon = Double.parseDouble(e.longitude);
			h.push(g.getDistance(latitude, longitude, lat, lon),e);
		}
		
		for(int i=20;i<data.size();i++){
			Entry e = data.get(i);
			double lat = Double.parseDouble(e.latitude);
			double lon = Double.parseDouble(e.longitude);
			double dist = g.getDistance(latitude, longitude, lat, lon);
			if( dist < h.o[1].k){
				h.pop();
				h.push(dist, e);
			}			
		}
		for(int i=19; i>=0; i--){
			Obj temp = h.pop();
			Navigation v = new Navigation(temp.v,temp.k);
			nav.add(v);
		}
		}catch(Exception e){
			Log.d("Navi Tag",e.getMessage()+ "");
		}
		
		
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		success = true;
	}
	
	
	

}
