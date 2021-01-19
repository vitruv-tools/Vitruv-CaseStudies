package tools.vitruv.applications.umljava.tests.uml2java

import org.apache.log4j.Logger
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.UMLFactory

import static tools.vitruv.domains.java.util.JavaPersistenceHelper.*
import org.eclipse.uml2.uml.Operation
import tools.vitruv.applications.umljava.tests.util.AbstractUmlJavaTest
import org.junit.jupiter.api.BeforeEach
import java.nio.file.Path
import static tools.vitruv.testutils.matchers.ModelMatchers.isResource
import static tools.vitruv.testutils.matchers.ModelMatchers.isNoResource
import static org.hamcrest.MatcherAssert.assertThat
import tools.vitruv.applications.umljava.UmlToJavaChangePropagationSpecification

/**
 * Abstract super class for uml to java test cases.
 * Initializes a uml rootmodel.
 * 
 * @author Fei
 */
abstract class UmlToJavaTransformationTest extends AbstractUmlJavaTest {
	protected static val Logger logger = Logger.getLogger(UmlToJavaTransformationTest)

	static val MODEL_FILE_EXTENSION = "uml"
	static val MODEL_NAME = "model" // name of the uml rootmodel

	private def Path getProjectModelPath(String modelName) {
		Path.of("model").resolve(modelName + "." + MODEL_FILE_EXTENSION)
	}

	protected def Model getRootElement() {
		return Model.from(MODEL_NAME.projectModelPath)
	}

	override protected getChangePropagationSpecifications() {
		return #[new UmlToJavaChangePropagationSpecification()]
	}

	@BeforeEach
	def protected void setup() {
		val umlModel = UMLFactory.eINSTANCE.createModel()
		umlModel.name = MODEL_NAME
		resourceAt(MODEL_NAME.projectModelPath).startRecordingChanges => [
			contents += umlModel
		]
		propagate
	}

	def protected assertJavaFileExists(String fileName, String[] namespaces) {
		assertThat(getUri(Path.of(buildJavaFilePath(fileName + ".java", namespaces))), isResource)
	}

	def protected assertJavaFileNotExists(String fileName, String[] namespaces) {
		assertThat(getUri(Path.of(buildJavaFilePath(fileName + ".java", namespaces))), isNoResource)
	}

	/**
	 * Retrieves the first corresponding java field for a given uml property
	 */
	def protected getCorrespondingAttribute(Property uAttribute) {
		return getFirstCorrespondingObjectWithClass(uAttribute, org.emftext.language.java.members.Field)
	}

	/**
	 * Retrieves the first corresponding java class method for a given uml operation
	 */
	def protected getCorrespondingClassMethod(Operation uOperation) {
		return getFirstCorrespondingObjectWithClass(uOperation, org.emftext.language.java.members.ClassMethod)
	}

	/**
	 * Retrieves the first corresponding java interface method for a given uml operation
	 */
	def protected getCorrespondingInterfaceMethod(Operation uOperation) {
		return getFirstCorrespondingObjectWithClass(uOperation, org.emftext.language.java.members.InterfaceMethod)
	}

	/**
	 * Retrieves the first corresponding java class for a given uml class
	 */
	def protected getCorrespondingClass(org.eclipse.uml2.uml.Classifier uClass) {
		return getFirstCorrespondingObjectWithClass(uClass, org.emftext.language.java.classifiers.Class)
	}

	/**
	 * Retrieves the first corresponding java compilationunit for a given uml class
	 */
	def protected getCorrespondingCompilationUnit(org.eclipse.uml2.uml.Class uClass) {
		return getFirstCorrespondingObjectWithClass(uClass, org.emftext.language.java.containers.CompilationUnit)
	}

	/**
	 * Retrieves the first corresponding java interface for a given uml interface
	 */
	def protected getCorrespondingInterface(org.eclipse.uml2.uml.Interface uInterface) {
		return getFirstCorrespondingObjectWithClass(uInterface, org.emftext.language.java.classifiers.Interface)
	}

	/**
	 * Retrieves the first corresponding java enumeration for a given uml enumeration
	 */
	def protected getCorrespondingEnum(org.eclipse.uml2.uml.Enumeration uEnumeration) {
		return getFirstCorrespondingObjectWithClass(uEnumeration, org.emftext.language.java.classifiers.Enumeration)
	}

	/**
	 * Retrieves the first corresponding java ordinary parameter for a given uml parameter
	 */
	def protected getCorrespondingParameter(org.eclipse.uml2.uml.Parameter uParam) {
		return getFirstCorrespondingObjectWithClass(uParam, org.emftext.language.java.parameters.OrdinaryParameter)
	}

	/**
	 * Retrieves the first corresponding java package for a given uml package
	 */
	def protected getCorrespondingPackage(org.eclipse.uml2.uml.Package uPackage) {
		return getFirstCorrespondingObjectWithClass(uPackage, org.emftext.language.java.containers.Package)
	}

	/**
	 * Retrieves the first corresponding java constructor for a given uml operation
	 */
	def protected getCorrespondingConstructor(org.eclipse.uml2.uml.Operation uOperation) {
		return getFirstCorrespondingObjectWithClass(uOperation, org.emftext.language.java.members.Constructor)
	}

}
