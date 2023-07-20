package tools.vitruv.applications.pcmumlclass.tests.helper;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

public class FluentPCMCompositeComponentBuilder {

	private final CompositeComponent result;

	public FluentPCMCompositeComponentBuilder(String name) {
		result = RepositoryFactory.eINSTANCE.createCompositeComponent();
		result.setEntityName(name);
	}

	public CompositeComponent build() {
		return result;
	}

	public FluentPCMCompositeComponentBuilder addAssemblyContext(String entityName,
			RepositoryComponent encapuslatedComponent) {
		AssemblyContext newAssemblyContext = CompositionFactory.eINSTANCE.createAssemblyContext();
		newAssemblyContext.setEntityName(entityName);
		newAssemblyContext.setEncapsulatedComponent__AssemblyContext(encapuslatedComponent);

		result.getAssemblyContexts__ComposedStructure().add(newAssemblyContext);
		return this;
	}

	public FluentPCMCompositeComponentBuilder addProvidedRole(String entityName, OperationInterface providedInterface) {
		OperationProvidedRole providedRole = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
		providedRole.setEntityName(entityName);
		providedRole.setProvidedInterface__OperationProvidedRole(providedInterface);

		result.getProvidedRoles_InterfaceProvidingEntity().add(providedRole);
		return this;
	}

	public FluentPCMCompositeComponentBuilder addRequiredRole(String entityName, OperationInterface requiredInterface) {
		OperationRequiredRole requiredRole = RepositoryFactory.eINSTANCE.createOperationRequiredRole();
		requiredRole.setEntityName(entityName);
		requiredRole.setRequiredInterface__OperationRequiredRole(requiredInterface);

		result.getRequiredRoles_InterfaceRequiringEntity().add(requiredRole);
		return this;
	}
}
