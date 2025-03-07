package tools.vitruv.applications.transitivechange.tests.util

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.ArrayList
import tools.vitruv.applications.pcmumlclass.CombinedPcmToUmlClassReactionsChangePropagationSpecification
import tools.vitruv.applications.pcmumlclass.CombinedUmlClassToPcmReactionsChangePropagationSpecification
import tools.vitruv.applications.umljava.UmlToJavaChangePropagationSpecification
import tools.vitruv.applications.umljava.JavaToUmlChangePropagationSpecification
import tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaChangePropagationSpecification
import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmChangePropagationSpecification
import tools.vitruv.change.propagation.ChangePropagationSpecification

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
	
}