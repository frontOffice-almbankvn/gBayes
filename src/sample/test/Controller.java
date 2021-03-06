package sample.test;

import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.graph.*;
import com.yworks.yfiles.view.GraphControl;
import com.yworks.yfiles.view.input.GraphEditorInputMode;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import sample.test.GNC.*;
import smile.Network;
//import sample.test.GNC.gNetwork;
//import sample.test.GNC.gNode;.GNC.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TreeView<String> treeView;
    public static gNetwork net;
    public GraphControl graphControl;
    public static IGraph iGraph;
    public GraphEditorInputMode graphEditorInputMode;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iGraph = graphControl.getGraph();
//        gNetwork net = new gNetwork();
//        net.setName("Tutorial01");
//
//        //net.deleteArc();
//        //net.deleteNode();
////        int e = gNode.createCptNode(net,
////                "Economy", "State of the economy",
////                new String[] {"Up","Flat","Down"},
////                160, 40);
//
//        gNode e = new gNode(net,"Economy","State of the economy",new String[] {"Up","Flat","Down"}, Network.NodeType.CPT,160,40);
//        gNode s = new gNode(net,"Success","Success of the venture",new String[] {"Success","Failure"}, Network.NodeType.CPT,60,40);
//        gNode f = new gNode(net,"Forecast","Expert forecast",new String[] {"Good","Moderate","Poor"}, Network.NodeType.CPT,110,140);
//        gNode i = new gNode(net,"Invest", "Investment decision", new String[]{ "Invest", "DoNotInvest" }, Network.NodeType.DECISION,160, 240);
//        gNode g = new gNode(net,"Gain", "Financial gain", null, Network.NodeType.UTILITY,160, 240);
//
//        net.addArc(e,s);
//        net.addArc(s,f);
//        net.addArc(e,f);
//        net.addArc(i,g);
//        net.addArc(s,g);
//
//
//
//        double[] economyDef = {
//                0.2, // P(Economy=U)
//                0.7, // P(Economy=F)
//                0.1  // P(Economy=D)
//        };
//        e.setNodeDefinition(economyDef);
//
//        double[] successDef = new double[] {
//                0.3, // P(Success=S|Economy=U)
//                0.7, // P(Success=F|Economy=U)
//                0.2, // P(Success=S|Economy=F)
//                0.8, // P(Success=F|Economy=F)
//                0.1, // P(Success=S|Economy=D)
//                0.9  // P(Success=F|Economy=D)
//        };
//        s.setNodeDefinition(successDef);
//
//        double[] forecastDef = new double[] {
//                0.70, // P(Forecast=G|Success=S,Economy=U)
//                0.29, // P(Forecast=M|Success=S,Economy=U)
//                0.01, // P(Forecast=P|Success=S,Economy=U)
//                0.65, // P(Forecast=G|Success=S,Economy=F)
//                0.30, // P(Forecast=M|Success=S,Economy=F)
//                0.05, // P(Forecast=P|Success=S,Economy=F)
//                0.60, // P(Forecast=G|Success=S,Economy=D)
//                0.30, // P(Forecast=M|Success=S,Economy=D)
//                0.10, // P(Forecast=P|Success=S,Economy=D)
//                0.15, // P(Forecast=G|Success=F,Economy=U)
//                0.30, // P(Forecast=M|Success=F,Economy=U)
//                0.55, // P(Forecast=P|Success=F,Economy=U)
//                0.10, // P(Forecast=G|Success=F,Economy=F)
//                0.30, // P(Forecast=M|Success=F,Economy=F)
//                0.60, // P(Forecast=P|Success=F,Economy=F)
//                0.05, // P(Forecast=G|Success=F,Economy=D)
//                0.25, // P(Forecast=G|Success=F,Economy=D)
//                0.70  // P(Forecast=G|Success=F,Economy=D)
//        };
//        f.setNodeDefinition(forecastDef);
//
//        double[] gainDefinition = new double[] {
//                10000, // Utility(Invest=I, Success=S)
//                -5000, // Utility(Invest=I, Success=F)
//                500,   // Utility(Invest=D, Success=S)
//                500    // Utility(Invest=D, Success=F)
//        };
//
//        g.setNodeDefinition(gainDefinition);
        net  = gNetwork.loadFromDb("Tutorial01",demoGUI.con);
        //net.setiGraph(iGraph);
        net.writeFile("tutorial1_loaded.xdsl");

