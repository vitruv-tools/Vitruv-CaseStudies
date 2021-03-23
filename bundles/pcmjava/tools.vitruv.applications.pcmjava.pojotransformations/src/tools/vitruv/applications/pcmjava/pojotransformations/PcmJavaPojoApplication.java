package tools.vitruv.applications.pcmjava.pojotransformations;

import java.util.HashSet;
import java.util.Set;

import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmChangePropagationSpecification;
import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaChangePropagationSpecification;
import tools.vitruv.framework.applications.AbstractVitruvApplication;
import tools.vitruv.framework.propagation.ChangePropagationSpecification;

public class PcmJavaPojoApplication extends AbstractVitruvApplication {

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

}
