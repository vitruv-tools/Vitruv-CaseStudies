package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm;

import static tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelHelper.newPcmRepository;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import tools.vitruv.applications.cbs.commonalities.tests.cbs.OperationTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsBase;
import tools.vitruv.applications.cbs.operators.pcm.PcmPrimitiveDataType;

public class PcmOperationTestModels extends PcmTestModelsBase implements OperationTest.DomainModels {

    private static OperationInterface newPcmOperationInterface() {
        OperationInterface operationInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
        operationInterface.setEntityName(INTERFACE_NAME);
        return operationInterface;
    }

    private static OperationSignature newPcmOperationSignature() {
        OperationSignature signature = RepositoryFactory.eINSTANCE.createOperationSignature();
        signature.setEntityName(OPERATION_NAME);
        return signature;
    }

    private static OperationSignature newPcmOperationSignature(DataType returnType, Parameter... parameters) {
        OperationSignature signature = newPcmOperationSignature();
        signature.setReturnType__OperationSignature(returnType);
        signature.getParameters__OperationSignature().addAll(List.of(parameters));
        return signature;
    }

    private static OperationSignature newPcmOperationSignatureWithParameters(Parameter... parameters) {
        OperationSignature signature = newPcmOperationSignature();
        signature.getParameters__OperationSignature().addAll(List.of(parameters));
        return signature;
    }

    private static Parameter newPcmParameter(String name, DataType dataType) {
        Parameter parameter = RepositoryFactory.eINSTANCE.createParameter();
        parameter.setParameterName(name);
        parameter.setDataType__Parameter(dataType);
        return parameter;
    }

    /**
     * The PCM repository containing an operation interface with the given
     * signatures.
     */
    private static List<EObject> interfaceWithSignatures(OperationSignature... signatures) {
        Repository pcmRepository = newPcmRepository();
        OperationInterface operationInterface = newPcmOperationInterface();
        operationInterface.getSignatures__OperationInterface().addAll(List.of(signatures));
        pcmRepository.getInterfaces__Repository().add(operationInterface);
        return List.of(pcmRepository);
    }

    public PcmOperationTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    // Empty

    @Override
    public DomainModel emptyOperationCreation() {
        return newModel(() -> interfaceWithSignatures(newPcmOperationSignature()));
    }

    // Return type

    @Override
    public DomainModel operationWithIntegerReturnCreation() {
        return newModel(() -> interfaceWithSignatures(
                newPcmOperationSignature(PcmPrimitiveDataType.INT.getPcmType())));
    }

    @Override
    public DomainModel operationWithStringReturnCreation() {
        return newModel(() -> interfaceWithSignatures(
                newPcmOperationSignature(PcmPrimitiveDataType.STRING.getPcmType())));
    }

    // Input parameters

    @Override
    public DomainModel operationWithIntegerInputCreation() {
        return newModel(() -> interfaceWithSignatures(newPcmOperationSignatureWithParameters(
                newPcmParameter(INTEGER_PARAMETER_NAME, PcmPrimitiveDataType.INT.getPcmType()))));
    }

    @Override
    public DomainModel operationWithMultiplePrimitiveInputsCreation() {
        return newModel(() -> interfaceWithSignatures(newPcmOperationSignatureWithParameters(
                newPcmParameter(BOOLEAN_PARAMETER_NAME, PcmPrimitiveDataType.BOOL.getPcmType()),
                newPcmParameter(INTEGER_PARAMETER_NAME, PcmPrimitiveDataType.INT.getPcmType()),
                newPcmParameter(DOUBLE_PARAMETER_NAME, PcmPrimitiveDataType.DOUBLE.getPcmType()))));
    }

    @Override
    public DomainModel operationWithStringInputCreation() {
        return newModel(() -> interfaceWithSignatures(newPcmOperationSignatureWithParameters(
                newPcmParameter(STRING_PARAMETER_NAME, PcmPrimitiveDataType.STRING.getPcmType()))));
    }

    // Mixed input and return types

    @Override
    public DomainModel operationWithMixedInputsAndReturnCreation() {
        return newModel(() -> interfaceWithSignatures(newPcmOperationSignature(
                PcmPrimitiveDataType.INT.getPcmType(),
                newPcmParameter(INTEGER_PARAMETER_NAME, PcmPrimitiveDataType.INT.getPcmType()),
                newPcmParameter(STRING_PARAMETER_NAME, PcmPrimitiveDataType.STRING.getPcmType()))));
    }

    // Multiple operations

    @Override
    public DomainModel multipleOperationsCreation() {
        return newModel(() -> {
            OperationSignature signature1 = newPcmOperationSignature(PcmPrimitiveDataType.INT.getPcmType(),
                    newPcmParameter(BOOLEAN_PARAMETER_NAME, PcmPrimitiveDataType.BOOL.getPcmType()));
            OperationSignature signature2 = newPcmOperationSignature(PcmPrimitiveDataType.INT.getPcmType(),
                    newPcmParameter(STRING_PARAMETER_NAME, PcmPrimitiveDataType.STRING.getPcmType()));
            signature2.setEntityName(OPERATION_2_NAME);
            return interfaceWithSignatures(signature1, signature2);
        });
    }
}
