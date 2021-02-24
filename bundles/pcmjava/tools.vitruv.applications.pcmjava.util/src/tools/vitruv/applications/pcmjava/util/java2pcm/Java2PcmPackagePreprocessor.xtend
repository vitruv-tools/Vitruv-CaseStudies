package tools.vitruv.applications.pcmjava.util.java2pcm

import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.resource.ResourceSet
import org.emftext.language.java.containers.Package
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.domains.java.echange.feature.attribute.JavaReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.description.ConcreteChange
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.processing.impl.AbstractChangePropagationSpecification
import tools.vitruv.domains.java.JavaDomainProvider
import tools.vitruv.domains.pcm.PcmDomainProvider
import tools.vitruv.framework.util.command.ResourceAccess

class Java2PcmPackagePreprocessor extends AbstractChangePropagationSpecification {
	new() {
		super(new JavaDomainProvider().domain, new PcmDomainProvider().domain)
	}

	override doesHandleChange(TransactionalChange change, CorrespondenceModel correspondenceModel) {
		if (change instanceof ConcreteChange) {
			val eChange = change.getEChange
			return eChange instanceof InsertRootEObject<?> ||
				eChange instanceof JavaReplaceSingleValuedEAttribute<?, ?>
		}
		return false
	}

	private def void prepareRenamePackageInfos(JavaReplaceSingleValuedEAttribute<?, ?> updateSingleValuedEAttribute,
		VURI vuri) {
		if (updateSingleValuedEAttribute.getOldAffectedEObject() instanceof Package &&
			updateSingleValuedEAttribute.getAffectedEObject() instanceof Package) {
			val Package oldPackage = updateSingleValuedEAttribute.getOldAffectedEObject() as Package
			val Package newPackage = updateSingleValuedEAttribute.getAffectedEObject() as Package
			this.attachPackageToResource(oldPackage, vuri)
			var String newVURIKey = vuri.toString()
			val String oldPackagePath = oldPackage.getName().replace(".", "/")
			val String newPackagePath = newPackage.getName().replace(".", "/")
			newVURIKey = newVURIKey.replace(oldPackagePath, newPackagePath)
			val VURI newVURI = VURI.getInstance(newVURIKey)
			this.attachPackageToResource(newPackage, newVURI)
		}
	}

	private def void attachPackageToResource(EObject eObject, VURI vuri) {
		if (eObject instanceof Package) {
			val Package newPackage = eObject
			// attach the package to a resource in order to enable the calculation of
			// a Tuid in the transformations
			val ResourceSet resourceSet = new ResourceSetImpl()
			val Resource resource = resourceSet.createResource(vuri.getEMFUri())
			resource.getContents().add(newPackage)
		}
	}

	/**
	 * Special treatment for packages: we have to use the package-info file as input for the
	 * transformation and make sure that the packages have resources attached
	 * 
	 * @param change
	 *            the change that may contain the newly created package
	 */
	override propagateChange(TransactionalChange change, CorrespondenceModel correspondenceModel,
		ResourceAccess resourceAccess) {
		if (change instanceof ConcreteChange) {
			val eChange = change.EChange
			switch(eChange) {
				InsertRootEObject<?>: attachPackageToResource(eChange.newValue, change.changedVURI)
				JavaReplaceSingleValuedEAttribute<?, ?>: prepareRenamePackageInfos(eChange, change.changedVURI)
				// TODO: package deletion
			}
		}
	}

}
