package tools.vitruv.applications.pcmjava.util.pcm2java

import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.Package

import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.applications.pcmjava.util.PcmJavaUtils
import static tools.vitruv.domains.java.util.JavaModificationUtil.*

abstract class Pcm2JavaUtils extends PcmJavaUtils {
	private static val Logger logger = Logger.getLogger(Pcm2JavaUtils.simpleName)

	private new() {
	}

	def static Package getContainingPackageFromCorrespondenceModel(Classifier classifier,
		CorrespondenceModel correspondenceModel) {
		var namespace = classifier.containingCompilationUnit.namespacesAsString
		if (namespace.endsWith("$") || namespace.endsWith(".")) {
			namespace = namespace.substring(0, namespace.length - 1)
		}
		val finalNamespace = namespace
		var Set<Package> packagesWithCorrespondences = correspondenceModel.
			getAllEObjectsOfTypeInCorrespondences(Package)
		val packagesWithNamespace = packagesWithCorrespondences.filter [ pack |
			finalNamespace.equals(pack.namespacesAsString + pack.name)
		]
		if (null !== packagesWithNamespace && 0 < packagesWithNamespace.size &&
			null !== packagesWithNamespace.iterator.next) {
			return packagesWithNamespace.iterator.next
		}
		return null;
	}

	def public static createJaMoPPMethod(String content) {
		try {
			val String cuContent = "class Dummy{" + content + "}"
			val String name = "vitruvius.meta/src/dummy.java";
			val cu = createJavaRoot(name, cuContent) as CompilationUnit
			val method = cu.classifiers.get(0).methods.get(0)
			EcoreUtil.remove(method)
			return method
		} catch (Throwable t) {
			logger.warn("Exception during createJaMoPPMethod with content " + content + " Exception: " + t)
			return null;
		}
	}

}
