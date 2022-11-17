package tools.vitruv.applications.pcmjava.tests.pcm2java

import java.nio.file.Path
import java.util.Collection
import java.util.Comparator
import java.util.HashSet
import java.util.Set
import java.util.stream.IntStream
import org.eclipse.emf.common.util.ECollections
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper
import org.emftext.language.java.commons.NamedElement
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.Package
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import org.palladiosimulator.pcm.system.System
import org.palladiosimulator.pcm.system.SystemFactory
import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmChangePropagationSpecification
import tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaChangePropagationSpecification
import tools.vitruv.applications.util.temporary.java.JavaSetup
import tools.vitruv.framework.views.View
import tools.vitruv.testutils.ViewBasedVitruvApplicationTest
import tools.vitruv.testutils.printing.DefaultModelPrinter
import tools.vitruv.testutils.printing.DefaultPrintIdProvider
import tools.vitruv.testutils.printing.DefaultPrintTarget

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.applications.pcmjava.tests.pcm2java.JavaQueryUtil.claimJavaCompilationUnit
import static tools.vitruv.applications.pcmjava.tests.pcm2java.JavaQueryUtil.claimJavaPackage
import static tools.vitruv.applications.pcmjava.tests.pcm2java.JavaQueryUtil.getJavaCompilationUnits
import static tools.vitruv.applications.pcmjava.tests.pcm2java.JavaQueryUtil.getJavaPackages

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne

class Pcm2JavaTransformationTest extends ViewBasedVitruvApplicationTest {
	protected var extension Pcm2JavaViewFactory viewFactory

	// === setup ===
	static val PCM_REPOSITORY_FILE_EXTENSION = "repository"
	static val PCM_SYSTEM_FILE_EXTENSION = "system"
	static val PCM_MODEL_FOLDER_NAME = "pcm"

	override protected enableTransitiveCyclicChangePropagation() {
		false
	}

	@BeforeAll
	def static void setupJavaFactories() {
		JavaSetup.prepareFactories()
	}

	@BeforeEach
	def final void setupViewFactory() {
		viewFactory = new Pcm2JavaViewFactory(virtualModel);
	}

	@BeforeEach
	def setupJavaClasspath() {
		JavaSetup.resetClasspathAndRegisterStandardLibrary();
	}

	protected def Path getProjectModelPath(String modelName, String modelFileExtension) {
		Path.of(PCM_MODEL_FOLDER_NAME).resolve(modelName + "." + modelFileExtension)
	}

	override protected getChangePropagationSpecifications() {
		return #[new Pcm2JavaChangePropagationSpecification(), new Java2PcmChangePropagationSpecification()]
	}

	// === creators ===
	private def void createRepository((Repository)=>void repositoryInitalization) {
		changePcmView[
			var repository = RepositoryFactory.eINSTANCE.createRepository();
			repositoryInitalization.apply(repository)
			registerRoot(repository, getProjectModelPath(repository.entityName, PCM_REPOSITORY_FILE_EXTENSION).uri)
		]
	}

	protected def void createRepository(String repositoryName) {
		createRepository[
			entityName = repositoryName;
		]
	}

	private def void createSystem((System)=>void systemInitialization) {
		changePcmView[
			val system = SystemFactory.eINSTANCE.createSystem();
			systemInitialization.apply(system)
			registerRoot(system, getProjectModelPath(system.entityName, PCM_SYSTEM_FILE_EXTENSION).uri)
		]
	}

	protected def void createSystem(String systemyName) {
		createSystem [
			entityName = systemyName;
		]
	}

	// === assertions ===
	protected def void assertExistenceOfCompilationUnitsDeeplyEqualTo(View view,
		Collection<CompilationUnit> expectedCompilationUnits) {
		val equalityHelper = new EcoreUtil.EqualityHelper()
		var allActualCompilationUnits = getJavaCompilationUnits(view)

		assertEquals(expectedCompilationUnits.size, allActualCompilationUnits.size)
		assertFalse(allActualCompilationUnits.map[name].hasDuplicate)
		assertFalse(expectedCompilationUnits.map[name].hasDuplicate)

		ignoreComparisonOfJavaUtilClassifieres(equalityHelper, allActualCompilationUnits, expectedCompilationUnits)
		expectedCompilationUnits.forEach [
			val actualUnit = claimJavaCompilationUnit(view, it.name)
			adaptClassMemberOrderOfExpectedToActualOrder(it, actualUnit)
			assertTrue(equalityHelper.equals(actualUnit, it), modelComparisonErrorMessage(it, actualUnit))
		]
	}

