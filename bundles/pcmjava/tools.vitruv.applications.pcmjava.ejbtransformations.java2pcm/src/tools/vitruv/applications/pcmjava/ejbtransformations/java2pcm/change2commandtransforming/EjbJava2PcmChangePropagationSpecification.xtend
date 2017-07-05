package tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.change2commandtransforming

import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.TuidUpdatePreprocessor
import mir.reactions.AbstractChangePropagationSpecificationJavaToPcm
import tools.vitruv.applications.pcmjava.util.java2pcm.Java2PcmPackagePreprocessor

class EjbJava2PcmChangePropagationSpecification extends AbstractChangePropagationSpecificationJavaToPcm {
	public override setup() {
		addChangePreprocessor(new TuidUpdatePreprocessor());
		addChangePreprocessor(new Java2PcmPackagePreprocessor());  
		super.setup();
	}
}
