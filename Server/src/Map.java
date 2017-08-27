import lipermi.exception.LipeRMIException;
import lipermi.handler.CallHandler;
import lipermi.net.Client;
import lipermi.net.Server;
import org.jgraph.JGraph;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;
import org.jgrapht.VertexFactory;

import javax.swing.event.SwingPropertyChangeSupport;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;

import static java.lang.Thread.sleep;

public class Map implements Serializable{

    SimpleGraph<Node, DefaultEdge> structure;

    public Node getNode(int i) {
        Iterator<Node> iter = structure.vertexSet().iterator();
        while (iter.hasNext()) {
            Node n = iter.next();
            if (n.name == i) {
                return n;
            }
        }
        return null;
    }

    public Node getNode(ClientProfile player) {
        Iterator<Node> iter = structure.vertexSet().iterator();
        while (iter.hasNext()) {
            Node n = iter.next();
            if(n.getUsers().contains(player)) return n;
        }
        return null;
    }

    public Set<Node> adjacentNodes(Node node) {
        NeighborIndex<Node, DefaultEdge> ngbr = new NeighborIndex<>(structure);
        return ngbr.neighborsOf(node);
    }

    public Map() {
        structure = new SimpleGraph<Node, DefaultEdge>(DefaultEdge.class);
        HyperCubeGraphGenerator<Node, DefaultEdge> connectedGenerator =
                new HyperCubeGraphGenerator<>(5);

        connectedGenerator.generateGraph(structure, vFactory, null);

        System.out.println(structure.vertexSet().toString());
    }

    transient VertexFactory<Node> vFactory = new VertexFactory<Node>() {
        @Override
        public Node createVertex() {
            return new Node();
        }
    };

    public void addPlayer(ClientProfile player){
        Node aux = null;
        if(player.getTeam()== EnumColor.white) aux = getNode(0);
        else aux = getNode(15);

        HashSet<ClientProfile> set = aux.getUsers();
        if(set == null) set = new HashSet<ClientProfile>();
        set.add(player);
        aux.setUsers(set);

        adjacentNodes(aux);
    }

    public void addRinnegato(ClientProfile player){
        Node aux = getNode(7);
        HashSet<ClientProfile> set = aux.getUsers();
        if(set == null) set = new HashSet<ClientProfile>();
        set.add(player);
        aux.setUsers(set);
    }

    public void moveRinnegato(ClientProfile rinnegato){
            Random rand = new Random();

            int  n = rand.nextInt(this.structure.vertexSet().size()-1) + 0;
            System.out.println(n);
            movePlayer(rinnegato,n);
    }

    public Map getMap() {
        return this;
    }

    public synchronized void movePlayer(ClientProfile player, int newPosition) {
//        System.out.println("Player stava in "+getNode(player).name +" e vuole andare in: "+newPosition.name);
        if(getNode(player) != null) {
            System.out.println("cesta!");
            getNode(player).removeUser(player);
            System.out.println("Ammo in node: "+ getNode(newPosition).getAmmo());
            getNode(newPosition).addUser(player);
            System.out.println("Ammo in tasca:" + player.getAmmo());
            //cambi stato di un oggetto del server
        }
        else { System.out.println("uncesta!"); }
        //System.out.println("Ho spostato il player su "+getNode(player).name+" ("+newPosition.name+")");
    }
}




