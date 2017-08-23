import java.rmi.RemoteException;

/**
 * Created by pcad on 2017/08/10.
 */
public interface RemoteObserver {
    void update(Object observable, Object updateMsg) throws RemoteException;
}
