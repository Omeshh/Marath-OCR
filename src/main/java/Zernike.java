package Project;

import java.awt.*;
import java.util.*;
import java.awt.image.*;

/* Moments*/


public class Zernike implements ImgToolInterface {

	public Zernike() {
  	}

  	static double NumberFactorial(int n) {
    	int i;
    	double sum = 1.0;
    	if (n == 0) {
      		return 1;
    	}
    	for (i = 1; i <= n; i++) {
      		sum *= i;
    	}
    	return sum;
  	}

  	static void Convert_X_Y_TO_Polar(int x, int y, double centerx, double centery,
                                   double[] ruo, double[] sita) {
    	double relativex = x - centerx, relativey = y - centery;
    	ruo[0] = Math.sqrt( (relativex * relativex) + (relativey * relativey));
    	if (Math.abs(relativey) < 1e-6 && Math.abs(relativex) < 1e-6) {
      		sita[0] = 0.0;
    	}
    	else {
      		sita[0] = Math.atan2(relativey, relativex);
    	}
    	if (sita[0] < 0) {
      		sita[0] += 2 * PI;
    	}
  	}

  /*Compute the Zernike Moment;  the result is saved in a file;
   algothrim can be found in PAMI vol 12 No 5,1990 p254-266   */
//Radial polynomial  as describe in page 490
  	static double Radial_Poly(int n, int m, double ruo) {
    	int s;
    	int i;
    	int k;
    	double temp;
    	double result = 0.0;
    	k = Math.abs(m);
    	if ( (n - k) % 2 != 0) {
      		return 0.0;
    	}
    	for (s = 0; s <= (n - k) / 2; s++) {
      		temp = 1.0;
      		for (i = (n + k) / 2 - s + 1; i <= n - s; i++) {
        		temp *= i;
      		}
      		temp /= NumberFactorial(s);
      		temp /= NumberFactorial( (n - k) / 2 - s);
      		temp *= Math.pow(ruo, n - 2 * s);
      		if (s % 2 != 0) {
        		temp *= -1.0;
      		}
      		result += temp;
    	}
    	return result;
  	}

  ////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////
  //V is polynomial defined as equ(3) in paper
  	static void V_poly(int n, int m, double ruo, double sita, double[] realpart,
                     double[] impart) {
    	double temp = Radial_Poly(n, m, ruo);
    	realpart[0] = temp * Math.cos(m * sita);
    	impart[0] = temp * Math.sin(m * sita);
  	}

  ////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////
  //description in  PAMI vol 12.No.5
  	

  	static DCOMPLEX MomentZernike(int[] img, int m_X, int m_Y, double cx,
                                double cy, double longest, int p, int q) {
//    double cx,cy;
//    double longest;
    	double[] ruo = new double[1];
    	double[] sita = new double[1];
    	double[] realmoment = {0};
    	double[] immoment = {0};
    	int x, y, lx, rx, ly, ry;
//    lx=objectbox[0];
//    rx=objectbox[1];
//    ly=objectbox[2];
//    ry=objectbox[3];
    	double realpart = 0.0; //new double[1];
//    realpart[0]=0.0;
    	double impart = 0.0; //new double[1];
//    impart[0]=0.0;
//    cx=(rx+lx)/2.0;
//    cy=(ry+ly)/2.0;
//    longest=Math.sqrt((lx-cx)*(lx-cx)+(ly-cy)*(ly-cy));
    	for (y = 0; y < m_Y; y++) {
      		for (x = 0; x < m_X; x++) {
//        if(x<0 || x>m_X-1 || y<0 || y>m_Y-1) continue;
        		if (img[y * m_X + x] != BKGRND) {
          			Convert_X_Y_TO_Polar(x, y, cx, cy, ruo, sita);
          			ruo[0] = ruo[0] / longest;
          			if (ruo[0] <= 1) {
            			V_poly(p, q, ruo[0], -sita[0], realmoment, immoment);
            			realpart += realmoment[0];
            			impart += immoment[0];
          			}
        		}
        	;
      		} //end for
    	}
    	realpart = realpart * (p + 1) / PI;
    	impart = impart * (p + 1) / PI;
    	DCOMPLEX dc = new DCOMPLEX(realpart, impart);
	//    double moment=Math.sqrt((realpart*realpart)+(impart*impart));
	//    System.out.print("MomentZernike("+p+","+q+")"+moment+"\n");
//    int sum=0;
//    for(int i=0; i<m_X*m_Y; i++)
//      sum+=img[i]/FRONT;
//    if(sum==0)
//      return 0;
//    else
//      {
//        moment/=sum;
//        return moment;
//      }
//    return moment;
    	return dc;
  	}

