/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geom;

import gui.PlegatFemGui;
import java.util.ArrayList;
import plegatfem.Material;
import plegatfem.Node;
import plegatfem.NodeDisp;
import plegatfem.NodeForce;
import plegatfem.Problem;
import plegatfem.Vector;

/**
 *
 * @author jmb2
 */
public class TestGeom {

    public static void main(String[] args) {

        GeoPoint p1 = new GeoPoint("1", 0, 0);
        GeoPoint p2 = new GeoPoint("2", 100, 0);
        GeoPoint p3 = new GeoPoint("3", 100, 10);
        GeoPoint p4 = new GeoPoint("4", 0, 10);

        Material mat = new Material("alu", 70000, 0.33);

        GeoQuad quad = new GeoQuad("1", p1, p2, p3, p4, 1, mat);
        quad.setNbElements(60, 6);
        quad.mesh(1, 1);

        Node nd1 = quad.getNode1();
        Node nd2 = quad.getNode2();
        Node nd3 = quad.getNode3();
        Node nd4 = quad.getNode4();

        System.out.println("nd1/2/3/4: " + nd1.getId() + "/" + nd2.getId() + "/" + nd3.getId() + "/" + nd4.getId());

        PlegatFemGui gui = new PlegatFemGui();

        Problem pb = new Problem();
        pb.setListNode(quad.getNodes());
        pb.setListTri(quad.getTris());

        NodeDisp bloc = new NodeDisp(nd1.getId(), "0,0,");
        NodeDisp bloc2 = new NodeDisp(nd4.getId(), "0,,");

        pb.addNodeDisp(bloc);
        pb.addNodeDisp(bloc2);

        NodeForce load = new NodeForce(nd2.getId(), 0, -100, 0, 0, 0, 0);
        pb.addNodeLoad(load);

        ArrayList<Node> list = quad.getNodes();

        for (int i = 0; i < list.size(); i++) {
            Node temp = list.get(i);

            NodeDisp tempnd = new NodeDisp(temp.getId(), ",,0");
            pb.addNodeDisp(tempnd);

        }

        pb.initStiffMatrix();
        pb.solve();

        /*
         Vector result=pb.getSolDisp();
        
         for (int i = 0; i < result.size(); i++) {
         System.out.println("result["+i+"]="+result.getVal(i));
            
         }
         */
        gui.setProblem(pb);

        gui.setVisible(true);

    }
}
