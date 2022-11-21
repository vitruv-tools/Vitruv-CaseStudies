package tools.vitruv.applications.pcmjava.javaeditor.java2pcm;

import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.vitruv.applications.pcmjava.javaeditor.util.JavaQueryUtil.claimPackage;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimDataType;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimSingleRepository;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IType;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.InsertEdit;
import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.Disabled;
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
	private static final String FIELD_NAME = "stringField";

	@Test
	void testAddFieldToClassCorrespondingToCompositeDataType() throws Exception {
		createRepositoryPackage();

		String compilationUnitName = Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME
				+ Pcm2JavaTestUtils.IMPLEMENTING_CLASS_SUFFIX;

		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_DATA_TYPE.getSelection());
		changeJavaEditorView(view -> {
			Package dataTypesPackage = claimPackage(view, JavaQueryUtil.DATATYPES_PACKAGE);
			view.getManipulationUtil().createClass(compilationUnitName, dataTypesPackage, null);
		});

		changeJavaEditorView(view -> {
			Package datatypesPackage = JavaQueryUtil.claimPackage(view, JavaQueryUtil.DATATYPES_PACKAGE);
			ICompilationUnit compilationUnit = view.getManipulationUtil().claimCompilationUnit(compilationUnitName,
					datatypesPackage);

			IType iClass = compilationUnit.getAllTypes()[0];
			int offset = CompilationUnitManipulatorHelper.getOffsetForClassifierManipulation(iClass);
			String fieldStr = "private " + FIELD_TYPE + " " + FIELD_NAME + ";";
			InsertEdit insertEdit = new InsertEdit(offset, fieldStr);
			view.getManipulationUtil().editCompilationUnit(compilationUnit, insertEdit);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			CompositeDataType dataType = claimDataType(repository, compilationUnitName, CompositeDataType.class);
			InnerDeclaration innerDeclaration = claimOne(dataType.getInnerDeclaration_CompositeDataType());
			assertEquals(FIELD_NAME, innerDeclaration.getEntityName(), "incorrect name for InnerDeclaration");
			assertEquals(FIELD_TYPE, getTypeNameOfPcmDataType(innerDeclaration.getDatatype_InnerDeclaration()));
		});
	}

	@Test
	void testRenameFieldInClassCorrespondingToCompositeDataType() throws Exception {
		testAddFieldToClassCorrespondingToCompositeDataType();

		String compilationUnitName = Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME
				+ Pcm2JavaTestUtils.IMPLEMENTING_CLASS_SUFFIX;
		String changedFieldName = FIELD_NAME + Pcm2JavaTestUtils.RENAME;

		changeJavaEditorView(view -> {
			Package datatypesPackage = JavaQueryUtil.claimPackage(view, JavaQueryUtil.DATATYPES_PACKAGE);
			ICompilationUnit compilationUnit = view.getManipulationUtil().claimCompilationUnit(compilationUnitName,
					datatypesPackage);

			IType type = compilationUnit.getType(compilationUnitName);
			IField fieldToRename = type.getField(FIELD_NAME);
			String fieldToRenameStr = fieldToRename.getSource();
			String fieldToRenameType = fieldToRenameStr.split(" ")[1];
			String fieldToRenameName = fieldToRenameStr.split(" ")[2];
			int offset = fieldToRename.getSourceRange().getOffset() + fieldToRenameStr.indexOf(fieldToRenameType)
					+ fieldToRenameType.length() + 1;
			int lengthToDelete = fieldToRenameName.length();
			DeleteEdit deleteEdit = new DeleteEdit(offset, lengthToDelete);
			InsertEdit insertEdit = new InsertEdit(offset, changedFieldName + ";");
			view.getManipulationUtil().editCompilationUnit(compilationUnit, deleteEdit, insertEdit);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			CompositeDataType dataType = claimDataType(repository, compilationUnitName, CompositeDataType.class);
			InnerDeclaration innerDeclaration = claimOne(dataType.getInnerDeclaration_CompositeDataType());
			assertEquals(changedFieldName, innerDeclaration.getEntityName(), "incorrect name for InnerDeclaration");
			assertEquals(FIELD_TYPE, getTypeNameOfPcmDataType(innerDeclaration.getDatatype_InnerDeclaration()));
		});
	}

	@Test
	void testChangeTypeOfFieldInClassCorrespondingToCompositeDataType() throws Exception {
		testAddFieldToClassCorrespondingToCompositeDataType();

		String compilationUnitName = Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME
				+ Pcm2JavaTestUtils.IMPLEMENTING_CLASS_SUFFIX;
		String changedFieldTypeName = "int";

		changeJavaEditorView(view -> {
			Package datatypesPackage = JavaQueryUtil.claimPackage(view, JavaQueryUtil.DATATYPES_PACKAGE);
			ICompilationUnit compilationUnit = view.getManipulationUtil().claimCompilationUnit(compilationUnitName,
					datatypesPackage);

			IType type = compilationUnit.getType(compilationUnitName);
			IField fieldToRename = type.getField(FIELD_NAME);
			String fieldSrc = fieldToRename.getSource();
			String fieldType = fieldSrc.split(" ")[1];
			int offset = fieldToRename.getSourceRange().getOffset() + fieldSrc.indexOf(fieldType);
			int lengthToDelete = fieldType.length();
			DeleteEdit deleteEdit = new DeleteEdit(offset, lengthToDelete);
			InsertEdit insertEdit = new InsertEdit(offset, changedFieldTypeName);
			view.getManipulationUtil().editCompilationUnit(compilationUnit, deleteEdit, insertEdit);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			CompositeDataType dataType = claimDataType(repository, compilationUnitName, CompositeDataType.class);
			InnerDeclaration innerDeclaration = claimOne(dataType.getInnerDeclaration_CompositeDataType());
			assertEquals(FIELD_NAME, innerDeclaration.getEntityName(), "incorrect name for InnerDeclaration");
			assertEquals(changedFieldTypeName,
					getTypeNameOfPcmDataType(innerDeclaration.getDatatype_InnerDeclaration()));
		});
	}

	@Disabled("Not yet implemented")
	@Test
	void testRemoveFieldInClassCorrespondingToBasicComponent() {
	}

	@Disabled("Not yet implemented")
	@Test
	void testAddFieldToClassCorrespondingToBasicComponent() {
	}

	@Disabled("Not yet implemented")
	@Test
	void testAddFieldInClassWithoutCorrespondence() {
	}

	@Test
	void testAddFieldWithTypeOfInterface() {
		//TODO:
	}

	@Test
	void testAddFieldWithTypeOfBasicComponent() {
		//TODO:
	}
}
