import lipermi.handler.CallHandler;
import lipermi.net.Client;
import java.io.IOException;
import java.util.Scanner;

public class ClientClass {

    static final String remoteHost = "localhost";
    static final int portWasBinded = 4455;


    public void startConnection(String remoteHost, int portWasBinded) throws IOException {
        CallHandler callHandler = new CallHandler();
        Client client = new Client(remoteHost, portWasBinded, callHandler);
        serverInterface remoteObject;
        remoteObject = (serverInterface) client.getGlobal(serverInterface.class);
        ClientProfile player = remoteObject.login(LoginGUI.Input);
        if(player == null){
            System.out.println("Game is full, try later !");
        }
        else {
            System.out.println("Welcome " + player.getNickname());
        }
    }

}

