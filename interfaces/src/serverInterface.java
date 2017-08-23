import lipermi.net.Client;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;
import java.rmi.RemoteException;

/**
 * Created by Filippo on 22/05/2017.
 */
public interface serverInterface {
    ClientProfile login (String username);
    int showConnectedPlayers();
    boolean removePlayer(ClientProfile player);
    int getMaxPlayers();

    void addObserver(RemoteObserver o) throws RemoteException;
    Map getMap();
    void movePlayer(ClientProfile player, int newPosition);
}
