package tools.vitruv.applications.pcmumlclassjava;

import java.util.HashSet;
import java.util.Set;

import tools.vitruv.applications.pcmumlclass.CombinedPcmToUmlClassReactionsChangePropagationSpecification;
import tools.vitruv.applications.pcmumlclass.CombinedUmlClassToPcmReactionsChangePropagationSpecification;
import tools.vitruv.applications.umljava.JavaToUmlChangePropagationSpecification;
import tools.vitruv.applications.umljava.UmlToJavaChangePropagationSpecification;
import tools.vitruv.domains.java.JavaDomainProvider;
import tools.vitruv.domains.pcm.PcmDomainProvider;
import tools.vitruv.domains.uml.UmlDomainProvider;
import tools.vitruv.framework.applications.AbstractVitruvApplication; 
import tools.vitruv.framework.change.processing.ChangePropagationSpecification;

public class PcmUmlClassJavaApplication extends AbstractVitruvApplication {

	public PcmUmlClassJavaApplication() {
		patchDomains();
	}
	
	@Override
	public Set<ChangePropagationSpecification> getChangePropagationSpecifications() {
		Set<ChangePropagationSpecification> specs = new HashSet<ChangePropagationSpecification>();
		specs.add(new CombinedPcmToUmlClassReactionsChangePropagationSpecification());
		specs.add(new CombinedUmlClassToPcmReactionsChangePropagationSpecification());
		specs.add(new UmlToJavaChangePropagationSpecification());
		specs.add(new JavaToUmlChangePropagationSpecification());
		return specs;
	}

	@Override
	public String getName() {
		return "PCM <-> UML Class <-> Java";
	}
	
	private void patchDomains() {
		new UmlDomainProvider().getDomain().enableTransitiveChangePropagation();
		new PcmDomainProvider().getDomain().enableTransitiveChangePropagation();
		new JavaDomainProvider().getDomain().enableTransitiveChangePropagation();
	}

}
