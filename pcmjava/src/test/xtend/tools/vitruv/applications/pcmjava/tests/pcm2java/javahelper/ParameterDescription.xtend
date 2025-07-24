package tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper

import org.emftext.language.java.types.TypeReference

class ParameterDescription {
	public final String name
	public final TypeReference type

	new(String name, TypeReference type) {
		this.name = name
		this.type = type
	}
}
