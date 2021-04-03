package tools.vitruv.applications.cbs.commonalities;

import java.util.Set;

import tools.vitruv.framework.applications.AbstractVitruvApplication;
import tools.vitruv.framework.propagation.ChangePropagationSpecification;

public class CbsCommonalitiesApplication extends AbstractVitruvApplication {
	@Override
	public Set<ChangePropagationSpecification> getChangePropagationSpecifications() {
		return Set.of(new CbsCommonalitiesChangePropagationSpecifications.Uml2ObjectOrientedDesign(),
			new CbsCommonalitiesChangePropagationSpecifications.ObjectOrientedDesign2Uml(),
			new CbsCommonalitiesChangePropagationSpecifications.Java2ObjectOrientedDesign(),
			new CbsCommonalitiesChangePropagationSpecifications.ObjectOrientedDesign2Java(),
			new CbsCommonalitiesChangePropagationSpecifications.Pcm2ComponentBasedSystems(),
			new CbsCommonalitiesChangePropagationSpecifications.ComponentBasedSystems2Pcm(),
			new CbsCommonalitiesChangePropagationSpecifications.ObjectOrientedDesign2ComponentBasedSystems(),
			new CbsCommonalitiesChangePropagationSpecifications.ComponentBasedSystems2ObjectOrientedDesign());
	}

	@Override
	public String getName() {
		return "CBS Commonalities";
	}
}