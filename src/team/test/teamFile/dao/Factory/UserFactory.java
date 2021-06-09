package team.test.teamFile.dao.Factory;

import team.test.teamFile.dao.Impl.UserDaoImpl;

/**
 * 用户的工厂
 * @author chh
 */
public class UserFactory implements Factory{
    @Override
    public Object getInstance() {
        return new UserDaoImpl();
    }
}
