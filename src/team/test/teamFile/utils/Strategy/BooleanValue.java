package team.test.teamFile.utils.Strategy;

public class BooleanValue implements CastValueStrategy {
    private CastValue castValue;

    public BooleanValue() {

    }

    @Override
    public Object castValue() {
        return Boolean.valueOf(castValue.getValue().toString());
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