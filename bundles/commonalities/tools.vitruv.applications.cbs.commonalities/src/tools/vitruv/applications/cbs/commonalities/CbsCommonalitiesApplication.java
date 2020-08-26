package tools.vitruv.applications.cbs.commonalities;

import java.util.HashSet;
import java.util.Set;

import tools.vitruv.framework.applications.AbstractVitruvApplication;
import tools.vitruv.framework.change.processing.ChangePropagationSpecification;

public class CbsCommonalitiesApplication extends AbstractVitruvApplication {

	@Override
	public Set<ChangePropagationSpecification> getChangePropagationSpecifications() {
		Set<ChangePropagationSpecification> specs = new HashSet<ChangePropagationSpecification>();
		// TODO This is not used/supported currently. The Commonalities language should probably generate a combined
		// change propagation specification for all the partial change propagation specifications it generates via the
		// Reactions language. For test cases we use the CBSExecutionTestCompiler to create this combined change
		// propagation specification by manually searching for generated change propagation specification classes.
		return specs;
	}

	@Override
	public String getName() {
		return "CBS Commonalities";
	}
}
