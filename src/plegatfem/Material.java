/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plegatfem;

/**
 *
 * @author jmb2
 */
public class Material {
    
    double young,nu;
    String name;

    public Material(String name,double young, double nu) {
        this.young = young;
        this.nu = nu;
        this.name = name;
        //test
    }

    public double getNu() {
        return nu;
    }

    public void setNu(double nu) {
        this.nu = nu;
    }

    public double getYoung() {
        return young;
    }

    public void setYoung(double young) {
        this.young = young;
    }
    
    
    
    
    
}
