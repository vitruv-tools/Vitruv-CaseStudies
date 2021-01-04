package tools.vitruv.applications.cbs.testutils

import tools.vitruv.testutils.printing.ModelPrinter
import tools.vitruv.testutils.printing.PrintTarget
import tools.vitruv.testutils.printing.PrintIdProvider
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import static de.uka.ipd.sdq.identifier.IdentifierPackage.Literals.*
import static tools.vitruv.testutils.printing.PrintResult.*

class PcmModelPrinter implements ModelPrinter {
	override printFeature(
		PrintTarget target,
		PrintIdProvider idProvider,
		EObject object,
		EStructuralFeature feature
	) {
		switch (feature) {
			case IDENTIFIER__ID: PRINTED_NO_OUTPUT
			default: NOT_RESPONSIBLE
		}
	}

	override withSubPrinter(ModelPrinter subPrinter) {
		this
	}
}
