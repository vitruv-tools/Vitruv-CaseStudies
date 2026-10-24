package tools.vitruv.applications.c4cbse2joana;

import java.util.HashSet;
import java.util.Set;

import tools.vitruv.applications.c4cbse2joana.changepropagationspecification.C4C2joanaChangePropagationSpecification;
import tools.vitruv.applications.c4cbse2joana.changepropagationspecification.JaMoPP2joanaChangePropagationSpecification;
import tools.vitruv.applications.c4cbse2joana.changepropagationspecification.Joana2c4cChangePropagationSpecification;
import tools.vitruv.applications.c4cbse2joana.changepropagationspecification.PCM2c4cChangePropagationSpecification;
import tools.vitruv.applications.util.temporary.VitruvApplication;
import tools.vitruv.change.propagation.ChangePropagationSpecification;

public class C4cbse2JoanaApplication implements VitruvApplication {

	@Override
	public Set<ChangePropagationSpecification> getChangePropagationSpecifications() {
		Set<ChangePropagationSpecification> specs = new HashSet<ChangePropagationSpecification>();
		specs.add(new C4C2joanaChangePropagationSpecification());
		specs.add(new Joana2c4cChangePropagationSpecification());
		specs.add(new JaMoPP2joanaChangePropagationSpecification());
		specs.add(new PCM2c4cChangePropagationSpecification());
		return specs;
	}

	@Override
	public String getName() {
		return "Confidentiality4CBSE <> JOANA";
	}

}
