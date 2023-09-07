package tools.vitruv.applications.simulinkautosar.tests.autosar2simulink

import edu.kit.ipd.sdq.metamodels.autosar.AutoSARFactory
import edu.kit.ipd.sdq.metamodels.autosar.AutoSARModel
import org.junit.jupiter.api.BeforeEach
import tools.vitruv.applications.simulinkautosar.tests.SimuLinkAutoSARTransformationTest
import edu.kit.ipd.sdq.metamodels.autosar.SwComponent

abstract class AbstractAutoSARToSimuLinkTest extends SimuLinkAutoSARTransformationTest {

	@BeforeEach
	def protected void setup() {
		createAutoSARModel[name = AUTOSAR_MODEL_NAME]
	}

	protected def void createAutoSARModel((AutoSARModel)=>void autoSARModelInitialization) {
		changeSimuLinkView [
			val autosarModel = AutoSARFactory.eINSTANCE.createAutoSARModel
			createAndRegisterRoot(autosarModel,AUTOSAR_MODEL_NAME.projectModelPath.uri)
			autoSARModelInitialization.apply(autosarModel)
		]
	}

	protected def void changeAutoSARModel((AutoSARModel)=>void modelModification) {
		changeAutoSARView [
			modelModification.apply(defaultAutoSARModel)
		]
	}
	
	private def void addComponentToRootModel(SwComponent newComponent, String name) {
		changeAutoSARModel [
			swcomponent += newComponent => [it.name = name]
		]
	}
	
	
	protected def void createAtomicSWComponentInModel(String componentName) {
		addComponentToRootModel(AutoSARFactory.eINSTANCE.createAtomicSwComponent(), componentName)
		assertSwComponentWithNameInRootModel(componentName)
	}
	
	
	protected def void createCompositeSWComponentInModel(String componentName){
		addComponentToRootModel(AutoSARFactory.eINSTANCE.createCompositeSwComponent(), componentName)
		
	}
	
	
}
