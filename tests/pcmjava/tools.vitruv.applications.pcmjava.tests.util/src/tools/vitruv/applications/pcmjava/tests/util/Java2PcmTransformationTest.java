package tools.vitruv.applications.pcmjava.tests.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jdt.core.IBuffer;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.jdt.core.refactoring.descriptors.RenameJavaElementDescriptor;
import org.eclipse.jdt.internal.corext.codemanipulation.StubUtility;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringContribution;
import org.eclipse.ltk.core.refactoring.RefactoringCore;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;
import org.emftext.language.java.JavaClasspath;
import org.emftext.language.java.annotations.AnnotationInstance;
import org.emftext.language.java.annotations.AnnotationsFactory;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.JavaRoot;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.system.System;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil;
import tools.vitruv.domains.pcm.PcmNamespace;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmUserSelection;
import tools.vitruv.applications.pcmjava.util.PcmJavaRepositoryCreationUtil;
import tools.vitruv.domains.java.JavaNamespace;
import tools.vitruv.domains.java.builder.VitruviusJavaBuilder;
import tools.vitruv.domains.java.builder.VitruviusJavaBuilderApplicator;
import tools.vitruv.domains.java.echange.feature.reference.JavaInsertEReference;
import tools.vitruv.domains.java.echange.feature.reference.ReferenceFactory;
import tools.vitruv.framework.change.description.ConcreteChange;
import tools.vitruv.framework.change.description.VitruviusChangeFactory;
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.correspondence.CorrespondenceModel;
import tools.vitruv.framework.ui.monitorededitor.ProjectBuildUtils;
import tools.vitruv.framework.util.bridges.EcoreResourceBridge;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagationAbortCause;
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagationListener;
import tools.vitruv.testutils.VitruviusUnmonitoredApplicationTest;
import tools.vitruv.testutils.util.TestUtil;

import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*;
import static tools.vitruv.domains.java.util.JavaQueryUtil.*;

/**
 * Test class that contains utillity methods that can be used by JaMoPP2PCM
 * transformation tests
 *
 */
