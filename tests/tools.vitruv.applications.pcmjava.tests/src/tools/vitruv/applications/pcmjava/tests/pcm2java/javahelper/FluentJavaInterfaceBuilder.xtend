package tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.imports.Import
import org.emftext.language.java.imports.ImportsFactory
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.parameters.ParametersFactory

import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.createCompilationUnit
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.createInterface
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.createInterfaceMethod

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne

/**
 * Can be used to dynamically create Java Interfaces with methods addImport() and addMethod().
 * Build returns a CompilationUnit according to the previous made add*() calls.
 * 
 * Caution: the FluentJavaInterfaceBuilder does not check for correctness of the builder API calls.
 * This means a duplicated import or methods call leads to duplicated imports or methods in the 
 * resulting model.
 */
class FluentJavaInterfaceBuilder {
	// === data ===
	final String interfaceName
	final String namespace
	final List<Import> interfaceImports
	final List<MethodDescription> interfaceMethods

	new(String interfaceName, String namespace) {
		this.interfaceName = interfaceName
		this.namespace = namespace
		this.interfaceImports = new ArrayList<Import>()
		this.interfaceMethods = new ArrayList<MethodDescription>()
	}

	// === builder-API ===
	def FluentJavaInterfaceBuilder addImport(CompilationUnit importedCompilationUnit) {
		var import = ImportsFactory.eINSTANCE.createClassifierImport
		import.namespaces += importedCompilationUnit.namespaces
		import.classifier = importedCompilationUnit.classifiers.claimOne

		this.interfaceImports += import
		return this
	}

	def FluentJavaInterfaceBuilder addMethod(MethodDescription description) {
		this.interfaceMethods += description
		return this
	}

	def CompilationUnit build() {
		val interface = createInterface [
			annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
			name = interfaceName
		]

		interface.members += interfaceMethods.map [ methodDescription |
			val methodParameters = methodDescription.parameters.map [ parameterDescription |
				var parameter = ParametersFactory.eINSTANCE.createOrdinaryParameter
				parameter.name = parameterDescription.name
				parameter.typeReference = EcoreUtil.copy(parameterDescription.type)
				return parameter
			]
			createInterfaceMethod [
				name = methodDescription.name
				typeReference = methodDescription.returnType
				parameters += methodParameters
			]
		]

		return createCompilationUnit[
			name = namespace + "." + interfaceName + ".java"
			namespaces += namespace.split("\\.")
			classifiers += interface
			imports += interfaceImports
		]
	}
}
