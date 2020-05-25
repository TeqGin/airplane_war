package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * this class is use for connect mysql
 * */

//数据库资源管理工具
public class DButil {
    
    private static Connection conn;
    private static Statement stmt;
    private static PreparedStatement pstmt;
    
    static {
        //加载驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    //实例化数据库连接conn
    public static Connection getConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/airplane_war?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false", "root", "60870736a");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }
    
    //实例化SQL执行句柄stmt
    public static Statement getStatement() {
        Connection conn = getConnection();
        try {
            if(conn != null) {
                stmt = conn.createStatement();
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return stmt;
    }
    
    //实例化SQL执行句柄pstmt
    public static PreparedStatement getPreparedStatement(String sql) {
        Connection conn = getConnection();
        try {
            if(conn != null) {
                pstmt = conn.prepareStatement(sql);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return pstmt;
    }
    
    //关闭数据库连接资源
    public static void closeDBResources() {
        try {
            if(pstmt != null && !pstmt.isClosed()) {
                pstmt.close();
            }
            if(stmt != null && !stmt.isClosed()) {
                //如果stmt不为空，并且还未关闭
                stmt.close();
            }
            if(conn != null && !conn.isClosed()) {
                conn.close();
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
    }
}


