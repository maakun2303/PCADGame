import java.io.Serializable;
import java.util.Random;

public class Node implements Serializable {
    public String name;
    public ClientProfile player;
    public int ammo;
    static int id = 0;

    Node() {
        name = "p" + id++;
        Random rand = new Random();
        ammo = rand.nextInt(10);
        player =  new ClientProfile();
    }

    @Override
    public String toString() {
        return name;
    }

}
