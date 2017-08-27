import javax.swing.event.SwingPropertyChangeSupport;
import java.io.Serializable;

public class ClientProfile implements Serializable{

    private String nickname;
    private EnumColor team;
    private int ammo;

    public void setNickname(String n) {
        this.nickname = n;
    }

    public void setTeam(Boolean b) {
        if (b == true)
            this.team = EnumColor.white;
        else
            this.team = EnumColor.red;
    }

    public void setTeam(EnumColor color){
        this.team = color;
    }

    public String getNickname() {
        return this.nickname;
    }

    public EnumColor getTeam() {
        return this.team;
    }

    ClientProfile() {
        this.ammo = 5;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof ClientProfile)) {
            return false;
        }

        ClientProfile profile = (ClientProfile) o;

        return profile.nickname.equals(nickname) &&
                profile.team == team;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + nickname.hashCode();
        result = 31 * result + team.hashCode();
        return result;
    }

    ClientProfile(String s) {
        this.nickname = s;
        this.ammo = 5;
    }

    ClientProfile(String s,EnumColor team) {
        this.nickname = s;
        this.team = team;
        this.ammo = 5;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo){
        this.ammo = ammo;
    }

    public void takeAmmo(int newAmmo){
            System.out.println("Ammo prima: " + ammo);
            ammo = ammo + newAmmo;
            System.out.println("Ammo dopo: " + ammo);
    }
}

enum EnumColor {
    red, white, blue;
}



