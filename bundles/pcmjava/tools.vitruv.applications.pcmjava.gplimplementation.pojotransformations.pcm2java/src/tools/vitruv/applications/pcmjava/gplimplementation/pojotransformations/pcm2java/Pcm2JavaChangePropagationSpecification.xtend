package tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java

import tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification
import tools.vitruv.domains.pcm.PcmDomainProvider
import tools.vitruv.domains.java.JavaDomainProvider

class Pcm2JavaChangePropagationSpecification extends CompositeChangePropagationSpecification {
	new() {
		super(new PcmDomainProvider().domain, new JavaDomainProvider().domain);
		setup();
	}

	def protected setup() {
		addChangeMainprocessor(new Pcm2JavaChangeProcessor());
	}
	
}
