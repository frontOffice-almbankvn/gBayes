package sample.yFile_tut05;

import com.yworks.yfiles.geometry.InsetsD;
import com.yworks.yfiles.geometry.PointD;
import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.geometry.SizeD;
import com.yworks.yfiles.graph.*;
import com.yworks.yfiles.graph.labelmodels.*;
import com.yworks.yfiles.graph.portlocationmodels.FreeNodePortLocationModel;
import com.yworks.yfiles.graph.styles.DefaultLabelStyle;
import com.yworks.yfiles.graph.styles.IArrow;
import com.yworks.yfiles.graph.styles.PolylineEdgeStyle;
import com.yworks.yfiles.graph.styles.ShinyPlateNodeStyle;
import com.yworks.yfiles.view.GraphControl;
import com.yworks.yfiles.view.Pen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class SampleTut05 extends Application {

    public GraphControl graphControl;
    public WebView help;

    /**
     * Initializes the application after its user interface has been built up.
     */
    public void initialize() {
        //////////// New in this Sample /////////////////////
        // Specifies the default label model parameters for node and edge label. Label model parameters control the actual
        // label placement, as well as the available placement candidates when moving the label interactively.
        setDefaultLabelParameters();
        /////////////////////////////////////////////////////

        // Specifies a default style for each type of graph element. These styles are applied to new graph elements if no
        // style is explicitly specified during element creation.
        setDefaultStyles();

        // Creates a sample graph and introduces all important graph elements present in yFiles: nodes, edges, bends, ports
        // and labels.
        populateGraph();
    }

    /**
     * Called right after stage is loaded.
     * In JavaFX, nodes don't have a width or height until the stage is displayed and the scene graph is calculated.
     * As {@link #initialize()} is called right after a node is created, but before displayed, we have to update
     * the view port later.
     */
    public void onLoaded() {
        // Updates the content rectangle that encloses the graph and adjust the zoom level to show the whole graph in the
        // view.
        updateViewPort();
    }

    //////////// New in this Sample /////////////////////
    /**
     * Sets up default label model parameters for graph elements.
     * Label model parameters control the actual label placement, as well as the available
     * placement candidates when moving the label interactively.
     */
    private void setDefaultLabelParameters() {
        IGraph graph = getGraph();
        // For node labels, the default is a label position at the node center
        // Let's keep the default.  Here is how to set it manually
        graph.getNodeDefaults().getLabelDefaults().setLayoutParameter(InteriorLabelModel.CENTER);

        // For edge labels, the default is a label that is rotated to match the associated edge segment
        // We'll start by creating a model that is similar to the default:
        EdgeSegmentLabelModel edgeSegmentLabelModel = new EdgeSegmentLabelModel();
        // However, by default, the rotated label is centered on the edge path.
        // Let's move the label off of the path:
        edgeSegmentLabelModel.setDistance(10);
        // Finally, we can set this label model as the default for edge labels using a location at the center of the first segment segment
        ILabelModelParameter labelModelParameter = edgeSegmentLabelModel.createParameterFromSource(0, 0.5, EdgeSides.RIGHT_OF_EDGE);
        graph.getEdgeDefaults().getLabelDefaults().setLayoutParameter(labelModelParameter);

        // For port labels, the default is a label that is placed outside the owner node
        graph.getNodeDefaults().getPortDefaults().getLabelDefaults().setLayoutParameter(new InsideOutsidePortLabelModel().createOutsideParameter());
    }
    /////////////////////////////////////////////////////

    /**
     * Sets up default styles for graph elements.
     * <p>
     * Default styles apply only to elements created after the default style has been set,
     * so typically, you'd set these as early as possible in your application.
     * </p>
     */
    private void setDefaultStyles() {
        IGraph graph = getGraph();
        // Sets the default style for nodes
        // Creates a nice ShinyPlateNodeStyle instance, using an orange color.
        // Sets this style as the default for all nodes that don't have another
        // style assigned explicitly
        ShinyPlateNodeStyle defaultNodeStyle = new ShinyPlateNodeStyle();
        defaultNodeStyle.setPaint(Color.ORANGE);
        graph.getNodeDefaults().setStyle(defaultNodeStyle);

        // Sets the default style for edges:
        // Creates an edge style that will apply a gray pen with thickness 1
        // to the entire line using PolyLineEdgeStyle,
        // which draws a polyline determined by the edge's control points (bends)
        PolylineEdgeStyle defaultEdgeStyle = new PolylineEdgeStyle();
        defaultEdgeStyle.setPen(Pen.getGray());

        // Sets the source and target arrows on the edge style instance
        // (Actually: no source arrow)
        // Note that IEdgeStyle itself does not have these properties
        // Also note that by default there are no arrows
        defaultEdgeStyle.setTargetArrow(IArrow.DEFAULT);

        // Sets the defined edge style as the default for all edges that don't have
        // another style assigned explicitly:
        graph.getEdgeDefaults().setStyle(defaultEdgeStyle);

        // Sets the default style for labels
        // Creates a label style with the label text color set to dark red
        DefaultLabelStyle defaultLabelStyle = new DefaultLabelStyle();
        defaultLabelStyle.setFont(Font.font(12));
        defaultLabelStyle.setTextPaint(Color.DARKRED);

        // Sets the defined style as the default for both edge and node labels:
        graph.getEdgeDefaults().getLabelDefaults().setStyle(defaultLabelStyle);
        graph.getNodeDefaults().getLabelDefaults().setStyle(defaultLabelStyle);

        // Sets the default node size explicitly to 40x40
        graph.getNodeDefaults().setSize(new SizeD(40, 40));
    }

    /**
     * Creates a sample graph and introduces all important graph elements present in yFiles. Additionally, this method
     * specifies the label placement for some specific labels.
     */
    private void populateGraph() {
        IGraph graph = getGraph();

        //////////// Sample node creation ///////////////////
        // Creates two nodes with the default node size
        // The location is specified for the center
        INode node1 = graph.createNode(new PointD(50, 50));
        INode node2 = graph.createNode(new PointD(150, 50));
        // Creates a third node with a different size of 80x40
        // In this case, the location of (360,380) describes the upper left
        // corner of the node bounds
        INode node3 = graph.createNode(new RectD(360, 380, 80, 40));
        /////////////////////////////////////////////////////

        //////////// Sample edge creation ///////////////////
        // Creates some edges between the nodes
        IEdge edge1 = graph.createEdge(node1, node2);
        IEdge edge2 = graph.createEdge(node2, node3);
        /////////////////////////////////////////////////////

        //////////// Using Bends ////////////////////////////
        // Creates the first bend for edge2 at (400, 50)
        IBend bend1 = graph.addBend(edge2, new PointD(400, 50));
        /////////////////////////////////////////////////////

        //////////// Using Ports ////////////////////////////
        // Actually, edges connect "ports", not nodes directly.
        // If necessary, you can manually create ports at nodes
        // and let the edges connect to these.
        // Creates a port in the center of the node layout
        IPort port1AtNode1 = graph.addPort(node1, FreeNodePortLocationModel.NODE_CENTER_ANCHORED);

        // Creates a port at the middle of the left border
        // Note to use absolute locations in world coordinates when placing ports using PointD.
        // The method obtains a model parameter that best matches the given port location.
        IPort port1AtNode3 = graph.addPort(node3, new PointD(node3.getLayout().getX(), node3.getLayout().getCenter().getY()));

        // Creates an edge that connects these specific ports
        IEdge edgeAtPorts = graph.createEdge(port1AtNode1, port1AtNode3);
        /////////////////////////////////////////////////////

        //////////// Sample label creation ///////////////////
        // Adds labels to several graph elements
        graph.addLabel(node1, "N 1");
        graph.addLabel(node2, "N 2");
        ILabel n3Label = graph.addLabel(node3, "N 3");
        graph.addLabel(edgeAtPorts, "Edge at Ports");
        graph.addLabel(port1AtNode3, "Port at Node");
        /////////////////////////////////////////////////////

        ///////////////// New in this Sample /////////////////
        // Override default label placement
        // For our "special" label, we use a model that describes discrete positions
        // outside the node bounds
        ExteriorLabelModel exteriorLabelModel = new ExteriorLabelModel();

        // We use some extra insets from the label to the node bounds
        exteriorLabelModel.setInsets(new InsetsD(5));

        // We assign this label a specific symbolic position out of the eight possible
        // external locations valid for ExteriorLabelModel
        graph.setLabelLayoutParameter(n3Label, exteriorLabelModel.createParameter(ExteriorLabelModel.Position.SOUTH));
        /////////////////////////////////////////////////////
    }

    /**
     * Updates the content rectangle to encompass all existing graph elements.
     * <p>
     * If you create your graph elements programmatically, the content rectangle
     * (i.e. the rectangle in <b>world coordinates</b> that encloses the graph)
     * is <b>not</b> updated automatically to enclose these elements. Typically,
     * this manifests in wrong/missing scrollbars, incorrect
     * {@link com.yworks.yfiles.view.GraphOverviewControl} behavior and the like.
     * </p>
     * <p>
     * This method demonstrates several ways to update the content rectangle, with
     * or without adjusting the zoom level to show the whole graph in the view.
     * </p>
     * <p>
     * Note that updating the content rectangle only does not change the current
     * view port (i.e. the world coordinate rectangle that corresponds to the
     * currently visible area in view coordinates).
     * </p>
     * <p>
     * Try to uncomment the example code in this method and observe the different
     * effects.
     * </p>
     * <p>
     * The following steps in this tutorial assume you just called
     * <code>graphControl.fitGraphBounds();</code> in this method.
     * </p>
     */
    void updateViewPort() {
        // Uncomment the following line to update the content rectangle
        // to include all graph elements
        // This should result in correct scrolling behaviour:

        // graphControl.updateContentRect();

        // Additionally, we can also set the zoom level so that the
        // content rectangle fits exactly into the view port area:
        // Uncomment this line in addition to UpdateContentRect:
        // Note that this changes the zoom level (i.e. the graph elements will look smaller)

        // graphControl.fitContent();

        // The sequence above is equivalent to just calling:
        graphControl.fitGraphBounds();
    }

    /**
     * Action handler for zoom in button action.
     *
     */
    public void handleZoomInAction() {
        graphControl.setZoom(graphControl.getZoom() * 1.25);
    }

    /**
     * Action handler for zoom out button action.
     *
     */
    public void handleZoomOutAction() {
        graphControl.setZoom(graphControl.getZoom() * 0.8);
    }

    /**
     * Action handler for reset zoom button action.
     */
    public void handleResetZoomAction() {
        graphControl.setZoom(1);
    }

    /**
     * Action handler for fit to content button action.
     *
     */
    public void handleFitToContentAction() {
        graphControl.fitGraphBounds();
    }

    /**
     * Convenience method to retrieve the graph.
     */
    public IGraph getGraph() {
        return graphControl.getGraph();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SampleApplication.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        //WebViewUtils.initHelp(help, this);

        Scene scene = new Scene(root, 1365, 768);

        // In JavaFX, nodes don't have a width or height until the stage is shown and the scene graph is calculated.
        // onLoaded does some initialization that need the correct bounds of the nodes.
        stage.setOnShown(windowEvent -> onLoaded());

        stage.setTitle("Step 5 - Placing Labels");
        stage.setScene(scene);
        stage.show();
    }
}
