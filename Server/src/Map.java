import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraphView;
import com.mxgraph.view.mxStylesheet;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;
import org.jgrapht.VertexFactory;

import javax.swing.*;
import java.io.Serializable;
import java.util.Hashtable;

public final class Map implements Serializable{

    private static volatile Map instance = null ; //singleton
    Graph<Node, DefaultEdge>structure = null;


    public Map() {
        structure = new SimpleGraph<>(DefaultEdge.class);

        // Create the CompleteGraphGenerator object
        ScaleFreeGraphGenerator<Node, DefaultEdge> connectedGenerator =
                new ScaleFreeGraphGenerator<Node, DefaultEdge>(10);

        connectedGenerator.generateGraph(structure, vFactory, null);

    }

    public Map getMap(){
        if(instance==null){
            synchronized (Map.class){
                if(instance==null){
                    instance = new Map();
                }
            }
        }
        return instance;
    }

    VertexFactory<Node> vFactory = new VertexFactory<Node>() {
        @Override
        public Node createVertex() {
            return new Node();
        }
    };

    public static void showGui(Graph<Node, DefaultEdge> g) {

        JFrame frame = new JFrame("DemoGraph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JGraphXAdapter<Node, DefaultEdge> graphAdapter =
                new JGraphXAdapter<Node, DefaultEdge>(g);

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


        gravie.setScale(1);
        //Adding panel for padding
        JPanel p =new JPanel();
        p.add(gracom);
        frame.add(p);
        frame.setSize(600, 400);



        mxIGraphLayout layout = new mxFastOrganicLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        frame.setLocationByPlatform(true);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Map m = new Map();
                showGui(m.structure);

            }
        });
    }


   /* public static class MyEdge extends DefaultWeightedEdge implements Serializable {
        @Override
        public String toString() {
            return String.valueOf(getWeight());
        }
    }*/

}
