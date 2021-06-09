package team.test.teamFile.dao.Factory;

/**
 * 抽象工厂，把数据层全部隐藏起来
 * @author chh
 */
public interface Factory {
    /**
     * 返回一个实例
     * @return Object
     */
    Object getInstance();
}