//        //net.saveToDb(demoGUI.con);
//
//        TreeItem<String> root = new TreeItem<String>();
//        TreeItem<String> netWork = new TreeItem<String>( "network");
//        TreeItem<String> iNode = new TreeItem<String>( net.getNodeName("Economy"));
//
//        //sample.getChildren()
//
//        netWork.getChildren().add(iNode);
//        root.getChildren().add(netWork);
//
//
//        root.setExpanded(true);
//        netWork.setExpanded(true);
//        iNode.setExpanded(true);
//
//        treeView.setRoot(root);
//        treeView.setShowRoot(false);

        setTreeView(treeView,net);

        graphEditorInputMode = new GraphEditorInputMode();
        graphEditorInputMode.setContextMenuItems(GraphItemTypes.NODE);

        treeView.getSelectionModel().selectedItemProperty().addListener( (observable, o,n) -> {
            System.out.println(n.getValue());
            var t = n;

            String id = t.getValue();
            gNode g = net.getGNode(id);
            //System.out.println(g.getNodeType());
            if ((g != null ) && (g.getNodeType() ==18)){
                System.out.println(g.getNodeType());
                setEvidenceScreen(g);
            }
        });



    }

    public static void setTreeView(TreeView<String> tv, gNetwork net){
        TreeItem<String> root = new TreeItem<String>();
        TreeItem<String> netWork = new TreeItem<String>( net.getName());

        for (gNode g : net.listNodes){
            TreeItem<String> iNode = new TreeItem<String>( g.getIdName());
            iNode.setExpanded(true);
            netWork.getChildren().add(iNode);
        }
        root.getChildren().add(netWork);
        netWork.setExpanded(true);

        tv.setRoot(root);
        tv.setShowRoot(false);
    }

    public void saveTheNet(){
        net.saveToDb(demoGUI.con);
        System.out.printf("Saved the net");
    }

    public void showManagementNode() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("nodeManagement.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void setEvidenceScreen(gNode g){
        Label label = new Label("Set evidence for: " + g.getName() );

        VBox layoutV = new VBox(10);
        layoutV.getChildren().add(label);
        for (String s: g.getOutcomes()){
            Button btn = new Button(s);
            btn.setOnAction( e -> {
                g.net.changeEvidenceAndUpdate(g,s);
                gNetwork.printAllPosteriors(net);
            });
            layoutV.getChildren().add(btn);
        }

        Scene scene = new Scene(layoutV,300,200);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void resetEvidence(){
        net.clearAllEvidence();
    }

    public void showResult() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("result.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void saveNetWork(){
        net.saveToDb(demoGUI.con);
    }

    public void creatNewNet(){
        Dialog<String> dialog = new Dialog<>();

        dialog.setTitle("Create new Network");
        dialog.setHeaderText("Network");

        ButtonType createNetType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createNetType,ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,150,10,10));

        TextField netName = new TextField();
        netName.setPromptText("Name of the network");

        grid.add(new Label("Netname"),0,0);
        grid.add(netName,1,0);

        Node createNet = dialog.getDialogPane().lookupButton(createNetType);
        createNet.setDisable(true);

        netName.textProperty().addListener((observable,o,n) ->{
            createNet.setDisable(n.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter( d -> {
            if(d == createNetType){
                return netName.getText();
            }
            return null;
        });

        var kq = dialog.showAndWait();
        kq.ifPresent( a -> {
            System.out.println(a);
            net = new gNetwork(a);
            setTreeView(treeView,net);
        });
    }

}
