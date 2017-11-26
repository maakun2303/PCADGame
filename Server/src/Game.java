import javax.swing.*;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Created by Filippo on 22/05/2017.
 */
public class Game extends Observable implements serverInterface {

    static List<ClientProfile> loggedPlayers = new CopyOnWriteArrayList<ClientProfile>();
    boolean b = false; //for team choice
    private int maxPlayers = 4;
    private Map gameMap;
    private static int moveNumber = 0;
    private ClientProfile turn;
    private ClientProfile rinnegato;
    private GameTimer gameTimer;
    static int disconnectionCount = 1;
    private MatchResult lastMatchInfo;

    public MatchResult getLastMatchInfo(){
        return lastMatchInfo;
    }

    public void resetGame(){
        deleteObservers();
        if(disconnectionCount == maxPlayers){
            Node.resetID();
            rinnegato = new ClientProfile("Rinnegato",EnumColor.blue);
            turn = new ClientProfile();
            moveNumber = 0;
        loggedPlayers = new CopyOnWriteArrayList<>();
        gameTimer = new GameTimer();
        gameMap = new Map();
        gameMap.addRinnegato(rinnegato);
        disconnectionCount = 1;}
        disconnectionCount++;
    }

    public int getTeamAmmo(EnumColor team) throws RemoteException{
        int result = 0;
        Iterator<ClientProfile> it = loggedPlayers.iterator();
        while(it.hasNext()) {
            ClientProfile aux = it.next();
            if(aux.getTeam() == team) result = result+aux.getAmmo();
        }
        return result;
    }

    public int getEnemyTeamAmmo(EnumColor team) throws RemoteException{
        return (team == EnumColor.white)? getTeamAmmo(EnumColor.red) : getTeamAmmo(EnumColor.white);
    }

    public MatchResult fight(ClientProfile player1, ClientProfile player2){
        MatchResult result = new MatchResult();
        Random rand = new Random();
        int d1,d2;
        do {
            d1 = rand.nextInt(6) + 1;
            d2 = rand.nextInt(6) + 1;
        }while(d1==d2);

        if(player1.getTeam()==EnumColor.blue) d1 = 7;
        if(player2.getTeam()==EnumColor.blue) d2 = 7;
        if(d1>d2) {
            result.setWinner(player1);
            result.setLoser(player2);
            result.setWinDice(d1);
            result.setLoseDice(d2);
            if(player2.hasAmmo()) { player2.decreseAmmo(); player1.increseAmmo(); }
            else { gameMap.resetPlayerPosition(player2); player2.setAmmo(3); player1.killBonus(); }
        }
        else if(d1<d2){
            result.setWinner(player2);
            result.setLoser(player1);
            result.setWinDice(d2);
            result.setLoseDice(d1);
            if(player1.hasAmmo()) { player1.decreseAmmo(); player2.increseAmmo(); }
            else { gameMap.resetPlayerPosition(player1); player1.setAmmo(3); player2.killBonus(); }
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

    public void movePlayer(String username, int newPosition) throws RemoteException{
        ClientProfile player = new ClientProfile();
        for (ClientProfile item : loggedPlayers) {
            if (item.getNickname().equals(username)) {
                player = item;
            }
        }
        ClientProfile rival = gameMap.getNode(newPosition).getUserIfOne();
        if((!gameMap.getNode(newPosition).isEmpty()&&player.getTeam()!=rival.getTeam())){
            lastMatchInfo = fight(player,rival);
        }
        else lastMatchInfo = null;

        if(lastMatchInfo!=null && player.getNickname() == lastMatchInfo.getLoser() && !player.hasAmmo()) {player.killBonus(); rival.killBonus(); newPosition = 0;}
        if(lastMatchInfo!=null && rival.getNickname() == lastMatchInfo.getLoser() && !rival.hasAmmo()) { rival.killBonus(); player.killBonus(); gameMap.movePlayer(rival,0); }
        gameMap.movePlayer(player, newPosition);
        moveNumber++;
        turn = loggedPlayers.get(moveNumber%loggedPlayers.size());
        if(moveNumber%loggedPlayers.size() == 0) { gameMap.moveRinnegato(rinnegato); }
        setChanged();
        notifyObservers(gameMap);
    }

    public int getPlayerAmmo(String username) throws RemoteException{
        ClientProfile player = new ClientProfile();
        for (ClientProfile item : loggedPlayers) {
            if (item.getNickname().equals(username)) {
                player = item;
            }
        }
        return player.getAmmo();
    }

    public ClientProfile login(String username) throws RemoteException{

        for (ClientProfile item : loggedPlayers) {
            if (item.getNickname().equals(username)) {
                return new ClientProfile("");
            }
        }

        ClientProfile player = new ClientProfile();
        player.setNickname(username);

        b = !b;

        player.setTeam(b);
        if(loggedPlayers.isEmpty()) turn = player;
        loggedPlayers.add(player);

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
    }

    @Override
    public void deleteObserver(RemoteObserver o) throws RemoteException {
        WrappedObserver mo = new WrappedObserver(o);
        deleteObserver(mo);
    }

    public Map getMap() throws RemoteException{
        return gameMap;
    }

    public GameTimer getGameTimer() throws RemoteException{
        return gameTimer;
    }

    public Game() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gameTimer = new GameTimer();
                rinnegato = new ClientProfile("Rinnegato");
                rinnegato.setTeam(EnumColor.blue);
                gameMap = new Map();
                gameMap.addRinnegato(rinnegato);
                System.out.println(gameMap.toString());
            }
        });

    }


    public static void main(String[] args) throws IOException {
        try {
            Registry rmiRegistry = LocateRegistry.createRegistry(4456);
            serverInterface rmiService = (serverInterface) UnicastRemoteObject.exportObject(new Game(), 4456);
            rmiRegistry.bind("RmiService", rmiService);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
