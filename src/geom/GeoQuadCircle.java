/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geom;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import plegatfem.Material;
import plegatfem.Node;
import plegatfem.Tri;

/**
 *
 * @author jmb2
 */
public class GeoQuadCircle {

    private String id;
    private GeoPoint p1, p2, p3, p4, p5, pc;
    private double thickness;
    private ArrayList<Node> nodes;
    private ArrayList<Tri> tris;
    private Material mat;
    private int nbElem1, nbElem2, nbElem3;
    private double radius;

    public GeoQuadCircle(String id, GeoPoint p1, GeoPoint p2, GeoPoint p3, GeoPoint p4, GeoPoint p5, double thickness, Material mat) {
        try {
            this.id = id;
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
            this.p4 = p4;
            this.p5 = p5;
            this.thickness = thickness;
            this.mat = mat;

            this.nodes = new ArrayList<Node>();
            this.tris = new ArrayList<Tri>();

            this.init();
            this.initCentre();
        } catch (Exception ex) {
            Logger.getLogger(GeoQuadCircle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public final void init() {
        this.nodes.clear();
        this.tris.clear();

        this.setNbElements(1, 1, 1);

    }

    public final void initCentre() throws Exception {

        double p1x = p1.getX();
        double p2x = p2.getX();
        double p3x = p3.getX();
        double p4x = p4.getX();
        double p1y = p1.getY();
        double p2y = p2.getY();
        double p3y = p3.getY();
        double p4y = p4.getY();

        double u = 0;
        double v = 0;

        if (Math.abs(p3x - p4x) < 1e-6) {
            if (Math.abs(p2x - p1x) < 1e-6) {
                System.out.println("option 1");
                throw new Exception("pb sur initiation centre");
            } else {
                System.out.println("option 2");
                u = (p4x - p1x) / (p2x - p1x);
                v = ((p1y - p4y) + u * (p2y - p1y)) / (p3y - p4y);
            }

        } else {
            if (Math.abs(p2x - p1x) < 1e-6) {
                System.out.println("option 3");
                v = (p1x - p4x) / (p3x - p4x);
                u = ((p4y - p1y) + v * (p3y - p4y)) / (p2y - p1y);
            } else {
                System.out.println("option 4");
                v = ((p1y - p4y) * (p2x - p1x) + (p2y - p1y) * (p4x - p1x)) / ((p2x - p1x) * (p3y - p4y) - (p2y - p1y) * (p3x - p4x));
                u = ((p4x - p1x) + v * (p3x - p4x)) / (p2x - p1x);
            }
        }

        System.out.println("u=" + u + ", v=" + v);

        double pcx = p1x + u * (p2x - p1x);
        double pcy = p1y + u * (p2y - p1y);


        this.pc = new GeoPoint("pc", pcx, pcy);
        this.radius = Math.sqrt(Math.pow(p2x - pcx, 2) + Math.pow(p2y - pcy, 2));

        System.out.println("centre: " + pcx + "/" + pcy + ", radius=" + this.radius);

    }

    public void setNbElementsBySize(int meshSize) {
        double l1 = Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
        double l3 = Math.sqrt(Math.pow(p4.getX() - p3.getX(), 2) + Math.pow(p4.getY() - p3.getY(), 2));
        double l4 = Math.sqrt(Math.pow(p5.getX() - p4.getX(), 2) + Math.pow(p5.getY() - p4.getY(), 2));
        double l5 = Math.sqrt(Math.pow(p1.getX() - p5.getX(), 2) + Math.pow(p1.getY() - p5.getY(), 2));

        this.nbElem1 = (int) Math.round(Math.min(l1, l3) / meshSize) + 1;
        this.nbElem2 = (int) Math.round(l4 / meshSize) + 1;
        this.nbElem3 = (int) Math.round(l5 / meshSize) + 1;

    }

    public void setNbElements(int side1, int side2, int side3) {
        this.nbElem1 = side1;
        this.nbElem2 = side2;
        this.nbElem3 = side3;
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

    public GeoPoint getP4() {
        return p4;
    }

    public void setP4(GeoPoint p4) {
        this.p4 = p4;
    }

    public GeoPoint getP5() {
        return p5;
    }

    public void setP5(GeoPoint p5) {
        this.p5 = p5;
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

    public void mesh(int nodeId, int elementId) {


        double p1x = p1.getX();
        double p2x = p2.getX();
        double p3x = p3.getX();
        double p4x = p4.getX();
        double p5x = p5.getX();
        double pcx = pc.getX();
        double p1y = p1.getY();
        double p2y = p2.getY();
        double p3y = p3.getY();
        double p4y = p4.getY();
        double p5y = p5.getY();
        double pcy = pc.getY();



        for (int j = 0; j < (this.nbElem1 + 1); j++) {
            for (int i = 0; i < (this.nbElem2 + this.nbElem3 + 1); i++) {

                double xNode1, yNode1;

                if (i <= nbElem2) {
                    xNode1 = p1x + (p5x - p1x) / this.nbElem2 * i;
                    yNode1 = p1y + (p5y - p1y) / this.nbElem2 * i;
                } else {
                    xNode1 = p5x + (p4x - p5x) / this.nbElem3 * (i - this.nbElem2);
                    yNode1 = p5y + (p4y - p5y) / this.nbElem3 * (i - this.nbElem2);
                }

                double l = Math.sqrt(Math.pow(xNode1 - pcx, 2) + Math.pow(yNode1 - pcy, 2));

                double xNode0 = pcx + (xNode1 - pcx) / l * this.radius;
                double yNode0 = pcy + (yNode1 - pcy) / l * this.radius;

                double xNode = xNode0 + (xNode1 - xNode0) / this.nbElem1 * j;
                double yNode = yNode0 + (yNode1 - yNode0) / this.nbElem1 * j;

                this.nodes.add(new Node(String.valueOf(nodeId), xNode, yNode));
                System.out.println("adding node: " + xNode + " / " + yNode);
                nodeId++;

            }
        }

        for (int i = 0; i < this.nbElem1; i++) {
            for (int j = 0; j < (this.nbElem2 + this.nbElem3); j++) {

                Node n1 = this.nodes.get(i * (this.nbElem2 + this.nbElem3 + 1) + j);
                Node n2 = this.nodes.get(i * (this.nbElem2 + this.nbElem3 + 1) + j + 1);
                Node n3 = this.nodes.get((i + 1) * (this.nbElem2 + this.nbElem3 + 1) + j + 1);
                Node n4 = this.nodes.get((i + 1) * (this.nbElem2 + this.nbElem3 + 1) + j);

                if (j <this.nbElem2) {
                    this.tris.add(new Tri(n1, n2, n4, thickness, mat, 0.0));
                    this.tris.add(new Tri(n2, n3, n4, thickness, mat, 0.0));
                } else {
                    this.tris.add(new Tri(n1, n2, n3, thickness, mat, 0.0));
                    this.tris.add(new Tri(n1, n3, n4, thickness, mat, 0.0));
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
