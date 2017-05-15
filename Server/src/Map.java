import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;
import org.jgrapht.VertexFactory;

public final class Map {

    Graph<Node, DefaultEdge> structure;

    public Map() {
        structure = new SimpleGraph<>(DefaultEdge.class);

        // Create the CompleteGraphGenerator object
        ScaleFreeGraphGenerator<Node, DefaultEdge> connectedGenerator =
                new ScaleFreeGraphGenerator<Node, DefaultEdge>(20);

        connectedGenerator.generateGraph(structure, vFactory, null);

        // Create the VertexFactory so the generator can create vertices

        // Use the CompleteGraphGenerator object to make mappa a
        // complete graph with [size] number of vertices
    }

    VertexFactory<Node> vFactory = new VertexFactory<Node>() {
        private int id = 0;

        @Override
        public Node createVertex() {
            return new Node();
        }
    };

    public static void main(String[] args) {
        // Create the graph object; it is null at this point
        Map mappa = new Map();

        // Print out the graph to be sure it's really complete
        System.out.println(mappa.structure.toString());
    }
}
