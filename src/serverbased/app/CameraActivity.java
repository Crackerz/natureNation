package serverbased.app;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * This activity will hold the Camera View within our app allowing users to
 * take photos without leaving the app.
 */
public class CameraActivity extends Activity {

	@Override
	public void onCreate(Bundle savedState) {
		super.onCreate(savedState);
		if(!cameraExists()) {this.finish();} //We didn't find a camera.
		
		Camera c = getCamera(); //Grab a pointer to the camera
		if(c==null) {this.finish();} //Nothing to do without a camera

		this.setContentView(R.layout.camera_activity);
		
		CameraView camera = new CameraView(this,c);
		FrameLayout cameraHolder = (FrameLayout) findViewById(R.id.camera_holder);
		
		cameraHolder.addView(camera);
	}
	
	@Override
	public void onPause() {
		releaseCamera();
		super.onPause();
	}
	
	/**
	 * Check to see if there is a camera on this phone. TECHNICALLY Google Play
	 * shouldn't allow this app to be installed on devices without a camera,
	 * thanks to our manifest, but this is still best practice.
	 * @return
	 * true if camera is found, false otherwise.
	 */
	private boolean cameraExists() {
		if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
			return true;//Found Camera
		Toast.makeText(this, "No Camera Available", Toast.LENGTH_SHORT).show();
		return false;//Did Not Find Camera
	}
	
	/**
	 * This function retrieves the camera. This function assumes you know
	 * that the camera already exists.
	 * @return
	 * null if camera can not be accessed, a pointer to the camera object
	 * otherwise
	 */
	private Camera getCamera() {
		Camera result = CameraWrapper.getInstance();
		if(result==null)
			Toast.makeText(this, "Camera Could Not Be Locked", Toast.LENGTH_SHORT).show();
		return CameraWrapper.getInstance();
	}
	
	private void releaseCamera() {
		CameraWrapper.release();
	}
	
}
