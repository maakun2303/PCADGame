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
        if(name == 0 || name == Constants.mapSize-1) ammo = 0;
        else
        {
            Random rand = new Random();
            ammo = rand.nextInt(10);
        }
        users = new HashSet<>();
    }

   @Override
    public String toString() {
        String aux = "";
        for(ClientProfile cp : users) {
            aux = aux + cp.getNickname() + "\n";
        }
        return aux;
   }

   public HashSet<ClientProfile> getUsers(){
        return users;
   }

   public int getAmmo(){
       return ammo;
   }

   public void addUser(ClientProfile player){
       player.takeAmmo(this.getAmmo());
       users.add(player);
       this.ammo = 0;
   }

    public void removeUser(ClientProfile player){ System.out.println(users.toString()); users.remove(player); System.out.println(users.toString());}

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
    public static void resetID(){
       id = 0;
    }
}
