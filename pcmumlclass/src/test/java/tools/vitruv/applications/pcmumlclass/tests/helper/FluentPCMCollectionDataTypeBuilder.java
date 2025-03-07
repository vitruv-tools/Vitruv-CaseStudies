package tools.vitruv.applications.pcmumlclass.tests.helper;

import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

public class FluentPCMCollectionDataTypeBuilder {

	private final CollectionDataType result;

	public FluentPCMCollectionDataTypeBuilder(String name, DataType innerType) {
		result = RepositoryFactory.eINSTANCE.createCollectionDataType();
		result.setInnerType_CollectionDataType(innerType);
		result.setEntityName(name);
	}

	public CollectionDataType build() {
		return result;
	}
}
