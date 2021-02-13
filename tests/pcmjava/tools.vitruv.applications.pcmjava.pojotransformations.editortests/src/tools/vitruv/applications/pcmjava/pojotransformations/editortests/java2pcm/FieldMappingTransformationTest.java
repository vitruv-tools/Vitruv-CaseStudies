package tools.vitruv.applications.pcmjava.pojotransformations.editortests.java2pcm;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IType;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.InsertEdit;
import org.emftext.language.java.members.Field;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

import tools.vitruv.applications.pcmjava.tests.util.java2pcm.CompilationUnitManipulatorHelper;
import tools.vitruv.applications.pcmjava.tests.util.pcm2java.Pcm2JavaTestUtils;
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil;
import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FieldMappingTransformationTest extends Java2PcmPackageMappingTransformationTest {

	@Test
	public void testAddFieldToClassThatCorrespondsToCompositeDatatype() throws Throwable {
		super.addRepoContractsAndDatatypesPackage();
		final CompositeDataType cdt = super.addClassThatCorrespondsToCompositeDatatype();
		final String fieldType = "String";
		final String fieldName = "stringField";

		final InnerDeclaration innerDeclaration = this.addFieldToClassWithName(cdt.getEntityName(), fieldType,
				fieldName, InnerDeclaration.class);

		this.assertInnerDeclaration(innerDeclaration, fieldType, fieldName);
	}

	@Test
	public void testRenameFieldInClassThatCorrespondsToCompositeDatatype() throws Throwable {
		final String fieldTypeName = "String";
		final String fieldName = "stringField";
		super.addRepoContractsAndDatatypesPackage();
		final CompositeDataType cdt = super.addClassThatCorrespondsToCompositeDatatype();
		this.addFieldToClassWithName(cdt.getEntityName(), fieldTypeName, fieldName, InnerDeclaration.class);

		final String newFieldName = fieldName + Pcm2JavaTestUtils.RENAME;
		final InnerDeclaration newInnerDeclaration = this.renameFieldInClass(cdt.getEntityName(), fieldName,
				newFieldName);

		this.assertInnerDeclaration(newInnerDeclaration, fieldTypeName, newFieldName);
	}

	@Test
	public void testChangeTypeOfFieldInClassThatCorrespondsToCompositeDatatype() throws Throwable {
		final String fieldTypeName = "String";
		final String fieldName = "stringField";
		super.addRepoContractsAndDatatypesPackage();
		final CompositeDataType cdt = super.addClassThatCorrespondsToCompositeDatatype();
		this.addFieldToClassWithName(cdt.getEntityName(), fieldTypeName, fieldName, InnerDeclaration.class);

		final String newFieldTypeName = "int";
		final InnerDeclaration newInnerDeclaration = this.changeFieldTypeInClass(cdt.getEntityName(), fieldName,
				newFieldTypeName);

		this.assertInnerDeclaration(newInnerDeclaration, newFieldTypeName, fieldName);
	}

	@Disabled
	@Test
	public void testRemoveFieldInClassThatCorrespondsToBasicComponent() {
		fail("Not yet implemented");
	}

	@Disabled
	@Test
	public void testAddFieldToClassThatCorrespondsToBasicComponent() {
		fail("Not yet implemented");
	}

	@Disabled
	@Test
	public void testAddFieldInClassWithoutCorrespondence() {
		fail("Not yet implemented");
	}

	@Disabled
	@Test
	public void testAddFieldWithTypeOfInterface() throws Throwable {
		this.createRepoBasicComponentAndInterface();

		// create required role from Pcm2JavaTestUtils.BASIC_COMPONENT_NAME +
		// "Requiring" to
		// Interface
		final OperationRequiredRole orrToInterface = this.addFieldToClassWithName(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + "Requiring" + "Impl", Pcm2JavaTestUtils.INTERFACE_NAME,
				"i" + Pcm2JavaTestUtils.INTERFACE_NAME, OperationRequiredRole.class);

		this.assertOperationRequiredRole(orrToInterface);
	}

	@Test
	public void testAddFieldWithTypeOfBasicComponentToClass() throws Throwable {
		this.createRepoBasicComponentAndInterface();

		// create required role from Pcm2JavaTestUtils.BASIC_COMPONENT_NAME +
		// "Requiring" to
		// Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + "Providing"
		final OperationRequiredRole orrToInterface = this.addFieldToClassWithName(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + "Requiring" + "Impl",
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + "Providing" + "Impl",
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME.toLowerCase() + "Providing", OperationRequiredRole.class);

		this.assertOperationRequiredRole(orrToInterface);
	}

	private void createRepoBasicComponentAndInterface() throws CoreException, IOException, InterruptedException {
		// create main package
		super.addRepoContractsAndDatatypesPackage();
		// create package and classes
		this.addPackageAndImplementingClass(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + "Providing");
		this.addPackageAndImplementingClass(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + "Requiring");
		// create interface
		super.createInterfaceInPackageBasedOnJaMoPPPackageWithCorrespondence("contracts",
				Pcm2JavaTestUtils.INTERFACE_NAME);
		// create provided role from providing compontent to interface
		super.addImplementsCorrespondingToOperationProvidedRoleToClass(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + "Providing" + "Impl", Pcm2JavaTestUtils.INTERFACE_NAME);
	}

	private void assertOperationRequiredRole(final OperationRequiredRole operationRequiredRole) throws Throwable {
		Set<EObject> correspondingEObjects = CorrespondenceModelUtil.getCorrespondingEObjects(
				FieldMappingTransformationTest.this.getCorrespondenceModel(), operationRequiredRole);

		boolean fieldFound = false;
		for (final EObject correspondingEObject : correspondingEObjects) {
			if (correspondingEObject instanceof Field) {
				fieldFound = true;
			} else {
				fail("OperationRequiredRole should correspond to field only, but corresonds also to: "
						+ correspondingEObject);
			}
		}
		assertTrue(fieldFound, "OperationRequiredRole does not correspond to a field");
	}

	private InnerDeclaration renameFieldInClass(final String className, final String fieldName,
			final String newFieldName) throws Throwable {
		final ICompilationUnit icu = CompilationUnitManipulatorHelper.findICompilationUnitWithClassName(className,
				this.getCurrentTestProject());
		final IType type = icu.getType(className);
		final IField fieldToRename = type.getField(fieldName);
		final String fieldToRenameStr = fieldToRename.getSource();
		final String fieldToRenameType = fieldToRenameStr.split(" ")[1];
		final String fieldToRenameName = fieldToRenameStr.split(" ")[2];
		final int offset = fieldToRename.getSourceRange().getOffset() + fieldToRenameStr.indexOf(fieldToRenameType)
				+ fieldToRenameType.length() + 1;
		final int lengthToDelete = fieldToRenameName.length();
		final DeleteEdit deleteEdit = new DeleteEdit(offset, lengthToDelete);
		final InsertEdit insertEdit = new InsertEdit(offset, newFieldName + ";");
		editCompilationUnit(icu, deleteEdit, insertEdit);
		final Field newJaMoPPField = this.getJaMoPPFieldFromClass(icu, newFieldName);
		return claimOne(CorrespondenceModelUtil.getCorrespondingEObjectsByType(this.getCorrespondenceModel(),
				newJaMoPPField, InnerDeclaration.class));
	}

	private InnerDeclaration changeFieldTypeInClass(final String className, final String fieldName,
			final String newFieldTypeName) throws Throwable {
		final ICompilationUnit icu = CompilationUnitManipulatorHelper.findICompilationUnitWithClassName(className,
				this.getCurrentTestProject());
		final IType type = icu.getType(className);
		final IField fieldToRename = type.getField(fieldName);
		final String fieldSrc = fieldToRename.getSource();
		final String fieldType = fieldSrc.split(" ")[1];
		final int offset = fieldToRename.getSourceRange().getOffset() + fieldSrc.indexOf(fieldType);
		final int lengthToDelete = fieldType.length();
		final DeleteEdit deleteEdit = new DeleteEdit(offset, lengthToDelete);
		final InsertEdit insertEdit = new InsertEdit(offset, newFieldTypeName);
		editCompilationUnit(icu, deleteEdit, insertEdit);
		final Field newJaMoPPField = this.getJaMoPPFieldFromClass(icu, fieldName);
		return claimOne(CorrespondenceModelUtil.getCorrespondingEObjectsByType(this.getCorrespondenceModel(),
				newJaMoPPField, InnerDeclaration.class));
	}

	private void assertInnerDeclaration(final InnerDeclaration innerDeclaration, final String fieldType,
			final String fieldName) throws Throwable {
		super.assertPCMNamedElement(innerDeclaration, fieldName);
		final String pcmDataTypeName = super.getNameFromPCMDataType(innerDeclaration.getDatatype_InnerDeclaration());
		assertEquals(pcmDataTypeName, fieldType, "The name of the PCM datatype does not equal the JaMoPP type name");
	}

}
