package tools.vitruv.applications.util.temporary.java

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.apache.log4j.Logger
import tools.mdsd.jamopp.model.java.types.NamespaceClassifierReference
import tools.mdsd.jamopp.model.java.types.TypesFactory
import tools.mdsd.jamopp.model.java.parameters.Parameter
import tools.mdsd.jamopp.model.java.types.TypeReference
import tools.mdsd.jamopp.model.java.parameters.ParametersFactory
import tools.mdsd.jamopp.model.java.statements.StatementsFactory
import tools.mdsd.jamopp.model.java.expressions.ExpressionsFactory
import tools.mdsd.jamopp.model.java.members.Field
import tools.mdsd.jamopp.model.java.statements.Statement
import tools.mdsd.jamopp.model.java.references.ReferencesFactory
import tools.mdsd.jamopp.model.java.literals.LiteralsFactory
import tools.mdsd.jamopp.model.java.operators.OperatorsFactory
import tools.mdsd.jamopp.model.java.imports.Import
import tools.mdsd.jamopp.model.java.classifiers.ConcreteClassifier
import tools.mdsd.jamopp.model.java.classifiers.Classifier
import tools.mdsd.jamopp.model.java.imports.ImportsFactory
import tools.mdsd.jamopp.model.java.types.PrimitiveType
import tools.mdsd.jamopp.model.java.types.Char
import tools.mdsd.jamopp.model.java.types.Int
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.mdsd.jamopp.model.java.modifiers.ModifiersFactory
import tools.mdsd.jamopp.model.java.imports.ClassifierImport
import tools.mdsd.jamopp.model.java.JavaClasspath
import static tools.vitruv.applications.util.temporary.java.JavaQueryUtil.*
import tools.mdsd.jamopp.model.java.modifiers.AnnotableAndModifiable
import tools.mdsd.jamopp.model.java.annotations.AnnotationsFactory
import tools.mdsd.jamopp.model.java.classifiers.ClassifiersFactory
import java.util.List
import tools.mdsd.jamopp.model.java.members.Constructor
import tools.mdsd.jamopp.model.java.classifiers.Class
import tools.mdsd.jamopp.model.java.members.MembersFactory
import tools.mdsd.jamopp.model.java.instantiations.NewConstructorCall
import tools.mdsd.jamopp.model.java.references.IdentifierReference
import java.util.ArrayList
import tools.mdsd.jamopp.model.java.references.ReferenceableElement

@Utility 
class JavaModificationUtil {
	static val Logger logger = Logger.getLogger(JavaModificationUtil)
	
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
		createNamespaceClassifierReference(namespaceClassifierReference, concreteClassifier)
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
		
	def static createNamespaceClassifierReference(NamespaceClassifierReference namespaceClassifierReference,
		ConcreteClassifier concreteClassifier) {
		val classifierRef = TypesFactory.eINSTANCE.createClassifierReference
		classifierRef.target = concreteClassifier
		namespaceClassifierReference.classifierReferences.add(classifierRef)
		if (concreteClassifier.containingCompilationUnit !== null) {
		    namespaceClassifierReference.namespaces += concreteClassifier.containingCompilationUnit.namespaces
		}
		else if (concreteClassifier.eIsProxy) {
		    //extract namespaces for java default types
		    val uri = EcoreUtil.getURI(concreteClassifier)
		    val prefix = "/javaclass/"
		    val suffix = concreteClassifier.name + JavaPersistenceHelper.FILE_EXTENSION_SEPARATOR + JavaPersistenceHelper.JAVA_FILE_EXTENSION
		    if (uri.path.startsWith(prefix) && uri.path.endsWith(suffix)) {
		        val namespaces = uri.path.substring(prefix.length, uri.path.length - suffix.length).split("\\.")
		        namespaceClassifierReference.namespaces += namespaces
		    }
		}
	}

