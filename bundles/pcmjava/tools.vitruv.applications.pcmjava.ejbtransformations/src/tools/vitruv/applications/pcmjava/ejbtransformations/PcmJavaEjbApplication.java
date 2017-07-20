package tools.vitruv.applications.pcmjava.ejbtransformations;

import java.util.HashSet;
import java.util.Set;

import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.change2commandtransforming.EjbJava2PcmChangePropagationSpecification;
import tools.vitruv.framework.applications.AbstractVitruvApplication;
import tools.vitruv.framework.change.processing.ChangePropagationSpecification;

public class PcmJavaEjbApplication extends AbstractVitruvApplication {

	@Override
	public Set<ChangePropagationSpecification> getChangePropagationSpecifications() {
		Set<ChangePropagationSpecification> specs = new HashSet<ChangePropagationSpecification>();
		specs.add(new EjbJava2PcmChangePropagationSpecification());
		return specs;
	}

	@Override
	public String getName() {
		return "PCM <> Java POJO";
	}

}
