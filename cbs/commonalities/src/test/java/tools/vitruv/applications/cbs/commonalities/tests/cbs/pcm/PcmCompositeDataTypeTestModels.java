package tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm;

import static tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelHelper.newPcmRepository;

import java.util.List;

import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import tools.vitruv.applications.cbs.commonalities.pcm.PcmPrimitiveDataType;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.CompositeDataTypeTest;
import tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelHelper;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsBase;

public class PcmCompositeDataTypeTestModels extends PcmTestModelsBase implements CompositeDataTypeTest.DomainModels {

    private static CompositeDataType newPcmCompositeDataType() {
        CompositeDataType dataType = RepositoryFactory.eINSTANCE.createCompositeDataType();
        dataType.setEntityName(COMPOSITE_DATATYPE_1_NAME);
        return dataType;
    }

    private static InnerDeclaration newPcmInnerDeclaration() {
        return RepositoryFactory.eINSTANCE.createInnerDeclaration();
    }

    public PcmCompositeDataTypeTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    @Override
    public DomainModel emptyCompositeDataTypeCreation() {
        return newModel(() -> {
            Repository pcmRepository = newPcmRepository();
            pcmRepository.getDataTypes__Repository().add(newPcmCompositeDataType());
            return List.of(pcmRepository);
        });
    }

    @Override
    public DomainModel compositeDataTypeWithBooleanElementCreation() {
        return newModel(() -> {
            Repository pcmRepository = newPcmRepository();

            CompositeDataType compositeType = newPcmCompositeDataType();
            InnerDeclaration innerDecl = newPcmInnerDeclaration();
            innerDecl.setEntityName(BOOLEAN_ELEMENT_NAME);
            innerDecl.setDatatype_InnerDeclaration(PcmPrimitiveDataType.BOOL.getPcmType());
            compositeType.getInnerDeclaration_CompositeDataType().add(innerDecl);

            pcmRepository.getDataTypes__Repository().add(compositeType);
            return List.of(pcmRepository);
        });
    }

    // Similar pattern for other primitive type methods...

    @Override
    public DomainModel compositeDataTypeWithWithMultiplePrimitiveElementsCreation() {
        return newModel(() -> {
            Repository pcmRepository = newPcmRepository();
            CompositeDataType compositeType = newPcmCompositeDataType();

            // Add boolean element
            InnerDeclaration boolDecl = newPcmInnerDeclaration();
            boolDecl.setEntityName(BOOLEAN_ELEMENT_NAME);
            boolDecl.setDatatype_InnerDeclaration(PcmPrimitiveDataType.BOOL.getPcmType());
            compositeType.getInnerDeclaration_CompositeDataType().add(boolDecl);

            // Add integer element
            InnerDeclaration intDecl = newPcmInnerDeclaration();
            intDecl.setEntityName(INTEGER_ELEMENT_NAME);
            intDecl.setDatatype_InnerDeclaration(PcmPrimitiveDataType.INT.getPcmType());
            compositeType.getInnerDeclaration_CompositeDataType().add(intDecl);

            // Add double element
            InnerDeclaration doubleDecl = newPcmInnerDeclaration();
            doubleDecl.setEntityName(DOUBLE_ELEMENT_NAME);
            doubleDecl.setDatatype_InnerDeclaration(PcmPrimitiveDataType.DOUBLE.getPcmType());
            compositeType.getInnerDeclaration_CompositeDataType().add(doubleDecl);

            // Add string element
            InnerDeclaration stringDecl = newPcmInnerDeclaration();
            stringDecl.setEntityName(STRING_ELEMENT_NAME);
            stringDecl.setDatatype_InnerDeclaration(PcmPrimitiveDataType.STRING.getPcmType());
            compositeType.getInnerDeclaration_CompositeDataType().add(stringDecl);

            pcmRepository.getDataTypes__Repository().add(compositeType);
            return List.of(pcmRepository);
        });
    }

    @Override
    public DomainModel multipleCompositeDataTypesWithPrimitiveElementsCreation() {
        return newModel(() -> {
            Repository pcmRepository = newPcmRepository();

            // First composite type with boolean element
            CompositeDataType compositeType1 = newPcmCompositeDataType();
            InnerDeclaration boolDecl = newPcmInnerDeclaration();
            boolDecl.setEntityName(BOOLEAN_ELEMENT_NAME);
            boolDecl.setDatatype_InnerDeclaration(PcmPrimitiveDataType.BOOL.getPcmType());
            compositeType1.getInnerDeclaration_CompositeDataType().add(boolDecl);
            pcmRepository.getDataTypes__Repository().add(compositeType1);

            // Second composite type with integer element
            CompositeDataType compositeType2 = newPcmCompositeDataType();
            compositeType2.setEntityName(COMPOSITE_DATATYPE_2_NAME);
            InnerDeclaration intDecl = newPcmInnerDeclaration();
            intDecl.setEntityName(INTEGER_ELEMENT_NAME);
            intDecl.setDatatype_InnerDeclaration(PcmPrimitiveDataType.INT.getPcmType());
            compositeType2.getInnerDeclaration_CompositeDataType().add(intDecl);
            pcmRepository.getDataTypes__Repository().add(compositeType2);

            return List.of(pcmRepository);
        });
    }

