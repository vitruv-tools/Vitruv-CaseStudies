package tools.vitruv.applications.pcmjava.pojotransformations;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmChangePropagationSpecification;
import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaChangePropagationSpecification;
import tools.vitruv.framework.applications.VitruvApplication;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.change.propagation.ChangePropagationSpecification;

public class PcmJavaPojoApplication implements VitruvApplication {

	@Override
	public Set<ChangePropagationSpecification> getChangePropagationSpecifications() {
		Set<ChangePropagationSpecification> specs = new HashSet<ChangePropagationSpecification>();
		specs.add(new Pcm2JavaChangePropagationSpecification());
		specs.add(new Java2PcmChangePropagationSpecification());
		return specs;
	}

	@Override
	public String getName() {
		return "PCM <> Java POJO";
	}

	@Override
	public Set<VitruvDomain> getVitruvDomains() {
		return Collections.emptySet();
	}

}
