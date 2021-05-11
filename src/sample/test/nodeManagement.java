package sample.test;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import sample.test.GNC.gNode;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class nodeManagement implements Initializable {
    @FXML
    TableView<gNode> table;

    @FXML
    private TableColumn<gNode, Integer> idColumn;
    @FXML
    private TableColumn<gNode, String> idNameColumn;
    @FXML
    private TableColumn<gNode, String> nameColumn;
    @FXML
    private TableColumn<gNode, Number> xPosColumn;
    @FXML
    private TableColumn<gNode, Number> yPosColumn;
    @FXML
    private TableColumn<gNode, Integer> nodeTypeColumn;

    private ObservableList<gNode> nodeList;

    @FXML
    private TextField txtIdName;

    @FXML
    private TextField txtParent;
    @FXML
    private TextField txtNodeName;
    @FXML
    private TextField txtOutComes;

    @FXML
    private TextField txtxPos;
    @FXML
    private TextField txtyPos;
    @FXML
    private TextField txtType;
    @FXML
    private TextField txtDef;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nodeList = FXCollections.observableArrayList( Controller.net.listNodes);

        idColumn.setCellValueFactory(new PropertyValueFactory<gNode,Integer>("id"));
        idNameColumn.setCellValueFactory(new PropertyValueFactory<gNode,String>("idName"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<gNode,String>("name"));
        //xPosColumn.setCellValueFactory(new PropertyValueFactory<gNode,Number>("xPos"));
        //yPosColumn.setCellValueFactory(new PropertyValueFactory<gNode,Number>("yPos"));
        nodeTypeColumn.setCellValueFactory(new PropertyValueFactory<gNode,Integer>("nodeType"));


        table.setOnMouseClicked( (a) -> {
            gNode g = table.getSelectionModel().getSelectedItem();

            txtIdName.setText(g.getIdName());
            txtNodeName.setText(g.getName());
            txtOutComes.setText(g.getSavedOutcomes());
            txtxPos.setText(g.getxPos() + "");
            txtyPos.setText(g.getyPos() + "");
            txtType.setText(g.getNodeType() + "");
            txtParent.setText(g.getSavedParentNodes());
            txtDef.setText(g.getSavedDefinition());
        });

        table.setItems(nodeList);
        //table.setOnMouseClicked

    }

    public void addNode(ActionEvent e){
        try{
            gNode g = new gNode(Controller.net,txtIdName.getText(),txtNodeName.getText(), gNode.converToStrings(txtOutComes.getText())
                    , Integer.parseInt(txtType.getText())
                    , Integer.parseInt(txtxPos.getText()), Integer.parseInt(txtyPos.getText()) );

            //Them parent
            if (gNode.converToStrings(txtParent.getText()) != null)
            {
                for (String ps: gNode.converToStrings(txtParent.getText())){
                    for (gNode p: Controller.net.listNodes){
                        if(ps.equals(p.getIdName())){
                            Controller.net.addArc(p,g);
                            break;
                        }
                    }
                }
            }


            //Set definition
            g.setNodeDefinition(gNode.convertToNodeDef(txtDef.getText()));

            nodeList = FXCollections.observableArrayList( Controller.net.listNodes);
            table.setItems(nodeList);
        } catch(Exception ex){
            ex.printStackTrace();
            showAlert(ex.getMessage());
        }
    }
    public void setDefinition(ActionEvent e){
        try{
            gNode r = null;
            //gNode g = table.getSelectionModel().getSelectedItem();
            for (gNode p: Controller.net.listNodes){
                if(txtIdName.getText().equals(p.getIdName())){
                    r = p;
                    break;
                }
            }

            //Set definition
            r.setNodeDefinition(gNode.convertToNodeDef(txtDef.getText()));

            nodeList = FXCollections.observableArrayList( Controller.net.listNodes);
            table.setItems(nodeList);
        } catch(Exception ex){
            ex.printStackTrace();
            showAlert(ex.getMessage());
        }
    }

    public void deleteNode(ActionEvent e){
        try {
            //gNode g = table.getSelectionModel().getSelectedItem();
            for (gNode p: Controller.net.listNodes){
                if (txtIdName.getText().equals(p.getIdName())) {
                    Controller.net.deleteNode(p);
                    break;
                }
            }

            nodeList = FXCollections.observableArrayList( Controller.net.listNodes);
            table.setItems(nodeList);
        } catch (Exception ex){
            ex.printStackTrace();
            showAlert(ex.getMessage());
        }

    }

    public void updateNode(ActionEvent e){
        deleteNode(e);
        addNode(e);
        //setDefinition(e);
//        try{
//            gNode r = null;
//            gNode g = table.getSelectionModel().getSelectedItem();
//            for (gNode p: Controller.net.listNodes){
//                if(g.getIdName().equals(p.getIdName())){
//                    r = p;
//                    break;
//                }
//            }
//
//            //Set definition
//            //r.setNodeDefinition(gNode.convertToNodeDef(txtDef.getText()));


//            nodeList = FXCollections.observableArrayList( Controller.net.listNodes);
//            table.setItems(nodeList);
//        } catch(Exception ex){
//            ex.printStackTrace();
//            showAlert(ex.getMessage());
//        }

        nodeList = FXCollections.observableArrayList( Controller.net.listNodes);
        table.setItems(nodeList);
    }

    private Optional<ButtonType> showAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }
}
