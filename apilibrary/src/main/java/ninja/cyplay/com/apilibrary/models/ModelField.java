package ninja.cyplay.com.apilibrary.models;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by romainlebouc on 26/09/16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ModelField {

    boolean embedded() default false;

    boolean many() default false;

    boolean eventLogging() default false;

}
