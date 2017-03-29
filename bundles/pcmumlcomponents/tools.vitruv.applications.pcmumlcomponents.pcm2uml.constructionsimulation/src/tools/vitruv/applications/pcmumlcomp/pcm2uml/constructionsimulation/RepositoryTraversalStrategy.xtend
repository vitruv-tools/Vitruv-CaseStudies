package tools.vitruv.applications.pcmumlcomp.pcm2uml.constructionsimulation

import tools.vitruv.extensions.constructionsimulation.traversal.EMFTraversalStrategy
import tools.vitruv.extensions.constructionsimulation.traversal.ITraversalStrategy
import org.palladiosimulator.pcm.repository.Repository
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.common.util.EList
import tools.vitruv.framework.change.description.VitruviusChange
import org.eclipse.emf.common.util.UniqueEList
import tools.vitruv.framework.util.datatypes.VURI
import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.Interface
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.CompositeComponent
import org.palladiosimulator.pcm.repository.ProvidesComponentType
import tools.vitruv.framework.change.description.CompositeContainerChange
import org.palladiosimulator.pcm.repository.CompleteComponentType
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.InnerDeclaration
import org.eclipse.emf.common.util.BasicEList

class RepositoryTraversalStrategy extends EMFTraversalStrategy implements ITraversalStrategy<Repository> {
	
	protected val EList<VitruviusChange> changeList = new UniqueEList<VitruviusChange>()
	
	private VURI vuri
	
	override EList<VitruviusChange> traverse(Repository entity, URI uri, EList<VitruviusChange> existingChanges)
		throws UnsupportedOperationException {
		
		if (existingChanges !== null) {
			throw new UnsupportedOperationException("TODO: auto-generated method stub")
		}
		
		val repository = entity
		vuri = VURI.getInstance(uri)
		
		val EList<DataType> dataTypes = repository.dataTypes__Repository
		val EList<RepositoryComponent> components = repository.components__Repository
		val EList<Interface> interfaces = repository.interfaces__Repository
		
		traverseRepository(repository)
		traversePrimitiveDataTypes(dataTypes)
		traverseCompositeDataTypes(dataTypes)
		traverseCollectionDataTypes(dataTypes)
		traverseComponents(components)
		traverseInterfaces(interfaces)
		traverseProvidesComponents(components)
		traverseCompleteComponents(components)
		traverseRoles(components)
		traverseSignaturesAndParameters(interfaces)
		traverseInnerDeclarations(dataTypes)
		
		return changeList
	}
	
	/**
     * Traverse repository.
     *
     * @param repository
     *            the repository
     */
	protected def traverseRepository(Repository repository) {
		val EChange repositoryChange = PCMChangeBuildHelper.createChangeFromRepository(repository)
		addChange(VitruviusChangeFactory.instance.createConcreteChange(repositoryChange, vuri), changeList)
	}
	
	/**
     * Traverse primitive data types.
     *
     * @param dataTypes
     *            the data types
     */
	protected def traversePrimitiveDataTypes(EList<DataType> dataTypes) {
		for (dataType : dataTypes) {
			if (dataType instanceof PrimitiveDataType) {
				addChange(VitruviusChangeFactory.instance.createConcreteChange(
					PCMChangeBuildHelper.createChangeFromDataType(dataType), vuri
				), changeList)
			}
		}
	}
	
	/**
     * Traverse composite data types.
     *
     * @param dataTypes
     *            the data types
     */
	protected def traverseCompositeDataTypes(EList<DataType> dataTypes) {
		for (dataType : dataTypes) {
			if (dataType instanceof CompositeDataType) {
				val EList<CompositeDataType> parents = dataType.parentType_CompositeDataType
				if (parents.empty) {
					addChange(VitruviusChangeFactory.instance.createConcreteChange(
						PCMChangeBuildHelper.createChangeFromDataType(dataType), vuri
					), changeList)
				} else {
					// TODO: traverse parents
				}
			}
		}
	}
	
	/**
     * Traverse collection data types.
     *
     * @param dataTypes
     *            the data types
     */
	protected def traverseCollectionDataTypes(EList<DataType> dataTypes) {
		for (dataType : dataTypes) {
			if (dataType instanceof CollectionDataType) {
				addChange(VitruviusChangeFactory.instance.createConcreteChange(
					PCMChangeBuildHelper.createChangeFromDataType(dataType), vuri
				), changeList)
			}
		}
	}
	
	/**
     * Traverse components.
     *
     * @param components
     *            the components
     */
	protected def traverseComponents(EList<RepositoryComponent> components) {
		for (component : components) {
			var EChange componentChange = null
			
			if (component instanceof BasicComponent) {
				componentChange = PCMChangeBuildHelper.createChangeFromBasicComponent(component)
			} else if (component instanceof CompositeComponent) {
				componentChange = PCMChangeBuildHelper.createChangeFromCompositeComponent(component)
			} else {
				return
			}
			
			addChange(VitruviusChangeFactory.instance.createConcreteChange(componentChange, vuri), changeList)
		}
	}
	
