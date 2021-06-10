package team.test.teamFile.utils.Strategy;

/**
 * 字符类
 * @author chh
 */
public class CharacterValue implements CastValueStrategy{
    private CastValue castValue;

    public CharacterValue() {

    }

    @Override
    public Object castValue() {
        return castValue.getValue().toString().charAt(0);
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
