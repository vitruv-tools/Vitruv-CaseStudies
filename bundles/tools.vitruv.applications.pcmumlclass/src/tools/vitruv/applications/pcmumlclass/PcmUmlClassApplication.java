package tools.vitruv.applications.pcmumlclass;

import java.util.HashSet;
import java.util.Set;

import tools.vitruv.domains.pcm.PcmDomainProvider;
import tools.vitruv.domains.uml.UmlDomainProvider;
import tools.vitruv.framework.applications.AbstractVitruvApplication;
import tools.vitruv.framework.propagation.ChangePropagationSpecification;

public class PcmUmlClassApplication extends AbstractVitruvApplication {

	public PcmUmlClassApplication() {
		patchDomains();
	}

	@Override
	public Set<ChangePropagationSpecification> getChangePropagationSpecifications() {
		Set<ChangePropagationSpecification> specs = new HashSet<ChangePropagationSpecification>();
		specs.add(new CombinedPcmToUmlClassReactionsChangePropagationSpecification());
		specs.add(new CombinedUmlClassToPcmReactionsChangePropagationSpecification());
		return specs;
	}

	@Override
	public String getName() {
		return "PCM <-> UML Class";
	}

	private void patchDomains() {
		new UmlDomainProvider().getDomain().enableTransitiveChangePropagation();
		new PcmDomainProvider().getDomain().enableTransitiveChangePropagation();
	}

}
