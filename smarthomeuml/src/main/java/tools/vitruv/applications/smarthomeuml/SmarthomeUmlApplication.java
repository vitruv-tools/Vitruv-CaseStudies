package tools.vitruv.applications.smarthomeuml;

import java.util.HashSet;
import java.util.Set;

import tools.vitruv.applications.util.temporary.VitruvApplication;
import tools.vitruv.change.propagation.ChangePropagationSpecification;

public class SmarthomeUmlApplication implements VitruvApplication {

	@Override
	public Set<ChangePropagationSpecification> getChangePropagationSpecifications() {
		Set<ChangePropagationSpecification> specs = new HashSet<ChangePropagationSpecification>();
		specs.add(new SmarthomeToUmlChangePropagationSpecification());
		return specs;
	}

	@Override
	public String getName() {
		return "Smarthome <-> UML Class";
	}

}
