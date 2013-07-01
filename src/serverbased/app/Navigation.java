package serverbased.app;

import java.io.Serializable;

public class Navigation implements Serializable {

	private static final long serialVersionUID = 4402458279246954745L;
	
	public Entry e;
	public double d;
	
	public Navigation(Entry e,double d){
		this.e = e;
		this.d = d;
	}
}
