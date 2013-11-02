/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geom;

/**
 *
 * @author jmb2
 */
public class GeoPoint {
    
    private String id;
    private double x,y;

    public GeoPoint(String id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    
    
    
}
