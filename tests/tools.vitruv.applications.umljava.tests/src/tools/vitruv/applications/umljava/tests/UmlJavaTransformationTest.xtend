package tools.vitruv.applications.umljava.tests

import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.UMLFactory

import static tools.vitruv.domains.java.util.JavaPersistenceHelper.*
import java.nio.file.Path
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
import static extension tools.vitruv.applications.umljava.tests.util.UmlQueryUtil.*
import tools.vitruv.applications.umljava.tests.util.JavaUmlClassifierEqualityValidation
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.URI
import org.junit.jupiter.api.BeforeEach
import static tools.vitruv.applications.umljava.tests.util.TransformationDirectionConfiguration.configureUnidirectionalExecution
import org.emftext.language.java.JavaClasspath
import tools.vitruv.domains.java.JamoppLibraryHelper

abstract class UmlJavaTransformationTest extends ViewBasedVitruvApplicationTest {
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
	def setupTransformationDirection() {
		// Reset Java classpath before every test to ensure that caches are reset
		// and not objects are stored and produce memory leaks
		JavaClasspath.reset()
		JamoppLibraryHelper.registerStdLib()
		configureUnidirectionalExecution()
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

	protected def void createUmlModel((Model)=>void modelInitialization) {
		changeView(createUmlView()) [
			val umlModel = UMLFactory.eINSTANCE.createModel
			createAndRegisterRoot(umlModel, UML_MODEL_NAME.projectModelPath.uri)
			modelInitialization.apply(umlModel)
		]
	}

	protected def void createJavaCompilationUnit((CompilationUnit)=>void compilationUnitInitialization) {
		changeJavaView [
			val compilationUnit = ContainersFactory.eINSTANCE.createCompilationUnit
			compilationUnitInitialization.apply(compilationUnit)
			createAndRegisterRoot(compilationUnit, Path.of(buildJavaFilePath(compilationUnit)).uri)
		]
	}

	protected def void createJavaPackage((Package)=>void packageInitialization) {
		changeJavaView [
			val package = ContainersFactory.eINSTANCE.createPackage
			packageInitialization.apply(package)
			createAndRegisterRoot(package, Path.of(buildJavaFilePath(package)).uri)
		]
	}

	protected def void moveJavaRootElement(View view, JavaRoot rootElement) {
		view.moveRoot(rootElement, Path.of(buildJavaFilePath(rootElement)).uri)
	}

	private def View createUmlView() {
		createViewOfElements("UML", #{Model})
	}

	private def View createJavaView() {
		createViewOfElements("Java packages and classes", #{Package, CompilationUnit})
	}

	private def View createUmlAndJavaClassesView() {
		createViewOfElements("UML and Java classes", #{CompilationUnit, Model})
	}

	private def View createUmlAndJavaPackagesView() {
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
	 * Changes the given view according to the given modification function, commits the performed changes
	 * and closes the view afterwards.
	 */
	private def void changeView(View view, (View)=>void modelModification) {
		modelModification.apply(view)
		view.commitChanges()
		view.close()
	}

	/**
	 * Changes the UML view containing all UML models as root elements according to the given modification 
	 * function, commits the performed changes and closes the view afterwards.
	 */
	protected def void changeUmlView((View)=>void modelModification) {
		changeView(createUmlView) [
			modelModification.apply(it)
		]
	}

	/**
	 * Changes the Java view containing all Java packages and classes as root elements according to the 
	 * given modification function, commits the performed changes and closes the view afterwards.
	 */
	protected def void changeJavaView((View)=>void modelModification) {
		changeView(createJavaView) [
			modelModification.apply(it)
		]
	}

	/**
	 * Validates the given view by applying the validation function and closes the view afterwards.
	 */
	private def void validateView(View view, (View)=>void viewValidation) {
		viewValidation.apply(view)
		view.close()
	}

	/**
	 * Validates the UML view containing all UML models by applying the validation function
	 * and closes the view afterwards.
	 */
	protected def void validateUmlView((View)=>void viewValidation) {
		validateView(createUmlView) [
			viewValidation.apply(it)
		]
	}

	/**
	 * Validates the Java view containing all packages and classes by applying the validation function
	 * and closes the view afterwards.
	 */
	protected def void validateJavaView((View)=>void viewValidation) {
		validateView(createJavaView) [
			viewValidation.apply(it)
		]
	}

	/**
	 * Validates the Java and UML view containing all UML models and Java classes by applying the 
	 * validation function and closes the view afterwards.
	 */
	protected def void validateUmlAndJavaClassesView((View)=>void viewValidation) {
		validateView(createUmlAndJavaClassesView) [
			viewValidation.apply(it)
		]
	}

	/**
	 * Validates the Java and UML view containing all UML models and Java packages by applying the 
	 * validation function and closes the view afterwards.
	 */
	protected def void validateUmlAndJavaPackagesView((View)=>void viewValidation) {
		validateView(createUmlAndJavaPackagesView) [
			viewValidation.apply(it)
		]
	}

}
