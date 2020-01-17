package tools.vitruv.applications.pcmjava.util.pcm2java

import java.util.Comparator
import java.util.Optional
import org.eclipse.emf.common.util.ECollections
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.expressions.ExpressionsFactory
import org.emftext.language.java.literals.LiteralsFactory
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.members.Method
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.modifiers.Public
import org.emftext.language.java.operators.OperatorsFactory
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.parameters.ParametersFactory
import org.emftext.language.java.references.ReferencesFactory
import org.emftext.language.java.statements.StatementsFactory
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.TypesFactory
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType

import static tools.vitruv.domains.java.util.JavaModificationUtil.*

class Pcm2JavaHelper {
	
	def static Constructor getOrCreateConstructorToClass(Class javaClass) {
		val constructors = javaClass.members.filter[it instanceof Constructor].map[it as Constructor]
		if (constructors.nullOrEmpty) {
			val Constructor constructor = MembersFactory.eINSTANCE.createConstructor
			return addConstructorToClass(constructor, javaClass)
		}
		return constructors.iterator.next
	}

	
	static def createSetter(Field field, ClassMethod method) {
		method.name = "set" + field.name.toFirstUpper
		method.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPublic)
		method.typeReference = TypesFactory.eINSTANCE.createVoid
		val parameter = ParametersFactory.eINSTANCE.createOrdinaryParameter
		parameter.name = field.name
		parameter.typeReference = EcoreUtil.copy(field.typeReference);
		method.parameters.add(parameter)
		val expressionStatement = StatementsFactory.eINSTANCE.createExpressionStatement
		val assigmentExpression = ExpressionsFactory.eINSTANCE.createAssignmentExpression

		// this.
		val selfReference = ReferencesFactory.eINSTANCE.createSelfReference
		assigmentExpression.child = selfReference

		// .fieldname
		val fieldReference = ReferencesFactory.eINSTANCE.createIdentifierReference
		fieldReference.target = field
		selfReference.next = fieldReference
		selfReference.^self = LiteralsFactory.eINSTANCE.createThis();
		// =
		assigmentExpression.assignmentOperator = OperatorsFactory.eINSTANCE.createAssignment

		// name		
		val identifierReference = ReferencesFactory.eINSTANCE.createIdentifierReference
		identifierReference.target = parameter

		assigmentExpression.value = identifierReference
		expressionStatement.expression = assigmentExpression
		method.statements.add(expressionStatement)
		return method
	}

	static def createGetter(Field field, ClassMethod method) {
		method.name = "get" + field.name.toFirstUpper
		method.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPublic)
		method.typeReference = EcoreUtil.copy(field.typeReference);

		// this.fieldname
		val identifierRef = ReferencesFactory.eINSTANCE.createIdentifierReference
		identifierRef.target = field

		// return
		val ret = StatementsFactory.eINSTANCE.createReturn
		ret.returnValue = identifierRef
		method.statements.add(ret);
		return method
	}

	/**
	 * sorts the member list to ensure that fields are printed before constructors and constructors before methods
	 */
	def static sortMembers(EList<? extends EObject> members) {
		ECollections.sort(members, new Comparator<EObject> {

			override compare(EObject o1, EObject o2) {

				// fields before constructors and methods
				if (o1 instanceof Field && (o2 instanceof Method || o2 instanceof Constructor)) {
					return -1
				} else if ((o1 instanceof Method || o1 instanceof Constructor) && o2 instanceof Field) {
					return 1

				// constructors before Methods	
				} else if (o1 instanceof Constructor && o2 instanceof Method) {
					return -1
				} else if (o1 instanceof Method && o2 instanceof Constructor) {
					return 1
				}
				return 0;
			}

			override equals(Object obj) {
				return this == obj;
			}

		})
	}

	public static def TypeReference createTypeReference(DataType originalDataType,
		Optional<Class> correspondingJavaClassIfExisting) {
		if (null === originalDataType) {
			return TypesFactory.eINSTANCE.createVoid
		}
		var TypeReference innerDataTypeReference = null;
		if (originalDataType instanceof PrimitiveDataType) {
			val type = EcoreUtil.copy(DataTypeCorrespondenceHelper.claimJaMoPPTypeForPrimitiveDataType(originalDataType));
			if (type instanceof TypeReference) {
				innerDataTypeReference = type;
			} else if (type instanceof ConcreteClassifier) {
				innerDataTypeReference = createNamespaceClassifierReference(type);
			} else {
				// This cannot be since the claimForPrimitiveType function does only return TypeReference or ConcreteClassifier
			}
		} else if (correspondingJavaClassIfExisting.present) {
			innerDataTypeReference = createNamespaceClassifierReference(correspondingJavaClassIfExisting.get);
		} else {
			throw new IllegalArgumentException(
				"Either the dataType must be primitive or a correspondingJavaClass must be specified");
		}
		return innerDataTypeReference;
	}

	public static def void initializeClassMethod(ClassMethod classMethod, Method implementedMethod,
		boolean ensurePublic) {
		initializeClassMethod(classMethod, implementedMethod.name, implementedMethod.typeReference,
			implementedMethod.modifiers, implementedMethod.parameters, ensurePublic)
	}

	public static def void initializeClassMethod(ClassMethod classMethod, String name, TypeReference typeReference,
		Modifier[] modifiers, Parameter[] parameters, boolean ensurePublic) {
		classMethod.name = name
		if (null !== typeReference) {
			classMethod.typeReference = EcoreUtil.copy(typeReference)
		}
		if (null !== modifiers) {
			classMethod.annotationsAndModifiers.addAll(EcoreUtil.copyAll(modifiers))
		}
		if (ensurePublic) {
			val alreadyPublic = classMethod.annotationsAndModifiers.filter[modifier|modifier instanceof Public].size > 0
			if (!alreadyPublic) {
				classMethod.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPublic)
			}
		}
		if (null !== parameters) {
			classMethod.parameters.addAll(EcoreUtil.copyAll(parameters))
		}
	}

	public static def ClassMethod findMethodInClass(ConcreteClassifier concreteClassifier, ClassMethod method) {
		for (Method currentMethod : concreteClassifier.methods) {
			if (currentMethod instanceof ClassMethod && currentMethod.name.equals(method.name) &&
				currentMethod.typeParameters.size == method.typeParameters.size) {
				// todo: finish check by comparing type reference and type of each parameter 
				return currentMethod as ClassMethod
			}
		}
		null
	}


}
