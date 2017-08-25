import com.mxgraph.layout.*;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxStylesheet;
import lipermi.handler.CallHandler;
import lipermi.net.Client;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Observable;
import java.util.Set;

/**
 * Created by msnsk on 2017/05/23.
 */
public class PlayingGUI extends UnicastRemoteObject implements RemoteObserver{
    private JFrame frame1;
    private JPanel panel1;
    private JLabel label1;
    private ClientProfile player;
    private Map map;
    private JGraphXAdapter<Node, DefaultEdge> graphAdapter;
    private mxGraphComponent gracom;
    private mxIGraphLayout layout;


    public PlayingGUI(ClientProfile player) throws RemoteException {
        super();

        try {
            serverInterface remoteService = (serverInterface) Naming.lookup("//"+Constants.remoteHost+":"+Constants.portWasBinded+"/RmiService");
            remoteService.addObserver(this);
            map = remoteService.getMap();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        frame1 = new JFrame();
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.player = player;

        frame1.setTitle("PlayingGUI " + "- Player: " + player.getNickname());
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(400, 300);
        frame1.setLocationRelativeTo(null);

        graphAdapter = new JGraphXAdapter<Node, DefaultEdge>(map.structure);
        gracom = new mxGraphComponent(graphAdapter);
        layout = new mxHierarchicalLayout(graphAdapter);
        showMap();
    }

    public void setAdjacentNodesColor(JGraphXAdapter<Node, DefaultEdge> graphAdapter) throws RemoteException {

        try {
            serverInterface remoteService = (serverInterface) Naming.lookup("//"+Constants.remoteHost+":"+Constants.portWasBinded+"/RmiService");
            ClientProfile turn = remoteService.getTurn();
            if(!turn.equals(player)) return;
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Set<Node> nodes = map.adjacentNodes(map.getNode(player));
        Iterator<Node> iter = nodes.iterator();
        while(iter.hasNext()){
            Node n = iter.next();
            Object cell = graphAdapter.getVertexToCellMap().get(n);
            graphAdapter.setCellStyles(mxConstants.STYLE_STROKECOLOR, "#00FF00", new Object[]{cell});
            graphAdapter.setCellStyles(mxConstants.STYLE_STROKEWIDTH, "3", new Object[]{cell});
        }
    }

    public void move() throws RemoteException {
        try {
            serverInterface remoteService = (serverInterface) Naming.lookup("//"+Constants.remoteHost+":"+Constants.portWasBinded+"/RmiService");
            ClientProfile turn = remoteService.getTurn();
            if(!turn.equals(player)) return;
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Set<Node> adjnodes = map.adjacentNodes(map.getNode(player));

        gracom.getGraphControl().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Object cell = gracom.getCellAt(e.getX(), e.getY());
                System.out.println("Mouse click in graph component");

                if (cell != null && adjnodes.contains(graphAdapter.getCellToVertexMap().get(cell))) {
                    System.out.println("cell=" + graphAdapter.getLabel(cell));

                    ClientClass client = null;
                    try {
                        client = new ClientClass();
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                    serverInterface remoteService = null;
                    try {
                        try {
                            remoteService = (serverInterface) Naming.lookup("//"+Constants.remoteHost+":"+Constants.portWasBinded+"/RmiService");
                        } catch (NotBoundException e1) {
                            e1.printStackTrace();
                        }

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        remoteService.movePlayer(player, graphAdapter.getCellToVertexMap().get(cell).name);
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }

                }
            }
        });
    }


    public void setNodeColor(JGraphXAdapter<Node, DefaultEdge> graphAdapter) throws RemoteException {
        Iterator<Node> iter = map.structure.vertexSet().iterator();
        while(iter.hasNext()){
            boolean someoneWhite = false;
            boolean someoneRed = false;
            Node n = iter.next();
            Object cell = graphAdapter.getVertexToCellMap().get(n);
            mxRectangle rc = new mxRectangle(0,0,45,45);
            graphAdapter.resizeCells(new Object[]{cell},new mxRectangle[]{rc});
            if(n.getUsers() != null) {
                Iterator<ClientProfile> iterProf = n.getUsers().iterator();
                while (iterProf.hasNext()) {
                    ClientProfile cp = iterProf.next();
                    if (cp.getTeam() == ClientProfile.EnumColor.white) {
                        //cell = graphAdapter.getVertexToCellMap().get(n);
                        if(someoneRed == false) graphAdapter.setCellStyles(mxConstants.STYLE_FILLCOLOR, "#FFFFFF", new Object[]{cell});
                        else graphAdapter.setCellStyles(mxConstants.STYLE_FILLCOLOR, "#FFC0CB", new Object[]{cell});
                        someoneWhite = true;
                    }
                    if (cp.getTeam() == ClientProfile.EnumColor.red) {
                        //cell = graphAdapter.getVertexToCellMap().get(n);
                        if(someoneWhite == false) graphAdapter.setCellStyles(mxConstants.STYLE_FILLCOLOR, "#FF0000", new Object[]{cell});
                        else graphAdapter.setCellStyles(mxConstants.STYLE_FILLCOLOR, "#FFC0CB", new Object[]{cell});
                        someoneRed = true;
                    }
                }
            }
        }
        setAdjacentNodesColor(graphAdapter);
    }

    public void showMap() throws RemoteException {


        mxRectangle rec = new mxRectangle(10.0,10.0,400.0,400.0);
        graphAdapter.setMaximumGraphBounds(rec);
        graphAdapter.setCellsSelectable(false);
        graphAdapter.setCellsEditable(false);
        graphAdapter.setCollapseToPreferredSize(true);

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
        style.put(mxConstants.STYLE_ALIGN,"center");

        setNodeColor(graphAdapter);

        mxStylesheet edgeStyle = new mxStylesheet();
        edgeStyle.setDefaultEdgeStyle(style);
        graphAdapter.setStylesheet(edgeStyle);

        //mxIGraphLayout layout = new mxHierarchicalLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        gracom.setConnectable(false);
        panel1.setLayout(new BorderLayout());
        panel1.add(gracom,BorderLayout.CENTER);

        frame1.add(panel1);

        move();

        frame1.pack();
        //setLocationByPlatform(true);
        frame1.setVisible(true);
    }

    @Override
    public void update(Object observable, Object updateMsg) throws RemoteException {;

        Runnable init = new Runnable() {
            public void run() {
                map = (Map) updateMsg;
                graphAdapter= new JGraphXAdapter<Node, DefaultEdge> (map.structure);
                gracom = new mxGraphComponent(graphAdapter);
                layout = new mxHierarchicalLayout(graphAdapter);
                panel1.removeAll();
                try {
                    showMap();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
    }
     };
      SwingUtilities.invokeLater(init);
    }
}
