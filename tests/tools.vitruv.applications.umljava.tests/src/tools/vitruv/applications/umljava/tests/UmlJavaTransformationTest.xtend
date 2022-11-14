package tools.vitruv.applications.umljava.tests

import java.nio.file.Path
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.applications.umljava.JavaToUmlChangePropagationSpecification
import tools.vitruv.applications.umljava.UmlToJavaChangePropagationSpecification
import tools.vitruv.applications.umljava.tests.util.JavaUmlClassifierEqualityValidation
import tools.vitruv.applications.umljava.tests.util.JavaUmlViewFactory
import tools.vitruv.applications.util.temporary.java.JavaSetup
import tools.vitruv.framework.views.View
import tools.vitruv.testutils.RegisterMetamodelsInStandalone
import tools.vitruv.testutils.ViewBasedVitruvApplicationTest

import static extension tools.vitruv.applications.testutility.uml.UmlQueryUtil.*

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
	
	override protected enableTransitiveCyclicChangePropagation() {
		false
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
