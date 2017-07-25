import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

/**
 * Created by Filippo on 22/05/2017.
 */
public interface serverInterface {

    ClientProfile login (String username);
    int showConnectedPlayers();
    boolean removePlayer(ClientProfile player);
    int getMaxPlayers();

    Map getMap();
    void showGui(Graph<Node, DefaultEdge> g);

}
