package tools.vitruv.applications.smarthomeuml;

import java.util.HashSet;
import java.util.Set;

import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.applications.VitruvApplication;

import tools.vitruv.applications.smarthomeuml.*;

public class SmarthomeUmlApplication implements VitruvApplication{

	@Override
	public Set<ChangePropagationSpecification> getChangePropagationSpecifications() {
		Set<ChangePropagationSpecification> specs = new HashSet<ChangePropagationSpecification>();
		specs.add(new SmarthomeToUmlChangePropagationSpecification());
//		specs.add(new CombinedUmlClassToPcmReactionsChangePropagationSpecification());
		return specs;
	}

	@Override
	public String getName() {
		return "Smarthome <-> UML Class";
	}

}
