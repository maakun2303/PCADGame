import java.util.Random;

public class Node {
    public String name;
    public int ammo;
    static int id = 0;

    Node() {
        Random rand = new Random();
        ammo = rand.nextInt(10);
        name = "p" + id++;
    }

    @Override
    public String toString() {
        return name;
    }
}
