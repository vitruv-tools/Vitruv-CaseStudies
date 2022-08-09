package tools.vitruv.applications.cbs.testutils.equivalencetest

import java.io.IOException
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import java.util.Collection
import java.util.LinkedHashSet
import java.util.List
import java.util.Map
import java.util.Optional
import java.util.Set
import java.util.function.Predicate
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Delegate
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.jupiter.api.function.Executable
import org.opentest4j.TestAbortedException
import tools.vitruv.applications.cbs.testutils.ModelComparisonSettings
import tools.vitruv.framework.vsum.VirtualModelBuilder
import tools.vitruv.testutils.views.BasicTestView
import tools.vitruv.testutils.views.ChangePublishingTestView
import tools.vitruv.testutils.TestProjectManager
import tools.vitruv.testutils.TestUserInteraction
import tools.vitruv.testutils.views.TestView
import tools.vitruv.testutils.views.UriMode
import tools.vitruv.testutils.printing.DefaultPrintIdProvider
import tools.vitruv.testutils.printing.ModelPrinting
import tools.vitruv.testutils.printing.PrintIdProvider
import tools.vitruv.testutils.printing.UriReplacingPrinter

import static com.google.common.base.Preconditions.checkNotNull
import static java.nio.file.FileVisitResult.*
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.containsModelOf
import static tools.vitruv.testutils.printing.PrintMode.*

import static extension java.nio.file.Files.walkFileTree
import static extension tools.vitruv.testutils.printing.ModelPrinting.*
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import tools.vitruv.change.propagation.ChangePropagationSpecification
import tools.vitruv.applications.cbs.testutils.MetamodelDescriptor

