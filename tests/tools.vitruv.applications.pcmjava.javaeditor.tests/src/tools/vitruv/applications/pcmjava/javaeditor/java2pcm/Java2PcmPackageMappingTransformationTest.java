
package tools.vitruv.applications.pcmjava.javaeditor.java2pcm;
import java.util.Collections;
import java.util.List;

import org.palladiosimulator.pcm.repository.CompositeDataType;

import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmUserSelection;
import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmChangePropagationSpecification;
import tools.vitruv.change.propagation.ChangePropagationSpecification;

public class Java2PcmPackageMappingTransformationTest extends Java2PcmTransformationTest {

	@Override
	protected List<ChangePropagationSpecification> getChangePropagationSpecifications() {
		return Collections.singletonList(new Java2PcmChangePropagationSpecification());
	}

	protected CompositeDataType addClassThatCorrespondsToCompositeDatatype() throws Throwable {
		this.getUserInteraction()
				.addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_DATA_TYPE.getSelection());
		final CompositeDataType cdt = this.addClassInPackage(this.getDatatypesPackage(), CompositeDataType.class);
		return cdt;
	}

}
