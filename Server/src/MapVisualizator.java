import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxRectangle;
import org.jgrapht.Graph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableDirectedWeightedGraph;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.view.*;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.graph.SimpleGraph;
import com.mxgraph.view.mxStylesheet;

import java.awt.*;
import java.util.HashMap;
import java.util.Hashtable;

public class MapVisualizator {


    private static void createAndShowGui() {
        JFrame frame = new JFrame("DemoGraph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Graph<Node, DefaultEdge> g = buildGraph();
        JGraphXAdapter<Node, DefaultEdge> graphAdapter =
                new JGraphXAdapter<Node, DefaultEdge>(g);

        //graphAdapter.setMinimumGraphSize(new mxRectangle(0,0,700,500)); //non va
        mxGraphComponent gracom = new mxGraphComponent(graphAdapter);
        mxGraphView gravie = graphAdapter.getView();

        Hashtable<String, Object> style = new Hashtable<String, Object>();

        style.put(mxConstants.STYLE_ROUNDED, true);
        style.put(mxConstants.STYLE_ORTHOGONAL, false);
        style.put(mxConstants.STYLE_EDGE, "elbowEdgeStyle");
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        style.put(mxConstants.STYLE_STROKECOLOR, "#6482B9"); // default is #6482B9
        style.put(mxConstants.STYLE_FONTCOLOR, "#446299");
        style.put(mxConstants.STYLE_NOLABEL, "1");

        mxStylesheet edgeStyle = new mxStylesheet();
        edgeStyle.setDefaultEdgeStyle(style);
        graphAdapter.setStylesheet(edgeStyle);


        gravie.setScale(2);
        gravie.setGraphBounds(new mxRectangle(20, 20, 1600, 900));
        frame.add(gracom);
        frame.setSize(1920, 1080);

        mxIGraphLayout layout = new mxFastOrganicLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
    }


    public static class MyEdge extends DefaultWeightedEdge {
        @Override
        public String toString() {
            return String.valueOf(getWeight());
        }
    }

    public static Graph<Node, DefaultEdge> buildGraph() {
        Map g = new Map();
        return g.structure;
    }
}
