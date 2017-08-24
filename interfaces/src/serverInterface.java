import lipermi.net.Client;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Filippo on 22/05/2017.
 */
public interface serverInterface extends Remote{
    ClientProfile login (String username) throws RemoteException;
    int showConnectedPlayers() throws RemoteException;
    boolean removePlayer(ClientProfile player) throws RemoteException;
    int getMaxPlayers() throws RemoteException;

    void addObserver(RemoteObserver o) throws RemoteException;
    Map getMap() throws RemoteException;
    void movePlayer(ClientProfile player, int newPosition) throws RemoteException;
}
