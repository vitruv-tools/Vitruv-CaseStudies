package tools.vitruv.applications.umljava.tests.java2uml

import static tools.vitruv.domains.java.util.JavaPersistenceHelper.*
import org.eclipse.uml2.uml.Model
import org.emftext.language.java.classifiers.Interface
import static tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.*
import org.emftext.language.java.members.Field
import tools.vitruv.applications.util.temporary.java.JavaVisibility
import tools.vitruv.applications.umljava.tests.util.AbstractUmlJavaTest
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Property
import java.util.List
import org.emftext.language.java.members.EnumConstant
import org.emftext.language.java.members.Member
import org.eclipse.uml2.uml.UMLPackage
import java.util.ArrayList
import org.junit.jupiter.api.BeforeEach
import tools.vitruv.applications.umljava.JavaToUmlChangePropagationSpecification
import java.nio.file.Path

/**
 * Abstract Class for Java To UML Tests. Contains functions to create Java-CompilationUnits 
 * as root models.
 * 
 * @author Fei
 */
abstract class JavaToUmlTransformationTest extends AbstractUmlJavaTest {
	static val UMLMODELPATH = "rootModelDirectory" // Directory of the Uml Model Path used in the java2uml tests
	static val UMLMODELNAME = "rootModelName" // Name of the Uml Model used in the java2uml tests

	@BeforeEach
	def protected setup() {
		userInteraction.addNextTextInput(UMLMODELNAME)
		userInteraction.addNextTextInput(UMLMODELPATH)
	}

	override protected getChangePropagationSpecifications() {
		return #[new JavaToUmlChangePropagationSpecification()]
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
	def protected createJavaClassWithCompilationUnit(String cName, JavaVisibility visibility, boolean abstr,
		boolean fin) {
		val cu = createCompilationUnitAsModel(cName)
		val cls = createJavaClass(cName, visibility, abstr, fin)
		cu.classifiers += cls
		propagate
		return cls
	}

	/**
	 * Returns a simple java class with the given name.
	 * The class is public, non-final, non-abstract and is contained in a
	 * synchronized compilation unit. The class has no members.
	 * 
	 * @param name the name of the java class
	 * @return the new Java-Class
	 */
	def protected createSimpleJavaClassWithCompilationUnit(String name) {
		return createJavaClassWithCompilationUnit(name, JavaVisibility.PUBLIC, false, false)
	}

	/**
	 * Returns a simple interface with the given name.
	 * The interface is public, has no members and no super interfaces.
	 * It is contained in a synchronized compilation unit.
	 * 
	 * @param name the name of the interface
	 * @return the new java interface
	 */
	def protected createSimpleJavaInterfaceWithCompilationUnit(String name) {
		return createJavaInterfaceWithCompilationUnit(name, null)
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
	def protected createJavaInterfaceWithCompilationUnit(String name, List<Interface> superInterfaces) {
		val cu = createCompilationUnitAsModel(name)
		val jI = createJavaInterface(name, superInterfaces)
		cu.classifiers += jI
		propagate
		return jI
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
	def protected createJavaEnumWithCompilationUnit(String name, JavaVisibility visibility,
		List<EnumConstant> constants) {
		val jEnum = createJavaEnum(name, visibility, constants)
		val cu = createCompilationUnitAsModel(name)
		cu.classifiers += jEnum
		propagate
		return jEnum
	}

	def private createCompilationUnitAsModel(String name) {
		val cu = createEmptyCompilationUnit(name)
		resourceAt(Path.of(buildJavaFilePath(cu))).startRecordingChanges => [
			contents += cu
		]
		propagate
		return cu
	}

	/**
	 * Creates a new java package and synchronizes it as root model.
	 * 
	 * @param name the name of the package
	 * @param superPackage the package that contains the new package. Can be null if it is the default package.
	 */
	def protected createJavaPackageAsModel(String name, org.emftext.language.java.containers.Package superPackage) {
		val jPackage = createJavaPackage(name, superPackage)
		resourceAt(Path.of(buildJavaFilePath(jPackage))).startRecordingChanges => [
			contents += jPackage
		]
		propagate
		return jPackage
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
	def protected getUmlPackagedElementsbyName(Class<? extends org.eclipse.uml2.uml.PackageableElement> type,
		String elementName) {
		val model = registeredUmlModel
		if(model === null) return new ArrayList<org.eclipse.uml2.uml.PackageableElement>()
		return model.packagedElements.filter(type).filter[it.name == elementName].toList
	}

	/**
	 * Retrieves the first corresponding uml class of the java class.
	 */
	def protected getCorrespondingClass(org.emftext.language.java.classifiers.Class jClass) {
		return getFirstCorrespondingObjectWithClass(jClass, org.eclipse.uml2.uml.Class)
	}

	/**
	 * Retrieves the first corresponding uml interface of the java interface.
	 */
	def protected getCorrespondingInterface(org.emftext.language.java.classifiers.Interface jInterface) {
		return getFirstCorrespondingObjectWithClass(jInterface, org.eclipse.uml2.uml.Interface)
	}

	/**
	 * Retrieves the first corresponding uml enumeration of the java enumeration.
	 */
	def protected getCorrespondingEnum(org.emftext.language.java.classifiers.Enumeration jEnum) {
		return getFirstCorrespondingObjectWithClass(jEnum, org.eclipse.uml2.uml.Enumeration)
	}

	/**
	 * Retrieves the first corresponding uml operation of the java member.
	 * (Java-ClassMethod, -InterfaceMethod and -Constructor can correspond to a
	 * uml operation )
	 */
	def protected getCorrespondingMethod(Member jMethod) {
		return getFirstCorrespondingObjectWithClass(jMethod, Operation)
	}

	/**
	 * Retrieves the first corresponding uml property of the java field.
	 */
	def protected getCorrespondingAttribute(Field jAttribute) {
		return getFirstCorrespondingObjectWithClass(jAttribute, Property)
	}

	/**
	 * Retrieves the first corresponding uml parameter of the java parameter.
	 */
	def protected getCorrespondingParameter(org.emftext.language.java.parameters.OrdinaryParameter jParam) {
		return getFirstCorrespondingObjectWithClass(jParam, org.eclipse.uml2.uml.Parameter)
	}

	/**
	 * Retrieves the first corresponding uml package of the java package.
	 */
	def protected getCorrespondingPackage(org.emftext.language.java.containers.Package jPackage) {
		return getFirstCorrespondingObjectWithClass(jPackage, org.eclipse.uml2.uml.Package)
	}

	def protected getRegisteredUmlModel() {
		val model = getCorrespondingEObjects(UMLPackage.Literals.MODEL, Model).head
		return model
	}

}
