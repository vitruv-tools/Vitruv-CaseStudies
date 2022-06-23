package tools.vitruv.applications.pcmumlcomponents;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import tools.vitruv.framework.applications.VitruvApplication;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.change.propagation.ChangePropagationSpecification;

public class PcmUmlComponentsApplication implements VitruvApplication {

	@Override
	public Set<ChangePropagationSpecification> getChangePropagationSpecifications() {
		Set<ChangePropagationSpecification> specs = new HashSet<ChangePropagationSpecification>();
		specs.add(new PcmToUmlComponentsChangePropagationSpecification());
		specs.add(new UmlToPcmComponentsChangePropagationSpecification());
		return specs;
	}

	@Override
	public String getName() {
		return "PCM <> UML Components";
	}

	@Override
	public Set<VitruvDomain> getVitruvDomains() {
		return Collections.emptySet();
	}

}
