import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Node implements Serializable {
    public int name;
    public int ammo;
    static int id = 0;
    List<ClientProfile> users;

    Node() {
        name = id++;
        if(name == 0 || name == Constants.mapSize-1) ammo = 0;
        else
        {
            Random rand = new Random();
            ammo = rand.nextInt(2);
        }
        users = new CopyOnWriteArrayList<ClientProfile>();
    }

   @Override
    public String toString() {
        String aux = "";
        for(ClientProfile cp : users) {
            aux = aux + cp.getNickname() + "\n";
        }
        return aux;
   }

   public List<ClientProfile> getUsers(){
        return users;
   }

   public ClientProfile getUserIfOne() {
       if(this.users != null && this.users.size() == 1) return this.users.iterator().next();
       else return null;
   }

   public int getAmmo(){
       return ammo;
   }

   public void addUser(ClientProfile player){
       player.takeAmmo(this.getAmmo());
       users.add(player);
       this.ammo = 0;
   }

   public int getId(){
       return id;
   }

    public boolean isEmpty(){
        return users == null || users.size() == 0;
    }

    public void removeUser(ClientProfile player){ users.remove(player);}

   public void setUsers(List<ClientProfile> users){
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
        return result;
    }
    public static void resetID(){
       id = 0;
    }
}
