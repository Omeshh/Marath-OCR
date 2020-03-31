package Project;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.JFrame;

public class Sample {
	public double input[][][];
	public double base[];
    public final int MAX_SAMPLE = 5;
    public final int MAX_DIGIT  = 34;
    public final String dirname1= new String("C:\\Users\\omesh.pa\\Desktop\\work\\projects\\OCR\\learn-data-set\\");
	public final String filename[]= {"BA","BHA", "CHA", "CHAA", "DA","DAA","DDHA","DHA","FA","GA","GHA","HA","JA","KA","KHA","LA","LLAA","MA","NA","NAAA","NYA","PA","RA","SA","SHA","SHAK","SHYA","TA","THA","TTA","TTHA","VA","YA","ZHA"};
	public final String marathi_char[]= {"\u092C","\u092D", "\u091A", "\u091B", "\u0926","\u0921","\u0922","\u0927","\u092B","\u0917","\u0918","\u0939","\u091C","\u0915","\u0916","\u0932","\u0933","\u092E","\u0928","\u0923","\0973","\u092A","\u0930","\u0938","\u0936","\u0972","\u0974","\u0924","\u0920","\u091F","\u0925","\u0935","\u092F","\u091D"};


  	public Sample() throws IOException {
    	input = new double[MAX_DIGIT][MAX_SAMPLE][70];
    	//*---------Remove this for storing in file input.tmp---------------------
    	DataInputStream in = null;
    	
		in = new DataInputStream(new BufferedInputStream(new FileInputStream(dirname1+"\\input.tmp")));
	
		for(int filePtr= 0; filePtr< MAX_SAMPLE; filePtr++)			
			for(int dirPtr= 0; dirPtr< MAX_DIGIT; dirPtr++)				
				for(int sple= 0; sple< 70; sple++) 
					input[dirPtr][filePtr][sple]=in.readDouble();
		in.close();
		//----------------------------------------------
    	openDataFile();   //*Remove this from comment for storing in file input.tmp 
  	}
  	void openDataFile() throws IOException // return true on success and false otherwise
  	{
  		final JFrame w = new JFrame("Owner Window");

  		TrainingProgress t= new TrainingProgress(w, "Initializing", "Initialization in progress");
  				
	
  		base= new double[70];
		for(int filePtr= 0; filePtr< MAX_SAMPLE; filePtr++ ) {
			for(int dirPtr= 0; dirPtr< MAX_SAMPLE; dirPtr++) {
					
				String fileName= new String((int)(filePtr+1) + ".jpg");
				String dirName1= new String(dirname1+filename[dirPtr]);
				
				String dirName= new String(dirName1);
				Binarize bin= new Binarize(fileName,dirName1,dirName);
			
				
				int ctr= 0;
				System.out.println("Input 2 Neural Network ");
				for( ctr= 0; ctr< bin.moments.length; ctr++) {
					if(base[ctr]< bin.moments[ctr])
						base[ctr]= bin.moments[ctr];
					input[dirPtr][filePtr][ctr] = bin.moments[ctr]; 
				}	
				if( (dirPtr)% 2 == 0)
					t.updateProgress();
					
			}	
			
		}
		//*DataOutputStream out = null; //*
		//*out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dirname1+"\\input.tmp"))); //*
			
		for(int filePtr= 0; filePtr< MAX_SAMPLE; filePtr++) {
			System.out.println("Font "+ (filePtr+1) );
			for(int dirPtr= 0; dirPtr< MAX_DIGIT; dirPtr++) {
				System.out.println("Character "+ (dirPtr + 1) );
				for(int sple= 0; sple< 70; sple++) {
					input[dirPtr][filePtr][sple] = (input[dirPtr][filePtr][sple]/1000/*base[sple]*/ ) ;
					System.out.print(input[dirPtr][filePtr][sple]+ " ");
		//*out.writeDouble(input[dirPtr][filePtr][sple]); //*				
				}
			}
		}
		//*out.close();
		t.close();
		t= null;
  	}  	
}
