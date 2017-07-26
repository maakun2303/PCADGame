import org.jgrapht.Graph;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;
import org.jgrapht.VertexFactory;

import java.io.Serializable;

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

    transient VertexFactory<Node> vFactory = new VertexFactory<Node>() {
        @Override
        public Node createVertex() {
            return new Node();
        }
    };

}


   /* public static class MyEdge extends DefaultWeightedEdge implements Serializable {
        @Override
        public String toString() {
            return String.valueOf(getWeight());
        }
    }*/

