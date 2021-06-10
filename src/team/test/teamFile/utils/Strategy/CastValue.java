package team.test.teamFile.utils.Strategy;

public class CastValue<T> {
    private Class valueType;
    private T value;

    public CastValue() {
    }

    public CastValue(Class valueType, T value) {
        this.valueType = valueType;
        this.value = value;
    }

    public Class getValueType() {
        return valueType;
    }

    public void setValueType(Class valueType) {
        this.valueType = valueType;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
