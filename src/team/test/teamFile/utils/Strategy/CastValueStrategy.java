package team.test.teamFile.utils.Strategy;

/**
 * 策略类
 * @author chh
 */
public interface CastValueStrategy<T> {
    /**
     * 获得一个转化后的数据，策略方法
     * @return
     */
    Object castValue();

    /**
     * 得到一个实例化对象
     */
    void afterPropertiesSet();

    /**
     * 设置内部数据
     * @param castValue 数据源
     */
    void setCastValue(CastValue castValue);
}
