package tools.vitruv.applications.pcmjava.gplimplementation.tests.pojotransformations.java2pcm

import java.util.List
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.java2pcm.Java2PcmGplImplementationChangePropagationSpecification

package class ChangePropagationSpecificationFactory {
	static def List<ChangePropagationSpecification> createJava2PcmGplImplementationChangePropagationSpecification() {
		return #[new Java2PcmGplImplementationChangePropagationSpecification()]
	}
}