	def static createPrivateField(Field field, TypeReference reference, String name) {
		field.typeReference = EcoreUtil.copy(reference)
		field.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPrivate)
		var String fieldName = name
		if (fieldName.nullOrEmpty) {
			fieldName = "field_" + getNameFromJaMoPPType(reference)
		}
		field.name = fieldName
	}
	
	/**
	 * returns the class object for a primitive type, e.g, Integer for int
	 */
	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(PrimitiveType type) {
		logger.warn("no dispatch method found for type: " + type)
		return null
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Boolean type) {
		createNamespaceClassifierReferenceForName("java.lang", "Boolean")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Byte type) {
		createNamespaceClassifierReferenceForName("java.lang", "Byte")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Char type) {
		createNamespaceClassifierReferenceForName("java.lang", "Character")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Double type) {
		createNamespaceClassifierReferenceForName("java.lang", "Double")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Float type) {
		createNamespaceClassifierReferenceForName("java.lang", "Float")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Int type) {
		createNamespaceClassifierReferenceForName("java.lang", "Integer")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Long type) {
		createNamespaceClassifierReferenceForName("java.lang", "Long")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(Short type) {
		createNamespaceClassifierReferenceForName("java.lang", "Short")
	}

	def dispatch static TypeReference getWrapperTypeReferenceForPrimitiveType(
		tools.mdsd.jamopp.model.java.types.Void type) {
		createNamespaceClassifierReferenceForName("java.lang", "Void")
	}

	/**
     * Creates a Java-ClassifierImport from a qualified name
     */
	def static ClassifierImport createJavaClassImport(String name) {
		val classifier = getClassifier(name);
		val classifierImport = ImportsFactory.eINSTANCE.createClassifierImport();
		classifierImport.classifier = classifier;
		return classifierImport
	}

	def static NamespaceClassifierReference createNamespaceClassifierReferenceForName(String namespace,
		String name) {
		val classifier = getClassifier(namespace + "." + name)
		return createNamespaceClassifierReference(classifier)
	}

	def static NamespaceClassifierReference createNamespaceClassifierReferenceForName(String qualifiedName) {
		createNamespaceClassifierReference(getClassifier(qualifiedName))
	}

	def static ConcreteClassifier getClassifier(String qualifiedName) {
		// To resolve classifiers from the Java standard library, this requires the Java standard library to be
		// registered (JavaClasspath.get().registerStdLib). Should be done by the domain by default.
		JavaClasspath.get().getClassifier(qualifiedName) as ConcreteClassifier
	}

	def static addAnnotationToAnnotableAndModifiable(AnnotableAndModifiable annotableAndModifiable,
		String annotationName) {
		val newAnnotation = AnnotationsFactory.eINSTANCE.createAnnotationInstance()
		val jaMoPPClass = ClassifiersFactory.eINSTANCE.createClass
		jaMoPPClass.setName(annotationName);
		newAnnotation.setAnnotation(jaMoPPClass)
		annotableAndModifiable.getAnnotationsAndModifiers().add(newAnnotation)
	}

	def static addImportToClassFromString(ConcreteClassifier jaMoPPClass, List<String> namespaceArray,
		String entityToImport) {
		for (Import import : jaMoPPClass.containingCompilationUnit.imports) {
			if ((import as ClassifierImport).classifier.name == entityToImport) {
				return // Import has already been added (in the case of inject.Inject could be from javax.inject package)
			}
		}
		val guiceImport = ImportsFactory.eINSTANCE.createClassifierImport
		val ConcreteClassifier cl = ClassifiersFactory.eINSTANCE.createClass
		cl.name = entityToImport
		guiceImport.classifier = cl
		guiceImport.namespaces.addAll(namespaceArray)
		jaMoPPClass.containingCompilationUnit.imports.add(guiceImport)
	}
	
	def static addConstructorToClass(Class javaClass) {
		val Constructor constructor = MembersFactory.eINSTANCE.createConstructor
		addConstructorToClass(constructor, javaClass)
	}

	
	def static addConstructorToClass(Constructor constructor, Class javaClass) {
		constructor.name = javaClass.name
		constructor.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPublic)
		javaClass.members.add(constructor)
		return constructor
	}

	def static addImportToCompilationUnitOfClassifier(ClassifierImport classifierImport, Classifier classifier,
		ConcreteClassifier classifierToImport) {
		if (null !== classifierToImport.containingCompilationUnit) {
			if (null !== classifierToImport.containingCompilationUnit.namespaces) {
				classifierImport.namespaces.addAll(classifierToImport.containingCompilationUnit.namespaces)
			}
			classifier.containingCompilationUnit.imports.add(classifierImport)
		}
		classifierImport.classifier = classifierToImport
	}

	def static createNewForFieldInConstructor(NewConstructorCall newConstructorCall, Constructor constructor,
		Field field) {
		val classifier = field.containingConcreteClassifier
		if (!(classifier instanceof Class)) {
			return null
		}
		val jaMoPPClass = classifier as Class

		addNewStatementToConstructor(newConstructorCall, constructor, field, jaMoPPClass.fields, constructor.parameters)
	}
	
	def static addNewStatementToConstructor(NewConstructorCall newConstructorCall, Constructor constructor, Field field,
		Field[] fieldsToUseAsArgument, Parameter[] parametersToUseAsArgument) {
		val expressionStatement = StatementsFactory.eINSTANCE.createExpressionStatement
		val assigmentExpression = ExpressionsFactory.eINSTANCE.createAssignmentExpression

		// this.
		val selfReference = ReferencesFactory.eINSTANCE.createSelfReference
		selfReference.self = LiteralsFactory.eINSTANCE.createThis
		assigmentExpression.child = selfReference

		// .fieldname
		val fieldReference = ReferencesFactory.eINSTANCE.createIdentifierReference
		fieldReference.target = field
		selfReference.next = EcoreUtil.copy(fieldReference)

		// =
		assigmentExpression.assignmentOperator = OperatorsFactory.eINSTANCE.createAssignment

		// new fieldType
		newConstructorCall.typeReference = EcoreUtil.copy(field.typeReference)

		// get order of type references of the constructor
		updateArgumentsOfConstructorCall(field, fieldsToUseAsArgument, parametersToUseAsArgument, newConstructorCall)

		assigmentExpression.value = newConstructorCall
		expressionStatement.expression = assigmentExpression
		constructor.statements.add(expressionStatement)
	}
	
	private def static updateArgumentsOfConstructorCall(Field field, Field[] fieldsToUseAsArgument,
		Parameter[] parametersToUseAsArgument, NewConstructorCall newConstructorCall) {
		val List<TypeReference> typeListForConstructor = new ArrayList<TypeReference>
		if (null !== field.typeReference && null !== field.typeReference.pureClassifierReference &&
			null !== field.typeReference.pureClassifierReference.target) {
			val classifier = field.typeReference.pureClassifierReference.target
			if (classifier instanceof Class) {
				val jaMoPPClass = classifier
				val constructorsForClass = jaMoPPClass.members.filter(typeof(Constructor))
				if (!constructorsForClass.nullOrEmpty) {
					val constructorForClass = constructorsForClass.get(0)
					for (parameter : constructorForClass.parameters) {
						typeListForConstructor.add(parameter.typeReference)
					}
				}
			}
		}

		// find type with same name in fields or parameters (start with parameter)
		for (typeRef : typeListForConstructor) {
			val refElement = typeRef.findMatchingTypeInParametersOrFields(fieldsToUseAsArgument,
				parametersToUseAsArgument)
			if (refElement !== null) {
				val IdentifierReference identifierReference = ReferencesFactory.eINSTANCE.createIdentifierReference
				identifierReference.target = refElement
			} else {
				newConstructorCall.arguments.add(LiteralsFactory.eINSTANCE.createNullLiteral)
			}
		}
	}
	
	private def static ReferenceableElement findMatchingTypeInParametersOrFields(TypeReference typeReferenceToFind,
		Field[] fieldsToUseAsArgument, Parameter[] parametersToUseAsArgument) {
		for (parameter : parametersToUseAsArgument) {
			if (parameter.typeReference.target == typeReferenceToFind.target) {
				return parameter
			}
		}
		for (field : fieldsToUseAsArgument) {
			if (field.typeReference.target == typeReferenceToFind.target) {
				return field
			}
		}
		return null
	}
}