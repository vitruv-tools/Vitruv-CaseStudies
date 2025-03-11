package tools.vitruv.applications.cbs.commonalities;

import java.util.Set;

import tools.vitruv.framework.applications.VitruvApplication;
import tools.vitruv.commonalities.CommonalitiesChangePropagationSpecificationProvider;
import tools.vitruv.change.propagation.ChangePropagationSpecification;

public class CbsCommonalitiesApplication implements VitruvApplication {
	@Override
	public Set<ChangePropagationSpecification> getChangePropagationSpecifications() {
		return CommonalitiesChangePropagationSpecificationProvider.getChangePropagationSpecifications();
	}

	@Override
	public String getName() {
		return "CBS Commonalities";
	}

}