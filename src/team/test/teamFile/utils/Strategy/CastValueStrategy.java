package team.test.teamFile.utils.Strategy;

/**
 * @author chh
 */
public interface CastValueStrategy<T> {
    Object castValue();
    void afterPropertiesSet();
    void setCastValue(CastValue castValue);
}
