package tools.vitruv.applications.pcmjava.javaeditor.util;

import static org.eclipse.emf.common.util.URI.createPlatformResourceURI;
import static tools.vitruv.applications.pcmjava.javaeditor.util.BlockingProgressMonitor.acceptSynchronousThrowing;
import static tools.vitruv.applications.pcmjava.javaeditor.util.BlockingProgressMonitor.applySynchronousThrowing;

import java.nio.file.Path;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.jdt.core.refactoring.descriptors.RenameJavaElementDescriptor;
import org.eclipse.jdt.internal.core.manipulation.StubUtility;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringContribution;
import org.eclipse.ltk.core.refactoring.RefactoringCore;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.TextEdit;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.JavaRoot;
import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.function.ThrowingConsumer;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil;

public class JavaEditorManipulationUtil {
	private static final String SRC_FOLDER = "src";
	
	private final JavaEditorView javaEditorView;
	private final Path projectPath;
	private IPackageFragmentRoot rootPackageFragment;
	
	JavaEditorManipulationUtil(JavaEditorView javaEditorView, Path projectPath) {
		this.javaEditorView = javaEditorView;
		this.projectPath = projectPath;
	}
	
	public void createClass(String name, Package containingPackage, ThrowingConsumer<ICompilationUnit> initialization) throws CoreException {
		createCompilationUnit(name, "class", containingPackage, initialization);
	}
	
	public void createInterface(String name, Package containingPackage, ThrowingConsumer<ICompilationUnit> initialization) throws CoreException {
		createCompilationUnit(name, "interface", containingPackage, initialization);
	}
	
	private void createCompilationUnit(String name, String type, Package containingPackage, ThrowingConsumer<ICompilationUnit> initialization) throws CoreException {
		IPackageFragment packageFragment = getPackageFragment(containingPackage);
		ICompilationUnit compilationUnit = createEmptyCompilationUnit(packageFragment, type, name);
		if (initialization != null) {
			try {
				initialization.accept(compilationUnit);
			}
			catch (Throwable t) {
				throw new IllegalStateException(t);
			}
		}
		javaEditorView.reloadResource(getFileUri(compilationUnit));
	}
	
	public ICompilationUnit claimCompilationUnit(String name, Package containingPackage) throws CoreException {
		IPackageFragment packageFragment = getPackageFragment(containingPackage);
		for (IJavaElement javaElement : packageFragment.getChildren()) {
			if (javaElement instanceof ICompilationUnit && javaElement.getElementName().equals(name + ".java")) {
				return (ICompilationUnit)javaElement;
			}
		}
		throw new IllegalStateException("Could not find class " + name + " in package " + containingPackage);
	}
	
	public void editCompilationUnit(final ICompilationUnit compilationUnit, final TextEdit... edits) throws JavaModelException {
		URI originalUri = getFileUri(compilationUnit);
		editCompilationUnitInternal(compilationUnit, edits);
		URI changedUri = getFileUri(compilationUnit);
		if (originalUri != changedUri) {
			javaEditorView.moveResource(originalUri, changedUri);
		}
		javaEditorView.reloadResource(changedUri);
	}
	
	/**
	 * Modifies the compilation unit without notifying the java editor view about resource changes.
	 */
	private void editCompilationUnitInternal(final ICompilationUnit compilationUnit, final TextEdit... edits) throws JavaModelException {
		acceptSynchronousThrowing(monitor -> compilationUnit.becomeWorkingCopy(monitor));
		for (final TextEdit edit : edits) {
			acceptSynchronousThrowing(monitor -> compilationUnit.applyTextEdit(edit, monitor));
		}
		saveCompilationUnit(compilationUnit);
	}
	
	private void saveCompilationUnit(ICompilationUnit compilationUnit) throws JavaModelException {
		acceptSynchronousThrowing(monitor -> compilationUnit.reconcile(ICompilationUnit.NO_AST, false, null, monitor));
		acceptSynchronousThrowing(monitor -> compilationUnit.commitWorkingCopy(true, monitor));
		compilationUnit.discardWorkingCopy();
		acceptSynchronousThrowing(monitor -> compilationUnit.save(monitor, true));
	}
	
	public void renamePackage(final Package packageToRename, final String newName) throws CoreException {
		final Resource resource = packageToRename.eResource();
		URI oldLocation = resource.getURI();
		final IFile iFile = getIFileForUri(resource.getURI(), projectPath);
		IPath iPath = iFile.getProjectRelativePath();
		iPath = iPath.removeLastSegments(1);
		final String newQualifiedName = packageToRename.getNamespacesAsString() + newName;
		final IFolder iFolder = iFile.getProject().getFolder(iPath);
		final IJavaElement javaPackage = JavaCore.create(iFolder);
		String packageFile = String.join("/", packageToRename.getNamespaces());
		packageFile = packageFile + "/" + newName + "/package-info.java";
		refactorRenameJavaElement(newQualifiedName, javaPackage, IJavaRefactorings.RENAME_PACKAGE);
		javaEditorView.moveResource(oldLocation, URIUtil.createFileURI(projectPath.resolve(SRC_FOLDER).resolve(packageFile).toFile()));
	}
	
