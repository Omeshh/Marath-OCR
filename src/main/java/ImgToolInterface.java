package Project;

public interface ImgToolInterface {
	    public static final int BKGRND=0;

		public static final double PI=Math.PI;
		
	    public static final int ZERNIKE_DISTANCE_COMPLEX = 0; // using complex itself as feature
	    
	    public static final int ZERNIKE_DISTANCE_MODULOUS = 1; //using complex's magtitude as feature
	    public static final int ZERNIKE_DISTANCE_NZMI = 2; //using NZMI as feature
	    public static final int zernike_mode=ZERNIKE_DISTANCE_MODULOUS;

}
