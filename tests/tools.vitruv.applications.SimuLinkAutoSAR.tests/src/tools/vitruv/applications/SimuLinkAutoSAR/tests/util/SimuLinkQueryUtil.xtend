package tools.vitruv.applications.simulinkautosar.tests.util

import edu.kit.ipd.sdq.activextendannotations.Utility
import edu.kit.ipd.sdq.metamodels.simulink.SimulinkModel
import edu.kit.ipd.sdq.metamodels.simulink.SimulinkElement
import edu.kit.ipd.sdq.metamodels.simulink.SimuLinkPackage
import tools.vitruv.framework.views.View

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import edu.kit.ipd.sdq.metamodels.simulink.Block
import edu.kit.ipd.sdq.metamodels.simulink.SubSystem

/*
 * 
 * Utility Class for Simulink 
 * Contains Methods to claim and find SimuLinkElements of different types
 * 
 */


@Utility
class SimuLinkQueryUtil {
	
	/*
	 * Returns a SimuLinkModel filtered by name in the RootObjects of the view.
	 * If no model with the name is found an error is thrown by the claimOne 
	 */
	static def SimulinkModel claimSimuLinkModel(View view, String modelname) {
		view.getRootObjects(SimulinkModel).filter[it.name == modelname].claimOne
	}
	
	static def getSimuLinkClassifiers(View view) {
		view.getRootObjects(SimuLinkPackage).map[EClassifiers].flatten
	}
	
	
	static def <T extends SimulinkElement> Iterable<T> getSimuLinkElementsOfType(View view, Class<T> type) {
		view.getSimuLinkClassifiers.filter(type)
	}
	
	static def getBlocksOfModel(View view, String elementName){
		view.getRootObjects(SimulinkModel).map[contains].flatten
	}
	
	static def claimSimuLinkBlock(View view, String blockName){
		view.claimSimuLinkElement(Block,blockName)
	}
	
	
	static def <T extends SimulinkElement> Block claimSimuLinkElement(View view, Class<T> simuLinkType,
		String elementName) {
		//view.getSimuLinkElementsOfType(simuLinkType).filter[
	 	//	it.name == elementName
		//].claimOne
		getBlocksOfModel(view,elementName).filter[
		 	it.name == elementName
		].claimOne
	}
	
	
	static def claimSimuLinkBlockOfSubsystem(SubSystem subsystem, String blockname){
		subsystem.subBlocks.filter[it.name == blockname].claimOne
	}
	
	static def claimSimuLinkPort(Block block, String portName){
		block.ports.filter[it.name == portName].claimOne
	}
	
	static def claimSimuLinkConnection(View view, String connectionName){
		view.getRootObjects(SimulinkModel).map[connection].flatten.filter[
			it.name == connectionName
		].claimOne
	}
	
	
	/* 
	static def help(UserInteractor userInteractor){
		val userSelection = userInteractor.singleSelectionDialogBuilder.message(
					"Please select whether you want it to be a PortBlock or not ").choices(["Block","NoBlock"])
				).startInteraction
	}
	*/
}
