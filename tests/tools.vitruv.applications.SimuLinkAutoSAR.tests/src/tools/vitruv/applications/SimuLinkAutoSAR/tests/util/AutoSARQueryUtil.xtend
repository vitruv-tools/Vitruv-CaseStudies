package tools.vitruv.applications.simulinkautosar.tests.util

import edu.kit.ipd.sdq.metamodels.autosar.AutoSARElement
import edu.kit.ipd.sdq.metamodels.autosar.AutoSARModel
import edu.kit.ipd.sdq.metamodels.autosar.Port
import edu.kit.ipd.sdq.metamodels.autosar.SwComponent
import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.framework.views.View

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import edu.kit.ipd.sdq.metamodels.autosar.CompositeSwComponent
import edu.kit.ipd.sdq.metamodels.autosar.AtomicSwComponent


/*
 * 
 * Utility Class for AutoSAR 
 * Contains Methods to claim and find AutoSARElements of different types
 * The AutoSAR Element was added to the meta model for easier handling and derivation of the name attribute to all AutoSARElements 
 * 
 */


@Utility
class AutoSARQueryUtil {
	
	static def AutoSARModel claimAutoSARModel(View view, String name) {
		view.getRootObjects(AutoSARModel).filter[it.name == name].claimOne
	}
	
	static def <T extends AutoSARElement> SwComponent claimAutoSARElement(View view, Class<T> element,
		String elementName) {
		view.getRootObjects(AutoSARModel).map[swcomponent].flatten.filter[
		 	it.name == elementName
		].claimOne
	}
	
	static def <T extends AutoSARElement> CompositeSwComponent claimAutoSARCompositeSwComponent(View view, Class<T> element,
		String elementName) {
		
		view.getRootObjects(AutoSARModel).map[swcomponent].flatten.filter[
 			it.name == elementName
		].claimOne as CompositeSwComponent
		
	}
	
	static def AtomicSwComponent claimAutoSARBlockinComposite(CompositeSwComponent compositeComponent, String atomicComponentName){
		compositeComponent.atomicswcomponent.filter[it.name == name].claimOne
	}
	
	static def Port claimAutoSARPort(SwComponent swComponent, String portname){
		swComponent.port.filter[it.name == portname].claimOne	
	}
}
