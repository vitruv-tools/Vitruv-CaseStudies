package tools.vitruv.applications.pcmumlclassjava.tests

import java.util.List
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.Package
import tools.vitruv.applications.pcmumlclass.CombinedPcmToUmlClassReactionsChangePropagationSpecification
import tools.vitruv.applications.pcmumlclass.CombinedUmlClassToPcmReactionsChangePropagationSpecification
import tools.vitruv.applications.pcmumlclass.tests.PcmUmlClassApplicationTest
import tools.vitruv.applications.util.temporary.java.JavaVisibility
import tools.vitruv.domains.java.JavaDomainProvider
import tools.vitruv.domains.java.util.JavaPersistenceHelper
import tools.vitruv.domains.pcm.PcmDomainProvider
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.framework.userinteraction.UserInteractionFactory

import static tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.*
import org.junit.jupiter.api.BeforeEach
import java.nio.file.Path
import tools.vitruv.applications.umljava.UmlToJavaChangePropagationSpecification
import tools.vitruv.applications.umljava.JavaToUmlChangePropagationSpecification

abstract class PcmUmlClassJavaApplicationTest extends PcmUmlClassApplicationTest {
	
	override protected getChangePropagationSpecifications() {
		return #[
			new CombinedPcmToUmlClassReactionsChangePropagationSpecification, 
			new CombinedUmlClassToPcmReactionsChangePropagationSpecification,
			new UmlToJavaChangePropagationSpecification,
			new JavaToUmlChangePropagationSpecification
		]  
	}
	
	@BeforeEach
	def void patchDomains() {
		new PcmDomainProvider().domain.enableTransitiveChangePropagation
		new UmlDomainProvider().domain.enableTransitiveChangePropagation
		new JavaDomainProvider().domain.enableTransitiveChangePropagation
	}
	
	protected def setInteractiveUserInteractor(){
		val userInteractor = UserInteractionFactory.instance.createDialogUserInteractor()
		virtualModel.userInteractor = userInteractor
	}
	
	def protected getJavaPackage(String qualifiedPackageName){
		val namespaces = qualifiedPackageName.split(".")
		return getJavaPackage(namespaces)
	}
	
	def protected getJavaPackage(String ... namespaces){
		val packageFileName = JavaPersistenceHelper.buildJavaFilePath(JavaPersistenceHelper.packageInfoClassName + ".java", namespaces)
		val resource = resourceAt(Path.of(packageFileName))
		if (
			resource !== null 
			&& resource.contents.head !== null 
			&& resource.contents.head instanceof Package
		){
			val package = resource.contents.head as Package
			return package
		}
		return null
	}
	
	/**
	 * Roundabout way to retrieve the jClass via uml correspondences because it fails when loading the CU directly.
	 */
	def protected getJavaClassFromCompilationUnit(String name, String ... namespaces){
		// This roundabout way works to retrieve the read-only instance of the VSUM but still fails when trying to load the CU from the URI.
//		val javaPkg = getJavaPackageElement(namespaces)
//		val umlPkg = CorrespondenceModelUtil.getCorrespondingEObjectsByType(correspondenceModel, javaPkg, org.eclipse.uml2.uml.Package).head
//		val umlCompImpl = umlPkg.packagedElements.filterNull.filter(org.eclipse.uml2.uml.Class)
//			.findFirst[it.name.toLowerCase.contains(javaPkg.name.toLowerCase)]
//		val javaCompImpl = CorrespondenceModelUtil.getCorrespondingEObjectsByType(correspondenceModel, umlCompImpl, org.emftext.language.java.classifiers.Class).head
//		// here it fails, because the CU cannot be loaded into the view-ResourceSet
//		// because the UUID resolver fails on trying to register the 'Object extends Object'-ClassifierReference 
//		val modifiableJavaCompImpl = getModelElement(EcoreUtil.getURI(javaCompImpl)) as org.emftext.language.java.classifiers.Class
//		return modifiableJavaCompImpl
		
		// fails because the compilationUnits reference is always empty on load
//		val package = getJavaPackageElement(namespaces)
//		val cu = package.compilationUnits.findFirst[it.name.contains(name)] // "endsWith" because the name might by qualified
//		return cu.containedClass

		// fails because the CU needs to load java.lang.Object 
		// and UUID resolver fails on trying to register the 'Object extends Object'-ClassifierReference 
		val cuFileName = JavaPersistenceHelper.buildJavaFilePath(name + ".java", namespaces)
		val resource = resourceAt(Path.of(cuFileName))
		if (
			resource !== null 
			&& resource.contents.head !== null 
			&& resource.contents.head instanceof Package
		){
			val cu = resource.contents.head as CompilationUnit
			return cu.containedClass
		}
		return null
	}
	
	def protected createCompilationUnitInPackage(Package containingPackage, String cuName){
		val List<String> namespace = if(containingPackage === null) #[] else (containingPackage.namespaces + #[containingPackage.name]).toList
		val compilationUnit = ContainersFactory.eINSTANCE.createCompilationUnit => [
			name = '''«name».java'''
			namespaces += namespace
		]
//        cu.name = (namespace + #[cuName]).join(".")  + ".java"
        containingPackage.compilationUnits += compilationUnit
        resourceAt(Path.of(JavaPersistenceHelper.buildJavaFilePath(compilationUnit))).startRecordingChanges => [
			contents += compilationUnit
		]
		propagate
        return compilationUnit
	}
	
	/**
	 * Implicitly creates the necessary CompilationUnit and propagates the changes. 
	 */
	def protected createJavaClassInPackage(
		Package containingPackage,
		String cName, JavaVisibility visibility, boolean abstr, boolean fin
	){
		val cu = createCompilationUnitInPackage(containingPackage, cName)
		val class = createJavaClass(cName, visibility, abstr, fin)
		cu.classifiers += class
		propagate
		return class
	}
	
	/**
	 * Implicitly creates the necessary CompilationUnit and propagates the changes. 
	 */
	def protected createJavaInterfaceInPackage(
		Package containingPackage,
		String cName, List<Interface> superInterfaces
	){
		val cu = createCompilationUnitInPackage(containingPackage, cName)
		val interface = createJavaInterface(cName, superInterfaces)
		cu.classifiers += interface
		propagate
		return interface
	}
	
//	############# copied from tools.vitruv.applications.umljava.java2uml.Java2UmlTransformationTest
    
    /**
     * Creates a new java package and synchronizes it as root model.
     * 
     * @param name the name of the package
     * @param superPackage the package that contains the new package. Can be null if it is the default package.
     */
    def protected createJavaPackageAsModel(String name, Package superPackage) {
        val jPackage = createJavaPackage(name, superPackage)
        resourceAt(Path.of(JavaPersistenceHelper.buildJavaFilePath(jPackage))).startRecordingChanges => [
			contents += jPackage
		]
		propagate
        return jPackage
    }

}