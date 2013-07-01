package serverbased.app;

import android.hardware.Camera;

/**
 * A singleton wrapper ensuring only one instance of Camera ever exists in
 * our app.
 */
public class CameraWrapper {

	private static Camera c = null; //Singleton
	
	private CameraWrapper() {/*Prevent constructor from being used.*/}
	
	/**
	 * Get a reference to the camera and lock it.
	 */
	public static Camera getInstance() {
		
		if(c!=null) return c; //If c already exists, return it
		
		try {
			c = Camera.open();
			c.lock();
		} catch (Exception e) {
			//Camera is not available. Function will return null.
			c = null;
		}
		
		return c;
	}
	
	/**
	 * Release the camera back to the OS
	 */
	public static void release() {
		if(c==null)
			return; //If c isn't instantiated, do nothing
		c.unlock();
		c=null;
	}
}
