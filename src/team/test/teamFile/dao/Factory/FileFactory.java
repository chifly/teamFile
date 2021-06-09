package team.test.teamFile.dao.Factory;

import team.test.teamFile.dao.Impl.FileDaoImpl;

/**
 * 文件工厂
 * @author chh
 */
public class FileFactory implements Factory{
    @Override
    public Object getInstance() {
        return new FileDaoImpl();
    }
}
