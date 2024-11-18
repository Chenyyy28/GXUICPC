package manage.gxuicpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryRedis {
    String value() default "";

    String key();

    boolean id();
    int suf()default 1;

    boolean page() default false;

    Class resType();

}
