package tools.vitruv.applications.pcmjava.pojotransformations.java2pcm

import tools.vitruv.applications.pcmjava.util.java2pcm.Java2PcmPackagePreprocessor

class Java2PcmChangePropagationSpecification extends mir.reactions.java2Pcm.Java2PcmChangePropagationSpecification {
	protected override setup() {
		addChangeMainprocessor(new TuidUpdatePreprocessor());
		super.setup();
		addChangePreprocessor(new Java2PcmPackagePreprocessor());
	}
}
