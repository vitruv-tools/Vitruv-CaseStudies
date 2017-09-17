package tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.java2pcm

import tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification
import tools.vitruv.applications.pcmjava.util.java2pcm.Java2PcmPackagePreprocessor
import tools.vitruv.domains.java.JavaDomainProvider
import tools.vitruv.domains.pcm.PcmDomainProvider

class Java2PcmGplImplementationChangePropagationSpecification extends CompositeChangePropagationSpecification {
	
	new() {
		super(new JavaDomainProvider().domain, new PcmDomainProvider().domain)
		setup()
	}

	def protected setup() {
		addChangePreprocessor(new Java2PcmPackagePreprocessor());
		addChangeMainprocessor(new Java2PcmChangeProcessor());
	}
	
}
