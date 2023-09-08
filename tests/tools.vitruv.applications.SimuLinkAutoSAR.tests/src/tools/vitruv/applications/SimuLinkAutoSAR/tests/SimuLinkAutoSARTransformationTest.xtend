package tools.vitruv.applications.simulinkautosar.tests


import java.nio.file.Path
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach 
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.applications.simulinkautosar.AutoSARToSimuLinkChangePropagationSpecification
import tools.vitruv.applications.simulinkautosar.SimuLinkToAutoSARChangePropagationSpecification
import tools.vitruv.framework.views.View
import tools.vitruv.testutils.RegisterMetamodelsInStandalone
import tools.vitruv.testutils.ViewBasedVitruvApplicationTest

import static extension tools.vitruv.applications.simulinkautosar.tests.util.SimuLinkQueryUtil.*
import static extension tools.vitruv.applications.simulinkautosar.tests.util.AutoSARQueryUtil.*
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import tools.vitruv.applications.simulinkautosar.tests.util.SimuLinkAutoSARViewFactory
import tools.vitruv.applications.simulinkautosar.tests.util.SimuLinkAutoSARClassifierEqualityValidation

@ExtendWith(RegisterMetamodelsInStandalone)
abstract class SimuLinkAutoSARTransformationTest extends ViewBasedVitruvApplicationTest {
	protected var extension SimuLinkAutoSARViewFactory viewFactory
	
	protected val extension SimuLinkAutoSARClassifierEqualityValidation = new SimuLinkAutoSARClassifierEqualityValidation(
		SIMULINK_MODEL_NAME, [ viewApplication |
			validateAutoSARAndSimuLinkClassesView [
				viewApplication.apply(it)
			]
		])
	
	@Accessors(PROTECTED_GETTER)
	static val MODEL_FILE_EXTENSION = "arxml"
	@Accessors(PROTECTED_GETTER)
	static val SIMULINK_MODEL_NAME = "Model"
	@Accessors(PROTECTED_GETTER)
	static val AUTOSAR_MODEL_NAME = "Model"
	@Accessors(PROTECTED_GETTER)
	static val MODEL_FOLDER_NAME = "model"
	
	
	@BeforeAll
	def static void setupSimuLinkFactories() {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
	}
	
	
	@BeforeEach
	def final void setupViewFactory() {
		viewFactory = new SimuLinkAutoSARViewFactory(virtualModel)
	}
	
	override protected enableTransitiveCyclicChangePropagation() {
		false
	}
	
	protected def getDefaultSimuLinkModel(View view) {
		view.claimSimuLinkModel(SIMULINK_MODEL_NAME)
	}
	
	protected def getDefaultAutoSARModel(View view) {
		view.claimAutoSARModel(AUTOSAR_MODEL_NAME)
	}

	protected def Path getProjectModelPath(String modelName) {
		Path.of(MODEL_FOLDER_NAME).resolve(modelName + "." + MODEL_FILE_EXTENSION)
	}

	override protected getChangePropagationSpecifications() {
		return #[new AutoSARToSimuLinkChangePropagationSpecification(), new SimuLinkToAutoSARChangePropagationSpecification()]
	}

	protected def void createAndRegisterRoot(View view, EObject rootObject, URI persistenceUri) {
		view.registerRoot(rootObject, persistenceUri)
	}
	
	

}
