/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import plegatfem.Node;

/**
 *
 * @author JMB
 */
public class DispTria {
    
    private Node nd1,nd2,nd3;
    private double u1,v1,u2,v2,u3,v3;
    private double area;

    public DispTria(Node nd1, Node nd2, Node nd3) {
        this.nd1 = nd1;
        this.nd2 = nd2;
        this.nd3 = nd3;
        
        this.calculateArea();
    }

    public Node getNd1() {
        return nd1;
    }

    public void setNd1(Node nd1) {
        this.nd1 = nd1;
    }

    public Node getNd2() {
        return nd2;
    }

    public void setNd2(Node nd2) {
        this.nd2 = nd2;
    }

    public Node getNd3() {
        return nd3;
    }

    public void setNd3(Node nd3) {
        this.nd3 = nd3;
    }
    
    public final void calculateArea() {
        
        this.area=0.5*((nd2.getX()-nd1.getX())*(nd3.getY()-nd1.getY())-(nd3.getX()-nd1.getX())*(nd2.getY()-nd1.getY()));
        
    }
    
    public double getN1(double x,double y) {
        
        double y23=nd2.getY()-nd3.getY();
        double x32=nd3.getX()-nd2.getX();
        double x3y2=nd3.getX()*nd2.getY();
        double x2y3=nd2.getX()*nd3.getY();
        
        return 1/(2*this.area)*(y23*x+x32*y-x3y2+x2y3);
    }
    
    public double getN2(double x,double y) {
        
        double y31=nd3.getY()-nd1.getY();
        double x13=nd1.getX()-nd3.getX();
        double x3y1=nd3.getX()*nd1.getY();
        double x1y3=nd1.getX()*nd3.getY();
        
        return 1/(2*this.area)*(y31*x+x13*y+x3y1-x1y3);
    }
    
    public double getN3(double x,double y) {
        
        double y12=nd1.getY()-nd2.getY();
        double x21=nd2.getX()-nd1.getX();
        double x2y1=nd2.getX()*nd1.getY();
        double x1y2=nd1.getX()*nd2.getY();
        
        return 1/(2*this.area)*(y12*x+x21*y-x2y1+x1y2);
    }
    
    public void setDispAtNodes(double u1,double v1,double u2,double v2,double u3,double v3) {
        this.u1=u1;
        this.u2=u2;
        this.u3=u3;
        this.v1=v1;
        this.v2=v2;
        this.v3=v3;
        
    }
    
    public double[] getDisp(double x,double y) {
        
        double n1=this.getN1(x, y);
        double n2=this.getN2(x, y);
        double n3=this.getN3(x, y);

        double[] disp=new double[2];
        
        disp[0]=n1*this.u1+n2*this.u2+n3*this.u3;
        disp[1]=n1*this.v1+n2*this.v2+n3*this.v3;
        
        return disp;
    }
    
    
}
