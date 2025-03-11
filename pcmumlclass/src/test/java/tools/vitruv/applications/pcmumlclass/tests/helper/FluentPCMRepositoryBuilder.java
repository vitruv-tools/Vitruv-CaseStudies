package tools.vitruv.applications.pcmumlclass.tests.helper;

import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

public class FluentPCMRepositoryBuilder {
	private final Repository result;

	public FluentPCMRepositoryBuilder(String enitiyName) {
		result = RepositoryFactory.eINSTANCE.createRepository();
		result.setEntityName(enitiyName);
	}
	
	public Repository build() {
		return result;
	}
	
	public FluentPCMRepositoryBuilder addComponent(RepositoryComponent component) {
		result.getComponents__Repository().add(component);
		return this;
	}
	
	public FluentPCMRepositoryBuilder addDataType(DataType dataType) {
		result.getDataTypes__Repository().add(dataType);
		return this;
	}

	public FluentPCMRepositoryBuilder addInterface(Interface _interface) {
		result.getInterfaces__Repository().add(_interface);
		return this;
	}
	
}
