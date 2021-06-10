package team.test.teamFile.utils.Strategy;

/**
 * 长整型数据的策略类
 * @author chh
 */
public class LongValue implements CastValueStrategy{
    private CastValue castValue;

    public LongValue() {

    }

    @Override
    public Object castValue() {

        return Long.valueOf(castValue.getValue().toString());
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
