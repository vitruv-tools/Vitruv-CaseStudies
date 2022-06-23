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
import org.emftext.language.java.JavaClasspath
import tools.vitruv.domains.java.JamoppLibraryHelper
import tools.vitruv.applications.umljava.tests.util.JavaUmlViewFactory

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

	@BeforeEach
	def void setupClasspathAndViewFactory() {
		// Reset Java classpath before every test to ensure that caches are reset
		// and not objects are stored and produce memory leaks
		JavaClasspath.reset()
		JamoppLibraryHelper.registerStdLib()
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
