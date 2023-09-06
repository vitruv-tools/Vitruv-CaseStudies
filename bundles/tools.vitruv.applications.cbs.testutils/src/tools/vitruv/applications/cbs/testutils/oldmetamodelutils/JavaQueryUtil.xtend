package tools.vitruv.applications.cbs.testutils.oldmetamodelutils

import org.emftext.language.java.types.Char
import org.emftext.language.java.types.Int
import org.emftext.language.java.types.ClassifierReference
import org.emftext.language.java.types.NamespaceClassifierReference

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
	
	def dispatch static getNameFromJaMoPPType(org.emftext.language.java.types.Boolean reference) {
		return "boolean"
	}

	def dispatch static getNameFromJaMoPPType(org.emftext.language.java.types.Byte reference) {
		return "byte"
	}

	def dispatch static getNameFromJaMoPPType(Char reference) {
		return "char"
	}

	def dispatch static getNameFromJaMoPPType(org.emftext.language.java.types.Double reference) {
		return "double"
	}

	def dispatch static getNameFromJaMoPPType(org.emftext.language.java.types.Float reference) {
		return "float"
	}

	def dispatch static getNameFromJaMoPPType(Int reference) {
		return "int"
	}

	def dispatch static getNameFromJaMoPPType(org.emftext.language.java.types.Long reference) {
		return "long"
	}

	def dispatch static getNameFromJaMoPPType(org.emftext.language.java.types.Short reference) {
		return "short"
	}

	def dispatch static getNameFromJaMoPPType(org.emftext.language.java.types.Void reference) {
		return "void"
	}
}
