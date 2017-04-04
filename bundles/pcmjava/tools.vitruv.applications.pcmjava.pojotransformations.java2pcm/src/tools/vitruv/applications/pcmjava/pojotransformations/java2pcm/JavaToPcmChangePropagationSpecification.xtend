package tools.vitruv.applications.pcmjava.pojotransformations.java2pcm

import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.java2pcm.Java2PCMChangeProcessor
import tools.vitruv.applications.pcmjava.util.java2pcm.Java2PcmPackagePreprocessor
import mir.reactions.AbstractChangePropagationSpecificationJavaToPcm

class JavaToPcmChangePropagationSpecification extends AbstractChangePropagationSpecificationJavaToPcm {
	protected override setup() {
		addChangeMainprocessor(new Java2PCMChangeProcessor(userInteracting));
		super.setup();
		addChangePreprocessor(new Java2PcmPackagePreprocessor(userInteracting));
	}
}