@SuppressWarnings("restriction")
public abstract class Java2PcmTransformationTest extends VitruviusUnmonitoredApplicationTest
		implements ChangePropagationListener, SynchronizationAwaitCallback {

	private static final Logger logger = Logger.getLogger(Java2PcmTransformationTest.class.getSimpleName());

	private static int MAXIMUM_SYNC_WAITING_TIME = 10000;

	protected Package mainPackage;
	protected Package secondPackage;
	private int expectedNumberOfSyncs = 0;

	public Java2PcmTransformationTest() {
		setTestProjectCreator(projectName -> TestUtil.createPlatformProject(projectName, true).getLocation().toFile());
	}

	@Override
	protected Iterable<VitruvDomain> getVitruvDomains() {
		return PcmJavaRepositoryCreationUtil.createPcmJamoppMetamodels();
	}

	protected IProject getCurrentTestProject() {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(getCurrentTestProjectFolder().getName());
	}

	@Override
	public void beforeTest() {
		super.beforeTest();
		this.getVirtualModel().addChangePropagationListener(this);
		// This is necessary because otherwise Maven tests will fail as
		// resources from previous
		// tests are still in the classpath and accidentally resolved
		JavaClasspath.reset();
		// add PCM Java Builder to Project under test
		final VitruviusJavaBuilderApplicator pcmJavaBuilder = new VitruviusJavaBuilderApplicator();
		pcmJavaBuilder.addToProject(this.getCurrentTestProject(), getVirtualModel().getFolder(),
				Collections.singletonList(PcmNamespace.REPOSITORY_FILE_EXTENSION));
		// build the project
		ProjectBuildUtils.issueIncrementalBuild(getCurrentTestProject(), VitruviusJavaBuilder.BUILDER_ID);
		this.expectedNumberOfSyncs = 0;
		// Pipe JaMoPP error output to null
		java.lang.System.setErr(null);
	}

	private String getPlatformModelPath(final String modelPathWithinProject) {
		return this.getCurrentTestProject().getName() + "/" + modelPathWithinProject;
	}

	// We override the modelVuri getter, because we have to use platform URIs
	// for the Java to PCM tests
	// because otherwise the JDT AST will not recognize the changes
	@Override
	protected VURI getModelVuri(String modelPathWithinProject) {
		return VURI.getInstance(getPlatformModelPath(modelPathWithinProject));
	}

	@Override
	public void afterTest() {
		// Remove PCM Java Builder
		final VitruviusJavaBuilderApplicator pcmJavaRemoveBuilder = new VitruviusJavaBuilderApplicator();
		pcmJavaRemoveBuilder.removeBuilderFromProject(this.getCurrentTestProject());
	}

	public void editCompilationUnit(final ICompilationUnit cu, final TextEdit... edits) throws JavaModelException {
		CompilationUnitManipulatorHelper.editCompilationUnit(cu, this, edits);
	}

	public synchronized void waitForSynchronization(int numberOfExpectedSynchronizationCalls) {
		expectedNumberOfSyncs += numberOfExpectedSynchronizationCalls;
		logger.debug("Starting to wait for finished synchronization. Expected syncs: "
				+ numberOfExpectedSynchronizationCalls + ", remaining syncs: " + expectedNumberOfSyncs);
		try {
			int wakeups = 0;
			while (expectedNumberOfSyncs > 0) {
				wait(MAXIMUM_SYNC_WAITING_TIME);
				wakeups++;
				// If we had more wakeups than expected sync calls, we had a
				// timeout
				// and so the synchronization was not finished as expected
				if (wakeups > numberOfExpectedSynchronizationCalls) {
					fail("Waiting for synchronization timed out");
				}
			}
		} catch (InterruptedException e) {
			fail("An interrupt occurred unexpectedly");
		} finally {
			logger.debug("Finished waiting for synchronization");
		}
	}

	@Override
	public synchronized void startedChangePropagation() {
	}

	@Override
	public synchronized void finishedChangePropagation() {
		expectedNumberOfSyncs--;
		logger.debug("Reducing number of expected syncs to: " + expectedNumberOfSyncs);
		this.notifyAll();
	}

	@Override
	public synchronized void abortedChangePropagation(ChangePropagationAbortCause cause) {
		expectedNumberOfSyncs--;
		logger.debug("Reducing number of expected syncs to: " + expectedNumberOfSyncs);
		this.notifyAll();
	}

	protected Repository addRepoContractsAndDatatypesPackage() throws IOException, CoreException {
		this.mainPackage = this.createPackageWithPackageInfo(new String[] { Pcm2JavaTestUtils.REPOSITORY_NAME });
		this.createPackageWithPackageInfo(new String[] { Pcm2JavaTestUtils.REPOSITORY_NAME, "contracts" });
		this.createPackageWithPackageInfo(new String[] { Pcm2JavaTestUtils.REPOSITORY_NAME, "datatypes" });
		final CorrespondenceModel ci = this.getCorrespondenceModel();
		if (null == ci) {
			throw new RuntimeException("Could not get correspondence instance.");
		}
		final Repository repo = claimOne(
				CorrespondenceModelUtil.getCorrespondingEObjectsByType(ci, this.mainPackage, Repository.class));
		return repo;
	}

	protected BasicComponent addSecondPackageCorrespondsToBasicComponent() throws Throwable {
		this.getUserInteractor().addNextSelections(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
		return this.createSecondPackage(BasicComponent.class, Pcm2JavaTestUtils.REPOSITORY_NAME,
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME);
	}

	protected <T> T createSecondPackage(final Class<T> correspondingType, final String... namespace) throws Throwable {
		this.secondPackage = this.createPackageWithPackageInfo(namespace);
		return claimOne(CorrespondenceModelUtil.getCorrespondingEObjectsByType(this.getCorrespondenceModel(),
				this.secondPackage, correspondingType));
	}

	private void createSecondPackageWithoutCorrespondence(final String... namespace) throws Throwable {
		this.secondPackage = this.createPackageWithPackageInfo(namespace);
	}

	protected void createPackage(final String[] namespace) throws Throwable {
		final IPackageFragmentRoot packageRoot = this.getIJavaProject();
		final String namespaceDotted = StringUtils.join(namespace, ".");
		final boolean force = true;
		packageRoot.createPackageFragment(namespaceDotted, force, new NullProgressMonitor());
		waitForSynchronization(1);
	}

	protected Package createPackageWithPackageInfo(final String... namespace) throws IOException {
		String packageFile = StringUtils.join(namespace, "/");
		packageFile = packageFile + "/package-info.java";
		final Package jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage();
		final List<String> namespaceList = Arrays.asList(namespace);
		jaMoPPPackage.setName(namespaceList.get(namespaceList.size() - 1));
		jaMoPPPackage.getNamespaces().addAll(namespaceList.subList(0, namespaceList.size() - 1));
		final Resource resource = this.createModelResource(getPathInProjectForSrcFile(packageFile));
		EcoreResourceBridge.saveEObjectAsOnlyContent(jaMoPPPackage, resource);
		waitForSynchronization(1);
		return jaMoPPPackage;
	}

	protected Package renamePackage(final Package packageToRename, String newName) throws CoreException {
		final Resource resource = packageToRename.eResource();
		final IFile iFile = URIUtil.getIFileForEMFUri(resource.getURI());
		IPath iPath = iFile.getProjectRelativePath();
		iPath = iPath.removeLastSegments(1);
		final String oldPackageName = packageToRename.getName();
		if (oldPackageName.contains(".")) {
			newName = oldPackageName.substring(0, oldPackageName.lastIndexOf(".") + 1) + newName;
		}
		final IFolder iFolder = iFile.getProject().getFolder(iPath);
		final IJavaElement javaPackage = JavaCore.create(iFolder);
		this.refactorRenameJavaElement(newName, javaPackage, IJavaRefactorings.RENAME_PACKAGE);
		final Package newPackage = this.findJaMoPPPackageWithName(newName);
		return newPackage;
	}

	private void refactorRenameJavaElement(final String newName, final IJavaElement iJavaElement,
			final String refactorRenameActionName) throws CoreException {
		final RefactoringContribution refacContrib = RefactoringCore
				.getRefactoringContribution(refactorRenameActionName);
		final RefactoringStatus status = new RefactoringStatus();
		final RenameJavaElementDescriptor desc = (RenameJavaElementDescriptor) refacContrib.createDescriptor();
		desc.setUpdateReferences(true);
		desc.setJavaElement(iJavaElement);
		desc.setNewName(newName);
		final NullProgressMonitor monitor = new NullProgressMonitor();
		final Refactoring refactoring = desc.createRefactoring(status);
		refactoring.checkInitialConditions(monitor);
		refactoring.checkFinalConditions(monitor);
		final Change change = refactoring.createChange(monitor);
		change.perform(monitor);
		waitForSynchronization(1);
	}

	protected <T> T renameClassifierWithName(final String entityName, final String newName, final Class<T> type)
			throws Throwable {
		try {
			final ICompilationUnit cu = CompilationUnitManipulatorHelper
					.findICompilationUnitWithClassName(entityName + ".java", this.getCurrentTestProject());
			final int offset = cu.getBuffer().getContents().indexOf(entityName);
			if (cu.getBuffer() instanceof IBuffer.ITextEditCapability) {
				logger.info(cu.getBuffer());
			}
			final ReplaceEdit edit = new ReplaceEdit(offset, entityName.length(), newName);
			editCompilationUnit(cu, edit);
			final VURI vuri = VURI.getInstance(cu.getResource());
			final Classifier jaMoPPClass = this.getJaMoPPClassifierForVURI(vuri);
			return claimOne(CorrespondenceModelUtil.getCorrespondingEObjectsByType(this.getCorrespondenceModel(),
					jaMoPPClass, type));
		} catch (final Throwable e) {
			logger.warn(e.getMessage());
		}
		return null;

	}

	private Package findJaMoPPPackageWithName(final String newName) throws JavaModelException {
		final IJavaProject javaProject = JavaCore.create(this.getCurrentTestProject());
		for (final IPackageFragmentRoot packageFragmentRoot : javaProject.getPackageFragmentRoots()) {
			final IJavaElement[] children = packageFragmentRoot.getChildren();
			for (final IJavaElement iJavaElement : children) {
				if (iJavaElement instanceof IPackageFragment) {
					final IPackageFragment fragment = (IPackageFragment) iJavaElement;
					if (fragment.getElementName().equals(newName)) {
						final VURI vuri = this.getVURIForElementInPackage(fragment, "package-info");
						final Package jaMoPPPackage = this.getJaMoPPRootForVURI(vuri);
						return jaMoPPPackage;
					}
					// final IJavaElement[] javaElements =
					// fragment.getChildren();
					// for (int k = 0; k < javaElements.length; k++) {
					// final IJavaElement javaElement = javaElements[k];
					// if (javaElement.getElementType() ==
					// IJavaElement.PACKAGE_FRAGMENT) {
					// final IPackageFragment packageFragment =
					// (IPackageFragment) javaElement;
					// if (packageFragment.getElementName().equals(newName)) {
					// final VURI vuri =
					// this.getVURIForElementInPackage(packageFragment,
					// "package-info.java");
					// final Package jaMoPPPackage =
					// this.getJaMoPPRootForVURI(vuri);
					// return jaMoPPPackage;
					// }
					// }
					// }
				}
			}
		}
		throw new RuntimeException("Could not find a compilation unit with name " + newName);
	}

	private IPackageFragmentRoot getIJavaProject() throws CoreException {
		final IProject project = this.getCurrentTestProject();
		final IJavaProject javaProject = JavaCore.create(project);
		final IFolder sourceFolder = project.getFolder(TestUtil.SOURCE_FOLDER);
		if (!sourceFolder.exists()) {
			final boolean force = true;
			final boolean local = true;
			sourceFolder.create(force, local, new NullProgressMonitor());
		}
		final IPackageFragmentRoot packageFragment = javaProject.getPackageFragmentRoot(sourceFolder);
		return packageFragment;
	}

	private String getPathInProjectForSrcFile(final String srcFilePath) {
		return TestUtil.SOURCE_FOLDER + "/" + srcFilePath;
	}

	protected <T> T addClassInSecondPackage(final Class<T> classOfCorrespondingObject) throws Throwable {
		final T createdEObject = this.addClassInPackage(this.secondPackage, classOfCorrespondingObject);
		return createdEObject;
	}

	protected <T> T addClassInPackage(final Package packageForClass, final Class<T> classOfCorrespondingObject)
			throws Throwable {
		final String implementingClassName = Pcm2JavaTestUtils.IMPLEMENTING_CLASS_NAME;
		return this.addClassInPackage(packageForClass, classOfCorrespondingObject, implementingClassName);
	}

	protected <T> T addClassInPackage(final Package packageForClass, final Class<T> classOfCorrespondingObject,
			final String implementingClassName) throws CoreException, InterruptedException {
		final Classifier jaMoPPClass = this.createClassInPackage(packageForClass, implementingClassName);
		final Set<T> eObjectsByType = CorrespondenceModelUtil
				.getCorrespondingEObjectsByType(this.getCorrespondenceModel(), jaMoPPClass, classOfCorrespondingObject);
		return claimOne(eObjectsByType);
	}

	protected Classifier createClassInPackage(final Package packageForClass, final String implementingClassName)
			throws CoreException {
		String packageName = packageForClass.getName();
		String packageNamespace = packageForClass.getNamespacesAsString() + packageName;
		return createClassInPackage(implementingClassName, packageNamespace);
	}

	protected Classifier createClassInPackage(final String implementingClassName, String packageNamespace)
			throws CoreException {
		final IPackageFragment packageFragment = this.getPackageFragment(packageNamespace);
		createEmptyClass(packageFragment, implementingClassName);
		final VURI vuri = this.getVURIForElementInPackage(packageFragment, implementingClassName);
		final Classifier jaMoPPClass = this.getJaMoPPClassifierForVURI(vuri);
		return jaMoPPClass;
	}

	private VURI getVURIForElementInPackage(final IPackageFragment packageFragment, final String elementName) {
		String vuriKey = packageFragment.getResource().getFullPath().toString() + "/" + elementName + "."
				+ JavaNamespace.FILE_EXTENSION;
		if (vuriKey.startsWith("/")) {
			vuriKey = vuriKey.substring(1, vuriKey.length());
		}
		final VURI vuri = VURI.getInstance(vuriKey);
		return vuri;
	}

	private IPackageFragment getPackageFragment(String packageNamespace) throws CoreException {
		final IPackageFragmentRoot packageRoot = this.getIJavaProject();
		for (final IJavaElement javaElement : packageRoot.getChildren()) {
			if (javaElement instanceof IPackageFragment && javaElement.getElementName().equals(packageNamespace)) {
				return (IPackageFragment) javaElement;
			}
		}
		throw new RuntimeException("No packageFragment found for JaMoPP package " + packageNamespace);
	}

	protected ConcreteClassifier getJaMoPPClassifierForVURI(final VURI vuri) {
		final CompilationUnit cu = this.getJaMoPPRootForVURI(vuri);
		final Classifier jaMoPPClassifier = cu.getClassifiers().get(0);
		return (ConcreteClassifier) jaMoPPClassifier;
	}

	private <T extends JavaRoot> T getJaMoPPRootForVURI(final VURI vuri) {
		final Resource resource = URIUtil.loadResourceAtURI(vuri.getEMFUri(), new ResourceSetImpl());
		// unchecked is OK for the test.
		@SuppressWarnings("unchecked")
		final T javaRoot = (T) resource.getContents().get(0);
		return javaRoot;
	}

	/**
	 * Change user interactor in changeSynchronizer of
	 * PCMJaMoPPTransformationExecuter by invoking the setUserInteractor method
	 * of the class ChangeSynchronizer
	 *
	 * @throws Throwable
	 */
	// private void setUserInteractor(final UserInteracting newUserInteracting)
	// throws Throwable {
	//// final PCMJavaBuilder pcmJavaBuilder =
	// this.getPCMJavaBuilderFromProject();
	//// final Change2CommandTransformingProviding transformingProviding =
	// JavaBridge
	//// .getFieldFromClass(VitruviusEmfBuilder.class, "transformingProviding",
	// pcmJavaBuilder);
	// this.setUserInteractor(newUserInteracting);
	// }

	protected CompositeComponent addSecondPackageCorrespondsToCompositeComponent() throws Throwable {
		this.getUserInteractor().addNextSelections(Java2PcmUserSelection.SELECT_COMPOSITE_COMPONENT.getSelection());
		return this.createSecondPackage(CompositeComponent.class, Pcm2JavaTestUtils.REPOSITORY_NAME,
				Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME);
	}

	protected org.palladiosimulator.pcm.system.System addSecondPackageCorrespondsToSystem() throws Throwable {
		this.getUserInteractor().addNextSelections(Java2PcmUserSelection.SELECT_SYSTEM.getSelection());
		return this.createSecondPackage(System.class, Pcm2JavaTestUtils.SYSTEM_NAME);
	}

	protected void addSecondPackageCorrespondsWithoutCorrespondences() throws Throwable {
		this.getUserInteractor().addNextSelections(Java2PcmUserSelection.SELECT_NOTHING_DECIDE_LATER.getSelection());
		this.createSecondPackageWithoutCorrespondence(Pcm2JavaTestUtils.REPOSITORY_NAME,
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME);
	}

	protected void assertRepositoryAndPCMName(final Repository repo, final RepositoryComponent repoComponent,
			final String expectedName) throws Throwable {

		assertEquals("Repository of compoennt is not the repository: " + repo, repo.getId(),
				repoComponent.getRepository__RepositoryComponent().getId());

		this.assertPCMNamedElement(repoComponent, expectedName);
	}

	protected void assertResourceAndFileForEObjects(final EObject... eObjects) throws Throwable {
		for (final EObject eObject : eObjects) {
			final Resource eResource = eObject.eResource();
			assertNotNull("Resource of eObject " + eObject + " is null", eResource);
			final IFile iFile = URIUtil.getIFileForEMFUri(eResource.getURI());
			assertTrue("No IFile for eObject " + eObject + " in resource " + eResource + " found.", iFile.exists());
		}
	}

	protected void assertFilesOnlyForEObjects(final EObject... eObjects) throws Throwable {
		final Set<String> fullFilePaths = new HashSet<String>();
		for (final EObject eObject : eObjects) {
			final IFile iFile = URIUtil.getIFileForEMFUri(eObject.eResource().getURI());
			fullFilePaths.add(iFile.getFullPath().toString());
		}
		final IFolder folder = this.getCurrentTestProject().getFolder("model");
		final List<String> foundAdditionalFiles = new ArrayList<>();
		for (final IResource iResource : folder.members()) {
			final String iResourcePath = iResource.getFullPath().toString();
			if (!fullFilePaths.contains(iResourcePath)) {
				foundAdditionalFiles.add(iResourcePath);
			}
		}
		if (0 < foundAdditionalFiles.size()) {
			final StringBuilder failMsg = new StringBuilder("Found addtional files in model folder: ");
			foundAdditionalFiles.forEach(str -> failMsg.append(str).append(", "));
			fail(failMsg.toString());
		}
	}

	protected void assertRepositoryAndPCMNameForDatatype(final Repository repo, final DataType dt,
			final String expectedName) throws Throwable {

		assertEquals("Repository of compoennt is not the repository: " + repo, repo.getId(),
				dt.getRepository__DataType().getId());
		if (dt instanceof CompositeDataType) {
			this.assertPCMNamedElement((CompositeDataType) dt, expectedName);
		} else if (dt instanceof CollectionDataType) {
			this.assertPCMNamedElement((CollectionDataType) dt, expectedName);
		} else {
			throw new RuntimeException("Primitive data types should not have a correspondence to classes");
		}
	}

	protected void assertPCMNamedElement(final NamedElement pcmNamedElement, final String expectedName)
			throws Throwable {
		assertEquals("The name of pcm named element is not " + expectedName, expectedName,
				pcmNamedElement.getEntityName());
		this.assertResourceAndFileForEObjects(pcmNamedElement);
	}

	protected void assertPcmParameter(final Parameter pcmParameter, final String expectedName) throws Throwable {
		assertEquals("The name of pcm parameter is not " + expectedName, expectedName, pcmParameter.getParameterName());
		this.assertResourceAndFileForEObjects(pcmParameter);
	}

	protected OperationInterface addInterfaceInContractsPackage() throws Throwable {
		return this.createInterfaceInPackage("contracts");
	}

	private OperationInterface createInterfaceInPackage(final String packageName)
			throws Throwable, CoreException, InterruptedException {
		final String interfaceName = Pcm2JavaTestUtils.INTERFACE_NAME;
		return this.createInterfaceInPackageBasedOnJaMoPPPackageWithCorrespondence(packageName, interfaceName);
	}

	protected OperationInterface createInterfaceInPackageBasedOnJaMoPPPackageWithCorrespondence(
			final String packageName, final String interfaceName) throws CoreException {
		Package jaMoPPPackage = this.getPackageWithNameFromCorrespondenceModel(packageName);
		return this.createInterfaceInPackage(jaMoPPPackage.getNamespacesAsString() + jaMoPPPackage.getName(),
				interfaceName, true);
	}

	protected ConcreteClassifier createInterfaceInPackageBasedOnJaMoPPPackageWithoutCorrespondence(
			final String packageName, final String interfaceName)
			throws Throwable, CoreException, InterruptedException {
		Package jaMoPPPackage = this.getPackageWithNameFromCorrespondenceModel(packageName);
		return this.createJaMoPPInterfaceInPackage(jaMoPPPackage.getNamespacesAsString() + jaMoPPPackage.getName(),
				interfaceName);
	}

	protected OperationInterface createInterfaceInPackage(String packageNamespace, final String interfaceName,
			boolean claimOne) throws CoreException {
		final Classifier jaMoPPIf = createJaMoPPInterfaceInPackage(packageNamespace, interfaceName);
		Set<OperationInterface> correspondingOpInterfaces = CorrespondenceModelUtil
				.getCorrespondingEObjectsByType(this.getCorrespondenceModel(), jaMoPPIf, OperationInterface.class);
		if (claimOne) {
			return claimOne(correspondingOpInterfaces);
		}
		if (null == correspondingOpInterfaces || 0 == correspondingOpInterfaces.size()) {
			return null;
		}
		logger.warn("More than one corresponding interfaces found for interface " + jaMoPPIf + ". Returning the first");
		return correspondingOpInterfaces.iterator().next();
	}

	protected ConcreteClassifier createJaMoPPInterfaceInPackage(String packageNamespace, final String interfaceName)
			throws CoreException {
		final IPackageFragment packageFragment = this.getPackageFragment(packageNamespace);
		createEmptyInterface(packageFragment, interfaceName);
		final VURI vuri = this.getVURIForElementInPackage(packageFragment, interfaceName);
		final ConcreteClassifier jaMoPPIf = this.getJaMoPPClassifierForVURI(vuri);

		return jaMoPPIf;
	}

	private void createEmptyCompilationUnit(IPackageFragment packageFragment, String typeName, String cuName)
			throws JavaModelException {
		String lineDelimiter = null;
		lineDelimiter = StubUtility.getLineDelimiterUsed(packageFragment.getJavaProject());
		ICompilationUnit compilationUnit = packageFragment.createCompilationUnit(cuName + ".java", "", false, null);
		InsertEdit edit = new InsertEdit(0, "package " + packageFragment.getElementName() + ";" + lineDelimiter
				+ lineDelimiter + "public " + typeName + " " + cuName + " { }");
		editCompilationUnit(compilationUnit, edit);
	}

	protected void createEmptyInterface(IPackageFragment packageFragment, String interfaceName)
			throws JavaModelException {
		createEmptyCompilationUnit(packageFragment, "interface", interfaceName);
	}

	protected void createEmptyClass(IPackageFragment packageFragment, String className) throws JavaModelException {
		createEmptyCompilationUnit(packageFragment, "class", className);
	}

	protected OperationInterface addInterfaceInSecondPackageWithCorrespondence(final String packageName)
			throws Throwable {
		this.getUserInteractor().addNextSelections(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
		return this.createInterfaceInPackage(packageName);
	}

	protected EObject addInterfaceInPackageWithoutCorrespondence(final String packageName) throws Throwable {
		this.getUserInteractor().addNextSelections(Java2PcmUserSelection.SELECT_COMPOSITE_COMPONENT.getSelection());
		Package jaMoPPPackage = this.getPackageWithNameFromCorrespondenceModel(packageName);
		return this.createInterfaceInPackage(jaMoPPPackage.getNamespacesAsString() + jaMoPPPackage.getName(),
				"I" + packageName, false);
	}

	protected Package getPackageWithNameFromCorrespondenceModel(final String name) throws CoreException {
		final Set<Package> packages = CorrespondenceModelUtil
				.getAllEObjectsOfTypeInCorrespondences(this.getCorrespondenceModel(), Package.class);
		for (final Package currentPackage : packages) {
			if (currentPackage.getName().equals(name)) {
				return currentPackage;
			}
		}
		throw new RuntimeException("Could not find package with name " + name);
	}

	protected OperationSignature addMethodToInterfaceWithCorrespondence(final String interfaceName) throws Throwable {
		final String methodName = Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME;
		return this.addMethodToInterfaceWithCorrespondence(interfaceName, methodName);
	}

	protected OperationSignature addMethodToInterfaceWithCorrespondence(final String interfaceName,
			final String methodName) throws Throwable, JavaModelException {
		final String methodString = "\nvoid " + methodName + "();\n";
		final ICompilationUnit cu = CompilationUnitManipulatorHelper.addMethodToCompilationUnit(interfaceName,
				methodString, this.getCurrentTestProject(), this);
		return this.findOperationSignatureForJaMoPPMethodInCompilationUnit(methodName, interfaceName, cu);
	}

	protected ResourceDemandingSEFF addClassMethodToClassThatOverridesInterfaceMethod(final String className,
			final String methodName) throws Throwable {
		final String methodString = "\n\tpublic void " + methodName + " () {\n\t}\n";
		final ICompilationUnit icu = CompilationUnitManipulatorHelper.addMethodToCompilationUnit(className,
				methodString, this.getCurrentTestProject(), this);
		final Method jaMoPPMethod = this.findJaMoPPMethodInICU(icu, methodName);
		final ClassMethod classMethod = (ClassMethod) jaMoPPMethod;
		return claimOne(CorrespondenceModelUtil.getCorrespondingEObjectsByType(this.getCorrespondenceModel(),
				classMethod, ResourceDemandingSEFF.class));
	}

	protected OperationSignature findOperationSignatureForJaMoPPMethodInCompilationUnit(final String methodName,
			final String interfaceName, final ICompilationUnit cu) throws Throwable {
		final VURI vuri = VURI.getInstance(cu.getResource());
		final Classifier classifier = this.getJaMoPPClassifierForVURI(vuri);
		final Interface jaMoPPInterface = (Interface) classifier;
		for (final Method jaMoPPMethod : jaMoPPInterface.getMethods()) {
			if (jaMoPPMethod.getName().equals(methodName)) {
				return claimOne(CorrespondenceModelUtil.getCorrespondingEObjectsByType(this.getCorrespondenceModel(),
						jaMoPPMethod, OperationSignature.class));
			}
		}
		logger.warn("No JaMoPP method with name " + methodName + " found in " + interfaceName);
		return null;
	}

	protected OperationSignature renameMethodInClassWithName(final String className, final String methodName)
			throws Throwable {
		final ICompilationUnit cu = CompilationUnitManipulatorHelper.findICompilationUnitWithClassName(className,
				this.getCurrentTestProject());
		final IMethod iMethod = cu.getType(className).getMethod(methodName, null);
		final int offset = iMethod.getNameRange().getOffset();
		final int length = iMethod.getNameRange().getLength();
		final String newMethodName = methodName + Pcm2JavaTestUtils.RENAME;
		final ReplaceEdit replaceEdit = new ReplaceEdit(offset, length, newMethodName);
		editCompilationUnit(cu, replaceEdit);
		return this.findOperationSignatureForJaMoPPMethodInCompilationUnit(newMethodName, className, cu);
	}

	protected void assertDataTypeName(final TypeReference typeReference, final DataType pcmDataType) {
		final String jaMoPPTypeName = getNameFromJaMoPPType(typeReference);
		final String pcmTypeName = this.getNameFromPCMDataType(pcmDataType);
		assertEquals("The name of the PCM datatype does not equal the JaMoPP type name", jaMoPPTypeName, pcmTypeName);
	}

	protected String getNameFromPCMDataType(final DataType pcmDataType) {
		if (null == pcmDataType) {
			return "void";
		} else if (pcmDataType instanceof CollectionDataType) {
			return ((CollectionDataType) pcmDataType).getEntityName();
		} else if (pcmDataType instanceof CompositeDataType) {
			return ((CompositeDataType) pcmDataType).getEntityName();
		} else if (pcmDataType instanceof PrimitiveDataType) {
			return this.getNameFromPrimitveDataType((PrimitiveDataType) pcmDataType);
		}
		throw new RuntimeException("getNameFromPCMDataType failed");
	}

	private String getNameFromPrimitveDataType(final PrimitiveDataType pcmDataType) {
		switch (pcmDataType.getType()) {
		case BOOL:
			return "boolean";
		case CHAR:
			return "char";
		case BYTE:
			return "byte";
		case DOUBLE:
			return "double";
		case INT:
			return "int";
		case LONG:
			return "long";
		case STRING:
			return "String";
		}
		throw new RuntimeException("getNameFromPrimitveDataType");
	}

	protected Parameter addParameterToSignature(final String interfaceName, final String methodName,
			final String typeName, final String parameterName, final String[] parameterTypeSignatures)
			throws Throwable {
		final ICompilationUnit icu = CompilationUnitManipulatorHelper.findICompilationUnitWithClassName(interfaceName,
				this.getCurrentTestProject());
		final IMethod iMethod = icu.getType(interfaceName).getMethod(methodName, parameterTypeSignatures);
		final String parameterStr = typeName + " " + parameterName;
		return this.insertParameterIntoSignature(methodName, parameterName, icu, iMethod, parameterStr);
	}

	protected Parameter insertParameterIntoSignature(final String methodName, final String parameterName,
			final ICompilationUnit icu, final IMethod iMethod, final String parameterStr)
			throws JavaModelException, Throwable {
		final int offset = iMethod.getSourceRange().getOffset() + iMethod.getSourceRange().getLength() - 2;
		final InsertEdit insertEdit = new InsertEdit(offset, parameterStr);
		editCompilationUnit(icu, insertEdit);
		final ConcreteClassifier concreateClassifier = this
				.getJaMoPPClassifierForVURI(VURI.getInstance(icu.getResource()));
		final Method jaMoPPMethod = (Method) concreateClassifier.getMembersByName(methodName).get(0);
		final org.emftext.language.java.parameters.Parameter jaMoPPParam = this
				.getJaMoPPParameterFromJaMoPPMethod(jaMoPPMethod, parameterName);
		return claimOne(CorrespondenceModelUtil.getCorrespondingEObjectsByType(this.getCorrespondenceModel(),
				jaMoPPParam, Parameter.class));
	}

	protected OperationSignature addReturnTypeToSignature(final String interfaceName, final String methodName,
			final String typeName, String oldTypeName) throws Throwable {
		if (null == oldTypeName) {
			oldTypeName = "void";
		}
		final ICompilationUnit icu = CompilationUnitManipulatorHelper.findICompilationUnitWithClassName(interfaceName,
				this.getCurrentTestProject());
		final IMethod iMethod = icu.getType(interfaceName).getMethod(methodName, null);
		final String retTypeStr = typeName;
		final int offset = iMethod.getSourceRange().getOffset()
				+ iMethod.getSourceRange().toString().indexOf(oldTypeName);
		final ReplaceEdit replaceEdit = new ReplaceEdit(offset + 1, oldTypeName.length() + 1, retTypeStr);
		editCompilationUnit(icu, replaceEdit);
		final ConcreteClassifier concreateClassifier = this
				.getJaMoPPClassifierForVURI(VURI.getInstance(icu.getResource()));
		final Method jaMoPPMethod = (Method) concreateClassifier.getMembersByName(methodName).get(0);
		return claimOne(CorrespondenceModelUtil.getCorrespondingEObjectsByType(this.getCorrespondenceModel(),
				jaMoPPMethod, OperationSignature.class));
	}

	protected org.emftext.language.java.parameters.Parameter getJaMoPPParameterFromJaMoPPMethod(
			final Method jaMoPPMethod, final String parameterName) {
		for (final org.emftext.language.java.parameters.Parameter jaMoPPParam : jaMoPPMethod.getParameters()) {
			if (jaMoPPParam.getName().equals(parameterName)) {
				return jaMoPPParam;
			}
		}
		throw new RuntimeException(
				"JaMoPP param with name " + parameterName + " not found in method " + jaMoPPMethod.getName());
	}

	protected org.emftext.language.java.parameters.Parameter findJaMoPPParameterInICU(final ICompilationUnit icu,
			final String interfaceName, final String methodName, final String parameterName) {
		final Method method = this.findJaMoPPMethodInICU(icu, methodName);
		return this.getJaMoPPParameterFromJaMoPPMethod(method, parameterName);
	}

	protected Method findJaMoPPMethodInICU(final ICompilationUnit icu, final String methodName) {
		final ConcreteClassifier cc = this.getJaMoPPClassifierForVURI(VURI.getInstance(icu.getResource()));
		final List<Member> jaMoPPMethods = cc.getMembersByName(methodName);
		for (final Member member : jaMoPPMethods) {
			if (member instanceof Method && member.getName().equals(methodName)) {
				return (Method) member;
			}
		}
		throw new RuntimeException("No method with name " + methodName + " found in " + icu);
	}

	protected String getNameFromPCMPrimitiveDataType(final PrimitiveDataType primitiveDataType) {
		return primitiveDataType.getType().getName();
	}

	protected Package getDatatypesPackage() throws Throwable {
		return this.getPackageWithNameFromCorrespondenceModel("datatypes");
	}

	protected IMethod findIMethodByName(final String typeName, final String methodName, final ICompilationUnit icu)
			throws JavaModelException {
		final IType type = icu.getAllTypes()[0];
		logger.info(type.getMethods());
		for (final IMethod method : type.getMethods()) {
			if (method.getElementName().equals(methodName)) {
				return method;
			}
		}
		throw new RuntimeException("Method not " + methodName + " not found in classifier " + typeName);
	}

	protected IField findIFieldByName(final String className, final String fieldName, final ICompilationUnit icu)
			throws JavaModelException {
		final IType type = icu.getType(className);
		return type.getField(fieldName);
	}

	protected String addPackageAndImplementingClass(final String componentName)
			throws CoreException, IOException, InterruptedException {
		this.getUserInteractor().addNextSelections(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());
		final Package mediaStorePackage = this.createPackageWithPackageInfo(Pcm2JavaTestUtils.REPOSITORY_NAME,
				componentName);
		this.getUserInteractor().addNextSelections(Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection());

		final String implementingClassName = componentName + "Impl";
		this.addClassInPackage(mediaStorePackage, BasicComponent.class, implementingClassName);
		return implementingClassName;
	}

	protected OperationProvidedRole addImplementsCorrespondingToOperationProvidedRoleToClass(final String className,
			final String implementingInterfaceName) throws CoreException {
		// TODO Somehow, we have to wait some time here.
		// If we do not wait, some effect of the previous interface creation has
		// not finished and thus
		// adding the implements statement to the class will result in an
		// unsuccessful proxy resolution
		// for the implemented interface, which means that no correspondence
		// gets created.
		// Even forcing a reload of the interface and class models in the VSUM
		// does not have any positive effect.
		// ADDITION: Using Maven, changes run properly without the sleep, so it
		// is removed by now.
		// In Eclipse, it does not work without the sleep.
		/*try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}*/
		final ICompilationUnit classCompilationUnit = CompilationUnitManipulatorHelper
				.findICompilationUnitWithClassName(className, this.getCurrentTestProject());
		this.importCompilationUnitWithName(implementingInterfaceName, classCompilationUnit);

		final IType classType = classCompilationUnit.getType(className);
		final String newSource = " implements " + implementingInterfaceName;
		int offset = classType.getSourceRange().getOffset();
		final int firstBracket = classType.getSource().indexOf("{");
		offset = offset + firstBracket - 1;
		final InsertEdit insertEdit = new InsertEdit(offset, newSource);
		editCompilationUnit(classCompilationUnit, insertEdit);
		final org.emftext.language.java.classifiers.Class jaMoPPClass = (org.emftext.language.java.classifiers.Class) this
				.getJaMoPPClassifierForVURI(VURI.getInstance(classCompilationUnit.getResource()));
		final EList<TypeReference> classImplements = jaMoPPClass.getImplements();
		logger.debug("Found implements: " + classImplements);
		for (final TypeReference implementsReference : classImplements) {
			logger.debug("Implements data: " + implementsReference.getTarget());
			final Set<OperationProvidedRole> correspondingEObjects = CorrespondenceModelUtil
					.getCorrespondingEObjectsByType(this.getCorrespondenceModel(), implementsReference,
							OperationProvidedRole.class);
			logger.debug("Corresponding provided roles: " + correspondingEObjects);
			if (null != correspondingEObjects && 0 < correspondingEObjects.size()) {
				return correspondingEObjects.iterator().next();
			}
		}
		throw new RuntimeException("Could not find an operation provided role for the newly created implements");
	}

	protected void importCompilationUnitWithName(final String implementingInterfaceName,
			final ICompilationUnit classCompilationUnit) throws JavaModelException {
		final ICompilationUnit interfaceCompilationUnit = CompilationUnitManipulatorHelper
				.findICompilationUnitWithClassName(implementingInterfaceName, this.getCurrentTestProject());
		final String namespace = interfaceCompilationUnit.getType(implementingInterfaceName).getFullyQualifiedName();
		classCompilationUnit.createImport(namespace, null, null);
	}

	protected <T> T addFieldToClassWithName(final String className, final String fieldType, final String fieldName,
			final Class<T> correspondingType) throws Throwable {
		final ICompilationUnit icu = CompilationUnitManipulatorHelper.findICompilationUnitWithClassName(className,
				this.getCurrentTestProject());
		if (!fieldType.equals("String")) {
			this.importCompilationUnitWithName(fieldType, icu);
		}
		final IType iClass = icu.getAllTypes()[0];
		final int offset = CompilationUnitManipulatorHelper.getOffsetForClassifierManipulation(iClass);
		final String fieldStr = "private " + fieldType + " " + fieldName + ";";
		final InsertEdit insertEdit = new InsertEdit(offset, fieldStr);
		editCompilationUnit(icu, insertEdit);
		final Field jaMoPPField = this.getJaMoPPFieldFromClass(icu, fieldName);
		if (correspondingType == null) {
			return null;
		}
		return claimOne(CorrespondenceModelUtil.getCorrespondingEObjectsByType(this.getCorrespondenceModel(),
				jaMoPPField, correspondingType));
	}

	protected Field getJaMoPPFieldFromClass(final ICompilationUnit icu, final String fieldName) {
		final ConcreteClassifier cc = this.getJaMoPPClassifierForVURI(VURI.getInstance(icu.getResource()));
		final Field field = (Field) cc.getMembersByName(fieldName).get(0);
		return field;
	}

	/**
	 * Create change for annotation manually and notify change synchronizer
	 *
	 * @param annotableAndModifiable
	 * @throws Throwable
	 */
	protected void addAnnotationToMember(final AnnotableAndModifiable annotableAndModifiable,
			final String annotationName) throws Throwable {
		final JavaInsertEReference<EObject, EObject> createChange = ReferenceFactory.eINSTANCE
				.createJavaInsertEReference();
		// createChange.setIsCreate(true);
		createChange.setOldAffectedEObject(annotableAndModifiable);
		final AnnotationInstance newAnnotation = AnnotationsFactory.eINSTANCE.createAnnotationInstance();
		final Classifier classifier = this.createClassifierFromName(annotationName);
		newAnnotation.setAnnotation(classifier);
		annotableAndModifiable.getAnnotationsAndModifiers().add(newAnnotation);
		createChange.setAffectedEObject(EcoreUtil.copy(annotableAndModifiable));
		final EReference containingReference = (EReference) newAnnotation.eContainingFeature();
		@SuppressWarnings("unchecked")
		final int index = ((EList<EObject>) createChange.getAffectedEObject().eGet(containingReference))
				.indexOf(newAnnotation);
		createChange.setAffectedFeature(containingReference);
		createChange.setIndex(index);
		createChange.setNewValue(newAnnotation);
		final VURI vuri = VURI.getInstance(annotableAndModifiable.eResource());
		final ConcreteChange change = VitruviusChangeFactory.getInstance().createConcreteChangeWithVuri(createChange, vuri);
		getVirtualModel().propagateChange(change);
	}

	// add Annotation via the framework
	protected <T> T addAnnotationToClassifier(final AnnotableAndModifiable annotable, final String annotationName,
			final Class<T> classOfCorrespondingObject, final String className) throws Throwable {
		final ICompilationUnit cu = CompilationUnitManipulatorHelper.findICompilationUnitWithClassName(className,
				this.getCurrentTestProject());
		final IType type = cu.getType(className);
		final int offset = CompilationUnitManipulatorHelper.getOffsetForAddingAnntationToClass(type);
		final InsertEdit insertEdit = new InsertEdit(offset, "@" + annotationName);
		editCompilationUnit(cu, insertEdit);
		final Set<T> eObjectsByType = CorrespondenceModelUtil
				.getCorrespondingEObjectsByType(this.getCorrespondenceModel(), annotable, classOfCorrespondingObject);
		return claimOne(eObjectsByType);
	}

	// add Annotation via the framework
	protected <T> T addAnnotationToField(final String fieldName, final String annotationName,
			final Class<T> classOfCorrespondingObject, final String className) throws Throwable {
		final ICompilationUnit cu = CompilationUnitManipulatorHelper.findICompilationUnitWithClassName(className,
				this.getCurrentTestProject());
		final IType type = cu.getType(className);
		final int offset = CompilationUnitManipulatorHelper.getOffsetForAddingAnntationToField(type, fieldName);
		final InsertEdit insertEdit = new InsertEdit(offset, "@" + annotationName + " ");
		editCompilationUnit(cu, insertEdit);
		final Field jaMoPPField = this.getJaMoPPFieldFromClass(cu, fieldName);
		final Set<T> eObjectsByType = CorrespondenceModelUtil
				.getCorrespondingEObjectsByType(this.getCorrespondenceModel(), jaMoPPField, classOfCorrespondingObject);
		return claimOne(eObjectsByType);
	}

	private Classifier createClassifierFromName(final String annotationName) {
		final org.emftext.language.java.classifiers.Class jaMoPPClass = ClassifiersFactory.eINSTANCE.createClass();
		jaMoPPClass.setName(annotationName);
		return jaMoPPClass;
	}

	protected void assertCorrespondingSEFF(final ResourceDemandingSEFF correspondingSeff, String methodName)
			throws Throwable {
		final ClassMethod jaMoPPMethod = claimOne(CorrespondenceModelUtil
				.getCorrespondingEObjectsByType(this.getCorrespondenceModel(), correspondingSeff, ClassMethod.class));
		assertEquals(jaMoPPMethod.getName(), methodName);
	}

	protected void assertOperationInterface(final Repository repo, final OperationInterface opIf,
			final String expectedName) {
		assertTrue("The created operation interface is null", null != opIf);
		assertEquals("OperationInterface name does not equals the expected interface Name.", opIf.getEntityName(),
				expectedName);
		assertEquals("The created operation interface is not in the repository", repo.getId(),
				opIf.getRepository__Interface().getId());
	}

}
