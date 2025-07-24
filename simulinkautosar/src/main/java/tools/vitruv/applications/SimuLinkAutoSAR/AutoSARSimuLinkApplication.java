package tools.vitruv.applications.simulinkautosar;

import java.util.HashSet;
import java.util.Set;

import tools.vitruv.framework.applications.VitruvApplication;
import tools.vitruv.change.propagation.ChangePropagationSpecification;

public class AutoSARSimuLinkApplication implements VitruvApplication {

	@Override
	public Set<ChangePropagationSpecification> getChangePropagationSpecifications() {
		Set<ChangePropagationSpecification> specs = new HashSet<ChangePropagationSpecification>();
		specs.add(new AutoSARToSimuLinkChangePropagationSpecification());
		specs.add(new SimuLinkToAutoSARChangePropagationSpecification());
		return specs;
	}

	@Override
	public String getName() {
		return "SimuLink <> AutoSAR";
	}

}
