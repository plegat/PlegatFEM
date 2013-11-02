/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plegatfem;

/**
 *
 * @author jmb2
 */
public class Bar {
    
    Node node1,node2;
    double section;
    Material mat;

    public Bar(Node node1, Node node2, double section, Material mat) {
        this.node1 = node1;
        this.node2 = node2;
        this.section = section;
        this.mat = mat;
    }
    
    public Node[] getNodes() {
        Node[] nodes=new Node[2];
        nodes[0]=this.node1;
        nodes[1]=this.node2;
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
    
    public Matrix getStiffnessMatrix() {
        
        Matrix stiff=new Matrix(6, 6);
        
        double l=Math.sqrt(Math.pow(this.node2.getX()-this.node1.getX(),2)+Math.pow(this.node2.getY()-this.node1.getY(),2));
        double ea_l=this.mat.getYoung()*this.section/l;
        
        stiff.setVal(0, 0, ea_l);
        stiff.setVal(3, 3, ea_l);
        stiff.setVal(0, 3, -ea_l);
        stiff.setVal(3, 0, -ea_l);
        
        
        return stiff;
        
    }
    
}
