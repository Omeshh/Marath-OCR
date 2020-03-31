package Project;

import javax.swing.*;
import java.awt.image.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import java.net.*;
import java.util.*;
import java.applet.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.PixelGrabber;


public class Binarize extends Applet/*extends JFrame*/{
	
//	private int 1024
	Dimension dim;
	Image img;
	int iWidth, iHeight;	int pixels[];	int imageMatrix[][];
	int width, height;	int hist[]= new int[256];
	int maxHist= 0;
	
	Binarize bin;

	Image newImage[]= new Image[2];
	int newMat[][];
	
	int arrayWidth, arrayHeight, imgArray;

	int iArray[][]; int iArray1[][];	int widthTop, widthBottom; 	int widthLeft, widthRight;
	
	double moments[];

	public Binarize(String fileName, String dirPath1, String dirPath2) {
		
		/*super("Optical Character Recognition : Marathi");
		setSize(800,600);
		setLocation(50,50);*/


		/*dim= getSize();
		width= dim.width;
		height= dim.height;
	
		bin = this; 


		/*this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				if(we.getSource() == bin) {
					bin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					//bin.setVisible(false);
					//System.exit(1);
				}
			}
		});

*/

//		show();


		

		try {
			GetImage gi= new GetImage(dirPath1, fileName);
			img= gi.returnImage();

			System.out.println(dirPath1+"\\"+ fileName);
	
			MediaTracker t= new MediaTracker(this);
			t.addImage(img, 0);
			t.waitForID(0);
			iWidth= img.getWidth(null);
			iHeight= img.getHeight(null);
			pixels= new int[iWidth* iHeight];
			PixelGrabber pg= new PixelGrabber(img, 0, 0, iWidth, iHeight, pixels, 0, iWidth);

			pg.grabPixels();
			

		} catch(Exception ex) {
		
		}

		
		Threshold threshold = new Threshold();
		pixels= threshold.threshold(pixels, iWidth, iHeight, 200); 
		img = createImage( new MemoryImageSource(iWidth, iHeight, pixels, 0, iWidth ));
		try {
      		MediaTracker tracker = new MediaTracker (this);
      		tracker.addImage (img,0);
	      	tracker.waitForID (0);
    	} catch (InterruptedException e) {  }

    	/*BufferedImage buffered_image1 = new BufferedImage (iWidth, iHeight, BufferedImage.TYPE_BYTE_GRAY);
    	Graphics2D g2 = buffered_image1.createGraphics ();
    	g2.drawImage (img,0 ,0, null);		
    	*/
    	imageMatrix = new int[iHeight][iWidth];
    	for(int ctr= 0; ctr< iHeight; ctr++) 
			for(int ctr1= 0; ctr1< iWidth; ctr1++) 		
				if(pixels[(ctr*iWidth) + ctr1] == 0xffffffff) 
					imageMatrix[ctr][ctr1]= 0;
				else 	imageMatrix[ctr][ctr1]= 1;
    	/*
    	for(int ctr= 0; ctr< iHeight; ctr++) 
			for(int ctr1= 0; ctr1< iWidth; ctr1++) 		
				if(imageMatrix[ctr][ctr1] == 0)
					pixels[(ctr*iWidth) + ctr1] = 0;
				else 	pixels[(ctr*iWidth) + ctr1] = 1;
    	
		Skew skewCorrection= new Skew();
		double angle;
		angle= skewCorrection.get_skew(buffered_image1);
		pixels= skewCorrection.rotate(pixels, iWidth, iHeight, angle);
    	
		imageMatrix = new int[iHeight][iWidth];

		for(int ctr= 0; ctr< iHeight; ctr++) 
			for(int ctr1= 0; ctr1< iWidth; ctr1++) 		
				if(pixels[(ctr*iWidth) + ctr1] == 0) 
					imageMatrix[ctr][ctr1]= 0;
				else 	imageMatrix[ctr][ctr1]= 1;
			*/	
		Thinning thin= new Thinning();
		/*
		imageMatrix= thin.thinning(imageMatrix, iHeight, iWidth);*/
		thin.crop(imageMatrix, iWidth, iHeight);
		
		iWidth= thin.getWidth();
		iHeight= thin.getHeight();
		
		imageMatrix= new int[iWidth][iHeight];
		imageMatrix= thin.getImage();
		// image image Matrix arfter it is cropped
		
		/* Replicating the image  */
		pixels= new int [iWidth*iHeight];
		
		for(int ctr= 0; ctr< iHeight; ctr++) 
			for(int ctr1= 0; ctr1< iWidth; ctr1++) 		
				if(imageMatrix[ctr][ctr1] == 0)
					pixels[(ctr*iWidth) + ctr1] = 0;
				else 	pixels[(ctr*iWidth) + ctr1] = 1;
		
		Scale scale= new Scale();
		
		int pixels1[]= new int [(iWidth*2)*(iHeight*2)];
		
		pixels1= scale.grow_replicate(pixels, iWidth, iHeight, 2);
		
		imageMatrix = new int[iHeight*2][iWidth*2];
		
		iHeight= 2* iHeight;
		iWidth= 2* iWidth;

		for(int ctr= 0; ctr< iHeight; ctr++) 
			for(int ctr1= 0; ctr1< iWidth; ctr1++) 		
				if(pixels1[(ctr*iWidth) + ctr1] == 0) 
					imageMatrix[ctr][ctr1]= 0;
				else 	imageMatrix[ctr][ctr1]= 1;
		
		double imgWidth= 40.0, imgHeight= 40.0;
		
		double scaleX= imgWidth/ iWidth;
		double scaleY= imgHeight/ iHeight;
		
		
		System.out.println("Image After cropped ");
		for(int i= 0; i< iHeight; i++ ) { 
			for(int j= 0; j< iWidth; j++) 
				System.out.print(imageMatrix[i][j]);
			System.out.print("\n");
		}
		
		
		newMat= new int[(int)imgHeight][(int)imgWidth];
		
		
		for(int i= 0; i< imgHeight; i++ ) 
			for(int j= 0; j< imgWidth; j++) 
				newMat[i][j]= 0;
		
		for(int i= 1; i< iHeight; i++ ) 
			for(int j= 1; j< iWidth; j++) 
				if(imageMatrix[i][j] == 1 ) { 
					newMat[(int) (scaleY*i)][(int) (scaleX*j)]= 1;
			}
		
		imageMatrix= new int[(int)imgHeight][(int)imgWidth];
		
		iWidth= iHeight= 40;
		
		for(int i= 0; i< iHeight; i++ ) 
			for(int j= 0; j< iWidth; j++) 
				imageMatrix[i][j]= newMat[i][j];
					
				
		imageMatrix= thin.thinning(imageMatrix, iHeight, iWidth);
		
		pixels= new int [iWidth*iHeight];
		for(int ctr= 0; ctr< iHeight; ctr++) 
			for(int ctr1= 0; ctr1< iWidth; ctr1++) 		
				if(imageMatrix[ctr][ctr1] == 0)
					pixels[(ctr*iWidth) + ctr1] = 0xffffffff;
				else 	pixels[(ctr*iWidth) + ctr1] = 0x00000000;
			
		
		img = createImage( new MemoryImageSource(iWidth, iHeight, pixels, 0, iWidth ));
		try {
      		MediaTracker tracker = new MediaTracker (this);
      		tracker.addImage (img,0);
	      	tracker.waitForID (0);
    	} catch (InterruptedException e) {  }

    	BufferedImage buffered_image = new BufferedImage (iWidth, iHeight, BufferedImage.TYPE_BYTE_GRAY);
    	Graphics2D g3 = buffered_image.createGraphics ();
    	g3.drawImage (img,0 ,0, null);		
/*
    	BufferedImage buffered_image1 = new BufferedImage (iWidth, iHeight, BufferedImage.TYPE_BYTE_GRAY);
    	Graphics2D g2 = buffered_image1.createGraphics ();
    	g2.drawImage (img,0 ,0, null);*/		

		
		Skew skewCorrection= new Skew();
		double angle;
		angle= skewCorrection.get_skew(buffered_image);
		buffered_image= skewCorrection.rotate(buffered_image, angle);
		//skewCorrection.rotate(pixels, iWidth, iHeight, angle);

		
		for(int ctr2= 0; ctr2< iHeight; ctr2++) {
			for(int ctr1= 0; ctr1< iWidth; ctr1++) 		
				System.out.print(imageMatrix[ctr2][ctr1]);
			System.out.print("\n");
		}
		
		Zernike zernike= new Zernike();
		
		Vector v= zernike.zer_mmts(15, buffered_image );
		System.out.println("Final Moments");
		Enumeration vEnum= v.elements();
		while(vEnum.hasMoreElements()) 
			System.out.println(vEnum.nextElement());
		
		
		moments= new double[v.size()];
		
		for (int i=0; i < v.size(); i++) 
      		moments[i] = ((DCOMPLEX)v.get(i)).getModulus();		

		/*int count= 0;
		
		while(vEnum.hasMoreElements()) 
			moments[count++]= Double.parseDouble( ( vEnum.nextElement()) );
		
	*/
				
		//repaint();
		
	}
/*
	public void update() {
		repaint();
	}

	public void paint(Graphics g) {
		g.drawImage(img, 20, 50 , null);
		for(int i= 0; i< iWidth; i++)
			for(int j= 0; j< iHeight; j++)
	        		g.drawString(" " + imageMatrix[i][j], 90+ j* 8, 120+ i* 8);
		
	}
*/
}
