import com.sun.xml.internal.ws.policy.sourcemodel.ModelNode;
import lipermi.exception.LipeRMIException;
import lipermi.handler.CallHandler;
import lipermi.net.Server;
import com.google.common.collect.*;
import org.antlr.v4.runtime.misc.MultiMap;
import org.jgrapht.Graph;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;
import org.jgrapht.VertexFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class Map implements Serializable{

    SimpleGraph<Node, DefaultEdge>structure;
    BiMap<Node,HashSet<ClientProfile>> positions;

    private Node getNode(int i) {
        Iterator<Node> iter = structure.vertexSet().iterator();
        while (iter.hasNext()) {
            Node n = iter.next();
            if (n.name == i) {
                return n;
            }
        }
        return null;
    }

    public Map() {
        structure = new  SimpleGraph<>(DefaultEdge.class);
        positions =  HashBiMap.create();

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

    public void addPlayer (ClientProfile player){

        Random rand = new Random();
        int randNum = rand.nextInt(10);

        Node aux = getNode(randNum);
        HashSet<ClientProfile> set = positions.get(aux);
        if(set == null) set = new HashSet<ClientProfile>();
        set.add(player);
        positions.put(aux,set);

        movePlayer(player,aux,getNode(7));

    }

    public Map getMap() {
        return this;
    }

    public void movePlayer(ClientProfile player, Node oldPosition, Node newPosition) {
        //System.out.println("Dentro move player sono " + positions);
        //System.out.println(structure.containsVertex( positions.get(player).toString());
        System.out.println(structure.vertexSet());

        HashSet<ClientProfile> oldUsers = positions.get(oldPosition);
        oldUsers.remove(player);
        positions.forcePut(oldPosition,oldUsers);
        HashSet<ClientProfile> newUsers = positions.get(newPosition);
        if(newUsers == null) newUsers = new HashSet<ClientProfile>();
        newUsers.add(player);
        positions.put(newPosition,newUsers);
    }
}




