package tools.vitruv.applications.util.temporary.java

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.io.File
import java.io.PrintWriter
import java.util.ArrayList
import java.util.Collections
import java.util.Iterator
import java.util.List
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.classifiers.Enumeration
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.commons.NamedElement
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.Package
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.EnumConstant
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.Member
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.TypeReference
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static tools.vitruv.applications.util.temporary.java.JavaModifierUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import java.util.Optional
import org.emftext.language.java.commons.NamespaceAwareElement
import static java.util.Collections.emptyList
import org.emftext.language.java.containers.ContainersPackage
import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.getCorrespondingEObjects
import org.emftext.language.java.JavaClasspath
import org.eclipse.emf.ecore.util.EcoreUtil

/**
 * Class for java classifier, package and compilation unit util functions
 * 
 * @author Fei
 */
@Utility
class JavaContainerAndClassifierUtil {

	/**
	 * Creates and return a  new java class with the given name, visibility and modifiers
	 * The new class is not contained in a compilation unit.
	 * @param name the name of the class
	 * @param visibility the visibility of the class
	 * @param abstr if the class should be abstract
	 * @param fin if the class should be final
	 * @return the new class with the given attributes
	 */
	def static createJavaClass(String name, JavaVisibility visibility, boolean abstr, boolean fin) {
		val jClass = ClassifiersFactory.eINSTANCE.createClass
		setName(jClass, name)
		setJavaVisibilityModifier(jClass, visibility)
		setAbstract(jClass, abstr)
		setFinal(jClass, fin)
		return jClass
	}

	/**
	 * Creates a new java package
	 * @param name the name of the new package
	 * @param containingPackage the super package of the new package or null if it is the default package
	 * @return the new package
	 */
	def static createJavaPackage(String name, Package containingPackage) {
		val jPackage = ContainersFactory.eINSTANCE.createPackage
		setName(jPackage, name)
		jPackage.namespaces += getJavaPackageAsStringList(containingPackage)
		return jPackage
	}

	/**
	 * Creates a new java interface with the given name and list of super interfaces
	 * The created interface is not contained in a compilation unit.
	 * @param name the name of the interface
	 * @param superInterfaces the superinterfaces of the interface
	 * @return the new interface
	 */
	def static createJavaInterface(String name, List<Interface> superInterfaces) {
		val jInterface = ClassifiersFactory.eINSTANCE.createInterface
		setName(jInterface, name)
		jInterface.makePublic
		if (!superInterfaces.nullOrEmpty) {
			jInterface.extends.addAll(createNamespaceReferenceFromList(superInterfaces))
		}
		return jInterface
	}

	/**
	 * Creats a new java enum with the given properties
	 * The created Enum is not contained in a compilationunit.
	 * @param name the name of the enum
	 * @param visibility the visibility of the enum
	 * @param constantsList list of enum constants for the enum
	 * @return the new enum
	 */
	def static createJavaEnum(String name, JavaVisibility visibility, List<EnumConstant> constantsList) {
		val jEnum = ClassifiersFactory.eINSTANCE.createEnumeration
		setName(jEnum, name)
		setJavaVisibilityModifier(jEnum, visibility)
		addEnumConstantIfNotNull(jEnum, constantsList)
		return jEnum
	}

	/**
	 * Add constantList to the enum constants of the given jEnum if constantsList is not null or empty
	 * 
	 */
	def static addEnumConstantIfNotNull(Enumeration jEnum, List<EnumConstant> constantsList) {
		if (!constantsList.nullOrEmpty) {
			jEnum.constants.addAll(constantsList)
		}
	}

	/**
	 * Creates a java compilation unit with the given naem
	 * The method automatically sets the .java FileExtension for the compilation unit name
	 * There are no classifiers in the compilation unit yet.
	 * @param nameWithoutFileExtension the name without .java file extension
	 * @return the new compilation unit
	 */
	def static createEmptyCompilationUnit(String nameWithoutFileExtension) {
		val cu = ContainersFactory.eINSTANCE.createCompilationUnit
		cu.name = nameWithoutFileExtension + ".java"
		return cu
	}

