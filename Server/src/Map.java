import org.jgrapht.alg.NeighborIndex;
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

    public Set<Node> adjacentNodes(Node node) {
        NeighborIndex<Node, DefaultEdge> ngbr = new NeighborIndex<>(structure);
        return ngbr.neighborsOf(node);
    }

    public Map() {
        structure = new  SimpleGraph<>(DefaultEdge.class);

        ScaleFreeGraphGenerator<Node, DefaultEdge> connectedGenerator =
                new ScaleFreeGraphGenerator<Node, DefaultEdge>(10);

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

        Random rand = new Random();
        int randNum = rand.nextInt(10);

        Node aux = getNode(randNum);
        HashSet<ClientProfile> set = aux.getUsers();
        if(set == null) set = new HashSet<ClientProfile>();
        set.add(player);
        aux.setUsers(set);

        movePlayer(player,aux,getNode(7));
        adjacentNodes(getNode(7));
    }

    public Map getMap() {
        return this;
    }

    public void movePlayer(ClientProfile player, Node oldPosition, Node newPosition) {
        oldPosition.removeUser(player);
        newPosition.addUser(player);
    }
}




