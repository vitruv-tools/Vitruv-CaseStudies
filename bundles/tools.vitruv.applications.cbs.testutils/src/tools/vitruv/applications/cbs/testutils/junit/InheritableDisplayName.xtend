package tools.vitruv.applications.cbs.testutils.junit

import java.lang.annotation.Target
import java.lang.annotation.Retention
import java.lang.annotation.Inherited
import org.junit.jupiter.api.DisplayNameGeneration

/**
 * Like JUnit’s @{@code DisplayName}, but inherited by subclasses.
 */
@Target(TYPE)
@Retention(RUNTIME)
@Inherited
@DisplayNameGeneration(Inheritable)
annotation InheritableDisplayName {
	val String value
}
