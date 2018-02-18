package tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.change2commandtransforming

import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.TuidUpdatePreprocessor
import tools.vitruv.applications.pcmjava.util.java2pcm.Java2PcmPackagePreprocessor

class EjbJava2PcmChangePropagationSpecification extends mir.reactions.ejbjava2pcm.Ejbjava2pcmChangePropagationSpecification {
	public override setup() {
		addChangePreprocessor(new TuidUpdatePreprocessor());
		addChangePreprocessor(new Java2PcmPackagePreprocessor());  
		super.setup();
	}
}
