import java.io.Serializable;

/**
 * Created by Filippo on 22/05/2017.
 */
public class ClientProfile implements Serializable{

    private String nickname;
    private EnumColor team;

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
    }
}


