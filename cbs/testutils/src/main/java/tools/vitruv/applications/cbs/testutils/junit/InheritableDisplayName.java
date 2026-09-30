package tools.vitruv.applications.cbs.testutils.junit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.DisplayNameGeneration;

/**
 * Like JUnit’s @{@code DisplayName}, but inherited by subclasses.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@DisplayNameGeneration(Inheritable.class)
public @interface InheritableDisplayName {
	String value();
}
