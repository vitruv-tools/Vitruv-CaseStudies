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
		UML_MODEL_NAME, [viewApplication |
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

	protected def View createUmlView() {
		createViewOfElements("UML", #{Model})
	}

	protected def View createJavaView() {
		createViewOfElements("Java packages and classes", #{Package, CompilationUnit})
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
	 * Changes the given view according to the given modification function, commits the performed changes
	 * and closes the view afterwards.
	 */
	protected def void changeView(View view, (View)=>void modelModification) {
		modelModification.apply(view)
		view.commitChanges()
		view.close()
	}

	protected def void changeUmlView((View)=>void modelModification) {
		changeView(createUmlView) [
			modelModification.apply(it)
		]
	}

	protected def void changeJavaView((View)=>void modelModification) {
		changeView(createJavaView) [
			modelModification.apply(it)
		]
	}

	/**
	 * Validates the given view by applying the validation functions and closes the view afterwards.
	 */
	protected def void validateView(View view, (View)=>void viewValidation) {
		viewValidation.apply(view)
		view.close()
	}

	protected def void validateUmlView((View)=>void viewValidation) {
		validateView(createUmlView) [
			viewValidation.apply(it)
		]
	}

	protected def void validateJavaView((View)=>void viewValidation) {
		validateView(createJavaView) [
			viewValidation.apply(it)
		]
	}

	protected def void validateUmlAndJavaClassesView((View)=>void viewValidation) {
		validateView(createUmlAndJavaClassesView) [
			viewValidation.apply(it)
		]
	}
	
	protected def void validateUmlAndJavaPackagesView((View)=>void viewValidation) {
		validateView(createUmlAndJavaPackagesView) [
			viewValidation.apply(it)
		]
	}

}
