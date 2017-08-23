import lipermi.handler.CallHandler;
import lipermi.net.Client;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class ClientClass extends UnicastRemoteObject implements RemoteObserver{

    static final String remoteHost = "localhost";
    static final int portWasBinded = 4455;

    protected ClientClass() throws RemoteException {
        super();
    }


    public serverInterface getServerInterface(String remoteHost, int portWasBinded) throws IOException {
        CallHandler callHandler = new CallHandler();
        Client client = new Client(remoteHost, portWasBinded, callHandler);
        serverInterface remoteObject;
        remoteObject = (serverInterface) client.getGlobal(serverInterface.class);
        System.out.println(Thread.currentThread().getName());
        return remoteObject;
    }

    public static void main(String[] args){
        serverInterface remoteService = null;
        try {
            CallHandler callHandler = new CallHandler();
            Client client = new Client(remoteHost, portWasBinded, callHandler);
            remoteService = (serverInterface) client.getGlobal(serverInterface.class);
            ClientClass client2 = new ClientClass();
            remoteService.addObserver(client2);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object observable, Object updateMsg) {
        System.out.println("got message:" + updateMsg);
    }
}

