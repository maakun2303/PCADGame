import com.sun.xml.internal.ws.policy.sourcemodel.ModelNode;
import lipermi.exception.LipeRMIException;
import lipermi.handler.CallHandler;
import lipermi.net.Server;
import org.jgrapht.Graph;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;
import org.jgrapht.VertexFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Map implements Serializable{

    SimpleGraph<Node, DefaultEdge>structure = null;


    public Map() {
        structure = new  SimpleGraph<>(DefaultEdge.class);

        ScaleFreeGraphGenerator<Node, DefaultEdge> connectedGenerator =
                new ScaleFreeGraphGenerator<Node, DefaultEdge>(10);

        connectedGenerator.generateGraph(structure, vFactory, null);


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
                n.setPlayer(player);
                player.setPosition(n);
            }
        }
        movePlayer(player);
    }

    public Map getMap() {
        return this;
    }

    public void movePlayer(ClientProfile player) {
        Set<DefaultEdge> edgeSet = this.structure.edgesOf(player.getPosition());
        Iterator<DefaultEdge> iterator = edgeSet.iterator();

        while (iterator.hasNext()){
            DefaultEdge edge = iterator.next();
            Set<Node> allNodes = new HashSet<Node>();
            if(this.structure.getEdgeTarget(edge) != player.getPosition()) allNodes.add(this.structure.getEdgeTarget(edge));
            else allNodes.add(this.structure.getEdgeSource(edge));

            System.out.println(allNodes.toString());
        }


    }
}




