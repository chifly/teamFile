package team.test.teamFile.utils.Strategy;

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
