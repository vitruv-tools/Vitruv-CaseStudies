package tools.vitruv.applications.cbs.commonalities;

import java.util.Set;

import tools.vitruv.domains.java.JavaDomainProvider;
import tools.vitruv.domains.pcm.PcmDomainProvider;
import tools.vitruv.domains.uml.UmlDomainProvider;
import tools.vitruv.framework.applications.VitruvApplication;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.propagation.ChangePropagationSpecification;

public class CbsCommonalitiesApplication implements VitruvApplication {
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

	@Override
	public Set<VitruvDomain> getVitruvDomains() {
		return Set.of(new PcmDomainProvider().getDomain(), new UmlDomainProvider().getDomain(),
				new JavaDomainProvider().getDomain());
	}

}