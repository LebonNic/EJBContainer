package fr.isima.ejbcontainer.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(value = {TYPE, FIELD})
@Retention(value = RUNTIME)
public @interface PersistenceContext {
    String unitName() default "defaultUnit";
}