  	public static Vector zer_mmts(int order, BufferedImage inband) {
//    Vector mmts=new Vector();
    	int n, m;
    	int i, j;
    	double i_0, j_0;
    	double i_scale, j_scale;
    	int isize, jsize;
	//    double x, y;
    	if (order <= 0) {
      		return null;
    	}
    	DCOMPLEX[][] A = new DCOMPLEX[order + 1][order + 1];

    	if (inband.getType() != BufferedImage.TYPE_BYTE_GRAY) {
      		System.out.print("zer_rec: Input pixel type must be unsigned byte.\n");
	
    	}
    	isize = inband.getWidth();
    	jsize = inband.getHeight();
    	int[] src_1d = new int[isize * jsize];

    	Raster rt = inband.getData();
    	rt.getSamples(0, 0, isize, jsize, 0, src_1d);

    	//get m00 and m01,m10 of the image
    	double[] moments = new double[3];

    	for (i = 0; i < 3; i++) {
      		moments[i] = 0;

    	}

    	for (int y = 0; y < jsize; y++) {
      		for (int x = 0; x < isize; x++) {
        		int value = src_1d[y * isize + x];
        		if (value != BKGRND) {
          			moments[0] += 1;
          			moments[1] += x;
          			moments[2] += y;
        		} //end if
      		}
    	}
    	if (moments[0] > 0) {
	      	moments[1] = moments[1] / moments[0];
      		moments[2] = moments[2] / moments[0];
    	}

    	System.out.print("The M00 is:" + moments[0] + "\n");
//    Point centroid=new Point((int)mt[1],(int)mt[2]);

    	int[] box = {0, 0, 0, 0};
    	double x0 = moments[1]; //(x0,y0)is the centroid of the image
    	double y0 = moments[2]; //notice centroid can be anywhere in the image
    	double radius = Math.sqrt(x0 * x0 + y0 * y0);
    	double r2 = Math.sqrt( (isize - 1 - x0) * (isize - 1 - x0) + y0 * y0);
    	radius = Math.max(radius, r2);
    	r2 = Math.sqrt(x0 * x0 + (jsize - 1 - y0) * (jsize - 1 - y0));
    	radius = Math.max(radius, r2);
    	r2 = Math.sqrt( (isize - 1 - x0) * (isize - 1 - x0) +
                   	(jsize - 1 - y0) * (jsize - 1 - y0));
    	radius = Math.max(radius, r2);
	
	    //adjusted by our handwriting application only
    //    radius+=4; //not appliable for small images

    	double r = radius * Math.sqrt(2) / 2;
    	box[0] = (int) (x0 - r);
    	box[1] = (int) (x0 + r);
    	box[2] = (int) (y0 - r);
    	box[3] = (int) (y0 + r);

    	System.out.print("centroid is (" + x0 + "," + y0 + "), side length is " +
                     2 * r + "\n");

    /**
     * Now to compute all moments lower than given order
     * Only consider order above 2
     * Notice |A(p,q)|=|A(p,-q)|
     */
    	for (n = 0; n <= order; n++) {
      		for (m = 0; m <= n; m++) {
        		if ( (n - Math.abs(m)) % 2 == 0) {
//                DCOMPLEX[] a=new DCOMPLEX[1];
//                a[0]=new DCOMPLEX();
          			DCOMPLEX a = MomentZernike(src_1d, isize, jsize, x0, y0, radius, n, m);
          			A[n][m] = a; //.getModulus();
          /**
           * kind of normalize, not restrict
           */
//                if(n>0)
//                  A[n][m].mo=a.getModulus()/A[0][0].getModulus();
//                mmts.add(a);
          			System.out.print("zer_mmts2: A(" + n + "," + m + ")=(" + a.re + "," +
                           a.im + ").\n");	
        		}
      		} //end for
    	}	

//    utility.save2DArray(A,"mmt1.csv");
    	DCOMPLEX[][] B;
//    if(zernike_mode==ZERNIKE_NORMALIZE_SIMPLE)
//      B=A;
//    else  B=normalize(A);
    /**
     * No normalize here
     */
    	B = A;
//    utility.save2DArray(B,"mmt2.csv");
    /**
     *  ZMI or NZMI Not used actually
     */
    	DCOMPLEX[][] ZMI = getZMI(B);
//    utility.save2DArray(ZMI,"mmt3.csv");
    	Vector ZMIS = new Vector();

    //we don't want to judge inside for loop
    	if (zernikeFea.fea_index != null && zernikeFea.fea_index[0] == null) {
      		int k = 0;
      //i from 2 to disgard A00 and A11
      		for (i = 0; i < ZMI.length; i++) {
        		for (j = 0; j < ZMI[i].length; j++) {
          			if (ZMI[i][j] != null) {
            			ZMIS.add(ZMI[i][j]);
            			if (k >= 2) {
	              			zernikeFea.fea_index[k - 2] = new Point(i, j);
    	        		}
        	    		k++;
          			}
        		}
      		}
    	}
    	else {
      		for (i = 0; i < ZMI.length; i++) {
        		for (j = 0; j < ZMI[i].length; j++) {
          			if (ZMI[i][j] != null) {
            			ZMIS.add(ZMI[i][j]);
          			}
        		}
      		}	
    	} //endif
//    //ignore A[0][0] and A[1][1]
    	ZMIS.removeElementAt(0);
    	ZMIS.removeElementAt(0);

    	return ZMIS;

	}

  
  /**
   * Normalize the absolute value of Zernike moments
   * According to the  reference [2]
   */
  	static DCOMPLEX[][] normalize(DCOMPLEX[][] A) {
    	int order = A.length - 1;
    	DCOMPLEX[][] B = new DCOMPLEX[order + 1][order + 1];

    	B[0][0] = A[0][0].clone2();
    	B[1][1] = A[1][1].clone2();
    	A[1][1].mo = 0.0;

    	for (int n = 2; n <= order; n++) {
      		for (int m = 0; m <= n; m++) {
        		if ( (n - Math.abs(m)) % 2 == 0) {
          			B[n][m] = A[n][m].clone2();
          			if (n != m && A[n - 2][m].getModulus() != 0) {
            			B[n][m].mo = A[n][m].getModulus() / A[n - 2][m].getModulus();
            //change real and image correspondingly
            			double mag = Math.sqrt(B[n][m].re * B[n][m].re +
                                   B[n][m].im * B[n][m].im);
            			B[n][m].re = B[n][m].re * B[n][m].mo / mag;
            			B[n][m].im = B[n][m].im * B[n][m].mo / mag;	
//                System.out.print("B["+n+"]"+"["+m+"]="+"A["+n+"]["+m+"]/A["+(n-2)+"]["+m+"]\n");
          			}
//               System.out.print("B["+n+"]"+"["+m+"]="+B[n][m]+"\n");
        		}
      		}
    	}
    	return B;

  	} //end of normalize

