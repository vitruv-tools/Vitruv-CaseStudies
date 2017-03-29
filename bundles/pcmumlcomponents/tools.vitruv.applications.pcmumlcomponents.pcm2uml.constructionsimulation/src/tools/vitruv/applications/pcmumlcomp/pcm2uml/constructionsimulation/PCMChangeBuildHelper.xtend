package tools.vitruv.applications.pcmumlcomp.pcm2uml.constructionsimulation

import tools.vitruv.extensions.constructionsimulation.traversal.util.ChangeBuildHelper
import org.palladiosimulator.pcm.core.entity.Entity
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.root.RootFactory
import tools.vitruv.framework.change.echange.EChange
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.CompositeComponent
import org.palladiosimulator.pcm.repository.Interface
import org.palladiosimulator.pcm.repository.Role
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.InnerDeclaration
import org.eclipse.emf.common.util.EList
import org.palladiosimulator.pcm.repository.ProvidesComponentType
import org.eclipse.emf.common.util.BasicEList
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.ReferenceFactory
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.CompleteComponentType
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.repository.Parameter

class PCMChangeBuildHelper extends ChangeBuildHelper {
	
	/**
	 * Creates the change from a repository
	 * 
	 * @param source
	 * 			: Repository to be traversed
	 * @return : CreateRootEObject Change
	 */
	static def EChange createChangeFromRepository(Repository source) {
		createChangeFromRootEntity(source)
	}
	
    /**
     * Creates the change from a system.
     *
     * @param source
     *            the source
     * @return the e change
     */
	static def EChange createChangeFromSystem(org.palladiosimulator.pcm.system.System source) {
		createChangeFromRootEntity(source)
	}
	
	/**
     * Creates the change from root entity.
     *
     * @param source
     *            the source
     * @return the e change
     */
	static def EChange createChangeFromRootEntity(Entity source) {
		val InsertRootEObject<EObject> change = RootFactory.eINSTANCE.createInsertRootEObject()
		change.setNewValue(source)
		return change
	}
	
	/**
     * Creates a change model from a BasicComponent element.
     *
     * @param source
     *            : BasicComponent to be traversed
     * @return : CreateNonRootEObject Change
     */
	static def EChange createChangeFromBasicComponent(BasicComponent source) {
		createSingleAddNonRootEObjectInListChange(source)
	}
	
	/**
     * Creates the change from composite component.
     *
     * @param source
     *            the source
     * @return the e change
     */
	static def EChange createChangeFromCompositeComponent(CompositeComponent source) {
		createSingleAddNonRootEObjectInListChange(source)
	}
	
	/**
     * Creates the change from interface.
     *
     * @param source
     *            the source
     * @return the e change
     */
	static def EChange createChangeFromInterface(Interface source) {
		createSingleAddNonRootEObjectInListChange(source)
	}
	
	/**
     * Creates the change from role.
     *
     * @param source
     *            the source
     * @return the e change
     */
	static def EChange createChangeFromRole(Role source) {
		createSingleAddNonRootEObjectInListChange(source)
	}
	
	/**
     * Creates the change from data type.
     *
     * @param source
     *            the source
     * @return the e change
     */
	static def EChange createChangeFromDataType(DataType source) {
		createSingleAddNonRootEObjectInListChange(source)
	}
	
	/**
     * Creates the change from inner declaration.
     *
     * @param source
     *            the source
     * @return the e change
     */
	static def EChange createChangeFromInnerDeclaration(InnerDeclaration source) {
		createSingleAddNonRootEObjectInListChange(source)
	}
	
	protected static def EList<EChange> createChangeFromProvidingEntity(InterfaceProvidingEntity component) {
		val EList<EChange> compositeChanges = new BasicEList<EChange>()
		val InsertEReference<EObject, EObject> componentChange = ReferenceFactory.eINSTANCE.createInsertEReference()
		componentChange.newValue = component
		
		compositeChanges += componentChange
		
		for (role : component.providedRoles_InterfaceProvidingEntity) {
			compositeChanges += createChangeFromRole(role)
		}
		
		return compositeChanges
	}
	
	/**
	 * Creates the change from provides component.
	 * 
	 * @param component
	 * 			the component
	 * @return the e list
	 */
	static def EList<EChange> createChangeFromProvidesComponent(ProvidesComponentType component) {
		createChangeFromProvidingEntity(component)
	}
	
	/**
     * Creates the change from complete component.
     *
     * @param component
     *            the component
     * @return the e list
     */
	static def EList<EChange> createChangeFromCompleteComponent(CompleteComponentType component) {
		val EList<EChange> compositeChanges = createChangeFromProvidingEntity(component)
		
		for (role : component.requiredRoles_InterfaceRequiringEntity) {
			compositeChanges += createChangeFromRole(role)
		}
		
		return compositeChanges
	}
	
	/**
     * Creates the change from signature.
     *
     * @param source
     *            the source
     * @return the e change
     */
	static def EChange createChangeFromSignature(OperationSignature source) {
		createSingleAddNonRootEObjectInListChange(source)
	}
	
    /**
     * Creates the change from parameter.
     *
     * @param source
     *            the source
     * @return the e change
     */
    static def EChange createChangeFromParameter(Parameter source) {
        return createSingleAddNonRootEObjectInListChange(source);
}
	
}