@FinalFieldsConstructor
package class EquivalenceTestExecutable implements Executable, AutoCloseable {
	static val testProjectManager = new TestProjectManager()
	val MetamodelStep testStep
	val Map<MetamodelDescriptor, List<MetamodelStep>> dependencySteps
	val Map<MetamodelDescriptor, MetamodelStep> referenceSteps
	val Collection<ChangePropagationSpecification> changePropagationSpecifications
	val UriMode uriMode
	val ModelComparisonSettings comparisonSettings
	val EquivalenceTestExtensionContext extensionContext

	override execute() throws Throwable {
		try (
			val testView = setupTestView();
			val referenceView = setupReferenceView();
			val printerChange = installViewDirectoryUriReplacement(testView, referenceView)
		) {
			checkNotNull(printerChange) // Suppress warning for variable not being used
			executeDependencies(testView, referenceView)

			testStep.executeIn(testView)
			referenceSteps.values.forEach[executeIn(referenceView)]
			verifyTestViewResults()
		} catch (Throwable t) {
			extensionContext.executionException = Optional.of(t)
			throw t
		} finally {
			close()
		}
	}

	def private DirectoryTestView setupTestView() {
		val viewDirectory = testProjectManager.getProject("", extensionContext)
		val vsumDirectory = testProjectManager.getProject("vsum", extensionContext)

		val changePropagationSpecifications = this.changePropagationSpecifications
		val userInteraction = new TestUserInteraction
		val vsum = new VirtualModelBuilder()
			.withStorageFolder(vsumDirectory)
			.withUserInteractorForResultProvider(new TestUserInteraction.ResultProvider(userInteraction))
			.withChangePropagationSpecifications(changePropagationSpecifications)
			.buildAndInitialize()
		new DirectoryTestView(
			new ChangePublishingTestView(viewDirectory, userInteraction, uriMode, vsum),
			viewDirectory
		)
	}

	def private DirectoryTestView setupReferenceView() {
		newBasicView(testProjectManager.getProject("reference", extensionContext))
	}

	def private DirectoryTestView setupReadOnlyTestView() {
		newBasicView(testProjectManager.getProject("", extensionContext))
	}

	def private executeDependencies(TestView testView, TestView referenceView) throws TestAbortedException {
		try {
			for (dependencyTestStep : dependencySteps.get(testStep.targetMetamodel)) {
				dependencyTestStep.executeIn(testView)
			}
			for (referenceMetamodel : referenceMetamodels) {
				for (dependencyReferenceStep : dependencySteps.get(referenceMetamodel)) {
					dependencyReferenceStep.executeIn(referenceView)
				}
			}

			verifyTestViewResults()
		} catch (AssertionError failure) {
			throw abortedException( 
				'This run was aborted because its dependency steps produces an inconsistent result:', failure 
			)
		} catch (Throwable failure) {
			throw abortedException('This run was aborted because executing its dependency steps failed:', failure)
		}
	}

	def private static abortedException(String reason, Throwable cause) {
		new TestAbortedException('''«reason»«System.lineSeparator»«cause.message»''', cause)
	}

	def private void verifyTestViewResults() throws Throwable {
		// use new views to reload the actual resources from disk
		try(
			val testView = setupReadOnlyTestView();
			val referenceView = setupReferenceView()
		) {
			val referenceFiles = referenceView.directory.dataFiles [ file |
				referenceMetamodels.exists[file.belongsTo(it)]
			]
	
			assertThat(testView, containsExactlyResources(referenceView, referenceFiles))
	
			referenceFiles.forEach [ model |
				val referenceResource = referenceView.resourceAt(model)
				val referenceMetamodel = checkNotNull(
					referenceMetamodels.findFirst[referenceResource.belongsTo(it)],
					'''Cannot find metamodel of «referenceResource»!'''
				)
				val filters = comparisonSettings.getEqualityOptionsForMetamodel(referenceMetamodel)
	
				assertThat(testView.resourceAt(model), containsModelOf(referenceResource, filters))
			]
		}
	}

	def private AutoCloseable installViewDirectoryUriReplacement(TestView testView, TestView referenceView) {
		ModelPrinting.prepend(new UriReplacingPrinter(List.of(
			// mind the order, the test view is a prefix of the reference view!
			referenceView.getUri(Path.of('.')).appendSegment('') -> URI.createFileURI("[reference view]/"),
			testView.getUri(Path.of('.')).appendSegment('') -> URI.createFileURI("[test view]/")
		)))
	}

	override close() {
		testProjectManager.afterEach(extensionContext)
		extensionContext.close()
	}

	def private static dataFiles(Path path, Predicate<Path> predicate) {
		val result = new LinkedHashSet()
		path.walkFileTree(new SimpleFileVisitor<Path> {
			override preVisitDirectory(Path dir, BasicFileAttributes attrs) {
				val dirName = dir.fileName.toString
				if (dirName.startsWith('[') && dirName.endsWith(']') && dir != path)
					SKIP_SUBTREE
				else
					CONTINUE
			}

			override visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				val relativized = path.relativize(file)
				if (predicate.test(relativized)) {
					result.add(relativized)
				}
				CONTINUE
			}
		})
		return result
	}

	def static private belongsTo(Path path, MetamodelDescriptor metamodel) {
		metamodel.fileExtensions.exists [path.toString.endsWith('''.«it»''')]
	}

	def static private belongsTo(Resource resource, MetamodelDescriptor metamodel) {
		metamodel.fileExtensions.contains(resource.URI.fileExtension)
	}

	def private Matcher<? super DirectoryTestView> containsExactlyResources(TestView referenceView,
		Set<Path> referenceFiles) {
		new ModelFilesMatcher(referenceFiles, referenceView, referenceMetamodels)
	}

	def private getReferenceMetamodels() {
		referenceSteps.keySet
	}

	def private newBasicView(Path viewDirectory) {
		new DirectoryTestView(new BasicTestView(viewDirectory, uriMode), viewDirectory)
	}

	@FinalFieldsConstructor
	private static class DirectoryTestView implements TestView {
		@Delegate
		val TestView delegate
		@Accessors
		val Path directory
	}

	@FinalFieldsConstructor
	private static class ModelFilesMatcher extends TypeSafeMatcher<DirectoryTestView> {
		val Set<Path> referenceFiles
		val TestView referenceView
		val Set<MetamodelDescriptor> referenceMetamodels
		var Set<Path> testFiles
		val PrintIdProvider idProvider = new DefaultPrintIdProvider()

		override describeTo(Description description) {
			description.appendText("exactly these resource paths to exist in the test view: ")
				.appendPrintResult [
					printSet(referenceFiles, MULTI_LINE_LIST) [ subTarget, path |
						subTarget.print(path.toString)
					]
				]
		}

		override matchesSafely(DirectoryTestView testView) {
			testFiles = testView.directory.dataFiles [ file |
				referenceMetamodels.exists [file.belongsTo(it)]
			]
			return testFiles == referenceFiles
		}

		override describeMismatchSafely(DirectoryTestView testView, Description mismatchDescription) {
			val missingResources = (new LinkedHashSet(referenceFiles) => [removeAll(testFiles)])
				.mapFixedTo(new LinkedHashSet) [referenceView.resourceAt(it)]
			val unexpectedResources = (new LinkedHashSet(testFiles) => [removeAll(referenceFiles)])
				.mapFixedTo(new LinkedHashSet) [referenceView.resourceAt(it)]
			
			if (!missingResources.isEmpty) {
				mismatchDescription.appendText("the following resources are missing in the test view: ").
					appendModelValueSet(missingResources, MULTI_LINE_LIST, idProvider)
			}
			if (!unexpectedResources.isEmpty) {
				if (!missingResources.isEmpty) {
					mismatchDescription.appendText(System.lineSeparator).appendText('    and ')
				}
				mismatchDescription.appendText("the test view contains the following unexpected resources: ").
					appendModelValueSet(unexpectedResources, MULTI_LINE_LIST, idProvider)
			}
		}
	}
}
