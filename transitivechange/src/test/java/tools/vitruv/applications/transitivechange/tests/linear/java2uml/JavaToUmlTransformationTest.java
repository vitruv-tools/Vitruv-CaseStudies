package tools.vitruv.applications.transitivechange.tests.linear.java2uml;

import static tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.createEmptyCompilationUnit;
import static tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.createJavaClass;
import static tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.createJavaEnum;
import static tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.createJavaInterface;
import static tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.createJavaPackage;
import static tools.vitruv.applications.util.temporary.java.JavaPersistenceHelper.buildJavaFilePath;

import com.google.common.collect.Iterables;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.EnumConstant;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Member;
import org.junit.jupiter.api.BeforeEach;
import tools.vitruv.applications.transitivechange.tests.util.AbstractUmlJavaTest;
import tools.vitruv.applications.transitivechange.tests.util.TransitiveChangeSetup;
import tools.vitruv.applications.util.temporary.java.JavaVisibility;
import tools.vitruv.change.propagation.ChangePropagationMode;
import tools.vitruv.change.propagation.ChangePropagationSpecification;

/**
 * Abstract Class for Java To UML Tests. Contains functions to create Java-CompilationUnits
 * as root models.
 *
 * @author Fei
 */
public abstract class JavaToUmlTransformationTest extends AbstractUmlJavaTest {
	private static final String UMLMODELPATH = "rootModelDirectory"; // Directory of the Uml Model Path used in the java2uml tests
	private static final String UMLMODELNAME = "rootModelName"; // Name of the Uml Model used in the java2uml tests

	@BeforeEach
	protected void disableTransitiveChangePropagation() {
		getVirtualModel().setChangePropagationMode(ChangePropagationMode.SINGLE_STEP);
	}

	@BeforeEach
	protected void setup() {
		getUserInteraction().addNextTextInput(UMLMODELNAME);
		getUserInteraction().addNextTextInput(UMLMODELPATH);
	}

	@Override
	protected List<ChangePropagationSpecification> getChangePropagationSpecifications() {
		return TransitiveChangeSetup.getChangePropagationSpecifications(true);
	}

	/**
	 * Returns a new java class with the given attributes.
	 * The java class is contained in an own compilation unit.
	 * The class and its compilation unit are already synchronized.
	 * The class has no members.
	 *
	 * @param cName the name of the java class
	 * @param visibility the visibility of the java class
	 * @param abstr if the class is abstract
	 * @param fin if the class is final
	 * @return the new java class
	 */
	protected org.emftext.language.java.classifiers.Class createJavaClassWithCompilationUnit(String cName,
		JavaVisibility visibility, boolean abstr, boolean fin) {
		CompilationUnit cu = createCompilationUnitAsModel(cName);
		org.emftext.language.java.classifiers.Class cls = createJavaClass(cName, visibility, abstr, fin);
		cu.getClassifiers().add(cls);
		propagate();
		return cls;
	}

	/**
	 * Returns a simple java class with the given name.
	 * The class is public, non-final, non-abstract and is contained in a
	 * synchronized compilation unit. The class has no members.
	 *
	 * @param name the name of the java class
	 * @return the new Java-Class
	 */
	protected org.emftext.language.java.classifiers.Class createSimpleJavaClassWithCompilationUnit(String name) {
		return createJavaClassWithCompilationUnit(name, JavaVisibility.PUBLIC, false, false);
	}

	/**
	 * Returns a simple interface with the given name.
	 * The interface is public, has no members and no super interfaces.
	 * It is contained in a synchronized compilation unit.
	 *
	 * @param name the name of the interface
	 * @return the new java interface
	 */
	protected Interface createSimpleJavaInterfaceWithCompilationUnit(String name) {
		return createJavaInterfaceWithCompilationUnit(name, null);
	}

	/**
	 * Returns a interface with the given name.
	 * The interface is public, has no members, but has the given super interfaces.
	 * It is contained in a synchronized compilation unit.
	 *
	 * @param name the name of the interface
	 * @param superInterfaces list of super interfaces
	 * @return the new java interface
	 */
	protected Interface createJavaInterfaceWithCompilationUnit(String name, List<Interface> superInterfaces) {
		CompilationUnit cu = createCompilationUnitAsModel(name);
		Interface jI = createJavaInterface(name, superInterfaces);
		cu.getClassifiers().add(jI);
		propagate();
		return jI;
	}

