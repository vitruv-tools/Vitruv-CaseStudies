package tools.vitruv.applications.cbs.testutils.equivalencetest

import tools.vitruv.change.testutils.views.UriMode
import java.lang.annotation.Retention
import java.lang.annotation.Target
import org.junit.jupiter.api.^extension.ExtendWith
import java.lang.annotation.Inherited
import tools.vitruv.applications.cbs.testutils.ModelComparisonSettings
import tools.vitruv.framework.applications.VitruvApplication
import tools.vitruv.change.testutils.TestLogging

@Retention(RUNTIME)
@Target(TYPE, METHOD)
@Inherited
@ExtendWith(TestLogging, EquivalenceTestExtension)
annotation EquivalenceTest {
	val Class<? extends VitruvApplication>[] applications
	val Class<? extends ModelComparisonSettings>[] comparisonSettings = #[]
	val UriMode uriMode = UriMode.FILE_URIS
}
