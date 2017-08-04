import java.io.Serializable;
import java.util.Random;

public class Node implements Serializable {
    public int name;
    public int ammo;
    static int id = 0;

    Node() {
        name = id++;
        Random rand = new Random();
        ammo = rand.nextInt(10);
    }

   @Override
    public String toString() {
       return String.valueOf(name);
   }

   @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Node)) {
            return false;
        }
        Node node = (Node) o;
        return node.name == name && node.ammo == ammo;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name;
        result = 31 * result + ammo;
        return result;
    }
}
