package tools.vitruv.applications.pcmumlcomp.uml2pcm.constructionsimulation

import tools.vitruv.extensions.constructionsimulation.traversal.util.ChangeBuildHelper
import org.eclipse.uml2.uml.Model
import tools.vitruv.framework.change.echange.EChange
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.Property
import tools.vitruv.framework.change.echange.root.RootFactory
import org.eclipse.uml2.uml.Usage
import org.eclipse.uml2.uml.InterfaceRealization
import org.eclipse.uml2.uml.Component

class UmlChangeBuildHelper extends ChangeBuildHelper {
	
	def static EChange createChangeFromModel(Model model) {
		val change = RootFactory.eINSTANCE.createInsertRootEObject()
		change.newValue = model
		return change
	}
	
	def static EChange createChangeFromPrimitiveDataType(PrimitiveType source) {
		return createSingleAddNonRootEObjectInListChange(source)
	}
	
	def static EChange createChangeFromDataType(DataType source) {
		return createSingleAddNonRootEObjectInListChange(source)
	}
	
	def static EChange createChangeFromInterface(Interface source) {
		return createSingleAddNonRootEObjectInListChange(source)
	}
	
	def static EChange createChangeFromOperation(Operation source) {
		return createSingleAddNonRootEObjectInListChange(source)
	}
	
	def static EChange createChangeFromParameter(Parameter source) {
		return createSingleAddNonRootEObjectInListChange(source)
	}
	
	def static EChange createChangeFromProperty(Property source) {
		return createSingleAddNonRootEObjectInListChange(source)
	}
	
	def static EChange createChangeFromUsage(Usage source) {
		return createSingleAddNonRootEObjectInListChange(source)
	}
	
	def static EChange createChangeFromInterfaceRealization(InterfaceRealization source) {
		return createSingleAddNonRootEObjectInListChange(source)
	}
	
	def static EChange createChangeFromComponent(Component source) {
		return createSingleAddNonRootEObjectInListChange(source)
	}
	
}