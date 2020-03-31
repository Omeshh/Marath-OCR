package Project;



/**
 *Threshold is an algorithm to apply thresholding to an image.
 *the output image will have only two values either black or white
 *the threshold value selected is 128 which can be chanded this is the argument for the
 * thereshold function.
 */


class Threshold extends Thread{
  
  //the width and height of this image in pixels
  	private int i_w, i_h;
  
  //pixel arrays for input and output images
  	private int[] src_1d;
  	private int[] dest_1d;
  
  /**
   *Constructs a new Threshold
   */
  	public Threshold(){
    
  	}
  
  /** 
   *Applies the thresholding operator with the specified threshold
   *value to the specified image array
   *
   *@param src pixel array representing image to be thresholded
   *@param width width of the image in pixels
   *@param height height of the image in pixels
   *@param value threshold value
   *@return a thresholded pixel array  of the input image array
   */
  	public int [] threshold(int [] src, int width, int height, int value){
    
    	i_w = width;
    	i_h = height;
//***    src_1d = new int[i_w * i_h];
    	dest_1d = new int[i_w*i_h];
    	src_1d = src;
 
    	apply_threshold(value);
    
    	return dest_1d;
  	}

/** 
	*Applies the thresholding operator between the specified threshold
	*values to the specified image array
   	*
   	*@param src pixel array representing image to be thresholded
   	*@param width width of the image in pixels
   	*@param height height of the image in pixels
   	*@param low lower threshold value
   	*@param high higher threshold value
   	*@return a thresholded pixel array  of the input image array
*/

  
  	public int [] twothreshold(int [] src, int width, int height, int low, int high){
     
    	i_w = width;
    	i_h = height;
    	dest_1d = new int[i_w*i_h];
    	src_1d = src;
	    
    	apply_two_threshold(low, high);
    	return dest_1d;
  	}
  

  //applies threshold to black-white images
  	private void  apply_threshold(int thresh) {

    	int blue = 0; 
    	for (int i=0; i<src_1d.length; i++) {
      		blue = src_1d[i] & 0x000000ff; 
      		dest_1d[i] = (blue>=thresh)?0xffffffff:0xff000000;
    	}
  	}

  //applies two thresholds to black and white image
  //if a pixel is between the threshold values then colour it white
  //other wise it should be black

  	private int []  apply_two_threshold(int low, int high) {
    
    	int blue = 0; 

    	for (int i=0; i<src_1d.length; i++){
      		blue = src_1d[i] & 0x000000ff; 
      		if ((blue<= high) && (blue >= low)) {
				dest_1d[i] = 0xffffffff;
      		} else dest_1d[i] = 0xff000000;
    	}
    	return dest_1d;
  	}

}