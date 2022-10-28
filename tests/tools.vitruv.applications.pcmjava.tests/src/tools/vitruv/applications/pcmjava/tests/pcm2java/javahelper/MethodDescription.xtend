package tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper

import java.util.List
import org.emftext.language.java.types.TypeReference

class MethodDescription {
	public final String name
	public final TypeReference returnType
	public final List<ParameterDescription> parameters
	
	new(String name, TypeReference returnType, List<ParameterDescription> parameters){
		this.name = name
		this.returnType = returnType
		this.parameters = parameters
	}
}