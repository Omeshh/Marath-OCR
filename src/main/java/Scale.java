package Project;


class Scale {

  	public Scale() {
  	}
	
	/**
   	*Grows an image by a factor of scalefactor using the replicate method
   	*@param src The input image array
   	*@param orig_w The width of the input image
   	*@param orig_h The height of the input image
   	*@param scalefactor The amount the image is to be grown
   	*@return The grown image array
   	*/


  	public int [] grow_replicate(int [] src, int orig_w, int orig_h, int scalefactor) {

    	int new_w = orig_w * scalefactor;
    	int new_h = orig_h * scalefactor;
    	int [] result = new int[new_w * new_h];
    	int sample;

    	for (int i = 0; i < new_h; i++) {
      		for (int j = 0; j < new_w; j++) {
				sample = src[(i / scalefactor) * orig_w + (j / scalefactor)];
				result[i * new_w + j] = sample;
      		}
    	}
    	return result;
  	} /* grow_replicate */
  

}
