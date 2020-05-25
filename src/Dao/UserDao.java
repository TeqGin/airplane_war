package Dao;

import Domain.User;
import Utils.DButil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 许达峰
 * @time 2020.5.14
 * this class is to operate the database
 * and it shouldn't be static
 * */

public class UserDao {
    public static User findUserById(String id){
        User user=null;
        String sql="select * from user where account=?";
        PreparedStatement statement= DButil.getPreparedStatement(sql);
        try {
            statement.setString(1,id);
            ResultSet resultSet=statement.executeQuery();
            if (resultSet.next()){
                user=new User(resultSet.getString("account"),resultSet.getString("password"));
                user.setUserPlaneAddress(resultSet.getString("user_plane_address"));
                user.setUserBulletAddress(resultSet.getString("user_bullet_address"));
                user.setCoin(resultSet.getInt("coin"));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.closeDBResources();
        }
        return user;
    }
    public static void addUser(String account,String password){
        String sql="insert into user(account,password) values(?,?)";
        PreparedStatement statement=DButil.getPreparedStatement(sql);
        try {
            statement.setString(1,account);
            statement.setString(2,password);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.closeDBResources();
        }
    }
    public static void addUser(String account,String password,int coin){
        String sql="insert into user(account,password,coin) values(?,?,?)";
        PreparedStatement statement=DButil.getPreparedStatement(sql);
        try {
            statement.setString(1,account);
            statement.setString(2,password);
            statement.setInt(3,coin);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.closeDBResources();
        }
    }
    public static void update(User user){
        String sql="update user set coin=?,user_plane_address=?,user_bullet_address=? where account=?";
        PreparedStatement statement=DButil.getPreparedStatement(sql);
        try {
            statement.setInt(1,user.getCoin());

            statement.setString(2,user.getUserPlaneAddress());
            statement.setString(3,user.getUserBulletAddress());
            statement.setString(4,user.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DButil.closeDBResources();
        }
    }
}
