import lipermi.handler.CallHandler;
import lipermi.net.Client;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Filippo on 22/05/2017.
 */
public class clientClass {


    public static void startConnection(String remoteHost, int portWasBinded) throws IOException {
        CallHandler callHandler = new CallHandler();
        Client client = new Client(remoteHost, portWasBinded, callHandler);
        serverInterface remoteObject;
        remoteObject = (serverInterface) client.getGlobal(serverInterface.class);
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter your nickname: ");
        String nickname = reader.next();
        clientProfile player = remoteObject.login(nickname);
    }

    public static void main(String[] args) throws IOException {
        String remoteHost = "localhost";
        int portWasBinded = 4455;
        startConnection(remoteHost,portWasBinded);

        //System.out.println("Welcome " + player.getNickname());
        //System.out.println("Your are in " + player.getTeam() + " Team");

    }


}

