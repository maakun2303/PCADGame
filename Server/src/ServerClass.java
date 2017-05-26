import lipermi.exception.LipeRMIException;
import lipermi.handler.CallHandler;
import lipermi.net.Server;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;


/**
 * Created by Filippo on 22/05/2017.
 */
public class ServerClass implements serverInterface, Serializable {

    List<ClientProfile>  loggedPlayers = new ArrayList<>();
    boolean b = false; //for team choice
    private int maxPlayers = 4;

    public ClientProfile login(String username){

        ClientProfile player = new ClientProfile();
        player.setNickname(username);

        b = (b) ? false : true;



        for(ClientProfile item: loggedPlayers){
            if(item.getNickname().equals(username)){
                System.out.println("An user used an existing nickname");
                return new ClientProfile("tryAgain");
            }
        }

        player.setTeam(b);
        loggedPlayers.add(player);

        System.out.println("Total players: " + loggedPlayers.size());

        if(loggedPlayers.size() == maxPlayers){
            System.out.println("Game is full");
        }

        return player;
    }

    public boolean removePlayer(ClientProfile player){
        boolean ok = loggedPlayers.remove(player);
        return ok;
    }

    public int showConnectedPlayers(){return loggedPlayers.size();}
    public int getMaxPlayers(){return maxPlayers;}



    public static void main(String[] args) throws IOException, LipeRMIException {

        System.out.println("Connecting...");
        CallHandler callHandler = new CallHandler();
        serverInterface interfaceImplementation;
        interfaceImplementation = new ServerClass();


        callHandler.registerGlobal(serverInterface.class,interfaceImplementation);

        Server server = new Server();
        int thePortIWantToBind = 4455;

        server.bind(thePortIWantToBind,callHandler);
        System.out.println("Binding...");


    }
}