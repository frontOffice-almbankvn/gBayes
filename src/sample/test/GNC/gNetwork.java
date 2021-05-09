package sample.test.GNC;

import com.yworks.yfiles.graph.IGraph;
import smile.Network;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class gNetwork extends Network {
    public ArrayList<gNode> listNodes;
    public IGraph iGraph;
    public IGraph getiGraph() {
        return iGraph;
    }

    public void setiGraph(IGraph iGraph) {
        this.iGraph = iGraph;
    }



    public gNetwork() {
        super();
        this.listNodes = new ArrayList<gNode>();
        this.iGraph = sample.test.Controller.iGraph;
        this.iGraph.clear();
    }
    public gNetwork(String n) {
        super();
        this.listNodes = new ArrayList<gNode>();
        this.setName(n);
        this.iGraph = sample.test.Controller.iGraph;
        this.iGraph.clear();
    }

    public ArrayList<gNode> gNetAllNodes(){
        return this.listNodes;
    }

    public gNode getGNode(String nodeId) {
        for(gNode g: listNodes){
            if (g.getIdName().equals(nodeId)){
                return g;
            }
        }
        return null;
    }

    public gNode getGNodeByName(String nodeName){
        for(gNode g: listNodes){
            if (g.getName().equals(nodeName)){
                return g;
            }
        }
        return null;
    }

    public void addArc(gNode parentHandle, gNode childHandle) {
        super.addArc(parentHandle.getId(), childHandle.getId());
        childHandle.parentNodes.add(parentHandle);

        //Phan GUI;
        this.iGraph.createEdge( parentHandle.getiNode(), childHandle.getiNode());
    }



    public void deleteArc(gNode parentHandle, gNode childHandle) {
        super.deleteArc(parentHandle.getId(), childHandle.getId());
        childHandle.parentNodes.remove(parentHandle);

        //Phan GUI;
        this.iGraph.remove(this.iGraph.getEdge(parentHandle.getiNode(),childHandle.getiNode()));
    }

    public void deleteNode(gNode nodeHandle) {
        //Xoa bo quan he cha con, con cha
        for(gNode p : nodeHandle.getParentNodes()){
            deleteArc(p,nodeHandle);
        }
        for (gNode c: this.listNodes){
            for (gNode p: c.getParentNodes()){
                if( p.getIdName().equals(nodeHandle.getIdName())){
                    deleteArc(nodeHandle,p);
                }
            }
        }
        //Xoa GUI
        this.iGraph.remove(nodeHandle.getiNode());

        listNodes.remove(nodeHandle); //Xoa trong danh sach

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
            var rs = pstmt.executeQuery();
            gNetwork net = new gNetwork(netName);

            while (rs.next()){
                String sOutComes = rs.getString("outcomes").trim();

                System.out.println( "/" + rs.getString("nodeDef").trim() +"/");
                var snodeDef = gNode.convertToNodeDef( rs.getString("nodeDef").trim());
                String tmpParent = rs.getString("parentNode").trim();

                gNode n = new gNode(net,rs.getString("idname").trim(),rs.getString("nodeName").trim(),
                        gNode.converToStrings(sOutComes),rs.getInt("nodeType"), rs.getInt("xPos"), rs.getInt("yPos"),tmpParent);
                if (snodeDef != null)  n.setTmpNodeDefinition(snodeDef)  ;
            }

            for (gNode g: net.listNodes){
                if(g.tmpParent.length() != 0) {
                    var s = gNode.converToStrings(g.tmpParent);
                    for (String m : s){
                        for (gNode g2: net.listNodes){
                            if (g2.getIdName().equals(m) ) {
                                net.addArc(g2,g);
                                break;
                            }
                        }
                    }
                }
                g.setNodeDefinition(g.getNodeDefinition());
            }

            return net;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void printPosteriors(Network net, int nodeHandle) {
        String nodeId = net.getNodeId(nodeHandle);
        if (net.isEvidence(nodeHandle)) {
            System.out.printf("%s has evidence set (%s)\n",
                    nodeId,
                    net.getOutcomeId(nodeHandle, net.getEvidence(nodeHandle)));
        } else {
            double[] posteriors = net.getNodeValue(nodeHandle);
            for (int i = 0; i < posteriors.length; i ++) {
                System.out.printf("P(%s=%s)=%f\n",
                        nodeId,
                        net.getOutcomeId(nodeHandle, i),
                        posteriors[i]);
            }
        }
    }

    public static void printAllPosteriors(gNetwork net) {
        for (gNode g: net.listNodes){
            if (g.getNodeType() == 18){
                printPosteriors(net, g.id);
            } else if( g.getNodeType() == 8){
                printUtilityNode(net,g);
            }
        }

        System.out.println();
    }
    public static void printUtilityNode(gNetwork net, gNode  node){
        double[] expectedUtility = net.getNodeValue(node.getIdName());
        int[] utilParents = net.getValueIndexingParents(node.getIdName());

        printUtilityMatrix(net, expectedUtility ,utilParents);

    }
    public static void printUtilityMatrix(gNetwork net, double[] mtx, int[] parents){
        int dimCount = 1 + parents.length;
        int[] dimSizes = new int[dimCount];

        for (int i = 0; i < dimCount - 1; i ++) {
            dimSizes[i] = net.getOutcomeCount(parents[i]);
        }
        dimSizes[dimSizes.length - 1] = 1;
        int[] coords = new int[dimCount];
        for (int elemIdx = 0; elemIdx < mtx.length; elemIdx ++) {
            indexToCoords(elemIdx, dimSizes, coords);
            System.out.print("    Utility(");
            if (dimCount > 1)
            {
                for (int parentIdx = 0; parentIdx < parents.length; parentIdx++)
                {
                    if (parentIdx > 0) System.out.print(",");
                    int parentHandle = parents[parentIdx];
                    System.out.printf("%s=%s",
                            net.getNodeId(parentHandle),
                            net.getOutcomeId(parentHandle, coords[parentIdx]));
                }
            }

            System.out.printf(")=%f\n", mtx[elemIdx]);
        }
        System.out.println();
    }

    static void indexToCoords(int index, int[] dimSizes, int[] coords) {
        int prod = 1;
        for (int i = dimSizes.length - 1; i >= 0; i --) {
            coords[i] = (index / prod) % dimSizes[i];
            prod *= dimSizes[i];
        }
    }

}
