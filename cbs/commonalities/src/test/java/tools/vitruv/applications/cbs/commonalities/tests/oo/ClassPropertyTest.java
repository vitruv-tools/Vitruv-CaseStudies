package tools.vitruv.applications.cbs.commonalities.tests.oo;

import java.util.List;
import org.eclipse.uml2.uml.VisibilityKind;
import org.emftext.language.java.modifiers.Modifier;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest;
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaClassPropertyTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlClassPropertyTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider;

public class ClassPropertyTest extends CBSCommonalitiesExecutionTest {

	public static List<Object[]> testParameters() {
		return List.of(
				new Object[] {
						new UmlTestModelsProvider<DomainModels>(UmlClassPropertyTestModels::new),
						new JavaTestModelsProvider<DomainModels>(context -> new JavaClassPropertyTestModels(context) {
							// UML creates properties with no visibility by default, which maps to public visibility in
							// Java.
							@Override
							protected Modifier defaultFieldVisibility() {
								return ModifiersFactory.eINSTANCE.createPublic();
							}
						})
				},
				new Object[] {
						new JavaTestModelsProvider<DomainModels>(JavaClassPropertyTestModels::new),
						// Java creates fields with package-private visibility by default.
						new UmlTestModelsProvider<DomainModels>(context -> new UmlClassPropertyTestModels(context) {
							@Override
							protected VisibilityKind defaultPropertyVisibility() {
								return VisibilityKind.PACKAGE_LITERAL;
							}
						})
				});
	}

	/**
	 * If not specified otherwise by the individual test cases, all created
	 * classes are of public visibility and all created properties are of
	 * private visibility and primitive <code>int</code> type.
	 * <p>
	 * Some test cases may use the domain's default property visibility. Since
	 * some domains have different default visibilities, this has to be
	 * considered in the pairwise tests between those domains.
	 */
	public interface DomainModels {

		String PACKAGE_NAME = "root";
		String CLASS_NAME = "Foo";
		String PROPERTY_NAME = "someProperty";
		String BOOLEAN_PROPERTY_NAME = "someBoolean";
		String INT_PROPERTY_NAME = "someInt";
		String DOUBLE_PROPERTY_NAME = "someDouble";
		String STRING_PROPERTY_NAME = "someString";

		// Basic

		/**
		 * A property with only the minimally required attributes and the
		 * domain's default visibility.
		 */
		DomainModel basicPrimitiveClassPropertyCreation();

		// Visibility

		DomainModel privateClassPropertyCreation();
		DomainModel publicClassPropertyCreation();
		DomainModel protectedClassPropertyCreation();
		DomainModel packagePrivateClassPropertyCreation();

		// Modifiers

		DomainModel finalClassPropertyCreation();
		DomainModel staticClassPropertyCreation();

		/**
		 * A property with the following attributes:
		 * <ul>
		 * <li>private visibility
		 * <li>static
		 * <li>final
		 * </ul>
		 */
		DomainModel classPropertyWithMultipleModifiersCreation();

		// Type references

		DomainModel stringClassPropertyCreation();

		// Multiple properties

		/**
		 * One property for each of the following primitive types:
		 * <ul>
		 * <li>boolean
		 * <li>int
		 * <li>double
		 * </ul>
		 * Note: String-typed properties are tested separately.
		 */
		DomainModel multiplePrimitiveClassPropertiesCreation();
	}

	// Basic

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void basicPrimitiveClassPropertyCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).basicPrimitiveClassPropertyCreation().createAndSynchronize();
		getModels(targetModelsProvider).basicPrimitiveClassPropertyCreation().check();
	}

	// Visibility

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void privateClassPropertyCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).privateClassPropertyCreation().createAndSynchronize();
		getModels(targetModelsProvider).privateClassPropertyCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void publicClassPropertyCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).publicClassPropertyCreation().createAndSynchronize();
		getModels(targetModelsProvider).publicClassPropertyCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void protectedClassPropertyCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).protectedClassPropertyCreation().createAndSynchronize();
		getModels(targetModelsProvider).protectedClassPropertyCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void packagePrivateClassPropertyCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).packagePrivateClassPropertyCreation().createAndSynchronize();
		getModels(targetModelsProvider).packagePrivateClassPropertyCreation().check();
	}

	// Modifiers

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void finalClassPropertyCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).finalClassPropertyCreation().createAndSynchronize();
		getModels(targetModelsProvider).finalClassPropertyCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void staticClassPropertyCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).finalClassPropertyCreation().createAndSynchronize();
		getModels(targetModelsProvider).finalClassPropertyCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void classPropertyWithMultipleModifiersCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).classPropertyWithMultipleModifiersCreation().createAndSynchronize();
		getModels(targetModelsProvider).classPropertyWithMultipleModifiersCreation().check();
	}

	// Type references

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void stringClassPropertyCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).stringClassPropertyCreation().createAndSynchronize();
		getModels(targetModelsProvider).stringClassPropertyCreation().check();
	}

	// Multiple properties

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void multipleClassesInDifferentPackagesCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).multiplePrimitiveClassPropertiesCreation().createAndSynchronize();
		getModels(targetModelsProvider).multiplePrimitiveClassPropertiesCreation().check();
	}

	// TODO other data types: Classes, interfaces

	// TODO collection data types
	// TODO enums
	// TODO primitive wrapper types
}
