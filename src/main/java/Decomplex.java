package Project;

import java.lang.Math;

public class DCOMPLEX implements ImgToolInterface {
  	double re, im,mo,theta; //real part, imagniary part, modulus
  	public DCOMPLEX() {
    	re=0.0;
    	im=0.0;
    	mo=-1.0;
    	theta=0.0;
  	}

  	public DCOMPLEX(DCOMPLEX dd) {
	    re=dd.re;
    	im=dd.im;
    	mo=dd.mo;
    	theta=dd.theta;
  	}	
  	public DCOMPLEX(double real,double imaginary) {
    	re=real;
    	im=imaginary;
	    mo=-1.0;
    	theta=0.0;
  	}

 	public double getModulus(){
    	if(mo>=0)
      		return mo;
    	mo=re*re+im*im;
    	mo=Math.sqrt(mo);
    	theta=Math.atan2(re,im); /*theta is (-pi,pi)*/
    	return mo;
  	}
  /**
   * same thing as getModulus()
   */
 	public double magnitude() {
    	if(mo>=0)
      		return mo;
    	mo=re*re+im*im;
    	mo=Math.sqrt(mo);
    	theta=phaseAngle(); /*atan2 is (-pi,pi)*/
    	return mo;
 	}
 /**
  * compute and return the phaseAngle
  */
 	public double phaseAngle() {
  		if(re==0.0D && im==0.0D)
   			return 0.0D;
  		else
    		return Math.atan(im/re); /*atan2 is (-pi,pi)*/
 	}
  /**
   * Copy the values of this complex to complex B
   */
 	public DCOMPLEX clone2(){
    	DCOMPLEX B=new DCOMPLEX();
    	B.mo=getModulus();
	    B.re=re;
    	B.im=im;
    	B.theta=theta;
    	return B;
  	}

  	public static double distance(Object x,Object y) {
	    double value=0.0;
    	DCOMPLEX xx=(DCOMPLEX)x;
    	DCOMPLEX yy=(DCOMPLEX)y;
	    value=Math.sqrt((xx.re-yy.re)*(xx.re-yy.re)+(xx.im-yy.im)*(xx.im-yy.im));
    	return value;
  	}
  	public static double distance2(Object x,Object y) {
	    double value=0.0;
    	DCOMPLEX xx=(DCOMPLEX)x;
	    DCOMPLEX yy=(DCOMPLEX)y;
//    value=xx.re*xx.re+xx.im*xx.im;
//    value=Math.sqrt(value);
//    double v2=yy.re*yy.re+yy.im*yy.im;
//    v2=Math.sqrt(v2);
//    value=Math.abs(value-v2);
    	value=Math.abs(xx.mo-yy.mo);
//      value=value/(xx.mo+yy.mo);
    	return value;
  	}
  	public String toString() {
//    if(this==null) return "";
    	String str=" ";
    	if(zernike_mode==ZERNIKE_DISTANCE_COMPLEX) {
     		str=String.valueOf(re);
     		str+=",";
     		str+=String.valueOf(im);
    	}
    	else
     		str+=String.valueOf(getModulus());

    	return str;
  	}
/**
 * Following Methods are for FFT class
 */
      /** A static class method to multiply complex numbers */
    public static DCOMPLEX cMult(DCOMPLEX a, DCOMPLEX b) {
        return new DCOMPLEX(a.re*b.re - a.im*b.im, a.re*b.im + a.im*b.re);
    }

    /** An instance method to multiply complex numbers */
    public  DCOMPLEX cMult(DCOMPLEX a) {
        return new DCOMPLEX(re*a.re - im*a.im, re*a.im + im*a.re);
    }
    /** A static class method to add complex numbers */
    public static DCOMPLEX cSum(DCOMPLEX a, DCOMPLEX b) {
        return new DCOMPLEX(a.re+b.re,a.im+b.im);
    }
    /** A static class method to substract complex numbers */
    public static DCOMPLEX cDif(DCOMPLEX a, DCOMPLEX b) {
        return new DCOMPLEX(a.re-b.re,a.im-b.im);
    }
      /** A class method to substract complex numbers */
    public double cNorm() {
        return (re*re+im*im);
    }

    /** A static class method to divide complex numbers */
    public static DCOMPLEX cDiv(DCOMPLEX a, DCOMPLEX b) {
        double d=b.cNorm();
        double real=a.re*b.re+a.im*b.im;
        real=real/d;
        double img=b.re*a.im-a.re*b.im;
        img=img/d;
        return new DCOMPLEX(real,img);
    }
    /** A static class method to exp complex numbers */
    public static DCOMPLEX cExp(DCOMPLEX dc) {
        DCOMPLEX a=new DCOMPLEX(Math.exp(dc.re),0.0D);
        DCOMPLEX b=new DCOMPLEX(Math.cos(dc.im),Math.sin(dc.im));
        return cMult(a,b);
    }

};