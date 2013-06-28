package serverbased.app;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MyItemizedOverlay extends ItemizedOverlay<OverlayItem> 
{
	
	private ArrayList<OverlayItem> overlayItemList = new ArrayList<OverlayItem>();
	private Context context;
	
	public MyItemizedOverlay(Drawable marker, Context c) 
	{
		super(boundCenterBottom(marker));
		context = c;
		populate();
	}

	public void addItem(GeoPoint p, String title, String snippet) 
	{
		OverlayItem newItem = new OverlayItem(p, title, snippet);
		overlayItemList.add(newItem);
		populate();
	}

	public void removePoint()
	{
		this.overlayItemList.clear();
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) 
	{
		// TODO Auto-generated method stub
		return overlayItemList.get(i);
	}

	@Override
	public int size() 
	{
		// TODO Auto-generated method stub
		return overlayItemList.size();
	}

	@Override
	protected boolean onTap(int index) 
	{
		OverlayItem item = (OverlayItem) overlayItemList.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle("Animal");
		dialog.setMessage(item.getSnippet());
		dialog.setNegativeButton("Close", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.cancel();
			}
		});
		dialog.show();
		return true;
	}

	@Override
	public void draw(Canvas canvas, MapView map, boolean shadow)
	{
		// TODO Auto-generated method stub
		super.draw(canvas, map, false);
	}
}
