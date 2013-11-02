/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Color;

/**
 *
 * @author JMB
 */
public class ColorBandConvertor {
    
    private double min,max;
    private int nbIndex;

    public ColorBandConvertor(double min, double max,int nbIndex) {
        this.min = min;
        this.max = max;
        this.nbIndex=nbIndex;
        
        System.out.println("cvb: "+this.min+"/"+this.max);
    }

    public ColorBandConvertor(double min, double max) {
        this(min,max,10);
    }
    
    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public int getNbIndex() {
        return nbIndex;
    }

    public void setNbIndex(int nbIndex) {
        this.nbIndex = nbIndex;
    }
    
    public Color getColor(double value) {
        
        int red,green,blue;
        
        double x=(value-this.min)/(this.max-this.min);
        
        if (x<0) {
            //System.out.println("value="+value);
            red=255;
            green=255;
            blue=255;
        } else if (x<=0.25) {
            red=0;
            green=(int)Math.round(255*Math.max(0,4.*x));
            blue=255;
        } else if (x<=0.5) {
            red=0;
            green=255;
            blue=(int)Math.round(255*Math.max(0,2-4.*x));
        } else if (x<=0.75) {
            red=(int)Math.round(255*Math.max(0,-2+4.*x));
            green=255;
            blue=0;
        } else if (x<=1) {
            red=255;
            green=(int)Math.round(255*Math.max(0,4-4.*x));
            blue=0;
        } else {
            red=0;
            green=0;
            blue=0;
        }
        //System.out.println("R"+red+"G"+green+"B"+blue);
        
        return new Color(red, green, blue);
        
    }
    
    public Color getIndexedColor(double value) {
    
        double step=(this.max-this.min)/this.nbIndex;
        double indexValue=Math.floor(value/step)*step;
        
        if (Math.abs(value-indexValue)<0.2) {
            return Color.gray;
        } else {
            return this.getColor(indexValue);
        }
        
        
    }
    
}
