package team.test.teamFile.dao.Impl;



import team.test.teamFile.dao.UserDao;
import team.test.teamFile.pojo.User;
import team.test.teamFile.utils.JdbcUtils;

import java.util.List;

/**
 * @author chh
 */
public class UserDaoImpl implements UserDao {
    @Override
    public User login(String username, String password) {
        List<User> users = JdbcUtils.query(User.class, "select * from t_user where username=? and password=?", username, password);
        if (users == null || users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public User selectUserById(Long userId) {
        List<User> users = JdbcUtils.query(User.class, "select * from t_user where id=?", userId);
        if (users == null || users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }
}
