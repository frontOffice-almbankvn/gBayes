package sample.test.GNC;

import smile.Network;

import java.util.ArrayList;

public class gNode {
    private int id;
    private String name;
    private String idName;
    private String[] outcomes;
    private int xPos;
    private int yPos;
    private gNetwork net;

    public int getNodeType() {
        return nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    private int nodeType;
    public ArrayList<gNode> parentNodes;
    public double[] nodeDefinition;

    public String getSavedParentNodes(){
        String kq = "";
        for(gNode g : parentNodes){
            kq += g.idName + " ";
        }
        return kq.trim();
    }

    public double[] getNodeDefinition() {
        return nodeDefinition;
    }

    public void setNodeDefinition(double[] nodeDefinition) {
        this.net.setNodeDefinition(id,nodeDefinition);
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
        String kq ="";
        for(String s: outcomes){
            kq += s +  ' ';
        }
        return kq.trim();
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
        this.id = createCptNode(net,id,name,outcomes,xPos,yPos);
        this.net.listNodes.add(this);
        this.parentNodes = new ArrayList<gNode>();
        this.nodeDefinition = new double[] {};
    }

    public static int createCptNode(
            Network net, String id, String name,
            String[] outcomes, int xPos, int yPos) {
        int handle = net.addNode(Network.NodeType.CPT, id);

        net.setNodeName(handle, name);
        net.setNodePosition(handle, xPos, yPos, 85, 55);

        int initialOutcomeCount = net.getOutcomeCount(handle);
        for (int i = 0; i < initialOutcomeCount; i++) {
            net.setOutcomeId(handle, i, outcomes[i]);
        }

        for (int i = initialOutcomeCount; i < outcomes.length; i++) {
            net.addOutcome(handle, outcomes[i]);
        }

        return handle;
    }
}
