package Service;

import Dao.UserDao;
import Domain.User;

/**
 * @author 许达峰
 * @time 2020.5.14
 * */

public class UserService {
    public static boolean isExist(String account,String password){
        User user= UserDao.findUserById(account);
        if (user!=null&&user.getPassword().equals(password)){
            return true;
        }else {
            return false;
        }
    }
    public static boolean isLegal(String account,String password,String passwordToConfirm){
        if ("".equals(account)||"".equals(password)){
            return false;
        }else if (!password.equals(passwordToConfirm)){
            return false;
        }else if (UserDao.findUserById(account)!=null){
            return false;
        }else {
            return true;
        }
    }
    public static void addUser(String account,String password){
        UserDao.addUser(account,password);
    }
    public static User findUserById(String id){
        return UserDao.findUserById(id);
    }
}
