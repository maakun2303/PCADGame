import lipermi.handler.CallHandler;
import lipermi.net.Client;
import java.io.IOException;


public class ClientClass implements RemoteObserver{

    static final String remoteHost = "localhost";
    static final int portWasBinded = 4455;



    public serverInterface getServerInterface(String remoteHost, int portWasBinded) throws IOException {
        CallHandler callHandler = new CallHandler();
        Client client = new Client(remoteHost, portWasBinded, callHandler);
        serverInterface remoteObject;
        remoteObject = (serverInterface) client.getGlobal(serverInterface.class);
        System.out.println(Thread.currentThread().getName());
        return remoteObject;
    }

    @Override
    public void update(Object observable, Object updateMsg) {
        System.out.println("got message:" + updateMsg);
    }
}

