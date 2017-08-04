import java.io.Serializable;

/**
 * Created by Filippo on 22/05/2017.
 */
public class ClientProfile implements Serializable{

    private String nickname;
    private EnumColor team;
    private Node position;

    public void setNickname(String n) {
        this.nickname = n;
    }

    public void setTeam(Boolean b) {
        if (b == true)
            this.team = EnumColor.white;
        else
            this.team = EnumColor.red;
    }

    public String getNickname() {
        return this.nickname;
    }

    public EnumColor getTeam() {
        return this.team;
    }

    public Node getPosition() {
        return position;
    }

    public void setPosition(Node position) {
        this.position = position;
    }

    enum EnumColor {
        red, white
    }

    ClientProfile() {
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof ClientProfile)) {
            return false;
        }

        ClientProfile profile = (ClientProfile) o;

        return profile.nickname.equals(nickname) &&
                profile.team == team &&
                profile.position.equals(position);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + nickname.hashCode();
        result = 31 * result + team.hashCode();
        result = 31 * result + position.hashCode();
        return result;
    }

    ClientProfile(String s) {
        this.nickname = s;
    }
}


