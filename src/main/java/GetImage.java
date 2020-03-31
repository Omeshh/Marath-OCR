package Project;


//import javax.swing.*;
import java.awt.*;
import java.applet.Applet;
import java.awt.Image;

/**
 * This class is used because the binarize class already Xtended Frame.
 * To get the image file into the image class we needed GetImage class to be Xtended by applets
 */


public class GetImage extends Applet {
	public Image img;

	/** 
   	*Copies the content of jpeg into the Image Class
   	*@param dirPath1 the address of the file
   	*@param fileName name of the JPG file   	
   	*/
		
	public GetImage(String dirPath1, String fileName ) {
		
		img= Toolkit.getDefaultToolkit().getImage(dirPath1+"\\"+ fileName);
	}

	/** 
   	*@return the content of variable img   	
   	*/

	public Image returnImage() {
		return img;
	}
}

