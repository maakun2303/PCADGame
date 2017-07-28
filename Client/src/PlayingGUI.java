import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraphView;
import com.mxgraph.view.mxStylesheet;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Created by msnsk on 2017/05/23.
 */
public class PlayingGUI extends JFrame{
    private JPanel panel1;
    private JLabel label1;
    private ClientProfile player;


    public PlayingGUI(ClientProfile player) {
        this.player = player;

        setTitle("PlayingGUI");
        //setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        //setVisible(true);

        ClientClass client = new ClientClass();
        serverInterface remoteObject = null;
        try {
            remoteObject = client.getServerInterface(client.remoteHost, client.portWasBinded);

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        System.out.println("CiaoClient0");
        Map m = remoteObject.getMap();
        showMap(m);
    }

    public void showMap(Map m) {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JGraphXAdapter<Node, DefaultEdge> graphAdapter =
                new JGraphXAdapter<Node, DefaultEdge>(m.structure);

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

        Iterator<Node> iter = m.structure.vertexSet().iterator();
        while(iter.hasNext()){
            Node n = iter.next();
            if(n.getPlayer().getTeam() == ClientProfile.EnumColor.white){
                Object cell = graphAdapter.getVertexToCellMap().get(n);
                graphAdapter.setCellStyles(mxConstants.STYLE_FILLCOLOR,"#FFFFFF",new Object[] {cell});
            }
            if(n.getPlayer().getTeam() == ClientProfile.EnumColor.red){
                Object cell = graphAdapter.getVertexToCellMap().get(n);
                graphAdapter.setCellStyles(mxConstants.STYLE_FILLCOLOR,"#FF0000",new Object[] {cell});
            }
        }

        mxStylesheet edgeStyle = new mxStylesheet();
        edgeStyle.setDefaultEdgeStyle(style);
        graphAdapter.setStylesheet(edgeStyle);
        graphAdapter.setCellsSelectable(false);
        graphAdapter.setCellsEditable(false);
        gracom.setConnectable(false);


        gravie.setScale(1);
        //Adding panel for padding
        JPanel p =new JPanel();

        mxIGraphLayout layout = new mxHierarchicalLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        p.add(gracom);

        add(p);
        setSize(600, 400);


//        setLocationByPlatform(true);
        setVisible(true);


//////Forse da spostare altrove///////
        gracom.getGraphControl().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Object cell = gracom.getCellAt(e.getX(), e.getY());
                //mxCell cell =(mxCell) gracom.getCellAt(e.getX(), e.getY());
                System.out.println("Mouse click in graph component");

                if (cell != null) {
                    System.out.println("cell=" + graphAdapter.getLabel(cell));
                    //cell.setStyle("STYLE_FILLCOLOR=#FFFFFF");
                    graphAdapter.setCellStyles(mxConstants.STYLE_FILLCOLOR,"#FFFFFF",new Object[] {cell});
                }
            }
        });
        /////////////
    }

}
