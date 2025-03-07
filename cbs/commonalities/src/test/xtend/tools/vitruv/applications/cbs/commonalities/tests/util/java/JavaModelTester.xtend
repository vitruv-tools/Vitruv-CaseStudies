package tools.vitruv.applications.cbs.commonalities.tests.util.java

import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.Package
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelTester
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestHelper

class JavaModelTester extends DomainModelTester {

	val extension JavaTestHelper javaTestHelper = new JavaTestHelper(vitruvApplicationTestAdapter)

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override createAndSynchronizeModel(EObject rootObject) {
		switch (rootObject) {
			Package: {
				rootObject.createAndSynchronizeJavaPackage
			}
			CompilationUnit: {
				rootObject.createAndSynchronizeJavaCompilationUnit
			}
			default: {
				throw new IllegalStateException("Unhandled Java root object: " + rootObject)
			}
		}
	}

	override assertModelExists(EObject rootObject) {
		switch (rootObject) {
			Package: {
				rootObject.assertJavaPackageExists
			}
			CompilationUnit: {
				rootObject.assertJavaCompilationUnitExists
			}
			default: {
				throw new IllegalStateException("Unhandled Java root object: " + rootObject)
			}
		}
	}
}
