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
import org.eclipse.xtend.lib.annotations.Accessors
import org.emftext.language.java.commons.NamedElement
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.Package
import org.junit.jupiter.api.BeforeEach
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import org.palladiosimulator.pcm.system.System
import org.palladiosimulator.pcm.system.SystemFactory
import tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaChangePropagationSpecification
import tools.vitruv.applications.util.temporary.java.JavaSetup
import tools.vitruv.change.propagation.ChangePropagationMode
import tools.vitruv.framework.views.View
import tools.vitruv.testutils.ViewBasedVitruvApplicationTest
import tools.vitruv.testutils.printing.DefaultModelPrinter
import tools.vitruv.testutils.printing.DefaultPrintIdProvider
import tools.vitruv.testutils.printing.DefaultPrintTarget

import static org.junit.jupiter.api.Assertions.*
import static tools.vitruv.applications.pcmjava.tests.pcm2java.JavaQueryUtil.*
import static tools.vitruv.applications.pcmjava.tests.pcm2java.TransformationDirectionConfiguration.configureUnidirectionalExecution

class Pcm2JavaTransformationTest extends ViewBasedVitruvApplicationTest {
	protected var extension Pcm2JavaViewFactory viewFactory
	
	// === setup ===
	
	@Accessors(PROTECTED_GETTER)
	static val PCM_MODEL_FILE_EXTENSION = "pcm"
	@Accessors(PROTECTED_GETTER)
	static val PCM_SYSTEM_FILE_EXTENSION = "system"
	@Accessors(PROTECTED_GETTER)
	static val PCM_MODEL_NAME = "model"
	@Accessors(PROTECTED_GETTER)
	static val JAVA_MODEL_FILE_EXTENSION = "java"
	@Accessors(PROTECTED_GETTER)
	static val JAVA_MODEL_NAME = "code"
	@Accessors(PROTECTED_GETTER)
	static val MODEL_FOLDER_NAME = "model"
	
	@BeforeEach
	def final void setupViewFactory(){
		viewFactory = new Pcm2JavaViewFactory(virtualModel);
	}
	
	@BeforeEach
	def setupTransformatonDirection() {
		configureUnidirectionalExecution(virtualModel)
	}
	
	@BeforeEach
	def setupJavaClasspath() {
		JavaSetup.resetClasspathAndRegisterStandardLibrary();
	}
	
	@BeforeEach
	def disableTransitiveChangePropagation() {
		virtualModel.changePropagationMode = ChangePropagationMode.SINGLE_STEP
	}

	protected def Path getProjectModelPath(String modelName, String modelFileExtension) {
		Path.of(MODEL_FOLDER_NAME).resolve(modelName + "." + modelFileExtension)
	}

	override protected getChangePropagationSpecifications() {
		return #[new Pcm2JavaChangePropagationSpecification()]
	}
	
	// === creators ===
	
	private def void createRepository((Repository)=> void repositoryInitalization){
		changePcmView[
			var repository = RepositoryFactory.eINSTANCE.createRepository();
			it.registerRoot(repository, getProjectModelPath(PCM_MODEL_NAME, PCM_MODEL_FILE_EXTENSION).uri)
			repositoryInitalization.apply(repository)
		]
	}
	
	protected def void createRepository(String repositoryName){
		createRepository[
			entityName = repositoryName;
		]
	}
	
	private def void createSystem((System) => void systemInitialization){
		changePcmView[
			val system = SystemFactory.eINSTANCE.createSystem();
			it.registerRoot(system, getProjectModelPath(PCM_MODEL_NAME, PCM_SYSTEM_FILE_EXTENSION).uri)
			systemInitialization.apply(system)
		]
	}
	
	protected def void createSystem(String systemyName){
		createSystem [
			entityName = systemyName;
		]
	}
	
	// === assertions ===
	
	protected def void assertExistenceOfCompilationUnitsDeeplyEqualTo(View view, Collection<CompilationUnit> expectedCompilationUnits){
		val equalityHelper = new EcoreUtil.EqualityHelper()
		var allActualCompilationUnits = getJavaCompilationUnits(view)
		
		assertEquals(expectedCompilationUnits.size, allActualCompilationUnits.size)
		assertFalse(allActualCompilationUnits.map[name].hasDuplicate)
		assertFalse(expectedCompilationUnits.map[name].hasDuplicate)
		
		expectedCompilationUnits.forEach[
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
		
		expectedPackages.forEach[
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
	
	private def void adaptClassMemberOrderOfExpectedToActualOrder(CompilationUnit expected, CompilationUnit actual) {
		expected.classifiers.forEach[expectedClassifier, classifierIdx|
			val actualClassifier = actual.classifiers.get(classifierIdx)
				
			ECollections.sort(expectedClassifier.members, Comparator.comparing[ item | 
				var maybeIndexInActualOfItem = IntStream.range(0, actualClassifier.members.size())
					.filter([idx |
						val actualMemberAtIdx = actualClassifier.members.get(idx)
						
						var namesEqualOrNotPresent = true
						if(actualMemberAtIdx instanceof NamedElement && item instanceof NamedElement) {
		                	namesEqualOrNotPresent = (actualMemberAtIdx as NamedElement).name.equals((item as NamedElement).name)
		                }
		                
						return actualMemberAtIdx.class == item.class && namesEqualOrNotPresent
					])
					.findFirst
				maybeIndexInActualOfItem.present ? maybeIndexInActualOfItem.asInt : actualClassifier.members.size
			])
		]
	}
	
	
	// === Misc ===
	
	private def <T> boolean hasDuplicate(Iterable<T> all) {
		val Set<T> set = new HashSet<T>();
	    // Set#add returns false if the set does not change, which
	    // indicates that a duplicate element has been added.
	    for (T each: all) if (!set.add(each)) return true;
	    return false;
	}
}
