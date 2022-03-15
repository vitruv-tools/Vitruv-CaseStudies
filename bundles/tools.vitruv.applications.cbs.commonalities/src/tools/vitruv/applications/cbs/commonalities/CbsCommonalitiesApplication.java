package tools.vitruv.applications.cbs.commonalities;

import java.util.Set;

import tools.vitruv.commonalities.CommonalitiesChangePropagationSpecificationProvider;
import tools.vitruv.framework.applications.AbstractVitruvApplication;
import tools.vitruv.framework.propagation.ChangePropagationSpecification;

public class CbsCommonalitiesApplication extends AbstractVitruvApplication {
	@Override
	public Set<ChangePropagationSpecification> getChangePropagationSpecifications() {
		return CommonalitiesChangePropagationSpecificationProvider.getChangePropagationSpecifications();
	}

	@Override
	public String getName() {
		return "CBS Commonalities";
	}
}