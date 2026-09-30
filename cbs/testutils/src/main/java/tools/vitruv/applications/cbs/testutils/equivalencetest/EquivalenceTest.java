package tools.vitruv.applications.cbs.testutils.equivalencetest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.vitruv.applications.cbs.testutils.ModelComparisonSettings;
import tools.vitruv.change.testutils.TestLogging;
import tools.vitruv.change.testutils.views.UriMode;
import tools.vitruv.framework.applications.VitruvApplication;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Inherited
@ExtendWith({ TestLogging.class, EquivalenceTestExtension.class })
public @interface EquivalenceTest {
	Class<? extends VitruvApplication>[] applications();

	Class<? extends ModelComparisonSettings>[] comparisonSettings() default {};

	UriMode uriMode() default UriMode.FILE_URIS;
}
