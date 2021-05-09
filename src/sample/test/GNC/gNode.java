package sample.test.GNC;

import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.graph.INode;
import smile.Network;

import java.util.ArrayList;

public class gNode {
    public int id;
    public String name;
    public String idName;
    public String[] outcomes;
    public int xPos;
    public int yPos;
    public gNetwork net;
    public String tmpParent;
    public int nodeType;
    public ArrayList<gNode> parentNodes;
    public double[] nodeDefinition;


    public INode getiNode() {
        return iNode;
    }

    public void setiNode(INode iNode) {
        this.iNode = iNode;
    }

    public INode iNode;

    public int getNodeType() {
        return nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }


    public String getSavedParentNodes(){
        String kq = "";
        for(gNode g : parentNodes){
            kq += g.idName + ",";
        }

        if (kq == "") return kq;
        return kq.trim().substring(0,kq.trim().length()-1);
    }

    public double[] getNodeDefinition() {
        return nodeDefinition;
    }

    public void setNodeDefinition(double[] nodeDefinition) {
        this.net.setNodeDefinition(id,nodeDefinition);
        this.nodeDefinition = nodeDefinition;
    }
    public void setTmpNodeDefinition(double[] nodeDefinition){
        this.nodeDefinition = nodeDefinition;
    }

    public String getSavedDefinition(){
        String kq = "";
        for (double d : nodeDefinition){
            kq += d + " ";
        }
        return kq.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String[] getOutcomes() {
        return outcomes;
    }
    public String getSavedOutcomes(){
        if (outcomes == null) return "";
        String kq ="";
        for(String s: outcomes){
            kq += s +  ',';
        }
        if (kq == "") return kq;
        return   kq.trim().substring(0,kq.trim().length()-1) ;
    }

    public void setOutcomes(String[] outcomes) {
        this.outcomes = outcomes;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public ArrayList<gNode> getParentNodes() {
        return parentNodes;
    }

    public int getId() {
        return id;
    }

    public gNode(gNetwork net, String id, String name,
                 String[] outcomes, int nodeType ,int xPos, int yPos){
        this.net = net;
        this.idName = id;
        this.name= name;
        this.outcomes = outcomes;
        this.xPos = xPos;
        this.yPos = yPos;
        this.nodeType = nodeType;
        this.name = name;
        if (nodeType == Network.NodeType.CPT) {
            this.id = createCptNode(net,id,name,outcomes,xPos,yPos);
        } else {
            this.id = createNode(net,nodeType,id,name,outcomes,xPos,yPos);
        }

        this.net.listNodes.add(this);
        this.parentNodes = new ArrayList<gNode>();
        this.nodeDefinition = new double[] {};

        //Phan GUI
        this.iNode = this.net.getiGraph().createNode(new RectD(xPos, yPos,
                70, 70));
        this.net.getiGraph().addLabel(iNode,this.idName.trim());

    }
    public gNode(gNetwork net, String id, String name,
                 String[] outcomes, int nodeType ,int xPos, int yPos, String tmpParent){
        this.net = net;
        this.idName = id;
        this.name= name;
        this.outcomes = outcomes;
        this.xPos = xPos;
        this.yPos = yPos;
        this.nodeType = nodeType;
        this.name = name;
        if (nodeType == Network.NodeType.CPT) {
            this.id = createCptNode(net,id,name,outcomes,xPos,yPos);
        } else {
            this.id = createNode(net,nodeType,id,name,outcomes,xPos,yPos);
        }

        this.net.listNodes.add(this);
        this.parentNodes = new ArrayList<gNode>();
        this.nodeDefinition = new double[] {};
        this.tmpParent = tmpParent;

        //Phan GUI
        this.iNode = this.net.getiGraph().createNode(new RectD(xPos, yPos,
                70, 70));
        this.net.getiGraph().addLabel(iNode,this.idName.trim());
    }

    public static int createCptNode(
            Network net, String id, String name,
            String[] outcomes, int xPos, int yPos) {
        int handle = net.addNode(Network.NodeType.CPT, id);

        net.setNodeName(handle, name);
       // net.setNodePosition(handle, xPos, yPos, 85, 55);

        int initialOutcomeCount = net.getOutcomeCount(handle);
        for (int i = 0; i < initialOutcomeCount; i++) {
            net.setOutcomeId(handle, i, outcomes[i]);
        }

        for (int i = initialOutcomeCount; i < outcomes.length; i++) {
            net.addOutcome(handle, outcomes[i]);
        }

        return handle;
    }
    private static int createNode(
            Network net, int nodeType, String id, String name,
            String[] outcomes, int xPos, int yPos) {
        int handle = net.addNode(nodeType, id);
        net.setNodeName(handle, name);
       // net.setNodePosition(handle, xPos, yPos, 85, 55);

        if (outcomes != null) {
            int initialOutcomeCount = net.getOutcomeCount(handle);
            for (int i = 0; i < initialOutcomeCount; i ++) {
                net.setOutcomeId(handle, i, outcomes[i]);
            }
            for (int i = initialOutcomeCount; i < outcomes.length; i ++) {
                net.addOutcome(handle, outcomes[i]);
            }
        }
        return handle;
    }
    public static double[] convertToNodeDef(String s){
        if ( s.length() == 0) return null;
        var aS = s.split(" ");
        double[] kq = new double[aS.length];
        for (int i = 0; i < aS.length; i ++){
            kq[i] = Double.parseDouble(aS[i]);
        }
        return kq;
    }
    public static String[] converToStrings(String s){
        if (s.length() == 0) return null;
        return s.split(",");
    }
}
