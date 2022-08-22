package tools.vitruv.applications.umljava.tests

import java.nio.file.Path
import tools.vitruv.applications.umljava.UmlToJavaChangePropagationSpecification
import tools.vitruv.framework.views.View
import tools.vitruv.testutils.ViewBasedVitruvApplicationTest
import tools.vitruv.applications.umljava.JavaToUmlChangePropagationSpecification
import org.eclipse.xtend.lib.annotations.Accessors
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*
import tools.vitruv.applications.umljava.tests.util.JavaUmlClassifierEqualityValidation
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.URI
import org.junit.jupiter.api.BeforeEach
import static tools.vitruv.applications.umljava.tests.util.TransformationDirectionConfiguration.configureUnidirectionalExecution
import tools.vitruv.applications.umljava.tests.util.JavaUmlViewFactory
import tools.vitruv.applications.util.temporary.java.JavaSetup
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.testutils.RegisterMetamodelsInStandalone

@ExtendWith(RegisterMetamodelsInStandalone)
abstract class UmlJavaTransformationTest extends ViewBasedVitruvApplicationTest {
	protected var extension JavaUmlViewFactory viewFactory

	protected val extension JavaUmlClassifierEqualityValidation = new JavaUmlClassifierEqualityValidation(
		UML_MODEL_NAME, [ viewApplication |
			validateUmlAndJavaClassesView [
				viewApplication.apply(it)
			]
		], [it.getUri()])

	@Accessors(PROTECTED_GETTER)
	static val MODEL_FILE_EXTENSION = "uml"
	@Accessors(PROTECTED_GETTER)
	static val UML_MODEL_NAME = "model"
	@Accessors(PROTECTED_GETTER)
	static val MODEL_FOLDER_NAME = "model"
	
	@BeforeAll
	def static void setupJavaFactories() {
		JavaSetup.prepareFactories()
	}

	@BeforeEach
	def final void setupJavaClasspath() {
		JavaSetup.resetClasspathAndRegisterStandardLibrary()
	}
	
	@BeforeEach
	def final void setupViewFactory() {
		viewFactory = new JavaUmlViewFactory(virtualModel)
	}

	@BeforeEach
	def setupTransformationDirection() {
		configureUnidirectionalExecution(virtualModel)
	}

	protected def getDefaultUmlModel(View view) {
		view.claimUmlModel(UML_MODEL_NAME)
	}

	protected def Path getProjectModelPath(String modelName) {
		Path.of(MODEL_FOLDER_NAME).resolve(modelName + "." + MODEL_FILE_EXTENSION)
	}

	override protected getChangePropagationSpecifications() {
		return #[new UmlToJavaChangePropagationSpecification(), new JavaToUmlChangePropagationSpecification()]
	}

	protected def void createAndRegisterRoot(View view, EObject rootObject, URI persistenceUri) {
		view.registerRoot(rootObject, persistenceUri)
	}

}