	/**
	 * Creates an enum with the given attributes.
	 * The enum contains no other members beside the given constants.
	 * It is contained in a synhronized compilation unit.
	 *
	 * @param name the name of the enum
	 * @param visibility the visibility of the enum
	 * @param constants a list of enum constants for the enum
	 * @return the new java enumeration
	 */
	protected org.emftext.language.java.classifiers.Enumeration createJavaEnumWithCompilationUnit(String name,
		JavaVisibility visibility, List<EnumConstant> constants) {
		org.emftext.language.java.classifiers.Enumeration jEnum = createJavaEnum(name, visibility, constants);
		CompilationUnit cu = createCompilationUnitAsModel(name);
		cu.getClassifiers().add(jEnum);
		propagate();
		return jEnum;
	}

	private CompilationUnit createCompilationUnitAsModel(String name) {
		CompilationUnit cu = createEmptyCompilationUnit(name);
		startRecordingChanges(resourceAt(Path.of(buildJavaFilePath(cu)))).getContents().add(cu);
		propagate();
		return cu;
	}

	/**
	 * Creates a new java package and synchronizes it as root model.
	 *
	 * @param name the name of the package
	 * @param superPackage the package that contains the new package. Can be null if it is the default package.
	 */
	protected org.emftext.language.java.containers.Package createJavaPackageAsModel(String name,
		org.emftext.language.java.containers.Package superPackage) {
		org.emftext.language.java.containers.Package jPackage = createJavaPackage(name, superPackage);
		startRecordingChanges(resourceAt(Path.of(buildJavaFilePath(jPackage)))).getContents().add(jPackage);
		propagate();
		return jPackage;
	}

	/**
	 * Search through the uml root model that is given by the modelPath.
	 * Returns all (directly) packaged elements with the given elementName of the
	 * given type.
	 *
	 * @param modelPath the path of the uml root model relative to the project root
	 * @param type the type of the packageable elements to find
	 * @param elementName the name of the packageable elements to find
	 * @return all packageable elements in the uml model that matches the type and the elementName
	 */
	protected List<org.eclipse.uml2.uml.PackageableElement> getUmlPackagedElementsbyName(
		Class<? extends org.eclipse.uml2.uml.PackageableElement> type, String elementName) {
		Model model = getRegisteredUmlModel();
		if (model == null) return new ArrayList<org.eclipse.uml2.uml.PackageableElement>();
		return model.getPackagedElements().stream()
			.filter(type::isInstance)
			.filter(it -> Objects.equals(it.getName(), elementName))
			.collect(Collectors.toList());
	}

	/**
	 * Retrieves the first corresponding uml class of the java class.
	 */
	protected org.eclipse.uml2.uml.Class getCorrespondingClass(org.emftext.language.java.classifiers.Class jClass) {
		return getFirstCorrespondingObjectWithClass(jClass, org.eclipse.uml2.uml.Class.class);
	}

	/**
	 * Retrieves the first corresponding uml interface of the java interface.
	 */
	protected org.eclipse.uml2.uml.Interface getCorrespondingInterface(org.emftext.language.java.classifiers.Interface jInterface) {
		return getFirstCorrespondingObjectWithClass(jInterface, org.eclipse.uml2.uml.Interface.class);
	}

	/**
	 * Retrieves the first corresponding uml enumeration of the java enumeration.
	 */
	protected org.eclipse.uml2.uml.Enumeration getCorrespondingEnum(org.emftext.language.java.classifiers.Enumeration jEnum) {
		return getFirstCorrespondingObjectWithClass(jEnum, org.eclipse.uml2.uml.Enumeration.class);
	}

	/**
	 * Retrieves the first corresponding uml operation of the java member.
	 * (Java-ClassMethod, -InterfaceMethod and -Constructor can correspond to a
	 * uml operation )
	 */
	protected Operation getCorrespondingMethod(Member jMethod) {
		return getFirstCorrespondingObjectWithClass(jMethod, Operation.class);
	}

	/**
	 * Retrieves the first corresponding uml property of the java field.
	 */
	protected Property getCorrespondingAttribute(Field jAttribute) {
		return getFirstCorrespondingObjectWithClass(jAttribute, Property.class);
	}

	/**
	 * Retrieves the first corresponding uml parameter of the java parameter.
	 */
	protected org.eclipse.uml2.uml.Parameter getCorrespondingParameter(org.emftext.language.java.parameters.OrdinaryParameter jParam) {
		return getFirstCorrespondingObjectWithClass(jParam, org.eclipse.uml2.uml.Parameter.class);
	}

	/**
	 * Retrieves the first corresponding uml package of the java package.
	 */
	protected org.eclipse.uml2.uml.Package getCorrespondingPackage(org.emftext.language.java.containers.Package jPackage) {
		return getFirstCorrespondingObjectWithClass(jPackage, org.eclipse.uml2.uml.Package.class);
	}

	protected Model getRegisteredUmlModel() {
		Model model = Iterables.getFirst(getCorrespondingEObjects(UMLPackage.Literals.MODEL, Model.class), null);
		return model;
	}

}
