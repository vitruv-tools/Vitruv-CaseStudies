package tools.vitruv.applications.pcmjava;

import java.util.HashSet;
import java.util.Set;

import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmChangePropagationSpecification;
import tools.vitruv.framework.applications.VitruvApplication;
import tools.vitruv.change.propagation.ChangePropagationSpecification;

public class PcmJavaApplication implements VitruvApplication {

	@Override
	public Set<ChangePropagationSpecification> getChangePropagationSpecifications() {
		Set<ChangePropagationSpecification> specs = new HashSet<ChangePropagationSpecification>();
		specs.add(new Pcm2JavaChangePropagationSpecification());
		specs.add((ChangePropagationSpecification) new Java2PcmChangePropagationSpecification());
		return specs;
	}

	@Override
	public String getName() {
		return "PCM <> Java POJO";
	}

}
