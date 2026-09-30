package tools.vitruv.applications.cbs.equivalencetests;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.vitruv.applications.cbs.testutils.JamoppComparisonSettings;
import tools.vitruv.applications.cbs.testutils.JamoppModelPrinter;
import tools.vitruv.applications.cbs.testutils.PcmComparisonSettings;
import tools.vitruv.applications.cbs.testutils.PcmModelPrinter;
import tools.vitruv.applications.cbs.testutils.equivalencetest.EquivalenceTest;
import tools.vitruv.applications.pcmumlclass.PcmUmlClassApplication;
import tools.vitruv.applications.umljava.UmlJavaApplication;
import tools.vitruv.change.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.change.testutils.printing.ModelPrinterChange;
import tools.vitruv.change.testutils.printing.UnsetFeaturesHidingModelPrinter;
import tools.vitruv.change.testutils.printing.UseModelPrinter;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EquivalenceTest(
	applications = {
		UmlJavaApplication.class,
		PcmUmlClassApplication.class
	},
	comparisonSettings = {
		JamoppComparisonSettings.class,
		PcmComparisonSettings.class
	}
)
@ExtendWith({ ModelPrinterChange.class, RegisterMetamodelsInStandalone.class })
@UseModelPrinter({ UnsetFeaturesHidingModelPrinter.class, JamoppModelPrinter.class, PcmModelPrinter.class })
public @interface ReactionsEquivalenceTest {
}
