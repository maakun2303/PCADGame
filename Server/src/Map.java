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
        Iterator<Node> iter = structure.vertexSet().iterator();
        while(iter.hasNext()){
            Node n = iter.next();
            if(n.name == randNum){
                HashSet<ClientProfile> set = positions.get(n);
                if(set == null) set = new HashSet<ClientProfile>();
                set.add(player);
                positions.put(n,set);
            }
        }
        movePlayer(player);
    }

    public Map getMap() {
        return this;
    }

    public void movePlayer(ClientProfile player) {
        //System.out.println("Dentro move player sono " + positions);
        //System.out.println(structure.containsVertex( positions.get(player).toString());
        System.out.println(structure.vertexSet());

        /*Node pos = positions.get(player);
        Set<DefaultEdge> edgeSet = this.structure.edgesOf(pos);
        Iterator<DefaultEdge> iterator = edgeSet.iterator();

        while (iterator.hasNext()){
            DefaultEdge edge = iterator.next();
            Set<Node> allNodes = new HashSet<Node>();
            if(this.structure.getEdgeTarget(edge) != pos) allNodes.add(this.structure.getEdgeTarget(edge));
            else allNodes.add(this.structure.getEdgeSource(edge));

            System.out.println(allNodes.toString());
        }*/


    }
}




