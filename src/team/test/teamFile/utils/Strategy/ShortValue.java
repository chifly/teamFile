package team.test.teamFile.utils.Strategy;

public class ShortValue implements CastValueStrategy{
    private CastValue castValue;

    public ShortValue() {

    }

    @Override
    public Object castValue() {
        return Short.valueOf(castValue.getValue().toString());
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
