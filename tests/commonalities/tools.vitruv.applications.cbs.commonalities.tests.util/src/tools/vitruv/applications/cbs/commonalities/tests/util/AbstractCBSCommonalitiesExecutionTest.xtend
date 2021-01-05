package tools.vitruv.applications.cbs.commonalities.tests.util

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.xtend.lib.annotations.Accessors

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach
import java.nio.file.Path

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
	val VitruvApplicationTestAdapter vitruvApplicationTestAdapter = new VitruvApplicationTestAdapter() {
		/**
		 * Returns a resource from the runtime test project
		 */
		override getResourceAt(String modelPathInProject) {
			validationResourceSet.getResource(getUri(Path.of(modelPathInProject)), true);
		}

		/**
		 * Returns a test resource from the test specification project (this project) rather than the runtime project
		 */
		override getTestResource(String resourcePathInExecutingProject) {
			validationResourceSet.getResource(URI.createURI(resourcePathInExecutingProject), true)
		}

		override createAndSynchronizeModel(String modelPathInProject, EObject rootElement) {
			val resource = resourceAt(Path.of(modelPathInProject)).startRecordingChanges => [
				contents += rootElement
			]
			propagate
			// This is a necessary hack, because the transformations recreate elements, such that the resulting models are the same but new UUIDs are assigned.
			// Starting recording again reloads the UUIDs
			startRecordingChanges(resource)
		}
	}

	@Accessors(PROTECTED_GETTER)
	var ResourceSet validationResourceSet

	@BeforeEach
	def protected void setupResourceSets() {
		validationResourceSet = new ResourceSetImpl()
	}

	@AfterEach
	def protected void cleanupResourceSets() {
		validationResourceSet.resources.forEach[unload]
		validationResourceSet.resources.clear
		validationResourceSet = null
	}

}
