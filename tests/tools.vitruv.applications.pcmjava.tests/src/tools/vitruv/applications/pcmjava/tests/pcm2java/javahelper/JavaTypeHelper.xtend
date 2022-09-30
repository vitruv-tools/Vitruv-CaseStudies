package tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper

import org.emftext.language.java.types.Int
import org.emftext.language.java.types.TypesFactory
import org.emftext.language.java.types.Void

class JavaTypeHelper {
	/*
	 * TODO:
	 * original idea was to return always the same object
	 * is this needed?
	 * // Map<Class<? extends Type>, TypeReference> knownTypes
	 * return knownTypes.computeIfAbsent(typeof(Void), [_ | TypesFactory.eINSTANCE.createVoid]) as Void
	 */
	
	new() {
	}
	
	def Void getVoid() {
		return TypesFactory.eINSTANCE.createVoid
	}
	
	def Int getInt() {
		return TypesFactory.eINSTANCE.createInt
	}
	
}
