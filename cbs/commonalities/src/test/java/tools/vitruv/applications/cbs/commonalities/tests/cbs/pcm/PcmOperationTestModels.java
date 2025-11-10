package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm;

import java.util.List;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import tools.vitruv.applications.cbs.commonalities.pcm.PcmPrimitiveDataType;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.OperationTest;
import tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelHelper;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsBase;
import static tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelHelper.newPcmRepository;

public class PcmOperationTestModels extends PcmTestModelsBase implements OperationTest.DomainModels {

    private static OperationInterface newPcmOperationInterface() {
        OperationInterface opInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
        opInterface.setEntityName(INTERFACE_NAME);
        return opInterface;
    }

    private static OperationSignature newPcmOperationSignature() {
        OperationSignature signature = RepositoryFactory.eINSTANCE.createOperationSignature();
        signature.setEntityName(OPERATION_NAME);
        return signature;
    }

    private static Parameter newPcmParameter() {
        return RepositoryFactory.eINSTANCE.createParameter();
    }

    public PcmOperationTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    @Override
    public DomainModel emptyOperationCreation() {
        return newModel(() -> {
            Repository pcmRepository = newPcmRepository();

            // Create operation interface with empty signature
            OperationInterface opInterface = newPcmOperationInterface();
            opInterface.getSignatures__OperationInterface().add(newPcmOperationSignature());
            pcmRepository.getInterfaces__Repository().add(opInterface);

            return List.of(pcmRepository);
        });
    }

    @Override
    public DomainModel operationWithIntegerReturnCreation() {
        return newModel(() -> {
            Repository pcmRepository = newPcmRepository();

            // Create operation interface with int return signature
            OperationInterface opInterface = newPcmOperationInterface();
            OperationSignature signature = newPcmOperationSignature();
            signature.setReturnType__OperationSignature(PcmPrimitiveDataType.INT.getPcmType());
            opInterface.getSignatures__OperationInterface().add(signature);
            pcmRepository.getInterfaces__Repository().add(opInterface);

            return List.of(pcmRepository);
        });
    }

    // ...similar pattern for other methods...

    @Override
    public DomainModel multipleOperationsCreation() {
        return newModel(() -> {
            Repository pcmRepository = newPcmRepository();
            OperationInterface opInterface = newPcmOperationInterface();

            // First operation with boolean parameter
            OperationSignature signature1 = newPcmOperationSignature();
            signature1.setReturnType__OperationSignature(PcmPrimitiveDataType.INT.getPcmType());

            Parameter boolParam = newPcmParameter();
            boolParam.setParameterName(BOOLEAN_PARAMETER_NAME);
            boolParam.setDataType__Parameter(PcmPrimitiveDataType.BOOL.getPcmType());
            signature1.getParameters__OperationSignature().add(boolParam);

            opInterface.getSignatures__OperationInterface().add(signature1);

            // Second operation with string parameter
            OperationSignature signature2 = newPcmOperationSignature();
            signature2.setEntityName(OPERATION_2_NAME);
            signature2.setReturnType__OperationSignature(PcmPrimitiveDataType.INT.getPcmType());

            Parameter stringParam = newPcmParameter();
            stringParam.setParameterName(STRING_PARAMETER_NAME);
            stringParam.setDataType__Parameter(PcmPrimitiveDataType.STRING.getPcmType());
            signature2.getParameters__OperationSignature().add(stringParam);

            opInterface.getSignatures__OperationInterface().add(signature2);

            pcmRepository.getInterfaces__Repository().add(opInterface);

            return List.of(pcmRepository);
        });
    }

    @Override
    public DomainModel operationWithStringReturnCreation() {
        return newModel(() -> {
            // Create repository
            Repository pcmRepository = PcmTestModelHelper.newPcmRepository();

            // Create operation interface
            OperationInterface opInterface = newPcmOperationInterface();

            // Create operation signature with string return type
            OperationSignature signature = newPcmOperationSignature();
            signature.setReturnType__OperationSignature(PcmPrimitiveDataType.STRING.getPcmType());

            // Add signature to interface
            opInterface.getSignatures__OperationInterface().add(signature);

            // Add interface to repository
            pcmRepository.getInterfaces__Repository().add(opInterface);

            return List.of(pcmRepository);
        });
    }

