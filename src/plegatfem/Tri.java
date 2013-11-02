/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plegatfem;

/**
 *
 * @author jmb2
 */
public class Tri {

    Node node1, node2, node3;
    double thickness;
    Material mat;
    double model;
    double area;

    public Tri(Node node1, Node node2, Node node3, double thickness, Material mat, double model) {
        this.node1 = node1;
        this.node2 = node2;
        this.node3 = node3;
        this.thickness = thickness;
        this.mat = mat;
        this.model = model; //0: contraintes planes, 1: déformations planes

        this.area = 0.5 * ((node2.getX() - node1.getX()) * (node3.getY() - node1.getY()) - (node3.getX() - node1.getX()) * (node2.getY() - node1.getY()));
        
    }

    public Node[] getNodes() {
        Node[] nodes = new Node[3];
        nodes[0] = this.node1;
        nodes[1] = this.node2;
        nodes[2] = this.node3;
        return nodes;
    }

    public Node getNode1() {
        return this.node1;
    }

    public Node getNode2() {
        return this.node2;
    }

    public Node getNode3() {
        return this.node3;
    }

    public Material getMat() {
        return mat;
    }

    public void setMat(Material mat) {
        this.mat = mat;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public double getModel() {
        return model;
    }

    public void setModel(double model) {
        this.model = model;
    }

    public Matrix getStiffnessMatrix() {

        Matrix stiff = new Matrix(9, 9);

        double g = this.mat.getYoung() / (2 * (1 + this.mat.getNu()));
        double h1 = 2 * g * (1 - this.model * this.mat.getNu()) / (1 - (1 + this.model) * this.mat.getNu());
        double h2 = this.mat.getNu() * h1 / (1 - this.model * this.mat.getNu());

        double coef = this.thickness / (4 * this.area);

        double x13 = node1.getX() - node3.getX();
        double x21 = node2.getX() - node1.getX();
        double x32 = node3.getX() - node2.getX();

        double y12 = node1.getY() - node2.getY();
        double y23 = node2.getY() - node3.getY();
        double y31 = node3.getY() - node1.getY();


        stiff.setVal(0, 0, h1 * y23 * y23 + g * x32 * x32);
        stiff.setVal(0, 1, h2 * x32 * y23 + g * x32 * y23);
        stiff.setVal(0, 3, h1 * y31 * y23 + g * x32 * x13);
        stiff.setVal(0, 4, h2 * x13 * y23 + g * x32 * y31);
        stiff.setVal(0, 6, h1 * y12 * y23 + g * x21 * x32);
        stiff.setVal(0, 7, h2 * x21 * y23 + g * x32 * y12);
        
        stiff.setVal(1, 1, h1 * x32 * x32 + g * y23 * y23);
        stiff.setVal(1, 3, h2 * x32 * y31 + g * x13 * y23);
        stiff.setVal(1, 4, h1 * x32 * x13 + g * y31 * y23);
        stiff.setVal(1, 6, h2 * x32 * y12 + g * x21 * y23);
        stiff.setVal(1, 7, h1 * x32 * x21 + g * y12 * y23);
        
        stiff.setVal(3, 3, h1 * y31 * y31 + g * x13 * x13);
        stiff.setVal(3, 4, h2 * x13 * y31 + g * x13 * y31);
        stiff.setVal(3, 6, h1 * y12 * y31 + g * x13 * x21);
        stiff.setVal(3, 7, h2 * x21 * y31 + g * x13 * y12);
        
        stiff.setVal(4, 4, h1 * x13 * x13 + g * y31 * y31);
        stiff.setVal(4, 6, h2 * x13 * y12 + g * x21 * y31);
        stiff.setVal(4, 7, h1 * x13 * x21 + g * y12 * y31);
        
        stiff.setVal(6, 6, h1 * y12 * y12 + g * x21 * x21);
        stiff.setVal(6, 7, h2 * x21 * y12 + g * x21 * y12);
        
        stiff.setVal(7, 7, h1 * x21 * x21 + g * y12 * y12);


        stiff.symetrize();
        stiff.mult(coef);


        return stiff;

    }

    public static void main(String[] args) {

        Material mat = new Material("alu", 70000, 0.33);
        Tri[] tris = new Tri[1000];
        for (int i = 0; i < 1000; i++) {

            tris[i] = new Tri(new Node("", 0, 0), new Node("", 2, 0), new Node("", 1, 1), 1, mat, 0);

        }

        System.out.println("tri #1000 matrix:");
        System.out.println(tris[999].getStiffnessMatrix().toString());

    }
}
