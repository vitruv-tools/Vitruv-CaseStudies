package tools.vitruv.applications.cbs.commonalities.equivalencetests

import java.lang.annotation.Target
import java.lang.annotation.Retention
import tools.vitruv.applications.cbs.testutils.equivalencetest.EquivalenceTest
import tools.vitruv.applications.cbs.testutils.JamoppComparisonSettings
import tools.vitruv.applications.cbs.testutils.PcmComparisonSettings
import tools.vitruv.testutils.printing.ModelPrinterChange
import tools.vitruv.applications.cbs.testutils.PcmModelPrinter
import tools.vitruv.applications.cbs.testutils.JamoppModelPrinter
import tools.vitruv.testutils.printing.UseModelPrinter
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.applications.cbs.commonalities.CbsCommonalitiesApplication
import tools.vitruv.testutils.RegisterMetamodelsInStandalone
import tools.vitruv.testutils.printing.UnsetFeaturesHidingModelPrinter

@Target(TYPE)
@Retention(RUNTIME)
@EquivalenceTest(
	applications = CbsCommonalitiesApplication,
	comparisonSettings = #[
		JamoppComparisonSettings,
		PcmComparisonSettings
	]
)
@ExtendWith(ModelPrinterChange, RegisterMetamodelsInStandalone)
@UseModelPrinter(UnsetFeaturesHidingModelPrinter, JamoppModelPrinter, PcmModelPrinter)
annotation CommonalitiesEquivalenceTest {
}