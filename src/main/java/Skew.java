package Project;


import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;


import java.awt.event.*;
import javax.swing.*;

import java.awt.geom.AffineTransform;
import java.awt.font.TextLayout;

import java.awt.image.*;
import com.sun.image.codec.jpeg.*;

public class Skew {

	public final double M_PI=3.14159265358979323846;

	public final int m_sample_skip=1;

	public Skew() {
	}


	public double get_skew(BufferedImage image) {
		double var_test;
		double var_opt     = 0.0;
		double step        = 10.0;
		double angle       = 0.0;
		double opt         = 0.0;
		int    i;
		int    j;
		int    iter        = 0;
		int    low         = -25;
		int    high        = 25;

		for (i = 0; i < 2; ++i) {
			angle = opt + low * step;
			for (j = low; j <= high; ++j) {
				var_test = histogram( image, angle/10);
				if (var_test > var_opt) {
					var_opt = var_test;
					opt    = angle;
				} else {
          /* do nothing - I'd like to short-circuit, but it doesn't work :( */
				}
				angle += step;
			}
			step /= 10.0;
			low  = -10;
			high = 10;
		}
		
		double m_skew = opt / 10.0;
		return m_skew;
	}

	public double histogram( BufferedImage raster, double angle) {
		int    i;
		double sum;
		double mean;
		double angle_diff  = Math.tan(angle / (180.0 / M_PI));
		int    diff_y      = -(int)( raster.getWidth() * angle_diff);
		int    min_y       = Math.max( 0, diff_y);
		int    max_y       = Math.min( (int)( raster.getHeight())
                            	, raster.getHeight() + diff_y);
		int    num_rows;
		int    dx;
		int    dy;


//    if (raster.getHeight() > m_max_rows) {
//      delete []m_rows;
		int m_max_rows = raster.getHeight();
		int[] m_rows = new int[m_max_rows+1];

//    }
		num_rows = (max_y - min_y) / m_sample_skip + 1;

		if (angle < 0) {
			dy = -1;
		} else {
			dy = +1;
		}
		if ((-0.05 < angle) && (angle < 0.05)) {
			dx = (int)(raster.getWidth());
		} else {
			dx =(int) (dy / (Math.tan( angle / (180.0 / M_PI))));
		}

		for (i = 0; i < num_rows; ++i) {
			m_rows[ i] = cast_ray( raster, min_y + i * m_sample_skip, dx, dy);
		}

		sum = 0.0;
		for (i = 0; i < num_rows; ++i) {
			sum += m_rows[ i];
		}
		mean = sum / num_rows;
//    	if(sum>0) System.out.print("sum="+sum+".\n");
		sum = 0.0;
		for (i = 0; i < num_rows; ++i) {
			sum += ( m_rows[ i] - mean)*( m_rows[ i] - mean);
		}

		return sum / num_rows;
	}
	public int cast_ray( BufferedImage inband, int row, int dx, int dy) {
		int  bits  = 0;
		int     start = 0;
		int     end   = 0;
		int     width = inband.getWidth();
		int     height = inband.getHeight();
		int       r     = row;

		int[] src_1d=new int[width*height+1];

		Raster rt=inband.getData();
		rt.getSamples(0,0,width,height,0,src_1d);
		src_1d[width*height]=0;

		start=dx<0?width:0;

		while (start <= width && start>=0 && r<height && r>0) {
			end = start + dx;

			if (end > width) {
				end = width;
			}
			else if(end<0)
				end=0;
			
			bits += bit_count(src_1d, start+r*width,end+r*width);
			
			start = end;
			r += dy;
		}
//    if(bits>0) System.out.print("bit_count="+bits+"\n");

		return bits;
	}
	public int bit_count(int[] src_1d,int start,int end) {
		int count=0;
		int i=0;
//    try{
		for(i=start;i<end;i++) 
			count+=src_1d[i]>0?1:0;
		
//    }catch(Exception e){System.out.print("i="+i+"\n");
//    }

			return count;
	}

	public BufferedImage rotate(BufferedImage src,double angle){
		int ww=src.getWidth();
		int hh=src.getHeight();
		BufferedImage dest = new BufferedImage(ww,hh,BufferedImage.TYPE_BYTE_GRAY);
		int[] src_1d=new int[ww*hh];

		Raster rt=src.getData();
		rt.getSamples(0,0,ww,hh,0,src_1d);

		int[] dest_1d=rotate(src_1d,ww,hh,angle);

		WritableRaster raster=dest.getRaster();
		raster.setSamples(0,0,ww,hh,0,dest_1d);

		return dest;
	}

  /**
   *rotate applies a rotation operator to an image
   *@param src_1d The source image as a pixel array
   *@param width width of the destination image in pixels
   *@param height height of the destination image in pixels
   *@param angle The angle to rotate the image by
   *
   *@return A pixel array containing the rotated image
   */


  //Bob's rotation algorithm..
  /*a) assume the image is grey level (hence RR=GG=BB)
    b) use value &0x000000ff to get the BB value
    c) apply the operation (eg rotate).
    d) return the result
    */

	public int [] rotate(int [] src_1d, int width, int height, double angle) {
		int d_w;
		int d_h;

		int[] dest_1d;
		d_w = width;
		d_h = height;
		dest_1d = new int[d_w * d_h];

		double dx_x = rot_x (-angle, 1.0, 0.0);
		double dx_y = rot_y (-angle, 1.0, 0.0);
		double dy_x = rot_x (-angle, 0.0, 1.0);
		double dy_y = rot_y (-angle, 0.0, 1.0);

		double x0 = rot_x (-angle, -d_w/2.0, -d_h/2.0) + d_w/2.0;
		double y0 = rot_y (-angle, -d_w/2.0, -d_h/2.0) + d_h/2.0;

		double x1 = x0;
		double y1 = y0;
		for (int y = 0; y<d_h; y++) {
			double x2 = x1;
			double y2 = y1;
			for (int x = 0; x<d_w; x++) {
				int pixel = src_color(src_1d,d_w,d_h,x2,y2);
				dest_1d [y*d_w + x] = pixel;
				x2 += dx_x;
				y2 += dx_y;
			}
			x1 += dy_x;
			y1 += dy_y;
		}
		return dest_1d;
	}
  /**
   * Rotates the x position of a point.
   */
	private double rot_x (double angle, double x, double y) {
		double cos = Math.cos(angle/180.0*Math.PI);
		double sin = Math.sin(angle/180.0*Math.PI);
		return (x * cos + y * (-sin));
	}
  /**
   * Rotates the y position of a point.
   */
	private double rot_y (double angle, double x, double y) {
		double cos = Math.cos(angle/180.0*Math.PI);
		double sin = Math.sin(angle/180.0*Math.PI);
		return (x * sin + y * cos);
	}
  /**
   * Return the value of the x,y point from the image
   * represented by the input array.
   */
	private int src_color (int [] src_1d,int d_w,int d_h, double x, double y) {
		int ix = (int) Math.floor (x);
		int iy = (int) Math.floor (y);
		if ((ix >= d_w) || (ix < 0) ||
				(iy >= d_h) || (iy < 0)) {
			return 0;
		} else {
			return src_1d[iy*d_w + ix];
		}
	}
}