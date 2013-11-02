/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plegatfem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jmb2
 */
public class Problem {

    private ArrayList<Tri> listTri;
    private ArrayList<Beam> listBeam;
    private HashMap<String, Node> listNode;
    private HashMap<String, Integer> listRankNode;
    private Matrix matStiff;
    private ArrayList<NodeDisp> disp;
    private ArrayList<NodeForce> load;
    private Vector solDisp, solLoad;
    private int nodeRank;

    public Problem() {
        this.listTri = new ArrayList<Tri>();
        this.listBeam = new ArrayList<Beam>();
        this.listNode = new HashMap<String, Node>();
        this.listRankNode = new HashMap<String, Integer>();

        this.disp = new ArrayList<NodeDisp>();
        this.load = new ArrayList<NodeForce>();

        this.init();
    }

    public final void init() {
        this.listTri.clear();
        this.listBeam.clear();
        this.listNode.clear();
        nodeRank = 0;

        this.initDisp();
        this.initLoad();
    }

    public Vector getSolDisp() {
        return solDisp;
    }

    public Vector getSolLoad() {
        return solLoad;
    }

    public void addTri(Tri tri) {
        this.listTri.add(tri);
    }

    public void addTri(ArrayList<Tri> list) {
        for (int i = 0; i < list.size(); i++) {
            this.addTri(list.get(i));
        }
    }

    public Tri getTri(int rank) {
        return this.listTri.get(rank);
    }

    public void addBeam(Beam beam) {
        this.listBeam.add(beam);
    }

    public void addBeam(ArrayList<Beam> list) {
        for (int i = 0; i < list.size(); i++) {
            this.addBeam(list.get(i));
        }
    }

    public Beam getBeam(int rank) {
        return this.listBeam.get(rank);
    }

    public void setListBeam(ArrayList<Beam> listBeam) {
        this.listBeam.clear();
        this.addBeam(listBeam);
    }

    public void setListNode(ArrayList<Node> listNode) {

        this.listNode.clear();
        this.addNode(listNode);
    }

    public void setListTri(ArrayList<Tri> listTri) {
        this.listTri.clear();
        this.addTri(listTri);
    }

    public void addNode(Node node) {
        String id = node.getId();
        Node temp = this.listNode.put(id, node);
        if (temp == null) {
            this.listRankNode.put(id, nodeRank);
            nodeRank++;
        }
    }

    public void addNode(ArrayList<Node> list) {
        for (int i = 0; i < list.size(); i++) {
            this.addNode(list.get(i));
        }
    }

    public Node getNode(String id) {
        return this.listNode.get(id);
    }

    public int getNodeRank(String id) {
        return this.listRankNode.get(id);
    }

