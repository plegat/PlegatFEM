/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testFem;

import gui.PlegatFemGui;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jmb2
 */
public class ProblemManager {

    private ArrayList<String> inputfiles;

    public ProblemManager() {
        this.inputfiles = new ArrayList<String>();
    }

    public void init() {
        this.inputfiles.clear();
    }

    public void addInputFile(String path) {
        this.inputfiles.add(path);
        System.out.println("adding file " + path + " to iput files list");
    }

    public void calculate() {

        int nbFiles = this.inputfiles.size();

        for (int i = 0; i < nbFiles; i++) {

            String inputFile = this.inputfiles.get(i);

            ProblemImporter pi = new ProblemImporter(inputFile);
            pi.importFile();
            pi.solve();

            pi.getPb().saveResults(inputFile + ".resu");


        }


    }

    public static void main(String[] args) {



        if (args.length == 0) {
            System.out.println("usage: java -jar [-Xms512m][-i inputFile|-l inputListFile]");
            System.exit(0);
        }


        long startTime = System.currentTimeMillis();

        ProblemManager pm = new ProblemManager();

        int rank = 0;

        while (rank < args.length) {
            if (args[rank].equals("-gui")) {

                java.awt.EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        new PlegatFemGui().setVisible(true);
                    }
                });
                
                rank = args.length;

            } else if (args[rank].equals("-i")) {
                pm.addInputFile(args[rank + 1]);
                rank = rank + 2;
            } else if (args[rank].equals("-l")) {

                String fileList = args[rank + 1];


                try {
                    BufferedReader br = new BufferedReader(new FileReader(fileList));
                    String line;

                    while ((line = br.readLine()) != null) {

                        if (!line.trim().equals("")) {
                            pm.addInputFile(line);
                        }

                    }


                    br.close();

                } catch (IOException ex) {
                    Logger.getLogger(ProblemManager.class.getName()).log(Level.SEVERE, null, ex);
                }

                rank = rank + 2;

            } else if (args[rank].startsWith("-X")) {
                rank = rank + 2;
            }

        }

        long endOfReadingTime = System.currentTimeMillis();

        pm.calculate();

        long endTime = System.currentTimeMillis();

        System.out.println("preparation time: " + (endOfReadingTime - startTime) + " ms");
        System.out.println("calculation time: " + (endTime - endOfReadingTime) + " ms");

    }
}
