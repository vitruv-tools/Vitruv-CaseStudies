package tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.util.transformationexecutor

import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.processing.impl.AbstractEChangePropagationSpecification
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.util.command.ResourceAccess

abstract class TransformationExecutorChangeProcessor extends AbstractEChangePropagationSpecification {
	private val TransformationExecutor transformationExecutor;

	new(VitruvDomain sourceDomain, VitruvDomain targetDomain) {
		super(sourceDomain, targetDomain);
		this.transformationExecutor = new TransformationExecutor();
	} 

	public def void addMapping(EObjectMappingTransformation transformation) {
		this.transformationExecutor.addMapping(transformation);
		this.transformationExecutor.userInteracting = userInteracting;
	}

	override protected doesHandleChange(EChange change, CorrespondenceModel correspondenceModel) {
		return true;
	}

	override protected propagateChange(EChange change, CorrespondenceModel correspondenceModel, ResourceAccess resourceAccess) {
		this.transformationExecutor.setCorrespondenceModel(correspondenceModel);
		val executor = this.transformationExecutor;
		executor.executeTransformationForChange(change, resourceAccess);
	}
	
	override setUserInteracting(UserInteracting userInteracting) {
		super.userInteracting = userInteracting;
		if (transformationExecutor !== null) this.transformationExecutor.userInteracting = userInteracting
	}

}