    public void initStiffMatrix() {


        System.out.println("initialisation matrice de raideur");

        int nbNodes = this.listNode.size();
        int nbTris = this.listTri.size();
        int nbBeams = this.listBeam.size();

        this.matStiff = new Matrix(nbNodes * 3, nbNodes * 3);

        // traitement des éléments "beam" 

        for (int i = 0; i < nbBeams; i++) {

            Beam temp = this.listBeam.get(i);

            Matrix matBeam = temp.getStiffnessMatrix();

            int rank1 = this.listRankNode.get(temp.getNode1().getId());
            int rank2 = this.listRankNode.get(temp.getNode2().getId());

            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    this.matStiff.setVal(rank1 * 3 + j, rank1 * 3 + k, this.matStiff.getVal(rank1 * 3 + j, rank1 * 3 + k) + matBeam.getVal(j, k));
                    this.matStiff.setVal(rank1 * 3 + j, rank2 * 3 + k, this.matStiff.getVal(rank1 * 3 + j, rank2 * 3 + k) + matBeam.getVal(j, 3 + k));

                    this.matStiff.setVal(rank2 * 3 + j, rank1 * 3 + k, this.matStiff.getVal(rank2 * 3 + j, rank1 * 3 + k) + matBeam.getVal(3 + j, k));
                    this.matStiff.setVal(rank2 * 3 + j, rank2 * 3 + k, this.matStiff.getVal(rank2 * 3 + j, rank2 * 3 + k) + matBeam.getVal(3 + j, 3 + k));
                }
            }
        }

        // traitement des éléments "tri" 

        for (int i = 0; i < nbTris; i++) {

            Tri temp = this.listTri.get(i);
            Matrix matTri = temp.getStiffnessMatrix();

            int rank1 = this.listRankNode.get(temp.getNode1().getId());
            int rank2 = this.listRankNode.get(temp.getNode2().getId());
            int rank3 = this.listRankNode.get(temp.getNode3().getId());

            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    this.matStiff.setVal(rank1 * 3 + j, rank1 * 3 + k, this.matStiff.getVal(rank1 * 3 + j, rank1 * 3 + k) + matTri.getVal(j, k));
                    this.matStiff.setVal(rank1 * 3 + j, rank2 * 3 + k, this.matStiff.getVal(rank1 * 3 + j, rank2 * 3 + k) + matTri.getVal(j, 3 + k));
                    this.matStiff.setVal(rank1 * 3 + j, rank3 * 3 + k, this.matStiff.getVal(rank1 * 3 + j, rank3 * 3 + k) + matTri.getVal(j, 6 + k));

                    this.matStiff.setVal(rank2 * 3 + j, rank1 * 3 + k, this.matStiff.getVal(rank2 * 3 + j, rank1 * 3 + k) + matTri.getVal(3 + j, k));
                    this.matStiff.setVal(rank2 * 3 + j, rank2 * 3 + k, this.matStiff.getVal(rank2 * 3 + j, rank2 * 3 + k) + matTri.getVal(3 + j, 3 + k));
                    this.matStiff.setVal(rank2 * 3 + j, rank3 * 3 + k, this.matStiff.getVal(rank2 * 3 + j, rank3 * 3 + k) + matTri.getVal(3 + j, 6 + k));

                    this.matStiff.setVal(rank3 * 3 + j, rank1 * 3 + k, this.matStiff.getVal(rank3 * 3 + j, rank1 * 3 + k) + matTri.getVal(6 + j, k));
                    this.matStiff.setVal(rank3 * 3 + j, rank2 * 3 + k, this.matStiff.getVal(rank3 * 3 + j, rank2 * 3 + k) + matTri.getVal(6 + j, 3 + k));
                    this.matStiff.setVal(rank3 * 3 + j, rank3 * 3 + k, this.matStiff.getVal(rank3 * 3 + j, rank3 * 3 + k) + matTri.getVal(6 + j, 6 + k));
                }
            }
        }

    }

    public void initDisp() {
        this.disp.clear();
    }

    public void initLoad() {
        this.load.clear();
    }

    public void addNodeDisp(NodeDisp nd) {
        this.disp.add(nd);
    }

    public void addNodeLoad(NodeForce nf) {
        this.load.add(nf);
    }

    public NodeDisp getNodeDisp(int rank) {
        return this.disp.get(rank);
    }

    public NodeForce getNodeLoad(int rank) {
        return this.load.get(rank);
    }

    public void solve() {

        long timeStart = System.currentTimeMillis();

        int nbNodes = this.listNode.size();

        this.solLoad = new Vector(nbNodes * 3);
        this.solLoad.init();

        System.out.println("initialisation chargement...");

        for (int i = 0; i < this.load.size(); i++) {

            NodeForce temp = this.load.get(i);
            int rank = this.listRankNode.get(temp.getNodeId());

            this.solLoad.setVal(rank * 3, temp.getFx());
            this.solLoad.setVal(rank * 3 + 1, temp.getFy());
            this.solLoad.setVal(rank * 3 + 2, temp.getMz());

        }

        ArrayList<Integer> rankToDelete = new ArrayList<Integer>();

        System.out.println("initialisation déplacements imposés...");

        this.solDisp = new Vector(nbNodes * 3);
        this.solDisp.init();

        for (int i = 0; i < this.disp.size(); i++) {

            NodeDisp temp = this.disp.get(i);
            int rank = this.listRankNode.get(temp.getNodeId());

            if (temp.isDxDef()) {
                rankToDelete.add(rank * 3);
                this.solDisp.setVal(rank * 3, temp.getDx());
            }

            if (temp.isDyDef()) {
                rankToDelete.add(rank * 3 + 1);
                this.solDisp.setVal(rank * 3 + 1, temp.getDy());
            }

            if (temp.isDrDef()) {
                rankToDelete.add(rank * 3 + 2);
                this.solDisp.setVal(rank * 3 + 2, temp.getDr());
            }


        }


        int[] rankToDeleteArray = new int[rankToDelete.size()];
        for (int i = 0; i < rankToDelete.size(); i++) {
            rankToDeleteArray[i] = rankToDelete.get(i);
        }
        Arrays.sort(rankToDeleteArray);

        //System.out.println("matrice de rigidité:");
        //System.out.println(this.matStiff.toString());

        Matrix matCalc = this.matStiff.clone();
        matCalc = matCalc.removeRowCol(rankToDeleteArray);

        //System.out.println("vecteur chargement:");
        //System.out.println(solLoad.toString());


        Vector loadCalc = solLoad.clone();            //intégrer les déplacements imposés

        for (int i = 0; i < loadCalc.size(); i++) {

            double tempLoad = loadCalc.getVal(i);

            for (int j = 0; j < rankToDeleteArray.length; j++) {
                tempLoad = tempLoad - this.matStiff.getVal(i, rankToDeleteArray[j]) * this.solDisp.getVal(rankToDeleteArray[j]);
            }

            loadCalc.setVal(i, tempLoad);
        }

        //System.out.println("vecteur chargement apres modif chargement impo:");
        //System.out.println(loadCalc.toString());



        loadCalc = loadCalc.removeRow(rankToDeleteArray);

        System.out.println("lancement du calcul...");

        loadCalc = matCalc.toUpperTri(loadCalc);
        Vector sol = matCalc.solve(loadCalc);


        matCalc = null;
        loadCalc = null;


        // mise en forme vecteur deplacements

        int offsetRow = 0;
        for (int i = 0; i < nbNodes * 3; i++) {
            if (rankToDeleteArray[offsetRow] != i) {
                this.solDisp.setVal(i, sol.getVal(i - offsetRow));
            } else {
                offsetRow++;
            }
        }

        try {
            // calcul vecteur efforts

            this.solLoad = this.matStiff.product(this.solDisp);

            System.out.println("calcul terminé");
            long timeEnd = System.currentTimeMillis();

            long timeCalc = timeEnd - timeStart;

            DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
            DecimalFormat df_ms = new DecimalFormat("0", dfs);
            DecimalFormat df_s = new DecimalFormat("0.0", dfs);

            String timeCalcSt;
            if (timeCalc < 1000) {
                timeCalcSt = df_ms.format(timeCalc) + " ms";
            } else {
                timeCalcSt = df_s.format(timeCalc / 1000.) + " s";
            }

            System.out.println("temps de calcul: " + timeCalcSt);


        } catch (Exception ex) {

            System.out.println("problème lors du calcul!!!");
            ex.printStackTrace();
        }





    }

    private static String format(double value, DecimalFormat df, int nbCar) {

        String chaine = df.format(value);
        int taille = chaine.length();

        for (int i = 0; i < (nbCar - taille); i++) {
            chaine = " " + chaine;
        }

        return chaine;

    }

    public void saveResults(String fichier) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fichier));

            //Vector solDisp = this.getSolDisp();
            //Vector solLoad = this.getSolLoad();

            DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
            DecimalFormat df = new DecimalFormat("0.0000", dfs);
            DecimalFormat df2 = new DecimalFormat("0.000", dfs);
            DecimalFormat df3 = new DecimalFormat("0", dfs);

            String newline = System.getProperty("line.separator");

            bw.write("résultats déplacements:");
            bw.write(newline);
            bw.write(newline);
            bw.write("Node        Dx              Dy              Rz");
            bw.write(newline);

            for (int i = 0; i < solDisp.size() / 3; i++) {
                bw.write(format(i + 1, df3, 5) + format(solDisp.getVal(i * 3), df, 16) + format(solDisp.getVal(i * 3 + 1), df, 16) + format(solDisp.getVal(i * 3 + 2), df, 16));
                bw.newLine();
            }

            bw.write(newline);

            bw.write("résultats efforts:");
            bw.write(newline);
            bw.write(newline);
            bw.write("Node        Fx              Fy              Mz");
            bw.write(newline);

            for (int i = 0; i < solLoad.size() / 3; i++) {

                double fx = solLoad.getVal(i * 3);
                double fy = solLoad.getVal(i * 3 + 1);
                double mz = solLoad.getVal(i * 3 + 2);

                if ((Math.abs(fx) > 1e-8) || (Math.abs(fy) > 1e-8) || (Math.abs(mz) > 1e-8)) {
                    bw.write(format(i + 1, df3, 5) + format(fx, df2, 16) + format(fy, df2, 16) + format(mz, df2, 16));
                    bw.write(newline);
                }
            }



            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Problem.class.getName()).log(Level.SEVERE, null, ex);
        }




    }

    public double getLoadAtNode(int nodeId, int component) {

        int rank = this.getNodeRank(String.valueOf(nodeId));

        return solLoad.getVal(rank * 3 + component);
    }

    public double getDispAtNode(String nodeId, int component) {

        int rank = this.getNodeRank(nodeId);

        return solDisp.getVal(rank * 3 + component);
    }

    public double getDispAtNode(int nodeIdInt, int component) {

        return this.getDispAtNode(String.valueOf(nodeIdInt), component);

    }

    public int getNbNodes() {
        return this.listNode.size();
    }

    public Node[] getNodes() {

        Node[] tabNode = new Node[this.getNbNodes()];
        this.listNode.values().toArray(tabNode);

        return tabNode;

    }

    public Tri[] getTris() {

        Tri[] tabTri = new Tri[this.listTri.size()];
        this.listTri.toArray(tabTri);

        return tabTri;

    }
}
