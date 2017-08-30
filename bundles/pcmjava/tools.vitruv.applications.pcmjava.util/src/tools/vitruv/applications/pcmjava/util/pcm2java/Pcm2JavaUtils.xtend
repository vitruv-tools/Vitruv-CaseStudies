package tools.vitruv.applications.pcmjava.util.pcm2java

import tools.vitruv.framework.util.datatypes.VURI
import java.io.ByteArrayInputStream
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.JavaRoot
import org.emftext.language.java.containers.Package
import org.emftext.language.java.expressions.ExpressionsFactory
import org.emftext.language.java.imports.Import
import org.emftext.language.java.imports.ImportsFactory
import org.emftext.language.java.literals.LiteralsFactory
import org.emftext.language.java.members.Field
import org.emftext.language.java.operators.OperatorsFactory
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.parameters.ParametersFactory
import org.emftext.language.java.references.ReferencesFactory
import org.emftext.language.java.statements.Statement
import org.emftext.language.java.statements.StatementsFactory
import org.emftext.language.java.types.Boolean
import org.emftext.language.java.types.Byte
import org.emftext.language.java.types.Char
import org.emftext.language.java.types.ClassifierReference
import org.emftext.language.java.types.Double
import org.emftext.language.java.types.Float
import org.emftext.language.java.types.Int
import org.emftext.language.java.types.Long
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.PrimitiveType
import org.emftext.language.java.types.Short
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.TypesFactory

import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.applications.pcmjava.util.PcmJavaUtils
import tools.vitruv.domains.java.util.jamoppparser.JamoppParser

abstract class Pcm2JavaUtils extends PcmJavaUtils {
	private static val Logger logger = Logger.getLogger(Pcm2JavaUtils.simpleName)

	private new() {
	}

	def static NamespaceClassifierReference createAndReturnNamespaceClassifierReferenceForName(String namespace,
		String name) {
		val classifier = ClassifiersFactory.eINSTANCE.createClass
		classifier.setName(name)
		val classifierReference = TypesFactory.eINSTANCE.createClassifierReference
		classifierReference.setTarget(classifier)
		val namespaceClassifierReference = TypesFactory.eINSTANCE.createNamespaceClassifierReference
		namespaceClassifierReference.classifierReferences.add(classifierReference)
		if (!namespace.nullOrEmpty) {
			namespaceClassifierReference.namespaces.addAll(namespace.split("."))
		} else {
			namespaceClassifierReference.namespaces.add("")
		}
		return namespaceClassifierReference
	}

	def static Parameter createOrdinaryParameter(TypeReference typeReference, String name) {
		val parameter = ParametersFactory.eINSTANCE.createOrdinaryParameter
		parameter.name = name
		parameter.typeReference = typeReference
		return parameter
	}

	def static Statement createAssignmentFromParameterToField(Field field, Parameter parameter) {
		val expressionStatement = StatementsFactory.eINSTANCE.createExpressionStatement
		val assigmentExpression = ExpressionsFactory.eINSTANCE.createAssignmentExpression

		// this.
		val selfReference = ReferencesFactory.eINSTANCE.createSelfReference
		selfReference.self = LiteralsFactory.eINSTANCE.createThis
		assigmentExpression.child = selfReference

		// .fieldname
		val fieldReference = ReferencesFactory.eINSTANCE.createIdentifierReference
		fieldReference.target = field
		selfReference.next = fieldReference

		// =
		assigmentExpression.assignmentOperator = OperatorsFactory.eINSTANCE.createAssignment

		// name		
		val identifierReference = ReferencesFactory.eINSTANCE.createIdentifierReference
		identifierReference.target = parameter

		assigmentExpression.value = identifierReference
		expressionStatement.expression = assigmentExpression
		return expressionStatement
	}

	def static NamespaceClassifierReference createNamespaceClassifierReference(ConcreteClassifier concreteClassifier) {
		val namespaceClassifierReference = TypesFactory.eINSTANCE.createNamespaceClassifierReference
		val classifierRef = TypesFactory.eINSTANCE.createClassifierReference
		classifierRef.target = concreteClassifier
		namespaceClassifierReference.classifierReferences.add(classifierRef)

		// namespaceClassifierReference.namespaces.addAll(concreteClassifier.containingCompilationUnit.namespaces)
		return namespaceClassifierReference
	}

	def static Import addImportToCompilationUnitOfClassifier(Classifier classifier,
		ConcreteClassifier classifierToImport) {
		val classifierImport = ImportsFactory.eINSTANCE.createClassifierImport
		if (null !== classifierToImport.containingCompilationUnit) {
			if (null !== classifierToImport.containingCompilationUnit.namespaces) {
				classifierImport.namespaces.addAll(classifierToImport.containingCompilationUnit.namespaces)
			}
			classifier.containingCompilationUnit.imports.add(classifierImport)
		}
		classifierImport.classifier = classifierToImport
		return classifierImport
	}

