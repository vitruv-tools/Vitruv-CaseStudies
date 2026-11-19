package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm;

import static tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelHelper.newPcmRepository;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import tools.vitruv.applications.cbs.commonalities.tests.cbs.CompositeDataTypeTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsBase;
import tools.vitruv.applications.cbs.operators.pcm.PcmPrimitiveDataType;

public class PcmCompositeDataTypeTestModels extends PcmTestModelsBase implements CompositeDataTypeTest.DomainModels {

    private static CompositeDataType newPcmCompositeDataType() {
        CompositeDataType compositeDataType = RepositoryFactory.eINSTANCE.createCompositeDataType();
        compositeDataType.setEntityName(COMPOSITE_DATATYPE_1_NAME);
        return compositeDataType;
    }

    private static CompositeDataType newPcmCompositeDataType(String name, InnerDeclaration... innerDeclarations) {
        CompositeDataType compositeDataType = newPcmCompositeDataType();
        compositeDataType.setEntityName(name);
        compositeDataType.getInnerDeclaration_CompositeDataType().addAll(List.of(innerDeclarations));
        return compositeDataType;
    }

    private static InnerDeclaration newPcmInnerDeclaration(String name, DataType dataType) {
        InnerDeclaration innerDeclaration = RepositoryFactory.eINSTANCE.createInnerDeclaration();
        innerDeclaration.setEntityName(name);
        innerDeclaration.setDatatype_InnerDeclaration(dataType);
        return innerDeclaration;
    }

    /**
     * The PCM repository containing the given data types.
     */
    private static List<EObject> repositoryWithDataTypes(CompositeDataType... compositeDataTypes) {
        Repository pcmRepository = newPcmRepository();
        pcmRepository.getDataTypes__Repository().addAll(List.of(compositeDataTypes));
        return List.of(pcmRepository);
    }

    public PcmCompositeDataTypeTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    // Empty CompositeDataType

    @Override
    public DomainModel emptyCompositeDataTypeCreation() {
        return newModel(() -> repositoryWithDataTypes(newPcmCompositeDataType()));
    }

    // Primitive inner elements

    @Override
    public DomainModel compositeDataTypeWithBooleanElementCreation() {
        return newModel(() -> repositoryWithDataTypes(newPcmCompositeDataType(COMPOSITE_DATATYPE_1_NAME,
                newPcmInnerDeclaration(BOOLEAN_ELEMENT_NAME, PcmPrimitiveDataType.BOOL.getPcmType()))));
    }

    @Override
    public DomainModel compositeDataTypeWithIntegerElementCreation() {
        return newModel(() -> repositoryWithDataTypes(newPcmCompositeDataType(COMPOSITE_DATATYPE_1_NAME,
                newPcmInnerDeclaration(INTEGER_ELEMENT_NAME, PcmPrimitiveDataType.INT.getPcmType()))));
    }

    @Override
    public DomainModel compositeDataTypeWithDoubleElementCreation() {
        return newModel(() -> repositoryWithDataTypes(newPcmCompositeDataType(COMPOSITE_DATATYPE_1_NAME,
                newPcmInnerDeclaration(DOUBLE_ELEMENT_NAME, PcmPrimitiveDataType.DOUBLE.getPcmType()))));
    }

    @Override
    public DomainModel compositeDataTypeWithStringElementCreation() {
        return newModel(() -> repositoryWithDataTypes(newPcmCompositeDataType(COMPOSITE_DATATYPE_1_NAME,
                newPcmInnerDeclaration(STRING_ELEMENT_NAME, PcmPrimitiveDataType.STRING.getPcmType()))));
    }

    @Override
    public DomainModel compositeDataTypeWithWithMultiplePrimitiveElementsCreation() {
        return newModel(() -> repositoryWithDataTypes(newPcmCompositeDataType(COMPOSITE_DATATYPE_1_NAME,
                newPcmInnerDeclaration(BOOLEAN_ELEMENT_NAME, PcmPrimitiveDataType.BOOL.getPcmType()),
                newPcmInnerDeclaration(INTEGER_ELEMENT_NAME, PcmPrimitiveDataType.INT.getPcmType()),
                newPcmInnerDeclaration(DOUBLE_ELEMENT_NAME, PcmPrimitiveDataType.DOUBLE.getPcmType()),
                newPcmInnerDeclaration(STRING_ELEMENT_NAME, PcmPrimitiveDataType.STRING.getPcmType()))));
    }

    // Multiple CompositeDataTypes

    @Override
    public DomainModel multipleCompositeDataTypesWithPrimitiveElementsCreation() {
        return newModel(() -> repositoryWithDataTypes(
                newPcmCompositeDataType(COMPOSITE_DATATYPE_1_NAME,
                        newPcmInnerDeclaration(BOOLEAN_ELEMENT_NAME, PcmPrimitiveDataType.BOOL.getPcmType())),
                newPcmCompositeDataType(COMPOSITE_DATATYPE_2_NAME,
                        newPcmInnerDeclaration(INTEGER_ELEMENT_NAME, PcmPrimitiveDataType.INT.getPcmType()))));
    }

    @Override
    public DomainModel compositeDataTypeWithCompositeElementsCreation() {
        return newModel(() -> {
            CompositeDataType compositeDataType1 = newPcmCompositeDataType(COMPOSITE_DATATYPE_1_NAME,
                    newPcmInnerDeclaration(INTEGER_ELEMENT_NAME, PcmPrimitiveDataType.INT.getPcmType()));
            CompositeDataType compositeDataType2 = newPcmCompositeDataType(COMPOSITE_DATATYPE_2_NAME,
                    newPcmInnerDeclaration(COMPOSITE_ELEMENT_1_NAME, compositeDataType1),
                    newPcmInnerDeclaration(COMPOSITE_ELEMENT_2_NAME, compositeDataType1));
            return repositoryWithDataTypes(compositeDataType1, compositeDataType2);
        });
    }
}
