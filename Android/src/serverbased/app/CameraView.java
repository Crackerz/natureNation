package serverbased.app;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

/**
 * This view can be embedded in an Activity to give the user access
 * to the camera from within the app
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback{
	private SurfaceHolder holder;
	private Camera camera;
	private Context context;
	
	@SuppressWarnings("deprecation")
	public CameraView(Context context, Camera camera) {
		super(context);
		
		this.context = context;
		this.camera = camera;
		
		//Make sure this class catches notifications for object.
		holder = getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); //Needed for <3.0 compat.
	}
	
	public CameraView(Context context) {
		super(context);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		if(holder.getSurface()==null)
			return; //Surface has been deleted.
		
		try { //Attempt to stop the camera
			camera.stopPreview();
		} catch(Exception e) {} //Doesn't matter if this fails, going to try to start it anyways
		
		try {
			camera.setPreviewDisplay(holder);
			camera.startPreview();
		} catch (Exception e) {
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		try {
			camera.setPreviewDisplay(holder);
			camera.startPreview();
		} catch(Exception e) {
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		//Take care of destroying assets in the activity, not here.
	}

}
