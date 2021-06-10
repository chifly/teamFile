package team.test.teamFile.utils.Strategy;

public class IntegerValue implements CastValueStrategy{
    private CastValue castValue;
    public IntegerValue() {

    }
    @Override
    public Object castValue() {
        return Integer.valueOf(castValue.getValue().toString());
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
