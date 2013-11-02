/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plegatfem;

/**
 *
 * @author jmb2
 */
public class NodeForce {
    
    private String nodeId;
    private double fx,fy,mz;

    public NodeForce(String nodeId, double fx, double fy, double mz) {
        this.nodeId = nodeId;
        this.fx = fx;
        this.fy = fy;
        this.mz = mz;
    }

    public NodeForce(int nodeIdInt, double fx, double fy, double mz) {
        this(String.valueOf(nodeIdInt),fx,fy,mz);
    }

    public double getFx() {
        return fx;
    }

    public void setFx(double fx) {
        this.fx = fx;
    }

    public double getFy() {
        return fy;
    }

    public void setFy(double fy) {
        this.fy = fy;
    }

    public double getMz() {
        return mz;
    }

    public void setMz(double mz) {
        this.mz = mz;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
    
    
    
}
