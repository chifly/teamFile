package team.test.teamFile.utils.Strategy;

/**
 * 单精度浮点型的数字类型
 * @author chh
 */
public class FloatValue implements CastValueStrategy{
    private CastValue castValue;

    public FloatValue() {

    }

    @Override
    public Object castValue() {
        return Float.valueOf(castValue.getValue().toString());
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
