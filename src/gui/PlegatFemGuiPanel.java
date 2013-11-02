/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PlegatFemGuiPanel.java
 *
 * Created on 16 oct. 2011, 20:47:44
 */
package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import plegatfem.Node;
import plegatfem.Problem;
import plegatfem.Tri;

/**
 *
 * @author jmb2
 */
public class PlegatFemGuiPanel extends javax.swing.JPanel {

    private Problem pb;
    private boolean flagDrawMesh, flagDrawDisp;
    private double xmin, xmax, ymin, ymax;
    private double echelleDisp = 10.;

    /** Creates new form PlegatFemGuiPanel */
    public PlegatFemGuiPanel() {
        initComponents();

        this.pb = null;
        this.flagDrawMesh = false;
        this.flagDrawDisp = false;

        this.initBounds();
    }

    public void setProblem(Problem pb) {
        this.pb = pb;

        this.initBounds();
    }

    public void setFlagDrawMesh(boolean flag) {
        this.flagDrawMesh = flag;
        this.repaint();
    }

    public void setFlagDrawDisp(boolean flag) {
        this.flagDrawDisp = flag;
        this.repaint();
    }

    public void setEchelleDisp(double value) {
        this.echelleDisp = value;
        this.repaint();
    }

    public final void initBounds() {

        if (this.pb != null) {

            Node[] tabNode = this.pb.getNodes();

            int nbNnode = tabNode.length;

            xmin = tabNode[0].getX();
            xmax = xmin;
            ymin = tabNode[0].getY();
            ymax = ymin;

            for (int i = 1; i < nbNnode; i++) {

                double x = tabNode[i].getX();
                double y = tabNode[i].getY();

                if (x > xmax) {
                    xmax = x;
                }
                if (x < xmin) {
                    xmin = x;
                }

                if (y > ymax) {
                    ymax = y;
                }
                if (y < ymin) {
                    ymin = y;
                }

            }

            xmax = xmax + Math.abs(0.1 * xmax);
            xmin = xmin - Math.abs(0.1 * xmin);
            ymax = ymax + Math.abs(0.1 * ymax);
            ymin = ymin - Math.abs(0.1 * ymin);


        } else {
            xmin = -50;
            xmax = 100;
            ymin = -50;
            ymax = 100;
        }

        System.out.println("min/max: x/"+xmin+"/"+xmax+", y/"+ymin+"/"+ymax);
        

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;


        int h = this.getHeight();
        int w = this.getWidth();



        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w, h);


        double scaleX = w / (xmax - xmin);
        double scaleY = h / (ymax - ymin);

        double scale = 0.9 * Math.min(scaleX, scaleY);

        //int originX = (int)Math.round(w/2-(xmax+xmin)/2*scale) ;
        //int originY = (int)Math.round(h/2-(ymax+ymin)/2*scale) ;

        int originX = w / 2 - (int) Math.round((xmax + xmin) / 2. * scale);
        int originY = h - (h / 2 - (int) Math.round((ymax + ymin) / 2. * scale));

        /*
        System.out.println("min/max: " + xmin + " / " + xmax + " , " + ymin + " / " + ymax);
        System.out.println("w/h: " + w + " / " + h);
        System.out.println("oigines: " + originX + " / " + originY);
        System.out.println("scale: " + scale);
         */

        // dessin axes

        g2.setStroke(new BasicStroke());

        g.setColor(Color.red);
        g.drawLine(0, originY, w, originY);
        g.drawLine(originX, 0, originX, h);


        // dessin du mesh

        g.setColor(Color.ORANGE);


        if ((flagDrawMesh) && (this.pb != null)) {
            this.drawMesh(w, h, scale, g);
        }

        // dessin du mesh deformé

        g.setColor(Color.BLACK);


        if ((flagDrawDisp) && (this.pb != null)) {
            this.drawDisp(w, h, scale, g);
        }





    }

    private void drawMesh(int w, int h, double scale, Graphics g) {


        Tri[] tabTri = this.pb.getTris();

        int nbTri = tabTri.length;

        int dx = w / 2 - (int) Math.round((xmax + xmin) / 2 * scale);
        int dy = h / 2 - (int) Math.round((ymax + ymin) / 2 * scale);

        for (int i = 0; i < nbTri; i++) {

            Node nd1 = tabTri[i].getNode1();
            Node nd2 = tabTri[i].getNode2();
            Node nd3 = tabTri[i].getNode3();

            g.drawLine((int) Math.round(nd1.getX() * scale) + dx, h - ((int) Math.round(nd1.getY() * scale) + dy), (int) Math.round(nd2.getX() * scale) + dx, h - ((int) Math.round(nd2.getY() * scale) + dy));
            g.drawLine((int) Math.round(nd2.getX() * scale) + dx, h - ((int) Math.round(nd2.getY() * scale) + dy), (int) Math.round(nd3.getX() * scale) + dx, h - ((int) Math.round(nd3.getY() * scale) + dy));
            g.drawLine((int) Math.round(nd3.getX() * scale) + dx, h - ((int) Math.round(nd3.getY() * scale) + dy), (int) Math.round(nd1.getX() * scale) + dx, h - ((int) Math.round(nd1.getY() * scale) + dy));



        }

    }

    private void drawDisp(int w, int h, double scale, Graphics g) {


        Tri[] tabTri = this.pb.getTris();

        int nbTri = tabTri.length;

        int dx = w / 2 - (int) Math.round((xmax + xmin) / 2 * scale);
        int dy = h / 2 - (int) Math.round((ymax + ymin) / 2 * scale);

        for (int i = 0; i < nbTri; i++) {

            Node nd1 = tabTri[i].getNode1();
            Node nd2 = tabTri[i].getNode2();
            Node nd3 = tabTri[i].getNode3();

            double dx1 = this.pb.getDispAtNode(nd1.getId(), 0) * this.echelleDisp * scale;
            double dy1 = this.pb.getDispAtNode(nd1.getId(), 1) * this.echelleDisp * scale;
            double dx2 = this.pb.getDispAtNode(nd2.getId(), 0) * this.echelleDisp * scale;
            double dy2 = this.pb.getDispAtNode(nd2.getId(), 1) * this.echelleDisp * scale;
            double dx3 = this.pb.getDispAtNode(nd3.getId(), 0) * this.echelleDisp * scale;
            double dy3 = this.pb.getDispAtNode(nd3.getId(), 1) * this.echelleDisp * scale;

            g.drawLine((int) Math.round(nd1.getX() * scale + dx1) + dx, h - ((int) Math.round(nd1.getY() * scale + dy1) + dy), (int) Math.round(nd2.getX() * scale + dx2) + dx, h - ((int) Math.round(nd2.getY() * scale + dy2) + dy));
            g.drawLine((int) Math.round(nd2.getX() * scale + dx2) + dx, h - ((int) Math.round(nd2.getY() * scale + dy2) + dy), (int) Math.round(nd3.getX() * scale + dx3) + dx, h - ((int) Math.round(nd3.getY() * scale + dy3) + dy));
            g.drawLine((int) Math.round(nd3.getX() * scale + dx3) + dx, h - ((int) Math.round(nd3.getY() * scale + dy3) + dy), (int) Math.round(nd1.getX() * scale + dx1) + dx, h - ((int) Math.round(nd1.getY() * scale + dy1) + dy));



        }

    }
}