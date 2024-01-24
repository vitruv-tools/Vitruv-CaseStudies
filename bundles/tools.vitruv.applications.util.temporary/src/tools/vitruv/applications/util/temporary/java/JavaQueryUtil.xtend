package tools.vitruv.applications.util.temporary.java

import tools.mdsd.jamopp.model.java.types.Char
import tools.mdsd.jamopp.model.java.types.Int
import tools.mdsd.jamopp.model.java.types.ClassifierReference
import tools.mdsd.jamopp.model.java.types.NamespaceClassifierReference

class JavaQueryUtil {
		
	def dispatch static getNameFromJaMoPPType(ClassifierReference reference) {
		return reference.target.name
	}

	def dispatch static getNameFromJaMoPPType(NamespaceClassifierReference reference) {
		val classRef = reference.pureClassifierReference
		return classRef.target.name

	// is currently not possible: see: https://bugs.eclipse.org/bugs/show_bug.cgi?id=404817
	// return getNameFromJaMoPPType(classRef)
	}
	
	def dispatch static getNameFromJaMoPPType(tools.mdsd.jamopp.model.java.types.Boolean reference) {
		return "boolean"
	}

	def dispatch static getNameFromJaMoPPType(tools.mdsd.jamopp.model.java.types.Byte reference) {
		return "byte"
	}

	def dispatch static getNameFromJaMoPPType(Char reference) {
		return "char"
	}

	def dispatch static getNameFromJaMoPPType(tools.mdsd.jamopp.model.java.types.Double reference) {
		return "double"
	}

	def dispatch static getNameFromJaMoPPType(tools.mdsd.jamopp.model.java.types.Float reference) {
		return "float"
	}

	def dispatch static getNameFromJaMoPPType(Int reference) {
		return "int"
	}

	def dispatch static getNameFromJaMoPPType(tools.mdsd.jamopp.model.java.types.Long reference) {
		return "long"
	}

	def dispatch static getNameFromJaMoPPType(tools.mdsd.jamopp.model.java.types.Short reference) {
		return "short"
	}

	def dispatch static getNameFromJaMoPPType(tools.mdsd.jamopp.model.java.types.Void reference) {
		return "void"
	}
}