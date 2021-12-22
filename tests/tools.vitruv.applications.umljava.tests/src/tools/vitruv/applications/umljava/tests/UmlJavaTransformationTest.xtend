package tools.vitruv.applications.umljava.tests

import org.apache.log4j.Logger
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.UMLFactory

import static tools.vitruv.domains.java.util.JavaPersistenceHelper.*
import org.junit.jupiter.api.BeforeEach
import java.nio.file.Path
import static tools.vitruv.testutils.matchers.ModelMatchers.isResource
import static tools.vitruv.testutils.matchers.ModelMatchers.isNoResource
import static org.hamcrest.MatcherAssert.assertThat
import tools.vitruv.applications.umljava.UmlToJavaChangePropagationSpecification
import tools.vitruv.framework.vsum.views.View
import tools.vitruv.framework.vsum.views.ViewTypeFactory
import static org.junit.jupiter.api.Assertions.assertNotNull
import org.eclipse.uml2.uml.UMLPackage
import org.emftext.language.java.containers.CompilationUnit
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.getUniqueUmlModel
import tools.vitruv.testutils.ViewBasedVitruvApplicationTest
import java.util.Collection

class UmlJavaTransformationTest extends ViewBasedVitruvApplicationTest {
	protected static val Logger logger = Logger.getLogger(UmlJavaTransformationTest)

	static val MODEL_FILE_EXTENSION = "uml"
	static val MODEL_NAME = "model"

	private def Path getProjectModelPath(String modelName) {
		Path.of("model").resolve(modelName + "." + MODEL_FILE_EXTENSION)
	}
	
	override protected getChangePropagationSpecifications() {
		return #[new UmlToJavaChangePropagationSpecification()]
	}

	@BeforeEach
	def protected void setup() {
		val umlModel = UMLFactory.eINSTANCE.createModel()
		umlModel.name = MODEL_NAME
		createUmlModel[name = MODEL_NAME]
	}

	def protected assertJavaFileExists(String fileName, String[] namespaces) {
		assertThat(getUri(Path.of(buildJavaFilePath(fileName + ".java", namespaces))), isResource)
	}

	def protected assertJavaFileNotExists(String fileName, String[] namespaces) {
		assertThat(getUri(Path.of(buildJavaFilePath(fileName + ".java", namespaces))), isNoResource)
	}
	
	protected def void createUmlModel((Model) => void modelInitialization) {
		val umlView = createUmlView()
		val root = umlView.createRoot(UMLPackage.eINSTANCE.model, MODEL_NAME.projectModelPath.uri)
		modelInitialization.apply(root as Model)
		umlView.commitChanges()
	}
	
	protected def View createUmlView() {
		createViewOfElements("UML", #{Model})
	}
	
	protected def View createJavaClassesView() {
		createViewOfElements("Java classes", #{CompilationUnit})
	}
	
	protected def View createUmlAndJavaClassesView() {
		createViewOfElements("UML and Java classes", #{CompilationUnit, Model})
	}
	
	private def View createViewOfElements(String viewNme, Collection<Class<?>> rootTypes) {
		val selector = virtualModel.createSelector(ViewTypeFactory.createBasicViewType(viewNme))
		
		for (rootElement : selector.selectableElements.filter[element | rootTypes.exists[it.isInstance(element)]]) {
			selector.setSelected(rootElement, true)	
		}
		val view = selector.createView()
		assertNotNull(view, "View must not be null")
		return view
	}
	
	/**
	 * Changes the UML model in the UML view and commits the performed changes.
	 * 
	 * Precondition: Can only be applied if single UML model exists
	 */
	protected def void changeUmlModel((Model) => void modelModification) {
		val umlView = createUmlView()
		modelModification.apply(umlView.uniqueUmlModel)
		umlView.commitChanges()
	}

}