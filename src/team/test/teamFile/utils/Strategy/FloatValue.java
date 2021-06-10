package team.test.teamFile.utils.Strategy;

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
