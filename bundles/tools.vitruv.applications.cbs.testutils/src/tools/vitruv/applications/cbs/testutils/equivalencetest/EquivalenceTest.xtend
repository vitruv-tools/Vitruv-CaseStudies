package tools.vitruv.applications.cbs.testutils.equivalencetest

import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.testutils.UriMode
import static tools.vitruv.testutils.UriMode.FILE_URIS
import java.lang.annotation.Retention
import java.lang.annotation.Target
import org.junit.jupiter.api.^extension.ExtendWith
import java.lang.annotation.Inherited
import tools.vitruv.applications.cbs.testutils.ModelComparisonSettings

@Retention(RUNTIME)
@Target(TYPE, METHOD)
@Inherited
@ExtendWith(EquivalenceTestExtension)
annotation EquivalenceTest {
	val Class<? extends ChangePropagationSpecification>[] value
	val Class<? extends ModelComparisonSettings>[] comparisonSettings = #[]
	val UriMode uriMode = FILE_URIS
}
