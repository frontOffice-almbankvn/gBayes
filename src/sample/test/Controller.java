package sample.test;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import sample.test.GNC.*;
import smile.Network;
//import sample.test.GNC.gNetwork;
//import sample.test.GNC.gNode;.GNC.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TreeView<String> treeView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        gNetwork net = new gNetwork();
        net.setName("Tutorial01");


//        int e = gNode.createCptNode(net,
//                "Economy", "State of the economy",
//                new String[] {"Up","Flat","Down"},
//                160, 40);
        gNode e = new gNode(net,"Economy","State of the economy",new String[] {"Up","Flat","Down"}, Network.NodeType.CPT,160,40);
        gNode s = new gNode(net,"Success","Success of the venture",new String[] {"Success","Failure"}, Network.NodeType.CPT,60,40);
        gNode f = new gNode(net,"Forecast","Expert forecast",new String[] {"Good","Moderate","Poor"}, Network.NodeType.CPT,110,140);
        net.addArc(e,s);
        net.addArc(s,f);
        net.addArc(e,f);

        

        double[] economyDef = {
                0.2, // P(Economy=U)
                0.7, // P(Economy=F)
                0.1  // P(Economy=D)
        };
        e.setNodeDefinition(economyDef);

        double[] successDef = new double[] {
                0.3, // P(Success=S|Economy=U)
                0.7, // P(Success=F|Economy=U)
                0.2, // P(Success=S|Economy=F)
                0.8, // P(Success=F|Economy=F)
                0.1, // P(Success=S|Economy=D)
                0.9  // P(Success=F|Economy=D)
        };
        s.setNodeDefinition(successDef);

        double[] forecastDef = new double[] {
                0.70, // P(Forecast=G|Success=S,Economy=U)
                0.29, // P(Forecast=M|Success=S,Economy=U)
                0.01, // P(Forecast=P|Success=S,Economy=U)
                0.65, // P(Forecast=G|Success=S,Economy=F)
                0.30, // P(Forecast=M|Success=S,Economy=F)
                0.05, // P(Forecast=P|Success=S,Economy=F)
                0.60, // P(Forecast=G|Success=S,Economy=D)
                0.30, // P(Forecast=M|Success=S,Economy=D)
                0.10, // P(Forecast=P|Success=S,Economy=D)
                0.15, // P(Forecast=G|Success=F,Economy=U)
                0.30, // P(Forecast=M|Success=F,Economy=U)
                0.55, // P(Forecast=P|Success=F,Economy=U)
                0.10, // P(Forecast=G|Success=F,Economy=F)
                0.30, // P(Forecast=M|Success=F,Economy=F)
                0.60, // P(Forecast=P|Success=F,Economy=F)
                0.05, // P(Forecast=G|Success=F,Economy=D)
                0.25, // P(Forecast=G|Success=F,Economy=D)
                0.70  // P(Forecast=G|Success=F,Economy=D)
        };
        f.setNodeDefinition(forecastDef);

        net.saveToDb(demoGUI.con);

        TreeItem<String> root = new TreeItem<String>();
        TreeItem<String> netWork = new TreeItem<String>( "network");
        TreeItem<String> iNode = new TreeItem<String>( net.getNodeName("Economy"));

        //sample.getChildren()

        netWork.getChildren().add(iNode);
        root.getChildren().add(netWork);


        root.setExpanded(true);
        netWork.setExpanded(true);
        iNode.setExpanded(true);

        treeView.setRoot(root);
        treeView.setShowRoot(true);

        treeView.getSelectionModel().selectedItemProperty().addListener( (observable, o,n) -> {
            System.out.println(n.getValue());
        });

    }
}
