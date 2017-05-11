public class ClientProfile {
    private long ID;
    private String Nickname;

    public ClientProfile(long ID) {
        this.ID = ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setNickname(String N) {
        this.Nickname = N;
    }

    public long getID() {
        return this.ID;
    }

    public String getNickname() {
        return this.Nickname;
    }
}