package tools.vitruv.applications.pcmjava.tests.pcm2java

import java.nio.file.Path
import java.util.Collection
import java.util.Comparator
import java.util.HashSet
import java.util.Set
import org.eclipse.emf.common.util.ECollections
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtend.lib.annotations.Accessors
import org.emftext.language.java.commons.NamedElement
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.Package
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.Method
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

import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.*
import static tools.vitruv.applications.pcmjava.tests.pcm2java.JavaQueryUtil.*
import static tools.vitruv.applications.pcmjava.tests.pcm2java.TransformationDirectionConfiguration.configureUnidirectionalExecution
import static tools.vitruv.testutils.matchers.ModelMatchers.*
import org.emftext.language.java.members.Member

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

	protected def void createAndRegisterRoot(View view, EObject rootObject, URI persistenceUri) {
		view.registerRoot(rootObject, persistenceUri)
	}
	
	protected def void deleteAndUnregisterRoot(View view, EObject rootObject) {
		EcoreUtil.delete(rootObject)
	}
	
	// === creators ===
	
	private def void createRepository((Repository)=> void repositoryInitalization){
		changePcmView[
			var repository = RepositoryFactory.eINSTANCE.createRepository();
			createAndRegisterRoot(repository, getProjectModelPath(PCM_MODEL_NAME, PCM_MODEL_FILE_EXTENSION).uri)
			repositoryInitalization.apply(repository)
		]
	}
	
	protected def void createRepostory(String repositoryName){
		createRepository[
			entityName = repositoryName;
		]
	}
	
	private def void createSystem((System) => void systemInitialization){
		changePcmView[
			val system = SystemFactory.eINSTANCE.createSystem();
			createAndRegisterRoot(system, getProjectModelPath(PCM_MODEL_NAME, PCM_SYSTEM_FILE_EXTENSION).uri)
			systemInitialization.apply(system)
		]
	}
	
	protected def void createSystem(String systemyName){
		createSystem [
			entityName = systemyName;
		]
	}
	
	// === assertions ===
	
	protected def void assertCompilationUnits(View view, Collection<CompilationUnit> expectedCompilationUnits){
		var allActualCompilationUnits = getJavaCompilationUnits(view)
		assertEquals(expectedCompilationUnits.size, allActualCompilationUnits.size)
		assertFalse(allActualCompilationUnits.map[name].hasDuplicate)
		assertFalse(expectedCompilationUnits.map[name].hasDuplicate)
		
		// we sort members on our own as the reactions don't create a traceable order in all cases
		allActualCompilationUnits.forEach[it.classifiers.forEach[sortMembers(it.members)]]
		expectedCompilationUnits.forEach[it.classifiers.forEach[sortMembers(it.members)]]
		
		expectedCompilationUnits.forEach[
			var actualUnit = claimJavaCompilationUnit(view, it.name)
			assertThat(actualUnit, equalsDeeply(it))
		]
	}
	
	protected def void assertPackages(View view, Collection<Package> expectedPackages) {
		val allActualPackages = getJavaPackages(view)
		assertEquals(expectedPackages.size, allActualPackages.size)
		assertFalse(allActualPackages.map[name].hasDuplicate)
		assertFalse(expectedPackages.map[name].hasDuplicate)
		
		expectedPackages.forEach[
			var actualPackage = claimJavaPackage(view, it.name)
			assertThat(actualPackage, equalsDeeply(it))
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
	
	/**
	 * sorts class members in the following order: <p>
	 * (1) Field <p>
	 * (2) Constructor <p>
	 * (3) Method <p>
	 * Fields, Constructors and Methods them self are sorted by their name.
	 */
	def static sortMembers(EList<? extends Member> members) {
        ECollections.sort(members, new Comparator<Member> {

            override compare(Member o1, Member o2) {
                // fields before constructors and methods
                if (o1 instanceof Field && (o2 instanceof Method || o2 instanceof Constructor)) {
                    return 1
                } else if ((o1 instanceof Method || o1 instanceof Constructor) && o2 instanceof Field) {
                    return -1

                // constructors before Methods  
                } else if (o1 instanceof Constructor && o2 instanceof Method) {
                    return 1
                } else if (o1 instanceof Method && o2 instanceof Constructor) {
                    return -1
                }
                
                // sort by name
                if(o1 instanceof NamedElement && o2 instanceof NamedElement) {
                	return (o1 as NamedElement).name.compareTo((o2 as NamedElement).name)
                }
                
                return 0
            }

            override equals(Object obj) {
                return this == obj
            }

        })
    }
	
}