    @Override
    public DomainModel operationWithIntegerInputCreation() {
        return newModel(() -> {
            // Create repository
            Repository pcmRepository = PcmTestModelHelper.newPcmRepository();

            // Create operation interface
            OperationInterface opInterface = newPcmOperationInterface();

            // Create operation signature
            OperationSignature signature = newPcmOperationSignature();

            // Create and configure integer parameter
            Parameter parameter = newPcmParameter();
            parameter.setParameterName(OperationTest.DomainModels.INTEGER_PARAMETER_NAME);
            parameter.setDataType__Parameter(PcmPrimitiveDataType.INT.getPcmType());

            // Build hierarchy
            signature.getParameters__OperationSignature().add(parameter);
            opInterface.getSignatures__OperationInterface().add(signature);
            pcmRepository.getInterfaces__Repository().add(opInterface);

            return List.of(pcmRepository);
        });
    }

    @Override
    public DomainModel operationWithMultiplePrimitiveInputsCreation() {
        return newModel(() -> {
            // Create repository
            Repository pcmRepository = PcmTestModelHelper.newPcmRepository();

            // Create operation interface
            OperationInterface opInterface = newPcmOperationInterface();

            // Create operation signature
            OperationSignature signature = newPcmOperationSignature();

            // Create and configure boolean parameter
            Parameter boolParam = newPcmParameter();
            boolParam.setParameterName(OperationTest.DomainModels.BOOLEAN_PARAMETER_NAME);
            boolParam.setDataType__Parameter(PcmPrimitiveDataType.BOOL.getPcmType());
            signature.getParameters__OperationSignature().add(boolParam);

            // Create and configure integer parameter
            Parameter intParam = newPcmParameter();
            intParam.setParameterName(OperationTest.DomainModels.INTEGER_PARAMETER_NAME);
            intParam.setDataType__Parameter(PcmPrimitiveDataType.INT.getPcmType());
            signature.getParameters__OperationSignature().add(intParam);

            // Create and configure double parameter
            Parameter doubleParam = newPcmParameter();
            doubleParam.setParameterName(OperationTest.DomainModels.DOUBLE_PARAMETER_NAME);
            doubleParam.setDataType__Parameter(PcmPrimitiveDataType.DOUBLE.getPcmType());
            signature.getParameters__OperationSignature().add(doubleParam);

            // Build model hierarchy
            opInterface.getSignatures__OperationInterface().add(signature);
            pcmRepository.getInterfaces__Repository().add(opInterface);

            return List.of(pcmRepository);
        });
    }

    @Override
    public DomainModel operationWithStringInputCreation() {
        return newModel(() -> {
            // Create repository
            Repository pcmRepository = PcmTestModelHelper.newPcmRepository();

            // Create operation interface
            OperationInterface opInterface = newPcmOperationInterface();

            // Create operation signature with string parameter
            OperationSignature signature = newPcmOperationSignature();

            // Create and configure string parameter
            Parameter stringParam = newPcmParameter();
            stringParam.setParameterName(OperationTest.DomainModels.STRING_PARAMETER_NAME);
            stringParam.setDataType__Parameter(PcmPrimitiveDataType.STRING.getPcmType());

            // Build model hierarchy
            signature.getParameters__OperationSignature().add(stringParam);
            opInterface.getSignatures__OperationInterface().add(signature);
            pcmRepository.getInterfaces__Repository().add(opInterface);

            return List.of(pcmRepository);
        });
    }

    @Override
    public DomainModel operationWithMixedInputsAndReturnCreation() {
        return newModel(() -> {
            // Create repository
            Repository pcmRepository = PcmTestModelHelper.newPcmRepository();

            // Create operation interface
            OperationInterface opInterface = newPcmOperationInterface();

            // Create operation signature with return type and parameters
            OperationSignature signature = newPcmOperationSignature();
            signature.setReturnType__OperationSignature(PcmPrimitiveDataType.INT.getPcmType());

            // Create and configure integer parameter
            Parameter intParam = newPcmParameter();
            intParam.setParameterName(OperationTest.DomainModels.INTEGER_PARAMETER_NAME);
            intParam.setDataType__Parameter(PcmPrimitiveDataType.INT.getPcmType());
            signature.getParameters__OperationSignature().add(intParam);

            // Create and configure string parameter
            Parameter stringParam = newPcmParameter();
            stringParam.setParameterName(OperationTest.DomainModels.STRING_PARAMETER_NAME);
            stringParam.setDataType__Parameter(PcmPrimitiveDataType.STRING.getPcmType());
            signature.getParameters__OperationSignature().add(stringParam);

            // Build model hierarchy
            opInterface.getSignatures__OperationInterface().add(signature);
            pcmRepository.getInterfaces__Repository().add(opInterface);

            return List.of(pcmRepository);
        });
    }
}