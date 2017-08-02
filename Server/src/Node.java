import java.io.Serializable;
import java.util.Random;

public class Node implements Serializable {
    public int name;
    private ClientProfile player;
    public int ammo;
    static int id = 0;

    Node() {
        name = id++;
        Random rand = new Random();
        ammo = rand.nextInt(10);
        player =  new ClientProfile();
    }

    public void setPlayer(ClientProfile player){
        this.player = player;
    }


   @Override
    public String toString() {
       //return String.valueOf(name);
       return player.getNickname() + " " + player.getTeam();
   }

    public ClientProfile getPlayer() {
        return player;
    }
}
