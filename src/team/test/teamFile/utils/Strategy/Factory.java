package team.test.teamFile.utils.Strategy;

import java.util.HashMap;
import java.util.Map;

public class Factory {
    private static Map<Class, CastValueStrategy> map = new HashMap<>();



    public static CastValueStrategy getInvokeHandler(Class Type) {
        return map.get(Type);
    }

    public static void register(Class Type, CastValueStrategy castValueStrategy) {
        if (Type == null && castValueStrategy == null) {
            return;
        }
        map.put(Type, castValueStrategy);
    }
}
