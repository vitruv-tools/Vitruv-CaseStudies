package tools.vitruv.applications.cbs.testutils.equivalencetest

import edu.kit.ipd.sdq.activextendannotations.CloseResource
import java.nio.file.Path
import java.util.List
import java.util.Optional
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Delegate
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.junit.jupiter.api.function.Executable
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.testutils.ChangePublishingTestView
import tools.vitruv.testutils.TestProjectManager
import tools.vitruv.testutils.TestView
import tools.vitruv.testutils.UriMode

import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.containsModelOf

import static extension java.nio.file.Files.walkFileTree
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import static java.nio.file.FileVisitResult.*
import java.io.IOException
import java.util.LinkedHashSet
import java.util.function.Predicate
import tools.vitruv.framework.domains.VitruvDomain
import org.hamcrest.TypeSafeMatcher
import java.util.Set
import org.hamcrest.Description

import static extension tools.vitruv.testutils.printing.ModelPrinting.*
import org.eclipse.emf.ecore.resource.Resource
import org.hamcrest.Matcher
import static tools.vitruv.testutils.printing.PrintMode.MULTI_LINE
import tools.vitruv.applications.cbs.testutils.ModelComparisonSettings
import static com.google.common.base.Preconditions.checkNotNull
import tools.vitruv.testutils.BasicTestView
import java.util.Map
import tools.vitruv.testutils.printing.PrintIdProvider
import tools.vitruv.testutils.printing.HamcrestDescriptionPrintTarget
import org.opentest4j.TestAbortedException
import tools.vitruv.testutils.printing.ModelPrinting
import org.eclipse.emf.common.util.URI
import tools.vitruv.testutils.printing.CombinedModelPrinter
import tools.vitruv.testutils.printing.UriReplacingPrinter
import tools.vitruv.testutils.printing.DefaultPrintIdProvider
import java.util.Collection
import tools.vitruv.testutils.TestUserInteraction
import tools.vitruv.framework.domains.repository.VitruvDomainRepositoryImpl
import tools.vitruv.framework.vsum.VirtualModelBuilder
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import edu.kit.ipd.sdq.activextendannotations.Lazy

