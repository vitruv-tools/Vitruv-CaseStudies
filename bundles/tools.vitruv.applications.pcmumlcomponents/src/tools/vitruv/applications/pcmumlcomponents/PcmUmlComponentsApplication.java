package tools.vitruv.applications.pcmumlcomponents;

import java.util.HashSet;
import java.util.Set;

import tools.vitruv.framework.applications.AbstractVitruvApplication;
import tools.vitruv.framework.propagation.ChangePropagationSpecification;

public class PcmUmlComponentsApplication extends AbstractVitruvApplication {

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

}
