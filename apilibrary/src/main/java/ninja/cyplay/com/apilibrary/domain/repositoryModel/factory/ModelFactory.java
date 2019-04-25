package ninja.cyplay.com.apilibrary.domain.repositoryModel.factory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ModelFactory {

   private final HashMap<String, Class> apiToAppClassMapping = new HashMap<>();
   private final Set<Class> modelClasses = new HashSet<>();
   private static ModelFactory INSTANCE = null;

   // Private __ctor__
   private ModelFactory() { }

   public static ModelFactory getInstance() {
      if (INSTANCE == null)
         INSTANCE = new ModelFactory();
      return INSTANCE;
   }

   public void register(Class klass, Class clazz) {
      modelClasses.add(klass);
      apiToAppClassMapping.put(klass.getName(), clazz);
   }

   public Class get(String key) {
      return apiToAppClassMapping.containsKey(key) ? apiToAppClassMapping.get(key) : null;
   }

   public Set<Class> getModelClasses() {
      return new HashSet<>(modelClasses);
   }
}