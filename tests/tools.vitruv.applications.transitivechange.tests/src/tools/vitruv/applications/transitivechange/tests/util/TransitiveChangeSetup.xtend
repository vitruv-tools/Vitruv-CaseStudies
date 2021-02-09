package tools.vitruv.applications.transitivechange.tests.util

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.ArrayList
import tools.vitruv.applications.pcmumlclass.CombinedPcmToUmlClassReactionsChangePropagationSpecification
import tools.vitruv.applications.pcmumlclass.CombinedUmlClassToPcmReactionsChangePropagationSpecification
import tools.vitruv.applications.umljava.UmlToJavaChangePropagationSpecification
import tools.vitruv.applications.umljava.JavaToUmlChangePropagationSpecification
import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaChangePropagationSpecification
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmChangePropagationSpecification
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.domains.pcm.PcmDomainProvider
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.domains.java.JavaDomainProvider

@Utility
class TransitiveChangeSetup {
	static def getChangePropagationSpecifications(boolean linearNetwork) {
		val specifications = new ArrayList<ChangePropagationSpecification>()
		specifications += new CombinedPcmToUmlClassReactionsChangePropagationSpecification
		specifications += new CombinedUmlClassToPcmReactionsChangePropagationSpecification
		specifications += new UmlToJavaChangePropagationSpecification
		specifications += new JavaToUmlChangePropagationSpecification
		if (!linearNetwork) {
			specifications += new Pcm2JavaChangePropagationSpecification
			specifications += new Java2PcmChangePropagationSpecification
		}
		return specifications
	}
	
	static def patchDomains() {
		new PcmDomainProvider().domain.enableTransitiveChangePropagation
		new UmlDomainProvider().domain.enableTransitiveChangePropagation
		new JavaDomainProvider().domain.enableTransitiveChangePropagation	
	}
}