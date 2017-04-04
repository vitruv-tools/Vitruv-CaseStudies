package tools.vitruv.applications.pcmumlcomp.uml2pcm.constructionsimulation

import java.util.List
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.URI
import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.PrimitiveType
import tools.vitruv.extensions.constructionsimulation.traversal.EMFTraversalStrategy
import tools.vitruv.extensions.constructionsimulation.traversal.ITraversalStrategy
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.Property
import tools.vitruv.framework.userinteraction.impl.UserInteractor
import org.eclipse.uml2.uml.Usage
import org.eclipse.uml2.uml.InterfaceRealization
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.change.util.ChangeRecorder
import tools.vitruv.framework.change.recording.AtomicEMFChangeRecorder

class ModelTraversalStrategy extends EMFTraversalStrategy implements ITraversalStrategy<Model> {
	
	protected EList<VitruviusChange> changeList
	
	protected VURI vuri
	
	protected UserInteractor userInteractor
	
	override EList<VitruviusChange> traverse(Model entity, URI uri, EList<VitruviusChange> existingChanges) throws UnsupportedOperationException {
		vuri = VURI.getInstance(uri)
		val resourceSet = new ResourceSetImpl
		val resource = resourceSet.createResource(uri)
		val changeRecorder = new AtomicEMFChangeRecorder
		changeRecorder.beginRecording(VURI.getInstance(uri), #{resource})
		changeList = new BasicEList<VitruviusChange>()
		traverseModel(entity)
		traversePackagedElements(entity)
		val changes = changeRecorder.endRecording
		changes.forEach[changeList.add(it)]
		return changeList
		//return changeList
	}
	
	def void setUserInteractor(UserInteractor userInteractor) {
		this.userInteractor = userInteractor
	}
	
	def UserInteractor getUserInteractor() {
		return this.userInteractor
	}
	
	def protected void traverseModel(Model model) {
		val change = UmlChangeBuildHelper.createChangeFromModel(model)
		addChange(VitruviusChangeFactory.instance.createConcreteChange(change, vuri), changeList)
	}
	
	def protected void traversePackagedElements(Model model) {
		val packagedElements = model.packagedElements
		traverseTypes(packagedElements.filter(DataType).toList)
		traverseInterfaces(packagedElements.filter(Interface).toList)
		traverseComponents(packagedElements.filter(Component).toList)
	}
	
	def protected void traverseTypes(List<DataType> source) {
		traversePrimitiveTypes(source.filter(PrimitiveType).toList)
		for (t : source) {
			if (!(t instanceof PrimitiveType)) {
				traverseDataType(t)
			}
		}
	}
	
	def protected void traversePrimitiveTypes(List<PrimitiveType> source) {
		for (t : source) {
			val change = UmlChangeBuildHelper.createChangeFromPrimitiveDataType(t)
			addChange(VitruviusChangeFactory.instance.createConcreteChange(change, vuri), changeList)
		}
	}
	
	def protected void traverseDataType(DataType source) {
		val change = UmlChangeBuildHelper.createChangeFromDataType(source)
		addChange(VitruviusChangeFactory.instance.createConcreteChange(change, vuri), changeList)
		traverseProperties(source.ownedAttributes)
	}
	
	def protected void traverseProperties(EList<Property> source) {
		for (p : source) {
			val change = UmlChangeBuildHelper.createChangeFromProperty(p)
			addChange(VitruviusChangeFactory.instance.createConcreteChange(change, vuri), changeList)
		}
	}
	
	def protected void traverseInterfaces(List<Interface> source) {
		for (i : source) {
			val change = UmlChangeBuildHelper.createChangeFromInterface(i)
			addChange(VitruviusChangeFactory.instance.createConcreteChange(change, vuri), changeList)
			for (op : i.ownedOperations) {
				traverseOperation(op)
			}
		}
	}
	
	def protected void traverseOperation(Operation source) {
		val change = UmlChangeBuildHelper.createChangeFromOperation(source)
		addChange(VitruviusChangeFactory.instance.createConcreteChange(change, vuri), changeList)
		for (p : source.ownedParameters) {
			traverseParameter(p)
		}
	}
	
	def protected void traverseParameter(Parameter source) {
		val change = UmlChangeBuildHelper.createChangeFromParameter(source)
        addChange(VitruviusChangeFactory.instance.createConcreteChange(change, vuri), changeList)
	}
	
	def protected void traverseComponents(List<Component> source) {
		// TODO: decide based on structure what kind of component to create
		for (c : source) {
			val change = UmlChangeBuildHelper.createChangeFromComponent(c)
			addChange(VitruviusChangeFactory.instance.createConcreteChange(change, vuri), changeList)
			traverseUsages(c.ownedElements.filter(Usage).toList)
			traverseInterfaceRealizations(c.ownedElements.filter(InterfaceRealization).toList)
		}
	}
	
	def protected void traverseUsages(List<Usage> source) {
		for (u : source) {
			val change = UmlChangeBuildHelper.createChangeFromUsage(u)
			addChange(VitruviusChangeFactory.instance.createConcreteChange(change, vuri), changeList)
		}
	}
	
	def protected traverseInterfaceRealizations(List<InterfaceRealization> source) {
		for (ir : source) {
			val change = UmlChangeBuildHelper.createChangeFromInterfaceRealization(ir)
			addChange(VitruviusChangeFactory.instance.createConcreteChange(change, vuri), changeList)
			
		}
	}
}