@FinalFieldsConstructor
package class EquivalenceTestExecutable implements Executable, AutoCloseable {
	static val testProjectManager = new TestProjectManager()
	val DomainStep testStep
	val Map<VitruvDomain, List<DomainStep>> dependencySteps
	val Map<VitruvDomain, DomainStep> referenceSteps
	val Collection<ChangePropagationSpecification> changePropagationSpecifications
	val UriMode uriMode
	val ModelComparisonSettings comparisonSettings
	val EquivalenceTestExtensionContext extensionContext
	@Lazy val VitruvDomainRepository propagationDomains = new VitruvDomainRepositoryImpl(
		changePropagationSpecifications.flatMap [List.of(sourceDomain, targetDomain)].toSet
	)

	override execute() throws Throwable {
		val testView = setupTestView()
		val referenceView = setupReferenceView()
		val printerChange = installViewDirectoryUriReplacement(testView, referenceView)
		
		execute(this, printerChange, setupTestView(), setupReferenceView())
	}

	def private void execute(
		@CloseResource AutoCloseable toClose1,
		@CloseResource AutoCloseable toClose2,
		@CloseResource DirectoryTestView testView,
		@CloseResource DirectoryTestView referenceView
	) throws Throwable {
		try {
			executeDependencies(testView, referenceView)

			testStep.executeIn(testView)
			referenceSteps.values.forEach[executeIn(referenceView)]
			verifyTestViewResults()
		} catch (Throwable t) {
			extensionContext.executionException = Optional.of(t)
			throw t
		}
	}

	def private DirectoryTestView setupTestView() {
		val viewDirectory = testProjectManager.getProject("", extensionContext)
		val vsumDirectory = testProjectManager.getProject("vsum", extensionContext)

		TuidManager.instance.reinitialize()
		val changePropagationSpecifications = this.changePropagationSpecifications
		val userInteraction = new TestUserInteraction
		val vsum = new VirtualModelBuilder()
			.withStorageFolder(vsumDirectory)
			.withUserInteractorForResultProvider(new TestUserInteraction.ResultProvider(userInteraction))
			.withDomainRepository(propagationDomains)
			.withChangePropagationSpecifications(changePropagationSpecifications)
			.build()
		new DirectoryTestView(
			new ChangePublishingTestView(viewDirectory, userInteraction, uriMode, vsum, propagationDomains),
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
			for (dependencyTestStep : dependencySteps.get(testStep.targetDomain)) {
				dependencyTestStep.executeIn(testView)
			}
			for (referenceDomain : referenceDomains) {
				for (dependencyReferenceStep : dependencySteps.get(referenceDomain)) {
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

	def private void verifyTestViewResults() {
		// use new views to reload the actual resources from disk
		verifyTestViewResults(setupReadOnlyTestView(), setupReferenceView())
	}

	def private void verifyTestViewResults(
		@CloseResource DirectoryTestView testView,
		@CloseResource DirectoryTestView referenceView
	) throws Throwable {
		val referenceFiles = referenceView.directory.dataFiles [ file |
			referenceDomains.exists[file.belongsTo(it)]
		]

		assertThat(testView, containsExactlyResources(referenceView, referenceFiles))

		referenceFiles.forEach [ model |
			val referenceResource = referenceView.resourceAt(model)
			val referenceDomain = checkNotNull(
				referenceDomains.findFirst[referenceResource.belongsTo(it)],
				'''Cannot find domain of «referenceResource»!'''
			)
			val filters = comparisonSettings.getEqualityOptionsForDomain(referenceDomain)

			assertThat(testView.resourceAt(model), containsModelOf(referenceResource, filters))
		]
	}

	def private AutoCloseable installViewDirectoryUriReplacement(TestView testView, TestView referenceView) {
		ModelPrinting.use [ currentPrinter |
			new CombinedModelPrinter(new UriReplacingPrinter(List.of(
				// mind the order, the test view is a prefix of the reference view!
				referenceView.getUri(Path.of('.')).appendSegment('') -> URI.createFileURI("[reference view]/"),
				testView.getUri(Path.of('.')).appendSegment('') -> URI.createFileURI("[test view]/")
			)), currentPrinter)
		]
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

	def static private belongsTo(Path path, VitruvDomain domain) {
		domain.fileExtensions.exists[path.toString.endsWith('''.«it»''')]
	}

	def static private belongsTo(Resource resource, VitruvDomain domain) {
		domain.fileExtensions.contains(resource.URI.fileExtension)
	}

	def private Matcher<? super DirectoryTestView> containsExactlyResources(TestView referenceView,
		Set<Path> referenceFiles) {
		new ModelFilesMatcher(referenceFiles, referenceView, referenceDomains)
	}

	def private getReferenceDomains() {
		referenceSteps.keySet
	}

	def private newBasicView(Path viewDirectory) {
		new DirectoryTestView(new BasicTestView(viewDirectory, uriMode, propagationDomains), viewDirectory)
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
		val Set<VitruvDomain> referenceDomains
		var Set<Path> testFiles
		val PrintIdProvider idProvider = new DefaultPrintIdProvider()

		override describeTo(Description description) {
			description.appendText("exactly these resource paths to exist in the test view: ")
			new HamcrestDescriptionPrintTarget(description).printSet(referenceFiles, MULTI_LINE) [ subTarget, path |
				subTarget.print(path.toString)
			]
		}

		override matchesSafely(DirectoryTestView testView) {
			testFiles = testView.directory.dataFiles [ file |
				referenceDomains.exists [file.belongsTo(it)]
			].toSet
			return testFiles == referenceFiles
		}

		override describeMismatchSafely(DirectoryTestView testView, Description mismatchDescription) {
			val missingResources = (new LinkedHashSet(referenceFiles) => [removeAll(testFiles)])
				.map[referenceView.resourceAt(it)].toSet
			val unexpectedResources = (new LinkedHashSet(testFiles) => [removeAll(referenceFiles)])
				.map[testView.resourceAt(it)].toSet
			if (!missingResources.isEmpty) {
				mismatchDescription.appendText("the following resources are missing in the test view: ").
					appendModelValueSet(missingResources, MULTI_LINE, idProvider)
			}
			if (!unexpectedResources.isEmpty) {
				if (!missingResources.isEmpty) {
					mismatchDescription.appendText(System.lineSeparator).appendText('    and ')
				}
				mismatchDescription.appendText("the test view contains the following unexpected resources: ").
					appendModelValueSet(unexpectedResources, MULTI_LINE, idProvider)
			}
		}
	}
}
