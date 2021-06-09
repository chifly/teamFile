package team.test.teamFile.dao;

import team.test.teamFile.pojo.User;

import java.sql.SQLException;

/**
 * 用户数据访问
 * @author chh
 */
public interface UserDao {
    /**
     * 用户登录验证
     * @param username 用户名
     * @param password 密码
     * @return User 一个用户
     * @throws SQLException sql异常
     */
    User login(String username, String password) throws SQLException;

    /**
     * 通过用户id查询文件
     * @param userId 用户id
     * @return User 一个用户
     * @throws SQLException sql异常
     */
    User selectUserById(Long userId) throws SQLException;
}
