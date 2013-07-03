package serverbased.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class LoadCachedImageTask extends AsyncTask<Void,Void,Bitmap> {

//	private int i;
	private String filepath;
	private ImageView img;
	
	public LoadCachedImageTask(String filePath, ImageView img){
	//	this.i =i;
		this.filepath = filePath;
		this.img = img;
	}
	@Override
	protected Bitmap doInBackground(Void... params) {
		// TODO Auto-generated method stub
		Bitmap b;
		BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile( filepath, options );
	        options.inJustDecodeBounds = false;
	        options.inSampleSize = 32; 

	        b = BitmapFactory.decodeFile( filepath, options );
	        if ( b != null ) {
	            b = Bitmap.createScaledBitmap( b, b.getWidth(), b.getHeight(), false );
	        }
		
		return b;
	}
	@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		img.setImageBitmap(result);
	//	pb.setVisibility(View.GONE);
	}

}
