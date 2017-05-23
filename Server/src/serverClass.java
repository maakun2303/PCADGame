import lipermi.exception.LipeRMIException;
import lipermi.handler.CallHandler;
import lipermi.net.Server;


import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;

/**
 * Created by Filippo on 22/05/2017.
 */
public class serverClass implements serverInterface {

    List<ClientProfile> loggedPlayers = new ArrayList<>();

    public ClientProfile login(String username) {
        ClientProfile player = new ClientProfile();
        player.setNickname(username);

        Random randomno = new Random();
        boolean b = randomno.nextBoolean();
        player.setTeam(b);


        return player;
    }

    public static void main(String[] args) throws IOException, LipeRMIException {

        System.out.println("Connecting...");
        CallHandler callHandler = new CallHandler();
        serverInterface interfaceImplementation;
        interfaceImplementation = new serverClass();


        callHandler.registerGlobal(serverInterface.class,interfaceImplementation);

        Server server = new Server();
        int thePortIWantToBind = 4455;

        server.bind(thePortIWantToBind,callHandler);
        System.out.println("Binding...");


    }
}