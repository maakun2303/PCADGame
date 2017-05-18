import org.jgrapht.Graph;
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
        @Override
        public Node createVertex() {
            return new Node();
        }
    };
}
