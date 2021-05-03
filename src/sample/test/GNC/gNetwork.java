package sample.test.GNC;

import smile.Network;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class gNetwork extends Network {
    public ArrayList<gNode> listNodes;

    public gNetwork() {
        super();
        this.listNodes = new ArrayList<gNode>();
    }
    public gNetwork(String n) {
        super();
        this.listNodes = new ArrayList<gNode>();
        this.setName(n);
    }


    public void addArc(gNode parentHandle, gNode childHandle) {
        super.addArc(parentHandle.getId(), childHandle.getId());
        childHandle.parentNodes.add(parentHandle);
    }


    public void deleteArc(gNode parentHandle, gNode childHandle) {
        super.deleteArc(parentHandle.getId(), childHandle.getId());
        childHandle.parentNodes.remove(parentHandle);
    }

    public void deleteNode(gNode nodeHandle) {
        listNodes.remove(nodeHandle);
        super.deleteNode(nodeHandle.getId());
    }

    public void changeEvidenceAndUpdate(gNode g, String outcomeId){
        if( outcomeId == null){
            this.clearAllEvidence();
        } else {
            this.setEvidence(g.getId(),outcomeId);
        }
        this.updateBeliefs();
    }

    public double[] getPosteriors(gNode g){
        if (this.isEvidence(g.getId())) return  null;
        return this.getNodeValue(g.getId());
    }

    public void updateNetwork(){
        this.updateBeliefs();
    }

    public void saveToDb(Connection con) {
        System.out.println(this.getName());
        try{

            PreparedStatement pstmt = con.prepareStatement("{call dbo.setNetWork (?) }");
            pstmt.setString(1,this.getName());
            var rs = pstmt.execute();
            System.out.println(rs);
            for (gNode g : this.listNodes){
                pstmt = con.prepareStatement("{call dbo.setNode (?,?,?,?,?,?,?,?,?,?)}");
                pstmt.setInt(1,g.getId());
                pstmt.setString(2,g.getIdName());
                pstmt.setString(3,g.getName());
                pstmt.setString(4,g.getSavedOutcomes());
                pstmt.setInt (5,g.getxPos());
                pstmt.setInt(6,g.getyPos());
                pstmt.setInt(7,g.getNodeType());
                pstmt.setString(8, g.getSavedDefinition());
                pstmt.setString(9,this.getName());
                pstmt.setString(10,g.getSavedParentNodes());
                rs = pstmt.execute();
            }
//            return true;
        } catch(Exception e){
            e.printStackTrace();
//            return false;
        }
    }

    public static gNetwork loadFromDb(String netName,Connection con){
        try {
            PreparedStatement pstmt = con.prepareStatement("{call dbo.getNode (?) }");
            pstmt.setString(1,netName);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
