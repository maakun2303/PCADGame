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
        Set <Node> adjSet = ngbr.neighborsOf(node);
        /*Iterator adjIt = adjSet.iterator();
        while(adjIt.hasNext()){
            Node aux = (Node) adjIt.next();
            if(aux.isColliding()) adjSet.remove(aux);
        }*/
        return adjSet;
    }

    public Map() {
        structure = new SimpleGraph<Node, DefaultEdge>(DefaultEdge.class);
        HyperCubeGraphGenerator<Node, DefaultEdge> connectedGenerator =
                new HyperCubeGraphGenerator<>(Constants.hypercubeSeed);

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
        else aux = getNode(Constants.mapSize-1);

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

    public HashSet<ClientProfile> getPlayers(Node n){
        return n.getUsers();
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

    public Set<Node> getNodes(){
        return structure.vertexSet();
    }

    public void resetPlayerPosition(ClientProfile player){
        int newPosition = -1;
        if(player.getTeam()== EnumColor.white) newPosition = 0;
        else newPosition = Constants.mapSize-1;

        getNode(player).removeUser(player);
        getNode(newPosition).addUser(player);
        HashSet<ClientProfile> set = getNode(newPosition).getUsers();
        if(set == null) set = new HashSet<ClientProfile>();
        set.add(player);
        getNode(newPosition).setUsers(set);
    }

    public ClientProfile getPlayerIfOne(Node n){
        return n.getUserIfOne();
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




