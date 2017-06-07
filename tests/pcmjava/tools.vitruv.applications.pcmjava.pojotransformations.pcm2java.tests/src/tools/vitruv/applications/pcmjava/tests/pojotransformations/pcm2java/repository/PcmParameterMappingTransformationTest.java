package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.repository;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.junit.Test;

import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.Pcm2JavaTransformationTest;
import tools.vitruv.applications.pcmjava.tests.util.Pcm2JavaTestUtils;
import tools.vitruv.applications.pcmjava.util.PcmJavaUtils;
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil;

public class PcmParameterMappingTransformationTest extends Pcm2JavaTransformationTest {

    @Test
    public void testAddParameter() throws Throwable {
        final Parameter param = this.createAndSyncRepoOpSigAndParameter();

        this.assertParameterCorrespondences(param);
    }

    @Test
    public void testChangeParameterName() throws Throwable {
        final Parameter param = this.createAndSyncRepoOpSigAndParameter();

        PcmJavaUtils.setParameterName(param, Pcm2JavaTestUtils.PARAMETER_NAME + Pcm2JavaTestUtils.RENAME);
        super.saveAndSynchronizeChanges(param);

        this.assertParameterCorrespondences(param);
    }

    @Test
    public void testChangeParameterType() throws Throwable {
        final Parameter param = this.createAndSyncRepoOpSigAndParameter();

        final Repository repo = param.getOperationSignature__Parameter().getInterface__OperationSignature().getRepository__Interface();
        final PrimitiveDataType pdt = createPrimitiveDataType(PrimitiveTypeEnum.STRING, repo);
        param.setDataType__Parameter(pdt);
        super.saveAndSynchronizeChanges(param);

        this.assertParameterCorrespondences(param);
    }

    @Test
    public void testAddParameterWithCompositeDataType() throws Throwable {
        final Parameter param = super.createAndSyncRepoOpSigAndParameterWithDataTypeName(
                Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME, Pcm2JavaTestUtils.PARAMETER_NAME);

        this.assertParameterCorrespondences(param);
    }

    @Test
    public void testRemoveParameter() throws Throwable {
        final Parameter param = this.createAndSyncRepoOpSigAndParameter();

        this.assertParameterCorrespondences(param);
        final OperationSignature opSig = param.getOperationSignature__Parameter();
        EcoreUtil.remove(param);
        saveAndSynchronizeChanges(opSig);
        
        this.assertCorrectSignatureMappingWithParameters(opSig, 0);
    }
    
    @Test
    public void testAddMultipleParameters() throws Throwable {
        final Parameter param = super.createAndSyncRepoOpSigAndParameterWithDataTypeName(
                Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME + "_1", "compositeParam1");
        final OperationSignature opSig = param.getOperationSignature__Parameter();
        final CompositeDataType cdt = super.createAndSyncCompositeDataType(opSig.getInterface__OperationSignature()
                .getRepository__Interface(), Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME + "_2");

        final Parameter param2 = super.addAndSyncParameterWithPrimitiveTypeToSignature(opSig);
        final Parameter param3 = super.addAndSyncParameterToSignature(opSig, cdt, "compositeParam2");

        this.assertParameterCorrespondences(param);
        this.assertParameterCorrespondences(param2);
        this.assertParameterCorrespondences(param3);
    }
    
    @Test
    public void testAddMultipleParametersAndRemoveOne() throws Throwable {
        final Parameter param = super.createAndSyncRepoOpSigAndParameterWithDataTypeName(
                Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME + "_1", "compositeParam1");
        final OperationSignature opSig = param.getOperationSignature__Parameter();
        final CompositeDataType cdt = super.createAndSyncCompositeDataType(opSig.getInterface__OperationSignature()
                .getRepository__Interface(), Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME + "_2");

        final Parameter param2 = super.addAndSyncParameterWithPrimitiveTypeToSignature(opSig);
        final Parameter param3 = super.addAndSyncParameterToSignature(opSig, cdt, "compositeParam2");
        
        this.assertCorrectSignatureMappingWithParameters(opSig, 3);
        this.assertParameterCorrespondences(param);
        this.assertParameterCorrespondences(param2);
        this.assertParameterCorrespondences(param3);
        
        EcoreUtil.remove(param2);
        saveAndSynchronizeChanges(opSig);
        this.assertCorrectSignatureMappingWithParameters(opSig, 2);
    }

    /**
     * assert that the parameter type has a correspondence to the type of the parameter in the
     * JaMoPP model
     */
    @SuppressWarnings("unchecked")
	private void assertParameterCorrespondences(final Parameter param) throws Throwable {
        this.assertDataTypeCorrespondence(param.getDataType__Parameter());
        this.assertCorrespondnecesAndCompareNames(param, 1,
                new java.lang.Class[] { OrdinaryParameter.class },
                new String[] { param.getParameterName() });
    }
    
    private void assertCorrectSignatureMappingWithParameters(final OperationSignature signature, int expectedParameterCount) throws Throwable {
    	Set<InterfaceMethod> ims = CorrespondenceModelUtil.getCorrespondingEObjectsByType(getCorrespondenceModel(), signature, InterfaceMethod.class);
    	 assertEquals(1, ims.size());
         InterfaceMethod im = ims.iterator().next();
         assertEquals(expectedParameterCount, im.getParameters().size());
         for (Parameter curParam : signature.getParameters__OperationSignature()) {
        	 Set<OrdinaryParameter> javaParams = CorrespondenceModelUtil.getCorrespondingEObjectsByType(getCorrespondenceModel(), curParam, OrdinaryParameter.class);
        	 assertEquals(1, javaParams.size());
        	 OrdinaryParameter javaParam = javaParams.iterator().next();
        	 assertEquals(im.getParameters().get(signature.getParameters__OperationSignature().indexOf(curParam)), javaParam);
         }
    }


}
