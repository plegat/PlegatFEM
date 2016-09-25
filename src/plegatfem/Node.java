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

    private double x, y, z;
    private String id;

    public Node(String id, double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = id;
    }

    public Node(String id, double x, double y) {
        this(id, x, y, 0.);
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

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setXYZ(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setXY(double x, double y) {
        this.setXYZ(x, y, 0.);
    }

}
