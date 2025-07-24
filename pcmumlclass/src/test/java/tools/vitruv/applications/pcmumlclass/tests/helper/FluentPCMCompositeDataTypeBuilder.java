package tools.vitruv.applications.pcmumlclass.tests.helper;

import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

public class FluentPCMCompositeDataTypeBuilder {

	private final CompositeDataType result;

	public FluentPCMCompositeDataTypeBuilder(String name) {
		result = RepositoryFactory.eINSTANCE.createCompositeDataType();
		result.setEntityName(name);
	}

	public CompositeDataType build() {
		return result;
	}

	public FluentPCMCompositeDataTypeBuilder addInnerDeclaration(String name, DataType innerDeclarationType) {
		InnerDeclaration innerDeclaration = RepositoryFactory.eINSTANCE.createInnerDeclaration();
		innerDeclaration.setEntityName(name);
		innerDeclaration.setDatatype_InnerDeclaration(innerDeclarationType);
		innerDeclaration.setCompositeDataType_InnerDeclaration(result);

		result.getInnerDeclaration_CompositeDataType().add(innerDeclaration);
		return this;
	}

	public FluentPCMCompositeDataTypeBuilder addParentType(CompositeDataType parentType) {
		result.getParentType_CompositeDataType().add(parentType);
		return this;
	}
}
