package tools.vitruv.applications.cbs.commonalities.tests.oo.java;

import static tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.newCompilationUnit;

import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.MembersFactory;
import org.emftext.language.java.modifiers.Modifier;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.emftext.language.java.types.TypesFactory;
import tools.vitruv.applications.cbs.commonalities.tests.oo.ClassPropertyTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase;

public class JavaClassPropertyTestModels extends JavaTestModelsBase implements ClassPropertyTest.DomainModels {

	private static Package newJavaPackage() {
		Package javaPackage = ContainersFactory.eINSTANCE.createPackage();
		javaPackage.setName(PACKAGE_NAME);
		return javaPackage;
	}

	private static Class newJavaClass() {
		Class javaClass = ClassifiersFactory.eINSTANCE.createClass();
		javaClass.setName(CLASS_NAME);
		javaClass.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
		return javaClass;
	}

	private static Field newBasicJavaField() {
		Field javaField = MembersFactory.eINSTANCE.createField();
		javaField.setName(PROPERTY_NAME);
		javaField.setTypeReference(TypesFactory.eINSTANCE.createInt()); // Default type
		return javaField;
	}

	private static Field newJavaField() {
		Field javaField = newBasicJavaField();
		javaField.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPrivate()); // Default visibility
		return javaField;
	}

	private static Field withModifiers(Field javaField, Modifier... modifiers) {
		javaField.getAnnotationsAndModifiers().addAll(List.of(modifiers));
		return javaField;
	}

	private static List<EObject> newJavaClassWithFields(Field... javaFields) {
		Package javaPackage = newJavaPackage();
		Class javaClass = newJavaClass();
		javaClass.getMembers().addAll(List.of(javaFields));
		CompilationUnit javaCompilationUnit = newCompilationUnit(javaPackage, javaClass);
		return List.of(
				javaPackage,
				javaCompilationUnit);
	}

	public JavaClassPropertyTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	/**
	 * Returning <code>null</code> results in no visibility modifier being
	 * added and therefore package-private visibility.
	 */
	protected Modifier defaultFieldVisibility() {
		return null; // package-private
	}

	private Field withDefaultVisibility(Field javaField) {
		Modifier defaultVisibility = defaultFieldVisibility();
		if (defaultVisibility != null) {
			javaField.getAnnotationsAndModifiers().add(defaultVisibility);
		}
		return javaField;
	}

	// Basic

	@Override
	public DomainModel basicPrimitiveClassPropertyCreation() {
		return newModel(() -> newJavaClassWithFields(withDefaultVisibility(newBasicJavaField())));
	}

	// Visibility

	@Override
	public DomainModel privateClassPropertyCreation() {
		return newModel(() -> newJavaClassWithFields(
				withModifiers(newBasicJavaField(), ModifiersFactory.eINSTANCE.createPrivate())));
	}

	@Override
	public DomainModel publicClassPropertyCreation() {
		return newModel(() -> newJavaClassWithFields(
				withModifiers(newBasicJavaField(), ModifiersFactory.eINSTANCE.createPublic())));
	}

	@Override
	public DomainModel protectedClassPropertyCreation() {
		return newModel(() -> newJavaClassWithFields(
				withModifiers(newBasicJavaField(), ModifiersFactory.eINSTANCE.createProtected())));
	}

	@Override
	public DomainModel packagePrivateClassPropertyCreation() {
		// The created field has no modifiers and is therefore package-private.
		return newModel(() -> newJavaClassWithFields(newBasicJavaField()));
	}

	// Modifiers

	@Override
	public DomainModel finalClassPropertyCreation() {
		return newModel(() -> newJavaClassWithFields(
				withModifiers(newJavaField(), ModifiersFactory.eINSTANCE.createFinal())));
	}

	@Override
	public DomainModel staticClassPropertyCreation() {
		return newModel(() -> newJavaClassWithFields(
				withModifiers(newJavaField(), ModifiersFactory.eINSTANCE.createStatic())));
	}

	@Override
	public DomainModel classPropertyWithMultipleModifiersCreation() {
		return newModel(() -> newJavaClassWithFields(
				withModifiers(newJavaField(), ModifiersFactory.eINSTANCE.createStatic(),
						ModifiersFactory.eINSTANCE.createFinal())));
	}

	// Type references

	@Override
	public DomainModel stringClassPropertyCreation() {
		return newModel(() -> {
			Field javaField = newJavaField();
			javaField.setName(STRING_PROPERTY_NAME);
			javaField.setTypeReference(referenceJamoppType(String.class));
			return newJavaClassWithFields(javaField);
		});
	}

	// Multiple properties

	@Override
	public DomainModel multiplePrimitiveClassPropertiesCreation() {
		return newModel(() -> {
			Field booleanField = newJavaField();
			booleanField.setName(BOOLEAN_PROPERTY_NAME);
			booleanField.setTypeReference(TypesFactory.eINSTANCE.createBoolean());
			Field intField = newJavaField();
			intField.setName(INT_PROPERTY_NAME);
			intField.setTypeReference(TypesFactory.eINSTANCE.createInt());
			Field doubleField = newJavaField();
			doubleField.setName(DOUBLE_PROPERTY_NAME);
			doubleField.setTypeReference(TypesFactory.eINSTANCE.createDouble());
			return newJavaClassWithFields(booleanField, intField, doubleField);
		});
	}
}
