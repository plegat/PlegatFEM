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
    private boolean dxDef,dyDef,drDef;
    private double dx,dy,dr;

    public NodeDisp(String nodeId) {
        this(nodeId,"");
    }
    
    public NodeDisp(int nodeIdInt,String components) {
        this(String.valueOf(nodeIdInt),components);
    }
    
    
    public NodeDisp(String nodeId,String components) {
        this.nodeId = nodeId;
        
        this.init();
        
        components="@"+components+"@";
        
        String[] args=components.split(",");
        
        for (int i = 0; i < args.length; i++) {
            
            args[i]=args[i].replace("@", "");
        }
        
        
        if (args.length==3) {
            
            for (int i = 0; i < args.length; i++) {
                
                if (!args[i].equals("")) {
                    if (i==0) {
                        this.setDx(Double.valueOf(args[i]));
                    } else if (i==1) {
                        this.setDy(Double.valueOf(args[i]));
                    } else if (i==2) {
                        this.setDr(Double.valueOf(args[i]));
                    }
                    
                }
                
            }
            
        } else {
            System.out.println("probleme sur définition des composantes NodeDisp "+this.nodeId);
            System.out.print("arguments: ");
            for (int i = 0; i < args.length; i++) {
                System.out.print(args[i]+" / ");
            }
            System.out.println("");
            System.out.println("definition par defaut");
        }
        
    }
    
    public final void init() {
        this.dxDef=false;
        this.dyDef=false;
        this.drDef=false;
        
        this.dx=0.;
        this.dy=0.;
        this.dr=0.;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String NodeId) {
        this.nodeId = NodeId;
    }

    public double getDr() {
        return dr;
    }

    public final void setDr(double dr) {
        this.dr = dr;
        this.drDef=true;
    }

    public boolean isDrDef() {
        return drDef;
    }

    public void setDrDef(boolean drDef) {
        this.drDef = drDef;
    }

    public double getDx() {
        return dx;
    }

    public final void setDx(double dx) {
        this.dx = dx;
        this.dxDef=true;
    }

    public boolean isDxDef() {
        return dxDef;
    }

    public void setDxDef(boolean dxDef) {
        this.dxDef = dxDef;
    }

    public double getDy() {
        return dy;
    }

    public final void setDy(double dy) {
        this.dy = dy;
        this.dyDef=true;
    }

    public boolean isDyDef() {
        return dyDef;
    }

    public void setDyDef(boolean dyDef) {
        this.dyDef = dyDef;
    }
    
    
}
