/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testFem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import plegatfem.Beam;
import plegatfem.Material;
import plegatfem.Node;
import plegatfem.NodeDisp;
import plegatfem.NodeForce;
import plegatfem.Problem;
import plegatfem.Tri;

/**
 *
 * @author jmb2
 */
public class ProblemImporter {

    private String datafile;
    private Problem pb;

    public ProblemImporter(String datafile) {
        this.datafile = datafile;

        this.pb = new Problem();
        

    }

    public Problem getPb() {
        return pb;
    }

    public void setDatafile(String datafile) {
        this.datafile = datafile;

        this.pb.init();
    }

    public void importFile() {
        BufferedReader br = null;
        String line="";
        long compteur = 0;
        
        int nbNodes=0;
        int nbTris=0;
        int nbBeams=0;
        

        try {
            br = new BufferedReader(new FileReader(this.datafile));

           

            int state = 0; /* 0: import des noeuds
             *              1: import des tris
             *              2: import des beams
             */


            while ((line = br.readLine()) != null) {
                compteur++;
                line=line.trim();
                

                if (!line.startsWith("$")) {

                    
                    if (state == 0) {   // importation des noeuds et conditions aux limites (déplacements, efforts)
                        
                        nbNodes = Integer.valueOf(line);

                        for (int i = 0; i < nbNodes; i++) {
                            compteur++;
                            line = br.readLine().trim();
                            
                            line="@"+line+"@"; 

                            String data[] = line.split(";");

                            if (data.length != 9) {

                                throw new Exception();

                            } else {
                                
                                data[0]=data[0].replace("@", "");
                                data[8]=data[8].replace("@", "");
                                
                                Node nodeTemp=new Node(data[0],Double.valueOf(data[1]),Double.valueOf(data[2]));
                                this.pb.addNode(nodeTemp);
                                
                                if ((!data[3].equals(""))||(!data[4].equals(""))||(!data[5].equals(""))) {
                                    
                                    StringBuilder sb=new StringBuilder(data[3]);
                                    sb.append(",");
                                    sb.append(data[4]);
                                    sb.append(",");
                                    sb.append(data[5]);
                                    
                                    NodeDisp ndTemp=new NodeDisp(data[0], sb.toString());
                                    this.pb.addNodeDisp(ndTemp);
                                }

                                if ((!data[6].equals(""))||(!data[7].equals(""))||(!data[8].equals(""))) {
                                    
                                    double fx=Double.valueOf(data[6]);
                                    double fy=Double.valueOf(data[7]);
                                    double mz=Double.valueOf(data[8]);
                                    
                                    NodeForce nfTemp=new NodeForce(data[0], fx,fy,0,0,0,mz);
                                    this.pb.addNodeLoad(nfTemp);
                                }

                                
                                
                                
                                
                            }



                        }

                        state = 1;
                    } else if (state==1) {  // importation des tris
                        
                        nbTris = Integer.valueOf(line);
                        Material matTemp=new Material("", 0, 0.33);

                        for (int i = 0; i < nbTris; i++) {
                            compteur++;
                            line = br.readLine().trim();
                            
                            line="@"+line+"@"; 

                            String data[] = line.split(";");

                            if (data.length != 7) {

                                throw new Exception();

                            } else {
                                
                                data[0]=data[0].replace("@", "");
                                data[6]=data[6].replace("@", "");
                                
                                double young=Double.valueOf(data[4]);
                                double nu=Double.valueOf(data[5]);
                                
                                if (matTemp.getYoung()!=young) {
                                    matTemp.setYoung(young);
                                }
                                if (matTemp.getNu()!=nu) {
                                    matTemp.setNu(nu);
                                }
                                
                                Node nd1=this.pb.getNode(data[1]);
                                Node nd2=this.pb.getNode(data[2]);
                                Node nd3=this.pb.getNode(data[3]);
                                
                                double ep=Double.valueOf(data[6]);
                                
                                Tri triTemp=new Tri(nd1, nd2, nd3, ep, matTemp, 0.);
                                this.pb.addTri(triTemp);
                            }
                        }
                        
                        state=2;
                        
                    } else if (state==2) {  // importation des beams
                        
                        nbBeams = Integer.valueOf(line);
                        Material matTemp=new Material("", 0, 0.33);

                        for (int i = 0; i < nbBeams; i++) {
                            compteur++;
                            line = br.readLine().trim();
                            
                            line="@"+line+"@"; 

                            String data[] = line.split(";");

                            if (data.length != 6) {

                                throw new Exception();

                            } else {
                                
                                data[0]=data[0].replace("@", "");
                                data[5]=data[5].replace("@", "");
                                
                                double young=Double.valueOf(data[3]);
                                
                                if (matTemp.getYoung()!=young) {
                                    matTemp.setYoung(young);
                                }
                                
                                Node nd1=this.pb.getNode(data[1]);
                                Node nd2=this.pb.getNode(data[2]);
                                
                                double area=Double.valueOf(data[4]);
                                double inertia=Double.valueOf(data[5]);
                                
                                
                                Beam beamTemp=new Beam(nd1, nd2, area, inertia, matTemp);
                                this.pb.addBeam(beamTemp);
                            }
                        }
                        
                        
                        state=3;
                    } else {
                        System.out.println("unattended string at line "+compteur+": "+line);
                    }


                }
            }

            System.out.println("fin d'importation du fichier");
            System.out.println("import\u00e9s: "+nbNodes+" noeuds, "+nbTris+" tris, "+nbBeams+" beams");


        } catch (IOException ex) {
            Logger.getLogger(ProblemImporter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProblemImporter.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(ProblemImporter.class.getName()).log(Level.SEVERE, "line #{0}: {1}", new Object[]{compteur, line});
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(ProblemImporter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void solve() {
        
        this.pb.initStiffMatrix();
        
        this.pb.solve();
        
        
    }
    
    

    public static void main(String[] args) {

        String chemin = "/home/jmb2/Bureau/test_MEF/test_plegat.dat";

        ProblemImporter pbimp = new ProblemImporter(chemin);
        pbimp.importFile();

        pbimp.solve();


    }
}
