/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import plegatfem.Node;

/**
 *
 * @author jmb2
 */
public class MyPaint implements Paint {

    private ColorBandConvertor cvb;
    private Node nd1,nd2,nd3;
    private double u1,v1,u2,v2,u3,v3;
    
    public MyPaint(ColorBandConvertor cvb) {
        this.cvb = cvb;
    }

    

    public void setNodes(Node nd1,Node nd2,Node nd3) {
        this.nd1 = nd1;
        this.nd2 = nd2;
        this.nd3 = nd3;
    }

    public void setDisp(double u1,double v1,double u2,double v2,double u3,double v3) {
        this.u1 = u1;
        this.u2 = u2;
        this.u3 = u3;
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        
    }

    
    
    
    
    
    
    
    
    @Override
    public PaintContext createContext(ColorModel cm, Rectangle deviceBounds, Rectangle2D userBounds, AffineTransform xform, RenderingHints hints) {
        
        MyPaintContext mpc= new MyPaintContext(cvb, cm, deviceBounds);
        mpc.setNodes(nd1, nd2, nd3);
        mpc.setDisp(u1, v1, u2, v2, u3, v3);
        
        return mpc;
    }
    

    @Override
    public int getTransparency() {
        return Paint.OPAQUE;
    }
    
}
