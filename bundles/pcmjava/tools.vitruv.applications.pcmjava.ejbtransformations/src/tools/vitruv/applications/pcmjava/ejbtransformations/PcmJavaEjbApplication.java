package tools.vitruv.applications.pcmjava.ejbtransformations;

import java.util.HashSet;
import java.util.Set;

import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.EjbJava2PcmChangePropagationSpecification;
import tools.vitruv.applications.pcmjava.ejbtransformations.pcm2java.Pcm2EjbJavaChangePropagationSpecification;
import tools.vitruv.framework.applications.AbstractVitruvApplication;
import tools.vitruv.framework.propagation.ChangePropagationSpecification;

public class PcmJavaEjbApplication extends AbstractVitruvApplication {

	@Override
	public Set<ChangePropagationSpecification> getChangePropagationSpecifications() {
		Set<ChangePropagationSpecification> specs = new HashSet<ChangePropagationSpecification>();
		specs.add(new EjbJava2PcmChangePropagationSpecification());
		specs.add(new Pcm2EjbJavaChangePropagationSpecification());
		return specs;
	}

	@Override
	public String getName() {
		return "PCM <> Java EJB";
	}

}
