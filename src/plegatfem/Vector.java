/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plegatfem;

/**
 *
 * @author jmb2
 */
public class Vector extends Matrix {

    public Vector(int row) {
        super(row, 1);
    }

    @Override
    public Vector clone() {


        Vector temp = new Vector(this.row);

        for (int i = 0; i < this.row; i++) {
            temp.setVal(i, this.getVal(i));
        }

        return temp;


    }

    public int size() {
        return this.row;
    }
    
    public double getVal(int numRow) {
        return this.getVal(numRow, 0);
    }

    public void setVal(int numRow, double value) {
        this.setVal(numRow, 0, value);
    }

    public Vector removeRow(int[] rank) {

        int nbRank = rank.length;

        Vector temp = new Vector(this.row - nbRank);

        int offset_row = 0;

        for (int i = 0; i < this.row; i++) {

            if (i == rank[offset_row]) {
                offset_row++;
            } else {
                temp.setVal(i-offset_row,this.getVal(i));
            }
        }

        return temp;

    }
}