    @Override
    public DomainModel compositeDataTypeWithCompositeElementsCreation() {
        return newModel(() -> {
            Repository pcmRepository = newPcmRepository();

            // Create first composite type with integer element
            CompositeDataType compositeType1 = newPcmCompositeDataType();
            InnerDeclaration intDecl = newPcmInnerDeclaration();
            intDecl.setEntityName(INTEGER_ELEMENT_NAME);
            intDecl.setDatatype_InnerDeclaration(PcmPrimitiveDataType.INT.getPcmType());
            compositeType1.getInnerDeclaration_CompositeDataType().add(intDecl);
            pcmRepository.getDataTypes__Repository().add(compositeType1);

            // Create second composite type with references to first type
            CompositeDataType compositeType2 = newPcmCompositeDataType();
            compositeType2.setEntityName(COMPOSITE_DATATYPE_2_NAME);

            InnerDeclaration compDecl1 = newPcmInnerDeclaration();
            compDecl1.setEntityName(COMPOSITE_ELEMENT_1_NAME);
            compDecl1.setDatatype_InnerDeclaration(compositeType1);
            compositeType2.getInnerDeclaration_CompositeDataType().add(compDecl1);

            InnerDeclaration compDecl2 = newPcmInnerDeclaration();
            compDecl2.setEntityName(COMPOSITE_ELEMENT_2_NAME);
            compDecl2.setDatatype_InnerDeclaration(compositeType1);
            compositeType2.getInnerDeclaration_CompositeDataType().add(compDecl2);

            pcmRepository.getDataTypes__Repository().add(compositeType2);

            return List.of(pcmRepository);
        });
    }

    @Override
    public DomainModel compositeDataTypeWithIntegerElementCreation() {
        return newModel(() -> {
            // Create repository
            Repository pcmRepository = PcmTestModelHelper.newPcmRepository();

            // Create composite data type with integer element
            CompositeDataType compositeType = newPcmCompositeDataType();

            // Create and configure inner declaration
            InnerDeclaration innerDecl = newPcmInnerDeclaration();
            innerDecl.setEntityName(CompositeDataTypeTest.DomainModels.INTEGER_ELEMENT_NAME);
            innerDecl.setDatatype_InnerDeclaration(PcmPrimitiveDataType.INT.getPcmType());

            // Add inner declaration to composite type
            compositeType.getInnerDeclaration_CompositeDataType().add(innerDecl);

            // Add composite type to repository
            pcmRepository.getDataTypes__Repository().add(compositeType);

            return List.of(pcmRepository);
        });
    }

    @Override
    public DomainModel compositeDataTypeWithDoubleElementCreation() {
        return newModel(() -> {
            // Create repository
            Repository pcmRepository = PcmTestModelHelper.newPcmRepository();

            // Create composite data type with double element
            CompositeDataType compositeType = newPcmCompositeDataType();

            // Create and configure inner declaration for double element
            InnerDeclaration innerDecl = newPcmInnerDeclaration();
            innerDecl.setEntityName(CompositeDataTypeTest.DomainModels.DOUBLE_ELEMENT_NAME);
            innerDecl.setDatatype_InnerDeclaration(PcmPrimitiveDataType.DOUBLE.getPcmType());

            // Add inner declaration to composite type
            compositeType.getInnerDeclaration_CompositeDataType().add(innerDecl);

            // Add composite type to repository
            pcmRepository.getDataTypes__Repository().add(compositeType);

            return List.of(pcmRepository);
        });
    }

    @Override
    public DomainModel compositeDataTypeWithStringElementCreation() {
        return newModel(() -> {
            // Create repository
            Repository pcmRepository = PcmTestModelHelper.newPcmRepository();

            // Create composite data type with string element
            CompositeDataType compositeType = newPcmCompositeDataType();

            // Create and configure inner declaration for string element
            InnerDeclaration innerDecl = newPcmInnerDeclaration();
            innerDecl.setEntityName(CompositeDataTypeTest.DomainModels.STRING_ELEMENT_NAME);
            innerDecl.setDatatype_InnerDeclaration(PcmPrimitiveDataType.STRING.getPcmType());

            // Add inner declaration to composite type
            compositeType.getInnerDeclaration_CompositeDataType().add(innerDecl);

            // Add composite type to repository
            pcmRepository.getDataTypes__Repository().add(compositeType);

            return List.of(pcmRepository);
        });
    }
}