	public void addImportToCompilationUnit(ICompilationUnit compilationUnit, ICompilationUnit compilationUnitToImport, String classifierToImport) throws JavaModelException {
		acceptSynchronousThrowing(monitor -> compilationUnit.becomeWorkingCopy(monitor));
		final String namespace = compilationUnitToImport.getType(classifierToImport).getFullyQualifiedName();
		acceptSynchronousThrowing(monitor -> compilationUnit.createImport(namespace, null, monitor));
		saveCompilationUnit(compilationUnit);
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
		final Refactoring refactoring = desc.createRefactoring(status);
		acceptSynchronousThrowing(monitor -> refactoring.checkInitialConditions(monitor));
		acceptSynchronousThrowing(monitor -> refactoring.checkFinalConditions(monitor));
		Change change = applySynchronousThrowing(monitor -> refactoring.createChange(monitor));
		acceptSynchronousThrowing(monitor -> change.perform(monitor));
	}
	
	private <T extends JavaRoot> T getJaMoPPRootForURI(final URI uri) {
		final Resource resource = new ResourceSetImpl().getResource(uri, true);
		// unchecked is OK for the test.
		@SuppressWarnings("unchecked")
		final T javaRoot = (T) resource.getContents().get(0);
		return javaRoot;
	}
	
	protected ConcreteClassifier getJaMoPPClassifierForURI(final URI uri) {
		final CompilationUnit cu = this.getJaMoPPRootForURI(uri);
		final Classifier jaMoPPClassifier = cu.getClassifiers().get(0);
		return (ConcreteClassifier) jaMoPPClassifier;
	}
	
	private ICompilationUnit createEmptyCompilationUnit(IPackageFragment packageFragment, String typeName, String name) throws JavaModelException {
		String lineDelimiter = StubUtility.getLineDelimiterUsed(packageFragment.getJavaProject());
		ICompilationUnit compilationUnit = applySynchronousThrowing(monitor -> packageFragment.createCompilationUnit(name + ".java", "", false, null));
		InsertEdit edit = new InsertEdit(0, "package " + packageFragment.getElementName() + ";" + lineDelimiter
						+ lineDelimiter + "public " + typeName + " " + name + " { }");
		editCompilationUnitInternal(compilationUnit, edit);
		return compilationUnit;
	}
	
	private static IFile getIFileForUri(URI uri, Path projectPath) {
		if (uri.isPlatform()) {
			return URIUtil.getIFileForEMFUri(uri);
		}
		URI base = URIUtil.createFileURI(projectPath.toFile());
		String path = uri.deresolve(base).path();
		path = path.substring(path.indexOf("/") + 1);
		URI platformUri = createPlatformResourceURI(projectPath.getFileName().resolve(path).normalize() + "/",
				true);
		return getIFileForUri(platformUri, projectPath);
	}
	
	private URI getUriForElementInPackage(IPackageFragment packageFragment, String elementName) {
		String uriString = packageFragment.getResource().getFullPath().toString() + "/" + elementName + ".java";
		return URI.createPlatformResourceURI(uriString, true);
	}
	
	private URI getFileUri(ICompilationUnit compilationUnit) throws JavaModelException {
		String[] namespace = compilationUnit.getPackageDeclarations()[0].getElementName().split("\\.");
		Path path = projectPath.resolve(SRC_FOLDER);
		for (String partialNamespace : namespace) {
			path = path.resolve(partialNamespace);
		}
		return URIUtil.createFileURI(path.resolve(compilationUnit.getElementName()).toFile());
	}
	
	private IPackageFragmentRoot getRootPackage() throws CoreException {
		if (rootPackageFragment != null) {
			return rootPackageFragment;
		}
		
		IFolder sourceFolder = javaEditorView.getEclipseProject().getFolder(SRC_FOLDER);
		if (!sourceFolder.exists()) {
			acceptSynchronousThrowing(monitor -> sourceFolder.create(true, true, monitor));
		}
		rootPackageFragment = javaEditorView.getJavaProject().getPackageFragmentRoot(sourceFolder);
		return rootPackageFragment;
	}
	
	private IPackageFragment getPackageFragment(Package somePackage) throws CoreException {
		String packageNamespace = somePackage.getNamespacesAsString() + somePackage.getName();
		for (IJavaElement javaElement : getRootPackage().getChildren()) {
			if (javaElement instanceof IPackageFragment && javaElement.getElementName().equals(packageNamespace)) {
				return (IPackageFragment)javaElement;
			}
		}
		throw new IllegalStateException("No package fragment found for JaMoPP package " + somePackage);
	}
}
