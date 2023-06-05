package tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.mdsd.jamopp.model.java.classifiers.Class
import tools.mdsd.jamopp.model.java.classifiers.ClassifiersFactory
import tools.mdsd.jamopp.model.java.classifiers.Interface
import tools.mdsd.jamopp.model.java.containers.CompilationUnit
import tools.mdsd.jamopp.model.java.containers.ContainersFactory
import tools.mdsd.jamopp.model.java.containers.Package
import tools.mdsd.jamopp.model.java.generics.GenericsFactory
import tools.mdsd.jamopp.model.java.members.ClassMethod
import tools.mdsd.jamopp.model.java.members.Field
import tools.mdsd.jamopp.model.java.members.InterfaceMethod
import tools.mdsd.jamopp.model.java.members.MembersFactory
import tools.mdsd.jamopp.model.java.types.Boolean
import tools.mdsd.jamopp.model.java.types.Int
import tools.mdsd.jamopp.model.java.types.NamespaceClassifierReference
import tools.mdsd.jamopp.model.java.types.PrimitiveType
import tools.mdsd.jamopp.model.java.types.TypesFactory
import tools.mdsd.jamopp.model.java.types.Void
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne

// TODO: merge this class with similar classes in Java <-> UML tests and testutils
@Utility
class JavaCreatorsUtil {
	// === Java: Basic ===
	static def Package createPackage((Package)=>void initialization) {
		var package = ContainersFactory.eINSTANCE.createPackage
		initialization.apply(package)
		return package
	}

	static def CompilationUnit createCompilationUnit((CompilationUnit)=>void initialization) {
		var compilationUnit = ContainersFactory.eINSTANCE.createCompilationUnit
		initialization.apply(compilationUnit)
		return compilationUnit
	}

	static def Class createClass((Class)=>void initialization) {
		var class = ClassifiersFactory.eINSTANCE.createClass
		initialization.apply(class)
		return class
	}

	static def Interface createInterface((Interface)=>void initialization) {
		var interface = ClassifiersFactory.eINSTANCE.createInterface
		initialization.apply(interface)
		return interface
	}

	static def ClassMethod createClassMethod((ClassMethod)=>void initialization) {
		var classMethod = MembersFactory.eINSTANCE.createClassMethod
		initialization.apply(classMethod)
		return classMethod
	}

	static def Field createField((Field)=>void initialization) {
		var field = MembersFactory.eINSTANCE.createField
		initialization.apply(field)
		return field
	}

	static def InterfaceMethod createInterfaceMethod((InterfaceMethod)=>void initialization) {
		var interfaceMethod = MembersFactory.eINSTANCE.createInterfaceMethod
		initialization.apply(interfaceMethod)
		return interfaceMethod
	}

	// === simple types ===
	static def Void createVoid() {
		return TypesFactory.eINSTANCE.createVoid
	}

	static def Int createInt() {
		return TypesFactory.eINSTANCE.createInt
	}

	static def Boolean createBoolean() {
		return TypesFactory.eINSTANCE.createBoolean
	}

	// === helper ===
	static def NamespaceClassifierReference getReference(CompilationUnit compilationUnit) {
		var referencedClassifier = compilationUnit.classifiers.claimOne()

		var classifierReference = TypesFactory.eINSTANCE.createClassifierReference
		classifierReference.target = referencedClassifier

		var reference = TypesFactory.eINSTANCE.createNamespaceClassifierReference
		reference.classifierReferences += classifierReference
		reference.namespaces += compilationUnit.namespaces

		return reference
	}

	static def NamespaceClassifierReference getTypedReference(CompilationUnit outerCompilationUnit,
		CompilationUnit innerCompilationUnit) {
		val outerReference = getReference(outerCompilationUnit)
		val innerReference = getReference(innerCompilationUnit)

		val typeArgument = GenericsFactory.eINSTANCE.createQualifiedTypeArgument
		typeArgument.typeReference = innerReference

		outerReference.classifierReferences.claimOne.typeArguments += typeArgument

		return outerReference
	}

	static def PrimitiveType getReference(PrimitiveTypeEnum type) {
		switch (type) {
			case BOOL: {
				return TypesFactory.eINSTANCE.createBoolean
			}
			case BYTE: {
				return TypesFactory.eINSTANCE.createByte
			}
			case CHAR: {
				return TypesFactory.eINSTANCE.createChar
			}
			case DOUBLE: {
				return TypesFactory.eINSTANCE.createDouble
			}
			case INT: {
				return TypesFactory.eINSTANCE.createInt
			}
			case LONG: {
				return TypesFactory.eINSTANCE.createLong
			}
			case STRING: {
				throw new IllegalArgumentException(type.toString + " can't be mapped to primitive type")
			}
			default: {
				throw new IllegalArgumentException(type.toString + " can't be mapped to primitive type")
			}
		}
	}

	static def String getRequiredInterfacFieldOrVariableName(String interfaceName, String componentName) {
		componentName + "_requires_" + interfaceName
	}

	static def String captialize(String input) {
		return input.substring(0, 1).toUpperCase() + input.substring(1)
	}
}
