package sample.yFile_tut01;

import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.view.GraphControl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

//import toolkit.WebViewUtils;

public class SampleTut01 extends Application {

    /**
     * <h1>Step 1: Creating the View.</h1>
     * <p>
     * Introduces class {@link com.yworks.yfiles.view.GraphControl}, the central UI element for working
     * with graphs in yFiles.
     * </p>
     * <p>
     * Please see the file help.html for more details.
     * </p>
     */
        /**
         * The {@link GraphControl} to operate on, aligned in the center of
         * the enclosing {@link javafx.scene.layout.BorderPane}.
         * <p>
         * It is defined in the <code>SampleApplication.fxml</code> file
         * and constructed by the {@link javafx.fxml.FXMLLoader}.
         * When {@link #initialize()} is called, this variable is
         * already available.
         * </p>
         */
        public GraphControl graphControl;
        public WebView help;

        /**
         * Initializes the application after its user interface has been built up. Creates a node with a label and load the
         * help text.
         */
        public void initialize() {
            // Creates a node with a label
            IGraph graph = graphControl.getGraph();
            INode node1 = graph.createNode();
            graph.addLabel(node1, "1");
        }

        /**
         * Called right after stage is loaded.
         * In JavaFX, nodes don't have a width or height until the stage is displayed and the scene graph is calculated.
         * As {@link #initialize()} is called right after a node is created, but before displayed, we have to center the graph
         * later.
         */
        public void onLoaded() {
            graphControl.fitGraphBounds();
        }

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage stage) throws Exception {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample_tut01.fxml"));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
           // WebViewUtils.initHelp(help, this);

            Scene scene = new Scene(root, 1365, 768);

            // In JavaFX, nodes don't have a width or height until the stage is shown and the scene graph is calculated.
            // onLoaded does some initialization that need the correct bounds of the nodes.
            stage.setOnShown(windowEvent -> onLoaded());

            stage.setTitle("Step 1 - Creating the View");
            stage.setScene(scene);
            stage.show();
        }
    }

