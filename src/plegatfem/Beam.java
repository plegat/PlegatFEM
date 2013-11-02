/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plegatfem;

/**
 *
 * @author jmb2
 */
public class Beam {

    Node node1, node2;
    double section;
    double inertia;
    Material mat;

    public Beam(Node node1, Node node2, double section, double inertia, Material mat) {
        this.node1 = node1;
        this.node2 = node2;
        this.section = section;
        this.inertia = inertia;
        this.mat = mat;
    }

    public Node[] getNodes() {
        Node[] nodes = new Node[2];
        nodes[0] = this.node1;
        nodes[1] = this.node2;
        return nodes;
    }

    public Node getNode1() {
        return this.node1;
    }

    public Node getNode2() {
        return this.node2;
    }

    public Material getMat() {
        return mat;
    }

    public void setMat(Material mat) {
        this.mat = mat;
    }

    public double getSection() {
        return section;
    }

    public void setSection(double section) {
        this.section = section;
    }

    public double getInertia() {
        return inertia;
    }

    public void setInertia(double inertia) {
        this.inertia = inertia;
    }

    public Matrix getStiffnessMatrix() {

        Matrix stiff = new Matrix(6, 6);

        double l = Math.sqrt(Math.pow(this.node2.getX() - this.node1.getX(), 2) + Math.pow(this.node2.getY() - this.node1.getY(), 2));
        double ea_l = this.mat.getYoung() * this.section / l;
        double ei_l = this.mat.getYoung() * this.inertia / l;
        double ei_l2 = ei_l / l;
        double ei_l3 = ei_l2 / l;

        stiff.setVal(0, 0, ea_l);
        stiff.setVal(0, 3, -ea_l);
        stiff.setVal(1, 1, 12 * ei_l3);
        stiff.setVal(1, 2, -6 * ei_l2);
        stiff.setVal(1, 4, -12 * ei_l3);
        stiff.setVal(1, 5, -6 * ei_l2);
        stiff.setVal(2, 2, 4 * ei_l);
        stiff.setVal(2, 4, 6 * ei_l2);
        stiff.setVal(2, 5, 2 * ei_l);
        stiff.setVal(3, 3, ea_l);
        stiff.setVal(4, 4, 12 * ei_l3);
        stiff.setVal(4, 5, 6 * ei_l2);
        stiff.setVal(5, 5, 4 * ei_l);

        stiff.setVal(3, 0, -ea_l);
        stiff.setVal(2, 1, -6 * ei_l2);
        stiff.setVal(4, 1, -12 * ei_l3);
        stiff.setVal(5, 1, -6 * ei_l2);
        stiff.setVal(4, 2, 6 * ei_l2);
        stiff.setVal(5, 2, 2 * ei_l);
        stiff.setVal(5, 4, 6 * ei_l2);

        double c=(this.node2.getX()-this.node1.getX())/l;
        double s=(this.node2.getY()-this.node1.getY())/l;

        Matrix t=new Matrix(6,6);
        t.setVal(0,0,c);
        t.setVal(1,1,c);
        t.setVal(2,2,1);
        t.setVal(3,3,c);
        t.setVal(4,4,c);
        t.setVal(5,5,1);

        t.setVal(0,1,s);
        t.setVal(1,0,-s);
        t.setVal(3,4,s);
        t.setVal(4,3,-s);

        Matrix tt=t.getTransposed();

        Matrix stiffFinal=new Matrix(1,1);

        try {
                stiffFinal=tt.product(stiff.product(t));
        } catch (Exception ex) {
                ex.printStackTrace();
        }




        return stiffFinal;

    }
}
