package tools.vitruv.applications.cbs.testutils.equivalencetest;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

import tools.vitruv.applications.cbs.testutils.ModelComparisonSettings;
import tools.vitruv.applications.util.temporary.VitruvApplication;
import tools.vitruv.change.testutils.TestLogging;
import tools.vitruv.change.testutils.views.UriMode;

/**
 * Configures {@linkplain EquivalenceTestBuilder equivalence tests} in the annotated test class or method.
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@Inherited
@ExtendWith({ TestLogging.class, EquivalenceTestExtension.class })
public @interface EquivalenceTest {
    Class<? extends VitruvApplication>[] applications();

    Class<? extends ModelComparisonSettings>[] comparisonSettings() default {};

    UriMode uriMode() default UriMode.FILE_URIS;
}
