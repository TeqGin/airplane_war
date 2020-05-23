package Domain;

import Utils.MusicUtil;

/**
 * @author 许达峰
 * @time 2020.5.14
 * */

public class Data {
    public static User user;
    public static int width;
    public static int height;
    public static int x=450;
    public static int y=25;
    //audio track 1
    public static MusicUtil backgroundMusic;
    public static MusicUtil backgroundMusic2=new MusicUtil();
    //sleep time
    public static int speed=30;

    public static int enemyNumber=2;
    public static int nowCheckPoint=1;

    public static int score=0;
    public static int targetScore=10000;

    public static float proportion =0.5f;

    public static boolean gameRunning=true;

    public static String mapAddress="static/image/map/bg_plain.jpg";
    public static String userPlaneAddress="static/image/plane/user_plane_level1.png";
    public static String userPlaneBulletAddress="static/image/bullet/bullet_2_blue.png";

}
