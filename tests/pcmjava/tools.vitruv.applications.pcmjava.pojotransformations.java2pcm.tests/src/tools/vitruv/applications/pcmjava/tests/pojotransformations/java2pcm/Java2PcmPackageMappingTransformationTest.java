package tools.vitruv.applications.pcmjava.tests.pojotransformations.java2pcm;

import java.util.Collections;
import java.util.List;

import org.palladiosimulator.pcm.repository.CompositeDataType;

import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.java2pcm.transformations.ClassMappingTransformation;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmChangePropagationSpecification;
import tools.vitruv.applications.pcmjava.tests.util.Java2PcmTransformationTest;
import tools.vitruv.framework.change.processing.ChangePropagationSpecification;

public class Java2PcmPackageMappingTransformationTest extends Java2PcmTransformationTest {

	@Override
	protected List<ChangePropagationSpecification> createChangePropagationSpecifications() {
		return Collections.singletonList(new Java2PcmChangePropagationSpecification());
	}

	protected CompositeDataType addClassThatCorrespondsToCompositeDatatype() throws Throwable {
		this.testUserInteractor.addNextSelections(ClassMappingTransformation.SELECT_CREATE_COMPOSITE_DATA_TYPE);
		final CompositeDataType cdt = this.addClassInPackage(this.getDatatypesPackage(), CompositeDataType.class);
		return cdt;
	}
	
}
