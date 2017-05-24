import java.io.Serializable;

/**
 * Created by Filippo on 22/05/2017.
 */
public class ClientProfile implements Serializable {

    private String nickname;
    private EnumColor team;

    public void setNickname(String n) {
        this.nickname = n;
    }

    public void setTeam(Boolean b) {
        if (b == true)
            this.team = EnumColor.white;
        else
            this.team = EnumColor.black;
    }

    public String getNickname() {
        return this.nickname;
    }

    public EnumColor getTeam() {
        return this.team;
    }

    enum EnumColor {
        black, white
    }

    ClientProfile() {
    }

    ClientProfile(String s) {
        this.nickname = s;
    }
}


