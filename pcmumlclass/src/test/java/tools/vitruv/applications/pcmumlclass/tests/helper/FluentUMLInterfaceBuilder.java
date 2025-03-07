package tools.vitruv.applications.pcmumlclass.tests.helper;

import java.util.List;

import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;

import edu.kit.ipd.sdq.commons.util.java.Quadruple;
import edu.kit.ipd.sdq.commons.util.java.Triple;

public class FluentUMLInterfaceBuilder {
	private Interface result;

	public FluentUMLInterfaceBuilder(String name) {
		result = UMLFactory.eINSTANCE.createInterface();
		result.setName(name);
	}

	public FluentUMLInterfaceBuilder addOperation(String operationName,
			List<Quadruple<String, Type, Integer, Integer>> parameters) {
		return this.addOperation(operationName, new Triple<>(null, 1, 1), parameters);
	}

	public FluentUMLInterfaceBuilder addOperation(String operationName, Triple<Type, Integer, Integer> returnParameter,
			List<Quadruple<String, Type, Integer, Integer>> parameters) {
		Operation operation = UMLFactory.eINSTANCE.createOperation();
		operation.setName(operationName);

		Parameter umlReturnParameter = UMLFactory.eINSTANCE.createParameter();
		umlReturnParameter.setName("returnParam");
		umlReturnParameter.setDirection(ParameterDirectionKind.RETURN_LITERAL);
		umlReturnParameter.setType(returnParameter.get0());
		umlReturnParameter.setLower(returnParameter.get1());
		umlReturnParameter.setUpper(returnParameter.get2());
		operation.getOwnedParameters().add(umlReturnParameter);

		parameters.forEach(currentParameter -> {
			Parameter parameter = UMLFactory.eINSTANCE.createParameter();
			parameter.setName(currentParameter.get0());
			parameter.setType(currentParameter.get1());
			parameter.setDirection(ParameterDirectionKind.IN_LITERAL);
			parameter.setLower(currentParameter.get2());
			parameter.setUpper(currentParameter.get3());
			operation.getOwnedParameters().add(parameter);
		});

		result.getOwnedOperations().add(operation);
		return this;
	}

	public Interface build() {
		return result;
	}
}
