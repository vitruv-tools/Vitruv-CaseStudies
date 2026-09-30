package tools.vitruv.applications.cbs.commonalities.tests.oo;

import java.util.List;
import org.eclipse.uml2.uml.VisibilityKind;
import org.emftext.language.java.modifiers.Modifier;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest;
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaClassTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlClassTestModels;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider;

public class ClassTest extends CBSCommonalitiesExecutionTest {

	public static List<Object[]> testParameters() {
		return List.of(
				new Object[] {
						new UmlTestModelsProvider<DomainModels>(UmlClassTestModels::new),
						new JavaTestModelsProvider<DomainModels>(context -> new JavaClassTestModels(context) {
							// UML creates classes with no visibility by default, which maps to public visibility in Java.
							@Override
							protected Modifier defaultClassVisibility() {
								return ModifiersFactory.eINSTANCE.createPublic();
							}
						})
				},
				new Object[] {
						new JavaTestModelsProvider<DomainModels>(JavaClassTestModels::new),
						// Java creates classes with package-private visibility by default.
						new UmlTestModelsProvider<DomainModels>(context -> new UmlClassTestModels(context) {
							@Override
							protected VisibilityKind defaultClassVisibility() {
								return VisibilityKind.PACKAGE_LITERAL;
							}
						})
				});
	}

	/**
	 * If not specified otherwise by the individual test cases, all created
	 * classes are of public visibility.
	 * <p>
	 * Some test cases may create classes with the domain's default visibility.
	 * Since some domains have different default class visibilities, this has
	 * to be taken into account when testing these pairs of domains.
	 */
	public interface DomainModels {

		String PACKAGE_1_NAME = "root";
		String PACKAGE_2_NAME = "root2";
		String CLASS_1_NAME = "Foo";
		String CLASS_2_NAME = "Bar";
		String INTERFACE_1_NAME = "IFoo";
		String INTERFACE_2_NAME = "IBar";

		// Empty class

		/**
		 * A class with only the minimally required attributes and the domain's
		 * default visibility.
		 */
		DomainModel emptyClassCreation();

		// Visibility

		DomainModel privateClassCreation();
		DomainModel publicClassCreation();
		DomainModel protectedClassCreation();
		DomainModel packagePrivateClassCreation();

		// Modifiers

		DomainModel finalClassCreation();
		DomainModel abstractClassCreation();

		/**
		 * A class with the following attributes:
		 * <ul>
		 * <li>public visibility
		 * <li>final
		 * <li>abstract
		 * </ul>
		 * This combination does not make much sense, but that is not important
		 * for this test.
		 */
		DomainModel classWithMultipleModifiersCreation();

		// Multiple classes

		DomainModel multipleClassesInSamePackageCreation();
		DomainModel multipleClassesInDifferentPackagesCreation();

		// Super class

		/**
		 * Class 1 extending class 2 from the same package.
		 */
		DomainModel classWithSuperClassCreation();

		// Implemented interfaces

		/**
		 * Class 1 implementing interface 1 from the same package.
		 */
		DomainModel classImplementingInterfaceCreation();

		/**
		 * Class 1 implementing interface 1 and 2 from the same package.
		 */
		DomainModel classImplementingMultipleInterfacesCreation();
	}

	// Empty class

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void emptyClassCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).emptyClassCreation().createAndSynchronize();
		getModels(targetModelsProvider).emptyClassCreation().check();
	}

	// Visibility

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void privateClassCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).privateClassCreation().createAndSynchronize();
		getModels(targetModelsProvider).privateClassCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void publicClassCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).publicClassCreation().createAndSynchronize();
		getModels(targetModelsProvider).publicClassCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void protectedClassCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).protectedClassCreation().createAndSynchronize();
		getModels(targetModelsProvider).protectedClassCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void packagePrivateClassCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).packagePrivateClassCreation().createAndSynchronize();
		getModels(targetModelsProvider).packagePrivateClassCreation().check();
	}

	// Modifiers

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void finalClassCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).finalClassCreation().createAndSynchronize();
		getModels(targetModelsProvider).finalClassCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void abstractClassCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).abstractClassCreation().createAndSynchronize();
		getModels(targetModelsProvider).abstractClassCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void classWithMultipleModifiersCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).classWithMultipleModifiersCreation().createAndSynchronize();
		getModels(targetModelsProvider).classWithMultipleModifiersCreation().check();
	}

	// Multiple classes

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void multipleClassesInSamePackageCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).multipleClassesInSamePackageCreation().createAndSynchronize();
		getModels(targetModelsProvider).multipleClassesInSamePackageCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void multipleClassesInDifferentPackagesCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).multipleClassesInDifferentPackagesCreation().createAndSynchronize();
		getModels(targetModelsProvider).multipleClassesInDifferentPackagesCreation().check();
	}

	// Super class

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void classWithSuperClassCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).classWithSuperClassCreation().createAndSynchronize();
		getModels(targetModelsProvider).classWithSuperClassCreation().check();
	}

	// Implemented interfaces

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void classImplementingInterfaceCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).classImplementingInterfaceCreation().createAndSynchronize();
		getModels(targetModelsProvider).classImplementingInterfaceCreation().check();
	}

	@ParameterizedTest(name = "{0} to {1}")
	@MethodSource("testParameters")
	public void classImplementingMultipleInterfacesCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
			DomainModelsProvider<DomainModels> targetModelsProvider) {
		getModels(sourceModelsProvider).classImplementingMultipleInterfacesCreation().createAndSynchronize();
		getModels(targetModelsProvider).classImplementingMultipleInterfacesCreation().check();
	}
}
