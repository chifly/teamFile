package team.test.teamFile.utils.Strategy;

/**
 * 双精度浮点型的数据
 * @author chh
 */
public class DoubleValue implements CastValueStrategy{
    private CastValue castValue;

    public DoubleValue() {

    }

    @Override
    public Object castValue() {
        return Double.valueOf(castValue.getValue().toString());
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
