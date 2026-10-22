package tools.vitruv.applications.pcmumlclass.tests.helper;

import java.util.List;

import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ParameterModifier;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import edu.kit.ipd.sdq.commons.util.java.Pair;

public class FluentPCMOperationInterfaceBuilder {
	private final OperationInterface result;

	public FluentPCMOperationInterfaceBuilder(String enitiyName) {
		result = RepositoryFactory.eINSTANCE.createOperationInterface();
		result.setEntityName(enitiyName);
	}

	public FluentPCMOperationInterfaceBuilder addSignature(String signatureName,
			List<Pair<String, DataType>> parameters) {
		OperationSignature operationSignature = RepositoryFactory.eINSTANCE.createOperationSignature();
		operationSignature.setEntityName(signatureName);
		parameters.forEach(parameter -> {
			Parameter pcmParameter = RepositoryFactory.eINSTANCE.createParameter();
			pcmParameter.setParameterName(parameter.get0());
			pcmParameter.setDataType__Parameter(parameter.get1());
			pcmParameter.setModifier__Parameter(ParameterModifier.INOUT);
			operationSignature.getParameters__OperationSignature().add(pcmParameter);
		});

		result.getSignatures__OperationInterface().add(operationSignature);

		return this;
	}

	public FluentPCMOperationInterfaceBuilder addSignatureWithReturnType(String signatureName, DataType returnType,
			List<Pair<String, DataType>> parameters) {
		this.addSignature(signatureName, parameters);
		OperationSignature signature = IterableExtensions.findFirst(result.getSignatures__OperationInterface(),
				currentSignature -> currentSignature.getEntityName().equals(signatureName));
		signature.setReturnType__OperationSignature(returnType);
		return this;
	}

	public OperationInterface build() {
		return result;
	}
}
