import lipermi.exception.LipeRMIException;
import lipermi.handler.CallHandler;
import lipermi.net.Server;

import javax.swing.*;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.AllPermission;
import java.util.*;

import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Created by Filippo on 22/05/2017.
 */
public class ServerClass extends Observable implements serverInterface {

    static List<ClientProfile> loggedPlayers = new CopyOnWriteArrayList<ClientProfile>(); //concorrente ?
    boolean b = false; //for team choice
    private int maxPlayers = 2;
    private Map gameMap;
    private static int moveNumber = 0;
    private ClientProfile turn;
    private ClientProfile rinnegato;

    public int GetTeamAmmo(EnumColor team){
        int result = 0;
        Iterator<ClientProfile> it = loggedPlayers.iterator();
        while(it.hasNext()) {
            ClientProfile aux = it.next();
            if(aux.getTeam() == team) result = result+aux.getAmmo();
        }
        return result;
    }

    public ClientProfile getTurn() {
        return turn;
    }

    private class WrappedObserver implements Observer, Serializable {
        private static final long serialVersionUID = 1L;
        private RemoteObserver ro = null;

        public WrappedObserver(RemoteObserver ro) {
            this.ro = ro;
        }

        @Override
        public void update(Observable o, Object arg) {
            try {
                ro.update(o.toString(), arg);
            } catch (RemoteException e) {
                System.out.println("Remote exception removing observer:"+this);
                o.deleteObserver(this);
            }
        }
    }


    public void movePlayer(ClientProfile player, int newPosition) throws RemoteException{
        gameMap.movePlayer(player, newPosition);

        moveNumber++;
        turn = loggedPlayers.get(moveNumber%loggedPlayers.size());
        if(moveNumber%loggedPlayers.size() == 0) gameMap.moveRinnegato(rinnegato);
        setChanged();
        notifyObservers(getMap());
        System.out.println(gameMap.toString());
    }

    public ClientProfile login(String username) throws RemoteException{

        for (ClientProfile item : loggedPlayers) {
            if (item.getNickname().equals(username)) {
                System.out.println("An user used an existing nickname");
                return new ClientProfile("");
            }
        }

        ClientProfile player = new ClientProfile();
        player.setNickname(username);

        b = (b) ? false : true;


        player.setTeam(b);
        if(loggedPlayers.isEmpty()) turn = player;
        loggedPlayers.add(player);

        System.out.println("Total players: " + loggedPlayers.size());

        if (loggedPlayers.size() == maxPlayers) {
            System.out.println("Game is full");

        }

        gameMap.addPlayer(player);
        setChanged();
        notifyObservers();
        return player;
    }

    public boolean removePlayer(ClientProfile player) throws RemoteException{
        boolean ok = loggedPlayers.remove(player);
        return ok;
    }

    public int showConnectedPlayers() throws RemoteException {
        return loggedPlayers.size();
    }

    public int getMaxPlayers() throws RemoteException{
        return maxPlayers;
    }

    @Override
    public void addObserver(RemoteObserver o) throws RemoteException {
        WrappedObserver mo = new WrappedObserver(o);
        addObserver(mo);
        System.out.println("Added observer:" + mo);
    }

    @Override
    public void deleteObserver(RemoteObserver o) throws RemoteException {
        WrappedObserver mo = new WrappedObserver(o);
        deleteObserver(mo);
        System.out.println("Delete observer:" + mo);
    }

    public Map getMap() throws RemoteException{
        return gameMap;
    }

    public ServerClass() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                rinnegato = new ClientProfile("DelziKiller");
                rinnegato.setTeam(EnumColor.blue);
                gameMap = new Map();
                gameMap.addRinnegato(rinnegato);
                System.out.println(gameMap.toString());
                //thread.start();
            }
        });

    }


    Thread thread = new Thread() {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    // ignore
                }
                setChanged();
                notifyObservers(new Map());
            }
        };
    };

    public static void main(String[] args) throws IOException, LipeRMIException {
        /*
        System.out.println("Connecting...");

        CallHandler callHandler = new CallHandler();
        serverInterface interfaceImplementation;
        interfaceImplementation = new ServerClass();
        callHandler.registerGlobal(serverInterface.class,interfaceImplementation);

        Server server = new Server();
        int thePortIWantToBind = 4455;
        server.bind(thePortIWantToBind,callHandler);
        System.out.println("Binding...");*/

        try {
            Registry rmiRegistry = LocateRegistry.createRegistry(4456);
            serverInterface rmiService = (serverInterface) UnicastRemoteObject.exportObject(new ServerClass(), 4456);
            rmiRegistry.bind("RmiService", rmiService);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
