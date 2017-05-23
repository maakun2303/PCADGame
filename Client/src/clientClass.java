import lipermi.handler.CallHandler;
import lipermi.net.Client;
import java.io.IOException;
import java.util.Scanner;

public class clientClass {

        static final String remoteHost = "localhost";
        static final int portWasBinded = 4455;

        private String nickname;

        public void setNickname(String nickname){ this.nickname = nickname;}

    public void startConnection(String remoteHost, int portWasBinded) throws IOException {
        CallHandler callHandler = new CallHandler();
        Client client = new Client(remoteHost, portWasBinded, callHandler);
        serverInterface remoteObject;
        remoteObject = (serverInterface) client.getGlobal(serverInterface.class);
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        clientProfile player = remoteObject.login(nickname);
        System.out.println("Welcome " + player.getNickname());
    }

    public static void main(String[] args) throws IOException {
        clientClass client = new clientClass();
        client.startConnection(remoteHost,portWasBinded);

        //System.out.println("Welcome " + player.getNickname());
        //System.out.println("Your are in " + player.getTeam() + " Team");

    }


}

