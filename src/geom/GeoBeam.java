/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geom;

import java.util.ArrayList;
import plegatfem.Beam;
import plegatfem.Material;
import plegatfem.Node;

/**
 *
 * @author jmb2
 */
public class GeoBeam {
    
    private String id;
    private GeoPoint p1,p2;
    private double area, inertia;
    private ArrayList<Node> nodes;
    private ArrayList<Beam> beams;
    private Material mat;
    private double nbElem;
    private ArrayList<Node> edgeNodes1;
    private Node node1,node2;
    

    public GeoBeam(String id, GeoPoint p1, GeoPoint p2, double area, double inertia,Material mat) {
        this.id = id;
        this.p1 = p1;
        this.p2 = p2;
        this.area = area;
        this.inertia = inertia;
        this.mat=mat;
        
        this.nodes=new ArrayList<Node>();
        this.beams=new ArrayList<Beam>();
        this.edgeNodes1=new ArrayList<Node>();
        
        this.init();
    }

    public final void init() {
        this.nodes.clear();
        this.beams.clear();
        
        this.edgeNodes1.clear();
    
        this.setNbElements(1);
        
    }

    public void setNbElementsBySize(int meshSize) {
        
        double l=Math.sqrt(Math.pow(p2.getX()-p1.getX(), 2)+Math.pow(p2.getY()-p1.getY(), 2));
        
        this.nbElem=(int)Math.round(l/meshSize)+1;
        
    }
    
    public void setNbElements(int side) {
        this.nbElem = side;
    }
    
    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getInertia() {
        return inertia;
    }

    public void setInertia(double inertia) {
        this.inertia = inertia;
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

    public Material getMat() {
        return mat;
    }

    public void setMat(Material mat) {
        this.mat = mat;
    }
    
    public ArrayList<Node> getEdgeNodes1() {
        return this.edgeNodes1;
    }

    public Node getNode1() {
        return node1;
    }

    public Node getNode2() {
        return node2;
    }
    
    
    public void mesh(int nodeId,int elementId) {
        
        
        double p1x=p1.getX();
        double p2x=p2.getX();
        double p1y=p1.getY();
        double p2y=p2.getY();
        
        
        this.nodes.add(new Node(String.valueOf(nodeId),p1x,p1y));
        nodeId++;
        
        for (int i = 0; i < this.nbElem; i++) {
            
            Node temp=new Node(String.valueOf(nodeId),p1x+(p2x-p1x)/this.nbElem*(i+1),p1y+(p2y-p1y)/this.nbElem*(i+1));
            
            this.nodes.add(temp);
            this.edgeNodes1.add(temp);
            nodeId++;
            
            this.beams.add(new Beam(this.nodes.get(i), this.nodes.get(i+1), this.area, this.inertia, this.mat));
            
        }
        
        this.node1=this.nodes.get(0);
        this.node2=this.nodes.get(this.nodes.size()-1);
        
        
    }

    public ArrayList<Beam> getBeams() {
        return beams;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    
    
}