	protected def void assertExistenceOfPackagesDeeplyEqualTo(View view, Collection<Package> expectedPackages) {
		val equalityHelper = new EcoreUtil.EqualityHelper()
		val allActualPackages = getJavaPackages(view)

		assertEquals(expectedPackages.size, allActualPackages.size)
		assertFalse(allActualPackages.map[name].hasDuplicate)
		assertFalse(expectedPackages.map[name].hasDuplicate)

		expectedPackages.forEach [
			var actualPackage = claimJavaPackage(view, it.name)
			assertTrue(equalityHelper.equals(actualPackage, it), modelComparisonErrorMessage(it, actualPackage))
		]
	}

	private def String modelComparisonErrorMessage(EObject expected, EObject actual) {
		var printTarget = new DefaultPrintTarget()
		var printIdProvider = new DefaultPrintIdProvider()
		var modelPrinter = new DefaultModelPrinter()

		printTarget.print("Expected actual model to equal expected model.")
		printTarget.newLine
		printTarget.print("Actual:")
		printTarget.newLine
		modelPrinter.printObject(printTarget, printIdProvider, actual)

		printTarget.newLine
		printTarget.print("Expected:")
		printTarget.newLine
		modelPrinter.printObject(printTarget, printIdProvider, expected)

		return printTarget.toString
	}

	private def void ignoreComparisonOfJavaUtilClassifieres(EqualityHelper equalityHelper,
		Collection<CompilationUnit> actualCompilationUnits, Collection<CompilationUnit> expectedCompilationUnits) {
		val expectedJavaUtilCompilationUnits = expectedCompilationUnits.filter [ cu |
			cu.namespaces.equals(#["java", "util"])
		]
		val actualJavaUtilCompilationUnits = actualCompilationUnits.filter[cu|cu.namespaces.equals(#["java", "util"])]

		expectedJavaUtilCompilationUnits.forEach [ expectedCompilationUnit |
			val actualCompilationUnit = actualJavaUtilCompilationUnits.filter [ acu |
				acu.name.equals(expectedCompilationUnit.name)
			].claimOne

			val expectedClassifier = expectedCompilationUnit.classifiers.claimOne
			val actualClassifier = actualCompilationUnit.classifiers.claimOne
			equalityHelper.put(expectedClassifier, actualClassifier)
			equalityHelper.put(actualClassifier, expectedClassifier)
		]
	}

	private def void adaptClassMemberOrderOfExpectedToActualOrder(CompilationUnit expected, CompilationUnit actual) {
		expected.classifiers.forEach [ expectedClassifier, classifierIdx |
			val actualClassifier = actual.classifiers.get(classifierIdx)

			ECollections.sort(expectedClassifier.members, Comparator.comparing [ item |
				var maybeIndexInActualOfItem = IntStream.range(0, actualClassifier.members.size()).filter([ idx |
					val actualMemberAtIdx = actualClassifier.members.get(idx)

					var namesEqualOrNotPresent = true
					if (actualMemberAtIdx instanceof NamedElement && item instanceof NamedElement) {
						namesEqualOrNotPresent = (actualMemberAtIdx as NamedElement).name.equals(
							(item as NamedElement).name)
					}

					return actualMemberAtIdx.class == item.class && namesEqualOrNotPresent
				]).findFirst
				maybeIndexInActualOfItem.present ? maybeIndexInActualOfItem.asInt : actualClassifier.members.size
			])
		]
	}

	// === Misc ===
	private def <T> boolean hasDuplicate(Iterable<T> all) {
		val Set<T> set = new HashSet<T>();
		// Set#add returns false if the set does not change, which
		// indicates that a duplicate element has been added.
		for (T each : all)
			if(!set.add(each)) return true;
		return false;
	}
}
