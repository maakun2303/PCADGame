import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;

public class Node implements Serializable {
    public int name;
    public int ammo;
    static int id = 0;
    HashSet<ClientProfile> users;

    Node() {
        name = id++;
        Random rand = new Random();
        ammo = rand.nextInt(10);
        users = new HashSet<ClientProfile>();
    }

   @Override
    public String toString() {
       return String.valueOf(name);
   }

   public HashSet<ClientProfile> getUsers(){
        return users;
   }

   public void addUser(ClientProfile player){
       users.add(player);
   }

    public void removeUser(ClientProfile player){
        users.remove(player);
    }

   public void setUsers(HashSet<ClientProfile> users){
       this.users = users;
   }

   @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Node)) {
            return false;
        }
        Node node = (Node) o;
        return node.name == name && node.ammo == ammo && node.users.equals(users);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name;
        result = 31 * result + ammo;
        //result = result + users.hashCode();
        return result;
    }
}
