/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Color;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import plegatfem.Node;
import sun.awt.image.IntegerComponentRaster;

/**
 *
 * @author jmb2
 */
public class MyPaintContext implements PaintContext {

    private ColorBandConvertor cvb;
    private Node nd1, nd2, nd3;
    private ColorModel modeleCouleur;
    private Rectangle rectangleEnglobant;
    private DispTria dt;

    public MyPaintContext(ColorBandConvertor cvb, Node nd1, Node nd2, Node nd3, double u1, double v1, double u2, double v2, double u3, double v3, ColorModel cm, Rectangle deviceBounds) {
        this.cvb = cvb;
        
        this.nd1 = nd1;
        this.nd2 = nd2;
        this.nd3 = nd3;

        this.modeleCouleur = cm;
        this.rectangleEnglobant = deviceBounds;

        this.dt = new DispTria(nd1, nd2, nd3);
        this.dt.setDispAtNodes(u1, v1, u2, v2, u3, v3);

    }

    public MyPaintContext(ColorBandConvertor cvb, ColorModel cm, Rectangle deviceBounds) {
        this.cvb = cvb;

        this.modeleCouleur = cm;
        this.rectangleEnglobant = deviceBounds;
    }

    public void setNodes(Node nd1, Node nd2, Node nd3) {
        this.nd1 = nd1;
        this.nd2 = nd2;
        this.nd3 = nd3;

        this.dt = new DispTria(nd1, nd2, nd3);
    }

    public void setDisp(double u1, double v1, double u2, double v2, double u3, double v3) {
        this.dt.setDispAtNodes(u1, v1, u2, v2, u3, v3);
    }

    @Override
    public void dispose() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ColorModel getColorModel() {
        return this.modeleCouleur;
    }

    @Override
    public Raster getRaster(int x, int y, int w, int h) {


        //Construit le raster de la taille du petit rectengle
        IntegerComponentRaster raster = (IntegerComponentRaster) this.modeleCouleur.createCompatibleWritableRaster(w, h);
        //Calcul l'abcisse du petit rectangle relativement à l'englobant
        int xx = x - this.rectangleEnglobant.x;
        int yy = y - this.rectangleEnglobant.y;
        //Offsett de dèmarage de peinture (En général ça vaut 0)
        int off = raster.getDataOffset(0);
        //Largeur de peinture (En gènèral c'est exactement la largeur du petit rectangle)
        int longueur = raster.getScanlineStride();
        //Pixels du Raster à peindre
        int[] pixels = raster.getDataStorage();
        //Pour chaque colone du rectangle faire
        for (int i = 0; i < w; i++) {
            //Calcul de la couleur selon l'abcisse du point



            //Sur toute la colone de l'absicce en cours colorier de la meme couleur
            for (int j = 0; j < h; j++) {

                int x0 = x + i;
                int y0 = y + j;

                double[] disp = this.dt.getDisp(x0, y0);



                Color coul = this.cvb.getIndexedColor(disp[0]);
                //Color coul = this.cvb.getColor(disp[0]);


                //Le tableau de pixel est d'une seule dimension, bien que reprèsentant une image à deux dimensions.
                //En fait la première ligne est sur les longueur premiers èlèments,
                //la seconde sur les longueur suivants
                //...
                //off est le point de dèpart sur le tableau
                pixels[j * longueur + i + off] = coul.getRGB();
            }
        }
        return raster;


    }
}
