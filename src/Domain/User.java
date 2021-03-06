package Domain;

/**
 * @author 许达峰
 * @time 2020.5.14
 * */

public class User {
    private String name;
    private String password;
    private int coin;
    private String userPlaneAddress;
    private String userBulletAddress;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.coin=0;
    }

    public User(String name, String password, int coin) {
        this.name = name;
        this.password = password;
        this.coin = coin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public String getUserPlaneAddress() {
        return userPlaneAddress;
    }

    public void setUserPlaneAddress(String userPlaneAddress) {
        this.userPlaneAddress = userPlaneAddress;
    }

    public String getUserBulletAddress() {
        return userBulletAddress;
    }

    public void setUserBulletAddress(String userBulletAddress) {
        this.userBulletAddress = userBulletAddress;
    }
}
