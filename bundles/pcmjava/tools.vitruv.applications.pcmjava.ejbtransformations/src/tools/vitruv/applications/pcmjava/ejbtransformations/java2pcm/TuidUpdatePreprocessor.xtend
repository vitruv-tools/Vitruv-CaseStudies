package tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm

import tools.vitruv.domains.java.echange.feature.JavaFeatureEChange
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.processing.impl.AbstractEChangePropagationSpecification
import org.eclipse.emf.ecore.EObject
import tools.vitruv.domains.java.JavaDomainProvider
import tools.vitruv.domains.pcm.PcmDomainProvider
import tools.vitruv.framework.util.command.ResourceAccess

package class TuidUpdatePreprocessor extends AbstractEChangePropagationSpecification {

	new() {
		super(new JavaDomainProvider().domain, new PcmDomainProvider().domain)
	}

	override doesHandleChange(EChange change, CorrespondenceModel correspondenceModel) {
		return change instanceof JavaFeatureEChange<?, ?>;
	}

	override propagateChange(EChange change, CorrespondenceModel correspondenceModel, ResourceAccess resourceAccess) {
		if (change instanceof JavaFeatureEChange<?, ?>) {
			val oldAffectedEObject = change.oldAffectedEObject as EObject // Cast necessary due to Xcore/Xtend problem
			val newAffectedEObject = change.affectedEObject as EObject // Cast necessary due to Xcore/Xtend problem
			if (null !== oldAffectedEObject && null !== newAffectedEObject) {
				TuidManager.instance.updateTuid(oldAffectedEObject, newAffectedEObject);
			}
		}
	}

}
