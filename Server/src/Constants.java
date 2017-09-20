import static java.lang.Math.pow;

public final class Constants{
    public static final String remoteHost = "localhost"; //192.168.1.125
    public static final int portWasBinded = 4456;
    public static final int hypercubeSeed = 3;
    public static final int mapSize = (int) pow(2,hypercubeSeed);
    public static final int matchMin = 0;
    public static final int matchSec = 10;
}
