import org.jgraph.JGraph;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;
import org.jgrapht.VertexFactory;

import java.io.Serializable;
import java.util.*;

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
                new HyperCubeGraphGenerator<>(4);

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
        if(player.getTeam()== ClientProfile.EnumColor.white) aux = getNode(0);
        else aux = getNode(15);

        HashSet<ClientProfile> set = aux.getUsers();
        if(set == null) set = new HashSet<ClientProfile>();
        set.add(player);
        aux.setUsers(set);

        adjacentNodes(aux);
    }

    public Map getMap() {
        return this;
    }

    public void movePlayer(ClientProfile player, int newPosition) {
//        System.out.println("Player stava in "+getNode(player).name +" e vuole andare in: "+newPosition.name);
        if(getNode(player) != null) {
            System.out.println("cesta!");
            getNode(player).removeUser(player);
            getNode(newPosition).addUser(player); //ok
            //cambi stato di un oggetto del server
        }
        else { System.out.println("uncesta!"); }
        //System.out.println("Ho spostato il player su "+getNode(player).name+" ("+newPosition.name+")");
    }
}




