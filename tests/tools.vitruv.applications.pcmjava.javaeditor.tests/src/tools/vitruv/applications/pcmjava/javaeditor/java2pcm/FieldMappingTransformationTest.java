package tools.vitruv.applications.pcmjava.javaeditor.java2pcm;

import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.vitruv.applications.pcmjava.javaeditor.util.JavaQueryUtil.claimPackage;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimComponent;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimDataType;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimInterface;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimNamedElement;
import static tools.vitruv.applications.pcmjava.javaeditor.util.PcmQueryUtil.claimSingleRepository;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.BASIC_COMPONENT_NAME;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.IMPLEMENTING_CLASS_SUFFIX;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.INTERFACE_NAME;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.RENAME;
import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.REPOSITORY_NAME;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.InsertEdit;
import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmUserSelection;
import tools.vitruv.applications.pcmjava.javaeditor.java2pcm.legacy.CompilationUnitManipulatorHelper;
import tools.vitruv.applications.pcmjava.javaeditor.util.JavaQueryUtil;

class FieldMappingTransformationTest extends Java2PcmTransformationTest {
	private static final String FIELD_TYPE = "String";
	private static final String FIELD_NAME = "stringField";

	private static final String PROVIDING_COMPONENT_NAME = BASIC_COMPONENT_NAME + "Providing";
	private static final String REQUIRING_COMPONENT_NAME = BASIC_COMPONENT_NAME + "Requiring";

	@Test
	void testAddFieldToClassCorrespondingToCompositeDataType() throws Exception {
		createRepositoryPackage();

		String compilationUnitName = COMPOSITE_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX;

		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_DATA_TYPE.getSelection());
		changeJavaEditorView(view -> {
			Package dataTypesPackage = claimPackage(view, JavaQueryUtil.DATATYPES_PACKAGE);
			view.getManipulationUtil().createClass(compilationUnitName, dataTypesPackage, null);
		});

