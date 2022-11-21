package tools.vitruv.applications.pcmjava.javaeditor.java2pcm;

import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.vitruv.applications.pcmjava.javaeditor.util.JavaQueryUtil.claimPackage;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimDataType;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimSingleRepository;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.text.edits.InsertEdit;
import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmUserSelection;
import tools.vitruv.applications.pcmjava.javaeditor.java2pcm.legacy.CompilationUnitManipulatorHelper;
import tools.vitruv.applications.pcmjava.javaeditor.util.JavaQueryUtil;
import tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils;

class FieldMappingTransformationTest extends Java2PcmTransformationTest {
	private static final String FIELD_TYPE = "String";
	private static final String FIELD_IDENTIFIER = "stringField";
	
	private static final String DATATYPES_PACKAGE = "datatypes";
	
	@Test
	void testAddFieldToClassCorrespondingToCompositeComponent() throws Exception {
		createRepositoryPackage();
		
		String compilationUnitName = Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME + Pcm2JavaTestUtils.IMPLEMENTING_CLASS_SUFFIX;
		
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_DATA_TYPE.getSelection());
		changeJavaEditorView(view -> {
			Package dataTypesPackage = claimPackage(view, DATATYPES_PACKAGE);
			view.getManipulationUtil().createClass(compilationUnitName, dataTypesPackage, null);
		});
		
		changeJavaEditorView(view -> {
			Package datatypesPackage = JavaQueryUtil.claimPackage(view, DATATYPES_PACKAGE);
			ICompilationUnit compilationUnit = view.getManipulationUtil().claimCompilationUnit(compilationUnitName, datatypesPackage);
			
			final IType iClass = compilationUnit.getAllTypes()[0];
			final int offset = CompilationUnitManipulatorHelper.getOffsetForClassifierManipulation(iClass);
			final String fieldStr = "private " + FIELD_TYPE + " " + FIELD_IDENTIFIER + ";";
			final InsertEdit insertEdit = new InsertEdit(offset, fieldStr);
			view.getManipulationUtil().editCompilationUnit(compilationUnit, insertEdit);
		});
		
		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			CompositeDataType dataType = claimDataType(repository, compilationUnitName, CompositeDataType.class);
			InnerDeclaration innerDeclaration = claimOne(dataType.getInnerDeclaration_CompositeDataType());
			assertEquals(FIELD_IDENTIFIER, innerDeclaration.getEntityName(), "incorrect name for InnerDeclaration");
			assertEquals(FIELD_TYPE, getTypeNameOfPcmDataType(innerDeclaration.getDatatype_InnerDeclaration()));
		});
	}
}
