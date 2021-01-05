package tools.vitruv.applications.cbs.commonalities.tests.util

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.xtend.lib.annotations.Accessors

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach
import java.nio.file.Path

import static org.junit.jupiter.api.Assertions.assertNotNull;
import tools.vitruv.dsls.commonalities.testutils.ExecutionTestCompiler
import org.junit.jupiter.api.^extension.ExtendWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.InjectWith
import tools.vitruv.dsls.commonalities.testutils.CombinedUiInjectorProvider
import org.junit.jupiter.api.TestInstance
import tools.vitruv.testutils.LegacyVitruvApplicationTest
import static com.google.common.base.Preconditions.checkNotNull
import org.junit.jupiter.api.BeforeAll
import tools.vitruv.testutils.TestProject
import com.google.inject.Inject

@ExtendWith(InjectionExtension)
@InjectWith(CombinedUiInjectorProvider)
@TestInstance(PER_CLASS)
abstract class AbstractCBSCommonalitiesExecutionTest extends LegacyVitruvApplicationTest {
	// Statically save the compiler to ensure that generation is only performed once per test run
	static ExecutionTestCompiler compiler
	ExecutionTestCompiler.Factory factory
	Path testProjectPath

	protected abstract def ExecutionTestCompiler createCompiler(ExecutionTestCompiler.Factory factory)

	@BeforeAll
	def void setProjectPath(@TestProject Path testProjectPath) {
		this.testProjectPath = testProjectPath
	}

	@Inject
	def setCompilerFactory(ExecutionTestCompiler.Factory factory) {
		this.factory = factory
	}

	def getOrCreateCompiler() {
		if (compiler === null) {
			compiler = createCompiler(
				checkNotNull(factory, "The compiler factory was not injected yet!").setParameters [
				commonalitiesOwner = this
				// We create an own directory that is not cleaned after the test runs of a class are completed. 
				// The folder remains after test execution.
				compilationProjectDir = checkNotNull(testProjectPath?.parent?.resolve("[compiled commonalities]"),
					"Compilation directory could not be acquired!")
			])
		}
		return compiler
	}

	override protected getChangePropagationSpecifications() {
		getOrCreateCompiler().changePropagationSpecifications
	}

	@Accessors(PROTECTED_GETTER)
	val VitruvApplicationTestAdapter vitruvApplicationTestAdapter = createVitruvApplicationTestAdapter()

	private def VitruvApplicationTestAdapter createVitruvApplicationTestAdapter() {
		val testResourceFunc = [ String resourcePath |
			getTestResource(resourcePath)
		]
		val createAndSynchronizeModelFunc = [ String modelPathInProject, EObject rootElement |
			createAndSynchronizeModel(modelPathInProject, rootElement)
			// This is a necessary hack, because the transformations recreate elements, such that the resulting models are the same but new UUIDs are assigned.
			// Starting recording again reloads the UUIDs
			startRecordingChanges(rootElement)
		]
		val saveAndSynchronizeChangesFunc = [ Resource resource |
			saveAndSynchronizeChanges(resource)
		]
		val deleteAndSynchronizeModelFunc = [ String modelPathInProject |
			deleteAndSynchronizeModel(modelPathInProject)
		]
		return new VitruvApplicationTestAdapter() {

			override getResourceAt(String modelPathInProject) {
				// The resources get loaded into the result resource set:
				resultResourceSet.getResource(getUri(Path.of(modelPathInProject)), true);
			}

			override getTestResource(String resourcePath) {
				testResourceFunc.apply(resourcePath)
			}

			override createAndSynchronizeModel(String modelPathInProject, EObject rootElement) {
				createAndSynchronizeModelFunc.apply(modelPathInProject, rootElement)
			}

			override saveAndSynchronizeChanges(Resource resource) {
				saveAndSynchronizeChangesFunc.apply(resource)
			}

			override deleteAndSynchronizeModel(String modelPathInProject) {
				deleteAndSynchronizeModelFunc.apply(modelPathInProject)
			}
		}
	}

	@Accessors(PROTECTED_GETTER)
	var ResourceSet testResourcesResourceSet

	// Stores the loaded target models.
	@Accessors(PROTECTED_GETTER)
	var ResourceSet resultResourceSet

	@BeforeEach
	def protected void setupResourceSets() {
		testResourcesResourceSet = new ResourceSetImpl()
		resultResourceSet = new ResourceSetImpl()
	}

	@AfterEach
	def protected void cleanupResourceSets() {
		testResourcesResourceSet = null
		resultResourceSet = null
	}

	protected final def getTestResource(String resourcePath) {
		val resourceUri = URI.createURI(resourcePath)
		val resource = testResourcesResourceSet.getResource(resourceUri, true)
		assertNotNull(resource, "Resource not found: " + resourcePath)
		return resource
	}
}
