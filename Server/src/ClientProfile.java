public class ClientProfile {

    private String Nickname;
    private EnumColor Team;

    public void setNickname(String N) {this.Nickname = N;}
    public String getNickname() {
        return this.Nickname
    }
    public EnumColor getTeam(){return this.Team;}
}

public enum EnumColor{
black, white;

}