  /**
   * Compute the ZMIs(NZMIs) according to reference [2]
   */
  	static DCOMPLEX[][] getZMI(DCOMPLEX[][] A) {
    	int order = A.length - 1;
    	DCOMPLEX[][] B = new DCOMPLEX[order + 1][2 * order + 1];

    	for (int n = 0; n <= order; n++) {
      		for (int m = 0; m <= n; m++) {
        		if ( (n - Math.abs(m)) % 2 == 0) {
          			B[n][m] = A[n][m].clone2();	
        		}
      		}

      		if (n % 2 == 0 && n >= 4) {
        		double degree;
	        	for (int L = 4; L <= n; L += 2) {
    	      		int z = L / 2;
        	  		int p = 2 / L;
          			degree = p * A[n][L].theta - A[n][2].theta;

          			B[n][n + z] = new DCOMPLEX();

	          		B[n][n + z].mo = 2 * A[n][2].getModulus() *
    	          		Math.pow(A[n][L].getModulus(), p) * Math.cos(degree);
        	  		if (B[n][n + z].mo < 0) {
            			B[n][n + z].mo = -B[n][n + z].mo;
          			}
        		}

        		degree = A[n - 2][2].theta - A[n][2].theta;

	        	B[n][n + 1] = new DCOMPLEX();

    	    	B[n][n + 1].mo = 2 * A[n - 2][2].getModulus() * A[n][2].getModulus() * Math.cos(degree);
        		if (B[n][n + 1].mo < 0) {
          			B[n][n + 1].mo = -B[n][n + 1].mo;
        		}
      		} //endif

      		if (n % 2 == 1 && n >= 3) {
        		double degree;
        		for (int L = 3; L <= n; L += 2) {
	
          			double p = 1 / L;
          			degree = p * A[n][L].theta - A[n][1].theta;

          			B[n][n + L] = new DCOMPLEX();

          			B[n][n + L].mo = 2 * A[n][1].getModulus() *
              				Math.pow(A[n][L].getModulus(), p) * Math.cos(degree);
          			if (B[n][n + L].mo < 0) {
            			B[n][n + L].mo = -B[n][n + L].mo;
          			}
        		}

        		degree = A[n - 2][1].theta - A[n][1].theta;
        		B[n][n + 1] = new DCOMPLEX();
        		B[n][n + 1].mo = 2 * A[n - 2][1].getModulus() * A[n][1].getModulus() * Math.cos(degree);
        		if (B[n][n + 1].mo < 0) {
          			B[n][n + 1].mo = -B[n][n + 1].mo;

        		}
      		} //endif

    	} //end for

    	if (zernike_mode == ZERNIKE_DISTANCE_NZMI) {
      		return B;
    	}
    	else {
      		return A;
    	}

  	} //end of normalize

}
/*P:zernike*
 ________________________________________________________________
                zernike
 ________________________________________________________________
 Name:		zernike - zernike moment image of a gray scale
                or binary image
 Syntax:		zernike [-n <n>] [-r] [-t <title>] <inimage> <outimage>
 Description:	The Zernike moment of order 'n' and repetition 'm' of
                an image f(x,y) is defined as follows:
                |          n+1                             *
                | A(n,m) = ---- Sum Sum f(x,y)[V(n,m, x,y)]
                |           pi   x   y
                |
                | where x^2+y^2 <= 1
                |
                The image V(n,m, x,y) is the Zernike basis images of
                order 'n' and repetition 'm'. These basis images are
                complex and orthogonal. The Zernike moments are
                essentially the projections of the input image onto
                these basis images.
                The original image can be reconstructed from the
                Zernike moments. The N-th order approximation is given
                by
                |  ^         N
                |  f(x,y) = Sum Sum A(n,m) V(n,m, x,y)
                |           n=0  m
                |
                The contribution or information content of the n-th
                order moments is
                |
                |  I(x,y, n) = Sum A(n,m) V(n,m, x,y)
                |              m
 Restrictions:   'inimage' must be single-band with pixel type unsigned byte.
 Options:	&-n n
                Use moment order 'n'. Default 0.
                &-r
                Reconstruct image from moments. Default: Compute the absolute
                value of I(x,y, n).
                &-t title
                Use 'title' for 'outimage'.
 See also:       zer_mom(3), zer_con(3), zer_rec(3), zer_pol(3)
 References:     &[1] 'A. Khotanzad and Y.H. Hong'
                "Invariant image recognition by Zernike Moments",
                IEEE trans. on Pattern Analysis and Machine Intelligence,
                vol.12, no.5, pp.489-487, May 1990.
                &[2] 'A. Khotanzad and Y.H. Hong'
                "Rotation invariant image recognition using features
                selected via a systematic method",
                Pattern Recognition, vol.23, no.10, pp.1089-1101, 1990.
                &[3] 'Thomas H. Reiss'
                "Recognizing Planar Objects Using Invariant Image Features",
                Lecture Notes in Computer Science, volume 676, pp. 17-20,
                Springer-Verlag, 1993.
 Return value:   0 : OK
 ________________________________________________________________
 */
/**
 *  The experiments results:
     *     1. n must be less than 14, or noise will appear in the reconstructed image
 *     2. when reconstruct, only uses the real part
 *     3. the result will be better when original image is 64*64 and is binary
 *     4. the image size seems doesn't matter, while the larger the slower
 *     5. at most two char together in  one image will be recognized
 *     6. the content in the center of the image is more easily recognized
 */
