package tools.vitruv.applications.pcmumlclass.tests.helper;

import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;

import edu.kit.ipd.sdq.commons.util.java.Pair;

public class FluentUMLClassBuilder {
	private final Class result;

	public FluentUMLClassBuilder(String name, boolean isFinal) {
		result = UMLFactory.eINSTANCE.createClass();
		result.setName(name);
		result.setIsFinalSpecialization(isFinal);
	}

	public FluentUMLClassBuilder addDefaultConstructor() {
		result.createOwnedOperation(result.getName(), new BasicEList<>(), new BasicEList<>());
		return this;
	}

	public FluentUMLClassBuilder addParameterizedConstructor(List<Pair<String, Type>> parameters) {
		EList<String> parameterNames = new BasicEList<>();
		EList<Type> parameterTypes = new BasicEList<>();
		parameters.forEach(parameter -> {
			parameterNames.add(parameter.get0());
			parameterTypes.add(parameter.get1());
		});
		result.createOwnedOperation(result.getName(), parameterNames, parameterTypes);
		return this;
	}

	public FluentUMLClassBuilder addAttribute(String name, Type type) {
		result.createOwnedAttribute(name, type);
		return this;
	}

	public FluentUMLClassBuilder addAttribute(String name, Type type, int lower, int upper) {
		result.createOwnedAttribute(name, type, lower, upper);
		return this;
	}

	public FluentUMLClassBuilder addGeneralization(Classifier parentClassifier) {
		result.createGeneralization(parentClassifier);
		return this;
	}

	public FluentUMLClassBuilder addInterfaceRealization(String name, Interface realizedInterface) {
		result.createInterfaceRealization(name, realizedInterface);
		return this;
	}

	public Class build() {
		return result;
	}
}