	/**
     * Traverse provides components.
     *
     * @param components
     *            the components
     */
    protected def traverseProvidesComponents(EList<RepositoryComponent> components) {
        for (component : components) {
            if (component instanceof ProvidesComponentType) {

            	val CompositeContainerChange compositeChange = VitruviusChangeFactory.instance.createCompositeContainerChange();

                val EList<EChange> providedComponentChanges = PCMChangeBuildHelper
                        .createChangeFromProvidesComponent(component);
                for (change : providedComponentChanges) {
                    compositeChange.addChange(VitruviusChangeFactory.instance.createConcreteChange(change, vuri));
                }

                addChange(compositeChange, changeList);
            }
        }
	}
	
	/**
     * Traverse complete components.
     *
     * @param components
     *            the components
     */
    protected def traverseCompleteComponents(EList<RepositoryComponent> components) {
        for (component : components) {
            if (component instanceof CompleteComponentType) {

                val CompositeContainerChange compositeChange = VitruviusChangeFactory.instance.createCompositeContainerChange();

                val EList<EChange> completeCompositeChanges = PCMChangeBuildHelper
                        .createChangeFromCompleteComponent(component);
                for (change : completeCompositeChanges) {
                    compositeChange.addChange(VitruviusChangeFactory.instance.createConcreteChange(change, vuri));
                }

                addChange(compositeChange, changeList);
            }
        }
	}
	
	/**
     * Traverse roles.
     *
     * @param components
     *            the components
     */
    protected def traverseRoles(EList<RepositoryComponent> components) {
        for (component : components) {
            if (component instanceof BasicComponent) {
                for (role : component.getProvidedRoles_InterfaceProvidingEntity()) {
                    addChange(VitruviusChangeFactory.instance.createConcreteChange(
                    	PCMChangeBuildHelper.createChangeFromRole(role), vuri
                    ), changeList);
                }
                for (role : component.getRequiredRoles_InterfaceRequiringEntity()) {
                    addChange(VitruviusChangeFactory.instance.createConcreteChange(
                    	PCMChangeBuildHelper.createChangeFromRole(role), vuri
                    ), changeList);
                }
            }
        }
	}
	
	/**
     * Traverse signatures and parameters.
     *
     * @param interfaces
     *            the interfaces
     */
	protected def traverseSignaturesAndParameters(EList<Interface> interfaces) {
		for (inter : interfaces) {
			if (inter instanceof OperationInterface) {
				for (signature : inter.signatures__OperationInterface) {
					addChange(VitruviusChangeFactory.instance.createConcreteChange(
						PCMChangeBuildHelper.createChangeFromSignature(signature), vuri
					), changeList)
					
					for (parameter : signature.parameters__OperationSignature) {
						addChange(VitruviusChangeFactory.instance.createConcreteChange(
							PCMChangeBuildHelper.createChangeFromParameter(parameter), vuri
						), changeList)
					}
				}
			}
		}
	}
	
	/**
     * Traverse inner declarations.
     *
     * @param dataTypes
     *            the data types
     */
	protected def traverseInnerDeclarations(EList<DataType> dataTypes) {
		for (dataType : dataTypes) {
			if (dataType instanceof CompositeDataType) {
				val EList<InnerDeclaration> innerTypes = dataType.innerDeclaration_CompositeDataType
				
				if (!innerTypes.empty) {
					for (innerDeclaration : innerTypes) {
						addChange(VitruviusChangeFactory.instance.createConcreteChange(
							PCMChangeBuildHelper.createChangeFromInnerDeclaration(innerDeclaration), vuri
						), changeList)
					}
				}
			}
		}
	}
	
	/**
     * Traverse interfaces
     *
     * @param interfaces
     *            the interfaces
     */
	protected def traverseInterfaces(EList<Interface> interfaces) {
		if (!interfaces.empty) {
			val EList<VitruviusChange> interfaceChanges = traverseInterfaceHierarchy(interfaces, vuri)
			for (change : interfaceChanges) {
				addChange(change, changeList)
			}
		}
	}
	
	protected def EList<VitruviusChange> traverseInterfaceHierarchy(EList<Interface> interfaces, VURI vuri) {
		val EList<Interface> traversedInterfaces = new BasicEList<Interface>()
		val EList<VitruviusChange> changes = new BasicEList<VitruviusChange>()
		var EChange interfaceChange = null
		
		while (traversedInterfaces.size < interfaces.size) {
			for (inter: interfaces) {
				if (inter.parentInterfaces__Interface.contains(inter)) {
					throw new IllegalStateException("Interface cannot inherit from itself")
				}
				if (traversedInterfaces.containsAll(inter.parentInterfaces__Interface)) {
					interfaceChange = PCMChangeBuildHelper.createChangeFromInterface(inter)
					changes.add(VitruviusChangeFactory.instance.createConcreteChange(interfaceChange, vuri))
					traversedInterfaces += inter
				}
			}
		}
		return changes
	}
	
}