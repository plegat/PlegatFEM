/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plegatfem;

/**
 *
 * @author jmb2
 */
public class Node {
    
    private double x,y;
    private String id;

    public Node(String id,double x, double y) {
        this.x = x;
        this.y = y;
        this.id = id;
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
    
    public void setXY(double x,double y) {
        this.x = x;
        this.y = y;
    }
    
    
    
    
}
