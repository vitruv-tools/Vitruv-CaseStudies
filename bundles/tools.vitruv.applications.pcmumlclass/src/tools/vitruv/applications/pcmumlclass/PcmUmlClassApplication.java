package tools.vitruv.applications.pcmumlclass;

import java.util.HashSet;
import java.util.Set;

import tools.vitruv.domains.pcm.PcmDomainProvider;
import tools.vitruv.domains.uml.UmlDomainProvider;
import tools.vitruv.framework.applications.VitruvApplication;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.change.propagation.ChangePropagationSpecification;

public class PcmUmlClassApplication implements VitruvApplication {
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

	public Set<VitruvDomain> getVitruvDomains() {
		return Set.of(new PcmDomainProvider().getDomain(), new UmlDomainProvider().getDomain());
	}

}