		changeJavaEditorView(view -> {
			Package datatypesPackage = JavaQueryUtil.claimPackage(view, JavaQueryUtil.DATATYPES_PACKAGE);
			ICompilationUnit compilationUnit = view.getManipulationUtil().claimCompilationUnit(compilationUnitName,
					datatypesPackage);

			InsertEdit insertEdit = editForAddingField(FIELD_NAME, FIELD_TYPE, compilationUnit, compilationUnitName);
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

		String compilationUnitName = COMPOSITE_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX;
		String changedFieldName = FIELD_NAME + RENAME;

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

		String compilationUnitName = COMPOSITE_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX;
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
					getTypeNameOfPcmDataType(innerDeclaration.getDatatype_InnerDeclaration()),
					"incorrect type for InnerDeclaration");
		});
	}

	@Disabled("Not yet implemented")
	@Test
	void testRemoveFieldInClassCorrespondingToBasicComponent() throws Exception {
	}

	@Disabled("Not yet implemented")
	@Test
	void testAddFieldToClassCorrespondingToBasicComponent() throws Exception {
	}

	@Disabled("Not yet implemented")
	@Test
	void testAddFieldInClassWithoutCorrespondence() throws Exception {
	}

	@Test
	void testAddFieldWithTypeOfInterface() throws Exception {
		setupExampleWithProvidingAndRequiringComponent();

		String fieldType = INTERFACE_NAME;
		String fieldName = "i" + INTERFACE_NAME;

		changeJavaEditorView(view -> {
			Package providingPackage = JavaQueryUtil.claimPackage(view, PROVIDING_COMPONENT_NAME);
			ICompilationUnit providingCompilationUnit = view.getManipulationUtil()
					.claimCompilationUnit(PROVIDING_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX, providingPackage);

			Package contractsPackage = JavaQueryUtil.claimPackage(view, JavaQueryUtil.CONTRACTS_PACKAGE);
			ICompilationUnit anInterface = view.getManipulationUtil().claimCompilationUnit(INTERFACE_NAME,
					contractsPackage);
			view.getManipulationUtil().addImportToCompilationUnit(providingCompilationUnit, anInterface,
					INTERFACE_NAME);

			InsertEdit insertEdit = editForAddingField(fieldName, fieldType, providingCompilationUnit,
					PROVIDING_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX);
			view.getManipulationUtil().editCompilationUnit(providingCompilationUnit, insertEdit);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			BasicComponent providingComponent = claimComponent(repository, PROVIDING_COMPONENT_NAME,
					BasicComponent.class);
			OperationInterface anInterface = claimInterface(repository, INTERFACE_NAME, OperationInterface.class);
			OperationRequiredRole requiredRole = claimNamedElement(
					providingComponent.getRequiredRoles_InterfaceRequiringEntity(), fieldName,
					OperationRequiredRole.class);
			assertEquals(fieldName, requiredRole.getEntityName(), "incorrect name for RequiredRole");
			assertEquals(anInterface, requiredRole.getRequiredInterface__OperationRequiredRole(),
					"incorrect interface for RequiredRole");
		});
	}

	@Test
	void testAddFieldWithTypeOfBasicComponent() throws Exception {
		setupExampleWithProvidingAndRequiringComponent();

		String fieldName = PROVIDING_COMPONENT_NAME.substring(0, 1).toLowerCase()
				+ PROVIDING_COMPONENT_NAME.substring(1);
		String fieldType = PROVIDING_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX;

		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
		changeJavaEditorView(view -> {
			Package requiringPackage = JavaQueryUtil.claimPackage(view, REQUIRING_COMPONENT_NAME);
			ICompilationUnit requiringCompilationUnit = view.getManipulationUtil()
					.claimCompilationUnit(REQUIRING_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX, requiringPackage);

			Package providingPackage = JavaQueryUtil.claimPackage(view, PROVIDING_COMPONENT_NAME);
			ICompilationUnit providingCompilationUnit = view.getManipulationUtil()
					.claimCompilationUnit(PROVIDING_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX, providingPackage);
			view.getManipulationUtil().addImportToCompilationUnit(requiringCompilationUnit, providingCompilationUnit,
					PROVIDING_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX);

			InsertEdit insertEdit = editForAddingField(fieldName, fieldType, requiringCompilationUnit,
					REQUIRING_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX);
			view.getManipulationUtil().editCompilationUnit(requiringCompilationUnit, insertEdit);
		});

		validatePcmView(view -> {
			Repository repository = claimSingleRepository(view);
			BasicComponent requiringComponent = claimComponent(repository, REQUIRING_COMPONENT_NAME,
					BasicComponent.class);
			OperationInterface anInterface = claimInterface(repository, INTERFACE_NAME, OperationInterface.class);
			OperationRequiredRole requiredRole = claimNamedElement(
					requiringComponent.getRequiredRoles_InterfaceRequiringEntity(), fieldName,
					OperationRequiredRole.class);
			assertEquals(fieldName, requiredRole.getEntityName(), "incorrect name for RequiredRole");
			assertEquals(anInterface, requiredRole.getRequiredInterface__OperationRequiredRole(),
					"incorrect interface for RequiredRole");
		});
	}

	/**
	 * Sets up an example scenario with the root Repository and two packages named
	 * {@link REQUIRING_COMPONENT_NAME} and {@link PROVIDING_COMPONENT_NAME} with
	 * each one class with suffix {IMPLEMENTING_CLASS_SUFFIX} as basic components.
	 * Sets up an interface named {@link INTERFACE_NAME} in the contracts package.
	 * Sets up an {@code implements} relation between the providing component class
	 * and the interface.
	 */
	private void setupExampleWithProvidingAndRequiringComponent() throws Exception {
		createRepositoryPackage();

		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
		changeJavaView(view -> {
			createPackageWithPackageInfo(view, REPOSITORY_NAME, REQUIRING_COMPONENT_NAME);
			createPackageWithPackageInfo(view, REPOSITORY_NAME, PROVIDING_COMPONENT_NAME);
		});

		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
		changeJavaEditorView(view -> {
			Package requiringPackage = JavaQueryUtil.claimPackage(view, REQUIRING_COMPONENT_NAME);
			view.getManipulationUtil().createClass(REQUIRING_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX,
					requiringPackage, null);

			Package providingPackage = JavaQueryUtil.claimPackage(view, PROVIDING_COMPONENT_NAME);
			view.getManipulationUtil().createClass(PROVIDING_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX,
					providingPackage, null);

			Package contractsPackage = JavaQueryUtil.claimPackage(view, JavaQueryUtil.CONTRACTS_PACKAGE);
			view.getManipulationUtil().createInterface(INTERFACE_NAME, contractsPackage, null);

			{
				ICompilationUnit providingCompilationUnit = view.getManipulationUtil()
						.claimCompilationUnit(PROVIDING_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX, providingPackage);
				ICompilationUnit anInterface = view.getManipulationUtil().claimCompilationUnit(INTERFACE_NAME,
						contractsPackage);
				view.getManipulationUtil().addImportToCompilationUnit(providingCompilationUnit, anInterface,
						INTERFACE_NAME);

				IType classType = providingCompilationUnit
						.getType(PROVIDING_COMPONENT_NAME + IMPLEMENTING_CLASS_SUFFIX);
				String newSource = " implements " + INTERFACE_NAME;
				int offset = classType.getSourceRange().getOffset();
				int firstBracket = classType.getSource().indexOf("{");
				offset = offset + firstBracket - 1;
				final InsertEdit insertEdit = new InsertEdit(offset, newSource);
				view.getManipulationUtil().editCompilationUnit(providingCompilationUnit, insertEdit);
			}
		});
	}

	/**
	 * Creates an edit action that inserts a private field into the class
	 * 
	 * @param fieldName       is the field name
	 * @param fieldType       is the field type
	 * @param compilationUnit is the compilation unit to edit
	 * @param typeName        is the type within the compilation unit to edit
	 * @return an edit action that can be applied to the compilation unit to add a
	 *         field
	 */
	private InsertEdit editForAddingField(String fieldName, String fieldType, ICompilationUnit compilationUnit,
			String typeName) throws JavaModelException {
		IType iClass = compilationUnit.getType(typeName);
		int offset = CompilationUnitManipulatorHelper.getOffsetForClassifierManipulation(iClass);
		String fieldStr = "private " + fieldType + " " + fieldName + ";";
		return new InsertEdit(offset, fieldStr);
	}
}
