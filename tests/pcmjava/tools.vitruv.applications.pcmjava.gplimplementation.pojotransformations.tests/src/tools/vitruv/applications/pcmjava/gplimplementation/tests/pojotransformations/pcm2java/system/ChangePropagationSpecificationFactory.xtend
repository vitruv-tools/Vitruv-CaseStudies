package tools.vitruv.applications.pcmjava.gplimplementation.tests.pojotransformations.pcm2java.system

import java.util.List
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java.Pcm2JavaChangePropagationSpecification

package class ChangePropagationSpecificationFactory {
	static def List<ChangePropagationSpecification> createPcm2JavaGplImplementationChangePropagationSpecification() {
		return #[new Pcm2JavaChangePropagationSpecification()]
	}
}
