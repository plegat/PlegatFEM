/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geom;

import java.util.ArrayList;
import plegatfem.Material;
import plegatfem.Node;
import plegatfem.Tri;

/**
 *
 * @author jmb2
 */
public class GeoTri {

    private String id;
    private GeoPoint p1, p2, p3;
    private double thickness;
    private ArrayList<Node> nodes;
    private ArrayList<Tri> tris;
    private Material mat;
    private int nbElem;
    private ArrayList<Node> edgeNodes1,edgeNodes2,edgeNodes3;
    private Node node1,node2,node3;
    
    

    public GeoTri(String id, GeoPoint p1, GeoPoint p2, GeoPoint p3, double thickness, Material mat) {
        this.id = id;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.thickness = thickness;
        this.mat = mat;

        this.nodes = new ArrayList<Node>();
        this.tris = new ArrayList<Tri>();
        
        this.edgeNodes1=new ArrayList<Node>();
        this.edgeNodes2=new ArrayList<Node>();
        this.edgeNodes3=new ArrayList<Node>();
        
        this.init();
    }

    public final void init() {
        this.nodes.clear();
        this.tris.clear();
        
        this.edgeNodes1.clear();
        this.edgeNodes2.clear();
        this.edgeNodes3.clear();
        
        this.setNbElements(1);
    }

    public void setNbElementsBySize(int meshSize) {
        double l1 = Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
        double l2 = Math.sqrt(Math.pow(p3.getX() - p2.getX(), 2) + Math.pow(p3.getY() - p2.getY(), 2));
        double l3 = Math.sqrt(Math.pow(p1.getX() - p3.getX(), 2) + Math.pow(p1.getY() - p3.getY(), 2));

        this.nbElem = (int) Math.round(Math.min(l1, Math.min(l2,l3)) / meshSize) + 1;
        
    }
    
    public void setNbElements(int side) {
        this.nbElem = side;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GeoPoint getP1() {
        return p1;
    }

    public void setP1(GeoPoint p1) {
        this.p1 = p1;
    }

    public GeoPoint getP2() {
        return p2;
    }

    public void setP2(GeoPoint p2) {
        this.p2 = p2;
    }

    public GeoPoint getP3() {
        return p3;
    }

    public void setP3(GeoPoint p3) {
        this.p3 = p3;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public Material getMat() {
        return mat;
    }

    public void setMat(Material mat) {
        this.mat = mat;
    }

    public ArrayList<Node> getEdgeNodes1() {
        return edgeNodes1;
    }

    public ArrayList<Node> getEdgeNodes2() {
        return edgeNodes2;
    }

    public ArrayList<Node> getEdgeNodes3() {
        return edgeNodes3;
    }

    public Node getNode1() {
        return node1;
    }

    public Node getNode2() {
        return node2;
    }

    public Node getNode3() {
        return node3;
    }

    
    public void mesh(int nodeId, int elementId) {

        double p1x = p1.getX();
        double p2x = p2.getX();
        double p3x = p3.getX();
        double p1y = p1.getY();
        double p2y = p2.getY();
        double p3y = p3.getY();

        int[] nodeStart = new int[this.nbElem + 1];

        int nodeIdtemp = nodeId;

        for (int j = 0; j < this.nbElem; j++) {

            nodeStart[j] = nodeId - nodeIdtemp;

            for (int i = 0; i < (this.nbElem + 1 - j); i++) {

                double xNode0 = p1x + (p3x - p1x) / this.nbElem * j;
                double xNode1 = p2x + (p3x - p2x) / this.nbElem * j;

                double yNode0 = p1y + (p3y - p1y) / this.nbElem * j;
                double yNode1 = p2y + (p3y - p2y) / this.nbElem * j;

                double xNode = xNode0 + (xNode1 - xNode0) / (this.nbElem - j) * i;
                double yNode = yNode0 + (yNode1 - yNode0) / (this.nbElem - j) * i;

                Node temp=new Node(String.valueOf(nodeId), xNode, yNode);
                this.nodes.add(temp);
                
                if (j==0) {
                    this.edgeNodes1.add(temp);
                    
                    if (i==0) {
                        this.node1=temp;
                    } else if (i==this.nbElem) {
                        this.node2=temp;
                    }
                }
                
                if (i==0) {
                    this.edgeNodes3.add(temp);
                } else if (i==(this.nbElem-j)) {
                    this.edgeNodes2.add(temp);
                }
                
                nodeId++;

            }
        }

        // ajout dernier noeud

        Node temp=new Node(String.valueOf(nodeId), p3x, p3y);
        
        this.nodes.add(temp);
        nodeStart[this.nbElem] = nodeId - nodeIdtemp;
        this.edgeNodes2.add(temp);
        this.edgeNodes3.add(temp);
        this.node3=temp;
        nodeId++;

        for (int i = 0; i < this.nbElem; i++) {

            for (int j = 0; j < (this.nbElem - i); j++) {

                Node n1 = this.nodes.get(nodeStart[i] + j);
                Node n2 = this.nodes.get(nodeStart[i] + j + 1);
                Node n3 = this.nodes.get(nodeStart[i + 1] + j);

                this.tris.add(new Tri(n1, n2, n3, thickness, mat, 0.0));
                elementId++;
            }

            if (i > 0) {
                for (int j = 0; j < (this.nbElem - i); j++) {

                    Node n1 = this.nodes.get(nodeStart[i] + j);
                    Node n2 = this.nodes.get(nodeStart[i] + j + 1);
                    Node n3 = this.nodes.get(nodeStart[i - 1] + 1 + j);

                    this.tris.add(new Tri(n1, n3, n2, thickness, mat, 0.0));
                    elementId++;
                }
            }


        }


    }

    public ArrayList<Tri> getTris() {
        return tris;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }
}
