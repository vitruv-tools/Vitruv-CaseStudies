package tools.vitruv.applications.pcmjava.pojotransformations.java2pcm

import mir.reactions.AbstractChangePropagationSpecificationJavaToPcm
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.java2pcm.Java2PcmChangeProcessor
import tools.vitruv.applications.pcmjava.util.java2pcm.Java2PcmPackagePreprocessor

class Java2PcmChangePropagationSpecification extends AbstractChangePropagationSpecificationJavaToPcm {
	protected override setup() {
		//TODO zeile raus
		addChangeMainprocessor(new Java2PcmChangeProcessor());
		super.setup();
		addChangePreprocessor(new Java2PcmPackagePreprocessor());
	}
}
