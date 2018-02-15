package tools.vitruv.applications.pcmjava.pojotransformations.java2pcm

import tools.vitruv.applications.pcmjava.util.java2pcm.Java2PcmPackagePreprocessor
import mir.reactions.reactionsJavaToPcm.java2Pcm.ChangePropagationSpecificationJavaToPcm

class Java2PcmChangePropagationSpecification extends ChangePropagationSpecificationJavaToPcm {
	protected override setup() {
		addChangeMainprocessor(new TuidUpdatePreprocessor());
		super.setup();
		addChangePreprocessor(new Java2PcmPackagePreprocessor());
	}
}
