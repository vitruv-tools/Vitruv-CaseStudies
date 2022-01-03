package tools.vitruv.applications.umljava.tests

import org.apache.log4j.Logger
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.UMLFactory

import static tools.vitruv.domains.java.util.JavaPersistenceHelper.*
import java.nio.file.Path
import static tools.vitruv.testutils.matchers.ModelMatchers.isResource
import static tools.vitruv.testutils.matchers.ModelMatchers.isNoResource
import static org.hamcrest.MatcherAssert.assertThat
import tools.vitruv.applications.umljava.UmlToJavaChangePropagationSpecification
import tools.vitruv.framework.vsum.views.View
import tools.vitruv.framework.vsum.views.ViewTypeFactory
import static org.junit.jupiter.api.Assertions.assertNotNull
import org.emftext.language.java.containers.CompilationUnit
import tools.vitruv.testutils.ViewBasedVitruvApplicationTest
import java.util.Collection
import org.emftext.language.java.containers.Package
import org.emftext.language.java.containers.ContainersFactory
import tools.vitruv.applications.umljava.JavaToUmlChangePropagationSpecification
import org.emftext.language.java.containers.JavaRoot
import org.eclipse.xtend.lib.annotations.Accessors

class UmlJavaTransformationTest extends ViewBasedVitruvApplicationTest {
	protected static val Logger logger = Logger.getLogger(UmlJavaTransformationTest)

	static val MODEL_FILE_EXTENSION = "uml"
	@Accessors(PROTECTED_GETTER)
	static val UML_MODEL_NAME = "model"
	@Accessors(PROTECTED_GETTER)
	static val MODEL_FOLDER_NAME = "model"

	private def Path getProjectModelPath(String modelName) {
		Path.of(MODEL_FOLDER_NAME).resolve(modelName + "." + MODEL_FILE_EXTENSION)
	}

	override protected getChangePropagationSpecifications() {
		return #[new UmlToJavaChangePropagationSpecification(), new JavaToUmlChangePropagationSpecification()]
	}

	def protected assertJavaFileExists(String fileName, String[] namespaces) {
		assertThat(getUri(Path.of(buildJavaFilePath(fileName + ".java", namespaces))), isResource)
	}

	def protected assertJavaFileNotExists(String fileName, String[] namespaces) {
		assertThat(getUri(Path.of(buildJavaFilePath(fileName + ".java", namespaces))), isNoResource)
	}

	protected def void createUmlModel((Model)=>void modelInitialization) {
		changeView(createUmlView()) [
			val umlModel = UMLFactory.eINSTANCE.createModel
			registerRoot(umlModel, UML_MODEL_NAME.projectModelPath.uri)
			modelInitialization.apply(umlModel)
		]
	}

	protected def void createJavaCompilationUnit((CompilationUnit)=>void compilationUnitInitialization) {
		changeView(createJavaClassesView()) [
			val compilationUnit = ContainersFactory.eINSTANCE.createCompilationUnit
			compilationUnitInitialization.apply(compilationUnit)
			registerRoot(compilationUnit, Path.of(buildJavaFilePath(compilationUnit)).uri)
		]
	}

	protected def void createJavaPackage((Package)=>void packageInitialization) {
		changeView(createJavaPackagesView()) [
			val package = ContainersFactory.eINSTANCE.createPackage
			packageInitialization.apply(package)
			registerRoot(package, Path.of(buildJavaFilePath(package)).uri)
		]
	}

	protected def void moveJavaRootElement(View view, JavaRoot rootElement) {
		view.moveRoot(rootElement, Path.of(buildJavaFilePath(rootElement)).uri)
	}

	protected def View createUmlView() {
		createViewOfElements("UML", #{Model})
	}

	protected def View createJavaClassesView() {
		createViewOfElements("Java classes", #{CompilationUnit})
	}

	protected def View createJavaPackagesView() {
		createViewOfElements("Java packages", #{Package})
	}

	protected def View createUmlAndJavaClassesView() {
		createViewOfElements("UML and Java classes", #{CompilationUnit, Model})
	}

	protected def View createUmlAndJavaPackagesView() {
		createViewOfElements("UML and Java packages", #{Package, Model})
	}

	private def View createViewOfElements(String viewNme, Collection<Class<?>> rootTypes) {
		val selector = virtualModel.createSelector(ViewTypeFactory.createBasicViewType(viewNme))

		for (rootElement : selector.selectableElements.filter[element|rootTypes.exists[it.isInstance(element)]]) {
			selector.setSelected(rootElement, true)
		}
		val view = selector.createView()
		assertNotNull(view, "View must not be null")
		return view
	}

	/**
	 * Changes the given view according to the given modification function and commits the performed changes.
	 */
	protected def void changeView(View view, (View)=>void modelModification) {
		modelModification.apply(view)
		view.commitChanges()
	}

}
