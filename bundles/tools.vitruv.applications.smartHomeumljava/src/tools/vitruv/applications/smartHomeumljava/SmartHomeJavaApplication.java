package tools.vitruv.applications.smartHomeumljava;

import java.util.HashSet;
import java.util.Set;

import tools.vitruv.framework.applications.VitruvApplication;
import tools.vitruv.change.propagation.ChangePropagationSpecification;

public class SmartHomeJavaApplication implements VitruvApplication {

	@Override
	public Set<ChangePropagationSpecification> getChangePropagationSpecifications() {
		Set<ChangePropagationSpecification> specs = new HashSet<ChangePropagationSpecification>();
		specs.add(new UmlToSmartHomeChangePropagationSpecification());
		specs.add(new SmartHomeToUmlChangePropagationSpecification());
		return specs;
	}

	@Override
	public String getName() {
		return "UML <> Java";
	}

}


