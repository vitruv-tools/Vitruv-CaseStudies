package tools.vitruv.applications.cbs.commonalities;

import java.util.HashSet;
import java.util.Set;

import tools.vitruv.framework.applications.AbstractVitruvApplication;
import tools.vitruv.framework.change.processing.ChangePropagationSpecification;

public class CbsCommonalitiesApplication extends AbstractVitruvApplication {

	@Override
	public Set<ChangePropagationSpecification> getChangePropagationSpecifications() {
		Set<ChangePropagationSpecification> specs = new HashSet<ChangePropagationSpecification>();
		//specs.add(new UmlToJavaChangePropagationSpecification()); // TODO
		//specs.add(new JavaToUmlChangePropagationSpecification());
		return specs;
	}

	@Override
	public String getName() {
		return "CBS Commonalities";
	}
}
