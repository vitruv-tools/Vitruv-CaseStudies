package tools.vitruv.applications.umljava;

import java.util.HashSet;
import java.util.Set;

import tools.vitruv.framework.applications.AbstractVitruvApplication;
import tools.vitruv.framework.propagation.ChangePropagationSpecification;

public class UmlJavaApplication extends AbstractVitruvApplication {

	@Override
	public Set<ChangePropagationSpecification> getChangePropagationSpecifications() {
		Set<ChangePropagationSpecification> specs = new HashSet<ChangePropagationSpecification>();
		specs.add(new UmlToJavaChangePropagationSpecification());
		specs.add(new JavaToUmlChangePropagationSpecification());
		return specs;
	}

	@Override
	public String getName() {
		return "UML <> Java";
	}

}
