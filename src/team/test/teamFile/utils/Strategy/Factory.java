package team.test.teamFile.utils.Strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * 各种数据类型的工厂
 * @author chh
 */
public class Factory {
    private static Map<Class, CastValueStrategy> Factorymaps = new HashMap<>();



    public static CastValueStrategy getInvokeHandler(Class type) {
        return Factorymaps.get(type);
    }

    public static void register(Class type, CastValueStrategy castValueStrategy) {
        if (type == null && castValueStrategy == null) {
            return;
        }
        Factorymaps.put(type, castValueStrategy);
    }
}
