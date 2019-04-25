package ninja.cyplay.com.apilibrary.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by damien on 02/08/16.
 */
public class ReflectionUtils {

    private static final String TYPE_NAME_PREFIX = "class ";

    @SuppressWarnings("unchecked")
    public static <V> V get(Object object, String fieldName) {
        if (object != null) {
            Class<?> clazz = object.getClass();
            while (clazz != null) {
                try {
                    if (fieldName.indexOf("__") > 0) {
                        // first level to id
                        String first_level = fieldName.substring(0, fieldName.indexOf("__"));
                        Field field = clazz.getDeclaredField(first_level);
                        field.setAccessible(true);
                        // id embedded object
                        Object embeddedObject = field.get(object);
                        // Next property to find
                        String nextLevel = fieldName.substring(fieldName.indexOf("__") + 2, fieldName.length());
                        // recall recursive
                        return get(embeddedObject, nextLevel);
                    } else {
                        Field field = clazz.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        return (V) field.get(object);
                    }
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        }
        return null;
    }

    public static boolean set(Object object, String fieldName, Object fieldValue) {
        if (object != null) {
            Class<?> clazz = object.getClass();
            while (clazz != null) {
                try {
                    if (fieldName.indexOf("__") > 0) {
                        // first level to id
                        String first_level = fieldName.substring(0, fieldName.indexOf("__"));
                        Field field = clazz.getDeclaredField(first_level);
                        field.setAccessible(true);
                        // id embedded object
                        Object embeddedObject = field.get(object);

                        // Next property to find
                        if ( embeddedObject ==null){
                            embeddedObject  = field.getType().newInstance();
                            field.set(object, embeddedObject);
                        }
                        String nextLevel = fieldName.substring(fieldName.indexOf("__") + 2, fieldName.length());
                        // recall recursive
                        return set(embeddedObject, nextLevel, fieldValue);
                    } else {
                        Field field = clazz.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        field.set(object, fieldValue);
                    }
                    return true;
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        }
        return false;
    }

    public static String getClassName(Type type) {
        if (type==null) {
            return "";
        }
        String className = type.toString();
        if (className.startsWith(TYPE_NAME_PREFIX)) {
            className = className.substring(TYPE_NAME_PREFIX.length());
        }
        return className;
    }

    public static Class<?> getClass(Type type)
            throws ClassNotFoundException {
        String className = getClassName(type);
        if (className==null || className.isEmpty()) {
            return null;
        }
        return Class.forName(className);
    }

    public static Object newInstance(Type type)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> clazz = getClass(type);
        if (clazz==null) {
            return null;
        }
        return clazz.newInstance();
    }

}
