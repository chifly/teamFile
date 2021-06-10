package team.test.teamFile.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import team.test.teamFile.utils.Strategy.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库工具类
 * @author chh
 */
public class JdbcUtils {

    /**
     * 创建连接池
     */
    private static ComboPooledDataSource dataSource = new ComboPooledDataSource();


    /**
     * 查询数据
     * @param clazz 实体类类型
     * @param sql 语句
     * @param args 参数
     * @param <T> 类型
     * @return list
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> List<T> query(Class clazz, String sql, Object... args) {
        HashMap<Class, CastValueStrategy> maps = new HashMap<>();
        mapInitialize(maps);
        //获得连接对象
        try (Connection conn = dataSource.getConnection()) {
            //获得命令对象
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            //设置占位符参数
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            //执行
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> list = new ArrayList<>();
            //访问结果集
            while (resultSet.next()) {
                //创建对象包装每一行数据
                Object obj = clazz.newInstance();
                //获得该类的所有属性
                Field[] fields = clazz.getDeclaredFields();
                //遍历
                for (Field field : fields) {
                    String name = field.getName();
                    //获得该列的值
                    Object value = resultSet.getObject(name);
                    //属性设置为可以访问
                    field.setAccessible(true);
                    //赋值给该列
                    //field.set(obj, castValue(field.getType(), value));
                    //策略模式赋值给该列
                    CastValue castValue = new CastValue(field.getType(), value);
                    CastValueStrategy castValueStrategy = maps.get(field.getType());
                    castValueStrategy.setCastValue(castValue);
                    field.set(obj, Factory.getInvokeHandler(field.getType()).castValue());
                }
                list.add((T) obj);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化各种属性值类
     * @param maps 一个储存策略类型的map
     */
    public static void mapInitialize(Map<Class, CastValueStrategy> maps){
        maps.put(Integer.class, new IntegerValue());
        maps.put(Float.class, new FloatValue());
        maps.put(Double.class, new DoubleValue());
        maps.put(Character.class, new CharacterValue());
        maps.put(Short.class, new ShortValue());
        maps.put(Boolean.class, new BooleanValue());
        maps.put(Long.class, new LongValue());
        maps.put(String.class, new OtherValue());
    }
//    /**
//     * 属性值类型转化
//     * @param valueType
//     * @param value
//     * @return
//     */
//    public static Object castValue(Class valueType, Object value){
//        if (valueType == Integer.class) {
//            return Integer.valueOf(value.toString());
//        }
//        if (valueType == Byte.class) {
//            return Byte.valueOf(value.toString());
//        }
//        if (valueType == Short.class) {
//            return Short.valueOf(value.toString());
//        }
//        if (valueType == Float.class) {
//            return Float.valueOf(value.toString());
//        }
//        if (valueType == Double.class) {
//            return Double.valueOf(value.toString());
//        }
//        if (valueType == Character.class) {
//            return value.toString().charAt(0);
//        }
//        if (valueType == Long.class) {
//            return Long.valueOf(value.toString());
//        }
//        if (valueType == Boolean.class) {
//            return Boolean.valueOf(value.toString());
//        }
//        return value.toString();
//    }

    /**
     * 增删改查
     * @param sql sql语句
     * @param args 属性值
     * @return update
     * @throws SQLException sql异常
     */
    public static int update(String sql, Object... args) {
        //获得连接对象
        try (Connection conn = dataSource.getConnection()) {
            //获得命令对象
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            //设置占位符参数
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            //执行
            int rows = preparedStatement.executeUpdate();
            return rows;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


}
