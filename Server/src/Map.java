import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;
import org.jgrapht.VertexFactory;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;


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
        return adjSet;
    }

    public Map() {
        structure = new SimpleGraph<Node, DefaultEdge>(DefaultEdge.class);
        HyperCubeGraphGenerator<Node, DefaultEdge> connectedGenerator =
                new HyperCubeGraphGenerator<>(Constants.hypercubeSeed);

        connectedGenerator.generateGraph(structure, vFactory, null);

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

        List<ClientProfile> set = aux.getUsers();
        if(set == null) set = new CopyOnWriteArrayList<ClientProfile>();
        set.add(player);
        aux.setUsers(set);

        adjacentNodes(aux);
    }

    public void addRinnegato(ClientProfile player){
        Node aux = getNode(7);
        List<ClientProfile> set = aux.getUsers();
        if(set == null) set = new CopyOnWriteArrayList<ClientProfile>();
        set.add(player);
        aux.setUsers(set);
    }

    public void moveRinnegato(ClientProfile rinnegato){
            Random rand = new Random();

            int  n = rand.nextInt(this.structure.vertexSet().size()-2) + 1;
            movePlayer(rinnegato,n);
    }

    public void resetPlayerPosition(ClientProfile player){
        int newPosition = -1;
        if(player.getTeam()== EnumColor.white) newPosition = 0;
        else newPosition = Constants.mapSize-1;

        getNode(player).removeUser(player);
        getNode(newPosition).addUser(player);
        List<ClientProfile> set = getNode(newPosition).getUsers();
        if(set == null) set = new CopyOnWriteArrayList<ClientProfile>();
        set.add(player);
        getNode(newPosition).setUsers(set);
    }

    public synchronized void movePlayer(ClientProfile player, int newPosition) {
        if(getNode(player) != null) {
            getNode(player).removeUser(player);
            getNode(newPosition).addUser(player);
        }
    }
}