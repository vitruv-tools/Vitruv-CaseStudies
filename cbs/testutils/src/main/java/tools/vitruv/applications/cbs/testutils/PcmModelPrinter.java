package tools.vitruv.applications.cbs.testutils;

import static de.uka.ipd.sdq.identifier.IdentifierPackage.Literals.IDENTIFIER__ID;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import tools.vitruv.change.testutils.printing.ModelPrinter;
import tools.vitruv.change.testutils.printing.PrintIdProvider;
import tools.vitruv.change.testutils.printing.PrintResult;
import tools.vitruv.change.testutils.printing.PrintTarget;

public class PcmModelPrinter implements ModelPrinter {
	@Override
	public PrintResult printFeature(PrintTarget target, PrintIdProvider idProvider, EObject object,
			EStructuralFeature feature) {
		if (feature == IDENTIFIER__ID) {
			return PrintResult.PRINTED_NO_OUTPUT;
		}
		return PrintResult.NOT_RESPONSIBLE;
	}

	@Override
	public ModelPrinter withSubPrinter(ModelPrinter subPrinter) {
		return this;
	}
}
