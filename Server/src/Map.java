import org.jgrapht.Graph;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;
import org.jgrapht.VertexFactory;
import org.jgrapht.traverse.DepthFirstIterator;

import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

public final class Map {

    static Graph<Node, DefaultEdge> completeGraph;

    // Number of vertices
    static int size = 10;

    /**
     * Main demo entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Create the graph object; it is null at this point
        completeGraph = new SimpleGraph<>(DefaultEdge.class);

        // Create the CompleteGraphGenerator object
        GnpRandomGraphGenerator<Node, DefaultEdge> completeGenerator =
                new GnpRandomGraphGenerator<Node, DefaultEdge>(10, 0.5);

        // Create the VertexFactory so the generator can create vertices
        VertexFactory<Node> vFactory = new VertexFactory<Node>() {
            private int id = 0;

            @Override
            public Node createVertex() {
                return new Node();
            }
        };

        // Use the CompleteGraphGenerator object to make completeGraph a
        // complete graph with [size] number of vertices
        completeGenerator.generateGraph(completeGraph, vFactory, null);

        // Print out the graph to be sure it's really complete
        System.out.println(completeGraph.toString());
    }
}
