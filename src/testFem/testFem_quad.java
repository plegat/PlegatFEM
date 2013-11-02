/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testFem;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import plegatfem.Material;
import plegatfem.Node;
import plegatfem.NodeDisp;
import plegatfem.NodeForce;
import plegatfem.Problem;
import plegatfem.Tri;
import plegatfem.Vector;


/**
 *
 * @author jmb2
 */
public class testFem_quad {
    
    public static void main(String[] args) {
        
        Material mat=new Material("alu", 70000, 0.33);
        
        Node n1=new Node("1", 0, 0);
        Node n2=new Node("2", 10, 0);
        Node n3=new Node("3", 10, 10);
        Node n4=new Node("4", 0, 10);
        
        Tri t1=new Tri(n1, n2, n3, 1, mat, 0);
        Tri t2=new Tri(n1, n3, n4, 1, mat, 0);
        
        Problem pb=new Problem();
        
        pb.addNode(n1);
        pb.addNode(n2);
        pb.addNode(n3);
        pb.addNode(n4);
        
        pb.addTri(t1);
        pb.addTri(t2);
        
        NodeDisp nd1=new NodeDisp(1,"0,0,0");
        pb.addNodeDisp(nd1);
        
        NodeDisp nd4=new NodeDisp(4,"0,,0");
        pb.addNodeDisp(nd4);
        
        NodeDisp nd2=new NodeDisp(2,",,0");
        pb.addNodeDisp(nd2);
        
        NodeDisp nd3=new NodeDisp(3,",,0");
        pb.addNodeDisp(nd3);
        
        
        NodeForce nf=new NodeForce(2, 0, -1000, 0);
        pb.addNodeLoad(nf);
        
        pb.initStiffMatrix();
        
        pb.solve();
        
        Vector solDisp=pb.getSolDisp();
        Vector solLoad=pb.getSolLoad();
        
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat("0.0000", dfs);
        DecimalFormat df2 = new DecimalFormat("0", dfs);

        
        System.out.println("résultats déplacements:");
        for (int i = 0; i < solDisp.size()/3; i++) {
            System.out.println("#"+(i+1)+": "+df.format(solDisp.getVal(i*3))+", "+df.format(solDisp.getVal(i*3+1))+", "+df.format(solDisp.getVal(i*3+2)));
        }
        
        System.out.println("");
        
        System.out.println("résultats efforts:");
        for (int i = 0; i < solLoad.size()/3; i++) {
            System.out.println("#"+(i+1)+": "+df2.format(solLoad.getVal(i*3))+", "+df2.format(solLoad.getVal(i*3+1))+", "+df2.format(solLoad.getVal(i*3+2)));
        }
        
        
    }
    
    
    
}
