import org.jgrapht.Graph;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;
import org.jgrapht.VertexFactory;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Random;

public final class Map implements Serializable{

    //private static volatile Map instance = null ; //singleton
    DefaultDirectedGraph<Node, DefaultEdge>structure = null;


    public Map() {
        structure = new  DefaultDirectedGraph<>(DefaultEdge.class);

        // Create the CompleteGraphGenerator object
        ScaleFreeGraphGenerator<Node, DefaultEdge> connectedGenerator =
                new ScaleFreeGraphGenerator<Node, DefaultEdge>(10);

        connectedGenerator.generateGraph(structure, vFactory, null);

        //Place the players+
        //structure.

    }

    /*public Map getMap(){
        if(instance==null){
            synchronized (Map.class){
                if(instance==null){
                    instance = new Map();
                }
            }
        }
        return instance;
    }*/

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
                n.setPlayer(player);
            }
        }

    }

}


   /* public static class MyEdge extends DefaultWeightedEdge implements Serializable {
        @Override
        public String toString() {
            return String.valueOf(getWeight());
        }
    }*/

