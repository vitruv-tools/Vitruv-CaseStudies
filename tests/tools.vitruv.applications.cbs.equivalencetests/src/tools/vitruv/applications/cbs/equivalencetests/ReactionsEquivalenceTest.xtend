package tools.vitruv.applications.cbs.equivalencetests

import tools.vitruv.applications.cbs.testutils.equivalencetest.EquivalenceTest
import java.lang.annotation.Target
import java.lang.annotation.Retention
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.testutils.printing.ModelPrinterChange
import tools.vitruv.testutils.printing.UseModelPrinter
import tools.vitruv.applications.cbs.testutils.JamoppModelPrinter
import tools.vitruv.applications.cbs.testutils.PcmModelPrinter
import tools.vitruv.applications.cbs.testutils.JamoppComparisonSettings
import tools.vitruv.applications.cbs.testutils.PcmComparisonSettings
import tools.vitruv.applications.pcmjava.pojotransformations.PcmJavaPojoApplication

@Target(TYPE)
@Retention(RUNTIME)
@EquivalenceTest(
	application = PcmJavaPojoApplication,
	comparisonSettings = #[
		JamoppComparisonSettings,
		PcmComparisonSettings
	]
)
@ExtendWith(ModelPrinterChange)
@UseModelPrinter(JamoppModelPrinter, PcmModelPrinter)
annotation ReactionsEquivalenceTest {
}
