package team.test.teamFile.utils.Strategy;

/**
 * 其他数据类的策略类，为了以后万一有改进方便添加
 * @author chh
 */
public class OtherValue implements CastValueStrategy{
    private CastValue castValue;

    public OtherValue() {

    }

    @Override
    public Object castValue() {
        return castValue.getValue().toString();
    }

    @Override
    public void afterPropertiesSet() {
        Factory.register(castValue.getValueType(), this);
    }

    @Override
    public void setCastValue(CastValue castValue) {
        this.castValue = castValue;
        afterPropertiesSet();
    }
}
