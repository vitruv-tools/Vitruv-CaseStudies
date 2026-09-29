package tools.vitruv.applications.transitivechange.tests.linear.uml2java;

import static org.hamcrest.MatcherAssert.assertThat;
import static tools.vitruv.applications.util.temporary.java.JavaPersistenceHelper.buildJavaFilePath;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.isNoResource;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.isResource;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.jupiter.api.BeforeEach;
import tools.vitruv.applications.transitivechange.tests.util.AbstractUmlJavaTest;
import tools.vitruv.applications.transitivechange.tests.util.TransitiveChangeSetup;
import tools.vitruv.change.propagation.ChangePropagationMode;
import tools.vitruv.change.propagation.ChangePropagationSpecification;

/**
 * Abstract super class for uml to java test cases.
 * Initializes a uml rootmodel.
 *
 * @author Fei
 */
public abstract class UmlToJavaTransformationTest extends AbstractUmlJavaTest {
	protected static final Logger logger = Logger.getLogger(UmlToJavaTransformationTest.class);

	private static final String MODEL_FILE_EXTENSION = "uml";
	private static final String MODEL_NAME = "model"; // name of the uml rootmodel

	private Path getProjectModelPath(String modelName) {
		return Path.of("model").resolve(modelName + "." + MODEL_FILE_EXTENSION);
	}

	protected Model getRootElement() {
		return from(Model.class, getProjectModelPath(MODEL_NAME));
	}

	@Override
	protected List<ChangePropagationSpecification> getChangePropagationSpecifications() {
		return TransitiveChangeSetup.getChangePropagationSpecifications(true);
	}

	@BeforeEach
	protected void disableTransitiveChangePropagation() {
		getVirtualModel().setChangePropagationMode(ChangePropagationMode.SINGLE_STEP);
	}

	@BeforeEach
	protected void setup() {
		Model umlModel = UMLFactory.eINSTANCE.createModel();
		umlModel.setName(MODEL_NAME);
		startRecordingChanges(resourceAt(getProjectModelPath(MODEL_NAME))).getContents().add(umlModel);
		propagate();
	}

	protected void assertJavaFileExists(String fileName, String[] namespaces) {
		assertThat(getUri(Path.of(buildJavaFilePath(fileName + ".java", Arrays.asList(namespaces)))), isResource());
	}

	protected void assertJavaFileNotExists(String fileName, String[] namespaces) {
		assertThat(getUri(Path.of(buildJavaFilePath(fileName + ".java", Arrays.asList(namespaces)))), isNoResource());
	}

	/**
	 * Retrieves the first corresponding java field for a given uml property
	 */
	protected org.emftext.language.java.members.Field getCorrespondingAttribute(Property uAttribute) {
		return getFirstCorrespondingObjectWithClass(uAttribute, org.emftext.language.java.members.Field.class);
	}

	/**
	 * Retrieves the first corresponding java class method for a given uml operation
	 */
	protected org.emftext.language.java.members.ClassMethod getCorrespondingClassMethod(Operation uOperation) {
		return getFirstCorrespondingObjectWithClass(uOperation, org.emftext.language.java.members.ClassMethod.class);
	}

	/**
	 * Retrieves the first corresponding java interface method for a given uml operation
	 */
	protected org.emftext.language.java.members.InterfaceMethod getCorrespondingInterfaceMethod(Operation uOperation) {
		return getFirstCorrespondingObjectWithClass(uOperation, org.emftext.language.java.members.InterfaceMethod.class);
	}

	/**
	 * Retrieves the first corresponding java class for a given uml class
	 */
	protected org.emftext.language.java.classifiers.Class getCorrespondingClass(org.eclipse.uml2.uml.Classifier uClass) {
		return getFirstCorrespondingObjectWithClass(uClass, org.emftext.language.java.classifiers.Class.class);
	}

	/**
	 * Retrieves the first corresponding java compilationunit for a given uml class
	 */
	protected org.emftext.language.java.containers.CompilationUnit getCorrespondingCompilationUnit(org.eclipse.uml2.uml.Class uClass) {
		return getFirstCorrespondingObjectWithClass(uClass, org.emftext.language.java.containers.CompilationUnit.class);
	}

	/**
	 * Retrieves the first corresponding java interface for a given uml interface
	 */
	protected org.emftext.language.java.classifiers.Interface getCorrespondingInterface(org.eclipse.uml2.uml.Interface uInterface) {
		return getFirstCorrespondingObjectWithClass(uInterface, org.emftext.language.java.classifiers.Interface.class);
	}

	/**
	 * Retrieves the first corresponding java enumeration for a given uml enumeration
	 */
	protected org.emftext.language.java.classifiers.Enumeration getCorrespondingEnum(org.eclipse.uml2.uml.Enumeration uEnumeration) {
		return getFirstCorrespondingObjectWithClass(uEnumeration, org.emftext.language.java.classifiers.Enumeration.class);
	}

	/**
	 * Retrieves the first corresponding java ordinary parameter for a given uml parameter
	 */
	protected org.emftext.language.java.parameters.OrdinaryParameter getCorrespondingParameter(org.eclipse.uml2.uml.Parameter uParam) {
		return getFirstCorrespondingObjectWithClass(uParam, org.emftext.language.java.parameters.OrdinaryParameter.class);
	}

	/**
	 * Retrieves the first corresponding java package for a given uml package
	 */
	protected org.emftext.language.java.containers.Package getCorrespondingPackage(org.eclipse.uml2.uml.Package uPackage) {
		return getFirstCorrespondingObjectWithClass(uPackage, org.emftext.language.java.containers.Package.class);
	}

	/**
	 * Retrieves the first corresponding java constructor for a given uml operation
	 */
	protected org.emftext.language.java.members.Constructor getCorrespondingConstructor(org.eclipse.uml2.uml.Operation uOperation) {
		return getFirstCorrespondingObjectWithClass(uOperation, org.emftext.language.java.members.Constructor.class);
	}

}
