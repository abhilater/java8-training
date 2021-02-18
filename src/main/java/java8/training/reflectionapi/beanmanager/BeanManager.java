package java8.training.reflectionapi.beanmanager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java8.training.reflectionapi.provider.H2ConnectionProvider;
import java8.training.reflectionapi.reflection.annotation.Inject;
import java8.training.reflectionapi.reflection.annotation.Provides;

public class BeanManager {

  private static BeanManager instance = new BeanManager();
  private Map<Class<?>, Supplier<?>> registry = new HashMap<>();

  private BeanManager() {
    // BeanManager needs to know all the classes or dependencies it needs to create and inject
    // i.e build the dependency graph
    List<Class<?>> classes = Arrays.asList(H2ConnectionProvider.class);
    for (Class<?> clss : classes) {
      Method[] methods = clss.getDeclaredMethods();
      for (Method m : methods) {
        Provides provides = m.getAnnotation(Provides.class);
        if (null != provides) {
          Class<?> returnType = m.getReturnType();
          Supplier<?> supplier = () -> {

            try {
              if (!Modifier.isStatic(m.getModifiers())) {
                Object obj = clss.getConstructor().newInstance();
                return m.invoke(obj);
              } else {
                return m.invoke(null);
              }
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          };

          registry.put(returnType, supplier);
        }
      }
    }
  }

  public static BeanManager getInstance() {
    return instance;
  }

  public <T> T getInstance(Class<T> clss) {
    T t = null;
    try {
      t = clss.getConstructor().newInstance();

      Field[] fields = clss.getDeclaredFields();
      for (Field field : fields) {
        Inject inject = field.getAnnotation(Inject.class);
        if (null != inject) {
          Class<?> injectedFieldType = field.getType();
          Supplier<?> supplier = registry.get(injectedFieldType);
          Object onjectToInject = supplier.get();
          field.setAccessible(true);
          field.set(t, onjectToInject);
        }
      }

      return t;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}

