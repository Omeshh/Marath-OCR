package Project;
/* 
 * FUNCTION thinning
 * This class implements the thinning by hilditch algorithm in thinning function
 * imageMatrix @PARAM for thinning function it has the original image
 * iHeight     @PARAM stores the height of the input image
 * iWidth  	   @PARAM stores the width of the input image
 *
 *
 * FUNCTION Crop 
 * the thinned image have many 0's which are not required the crop fuction 
 * reduces the size of the image such that the array with useful information
 * are kept and rest all are clipped
 * 
 *
 * NOTE: This function must be called after invoking the thinning function 
 */
class Thinning {
	
	int arrayWidth, arrayHeight;

	int iArray[][]; 
	int iArray1[][];	
	int top, bottom; 	
	int left, right;
	
	
	public int[][] thinning( int imageMatrix[][], int iHeight, int iWidth) {
		
		arrayHeight= iHeight;
		arrayWidth= iWidth;
		iArray= new int[arrayHeight][arrayWidth];
		for(int i= 0; i< arrayHeight; i++) 
	     		for(int j= 0; j< arrayWidth; j++)
	      			iArray[i][j]=imageMatrix[i][j];     

		

//	---------------Thinning of image----------------------    
   
	   	iArray1=new int[arrayHeight][arrayWidth];   
		int g= 1, h = 1;
	    
	   
		for(int ctr2= 0; ctr2< iHeight; ctr2++) {
			for(int ctr1= 0; ctr1< iWidth; ctr1++) 		
				System.out.print(iArray[ctr2][ctr1]);
			System.out.print("\n"); 
		}   

	 	
	 	while(h == 1 ) {
	  		
	   		h=0;	
	  		for(int i= 2; i< arrayHeight- 2; i++)
	   			for(int j= 2; j< arrayWidth- 2; j++)
	   				if(iArray[i][j] == 1) {
						int p[],p2[],p4[];int i1=i,j1=j;
	   					p=new int[9];
						p[0]= iArray[i-1][j];			p[1]= iArray[i-1][j-1]; 
						p[2]= iArray[i][j-1];			p[3]= iArray[i+1][j-1]; 
						p[4]= iArray[i+1][j];			p[5]= iArray[i+1][j+1]; 
						p[6]= iArray[i][j+1];			p[7]= iArray[i-1][j+1]; 
						p[8]= iArray[i-1][j];   

						
						int ap=0,bp=0; 
	
	    				 
	    				for( int k= 0; k< 8; k++) {
							if( p[k]< p[k+1] )
	       							ap++;
	      					if(p[k]	== 1)
	       						bp++;    
	     				}
	     				
	     				if(bp>1 && bp<7)
	     					if(ap==1)
		   						if( (p[2] == 0 || p[4] == 0 ) || (p[0] == 0 && p[6] == 0 ) ) {
			   						h=1; iArray1[i][j]=1;  
	       						}	    
	   				}
					
					for(int i= 0; i< arrayHeight; i++)
       					for(int j= 0; j< arrayWidth; j++)
	    					if(iArray1[i][j] == 1) {
	    							iArray[i][j]=iArray1[i][j]=0;
	    					}
	    					
	    			for(int i= 1; i< arrayHeight- 1; i++)
	   					for(int j= 1; j< arrayWidth- 1; j++)
							if(iArray[i][j] == 1) { 
								int p[];
		  						p= new int[9];
		  						p[0]= iArray[i-1][j]; 		p[1]= iArray[i-1][j+1]; 
		  						p[2]= iArray[i][j+1]; 		p[3]= iArray[i+1][j+1]; 
		  						p[4]= iArray[i+1][j]; 		p[5]= iArray[i+1][j-1]; 
		  						p[6]= iArray[i][j-1]; 		p[7]= iArray[i-1][j-1]; 
		  						p[8]= iArray[i-1][j];   
		  						
		  						int ap=0,bp=0;
		     
		  						for( int k= 0; k< 8; k++) {
	        						if( p[k]< p[k+1])
		     							ap++;
		    							if( p[k] == 1)
		     								bp++;    
		  						}
		  	
		  						if(bp> 1 && bp< 7)
		   							if(ap == 1)
										if( (p[0] == 0 || p[6] == 0) || (p[2] == 0 && p[4] == 0)) {
				   							h=1; iArray1[i][j]=1;  
		      							}	    
		   					}
		   					
	      			for(int i= 0; i< arrayHeight; i++)
	       				for(int j= 0; j< arrayWidth; j++)
		    				if(iArray1[i][j] == 1) {
		    						iArray[i][j]=iArray1[i][j]=0;
		    				} 		     	  
		}
		
		return iArray;
	}
	
	/* @return returns the thinned image */
	
	
	public void crop(int [][]img, int width, int height) {
		
		arrayWidth= width;
		arrayHeight= height;
		
		iArray= new int[arrayHeight][arrayWidth];
		
		for(int ctr= 0; ctr< arrayHeight; ctr++) 
			for( int ctr1= 0; ctr1< arrayWidth; ctr1++)
				iArray[ctr][ctr1]= img[ctr][ctr1];
			
			
		int flag= 1;
		top= 0;
		//fist we find the top of the image
		while(flag== 1) 
			for(int col= 0; col< arrayWidth; col++ ) 
				if( iArray[top][col] == 1) {
					flag= 0; col= arrayWidth;
				} else  {
					if(col == ( arrayWidth- 1) && flag == 1) {
						top++;
						col= 0;
					}
				}			
		
		
		// we find the bottom of the image;
		
		bottom= arrayHeight- 1;
		flag= 1;
		while(flag== 1) 
			for(int col= arrayWidth- 1; col>= 0; col-- ) 
				if( iArray[bottom][col] == 1) {
					flag= 0; col= 0;
				} else  {
					if(col == (0) && flag == 1) {
						bottom--;
						col= arrayWidth- 1;
					}
				}	
		
		// we find the left of the image
		
		left= 0; 
		flag= 1;
		while(flag== 1) 
			for(int row= 0; row< arrayHeight; row++ ) 
				if( iArray[row][left] == 1) {
					flag= 0; row= arrayHeight;
				} else  {
					if(row == ( arrayHeight- 1) && flag== 1 ) {
						left++;
						row= 0;
					}
				}	
		
		// we find the roght of the image		
		right= arrayWidth- 1; 
		flag= 1;
		while(flag == 1) 
			for(int row= 0; row< arrayHeight; row++ ) 
				if( iArray[row][right] == 1) {
					flag= 0; row= arrayHeight;
				} else  {
					if(row == ( arrayHeight- 1) && flag == 1) {
						right--;
						row= 0;
					}
				}			
		
		
		iArray1= new int[bottom- top + 1][right- left + 1];
		System.out.print("Right= "+right+"\nLeft= "+left+"\nTop= "+top+"\nBottom= "+bottom);
		for( int ctr= top, row= 0; ctr<= bottom; ctr++, row++) {
			for(int ctr1= left, col= 0 ; ctr1<= right; col++, ctr1++) {
				iArray1[row][col]= iArray[ctr][ctr1];
			}
		}
	}
	
	
	public int [][] getImage() {
		return iArray1;
	}
	public int getWidth() {
		return right- left + 1;
	}
	public int getHeight() {
		return bottom- top+ 1;
	}
}
