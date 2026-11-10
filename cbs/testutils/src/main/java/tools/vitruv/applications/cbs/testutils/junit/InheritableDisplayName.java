package tools.vitruv.applications.cbs.testutils.junit;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.junit.jupiter.api.DisplayNameGeneration;

/**
 * Like JUnit’s @{@code DisplayName}, but inherited by subclasses.
 */
@Target(TYPE)
@Retention(RUNTIME)
@Inherited
@DisplayNameGeneration(Inheritable.class)
public @interface InheritableDisplayName {
    String value();
}
