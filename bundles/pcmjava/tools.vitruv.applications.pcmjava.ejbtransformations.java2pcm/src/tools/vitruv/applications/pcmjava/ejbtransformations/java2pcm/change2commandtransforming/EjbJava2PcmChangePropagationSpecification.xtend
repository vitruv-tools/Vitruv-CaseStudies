package tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.change2commandtransforming

import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.TuidUpdatePreprocessor
import tools.vitruv.applications.pcmjava.util.java2pcm.Java2PcmPackagePreprocessor
import mir.reactions.reactionsJavaToPcm.ejbjava2pcm.ChangePropagationSpecificationJavaToPcm

class EjbJava2PcmChangePropagationSpecification extends ChangePropagationSpecificationJavaToPcm {
	public override setup() {
		addChangePreprocessor(new TuidUpdatePreprocessor());
		addChangePreprocessor(new Java2PcmPackagePreprocessor());  
		super.setup();
	}
}