	def static Package getContainingPackageFromCorrespondenceModel(Classifier classifier,
		CorrespondenceModel correspondenceModel) {
		var namespace = classifier.containingCompilationUnit.namespacesAsString
		if (namespace.endsWith("$") || namespace.endsWith(".")) {
			namespace = namespace.substring(0, namespace.length - 1)
		}
		val finalNamespace = namespace
		var Set<Package> packagesWithCorrespondences = correspondenceModel.
			getAllEObjectsOfTypeInCorrespondences(Package)
		val packagesWithNamespace = packagesWithCorrespondences.filter [ pack |
			finalNamespace.equals(pack.namespacesAsString + pack.name)
		]
		if (null !== packagesWithNamespace && 0 < packagesWithNamespace.size &&
			null !== packagesWithNamespace.iterator.next) {
			return packagesWithNamespace.iterator.next
		}
		return null;
	}

	def static CompilationUnit createCompilationUnit(String name, String content) {
		return createJavaRoot(name, content) as CompilationUnit
	}

	def static JavaRoot createJavaRoot(String name, String content) {
		val JamoppParser jaMoPPParser = new JamoppParser
		val inStream = new ByteArrayInputStream(content.bytes)
		val javaRoot = jaMoPPParser.parseCompilationUnitFromInputStream(VURI.getInstance(name + ".java").getEMFUri,
			inStream)
		javaRoot.name = name + ".java"
		EcoreUtil.remove(javaRoot)
		return javaRoot
	}

	def public static createJaMoPPMethod(String content) {
		try {
			val String cuContent = "class Dummy{" + content + "}"
			val String name = "vitruvius.meta/src/dummy.java";
			val cu = createJavaRoot(name, cuContent) as CompilationUnit
			val method = cu.classifiers.get(0).methods.get(0)
			EcoreUtil.remove(method)
			return method
		} catch (Throwable t) {
			logger.warn("Exception during createJaMoPPMethod with content " + content + " Exception: " + t)
			return null;
		}
	}

	def dispatch static getNameFromJaMoPPType(ClassifierReference reference) {
		return reference.target.name
	}

	def dispatch static getNameFromJaMoPPType(NamespaceClassifierReference reference) {
		val classRef = reference.pureClassifierReference
		return classRef.target.name

	// is currently not possible: see: https://bugs.eclipse.org/bugs/show_bug.cgi?id=404817
	// return getNameFromJaMoPPType(classRef)
	}

	def dispatch static getNameFromJaMoPPType(Boolean reference) {
		return "boolean"
	}

	def dispatch static getNameFromJaMoPPType(Byte reference) {
		return "byte"
	}

	def dispatch static getNameFromJaMoPPType(Char reference) {
		return "char"
	}

	def dispatch static getNameFromJaMoPPType(Double reference) {
		return "double"
	}

	def dispatch static getNameFromJaMoPPType(Float reference) {
		return "float"
	}

	def dispatch static getNameFromJaMoPPType(Int reference) {
		return "int"
	}

	def dispatch static getNameFromJaMoPPType(Long reference) {
		return "long"
	}

	def dispatch static getNameFromJaMoPPType(Short reference) {
		return "short"
	}

	def dispatch static getNameFromJaMoPPType(org.emftext.language.java.types.Void reference) {
		return "void"
	}

	/**
	 * returns the class object for a primitive type, e.g, Integer for int
	 */
	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(PrimitiveType type) {
		logger.warn("no dispatch method found for type: " + type)
		return null
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Boolean type) {
		Pcm2JavaUtils.createAndReturnNamespaceClassifierReferenceForName("java.lang", "Boolean")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Byte type) {
		Pcm2JavaUtils.createAndReturnNamespaceClassifierReferenceForName("java.lang", "Byte")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Char type) {
		Pcm2JavaUtils.createAndReturnNamespaceClassifierReferenceForName("java.lang", "Character")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Double type) {
		Pcm2JavaUtils.createAndReturnNamespaceClassifierReferenceForName("java.lang", "Double")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Float type) {
		Pcm2JavaUtils.createAndReturnNamespaceClassifierReferenceForName("java.lang", "Float")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Int type) {
		Pcm2JavaUtils.createAndReturnNamespaceClassifierReferenceForName("java.lang", "Integer")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Long type) {
		Pcm2JavaUtils.createAndReturnNamespaceClassifierReferenceForName("java.lang", "Long")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Short type) {
		Pcm2JavaUtils.createAndReturnNamespaceClassifierReferenceForName("java.lang", "Short")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(
		org.emftext.language.java.types.Void type) {
		Pcm2JavaUtils.createAndReturnNamespaceClassifierReferenceForName("java.lang", "Void")
	}

}
