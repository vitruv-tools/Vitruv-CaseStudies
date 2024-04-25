package tools.vitruv.application.viewfilter.informationview.internal;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.views.viewfilter.infostructure.model.infostructuremodel.SingleInformation;

/**
 * Transforms given models into a {@link SingleInformation}. A transformation could be 
 * counting the elements of a certain type in a given model for instance. 
 * The actual transformation depends on the overwriting implementation.
 */
public interface InformationViewTransformator {

	/**
	 * Method transforms the given models (represented by their roots) into a {@link SingleInformation}.
	 * The transformation corresponds to the calculation of the actual information, which will than 
	 * be represented by the {@link SingleInformation} instance which the method returns.
	 * A transformation could be counting the elements of a certain type in a given model for instance.
	 * The actual transformation depends on the actual implementation of this method
	 * 
	 * @param roots The list of root elements of the models which are supposed to be transformed
	 * @return The information which has been extracted from the given models
	 */
	public abstract SingleInformation transform(List<EObject> roots);
	
}
