package tools.vitruv.applications.transitivechange.tests.util;

import java.util.ArrayList;
import java.util.List;
import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmChangePropagationSpecification;
import tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaChangePropagationSpecification;
import tools.vitruv.applications.pcmumlclass.CombinedPcmToUmlClassReactionsChangePropagationSpecification;
import tools.vitruv.applications.pcmumlclass.CombinedUmlClassToPcmReactionsChangePropagationSpecification;
import tools.vitruv.applications.umljava.JavaToUmlChangePropagationSpecification;
import tools.vitruv.applications.umljava.UmlToJavaChangePropagationSpecification;
import tools.vitruv.change.propagation.ChangePropagationSpecification;

public final class TransitiveChangeSetup {

	private TransitiveChangeSetup() {
	}

	public static List<ChangePropagationSpecification> getChangePropagationSpecifications(boolean linearNetwork) {
		List<ChangePropagationSpecification> specifications = new ArrayList<>();
		specifications.add(new CombinedPcmToUmlClassReactionsChangePropagationSpecification());
		specifications.add(new CombinedUmlClassToPcmReactionsChangePropagationSpecification());
		specifications.add(new UmlToJavaChangePropagationSpecification());
		specifications.add(new JavaToUmlChangePropagationSpecification());
		if (!linearNetwork) {
			specifications.add(new Pcm2JavaChangePropagationSpecification());
			specifications.add(new Java2PcmChangePropagationSpecification());
		}
		return specifications;
	}

}
