/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plegatfem;

/**
 *
 * @author jmb2
 */
public class NodeDisp {

    private String nodeId;
    private boolean dxDef, dyDef, dzDef, drxDef, dryDef, drzDef;
    private double dx, dy, dz, drx, dry, drz;

    public NodeDisp(String nodeId) {
        this(nodeId, "");
    }

    public NodeDisp(int nodeIdInt, String components) {
        this(String.valueOf(nodeIdInt), components);
    }

    public NodeDisp(String nodeId, String components) {
        this.nodeId = nodeId;

        this.init();

        components = "@" + components + "@";

        String[] args = components.split(",");

        for (int i = 0; i < args.length; i++) {

            args[i] = args[i].replace("@", "");
        }

        if (args.length == 6) {

            for (int i = 0; i < args.length; i++) {

                if (!args[i].equals("")) {
                    if (i == 0) {
                        this.setDx(Double.valueOf(args[i]));
                    } else if (i == 1) {
                        this.setDy(Double.valueOf(args[i]));
                    } else if (i == 2) {
                        this.setDz(Double.valueOf(args[i]));
                    } else if (i == 3) {
                        this.setDrx(Double.valueOf(args[i]));
                    } else if (i == 4) {
                        this.setDry(Double.valueOf(args[i]));
                    } else if (i == 5) {
                        this.setDrz(Double.valueOf(args[i]));
                    }

                }

            }

        } else {
            System.out.println("probleme sur définition des composantes NodeDisp " + this.nodeId);
            System.out.print("arguments: ");
            for (int i = 0; i < args.length; i++) {
                System.out.print(args[i] + " / ");
            }
            System.out.println("");
            System.out.println("definition par defaut");
        }

    }

    public final void init() {
        this.dxDef = false;
        this.dyDef = false;
        this.dzDef = false;
        this.drxDef = false;
        this.dryDef = false;
        this.drzDef = false;

        this.dx = 0.;
        this.dy = 0.;
        this.dz = 0.;
        this.drx = 0.;
        this.dry = 0.;
        this.drz = 0.;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String NodeId) {
        this.nodeId = NodeId;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public double getDz() {
        return dz;
    }

    public double getDrx() {
        return drx;
    }

    public double getDry() {
        return dry;
    }

    public double getDrz() {
        return drz;
    }

    public boolean isDxDef() {
        return dxDef;
    }

    public boolean isDyDef() {
        return dyDef;
    }

    public boolean isDzDef() {
        return dzDef;
    }

    public boolean isDrxDef() {
        return drxDef;
    }

    public boolean isDryDef() {
        return dryDef;
    }

    public boolean isDrzDef() {
        return drzDef;
    }

    public final void setDx(double dx) {
        this.dx = dx;
        this.dxDef = true;
    }

    public final void setDy(double dy) {
        this.dy = dy;
        this.dyDef = true;
    }

    public final void setDz(double dz) {
        this.dz = dz;
        this.dzDef = true;
    }

    public final void setDrx(double dr) {
        this.drx = dr;
        this.drxDef = true;
    }

    public final void setDry(double dr) {
        this.dry = dr;
        this.dryDef = true;
    }

    public final void setDrz(double dr) {
        this.drz = dr;
        this.drzDef = true;
    }

    public void setDrxDef(boolean drDef) {
        this.drxDef = drDef;
    }

    public void setDryDef(boolean drDef) {
        this.dryDef = drDef;
    }

    public void setDrzDef(boolean drDef) {
        this.drzDef = drDef;
    }

    public void setDxDef(boolean dxDef) {
        this.dxDef = dxDef;
    }

    public void setDyDef(boolean dyDef) {
        this.dyDef = dyDef;
    }

    public void setDzDef(boolean dzDef) {
        this.dzDef = dzDef;
    }

}