	def static createJavaCompilationUnitWithClassifierInPackage(ConcreteClassifier jClassifier, Package jPackage) {
		val compUnit = createEmptyCompilationUnit(jClassifier.name)
		compUnit.classifiers += jClassifier
		compUnit.namespaces.addAll(getJavaPackageAsStringList(jPackage))
		return compUnit
	}

	/**
	 * Removes all classifiers of the iterator which has the same name as the given classifier classif
	 * @param iter iterator of typreferences
	 * @param classif classifier that shoud be removed from the iterator
	 */
	def static removeClassifierFromIterator(Iterator<TypeReference> iter, ConcreteClassifier classif) {
		while (iter.hasNext) {
			val type = (iter.next as NamespaceClassifierReference).classifierReferences.head.target
			if (classif.name.equals(type.name)) {
				iter.remove
			}
		}
	}

	/**
	 * For org.example.package it will return [org, example, package]
	 * Returns empty list if jPackage is the default package.
	 */
	def static getJavaPackageAsStringList(Package jPackage) {
		if (jPackage === null || jPackage.name.nullOrEmpty) { // Defaultpackage
			return Collections.<String>emptyList()
		}
		val packageStringList = new ArrayList<String>()
		packageStringList.addAll(jPackage.namespaces)
		packageStringList += jPackage.name
		return packageStringList
	}

	def static Field getJavaAttributeByName(Class jClass, String attributeName) {
		val candidates = jClass.members.filter(Field)
		for (member : candidates) {
			if (member.name == attributeName) {
				return member
			}
		}
		return null
	}

	def static Constructor getFirstJavaConstructor(Class jClass) {
		val candidates = jClass.members.filter(Constructor)
		if (!candidates.nullOrEmpty) {
			return candidates.head
		} else {
			return null
		}
	}

	def static removeJavaClassifierFromPackage(Package jPackage, ConcreteClassifier jClassifier) {
	}

	def static File createPackageInfo(String directory, String packageName) {
		val file = new File(directory + "/package-info.java")
		file.createNewFile
		val writer = new PrintWriter(file)
		writer.println("package " + packageName + "")
		writer.close
		return file
	}

	/**
	 * Finds and retrieves a specific {@link ConcreteClassifier} that is contained in a {@link Package}.
	 * The {@link ConcreteClassifier} is found by name, ignoring the capitalization of the first letter.
	 * @param name is the name of the desired {@link ConcreteClassifier}, the first letter can be upper and lower case.
	 * @param javaPackage is the specific {@link Package} to search in.
	 * @param classifierType specifies the class of the desired {@link ConcreteClassifier}, e.g. {@link Interface}.
	 * @return the found classifier, or null if there is no matching classifer.
	 * @throws IllegalStateException if there are multiple classifers in the package with a matching name.
	 */
	static def <T extends ConcreteClassifier> T findClassifier(String name, Package javaPackage,
		java.lang.Class<T> classifierType) {
		val possibleClassifiers = JavaClasspath.get.getConcreteClassifiers(javaPackage.namespacesAsString)
		val matchingClassifiers = possibleClassifiers.map[
			EcoreUtil.resolve(it, javaPackage) as ConcreteClassifier
		].filter[it !== null].filter[classifierType.isInstance(it)].map[
			it as T
		].filter[
			it.name.toFirstUpper == name.toFirstUpper
		]
		if (matchingClassifiers.size > 1)
			throw new IllegalStateException("Multiple matching classifers were found: " + matchingClassifiers)
		return matchingClassifiers.head
	}

	def static Package getContainingPackageFromCorrespondenceModel(Classifier classifier,
		CorrespondenceModel correspondenceModel) {
		var namespace = classifier.containingCompilationUnit.namespacesAsString
		if (namespace.endsWith("$") || namespace.endsWith(".")) {
			namespace = namespace.substring(0, namespace.length - 1)
		}
		val finalNamespace = namespace
		var Iterable<Package> packagesWithCorrespondences = correspondenceModel.getCorrespondingEObjects(
			ContainersPackage.Literals.PACKAGE, Package)
		val packagesWithNamespace = packagesWithCorrespondences.filter [ pack |
			finalNamespace.equals(pack.namespacesAsString + pack.name)
		]
		if (null !== packagesWithNamespace && 0 < packagesWithNamespace.size &&
			null !== packagesWithNamespace.iterator.next) {
			return packagesWithNamespace.iterator.next
		}
		return null
	}

	/**
	 * Returns the namespace of the compilation unit where the given object is directly or indirectly contained
	 */
	def static dispatch List<String> getJavaNamespace(CompilationUnit compUnit) {
		return compUnit.namespaces
	}

	def static dispatch List<String> getJavaNamespace(ConcreteClassifier classifier) {
		return getJavaNamespace(classifier.eContainer as CompilationUnit)
	}

	def static dispatch List<String> getJavaNamespace(NamedElement element) {
		throw new IllegalArgumentException("Unsupported type for retrieving namespace: " + element)
	}

	def static dispatch List<String> getJavaNamespace(Void element) {
		throw new IllegalArgumentException("Can not retrieve namespace for " + element)
	}

	def static dispatch CompilationUnit getContainingCompilationUnit(ConcreteClassifier classifier) {
		return classifier.eContainer as CompilationUnit
	}

	def static dispatch CompilationUnit getContainingCompilationUnit(Member mem) {
		return getContainingCompilationUnit(mem.eContainer as ConcreteClassifier)
	}

	def static dispatch CompilationUnit getContainingCompilationUnit(Parameter param) {
		return getContainingCompilationUnit(param.eContainer as Member)
	}

	def static dispatch CompilationUnit getContainingCompilationUnit(NamedElement element) {
		throw new IllegalArgumentException("Unsupported type for retrieving compilation unit: " + element)
	}

	def static dispatch CompilationUnit getContainingCompilationUnit(Void element) {
		throw new IllegalArgumentException("Can not retrieve compilation unit for " + element)
	}

	def static String getRootPackageName(String packageName) { // TODO TS technically not depending on Java domain
		return packageName?.split("\\.")?.get(0)
	}

	def static String getLastPackageName(String packageName) { // TODO TS technically not depending on Java domain
		return packageName?.substring(packageName.indexOf('.') + 1)
	}

	def static String getCompilationUnitName(Package containingPackage, String className) {
		'''«IF containingPackage !== null»«containingPackage.namespacesAsString»«containingPackage.name».«ENDIF»«className».java'''
	}

	def static String getCompilationUnitName(Optional<Package> containingPackage, String className) {
		getCompilationUnitName(if(containingPackage.present) containingPackage.get else null, className)
	}

	def static boolean updateNamespaces(NamespaceAwareElement elementToChange, List<String> newNamespaces) {
		if (newNamespaces != elementToChange.namespaces) {
			elementToChange.namespaces.clear
			elementToChange.namespaces += newNamespaces
			return true
		}
		return false
	}

	def static boolean updateNamespaces(NamespaceAwareElement elementToChange, Optional<Package> containingPackage) {
		if (containingPackage.present) {
			elementToChange.updateNamespaces(containingPackage.get)
		} else {
			elementToChange.updateNamespaces(emptyList)
		}
	}

	def static boolean updateNamespaces(NamespaceAwareElement elementToChange, Package containingPackage) {
		return elementToChange.updateNamespaces(containingPackage.javaPackageAsStringList)
	}

	def static boolean updateName(NamedElement elementToChange, String newName) {
		if (newName != elementToChange.name) {
			elementToChange.name = newName
			return true
		}
		return false
	}

}
